var GRID_DATA_KEY = "JSGrid";
var GRID_ROW_DATA_KEY = "JSGridItem";
var GRID_ROW_SELECTED = "grid-row-selected";
var GRID_ROW_ASK_TARGET = "grid-row-target";
var GRID_ROW_DATA_CHANGE = "item_chg";
var GRID_ITEMS_KEY = "JSGridItems";
var GRID_UPDATE_DATA_KEY = "grid-update-item-list";
var GRID_DELETE_DATA_KEY = "grid-delete-id-list" 
	
$.fn.initializeGrid = function(srcfields, hasCheckbox, hasPager) {
	var $grid = this;
	
	var fields = srcfields.slice();		
	if (hasCheckbox)
		fields.unshift(fieldCheckBox);
	
	$.each(fields, function(key, field) {
		if(field.type == 'button') {
			$.extend(field, fieldButton);
		}
	});
	
	var gridHeight = $grid.height();
	var options = {
		fields : fields,
		data : [],
		autoload : true,

		width : "98%",
		height : gridHeight,

		heading : true,
		filtering : false,
		inserting : false,
		editing : true,
		selecting : true,
		sorting : true,
		loadIndication : false,
		paging : false,

		noDataContent : msg_result_notfound,
		confirmDeleting : false,
		rowRenderer: rowRenderer
	};
	
	if(hasPager) {
		var pagerId = "#" + $grid.attr("id") + "_pager";
		var pageSize = parseInt(gridHeight / 33.5) - 1;
		
		$.extend(options, {
			paging: true,
			pageSize: pageSize,
			pageButtonCount: 10,
			pagerContainer: pagerId,
			pagerFormat: "{first} {pages} {last}",
	        pageFirstText: "&laquo;",
	        pageLastText: "&raquo;",
		});
	}
	
	if($grid.is($("#intents_grid"))) {
		$.extend(options, {
			controller: {
				loadData:function(filter) {
		            var items = $grid.data(GRID_DATA_KEY).data;
					return $.grep(items, function(item) {
						return (!filter.top || $.inArray(item.top, filter.top) > -1) 
							&& (!filter.type || filter.type.length == 0 || $.inArray(item.type, filter.type) > -1);
					});
				}
			}
		});
	}
	else if($grid.is($("#entity_grid"))) {
		$.extend(options, {
			controller: {
				loadData:function(filter) {
		            var items = $grid.data(GRID_DATA_KEY).data;
		            var warning = $("#chkWarning").is(":checked");
					return $.grep(items, function(item) {
						var isGrep1 = false;
						if(warning) {
							if(item.warning != null && !$.isEmptyObject(item.warning))
								isGrep1 = true;	
						}
						else
							isGrep1 = true;
								
						var isGrep2 = false;
						if(filter.type) {
							$.each(filter.type, function(idx, type) {
								if($.inArray(type, item.type) > -1) {
									isGrep2 = true;
									return false;
								}
							});
						}
						else
							isGrep2 = true;

						var isGrep = isGrep1 && isGrep2; 
						return isGrep;
					});
				}
			}
		});
	}
	else if($grid.is($("#keyword_grid"))) {
		$.extend(options, {
			controller: {
				loadData:function(filter) {
		            var items = $grid.data(GRID_DATA_KEY).data;
					return $.grep(items, function(item) {
						return (!filter.keyword || item.keyword.toLowerCase().indexOf(filter.keyword.toLowerCase()) > -1);
					});
				}
			}
		});
	}
	
	var dbclick = false;	
	$.extend(options, {
		rowClick : function(args) {
			if($grid.is($("#questionlist_grid"))) {
				return false; //intentList.manageRow(args.item);
			}
			console.log("row click", args);
			var $row = $(args.event.currentTarget);
			if (this._editingRow) {
				var editrow = this._getEditRow();
				if ($(args.event.target).is(editrow)) {
					if (!$(args.event.target).is("input[type=checkbox]")) {
						editrow.click();
					}
				} else {
					editrow.click();
					$(".jsgrid-row, .jsgrid-alt-row").removeClass(GRID_ROW_SELECTED);
				}
			} 
			else {
				var updatedItem = $.extend({}, args.item);
				
				if (!($row.hasClass("jsgrid-row") || $row.hasClass("jsgrid-alt-row")))
					$row = $(args.event.target).parents(".jsgrid-row, .jsgrid-alt-row");

				if(!$(args.event.target).is("input[type=checkbox]")) {
					if(!$row.hasClass(GRID_ROW_SELECTED)) {
						$(".jsgrid-row, .jsgrid-alt-row").removeClass(GRID_ROW_SELECTED);
						$row.addClass(GRID_ROW_SELECTED);
					}
					
					var _target = $(args.event.target), field;
					
					if(_target.is("td")) field = $(args.event.target).data("field");
					else field = $(args.event.target).parents("td").data("field");
					
					setTimeout(function() {
						if(dbclick) {
							dbclick = false;	
						}
						else if(field !== undefined && field.clickEvent) {
							field.clickEvent(args.item);
						}
					}, 250);	
				}
			}
		},
		rowDoubleClick : function(args) {
			
			if($grid.is($("#intents_grid")) || $grid.is($("#entity_grid")) || $grid.is($("#questionlist_grid"))) {
				return false; //intentList.manageRow(args.item);
			}
			else {
				dbclick = true;
				
				if (this._editingRow) {
					this._getEditRow().click();
				}
				$grid.jsGrid("editItem", args.item);
			}
		},
		onItemEditing : function(args) {
			if(args.item.id) {
				args.cancel = true;
			}
		},
		onItemEdited : function(args) {
			var $this = this;
			
			$("input[type=text]:first", $(args.editrow)).focus();
			
			args.editrow.on("click", $.proxy(function(event) {
				if (!($(event.target).is("input[type=text]") || $(event.target).is("input[type=checkbox]"))) {
					
					var editedItem = {};
					$this._eachField(function(field) {
						
						var callback = function(editValue) {
							$this._setItemFieldValue(editedItem, field, editValue);
						};
					
						if(field.editing) {
							callback(field.editValue());
						}
		            });
					
					editedItem.chkGrid = args.editrow.find("input[type=checkbox]").is(":checked");
					this.updateItem(args.item, editedItem);	
				}
			}, this));
		},
		onItemUpdated : function(args) {
			console.log(args);
			
			if($grid.is($("#intents_grid")) ) {
				if(args.item.pkey.indexOf("new") == 0)
					_itemUpdRow();
				else
					$.each(args.item, function(key, value) {
						//console.log(key, args.previousItem[key] , "=>", value);
						if(args.previousItem[key] != value) {
							_itemUpdRow();
							return false; 
						}
					});	
			} 
			else if(($grid.is($("#ne_grid")) && args.previousItem.ne != args.item.ne) || ($grid.is($("#keyword_grid")) && args.previousItem.keyword != args.item.keyword)) {
				_itemUpdRow();
			}
			else {
				args.item[GRID_ROW_DATA_CHANGE] = false;
				args.row.removeClass(GRID_ROW_DATA_CHANGE);
			}
			
			args.row.addClass(GRID_ROW_SELECTED);
			
			function _itemUpdRow() {
				args.item[GRID_ROW_DATA_CHANGE] = true;
				args.row.addClass(GRID_ROW_DATA_CHANGE);
				$grid.gridSetItemsByChgRow(args.item, false);
			}
		},
		onItemInserted : function(args) {
			var insertedRow = $grid.find(".jsgrid-row, .jsgrid-alt-row").get(args.itemIndex);
			$(".jsgrid-row, .jsgrid-alt-row").removeClass(GRID_ROW_SELECTED);
			$(insertedRow).addClass(GRID_ROW_SELECTED);
			//$(insertedRow).click();
			this.updateItem(args.item);
		},
		onItemDeleted : function(args) {
			$grid.data(GRID_ITEMS_KEY).splice($.inArray(args.item, $grid.data(GRID_ITEMS_KEY)), 1);
		}
	});
	
	$grid.jsGrid(options);
	$grid.refreshGrid();
};

$.fn.refreshGrid = function(data, isKeepChgData) {
	//GRID_IS_MOVE_PAGE = false;
	
	$(this).jsGrid("openPage", 1);
	
	if(!data) data = [];

	$(this).data(GRID_ITEMS_KEY, data);
	
	if(!isKeepChgData) {
		$(this).data(GRID_UPDATE_DATA_KEY, []);
		$(this).data(GRID_DELETE_DATA_KEY, []);	
	}
	
	$(this).jsGrid("option", "data", data);	
	$(this).jsGrid("refresh");
};

$.fn.gridGetItems = function(isCheckedRow) {
	var items = $.map($(this).find(".jsgrid-row, .jsgrid-alt-row"), function(value, key) {
		if(isCheckedRow) {
			if ($(value).find("input[name=chk_grid]").is(":checked")) {
				return $(value).data(GRID_ROW_DATA_KEY);
			}
		} else {
			return $(value).data(GRID_ROW_DATA_KEY);	
		}
	});
	return items;
};

$.fn.gridSetItemsByChgRow = function(item, isdel) {
	var $grid = $(this);
	var params = $grid.gridGetItemsByChgRow();
	
	if(isdel) {
		$.each(params, function(i, param){
			if(param.pkey == item.pkey) {
				$grid.data(GRID_UPDATE_DATA_KEY).splice(i, 1);
				return false;
			}
		});
	}
	else {
		var isexist = false;
		
		$.each(params, function(i, param){
			if(param.pkey == item.pkey) {
				param = item;
				isexist = true;
				return false;	
			}
		});
		
		if(!isexist) {
			$grid.data(GRID_UPDATE_DATA_KEY).push(item);	
		}
	}
	
	console.log($grid.gridGetItemsByChgRow());
};

$.fn.gridGetItemsByChgRow = function() {
	var $grid = $(this);
	var items = $.map($grid.data(GRID_UPDATE_DATA_KEY), function(item, idx) {
		delete item[GRID_ROW_DATA_CHANGE];
		delete item["chkGrid"];
		if($grid.is($("#intents_grid")) && item.pkey && item.pkey.indexOf("new") == 0 && item.path.length == 0)
			return null;
		return item;
	});
	return items;
};

$.fn.gridAddRow = function(iteminfo) {
	var $grid = this;
	
	var $selectedRow = $grid.find("."+GRID_ROW_SELECTED);
	console.log("selectedRow ==> ", $selectedRow);
	
	var isSelectedRow = $selectedRow.length > 0;
	
	var selectedItemIndex = 0;
	if(isSelectedRow)
		selectedItemIndex = $grid.data(GRID_DATA_KEY)._itemIndex($selectedRow.data(GRID_ROW_DATA_KEY));
	else if($grid.jsGrid("option", "paging")) {
		var pageSize = $grid.jsGrid("option", "pageSize");
		var pageIndex = $grid.jsGrid("option", "pageIndex");
		selectedItemIndex = (pageIndex-1) * pageSize;
	}
	
	console.log("addrow iteminfo ==> ", iteminfo);
	
	var insertingItem;
	if(arguments.length === 0) {
		insertingItem = $.extend({"pkey":"new|"+$.now()}, $grid.data(GRID_DATA_KEY)._getValidatedInsertItem());
    }
	else {
		insertingItem = $.extend(iteminfo, $grid.data(GRID_DATA_KEY)._getValidatedEditedItem());
	}
	
	$grid.jsGrid("insertItem", insertingItem, selectedItemIndex);
};

$.fn.gridUpdateRow = function(iteminfo) {
	var $grid = this;
	var $selectedRow = $grid.find("."+GRID_ROW_SELECTED);
	var updatingItem = $.extend(iteminfo, $grid.data(GRID_DATA_KEY)._getValidatedEditedItem());
	$grid.data(GRID_DATA_KEY)._updateRow($selectedRow, updatingItem);
};

$.fn.gridDeleteRow = function(isCheckedRow) {
	var $grid = this;
	var items = $grid.gridGetItems(isCheckedRow);
	$.each(items, function(key, value) {
		if(this.pkey && this.pkey.indexOf("new") != 0) {
			$grid.data(GRID_DELETE_DATA_KEY).push(this.pkey);
		}
		$grid.gridSetItemsByChgRow(this, true);
		$grid.jsGrid("deleteItem", this);
	});
};

$.fn.gridSetCount = function(count) {
	$(".grid-count").text(count);
	$(".countarea").show();
};

var fieldCheckBox = {
	align : "center",
	width : "3%",
	sorting : false,
	name : "chkGrid",
	headercss : "checkbox-cell",
	headerTemplate : function(value) {
		return $("<input>").attr({
			"type" : "checkbox",
			"id" : "chkAll"
		}).prop("checked", false).on("change", function() {
			$('input[name="chk_grid"]').prop('checked', this.checked).change();
		});
	},
	editTemplate : function(value) {
		var $row = this._grid.rowByItem(arguments[1]);
		value = $row.find("input[name=chk_grid]").prop("checked");
		return $("<input>").attr({
			"type" : "checkbox",
			"name" : "chk_grid"
		}).prop("checked", value);
	},
	cellRenderer : function(value, item) {
		if (!value) value = false;
		
		var $input = $("<input />").attr({	"type" : "checkbox",	"name" : "chk_grid"	});
		$input.prop("checked", value)
				.on("change", function() {
					$(this).parents(".jsgrid-row, .jsgrid-alt-row").click();
				});
		
		var $cell = $("<td />").addClass("checkbox-cell");	
		$cell.append($input);
		
		return $cell;
	}
};

var fieldButton = {
	itemTemplate : function(value, item) {
		var $this = this;
		var iconName = $this.iconName;
		
		if(value) {
			iconName += "-" + value;
		}
		
		var $button = $('<button class="btn btn-'+ iconName+'"/>');
		$button.on("click", function() {
			$this.clickEvent(item);

			$(".btn-hover").removeClass("btn-hover");
			$(this).addClass("btn-hover");
		});
		return $button;
	},
	editTemplate : function(value, item) {
		var $this = this;
		var $row = this._grid.rowByItem(arguments[1]);
		var $button = $row.find(".btn.btn-"+$this.iconName).clone();
		$button.on("click", function() {
			$this.clickEvent(item);	
	
			$(".btn-hover").removeClass("btn-hover");
			$this._grid.rowByItem(item).find(".btn-"+$this.iconName).addClass("btn-hover");
		});
		return $button;
	},
};

var rowRenderer = function(item, itemIndex) {
	var orgItem = $.extend(true, {}, item);
	var $row = $("<tr>").data("OrgItem", orgItem);
	if(item[GRID_ROW_DATA_CHANGE])
		$row.addClass(GRID_ROW_DATA_CHANGE);
		
	this._renderCells($row, item);
	return $row;
};

var cellRenderer = function(value, item) {
	var $cell = $("<td />").addClass("wordwrap").addClass(this.name).data("field", this);
	if(value == undefined || value == null)
		value = "";
	$cell.append(value).attr({"title" : value});
	return $cell;
};

var nowrapCellRenderer = function(value, item) {
	var $cell = $("<td />").addClass(this.name).data("field", this);
	if(value == undefined || value == null)
		value = "";
	$cell.text(value).attr({"title" : value});
	return $cell;
};

//HEADER FILTER
var filter = {};
filter.template = function() {
	return $.templates('#filter-template').render({'title':this.title, 'name':this.name});
};
filter.printMenu = function($grid, name, menus) {
	if(menus.length > 0) {
		var $menu = $($.templates('#filter-menu-template').render({'name':name, 'menus':menus}));
		var $header = $grid.find(".filter-"+name);
		
		if($header.find(".dropdown").length > 0) {
			$header.find(".dropdown").remove();
			$header.data("mark", []);
		}
		
		var mark = $.map(menus, function(menu) {
			try {
				return $.map(menu, function(value, key) {return key;});
			} 
			catch(e) {
				return menu;
			}
		});
		
		$header.data("mark", mark);
		$header.append($menu);
		$header.find(".dropdown").on('hidden.bs.dropdown', function () {filter.chk.revert($header);});	
	}
};
filter.reset = function($grid) {
	$grid.find(".btn-drop").removeClass("flt");
	$grid.find("input[type=checkbox]").prop("checked", true);
	$grid.find(".grid-filter").data("mark", []);
};
filter.mark = function($header) {
	$header.data("mark", []);
	
	var $chked = $header.find("input[name=chk_"+$header.data("name")+"]:checked");
	$chked.each(function() {
		$header.data("mark").push($(this).closest("li").data("menu").toString());
	});
};
filter.toggle = function(e) {
	e.stopPropagation();
	if($(e.target).closest(".btn-droplist").length == 0) {
		$(".btn-drop", e.currentTarget).dropdown('toggle');
	}
};
filter.ok = function(btn_ok) {
	var $header = $(btn_ok).closest(".grid-filter");
	
	filter.mark($header);
	
	var search_data = {};
	var $grid = $header.closest(".jsgrid");
	$grid.find(".grid-filter").each(function() {
		if($(this).data("mark"))
			search_data[$(this).data("name")] = $(this).data("mark");
	});
	
	$grid.refreshGrid($grid.data(GRID_ITEMS_KEY), true);
	
	$header.find(".btn-drop").removeClass("flt");
	$header.find("input[type=checkbox]").prop("checked", true);
	
	if(!$.isEmptyObject(search_data)) {
		$grid.data(GRID_DATA_KEY).search(search_data).done(function(data) {
			var $chk = $header.find("input[name=chk_"+$header.data("name")+"]");
			if($chk.length == search_data[$header.data("name")].length) {
				$header.find(".btn-drop").removeClass("flt");
			}
			else {
				$header.find(".btn-drop").addClass("flt");
			}
			$grid.gridSetCount(data.length);
		});
	}
};
filter.cancel = function(btn_cancel) {
	filter.chk.revert($(btn_cancel).closest(".grid-filter"));
};

filter.chkall = {};
filter.chkall.change = function(chkbox) {
	var $header = $(chkbox).closest(".grid-filter");
	var $chk = $header.find("input[name=chk_"+$header.data("name")+"]");
	$chk.prop("checked", $(chkbox).prop("checked"));
	$header.find(".dropdown-ok").prop("disabled", !$(chkbox).prop("checked"));
};
filter.chkall.check = function($header) {
	var $chk = $header.find("input[name=chk_"+$header.data("name")+"]");
	var $chked = $header.find("input[name=chk_"+$header.data("name")+"]:checked");
	
	if($chked.length == 0) {
		$header.find(".dropdown-ok").prop("disabled", true);
	}
	else {
		$header.find(".dropdown-ok").prop("disabled", false);
		$("input[name=chk_flt_all]", $header).prop("checked", $chk.length == $chked.length);
	}
};

filter.chk = {};
filter.chk.change = function(chkbox) {
	filter.chkall.check($(chkbox).closest(".grid-filter"));
};
filter.chk.revert = function($header) {
	var $chk = $header.find("input[name=chk_"+$header.data("name")+"]");
	$chk.each(function() {
		$(this).prop("checked", $.inArray($(this).closest("li").data("menu").toString(), $header.data("mark")) > -1 ? true : false);
	});
	filter.chkall.check($header);
};