// 알람, 확인 메세지용
var message = {
	info: "정보",
	noti: "알림",
	success: "성공",
	warning: "경고",
	danger: "위험",
	show : function(title, message, type, callback) {
		
		this.dialog(title, type, callback);
		
		// Message Set
		var _message = "<p>" + message + "</p>";
		$('#messageModal').html(_message);
		
		// Message Modal show.
		$('#messageModal').dialog('open');
	},
	close : function() {
		// Message Modal close.
		$('#messageModal').dialog('close');
	},
	dialog : function(title, type, callback) {
		
		var _icon = "";
		if ( title == "알림" ) {
			_icon = "fa-check";
		} else {
			_icon = "fa-warning";
		}
		
		if ( type == "alert" ) {
			$('#messageModal').dialog({
				autoOpen : false,
				width : 600,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4>" + title + "</h4></div>",
				buttons : [{
					html : "<i class='fa fa-check'></i>&nbsp; 확인",
					"class" : "btn btn-default",
					click : function() {
						if ($.isFunction(callback)) {
							callback.apply();
		                }
						$(this).dialog("close");
					}
				}]
			});
		} else if ( type == "confirm" ) {
			$('#messageModal').dialog({
				autoOpen : false,
				width : 600,
				resizable : false,
				modal : true,
				title : "<div class='widget-header'><h4><i class='fa " + _icon + "'></i> " + title + "</h4></div>",
				buttons : [{
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
		}
		
	}
};