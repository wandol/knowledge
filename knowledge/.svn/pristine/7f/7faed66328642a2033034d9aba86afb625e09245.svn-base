function enterSearchEntity(e) {
	if(e && e.which === $.ui.keyCode.ENTER) {
		$("#entitySearchBtn").click();
		return false;
	}
}

function searchEntity() {
	printList($.trim($("#entitySearchKwd").val()));
	filter.reset($("#entity_grid"));
	$("#chkWarning").prop("checked", false);
}

function printList(keyword) {
	blocking.on();
	console.log("get entities");
	
	$.post(CONTEXTPATH + "api/domains/"+DOMAIN+"/entities", 
			{keyword: keyword}, 
			function(ret) {
		console.log(ret);
		
		if(ret.code == "200") {
			var entitylist = ret.data;
			$("#entity_grid").gridSetCount(entitylist.length);
			$("#entity_grid").refreshGrid(entitylist);
		}
		else {
			blocking.on(msg, "error");
			$("#entity_grid").refreshGrid();
			blocking.off(1000);
		}
	})
	.fail(function(xhr){
		layer.error(xhr.responseText, blocking.off());
	})
	.always(function() {
		blocking.off();
	});
}

var entityCellRenderer = {};
entityCellRenderer.entity = function(value, item){
	var $cell = $("<td />").addClass(this.name).data("field", this);
	
	var $span = $("<span />").text(value);
	if(item.hasOwnProperty('warning') && item.warning != null && Object.keys(item.warning).length > 0) {
		$cell.addClass("has-error");
		if(item.warning.ner)
			$span.addClass("has-error");
	}
	$cell.append($span).attr({"title" : value});
	return $cell;
};
entityCellRenderer.reference = function(value, item){
	return entityCellRenderer.exec(value, item, 1);
};
entityCellRenderer.tfidf = function(value, item){
	return entityCellRenderer.exec(value, item, 2);
};
entityCellRenderer.type = function(value, item){
	var $cell = $("<td />").addClass(this.name).data("field", this);
	
	var warnDic;
	if(item.hasOwnProperty('warning') && item.warning != null && Object.keys(item.warning).length > 0) {
		$cell.addClass("has-error");
		if( item.warning.hasOwnProperty('type') )
			warnDic = item.warning['type'];
	}
	
	$.each(dictionaries, function(type, file) {
		var $button = $("<button class='btn btn-default btn-sm' title='"+file+"'>"+type+'</button>');
		
		if($.inArray(type, value) == -1)
			$button.prop('disabled', true);
		
		$button.on("click", function() { 
			goDictionary('ner', item.ner, file); 
		});
		
		if($.inArray(type, warnDic) > -1)
			$button.addClass('has-error');
		
		$cell.append($button);
	});
	
	return $cell;
};

entityCellRenderer.syn = function(value, item){
	var $cell = $("<td />").addClass(this.name).data("field", this);
	
	var warnSyn;
	if(item.hasOwnProperty('warning') && item.warning != null && Object.keys(item.warning).length > 0) {
		$cell.addClass("has-error");
		
		if(item.warning.hasOwnProperty('syn'))
			warnSyn = item.warning['syn'];
	}
		
	for (var i = 0; i < value.length; i++) {
		var $span = $("<span />").text(value[i]);
		
		if($.inArray(value[i], warnSyn) > -1)
			$span.addClass("has-error");

		if(i>0)
			$cell.append(", ");
		
		$cell.append($span);
	}
	$cell.attr({"title" : value});
	
	return $cell;		
};

entityCellRenderer.warning = function(value, item) {
	var $cell = $("<td />").addClass(this.name).data("field", this);
	
	value = "";
	if(item.hasOwnProperty('warning') && item.warning != null && Object.keys(item.warning).length > 0) {
		console.log(item);
		
		if(item.warning.hasOwnProperty('syn') || item.warning.hasOwnProperty('ner') ) {
			if(value.length > 0) value += ", ";
			value = msgWarningCyclerefer;
		}
		if(item.warning.hasOwnProperty('type') ) {
			if(value.length > 0) value += ", ";
			value += msgWarningSetweight;
		}
		if(item.warning.hasOwnProperty('reference') ) {
			if(value.length > 0) value += ", ";
			value += msgWarningNorefer;
		}
			
		$cell.addClass("has-error");
	}
	
	$cell.text(value).attr({"title" : value});
	
	return $cell;
};

entityCellRenderer.exec = function(value, item, collIdx) {
	var $cell = $("<td />").addClass(this.name).data("field", this);
	if(value == undefined || value == null){
		value = "";
	}
	
	if(item.hasOwnProperty('warning') && item.warning != null && Object.keys(item.warning).length > 0) {
		$cell.addClass("has-error");
	}
	
	$cell.text(value).attr({"title" : value});
	
	return $cell;
};

function clickSyn(item) {
	if(item.syn != null && item.syn.length > 0)
		goDictionary('syn', item.ner, "syn.txt");
}

function chgChkWarning(obj) {
	if($(obj).is(":checked")) {
		$("#entity_grid").data(GRID_DATA_KEY).search().done(function(data) {
			$("#entity_grid").gridSetCount(data.length);
		});	
	}
	else {
		$("#entity_grid").refreshGrid($("#entity_grid").data(GRID_ITEMS_KEY));
		$("#entity_grid").gridSetCount($("#entity_grid").data(GRID_ITEMS_KEY).length);
		filter.reset($("#entity_grid"));
	}
} 

function clickNer(item) {
	blocking.on();
	
	$("#explorer_container").empty();
	$("#questionCount .number").text("-");
	$("#questionlist_grid").refreshGrid();
	
	if(item.ner != null && item.ner.length > 0){
		
		$.post(CONTEXTPATH + "api/domains/"+DOMAIN+"/entiQuestion", 
			{entity: item.ner}, 
			function(ret) {
				$("#questionlist_grid").refreshGrid(ret.data.questions);
				$("#questionCount .number").text(ret.data.questions.length);
				
				if(ret.data.explorers && ret.data.explorers.length > 0) {
					$("#explorer_container").visualization({
						data:ret.data.explorers,
						type:'explorer',
						linkClass :'link',
						nodeClass :'node',
						removeUse: false,
						click: function(d) {
							if(d.depth > 0) {
								var grid = $("#entity_grid").data(GRID_DATA_KEY);
								var $row = null;
								var flag = false;
								
								$.each(grid.data, function(_index, _item) {
									if(_item.ner == d.ner) {
										grid.openPage(Math.ceil(_index/grid.pageSize));	
										$row = $(".jsgrid-row, .jsgrid-alt-row", "#entity_grid").get(_index%grid.pageSize);
										flag = false;
										return false;
									} else {
										flag = true;
									}
								});
								if(flag ){
									layer.warning(msgEmptyEntity);
								}
								if($row != null) {
									$(".ner", $row).click();
								}	
							} 
						}
					});	
				}
		}).always(function() {blocking.off(100);});
	}
	else
		blocking.off();
}