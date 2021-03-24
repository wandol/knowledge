// 테이블형 모달
var commonModal = {
	show : function(title, body, option, type, callback) {
		
		this.dialog(title, option, type, callback);
		
		this.makeBody(body);
		
		// Message Modal show.
		$('#commonModal').dialog('open');
	},
	makeBody : function(body) {
		var _body = "<form id='commonForm'>";
			_body += "<fieldset>";
			_body += "<div class=\"table-responsive no-margin\">";
			_body += "<table class=\"tblT1\">";
			
			_body += body;
			
			_body += "</table>";
			_body += "</div>";
			_body += "</fieldset>";
			_body += "</form>";
			
			$('#commonModal').html(_body);
	},
	close : function() {
		// Message Modal close.
		$('#commonModal').dialog('close');
		$('#commonModal').html('');
	},
	dialog : function(title, option, type, callback) {
		
		if ( option.width == null || option.width == undefined || option.width == "" ) option.width = 600;
		if ( option.height == null || option.height == undefined || option.height == "" ) option.height = 'auto';
		
		// 알람
		if (type == "alert") {
			$('#commonModal').dialog({
				autoOpen : false,
				width : option.width,
				height : option.height,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>",
				buttons : [{
					html : "<i class='fa fa-times'></i>&nbsp; 닫기",
					"class" : "btn btn-default",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		} else if ( type == "confirm" ) {	// 확인 창
			$('#commonModal').dialog({
				autoOpen : false,
				width : option.width,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>"
				,buttons : [{
					html : "<i class='fa fa-check'></i>&nbsp; 확인",
					"class" : "btn btn-default",
					click : function() {
						if ($.isFunction(callback)) {
							callback.apply();
		                }
						$(this).dialog("close");
					}
				}, {
					html : "<i class='fa fa-times'></i>&nbsp; 닫기",
					"class" : "btn btn-default",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		} else {
			$('#commonModal').dialog({
				autoOpen : false,
				width : option.width,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>"
				,buttons : [{
					html : "<i class='fa fa-times'></i>&nbsp; 닫기",
					"class" : "btn btn-default",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		} 
	}
};

//테이블형 모달
var imgListModal = {
	show : function(title, body, option, type, callback) {
		
		this.dialog(title, option, type, callback);
		
		this.makeBody(body);
		
		// Message Modal show.
		$('#imgListModal').dialog('open');
	},
	makeBody : function(body) {
		var _body = "<form id='commonForm'>";
			_body += "<fieldset>";
			_body += "<div class=\"table-responsive no-margin\">";
			_body += "<table class=\"tblT1\">";
			
			_body += body;
			
			_body += "</table>";
			_body += "</div>";
			_body += "</fieldset>";
			_body += "</form>";
			
			$('#imgListModal').html(_body);
	},
	close : function() {
		// Message Modal close.
		$('#imgListModal').dialog('close');
		$('#imgListModal').html('');
	},
	dialog : function(title, option, type, callback) {
		
		if ( option.width == null || option.width == undefined || option.width == "" ) option.width = 600;
		if ( option.height == null || option.height == undefined || option.height == "" ) option.height = 'auto';
		
		// 알람
		if (type == "alert") {
			$('#imgListModal').dialog({
				autoOpen : false,
				width : option.width,
				height : option.height,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>",
				buttons : [{
					html : "<i class='fa fa-times'></i>&nbsp; 닫기",
					"class" : "btn btn-default",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		} else if ( type == "confirm" ) {	// 확인 창
			$('#imgListModal').dialog({
				autoOpen : false,
				width : option.width,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>"
				,buttons : [{
					html : "<i class='fa fa-check'></i>&nbsp; 확인",
					"class" : "btn btn-default",
					click : function() {
						if ($.isFunction(callback)) {
							callback.apply();
		                }
						$(this).dialog("close");
					}
				}, {
					html : "<i class='fa fa-times'></i>&nbsp; 닫기",
					"class" : "btn btn-default",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		} else {
			$('#imgListModal').dialog({
				autoOpen : false,
				width : option.width,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>"
				,buttons : [{
					html : "<i class='fa fa-times'></i>&nbsp; 닫기",
					"class" : "btn btn-default",
					click : function() {
						$(this).dialog("close");
					}
				}]
			});
		} 
	}
};