var layer = {
	show : function($layer) {
		$layer.parent().css({
			opacity : 1,
			visibility : "visible"
		});
		$layer.popup("show");
	},
	hide : function($layer) {
		$layer.popup("hide");
	},
	confirm : function(msg, callback, cancelcallback) {
		layer.showNotice("confirm", msg, callback, cancelcallback);
	},
	info : function(msg, callback) {
		layer.showNotice("info", msg, callback);
	},
	warning : function(msg, callback) {
		layer.showNotice("warning", msg, callback);
	},
	error : function(msg, callback) {
		layer.showNotice("error", msg, callback);
	},
	showNotice : function(type, msg, callback, cancelcallback) {
		$layer = $("#layerNotice");
		$layer.find(".modal-title").addClass("hidden");
		$layer.find(".layer_icon").removeClass("fa-question-circle fa-exclamation-triangle fa-times-circle fa-exclamation-circle");
		$layer.find(".layer_icon").removeClass("confirm info warning error").addClass(type);
		$layer.find(".layer_message").html(msg);
		if (type == "confirm") {
			$layer.find(".layer_icon").addClass("fa-question-circle");
			$layer.find(".modal-title.confirm_title").removeClass("hidden");
			$layer.find(".layer_cancel").removeClass("hidden");
		} else {
			if (type == "warning") {
				$layer.find(".layer_icon").addClass("fa-exclamation-triangle");
				$layer.find(".modal-title.warning_title").removeClass("hidden");
			} else if (type == "error") {
				$layer.find(".layer_icon").addClass("fa-times-circle");
				$layer.find(".modal-title.error_title").removeClass("hidden");
			} else if (type == "info") {
				$layer.find(".layer_icon").addClass("fa-exclamation-circle");
				$layer.find(".modal-title.info_title").removeClass("hidden");
			}
			$layer.find(".layer_cancel").addClass("hidden");
		}
		$layer.find(".layer_ok").unbind().click(function() {
			layer.hideNotice();
			if(callback)
				setTimeout(callback, 100);
		});
		$layer.find(".layer_cancel, .layer_close").unbind().click(function() {
			layer.hideNotice();
			if(cancelcallback)
				setTimeout(cancelcallback, 100);
		});
		layer.show($layer);
		setTimeout(function() {
			if($layer.find(".layer_cancel").hasClass("hidden"))
				$layer.find(".layer_ok").focus();	
			else
				$layer.find(".layer_cancel").focus();
			
		}, 100);
	},
	hideNotice : function() {
		var $layer = $("#layerNotice");
		layer.hide($layer);
	}
};

var blocking = {
	on : function(msg, iconname) {
		var css = {};
		if(!msg) {
			msg = "<img src='"+CONTEXTPATH+"img/loading/loadingspin.gif' />"
			css = {left : "50%", width : '60px', border: 'none'};
		}
		else {
			if(!iconname) iconname = "check";
			msg = "<div id='blockmsg'><img src='"+CONTEXTPATH+"img/icon/"+iconname+".png' /> "+msg+"</div>";
			css = {
				left : "40%",
				//width : "350px",
				padding : "10px",
				border : "none",
				color : "#fff",
				backgroundColor : "#000",
				'-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px'
			};
		}
		
		$.blockUI({
			centerX: true,
			centerY: true,
			message: msg,
			css : css,
			overlayCSS: {zIndex : 10000, opacity: 0.1, cursor: 'wait'}
		});
	},
	
	off : function(timeout) {
		if(!timeout) timeout = 500;
		$.unblockUI({timeout: timeout});
	}
};

var chatLayer = {};
chatLayer.scenario = "";
chatLayer.show = function(scenario) {
	if(!scenario) scenario = "";
	chatLayer.scenario = scenario;
	$('#trychat').popup('show');
};
chatLayer.hide = function() {
	$('#trychat').popup('hide');
};

$(function() {
	$(".layer").popup({
		autozindex : true,
		blur : false,
		scrolllock : true,
		escape : false
	});
	
	$("#slideLayer").popup({	
		//horizontal : 'right',
		//transition: 'all 0.5s',
		autozindex : true,
		blur : false,
		scrolllock : true,
		escape : false,
		beforeopen: function() {
			//space = top margin + bottom margin + header + footer + border*2 
			var height = $(window).height() - (50 + 50 + 33 + 51 + 1 + 1);
			
			//$(this).find(".modal-body").css("min-height", height+"px");
			$(this).find(".tab-content").css("min-height", height+"px");
		},
		onopen: function() {
			$(this).find(".modal-dialog").addClass("active");
		},
		onclose: function() {
			$(this).find(".modal-dialog").removeClass("active"); 
		}
	});
	
	$('#trychat').popup({
	    horizontal : 'right',
	    autozindex : true,
		blur : false,
	    scrolllock : true,
	    beforeopen: function() {
	    	$.ajax({
	    		url : context + "/comm/chatbot.do?scenario=" + chatLayer.scenario,
	    		dataType : "html",
	    		type : "GET",
	    		contentType : "text/html; charset=utf-8",
	    		async: false,
	    		cache: false
	    	})
	    	.done(function(ret){
	    		$("#trychat").html(ret);
	    	})
	    	.fail(function(xhr){
	    		layer.error(xhr.responseText);
	    	});	
	    }, 
	    onopen: function() {
	    	$(".chat-container").css("margin-right", "0");
	    	$('#chat-textarea').focus();
	    },
	    onclose: function() {
	    	toggler_close();
	    	$(".chat-container").css("margin-right", "-531px");
	    },
	    
	});
});