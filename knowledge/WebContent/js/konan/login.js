$(document).ready(function() {
	// 로그인 정보 하단 스크롤 따라다니기 기능
	$(".header-bottom").stop(false,false).animate({"top": $(window).scrollTop()+"px"}, 600);
    $(window).scroll(function() {
    	$(".header-bottom").stop(false,false).animate({"top": $(window).scrollTop()+"px"}, 600);
    });
});

$(function(){
	$(document).on("click", "#left-panel .header-bottom .user-btn", function(e){
	   e.preventDefault();
	   $(this).parent().parent().toggleClass('active');
	});

	$('html').click(function(e) { if(!$(e.target).hasClass("header-bottom")) { 
			$('#left-panel .header-bottom').removeClass('active');
		} 
	});
});

function ssoCheck() {
	$.ajax({
		type: "GET",
		url : $('#his_ssoUrl').val(),
		
		dataType: "json",
		success: function(data) {
			//console.log(data);
			if ( data != null && data != "" && data != undefined ) {
				var _id = data.userId;
				var _password = data.password;
				var _role = data.role.replace(/'/g, "");
				
				if ( _id == "" || _password == "" || _role == "" ) {
					message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
						logout();
					});
				}
			}
		},
		error: function(xhr, textStatus, error) {
			message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = $('#his_loginUrl').val();
			});
		}
	
	});
}

function login() {
	
	
	var _id = $("#id").val();
	var _passwd = $("#passwd").val();
	
	if ( !validInputText("login", _id) || !validInputText("login", _passwd) ) {
		message.show(message.warning, "아이디 또는 비밀번호가 입력이 되지 않았습니다.", ALERT);
		return;
	}
	
	// Cookie 체크 및 저장
	idSaveChek();
	$('#login-form')[0].submit();
}

function logout() {
	
	//message.show(message.noti, "로그아웃을 진행하시겠습니까?", CONFIRM, function() {
		$.ajax({
			type: "POST",
			url : "../logout.do",
			dataType: "json",
			success: function(data) {
				if ( data.result == "success" ) {
					location.href = $('#his_loginUrl').val();
				}
			},
			error: function(xhr, textStatus, error) {
				if ( xhr.status == 901 ) {
					message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
						location.href = $('#his_loginUrl').val();
					});
				}
			}
		
		});
	//});
	
	/*
	$.ajax({
		type: "GET",
		url : "../logout.do",
		dataType: "json",
		success: function(data) {
			if ( data.result == "success" ) {
				message.show(message.noti, "로그아웃이 정상적으로 되었습니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				})
			}
		},
		error: function(xhr, textStatus, error) {
			if ( xhr.status == 901 ) {
				message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			}
		}
	
	});
	*/
};

//체크여부와 id 쿠키에 심어둠
function idSaveChek(){
	//아이디 저장 체크여부 쿠키에 심어둠
	var chechStatus = $("input:checkbox[id='rememberId']").is(":checked")
	if(chechStatus == true){
		deleteCookies("id_save_yn");
		deleteCookies("save_user_id");
		setCookies("id_save_yn","Y","30")
		setCookies("save_user_id",$("#id").val(),"30")
		
	}else{
		deleteCookies("id_save_yn");
		deleteCookies("save_user_id");
	}
};

//쿠키생성
function setCookies(cName, cValue, cDay){
    var expire = new Date();
    expire.setDate(expire.getDate() + cDay);
    cookies = cName + '=' + escape(cValue) + '; path=/ '; // 한글 깨짐을 막기위해 escape(cValue)를 합니다.
    if(typeof cDay != 'undefined') cookies += ';expires=' + expire.toGMTString() + ';';
    document.cookie = cookies;
}
// 쿠키 가져오기
function getCookies(cName) {
    cName = cName + '=';
    var cookieData = document.cookie;
    var start = cookieData.indexOf(cName);
    var cValue = '';
    if(start != -1){
         start += cName.length;
         var end = cookieData.indexOf(';', start);
         if(end == -1)end = cookieData.length;
         cValue = cookieData.substring(start, end);
    }
    return unescape(cValue);
}
//쿠키삭제 
function deleteCookies( cookieName ) {
  var expireDate = new Date();
  
  //어제 날짜를 쿠키 소멸 날짜로 설정한다.
  expireDate.setDate( expireDate.getDate() - 1 );
  document.cookie = cookieName + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}