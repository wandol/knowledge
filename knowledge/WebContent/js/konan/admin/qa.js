/**
 * 	질문셋 추가 팝업.
 */
 
$(document).ready(function(){
	// 등록화면 Modal창 초기화
	$('#qAManage').dialog({
		autoOpen : false,
		width : "1700",
		resizable : false,
		modal : true,
		buttons : [{
		}, {
		}]
	});
	
}); 

function qaFileCheck(){
	var _data = new FormData();
	_data.append("file", $('#qaFile').prop('files')[0]);
    
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: context + "/admin/qaFileCheck",
        data: _data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
        	$('#btnUpload').prop('disabled', false);
        	alert('success')
        },
        error: function (e) {
            alert('fail');
        }
    });
}
function addQaPop() {
	
	$("#qAManage").dialog({
		autoOpen : false,
		width : 1700,
		resizable : false,
		modal : true,
		title : "<div class='widget-header'><h4>질문답변 생성</h4></div>"
	});
	$('#qAManage').dialog('open');	
}