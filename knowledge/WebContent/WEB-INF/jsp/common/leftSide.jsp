<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./tagLib.jsp" %>

<!-- 타시스템 연계 -->
<spring:eval expression="@global['pix.url']" var="pixUrl" />	<!-- 발전운전데이터시스템 -->
<spring:eval expression="@global['arvr.url']" var="arvrUrl" />	<!-- AR/VR -->

<!-- LEFT MENU START -->
<aside id="left-panel">
	<div class="header-top">
		<h1>
			<a href="http://idpp.kepco.co.kr" style="background: url('<c:url value="/assets/img/admin/idpp_logo2.png"/> ') center no-repeat;" title="IDPP HOME">
				<p class="sr-only">idpp</p>
			</a>
		</h1>
	</div>

	<div class="header-mid">					
			<%-- my service 안쓴다고 함. (혹시 몰라서 디자인 남기려고 주석처리)
			<!-- my service -->
			<div id="myService">
				<div class="sub-title">
					My service
				</div>
				<div class="my-link">
					<ul class="depth2">
					</ul>
				</div>
			</div>
			<!-- //my service -->
 			--%>
			<div class="scroll-bar">
			<nav>
				<ul>
					<li><a href="${pixUrl }"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_script.png" alt="" class="mCS_img_loaded"></span><span class="menu-item-parent">발전운전 데이터 관리</span></a></li>
					<li><a href="#" class="menuSelect"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_cloudcomputing.png" alt="" class="mCS_img_loaded"></span><span class="menu-item-parent">발전운전 전문가 지식</span></a>
						<ul style="display: block;">
							<li  <c:if test="${openMenu eq 'dashboard' }">class="open"</c:if>><a href="<c:url value="/dashboard/dashboard.do"/> "><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_dashboard.png" alt="" class="mCS_img_loaded"></span>발전운전 지식현황</a></li>
							
							<li <c:if test="${openMenu eq 'pfAnalysis' }">class="open"</c:if>><a href="<c:url value="/problemFocus/pfAnalysis.do?category=BOILER"/> "><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_chart.png" alt="" class="mCS_img_loaded"></span>진단 DB 분석</a></li>
							
							<li <c:if test="${openMenu eq 'search' }">class="open"</c:if>><a href="#"><i class="fa fa-search"></i> 기술문서 탐색</a>
								<ul <c:if test="${openMenu eq 'search' }">style="display: block;"</c:if>>
									<li><a href="<c:url value="/kepri/search.do?category=BOILER"/> ">보일러</a></li>
									<li><a href="<c:url value="/kepri/search.do?category=GT_TURBINE"/> ">가스터빈</a></li>
									<li><a href="<c:url value="/kepri/search.do?category=ST_TURBINE"/> ">증기터빈</a></li>
									<li><a href="<c:url value="/kepri/search.do?category=GEN_PREV"/> ">발전기 예방진단</a></li>
									<li><a href="<c:url value="/kepri/search.do?category=GEN_INS"/> ">발전기 절연진단</a></li>
									<li><a href="<c:url value="/kepri/search.do?category=KEPRI"/> ">발전기 누설흡습</a></li>
									<li><a href="<c:url value="/kepri/search.do?category=PERFORM"/> ">성능 예방진단</a></li>
								</ul>
							</li>
							<li <c:if test="${openMenu eq 'problemFocus' }">class="open"</c:if>><a href="#"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_database2.png" alt="" class="mCS_img_loaded"></span>진단 DB 탐색</a>
								<ul <c:if test="${openMenu eq 'problemFocus' }">style="display: block;"</c:if>>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=BOILER"/> ">보일러</a></li>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=GT_TURBINE"/> ">가스터빈</a></li>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=ST_TURBINE"/> ">증기터빈</a></li>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=GEN_PREV"/> ">발전기 예방진단</a></li>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=GEN_INS"/> ">발전기 절연진단</a></li>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=KEPRI"/> ">발전기 누설흡습</a></li>
									<li><a href="<c:url value="/problemFocus/pfSearch.do?category=PERFORM"/> ">성능 예방진단</a></li>
								</ul>
							</li>
							
							<li <c:if test="${openMenu eq 'document' }">class="open"</c:if>><a href="#"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_sw.png" alt="" class="mCS_img_loaded"></span>발전문서 탐색</a>
								<ul <c:if test="${openMenu eq 'document' }">style="display: block;"</c:if>>
									<li><a href="<c:url value="/document/docSearch.do"/> "><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_filetext.png" alt="" class="mCS_img_loaded"></span>발전문서 검색</a></li>
									<%-- 권한 체크대상 --%>
									<c:if test="${sessionScope.login_user.auth_gb == 'ROLE_ADMIN'}">
										<li><a href="<c:url value="/document/categoryManage.do"/> "><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_columns.png" alt="" class="mCS_img_loaded"></span>카테고리 관리</a></li>
									</c:if>
								</ul>
							</li>
							
							
							<li><a href="javascript:chatLayer.show();"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_message.png" alt="" class="mCS_img_loaded"></span>발전운전 QnA (챗봇)</a></li>
							
							<li><a href="javascript:showUpload();"><i class="fa fa-upload"></i> 보고서 업로드</a></li>
							
							<%-- 권한 체크대상 --%>
							<c:if test="${sessionScope.login_user.auth_gb == 'ROLE_ADMIN'}">
								<li <c:if test="${openMenu eq 'visualize' }">class="open"</c:if>><a href="<c:url value="/analysis/visualize.do"/> "><i class="fa fa-text-width"></i> 텍스트분석 가시화</a></li>
								<li <c:if test="${openMenu eq 'repository' }">class="open"</c:if>><a href="#"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_cloudserver.png" alt="" class="mCS_img_loaded"></span>지식 관리</a>
									<ul <c:if test="${openMenu eq 'repository' }">style="display: block;"</c:if>>
										<li><a href="<c:url value="/admin/repository.do?category=BOILER"/> "><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_sw.png" alt="" class="mCS_img_loaded"></span> 지식 관리</a></li>
										<li><a href="<c:url value="/admin/qa.do"/> "><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_sw.png" alt="" class="mCS_img_loaded"></span> Q&A 관리</a></li>
										<li><a href="<c:url value="/kepri_pattern/main.do"/>" target="_blank"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_gitbranch.png" alt="" class="mCS_img_loaded"></span>패턴 관리기</a></li>
										<li><a href="javascript:void(0);" onclick="javascript:goSearchAdmin();"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_cpu.png" alt="" class="mCS_img_loaded"></span>엔진 관리기</a></li>
									</ul>
								</li>
								
							 </c:if>
						</ul>
					</li>
					<li><a href="${arvrUrl }"><span class="depth-icon-img"><img src="/knowledge/assets/img/admin/ico_vr.png" alt="" class="mCS_img_loaded"></span><span class="menu-item-parent">AR/VR</span></a></li>
				</ul>
			</nav>
		</div>
	</div>
	<div class="header-bottom">
		<div class="user-btn-area">
			<a href="javascript:void(0);" class="user-btn">
				<span class="name">${sessionScope.login_user.user_nm}</span>
				<em class="etc"></em>
			</a>
		</div>
		<div class="user-popup">
			<div class="inner">
				<ul>
					<li>
						<a href="javascript:logout();">
							<i class="xi-power-off color-danger"></i>
							<span>Logout</span>
						</a>
					</li>
				</ul>
			</div>
		</div>
		
	</div>
</aside>
<!-- LEFT MENU END -->


<script type="text/javascript">
/**
 * 왼쪽 현재 메뉴 하이라이팅 기능(서브 카테고리 있는 경우)
 */
var _pathname = $(location).attr('pathname'); 	// URL정보
var _param = $(location).attr('search');		// 파라미터 정보
var _cate = $('#his_category').val();			// 현재 카테고리
var selectClass = "menuSelect";

$('.scroll-bar > nav > ul > li > ul').children('.open').find('a').each(function(){
	var _href = $(this).attr('href');
	
	if(_pathname.indexOf('repository') > -1 && _href.indexOf('repository') > -1 ){ // 지식관리 > 지식관리
		$(this).addClass(selectClass);
	}else if(_pathname.indexOf('docSearch') > -1 && _href.indexOf('docSearch') > -1 ){ // 발전문서관리 > 발전문서 검색
		$(this).addClass(selectClass);
	}else if(_pathname.indexOf('categoryManage') > -1 && _href.indexOf('categoryManage') > -1 ){ // 발전문서관리 > 카테고리 관리
		$(this).addClass(selectClass);
	}else{
		if(_pathname.indexOf('repository') == -1 && _param != "" && _href.indexOf(_cate) > -1){ // 기술문서탐색,진단 DB 탐색 
			$(this).addClass(selectClass);
		}
	}
}); 
</script>
