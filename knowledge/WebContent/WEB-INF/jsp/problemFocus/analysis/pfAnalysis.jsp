<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/tagLib.jsp" %>
<!-- MAIN CONTENT -->
<div id="content">
	
	<div class="page-title">
		<div id="spot">
			<div class="page-info">
				<c:choose>
					<c:when test="${params.category eq 'GT_TURBINE' }">
						가스터빈
					</c:when>
					<c:when test="${params.category eq 'ST_TURBINE' }">
						증기터빈
					</c:when>
					<c:when test="${params.category eq 'GEN_PREV' }">
						발전기 예방진단
					</c:when>
					<c:when test="${params.category eq 'GEN_INS' }">
						발전기 절연진단
					</c:when>
					<c:when test="${params.category eq 'KEPRI' }">
						발전기 누설/흡습
					</c:when>
					<c:when test="${params.category eq 'PERFORM' }">
						성능 예방진단
					</c:when>
					<c:otherwise>
						보일러
					</c:otherwise>
				</c:choose>
			</div>
			<div class="page-location">
				<span class="home">
					<i class="xi-home-o"></i>
					빌전운전 전문가 지식
				</span>
				<span>진단 DB 분석</span>
				<span>
					<c:choose>
						<c:when test="${params.category eq 'GT_TURBINE' }">
							가스터빈
						</c:when>
						<c:when test="${params.category eq 'ST_TURBINE' }">
							증기터빈
						</c:when>
						<c:when test="${params.category eq 'GEN_PREV' }">
							발전기 예방진단
						</c:when>
						<c:when test="${params.category eq 'GEN_INS' }">
							발전기 절연진단
						</c:when>
						<c:when test="${params.category eq 'KEPRI' }">
							발전기 누설/흡습
						</c:when>
						<c:when test="${params.category eq 'PERFORM' }">
							성능 예방진단
						</c:when>
						<c:otherwise>
							보일러
						</c:otherwise>
					</c:choose>
				</span>
			</div>
		</div>
	</div>
	
	<!-- DB START -->
	<section id="allSearch" class="">
		<div class="row">

			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="u-box">
					<form class="form-horizontal">
					<fieldset>
						<legend class="hidden">search</legend>

						<div class="row row-head">
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="pfACategory" onchange="javascript:chgCategory(this.value);">
									<option value="BOILER" <c:if test="${params.category eq 'BOILER' }">selected</c:if>>보일러</option>
									<option value="GT_TURBINE" <c:if test="${params.category eq 'GT_TURBINE' }">selected</c:if>>가스터빈</option>
									<option value="ST_TURBINE" <c:if test="${params.category eq 'ST_TURBINE' }">selected</c:if>>증기터빈</option>
									<option value="GEN_PREV" <c:if test="${params.category eq 'GEN_PREV' }">selected</c:if>>발전기-예방진단</option>
								</select>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="pfAPowerComp">
									<option value="all">발전사</option>
								</select>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="pfAPowerSt">
									<option value="all">사업소</option>
								</select>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="pfAStNo">
									<option value="all">호기</option>
								</select>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="pfAPartName">
									<option value="all">진단부위</option>
								</select>
							</div>
						</div>
					</fieldset>
					</form>
				</div>
			</article>
		</div>
	</section>
	<!-- DB END -->
	
	
	<!-- Symptom START -->
	<section class="">
		<div class="row">
			<article class="col-xs-6" id="pfASymptomDiv">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="pfASymptomTTL"></div>
					</header>
					<div class="widget-body pfGrapDiv-pl40">
		
						<!-- superbox start -->
						<div class="row" id="pfASymptom" style="height: 300px;">
							
						</div>
					</div>
				</div>
			</article>
			<article class="col-xs-6" id="pfAActionDiv" style="display: block;">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="pfAActionTTL"></div>
					</header>
					<div class="widget-body pfGrapDiv-pl40">
		
						<!-- superbox start -->
						<div class="row" id="pfAAction" style="height: 300px;">
							
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
	<!-- Symptom END -->
	
	<!-- Publish START -->
	<section id="pfAPublishDepthDiv" class="">
		<div class="row">
			<article class="col-xs-8" id="pfAPublishArticle">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="pfAPublishTTL"></div>
					</header>
					<div class="widget-body pfGrapDiv-pl40">
		
						<!-- superbox start -->
						<div class="row" id="pfAPublish" style="height: 300px;">
							
						</div>
					</div>
				</div>
			</article>
			
			<article class="col-xs-4" id="pfADepthArticle">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<div id="pfADepthTTL"></div>
					</header>
					<div class="widget-body pfGrapDiv-pr40">
		
						<!-- superbox start -->
						<div class="row" id="pfADepth" style="height: 300px;">
							
						</div>
					</div>
				</div>
			</article>
		</div>
	</section>
</div>

<script src="<c:url value="/js/konan/common/common.js" />"></script>
<script src="<c:url value="/js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js" />"></script>
<script src="<c:url value="/js/plugin/fuelux/wizard/wizard.min.js" />"></script>
<script src="<c:url value="/js/contents/wizard.js" />"></script>
<script src="<c:url value="/js/plugin/flot/jquery.flot.time.min.js" />"></script>
<script src="<c:url value="/js/konan/kepri/problemAnalysis.js" />"></script>