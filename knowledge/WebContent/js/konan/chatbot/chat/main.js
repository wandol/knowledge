$.ajaxPrefilter(function( options, originalOptions, jqXHR ) { options.async = true; });
var ajax = {
	call : function(url, callback, params) {
		$.ajax({
			url : CONTEXTPATH + url,
			cache : false,
			type : 'POST',
			contentType : "application/json",
			async : true,
			data : params,
			dataType: "json",
			beforeSend: function() {
		    },
			success : function(data) {
				if(typeof callback == "object") {
					for(var i in callback) {
						var method = callback[i];
						method(data);
					}
				} else {
					callback(data);
				}
			},
			complete : function(data) {
		    },
			error : function(xhr, ajaxOptions, thrownError) {
				//alert(thrownError);
			}
		});
	}
};
