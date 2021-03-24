var flow = {};

flow.instance = null;

flow.instanceData = {
	Container : "flow",
	DragOptions : {
		cursor : 'pointer',
		zIndex : 2000
	},
	Endpoint : "Blank",
	ConnectionOverlays : [ 
	["PlainArrow", {
			location : 1,
			direction : 1.1,
			visible : true,
			width : 19,
			length : 11,
			id : "ARROW",
			
		}]
	],
};

flow.normalLine = {
	connector : [ "Flowchart", {
		cornerRadius : 50,
		midpoint : 0.3,
		//alwaysRespectStubs : true,
		//gap : 5
	} ],
	label : null,
	paintStyle : {
		strokeWidth : 8,
		stroke : "#5fc8d7",
	},
	hoverPaintStyle : {
		stroke : "#2682bd",
	},
	anchor : "AutoDefault",
	detachable : false,
	endpointsOnTop : true,
	deleteEndpointsOnDetach : true
};

flow.init = function(connections) {
	flow.instance = window.instance = jsPlumb.getInstance(flow.instanceData);

	flow.dragndrop(jsPlumb.getSelector(".action"));
	
	$.each(connections, function(idx, connection) {
		flow.connect(connection.source, connection.target);
	});

	jsPlumb.fire("jsPlumbDemoLoaded", flow.instance);
};

flow.dragndrop = function(obj) {
	flow.instance.draggable(obj, {
		containment : "#flow",
		grid : [ 40, 40 ],
		drag : function(e, ui) {
			$(".action").css("z-index", 2);
			$(e.el).css("z-index", 4);
			$(".jtk-connector").css("z-index", 1);
		},
		stop : function(e, ui) {
			var pr = document.getElementById(flow.instanceData.Container).getBoundingClientRect();
			var cr = e.el.getBoundingClientRect();
			if (cr.right >= pr.right || cr.bottom >= pr.bottom) {
				var $flow = $('#' + flow.instanceData.Container);
				if (cr.right >= pr.right) {
					var _w = $flow.width() + $(window).width()/2;
					$("#flow, .container").width( _w );
				}
				if (cr.bottom >= pr.bottom) {
					var _h = $flow.innerHeight() + $(window).height()/2;
					$("#flow, .scenariodetail").height( _h );
					$(".navbar-left").height( _h + 70 );
				}
				
				flow.instance.repaintEverything();
			}
		}
	});
};

flow.connect = function(sourceId, targetId) {
		if(sourceId == targetId) {
			if($("#"+sourceId).find(".self_line").length == 0)
				$("#"+sourceId).append("<span class='self_line'></span>");
		}
		else {
			flow.instance.connect($.extend({
				"scope" :  sourceId,
				"source" : sourceId,
				"target" : targetId
			}, flow.normalLine));
		}
};