<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="./common/tagLib.jsp" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>지식 DB 탐색 <c:if test="${not empty  pageName}"> > ${pageName }</c:if></title>
<link rel="shortcut icon" href="<c:url value="/images/favicon/favicon.ico" />" type="image/x-icon">
<link rel="icon" href="<c:url value="/images/favicon/favicon.ico" />" type="image/x-icon">


<link rel="stylesheet" href="<c:url value="/assets/lib/NotoSansKr/NotoSansKr.css" />">
<link rel="stylesheet" href="<c:url value="/assets/lib/NanumGothic/webfonts_NanumGothic.css" />">
<link rel="stylesheet" href="<c:url value="/assets/lib/NanumScript/webfonts_NanumScript.css" />">

<link rel="stylesheet" href="<c:url value="/assets/lib/XEIcon/xeicon.min.css" />">
<link rel="stylesheet" href="<c:url value="/assets/lib/bootstrap/hubpop/bootstrap_hubpop.css" />">

<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font-awesome.min.css" />">

<!-- smart admin CSS -->
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-production-plugins.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-production.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-skins.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-rtl.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin.css" />">

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/skinmin.css" />">
<!-- INIT JS -->
<script src="<c:url value="/js/jquery.min.js" />"></script>

<!-- bxslider 사용시 /js/libs/jquery.min.js과 버전 문제가 있음 1.11-->
<%-- <script src="<c:url value="/js/libs/jquery.min.js" />"></script> --%>
<script>
	if (!window.jQuery) {document.write('<script src="<c:url value="/js/libs/jquery-3.2.1.min.js" />"><\/script>');}
</script>
<script src="<c:url value="/js/libs/jquery-ui.min.js" />"></script>
<script>
	if (!window.jQuery.ui) {document.write('<script src="<c:url value="/js/libs/jquery-ui.min.js" />"><\/script>');}
</script>
<script src="<c:url value="/js/bootstrap/bootstrap.min.js" />"></script>

<script src="<c:url value="/js/basic.js" />"></script>

<!-- konan ksf -->
<%-- <link rel="stylesheet" type="text/css" href="<c:url value="/css/konan/ksf/konan.sf.css" />"> --%>
<%-- <script src="<c:url value="/js/konan/ksf/jquery.js" />"></script> --%>
<%-- <script src="<c:url value="/js/konan/ksf/jquery-ui-1.9.2.custom.js" />"></script> --%>
<%-- <script src="<c:url value="/js/konan/ksf/jquery.konan.sf.js" />"></script> --%>
<%-- <script src="<c:url value="/js/konan/ksf/i18n/jquery.konan.sf-ko.js" />"></script> --%>

<!-- konan lib -->
<script src="<c:url value="/js/konan/lib/KDaViF.js" />"></script>	<!-- Davif Lib -->
<script src="<c:url value="/js/konan/lib/KDaViF.map.js" />"></script>	<!-- Davif Lib -->

<!-- konan custom -->
<script src="<c:url value="/js/konan/common/var.js" />"></script>	<!-- 공통 변수 -->
<script src="<c:url value="/js/konan/common/message.js" />"></script>	<!-- 알람창 -->
<script src="<c:url value="/js/konan/common/commonModal.js" />"></script>	<!-- 알람창 -->
<script src="<c:url value="/js/konan/common/drawChart.js" />"></script>	<!-- davif, flot chart 그리기용 모듈 -->
<script src="<c:url value="/js/konan/common/common.js" />"></script>
<script src="<c:url value="/js/konan/common/upload.js" />"></script>
<script src="<c:url value="/js/konan/login.js" />"></script>

<!-- Cryptography : 관리기 로그인용 -->
<script type="text/javascript" src=<c:url value="/js/konan/crypto/aes.js"/>></script>
<script type="text/javascript" src=<c:url value="/js/konan/crypto/pbkdf2.js" />></script>
<script type="text/javascript" src=<c:url value="/js/konan/crypto/crypto.js" />></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/css/konan/konan.css" />">

<!-- konan chatbot -->
<script src="<c:url value="/js/konan/chatbot/lib/jquery/jquery.blockUI.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/jquery/jquery.popupoverlay.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/jsrender/jsrender.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/common.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/app.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/layer.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/flot/jquery.flot.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/flot/jquery.flot.pie.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/flot/jquery.flot.resize.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/flot/jquery.flot.tooltip.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/flot/jquery.flot.axislabels.js" />"></script>
<script src="<c:url value="/js/konan/chatbot/lib/flot/jquery.flot.categories.js" />"></script>



<!-- slide -->
<script src="<c:url value="/js/konan/lib/slide/slick.min.js" />"></script>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/konan/slide/slick.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/konan/slide/slick-theme.css" />">


<link rel="stylesheet" type="text/css" href="<c:url value="/css/user.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/jquery.bxslider.css" />">

<style type = "text/css"> <!-- 로딩바스타일 -->
body
{
 text-align: center;
 margin: 0 auto;
}
.loadingBar
{
 position: absolute;
 left: 50%;
 top: 50%;
 background: #ffffff;
 z-index: 10000;
}
</style>
<script type="text/javascript">
$(function() {
	$('#loading').hide();
	
	// 페이지 오픈시마다 세션체크를 한다.
	ssoCheck();
})
</script>
</head>

<body class="">
	<!-- 
	<div id="loading" class="loadingBar">
		<img src="<c:url value="/images/ajax-loader.gif" />" alt="loading">
	</div>
	 -->
	 
	<!-- loading -->
	<div id="loading" class="loading-wrap">
		<div class="loading-box">
			<div class="img">
				<img src="/knowledge/assets/img/common/loading.png" alt="">
			</div>
			<ul class="dot-pulse">
				<li>
					<div class="dot"></div>
				</li>
				<li>
					<div class="dot"></div>
				</li>
				<li>
					<div class="dot"></div>
				</li>
				<li>
					<div class="dot"></div>
				</li>
				<li>
					<div class="dot"></div>
				</li>
			</ul>

		</div>
	</div>
	<!-- //loading -->
	<!-- 검색용 History -->
	<jsp:include page="./common/historyForm.jsp" />
	
	<!-- 정보용 History(URL 등등)  -->
	<jsp:include page="./common/historyInfoForm.jsp" />

	<!-- 헤더, 왼쪽바, 콘텐츠, 하단 영역은 Tiles 기법을 사용하여 관리하고 있다 ( WEB-INF/tiles/tiles.xml 참고) -->
	<!-- 헤더 -->
<%-- 	<tiles:insertAttribute name="header" /> <!--  /WEB-INF/view/common/layout/header.jsp --> --%>
	
	<!-- 왼쪽바 -->
	<tiles:insertAttribute name="left" /> <!-- /WEB-INF/view/common/layout/left.jsp -->
	
	<!-- MAIN START -->
	<!-- 콘텐츠 영역 -->
	<div id="main" role="main">
		<tiles:insertAttribute name="body" /> <!-- /WEB-INF/view/main/body/* -->
		
		<div id="messageModal"></div>	<!-- 메세지 모달창 -->
		<div id="commonModal"></div>	<!-- 공통 모달창 -->
		<div id="imgListModal"></div>	<!-- 이미지 리스트 모달창 -->
		<div id="davifTip" class="davifTip"></div> <!-- Kdavif Tooltip area -->
	</div>
	
	<!-- Upload -->
	<jsp:include page="./common/modal/upload.jsp" />
	<!-- Chatbot -->
	<div class="modal" id="trychat" ></div>
	<div class="toggler" style="display:none;">
		<div id="effect" viewyn="off">
		    <div id="eff_cont">
		    	<div class="vrlist">
					<article>
						<div class="jarviswidget">
							<header class="ui-sortable-handle">
								<span class="widget-icon"> <i class="fa fa-th-list"></i> </span>
								<h2>VR 골라보기</h2>
								<span class="part-out" onclick="toggler_close();" title="창 닫기"></span>
							</header>
							<div class="widget-body scroll">
								<table class="display table table-bordered" width="100%">
									<colgroup>
										<col style="width:15px">
										<col style="width:10%">
										<col style="width:15%">
										<col>
									</colgroup>
									<thead>			                
										<tr>
											<th><b>No</b></th>
											<th><b>중분류</b></th>
											<th><b>소분류</b></th>
											<th><b>파트명</b></th>
										</tr>
									</thead>
									<tbody id="vrpartlist">
									</tbody>
								</table>
							</div>
						</div>
					</article>
				</div>
				<img src="/knowledge/img/boiler_equip.png" id="cont_img">
			</div>
		</div>
	</div>
	
	<!-- 하단 -->
<%-- 	<tiles:insertAttribute name="footer" /> <!-- /WEB-INF/views/common/layout/footer.jsp --> --%>
	
	<!-- Loading Bar JS -->
	<!-- Loading Progress -->
<%-- 	<script data-pace-options='{ "restartOnRequestAfter": true }' src="<c:url value="/js/plugin/pace/pace.min.js" />"></script> --%>

	<!-- App Config JS -->
	<script src="<c:url value="/js/app.config.js" />"></script>

	<!-- Custom JS -->
	<script src="<c:url value="/js/notification/SmartNotification.min.js" />"></script>

	<!-- Jarvis JS -->
	<script src="<c:url value="/js/smartwidgets/jarvis.widget.min.js" />"></script>

	<!-- Easy Pie Charts JS -->
	<script src="<c:url value="/js/plugin/easy-pie-chart/jquery.easy-pie-chart.min.js" />"></script>

	<!-- SPARKLINES -->
	<script src="<c:url value="/js/plugin/sparkline/jquery.sparkline.min.js" />"></script>

	<!-- JQUERY VALIDATE -->
	<script src="<c:url value="/js/plugin/jquery-validate/jquery.validate.min.js" />"></script>

	<!-- JQUERY MASKED INPUT -->
	<script src="<c:url value="/js/plugin/masked-input/jquery.maskedinput.min.js" />"></script>

	<!-- JQUERY SELECT2 INPUT -->
	<script src="<c:url value="/js/plugin/select2/select2.min.js" />"></script>

	<!-- JQUERY UI + Bootstrap Slider -->
	<script src="<c:url value="/js/plugin/bootstrap-slider/bootstrap-slider.min.js" />"></script>

	<!-- browser msie issue fix -->
	<script src="<c:url value="/js/plugin/msie-fix/jquery.mb.browser.min.js" />"></script>

	<!-- FastClick: For mobile devices -->
	<script src="<c:url value="/js/plugin/fastclick/fastclick.min.js" />"></script>


	<!-- MAIN APP JS FILE -->
	<script src="<c:url value="/js/app.min.js" />"></script>

	<!-- Voice command : plugin -->
	<script src="<c:url value="/js/speech/voicecommand.min.js" />"></script>

	<!-- Smart Chat UI -->
	<script src="<c:url value="/js/smart-chat-ui/smart.chat.ui.min.js" />"></script>
	<script src="<c:url value="/js/smart-chat-ui/smart.chat.manager.min.js" />"></script>

	<!-- demo JS -->
	<script src="<c:url value="/js/demo.min.js" />"></script>
	
	<!-- user JS -->
	<script src="<c:url value="/js/user.js" />"></script>
	
	<!-- bxSlider -->
	<script src="<c:url value="/js/jquery.bxslider.js" />"></script>
</body>
</html>