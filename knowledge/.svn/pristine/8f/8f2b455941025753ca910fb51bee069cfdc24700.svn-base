/*******************************************************************************
 * 파 일 명 : user.js
 * 내   용 : Template3을 구동하기 위한 js 
 ******************************************************************************/


$(function() {
	
	runAllForms();
	
	// ***** register start *****
	
	// register agree(회원가입 동의하기)
	$("#i-agree").click(function(){
		$this=$("#terms");
		if($this.checked) {
			$('#myModal').modal('toggle');
		} else {
			$this.prop('checked', true);
			$('#myModal').modal('toggle');
		}
	});
	
	// register validate(회원가입 정규식)
	$("#smart-form-register").validate({

		rules : {
			username : {
				required : true
			},
			email : {
				required : true,
				email : true
			},
			password : {
				required : true,
				minlength : 3,
				maxlength : 20
			},
			passwordConfirm : {
				required : true,
				minlength : 3,
				maxlength : 20,
				equalTo : '#password'
			},
			gender : {
				required : true
			},
			terms : {
				required : true
			}
		},
		messages : {
			login : {
				required : 'Please enter your login'
			},
			username : {
				required : '아이디를 입력해 주세요.'
			},
			email : {
				required : '이메일 주소를 입력해 주세요.',
				email : '이메일 형식에 맞게 입력해 주세요.'
			},
			password : {
				required : '비밀번호를 입력해주세요.'
			},
			passwordConfirm : {
				required : '비밀번호를 한번 더 입력해 주세요.',
				equalTo : '비밀번호가 일치하지 않습니다.'
			},
			gender : {
				required : '성별을 선택해 주세요.'
			},
			terms : {
				required : '(필수)개인정보 수집 및 이용에 동의해 주세요.'
			}
		},
		submitHandler : function(form) {
			$(form).ajaxSubmit({
				success : function() {
					$("#smart-form-register").addClass('submited');
				}
			});
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element.parent());
		}
	});
	// ***** register end *****
	
});
