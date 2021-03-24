<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./tagLib.jsp" %>
<!-- HEADER START -->
<header id="header">
	<div id="logo-group">
		<!-- logo start -->
<!-- 		<span id="logo"><img src="../images/konan/h1_logo.png" alt="SmartAdmin"></span> -->
		<span id="logo"><a href="/Kepri_Func/main/dashboard.do"><img src="<c:url value="/images/logo.png" />" alt="SmartAdmin"></a></span>
		<!-- logo end -->
		
		<span id="activity" class="activity-dropdown"> <i class="fa fa-user"></i><b class="badge"> 21 </b></span>
		
		<!-- notice start -->
		<div class="ajax-dropdown">
			<div class="btn-group btn-group-justified" data-toggle="buttons">
				<label class="btn btn-default">
					<input type="radio" name="activity" id="ajax/notify/mail.html">	메시지 (14) 
				</label>
				<label class="btn btn-default">
					<input type="radio" name="activity" id="ajax/notify/notifications.html"> 공지사항 (3) 
				</label>
				<label class="btn btn-default">
					<input type="radio" name="activity" id="ajax/notify/tasks.html"> 상태 (4) 
				</label>
			</div>
			<div class="ajax-notifications custom-scroll">
				<div class="alert alert-transparent">
					<h4>탭을 클릭하여 주세요.</h4>
					탭을 클릭하시면 내용 미리보기가 가능합니다.
				</div>
				<i class="fa fa-lock fa-4x fa-border"></i>
			</div>
			<span> 마지막 업데이트: 2018년 7월 1일 AM 9:00
				<button type="button" data-loading-text="<i class='fa fa-refresh fa-spin'></i> Loading..." class="btn btn-xs btn-default pull-right">
					<i class="fa fa-refresh"></i>
				</button>
			</span>
		</div>
		<!-- notice end -->
	</div>
	
	<!-- user info start -->
	<div class="project-context hidden-xs">
		<span class="label">Login User</span>
		<span class="project-selector dropdown-toggle" data-toggle="dropdown">${sessionScope.login_user.user_nm }님 <i class="fa fa-angle-down"></i></span>
		<ul class="dropdown-menu">
			<li>
				<a href="#">개인 정보 수정</a>
			</li>
			<li class="divider"></li>
			<li>
				<a href="javascript:void(0);" onclick="javascript:logout();"><i class="fa fa-power-off"></i> 로그아웃</a>
			</li>
		</ul>
	</div>
	<!-- user info end -->
</header>
<!-- HEADER END -->