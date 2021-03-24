function removeOverlap(arr) {
	var newarr = [];
	var newIdx = 0;
	for(var i in arr) {
		if(newarr.indexOf(arr[i]) == -1)
			newarr[newIdx++] = arr[i];
	}
	return newarr;
}

function goPage(page) {
	location.href = CONTEXTPATH + "admin/" + page;
}

function changeDomain(domain, page) {
	$.ajax({
		url : CONTEXTPATH + "/admin/session.bot?action=change",
		type : "POST",
		headers : {
			'Accept' : 'application/json; charset=utf8',
			'Content-Type' : 'application/json'
		},
		data : JSON.stringify({
			"domain" : domain, 		
			"page" : page || ((existHistoryTable)? "dashboard" : "intent")
		}),
		dataType : "json"
	})
	.done(function(ret){
		goPage(ret.data);
	})
	.fail(function(xhr){
		layer.error(xhr.responseText);
	});
}

var datepicker = {};
datepicker.init = function(id) {
	$("#" + id).attr("readonly", true);
	$("#" + id).datepicker({
		showAnim:"slideDown", 
		dateFormat:"yy-mm-dd", 
		maxDate : "0", 
		minDate :"-6m"
	});
	
	$("#" + id + "Btn").click(function(e) {
		if($("#ui-datepicker-div").css("display") == "block")
			$("#" + id).datepicker("hide");	
		else
			$("#" + id).datepicker("show");	
	});
}
