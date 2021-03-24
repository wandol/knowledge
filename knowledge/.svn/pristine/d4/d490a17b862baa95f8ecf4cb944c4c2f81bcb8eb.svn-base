var ALL_VARIABLE, RESERVED_VARIABLE;
$(document).ready(function() {
	var VARIABLE_INFO = {};
	if(typeof getVariableUrl !== 'undefined' && getVariableUrl && getVariableUrl.length > 0) //CONTEXTPATH + "scenario/variables.bot";
		VARIABLE_INFO = ($.getJsonSync(getVariableUrl)).data.data;
	
	if(!$.isEmptyObject(VARIABLE_INFO)) {
		ALL_VARIABLE = $.merge($.merge( [], VARIABLE_INFO.reserved ), VARIABLE_INFO.variables );
		RESERVED_VARIABLE = $.map( VARIABLE_INFO.reserved, function(va) {
			return va.name;
		});	
	}	
});

var variables = {};
variables.check = function(groupobj, keyarr, tabname) {
	var $k = $(groupobj).find('input[name=key]'),
			_k = $.trim( $k.val() ),
		$v = $(groupobj).find('textarea[name=value]'),
			_v = $.trim( $v.val() ),
		_t = $(groupobj).find("select[name=type]").val();
	
	if(_k != "" || _v != "") {
		console.log(_k, _v, _t, keyarr);
		
		var validResult = "ok";
		var message;
		
		if(_k == "" && _v != "") {
			validResult = "fail";
			message = msg_error_variable_keyname;
		} else if (_k != "" && _v == "") {
			validResult = "fail";
			message = msg_error_variable_empty;
		}
		else if($.inArray(_k, keyarr) > -1) {
			validResult = "fail";
			message = msg_error_variable_duplicate;
		}
		else if(_k.indexOf("_") == 0 && $.inArray(_k, RESERVED_VARIABLE) > -1) {
			validResult = "fail";
			message = msg_error_variable_reserved;
		}
		else if(_t != "String") {
			try {
				_v = JSON.parse(_v);
			}
			catch (e) {
				console.log(e);
				validResult = "fail";
				message = msg_error_variable_jsonparsing;
			}
		}
		
		return {valid : validResult, key : _k, value : _v, error: {msg : message, group : groupobj, obj : $k, tab : tabname}};
	}
	else return {isValid : "none"};
};

variables.displayError = function(errors, defaultMessage) {
	$.each(errors, function(i, e) {
		if(e.msg && e.msg != undefined && e.msg.length > 0) {
			$(e.obj).tooltip({
				'container':'#layer_area_wrapper',
				'placement':'bottom',
				'title':e.msg.replace(/\r?\n/g, '<br />'),
				'html':true
			});	
		}
		$(e.group).addClass('has-error');
	});
	
	if(defaultMessage) {
		layer.error(defaultMessage);
	}
	
	if(errors[0].tab) {
		$("#nav_"+errors[0].tab).click();
	}
	$(errors[0].obj).focus();
};

variables.autocomplete = function(obj) {
	$(obj).textcomplete('destroy');
	
	var isCondition = $(obj).attr("name") == "condition";
	var _vtype = $(obj).parent().prev().children("select").val();
	console.log(_vtype);
	
	if(!_vtype || _vtype == "String") {
		$(obj).textcomplete(
				[
				{
				    id: 'tech-companies',
				    props: ALL_VARIABLE,
				    match: /\B{{([\-+\w]*)$/,
				    search: function (term, callback) {
				        callback($.map(this.props, function (prop) {
			        		return prop.name.indexOf(term) >= 0 ? prop : null;
				        }));
				    },
				    template: function (prop) {
				    	return "<strong class='variabletype'>" + prop.label + "</strong> " + prop.name;
			        },
				    index: 1,
				    replace: function (prop) {
				    	var rname = prop.name;
				    	if(prop.type === "function")
				    		rname = prop.name.substring(prop.name.indexOf(" ")+1);
				    	if(!isCondition)
				    		rname = "{{" + rname + "}}";
				        return rname;
				    }
				}
			] /*, {onKeydown : function(e, commands) {
				if(e.keyCode == 13) {
					return commands.KEY_ENTER;
				}
				return false;
			 } } */ );
		
		$(".textcomplete-dropdown").css("z-index", Number($("#layer_area_wrapper").css("z-index")) + 1);
	}
}

function resizeAllTextarea() {
	$("#slideLayer .form-group textarea, #layer_area .form-group textarea").each(function() {
		var heightValue = $(this).height();
		if(this.value != "" && heightValue < 135) {
			var scrollHeight = $(this).prop("scrollHeight");
			var _h = scrollHeight - 12;
			$(this).height(0).height(_h);
		}
	});
}

function resize(obj, event) {
	var _h = obj.scrollHeight - 12;
    $(obj).height(0).height(_h);
	$(obj).css("overflow-y", _h < 135 ? "hidden" : "auto");
}