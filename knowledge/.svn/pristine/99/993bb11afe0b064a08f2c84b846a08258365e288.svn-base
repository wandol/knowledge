<!DOCTYPE html>
<html lang="ko">
<head>

<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<title>error500.html</title>

<link rel="shortcut icon" href="<c:url value="/images/favicon/favicon.ico" />" type="image/x-icon">
<link rel="icon" href="<c:url value="/images/favicon/favicon.ico" />" type="image/x-icon">

<link rel="stylesheet" href="<c:url value="/css/font-google.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/font-awesome.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-production-plugins.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-production.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-skins.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin-rtl.min.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/smartadmin.css" />">
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/skinmin.css" />">
<link rel="stylesheet" type="text/css" href="<c:url value="/css/user.css" />">
<!-- INIT JS -->
<script src="<c:url value="/js/libs/jquery.min.js" />"></script>
<script>
	if (!window.jQuery) {document.write('<script src="<c:url value="/js/libs/jquery-3.2.1.min.js" />"><\/script>');}
</script>
<script src="<c:url value="/js/libs/jquery-ui.min.js" />"></script>
<script>
	if (!window.jQuery.ui) {document.write('<script src="<c:url value="/js/libs/jquery-ui.min.js" />"><\/script>');}
</script>
<script src="<c:url value="/js/bootstrap/bootstrap.min.js" />"></script>

<script src="<c:url value="/js/basic.js" />"></script>

</head>
<!-- #BODY -->
<!-- Possible Classes
	* 'smart-style-{#SKIN}'	- skin 테마 설정(#SKIN: 1부터 7까지)
	* 'smart-rtl'        	- Switch theme mode to RTL
	* 'menu-on-top'       	- Switch to top navigation (no DOM change required)
	* 'no-menu'			  	- Hides the menu completely
	* 'hidden-menu'       	- Hides the main menu but still accessable by hovering over left edge
	* 'fixed-header'      	- Fixes the header
	* 'fixed-navigation'  	- Fixes the main menu
	* 'fixed-ribbon'      	- Fixes breadcrumb
	* 'fixed-page-footer' 	- footer 하단 고정
	* 'container'         	- boxed layout mode (non-responsive: will not work with fixed-navigation & fixed-ribbon)
-->
<body class="">

	<!-- HEADER START -->
	<header id="header">
		<div id="logo-group">
			<!-- logo start -->
			<span id="logo"><img src="../images/logo.png" alt="SmartAdmin"></span>
			<!-- logo end -->
			
			<span id="activity" class="activity-dropdown"> <i class="fa fa-user"></i><b class="badge"> 21 </b></span>
			
			<!-- notice start -->
			<div class="ajax-dropdown">
				<div class="btn-group btn-group-justified" data-toggle="buttons">
					<label class="btn btn-default">
						<input type="radio" name="activity" id="../ajax/notify/mail.html">	메시지 (14) 
					</label>
					<label class="btn btn-default">
						<input type="radio" name="activity" id="../ajax/notify/notifications.html"> 공지사항 (3) 
					</label>
					<label class="btn btn-default">
						<input type="radio" name="activity" id="../ajax/notify/tasks.html"> 상태 (4) 
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
			<span class="project-selector dropdown-toggle" data-toggle="dropdown">김기범 연구원님 <i class="fa fa-angle-down"></i></span>
			<ul class="dropdown-menu">
				<li>
					<a href="#">개인 정보 수정</a>
				</li>
				<li class="divider"></li>
				<li>
					<a href="#"><i class="fa fa-power-off"></i> 로그아웃</a>
				</li>
			</ul>
		</div>
		<!-- user info end -->
		
		<!-- header menu start -->
		<div class="pull-right">
			<!-- left menu toggle start -->
			<div id="hide-menu" class="btn-header pull-right">
				<span><a href="#" data-action="toggleMenu" title="Collapse Menu"><i class="fa fa-reorder"></i></a></span>
			</div>
			<!-- left menu toggle end -->
			
			<!-- logout button start -->
			<div id="logout" class="btn-header transparent pull-right">
				<span> <a href="#" title="Logout" data-action="userLogout" data-logout-msg="정말로 로그아웃 하시겠습니까?"><i class="fa fa-sign-out"></i></a></span>
			</div>
			<!-- logout button end -->

			<!-- search start -->
			<div id="search-mobile" class="btn-header transparent pull-right">
				<span><a href="#" title="Search"><i class="fa fa-search"></i></a></span>
			</div>
			<form class="header-search pull-right">
				<input id="search-fld" type="text" name="param" placeholder="검색어 입력">
				<button type="submit"><i class="fa fa-search"></i></button>
				<a href="#" id="cancel-search-js" title="Cancel Search"><i class="fa fa-times"></i></a>
			</form>
			<!-- search end -->

			<!-- full screen start -->
			<div id="fullscreen" class="btn-header transparent pull-right">
				<span><a href="#" data-action="launchFullscreen" title="Full Screen"><i class="fa fa-arrows-alt"></i></a></span>
			</div>
			<!-- full screen end -->

			<!-- voice start -->
			<div id="speech-btn" class="btn-header transparent pull-right hidden-sm hidden-xs">
				<div> 
					<a href="#" title="Voice Command" data-action="voiceCommand"><i class="fa fa-microphone"></i></a> 
					<div class="popover bottom"><div class="arrow"></div>
						<div class="popover-content">
							<h4 class="vc-title">Voice command activated <br><small>Please speak clearly into the mic</small></h4>
							<h4 class="vc-title-error text-center">
								<i class="fa fa-microphone-slash"></i> Voice command failed
								<br><small class="txt-color-red">Must <strong>"Allow"</strong> Microphone</small>
								<br><small class="txt-color-red">Must have <strong>Internet Connection</strong></small>
							</h4>
							<a href="#" class="btn btn-success" onclick="commands.help()">See Commands</a> 
							<a href="#" class="btn bg-color-purple txt-color-white" onclick="$('#speech-btn .popover').fadeOut(50);">Close Popup</a> 
						</div>
					</div>
				</div>
			</div>
			<!-- voice end -->
			
			<!-- flag start -->
			<ul class="header-dropdown-list hidden-xs">
				<li>
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <img src="../images/blank.gif" class="flag flag-kr" alt="United States"> <span> 한국어 </span> <i class="fa fa-angle-down"></i> </a>
					<ul class="dropdown-menu pull-right">
						<li>
							<a href="#"><img src="../images/blank.gif" class="flag flag-us" alt="United States"> English (US)</a>
						</li>
						<li>
							<a href="#"><img src="../images/blank.gif" class="flag flag-jp" alt="Japan"> 日本語</a>
						</li>
						<li>
							<a href="#"><img src="../images/blank.gif" class="flag flag-cn" alt="China"> 中文</a>
						</li>	
						<li class="active">
							<a href="#"><img src="../images/blank.gif" class="flag flag-kr" alt="Korea"> 한국어</a>
						</li>						
					</ul>
				</li>
			</ul>
			<!-- flag end -->
		</div>
		<!-- header menu end -->
	</header>
	<!-- HEADER END -->
	
	<!-- LEFT MENU START -->
	<aside id="left-panel">
		<div class="login-info">
			<span>
				<a href="#" data-action="toggleShortcut">
					<i class="fa fa-lg fa-fw fa-user online"></i>
					<span>MY MENU</span>
					<i class="fa fa-angle-down"></i>
				</a> 
			</span>
		</div>
		<nav>
			<ul>
				<li>
					<a href="#" title="Dashboard"><i class="fa fa-lg fa-fw fa-home"></i> <span class="menu-item-parent">HOME</span></a>
					<!-- six level menu -->
					<ul>
						<li>
							<a href="#">Six Level Menu</a>
							<ul>
								<li>
									<a href="#"><i class="fa fa-fw fa-folder-open"></i> Item #2</a>
									<ul>
										<li>
											<a href="#"><i class="fa fa-fw fa-folder-open"></i> Sub #2.1 </a>
											<ul>
												<li>
													<a href="#"><i class="fa fa-fw fa-file-text"></i> Item #2.1.1</a>
												</li>
												<li>
													<a href="#"><i class="fa fa-fw fa-plus"></i> Expand</a>
													<ul>
														<li>
															<a href="#"><i class="fa fa-fw fa-file-text"></i> File</a>
														</li>
													</ul>
												</li>
											</ul>
										</li>
									</ul>
								</li>
								<li>
									<a href="#"><i class="fa fa-fw fa-folder-open"></i> Item #3</a>
									<ul>
										<li>
											<a href="#"><i class="fa fa-fw fa-folder-open"></i> 3ed Level </a>
											<ul>
												<li>
													<a href="#"><i class="fa fa-fw fa-file-text"></i> File</a>
												</li>
												<li>
													<a href="#"><i class="fa fa-fw fa-file-text"></i> File</a>
												</li>
											</ul>
										</li>
									</ul>	
								</li>
								<li>
									<a href="#" class="inactive"><i class="fa fa-fw fa-folder-open"></i> Item #4 (disabled)</a>
								</li>	
								
							</ul>
						</li>
					</ul>
				</li>
				<li class="top-menu-invisible">
					<a href="#"><i class="fa fa-lg fa-fw fa-cube txt-color-blue"></i> <span class="menu-item-parent">프로젝트</span></a>
				</li>
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-inbox"></i> <span class="menu-item-parent">메일</span> <span class="badge pull-right inbox-badge margin-right-13">14</span></a>
				</li>
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-desktop"></i> <span class="menu-item-parent">모니터링</span></a>
				</li>	
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-bar-chart-o"></i> <span class="menu-item-parent">통계</span></a>
				</li>
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-table"></i> <span class="menu-item-parent">보고서</span></a>
				</li>
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-pencil-square-o"></i> <span class="menu-item-parent">게시판</span></a>
				</li>
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-list-alt"></i> <span class="menu-item-parent">스케쥴</span></a>
				</li>
				<li>
					<a href="#"><i class="fa fa-lg fa-fw fa-cloud"><em>3</em></i> <span class="menu-item-parent">클라우드</span></a>
				</li>	
				<li class="chat-users top-menu-invisible">
					<a href="#"><i class="fa fa-lg fa-fw fa-comment-o"><em class="bg-color-pink flash animated">!</em></i> <span class="menu-item-parent">메신저 <sup>beta</sup></span></a>
				</li>
			</ul>
		</nav>
		
		<!-- left mini menu start -->
		<span class="minifyme" data-action="minifyMenu"> 
			<i class="fa fa-arrow-circle-left hit"></i> 
		</span>
		<!-- left mini menu end -->
	</aside>
	<!-- LEFT MENU END -->

	<!-- CONTENT START -->
	<div id="main" role="main">
	
		<!-- breadcrumb start -->
		<div id="ribbon">
			<span class="ribbon-button-alignment"> 
				<span id="refresh" class="btn btn-ribbon" data-action="resetWidgets" data-title="refresh"  rel="tooltip" data-placement="bottom" data-original-title="<i class='text-warning fa fa-warning'></i> 새로고침시 입력한 데이터가 저장되지 않을 수 있습니다." data-html="true">
					<i class="fa fa-refresh"></i>
				</span> 
			</span>
			<ol class="breadcrumb"> 
				<li>Home</li><li>Error 404</li>
			</ol>
		</div>
		<!-- breadcrumb end -->
		
		<!-- content start -->
		<div id="content">
			<div class="row">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="row">
						<div class="col-sm-12">
							<div class="text-center error-box">
								<h1 class="error-text tada animated"><i class="fa fa-times-circle text-danger error-icon-shadow"></i> Error 500</h1>
								<h2 class="font-xl"><strong>Oooops, Something went wrong!</strong></h2>
								<br />
								<p class="lead semi-bold">
									<strong>You have experienced a technical error. We apologize.</strong><br><br>
									<small>
										We are working hard to correct this issue. Please wait a few moments and try your search again. <br> In the meantime, check out whats new on SmartAdmin:
									</small>
								</p>
								<ul class="error-search text-left font-md">
						            <li><a href="#"><small>Go to My Dashboard <i class="fa fa-arrow-right"></i></small></a></li>
						            <li><a href="#"><small>Contact SmartAdmin IT Staff <i class="fa fa-mail-forward"></i></small></a></li>
						            <li><a href="#"><small>Report error!</small></a></li>
						            <li><a href="#"><small>Go back</small></a></li>
						        </ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- content end -->
	</div>
	<!-- CONTENT END -->
	
	<!-- FOOTER START -->
	<div class="page-footer">
		<div class="row">
		
			<!-- copyright start -->
			<div class="col-xs-12 col-sm-6">
				<span class="txt-color-white">전력 SW 개발 프레임워크 <span class="hidden-xs"></span> © 2018, 한국전력연구원. All rights reserved.</span>
			</div>
			<!-- copyright end -->
			
			<!-- footer button start -->
			<div class="col-xs-6 col-sm-6 text-right hidden-xs">
				<div class="txt-color-white inline-block">
					<i class="txt-color-blueLight hidden-mobile">마지막 활동 <i class="fa fa-clock-o"></i> <strong>52분 전... &nbsp;</strong> </i>
					<div class="btn-group dropup">
						<button class="btn btn-xs dropdown-toggle bg-color-blue txt-color-white" data-toggle="dropdown">
							<i class="fa fa-link"></i> <span class="caret"></span>
						</button>
						<ul class="dropdown-menu pull-right text-left">
							<li>
								<div class="padding-5">
									<p class="txt-color-darken font-sm no-margin">Download Progress</p>
									<div class="progress progress-micro no-margin">
										<div class="progress-bar progress-bar-success" style="width: 50%;"></div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="padding-5">
									<p class="txt-color-darken font-sm no-margin">Server Load</p>
									<div class="progress progress-micro no-margin">
										<div class="progress-bar progress-bar-success" style="width: 20%;"></div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="padding-5">
									<p class="txt-color-darken font-sm no-margin">Memory Load <span class="text-danger">*critical*</span></p>
									<div class="progress progress-micro no-margin">
										<div class="progress-bar progress-bar-danger" style="width: 70%;"></div>
									</div>
								</div>
							</li>
							<li class="divider"></li>
							<li>
								<div class="padding-5">
									<button class="btn btn-block btn-default">새로고침</button>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			<!-- footer button end -->
		</div>
	</div>
	<!-- FOOTER END -->
	
	<!-- MY MENU START -->
	<div id="shortcut">
		<ul>
			<li>
				<a href="#" class="jarvismetro-tile big-cubes bg-color-blue"> <span class="iconbox"> <i class="fa fa-envelope fa-4x"></i> <span>메일 <span class="label pull-right bg-color-darken">14</span></span> </span> </a>
			</li>
			<li>
				<a href="#" class="jarvismetro-tile big-cubes bg-color-orangeDark"> <span class="iconbox"> <i class="fa fa-calendar fa-4x"></i> <span>캘린더</span> </span> </a>
			</li>
			<li>
				<a href="#" class="jarvismetro-tile big-cubes bg-color-purple"> <span class="iconbox"> <i class="fa fa-map-marker fa-4x"></i> <span>지도</span> </span> </a>
			</li>
			<li>
				<a href="#" class="jarvismetro-tile big-cubes bg-color-greenLight"> <span class="iconbox"> <i class="fa fa-picture-o fa-4x"></i> <span>갤러리 </span> </span> </a>
			</li>
			<li>
				<a href="#" class="jarvismetro-tile big-cubes selected bg-color-pinkDark"> <span class="iconbox"> <i class="fa fa-user fa-4x"></i> <span>마이페이지 </span> </span> </a>
			</li>
		</ul>
	</div>
	<!-- MY MENU END -->

	<!-- Loading Bar JS -->
	<script data-pace-options='{ "restartOnRequestAfter": true }' src="<c:url value="/js/plugin/pace/pace.min.js" />"></script>

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

</body>
</html>