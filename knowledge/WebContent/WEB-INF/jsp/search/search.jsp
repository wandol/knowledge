<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tagLib.jsp" %>


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
				<span>기술문서 탐색</span>
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

	<section id="widget-grid">

		<div class="row">
			<article class="col-xs-12">
				<form class="form-horizontal">
				<fieldset>
					<legend class="hidden">search</legend>
					<div class="jarviswidget" id="wid-id-1" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
						<header class="ui-sortable-handle">
							<h2>탐색옵션</h2>
						</header>
						<div class="widget-body">

							<!-- superbox start -->
							<div class="row show-grid">
								<div class="col-xs-3">
									<div class="form-group">
										<label class="control-label col-xs-4">발전사</label>
										<div class="col-xs-8">
											<select class="form-control" id="powerComp">
												<option value="all">전체</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="form-group">
										<label class="control-label col-xs-4">사업소</label>
										<div class="col-xs-8">
											<select class="form-control" id="powerSt">
												<option value="all">전체</option>
											</select>
										</div>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="form-group">
										<label class="control-label col-xs-4">호기</label>
										<div class="col-xs-8">
											<select class="form-control" id="stNo">
												<option value="all">전체</option>
											</select>
										</div>
									</div>
								</div>
							</div>

							<div class="row show-grid">
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label col-xs-2">상세탐색</label>
										<div class="col-xs-8">
											<select class="form-control" id="srcFields" style="width:19%;float:left;" title="검색대상 필드">
												<option value="" <c:if test="${empty params.fields}">selected="selected"</c:if>>전체</option>
												<option value="REPORT_NM" <c:if test="${params.fields eq 'REPORT_NM' }">selected="selected"</c:if>>보고서명</option>
												<option value="FILEBODY" <c:if test="${params.fields eq 'FILEBODY' }">selected="selected"</c:if>>파일내용</option>
											</select>
											<input class="form-control"  style="width:80%;float:right;" value="${params.kwd }" placeholder="검색어를 입력하세요" type="text" id="kwd" onkeypress="if(event.keyCode==13) {detailSearch(); return false;}">
										</div>
										<div class="col-xs-2">
											<button class="btn btn-default btn-search" type="button" onclick="javascript:detailSearch();"><i class="fa fa-search"></i></button>
										</div>
									</div>
								</div>
								<div class="col-xs-6">
									<div class="form-group">
										<label class="control-label col-xs-2">시험일시</label>
										<div class="col-xs-4">
											<div class="hasinput icon-addonr">
												<input id="startDate" type="text" placeholder="YYYY.MM" class="form-control datepicker" data-dateformat="yy.mm" value="${ params.startPublishYm}">
											</div>
										</div>
										<div class="col-xs-1 center hipen">~</div>
										<div class="col-xs-4">
											<input id="endDate" type="text" placeholder="YYYY.MM" class="form-control datepicker" data-dateformat="yy.mm" value="${ params.endPublishYm}">
										</div>
									</div>
								</div>
							</div>
							<!-- superbox end -->

						</div>

						<div class="widget-foot dark">
							<div class="row show-grid">
								<div class="col-xs-12">
									<div class="fbold stitle">* 선택한 옵션</div>
									<div class="selectBtn">
									</div>
								</div>
							</div>
						</div>

					</div>
				</fieldset>
				</form>
			</article>
		
		</div>
	</section>
	
	<!-- 각 분야별 결과 페이지 -->
	<c:if test="${params.category eq 'BOILER' }">
		<jsp:include page="./result/boiler.jsp" />
	</c:if>
	
	<c:if test="${params.category eq 'GT_TURBINE' }">
		<jsp:include page="./result/gtTurbine.jsp" />
	</c:if>
	
	<c:if test="${params.category eq 'ST_TURBINE' }">
		<jsp:include page="./result/stTurbine.jsp" />
	</c:if>
	
	<c:if test="${params.category eq 'GEN_PREV' }">
		<jsp:include page="./result/genPrev.jsp" />
	</c:if>
	
	<c:if test="${params.category eq 'GEN_INS' }">
		<jsp:include page="./result/genIns.jsp" />
	</c:if>
	
	<c:if test="${params.category eq 'KEPRI' }">
		<jsp:include page="./result/kepri.jsp" />
	</c:if>
	
	<c:if test="${params.category eq 'PERFORM' }">
		<jsp:include page="./result/perform.jsp" />
	</c:if>

	<script src="<c:url value="/js/bootstrapForms.js" />"></script>
	<script src="<c:url value="/js/plugin/datatables/jquery.dataTables.min.js" />"></script>
	<script src="<c:url value="/js/plugin/datatables/dataTables.colVis.min.js" />"></script>
	<script src="<c:url value="/js/plugin/datatables/dataTables.tableTools.min.js" />"></script>
	<script src="<c:url value="/js/plugin/datatables/dataTables.bootstrap.min.js" />"></script>
	<script src="<c:url value="/js/plugin/datatable-responsive/datatables.responsive.min.js" />"></script>
	<script src="<c:url value="/js/datatables.js" />"></script>
	<script src="<c:url value="/js/jqui.js" />"></script>
	<script src="<c:url value="/js/jquery.bxslider.js" />"></script>
	<script src="<c:url value="/js/konan/kepri/kepri.js" />"></script>
	<script src="<c:url value="/js/konan/kepri/problemFocus.js" />"></script>
	<script>
	function slider(){
		kepriSlider = $('#dProblemFocus').bxSlider({
			touchEnabled: false
		});
	}
	
	function destroySlider() {
		if ( kepriSlider != undefined ) { 
			kepriSlider.destroySlider(); 
		}
	}
	function slider2(){
		pfSlider = $('#pfImgSlide').bxSlider({
			pager:false,
			touchEnabled: false
		});
	}
	function slider3(){
		pfCommentSlider = $('#datailreplySlider').bxSlider({
			controls:false,
			touchEnabled: false
		});
	}
</script>
</div>

<div id="fileBodyModal"></div>	<!-- 파일 원문보기 모달창 -->
<jsp:include page="../common/modal/detailReport.jsp" />
<jsp:include page="../common/modal/detailPFReport.jsp" />
<jsp:include page="../common/modal/memechecker.jsp" />