<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tagLib.jsp" %>
<!-- MAIN CONTENT -->
<div id="content">
	<div class="page-title">
		<div id="spot">
			<div class="page-info">
				발전분야 진단 DB 현황
			</div>
			<div class="page-location">
				<span class="home">
					<i class="xi-home-o"></i>
					빌전운전 전문가 지식
				</span>
				<span>발전분야 진단 DB 현황</span>
			</div>
		</div>
	</div>
	
	<section id="dashTopSection" class="">
		<div class="row">
			<article class="col-xs-4" id="">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="" class="dashTTL">보일러</div>
					</header>
					<div class="widget-body dashContentDiv">
		
						<!-- Header start -->
						<div class="row dashHeaderDiv" id="">
							<div class="dashButtonDiv" category="BOILER">
								<div class="text-center btn-wrap">
									<a href="javascript:void(0);" class="btn btn-sm btn-select" val="part">진단부위</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="sym">현상</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="act">조치결과</a>
								</div>
							</div>
							<div class="dashListTitleDiv" id="dash_BOILER_ListTitle"></div>
						</div>
						
						<!-- Grape top start -->
						<div class="row dashTopDiv" id="">
							<div class="dashGraphDiv">
								<div id="dash_BOILER_PieDiv"></div>												
							</div>
							<div class="dashListDiv" id="dash_BOILER_List">
								<ul></ul>
							</div>
						</div>
						<hr/>
						
						<!-- Grape bottom start -->
						<div>
							<div id="dash_BOILER_BottomTitle"></div>
							<div class="row dashBottomDiv" id="dash_BOILER_BottomDiv"></div>
						</div>
					</div>
				</div>
			</article>
			
			<article class="col-xs-4" id="">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="" class="dashTTL">가스터빈</div>
					</header>
					<div class="widget-body dashContentDiv">
		
						<!-- Header start -->
						<div class="row dashHeaderDiv" id="">
							<div class="dashButtonDiv" category="GT_TURBINE">
								<div class="text-center btn-wrap">
									<a href="javascript:void(0);" class="btn btn-sm btn-select" val="part">진단부위</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="sym">현상</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="act">조치결과</a>
								</div>
							</div>
							<div class="dashListTitleDiv" id="dash_GT_TURBINE_ListTitle"></div>
						</div>
						
						<!-- Grape top start -->
						<div class="row dashTopDiv" id="">
							<div class="dashGraphDiv">
								<div id="dash_GT_TURBINE_PieDiv"></div>												
							</div>
							<div class="dashListDiv" id="dash_GT_TURBINE_List">
								<ul></ul>
							</div>
						</div>
						<hr/>
						
						<!-- Grape bottom start -->
						<div>
							<div id="dash_GT_TURBINE_BottomTitle"></div>
							<div class="row dashBottomDiv"  id="dash_GT_TURBINE_BottomDiv"></div>
						</div>
						
					</div>
				</div>
			</article>
			
			<article class="col-xs-4" id="">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="" class="dashTTL">증기터빈</div>
					</header>
					<div class="widget-body dashContentDiv">
		
						<!-- Header start -->
						<div class="row dashHeaderDiv" id="">
							<div class="dashButtonDiv" category="ST_TURBINE" >
								<div class="text-center btn-wrap">
									<a href="javascript:void(0);" class="btn btn-sm btn-select" val="part">진단부위</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="sym">현상</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="act">조치결과</a>
								</div>
							</div>
							<div class="dashListTitleDiv" id="dash_ST_TURBINE_ListTitle"></div>
						</div>
						
						<!-- Grape top start -->
						<div class="row dashTopDiv" id="">
							<div class="dashGraphDiv">
								<div id="dash_ST_TURBINE_PieDiv"></div>												
							</div>
							<div class="dashListDiv" id="dash_ST_TURBINE_List">
								<ul></ul>
							</div>
						</div>
						<hr/>
						
						<!-- Grape bottom start -->
						<div>
							<div id="dash_ST_TURBINE_BottomTitle"></div>
							<div class="row dashBottomDiv" id="dash_ST_TURBINE_BottomDiv"></div>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
	
	<!-- Publish START -->
	<section id="dashBottomSection" class="">
		<div class="row">
			<article class="col-xs-4" id="">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="" class="dashTTL">발전기 예방진단</div>
					</header>
					<div class="widget-body">
						<div class="row" style="height: 200px;">
							<div class="dashGraphDiv">
								<div id="dash_GEN_PREV_Div"></div>
							</div>
							<div class="dashButtonDiv" category="GEN_PREV">
								<div class="text-center btn-wrap">
									<a href="javascript:void(0);" class="btn btn-sm btn-select" val="part" chartType="bar">진단부위</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="sym" chartType="bar">현상</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default"  val="act" chartType="bar">조치결과</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>
			
			<article class="col-xs-4" id="pfADepthArticle">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="" class="dashTTL">발전기 절연진단</div>
					</header>
					<div class="widget-body">
		
						<div class="row" style="height: 200px;">
							<div class="dashGraphDiv">
								<div id="dash_GEN_INS_Div"></div>
							</div>
							<div class="dashButtonDiv" category="GEN_INS">
								<div class="text-center btn-wrap">
									<a href="javascript:void(0);" class="btn btn-sm btn-select" val="comp" chartType="hBar">발전사</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default" val="powerSt" chartType="hBar">사업소</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>
			
			<article class="col-xs-4" id="">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="" class="dashTTL">발전기 누설흡습</div>
					</header>
					<div class="widget-body">
						<div class="row" style="height: 200px;">
							<div class="dashGraphDiv">
								<div id="dash_KEPRI_Div"></div>
							</div>
							<div class="dashButtonDiv" category="KEPRI">
								<div class="text-center btn-wrap">
									<a href="javascript:void(0);" class="btn btn-sm btn-select" val="comp" chartType="line">발전사</a>
									<a href="javascript:void(0);" class="btn btn-sm btn-default" val="powerSt" chartType="line">사업소</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
	
</div>	
<script src="<c:url value="/js/konan/dashboard/dashboard.js" />"></script>