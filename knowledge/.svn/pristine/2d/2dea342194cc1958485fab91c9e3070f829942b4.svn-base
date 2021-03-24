<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="./common/tagLib.jsp" %>

<!DOCTYPE html>
<html lang="ko" id="extr-page">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>지식 DB 탐색</title>

<!-- FAVICON -->
<link rel="shortcut icon" href="<c:url value="/images/favicon/favicon.ico" />" type="image/x-icon">
<link rel="icon" href="<c:url value="/images/favicon/favicon.ico" />" type="image/x-icon">

<!-- GOOGLE FONT -->
<link rel="stylesheet" href="<c:url value="/css/old/font-google.css" />">

<!-- CSS -->
<!-- basic CSS -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/font-awesome.min.css" />">

<!-- smart admin CSS -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/smartadmin-production-plugins.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/smartadmin-production.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/smartadmin-skins.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/smartadmin-rtl.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/old/smartadmin.css" />">

<!-- user CSS -->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/old/user.css" />">

<!-- JS -->
<!-- init JS -->
<script src="<c:url value="/js/libs/jquery.min.js" />"></script>
<script> if (!window.jQuery) { document.write('<script src="<c:url value="/js/libs/jquery-3.2.1.min.js" />"><\/script>');} </script>
<script src="<c:url value="/js/libs/jquery-ui.min.js" />"></script>
<script> if (!window.jQuery.ui) { document.write('<script src="<c:url value="/js/libs/jquery-ui.min.js" />"><\/script>');} </script>
<script src="<c:url value="/js/plugin/jquery-validate/jquery.validate.min.js" />"></script>
<script src="<c:url value="/js/plugin/masked-input/jquery.maskedinput.min.js" />"></script>

<!-- bootstrap JS -->		
<script src="<c:url value="/js/bootstrap/bootstrap.min.js" />"></script>

<!-- app JS -->
<script src="<c:url value="/js/app.config.js" />"></script>
<script src="<c:url value="/js/app.min.js" />"></script>

<!-- user JS -->
<script src="<c:url value="/js/user.js" />"></script>

<!-- PACE LOADER -->
<script src="<c:url value="/js/plugin/pace/pace.min.js" />"></script>

<script src="<c:url value="/js/basic.js" />"></script>

<!-- user JS -->
<script src="<c:url value="/js/konan/common/common.js" />"></script>
<script src="<c:url value="/js/konan/login.js" />"></script>
<script type="text/javascript">
$( document ).ready(function(){
	
	var getCookie = getCookies("id_save_yn");
	var getCookieUserId = getCookies("save_user_id");
	//쿠키가 있을때
	if(getCookie!=null && getCookie!=""){
		if(getCookie=="Y"){
			$("input:checkbox[id='rememberId']").attr("checked", true);
			$("#id").val(getCookieUserId);
		}else{
			$("input:checkbox[id='rememberId']").attr("checked", false);
		}
	}
});
</script>

</head>

<body class="animated fadeInDown">

	<!-- HEADER START -->
	<header id="header">
		<div id="logo-group">
			<span id="logo"> <img src="images/logo.png" alt="SmartAdmin"> </span>
		</div>
	</header>
	<!-- HEADER END -->
	
	<!-- MAIN START -->
	<!-- 콘텐츠 영역 -->
	<div id="main" role="main">
		<tiles:insertAttribute name="body" /> <!-- /WEB-INF/view/main/body/* -->
		
		<div id="messageModal"></div>	<!-- 메세지 모달창 -->
	</div>
</body>
</html>