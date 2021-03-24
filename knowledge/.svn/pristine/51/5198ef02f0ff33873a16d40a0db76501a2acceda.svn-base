(function($) {
	
	$.fn.serializeObject = function() {
		"use strict";
		var result = {};
		var extend = function(i, element) {
			var node = result[element.name];
			if ('undefined' !== typeof node && node !== null) {
				if ($.isArray(node)) {
					node.push(element.value);
				} else {
					result[element.name] = [ node, element.value ];
				}
			} else {
				result[element.name] = element.name.endsWith('[]') 
						? [ element.value ] : element.value;
			}
		};

		$.each(this.serializeArray(), extend);
		return result;
	};

	$.extend({
	    getParams: function() {
	        var vars = [], hash;
	        var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	        for (var i = 0; i < hashes.length; i++) {
	            hash = hashes[i].split('=');
	            vars.push(hash[0]);
	            vars[hash[0]] = hash[1];
	        }
	        return vars;
	    },
	    getParam: function(name) {
	        return $.getParams()[name];
	    },
	    getJsonSync: function(url) {
	    	var jqXHR = $.ajax({
	    		'type': 'GET',
	    		'url': url,
	    		dataType: 'json',
	    		cache: false,
	    		async: false
	    		
	    	});
			return {
				valid : jqXHR.status === 200,
				data  : jqXHR.responseJSON
			};
	    }, 
	    postJson: function(url, data) {
			return $.ajax({
				'type' : 'POST',
				'url' : url,
				'headers' : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				'data' : JSON.stringify(data),
			});
	    }
	});
	
	$.templateRenderer = function(filename, renderData, contextpath) {
		if(!contextpath) contextpath = CONTEXTPATH;
		$.extend(renderData, {
			"contextpath" : contextpath
		});	
		
		var content = $.ajax({
	    	url: CONTEXTPATH + "js/tmpl/"+filename+".tmpl",
	        dataType:"text",
	        async : false,
	        type:"get",
	        processData:false
	    });
		
		var template = $.templates(content.responseText);
		return template.render(renderData);
	};
	
	$.getTemplateStr = function(filename, contextpath) {
		
		if(!contextpath) contextpath = CONTEXTPATH || "./";
		var content = $.ajax({
//	    	url: contextpath + "../js/konan/chatbot/tmpl/"+filename+".tmpl",
			url: context + "/js/konan/chatbot/tmpl/"+filename+".tmpl",
	        dataType:"text",
	        async : false,
	        type:"get",
	        processData:false
	    });
		return $.templates(content.responseText);
	};
	//2020-03-06
	$.registTemplateStr = function(registName, filename, contextpath) {
		if(!contextpath) contextpath = CONTEXTPATH || "./";
		var content = $.ajax({
	    	url: contextpath + "js/tmpl/"+filename+".tmpl",
	        dataType:"text",
	        async : false,
	        type:"get",
	        processData:false
	    });
		return $.templates(registName, content.responseText);
	};
	$.dialogResponse = function(contents, tmpl) {
		if(!tmpl)
			tmpl = $.getTemplateStr("dialog-response");
	
		var data = {
			contents : contents, 
			contextpath : CONTEXTPATH,
			functionName : "chat.sendRichMessage",
			botBubbleId : $("li.bot").length + 1 
		};
		var $dialogResponse = $(tmpl.render(data));
		return $dialogResponse;
	};
	
}(jQuery));