<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/tagLib.jsp" %>

<spring:eval expression="@global['login.url']" var="loginUrl" />
<spring:eval expression="@global['sso.url']" var="ssoUrl" />
<input type="hidden" id="his_loginUrl" value='${loginUrl }'>
<input type="hidden" id="his_ssoUrl" value='${ssoUrl }'>
	
<script src="js/libs/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function() {
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
					
					if ( _id != "" && _password != "" && _role != "" ) {
						var form = $('<form></form>');
					    form.attr('action', "ssoCheck.do");
					    form.attr('method', 'get');	
					    form.appendTo('body');
					    
					    var user_id = $("<input type='hidden' value='" + _id + "' name='user_id'>");
					    var user_nm = $("<input type='hidden' value='" + _id + "' name='user_nm'>");
					    var user_pw = $("<input type='hidden' value='" + _password + "' name='user_pw'>");
					    var auth_gb = $("<input type='hidden' value='" + _role + "' name='auth_gb'>");
					    
					    form.append(user_id);
					    form.append(user_nm);
					    form.append(user_pw);
					    form.append(auth_gb);
					    form.submit();
					} else {
						location.href = $('#his_loginUrl').val();
					}
				}
			},
			error: function(xhr, textStatus, error) {
				location.href = $('#his_loginUrl').val();
			}
		
		});
	}
	
	// sso Check
	ssoCheck();
});

</script>

<%-- <jsp:forward page="kepri/search.do?category=BOILER"/> --%>
<body>

</body>