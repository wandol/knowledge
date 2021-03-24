function onModalAction(action) {
	var gotoes = $.map(scenario.actions, function(a) { 
		if (a.id != action.id)
				return { "id": a.id, "name": a.name ? a.name + " (" + a.id + ")" : a.id }; 
	});	
	gotoes.unshift({ "id": action.id, "name": "_self (" + action.id + ")" }, { "id": null, "name": "_exit" });
	
	var conditions = [],
		fallback = [];
	
	if (action.conditions) {
		$.each(action.conditions, function(i, c) {
			var _c = $.extend(true, {"cid":"c_"+i, "output": {"text":"","menu":"", "value":{}}}, c);
								
			var outputvalue = [];
			if(c.output && c.output.value) {
				$.each(c.output.value, function(key, value) {
					var type;
					if($.type(value) === "string")
						type = "String";
					else {
						type = "Object";
						value = JSON.stringify(value);
					}
					
					var _outputvalue = {
						"key": key,
						"type":type,
						"value": value
					};
					outputvalue.push(_outputvalue);
				});
			}
			if(outputvalue.length == 0) outputvalue = [{"key": "","type":"String","value": ""}];
			_c.output.value = outputvalue;

			if (c.hasOwnProperty('condition')) {
				conditions.push(_c);
			}
			else {
				fallback.push(_c);
			}
			
			console.log(_c, c);
		});
	}
	if(fallback.length == 0) fallback = [{ "output": {"value":[{"key": "","type":"String","value": ""}]}, "goto": null }];

	var context = [];
	if (action.context) {
		$.each(action.context, function(key, value) {
			var type;
			if($.type(value) === "string")
				type = "String";
			else {
				type = "Object";
				value = JSON.stringify(value);
			}
			
			var _context = {
				"key": key,
				"type":type,
				"value": value
			};
			context.push(_context);
		});
	}
	if(context.length == 0) context = [{"key": "","type":"String","value": ""}];
		
	$.ajax({
		url: CONTEXTPATH + "admin/scenario/edit.bot?target=action", 
		dataType: 'html'
	}).done(function( responseText ) {
		var template = $.templates( responseText ),
			html = template.render({ 
					_action: action, 
					_goto: gotoes, 
					_filename: scnFilename, 
					_conditions:conditions, 
					_fallback:fallback,
					_context: context
				});
		$('#slideLayer').html( html );
		
		$('li a', '#action-nav').on('shown.bs.tab', function() {
			resizeAllTextarea();
		});
		
		$('#tab_conditions').on('click', '#add_outputvalue', function(e) {
			$('#template-outputvalue').clone()
			.attr('id', '')
			.removeClass('hidden')
			.insertAfter( $(this).closest('.output-value'));
		}).on('click', '#remove_outputvalue', function(e) {
			var o_v = $(this).closest('.output-value');
			if($(this).closest('.output-values').find('.output-value').length == 1)
				o_v.find('#add_outputvalue').click();
			o_v.remove();
		});
		
		loadPayload($("#tab_initial input[name=radio-initial]").closest(".radioarea"), action.initial);
		
		$.each(conditions, function(i, c) {
			loadPayload($("#tab_conditions input[name=radio-condition-"+c.cid+"]").closest(".radioarea"), c.output);
		});
		
		loadPayload($("#tab_conditions input[name=radio-fallback]").closest(".radioarea"), fallback[0].output);
		
		layer.show($('#slideLayer'));
		resizeAllTextarea();
		
		if(validScenario) {
			var errors = [];
			var validAction = ($.grep(validScenario.actions, function(a) { return a.id === action.id; }))[0];
			
			if(validAction._errorCount > 0) {
				
				if(validAction.initial && validAction.initial._error) {
					var _e = validAction.initial._error; 
					$.each(_e, function(key, value) {
						var $e = $('#tab_conditions .initial-wrap textarea[name=output-'+key+']');
						if($e.length > 0) {
							errors.push({
								group : $e.parent(),
								obj : $e,
								msg : value,
								tab : "tab_conditions"
							});
						}
					});
				}
				
				if(validAction.context && validAction.context._error) {
					var _e = validAction.context._error; 
					$.each(_e, function(key, value) {
						var $e_group = $('#tab_name .context-wrap input[name=key][value='+key+']').closest('.form-group'); 
						var $e = $e_group.find('textarea[name=value]');
						if($e.length > 0) {
							errors.push({
								group : $e.parent(),
								obj : $e,
								msg : value,
								tab : "tab_name"
							});
						}
					});
				}
			
				if(validAction.conditions) {
					$.each(validAction.conditions, function(i, c) {
						var cid = $('#tab_conditions #c_'+i);
						
						if(c._error) {
							var _e = c._error;
							var $e;
							$.each(_e, function(key, value) {
								if(key == 'condition') {
									$e = $(cid).find('textarea[name='+key+']');
									if($e.length > 0) {
										errors.push({
											group : $e.parent(),
											obj : $e,
											msg : value,
											tab : "tab_conditions"
										});
									}
								}
								else {
									if(key == 'value') {
										$.each(value, function(v_key, v_value) {
											var $e_group = $(cid).find('input[name=key][value='+v_key+']').closest('.form-group'); 
											var $e = $e_group.find('textarea[name=value]');
											if($e.length > 0) {
												errors.push({
													'group' : $e.parent(),
													'obj' : $e,
													'msg' : v_value
												});
											}
										});
									}
									else {
										$e = $(cid).find('textarea[name=output-'+key+']');
										if($e.length > 0) {
											errors.push({
												group : $e.parent(),
												obj : $e,
												msg : value,
												tab : "tab_conditions"
											});
										}
									}
								}
								
								
							});
						}
					});
				}
				
				variables.displayError(errors);
				/*
				$.each(errors, function(i, e) {
					e.error.tooltip({
						'container':'#slideLayer_wrapper',
						'placement':'bottom',
						'title':e.msg.replace(/\r?\n/g, '<br />'),
						'html':true
					});
					e.group.addClass('has-error');
					
					if(i==0) {
						setTimeout(function() {
							$("#nav_"+e.error.closest('.tab-pane').attr('id')).click();
							e.obj.focus();
						}, 100);	
					}
				});
				*/
			}
		}
	
		console.log('onmodalaction');
	});
}

function applyLayout() {
	var fw = $('#flow').width(), fh = $('#flow').height(), 
	mx = fw, my = fh,
	aw = $('.action:first').width(), ah = $('.action:first').height(), 
	data = {
		gridWidth : 40,
		gridHeight: 40,
		nodeWidth : aw,
		nodeHeight: ah,
		nodes     : {}
	};
	$.each(scenario.actions, function(i, a) {
		data.nodes[a.id] = [];
		if (a.conditions) {
			$.each(a.conditions, function(j, c) {
				if (c['goto']) {
					data.nodes[a.id].push(c['goto']);
				}
			});
		}
	});
	
	$.postJson(CONTEXTPATH + "api/domains/"+DOMAIN+"/layout", data ).done(function(data, textStatus, jqXHR) {
		$.each(data, function(i, p) {
			$('#' + p.id).css('left', p.x + 'px').css('top', p.y + 'px');
			mx = Math.max(mx, p.x + aw + 40);
			my = Math.max(my, p.y + ah*2 + 40);
		});
		
		console.log(mx, fw, my, fh);
		
		if (mx > fw || my > fh) {
			$('#flow, .container').width(mx);
			$('#flow, .scenariodetail').height(my);
			$('.navbar-left').height(my + 70);
		}
		flow.instance.repaintEverything();
	});
	console.log("apply Layout");
}

function resizeFlow() {
	var b = document.body.getBoundingClientRect();
	var pr = document.getElementById('leftMenu').getBoundingClientRect();
	var max_bottom = pr.bottom ;
	var max_right = b.right;
	if( $(".scenariodetail.full").length == 0 )
		max_right -= 220;
	
	$("#flow, .container").width( max_right );
	$("#flow, .scenariodetail").height( max_bottom - 70 );
	$(".navbar-left").height( max_bottom);
	//console.log('resizeflow 1', max_bottom);
	
	$('.action').each(function(i, _a) {
		var _h = $(_a).position().top + $(_a).height() + 100;
		var _w = $(_a).position().left + $(_a).width() + 100;
		if(_h > max_bottom)
			max_bottom = _h;
		if(_w > max_right)
			max_right = _w;
	});	
	
	var pr = document.getElementById('flow').getBoundingClientRect();

	if (max_right >= pr.right || max_bottom >= pr.bottom) {
		if (max_right > pr.right) {
			$('#flow, .scenariodetail').width( max_right );
		}
		if(max_bottom > pr.bottom) {
			$('#flow, .scenariodetail').height( max_bottom );
			$(".navbar-left").height(  max_bottom + 70 );
			//console.log('resizeflow 2', _h);
		}
		if(flow.instance) {
			flow.instance.repaintEverything();	
		}
	}
}

function saveAction() {
	$("#tab_action .has-error").removeClass("has-error");
	
	var _ret = {}, $e = [], eidx = 0;
	var $m = $('#tab_action');
	
	if($.trim($m.find('input[name=name]').val()).length == 0) {
		var $aname = $m.find('input[name=name]');
		variables.displayError(
			[{
				msg : msg_error_nameisempty, 
				group : $aname.parent(), 
				obj : $aname,
				tab : "tab_name"
			}]);
		return;
	}
	
	var a = { 
			name: $m.find('input[name=name]').val(),
			id: $m.find('input[name=id]').val(),
			context: {},
			conditions: [],
			initial: {
				'payload': getPayload($("input[name=radio-initial]:checked")),
				'menu': $m.find('.initial-wrap textarea[name=output-menu]').val()
			}
		};
	
	var keyarr = [];
	$m.find('#tab_name .context-wrap .form-group').each(function(i, div) {
		_ret = variables.check(div, keyarr, "tab_name");
		if(_ret.valid == "ok") {
			a.context[_ret.key] = _ret.value;
			keyarr.push(_ret.key);
		}
		else if(_ret.valid == "fail") {
			$e[eidx++] = _ret.error;
		}
	});

	$m.find('#tab_conditions .condition-wrap').each(function() {
		var _c = {};

		_c['output'] = { 
			'payload': getPayload($("input[type=radio]:checked", this)),
			'menu': $.trim($(this).find('textarea[name=output-menu]').val()),
			'value': {}
		};
		
		var is_error_variable = false;
		keyarr = [];
		$(this).find('.output-value').each(function(i, div) {
			
			_ret = variables.check(div, keyarr, "tab_conditions");
			if(_ret.valid == "ok") {
				_c.output.value[_ret.key] = _ret.value;
				keyarr.push(_ret.key);
			}
			else if(_ret.valid == "fail") {
				$e[eidx++] = _ret.error;
				is_error_variable = true;
			}
		});
		
		_c['goto'] = $(this).find('select[name=goto]').val() || null;
		
		var $c = $(this).find('textarea[name=condition]');
		if ($c.length) {
			var c_value =  $c.val();
			c_value =  $.trim(c_value);
			
			if(c_value == '') {
				if(_c.output.text == '' && _c.output.menu == '' && $.isEmptyObject(_c.output.value) && !is_error_variable) {
					return;
				}
				else {
					$e[eidx++] = {
							msg : msg_error_condition_empty, 
							group : $c.parent(), 
							obj : $c,
							tab : "tab_conditions"
						};	
				}
			}
			else if(c_value.indexOf("{{") == 0 && c_value.indexOf("}}") == c_value.length-2) {
				c_value = c_value.replace("{{", "");
				c_value = c_value.replace("}}", "");
			}
			
			_c['condition'] = c_value;	
		}

		a.conditions.push(_c);
	});
	
	if($e && $e.length > 0) {
		console.log("ERROR", $e);
		variables.displayError($e);
		return;
	}

	var index = $.map(scenario.actions, function(a, i) { return a.id; } ).indexOf(a.id);
	//액션추가
	if (index === -1) {
		_nextId++;
		var template = $.templates('#template-flow'),
			html = template.render( { 'actions': [ a ] } ),
			$action = $(html).css('left','40px').css('top','40px');
		$('#flow').append( $action);
		scenario.actions.push( a );
		flow.dragndrop( $action );
	} 
	//액션수정
	else {
		scenario.actions[ index ] = a;
		$('#' + a.id).find('.b-title').text(a.name);
	}
	
	a.position = $('#' + a.id).position();

	//전체 액션 다시 연결
	$('#' + a.id).find(".self_line").remove();
	$(flow.instance.getConnections({scope:a.id})).each(function(idx2, conn) {
		flow.instance.deleteConnection(conn);
	});
	
	$.each(a.conditions, function(i, c) {
		flow.connect( a.id, c['goto']);	
	});
	
//	console.log(JSON.stringify(a));
	
	$('#slideLayer').empty();
	layer.hide($('#slideLayer'));
}

function saveScenario() {
	$('.box.action').removeClass('has-error');
	
	var _ts = $.extend(true, {}, scenario);
	
	$.each(_ts.actions, function(i, action) {
		action.position = $('#'+action.id).position();
		
		if (action.conditions) {
			$.each(action.conditions, function(i, c) {
				
				if(c.condition) {
					c.condition = "{{" + c.condition + "}}";
				}
			});
		}
	});
	
	$.postJson(CONTEXTPATH + "api/domains/"+ DOMAIN + "/scenarios/"+scnFilename+"?action=save", _ts).done(function( ret ) {
		validScenario = ret.data;
		
		if(ret.code == 200) {
   			layer.info(ret.message);
		}
		else if(ret.code == 400) {
			console.log(ret.data);
			var errorActionId;
			
			$.each(ret.data.actions, function(actionId, validAction) {
				if(validAction._errorCount > 0) {
					$('#'+validAction.id).addClass('has-error');
					if(!errorActionId) errorActionId = validAction.id;
				}
			});
			
			layer.error(ret.message, function() {
				if(errorActionId) {
					var actions = $.grep(scenario.actions, function(a) { return a.id === errorActionId; });
					if (actions.length === 1) {
						onModalAction(actions[0]);
					}
				}
			});
		}
		
		_ts = null;
	});
}

function _getActiveActions() {
	var actionIds = $.map($('.box.action.active'), function(a) {return $(a).attr('id');});
	console.log('copy active ids', actionIds);
	
	var actions = null;
	if(actionIds.length > 0)
		actions = $.grep(scenario.actions, function(a) { return $.inArray(a.id, actionIds)  > -1; });
	console.log('get active actions', actions);
	
	return actions;
}

function copyActionClipboardEvent(e) {
	e.stopPropagation();
	e.preventDefault();
	
	var actions = _getActiveActions();
	if(actions.length > 0) {
		var clipdata = JSON.stringify(actions);
		console.log('copy clipdata', clipdata);
		
		var clipboardData = e.clipboardData || window.clipboardData 
						|| (e.originalEvent && e.originalEvent.clipboardData ? e.originalEvent.clipboardData : null);
		
		if(clipboardData)
			clipboardData.setData('Text', clipdata);
	}
}

function pasteActionClipboardEvent(e) {
	e.stopPropagation();
	e.preventDefault();

	var clipboardData, pastedData;
	clipboardData = e.clipboardData || window.clipboardData || e.originalEvent.clipboardData;
	
	var clipdata = clipboardData.getData('Text');
	console.log('paste clipdata ', clipdata);
	
	var actions = JSON.parse(clipdata);
	console.log('paste actions ', actions);
}

function copyAction() {
	var actions = _getActiveActions();
	$(window).data('actions', actions);
	if(actions && actions.length > 0) {
		layer.info(msg_scenariodetail_info_copy_clipboard);
	}
}

function pasteAction(e) {
	var actions = $(window).data('actions');
	console.log('paste actions ', actions);
	
	if(actions && actions.length > 0) {
		$('.box.action').removeClass('active');
		
		$.each(actions, function(idx, action) {
			var a = $.extend({}, action);
			
			a.id = 'action_' + _nextId;
			_nextId++;
			
			a.position.left += 40;
			a.position.top += 40;
			
			var template = $.templates('#template-flow'), 
			html = template.render({'actions' : [ a ]}),
			$action = $(html).css('left', a.position.left + 'px')
							.css('top',a.position.top + 'px')
							.css('z-index', 4)
							.addClass('active');
			
			if (a.conditions) {
				$.each(a.conditions, function(i, c) {
					c['goto'] = null;
				});
			}

			$('#flow').append($action);
			
			scenario.actions.push(a);
			flow.dragndrop($action);
			
			resizeFlow();
		});
	}
}

//addAction
$('#addAction').on('click', function(e) {
	onModalAction( { id: 'action_' + _nextId, context: {}, conditions: [ { output: {} } ] } );
});

//saveScenario
$('#saveScenario').on('click', function(e) {
	layer.confirm(msg_confirm_save_scenario, function() {
		saveScenario();
	});
});

//autoPosition
$('#autoPosition').on('click', function(e) {
	applyLayout();
});

//flow
$('#flow').on('click', '.btn-setting', function(e) {
	var id = $(this).closest('.action').attr('id'),
		actions = $.grep(scenario.actions, function(a) { return a.id === id; });
	if (actions.length === 1) {
		onModalAction(actions[0]);
	}
}).on('click', '.btn-delete', function(e) {
	var $action = $(this).closest('.action'),
		id = $action.attr('id');
	scenario.actions = $.grep(scenario.actions, function(a) { return a.id != id; });
	flow.instance.remove(id);
}).on('click', '.btn-copy', function(e) {
	e.stopPropagation();
	e.preventDefault();
	
	$('.box.action').removeClass('active');
	$(this).closest('.action').addClass('active');
	
	copyAction();
}).on('click', '.box.action', function(e) {
	  console.log('CTRL pressed during click:', e.ctrlKey);
	  if(!e.ctrlKey)
		  $('.box.action').removeClass('active');
	  $(this).toggleClass('active');
});

var pasteFlag = false;
var resizeTimer;
$(window).on('resize',function () {
	clearTimeout(resizeTimer);
	resizeTimer = setTimeout(function() {
		resizeFlow();
	}, 250);
}).on('beforeunload', function(e) {
	e = e || window.event;
	// For IE<8 and Firefox prior to version 4
	if (e) e.returnValue = msg_scenariodetail_confirm_unload;
	// For Chrome, Safari, IE8+ and Opera 12+
	//return msg_scenariodetail_confirm_unload;
});

//slideLayer
$('#slideLayer').on('click', '#add_context', function(e) {
	$('#template-context').clone()
		.attr('id', '')
		.removeClass('hidden')
		.insertAfter( $(this).closest('.context'));
}).on('click', '#remove_context', function(e) {
	var c = $(this).closest('.context');
	if($(this).closest('.context-wrap').find('.context').length == 1)
		c.find('#add_context').click();
	c.remove();
}).on('click', '#add_condition', function(e) {
	var cidx = $('#tab_conditions .condition-wrap').length;
	$('#tab_conditions .condition-wrap').each(function() {
		var _cidx = parseInt($(this).attr("id").substring(2));
		if($.isNumeric(_cidx) && _cidx >= cidx){
			cidx = _cidx + 1;
		}
	});
	
	var html = $('#template-condition').clone();
	$(html).attr('id', "c_" + cidx)
		.removeClass('hidden')
		.insertBefore( $(this).parent() );

	loadPayload($(html).find(".radioarea"), "");
}).on('click', '#remove_condition', function(e) {
	$(this)
		.closest('.tab-form-wrap')
		.remove();
}).on('keyup keydown paste', 'textarea', function(e) {
	resize(this, e);
}).on('focus', 'textarea', function(e) {
	variables.autocomplete(this);
});

