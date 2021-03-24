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
				<span>진단 DB 탐색</span>
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
							<c:if test="${params.category eq 'KEPRI' || params.category eq 'PERFORM' || params.category eq 'GEN_INS' }">
								<div class="col-xs-4 col-sm-4 col-md-2">
									<select class="form-control" id="pfPowerComp">
										<option value="all">발전사</option>
									</select>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-2">
									<select class="form-control" id="pfPowerSt">
										<!-- 대쉬보드에서 넘어온 데이터중 발전소 선택 안해도 검색은 가능하나 select box 미 표시로 인해 강제로 값 주입. -->
										<c:choose>
											<c:when test="${params.powerSt ne 'all'}">
												<option value="${params.powerSt }">${params.powerSt }</option>
											</c:when>
											<c:otherwise>
												<option value="all">사업소</option>
											</c:otherwise>
										</c:choose>
									</select>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-2">
									<select class="form-control" id="pfStNo">
										<option value="all">호기</option>
									</select>
								</div>
							</c:if>
							<!-- 누설흡습의 경우 진단부위도 보이지 않는다. -->
							<c:if test="${params.category ne 'KEPRI' && params.category ne 'PERFORM' && params.category ne 'GEN_INS' }">
								<div class="col-xs-4 col-sm-4 col-md-2">
									<select class="form-control" id="pfPartName">
										<option value="all">
											<c:choose>
												<c:when test="${params.category eq 'GEN_PREV' }">점검항목</c:when>
												<c:otherwise>진단부위</c:otherwise>
											</c:choose>
										</option>
									</select>
								</div>
							</c:if>
							<!-- 발전기 절연진단, 누설흡습 , 성능의 경우 현상,대책 옵션은 보이지 않는다. -->
							<c:if test="${params.category ne 'PERFORM' && params.category ne 'GEN_INS' && params.category ne 'KEPRI' }">
								<div class="col-xs-4 col-sm-4 col-md-2">
									<select class="form-control" id="pfSymptomKwd">
										<option value="all">현상</option>
									</select>
								</div>
								<div class="col-xs-4 col-sm-4 col-md-2">
									<select class="form-control" id="pfActionKwd">
										<option value="all">대책</option>
									</select>
								</div>
							</c:if>
						</div>

						<div class="row row-body show-grid">
							<div class="col-xs-12">
								<div class="fbold stitle">* 선택한 옵션</div>
								<div class="selectBtn">
								</div>
							</div>
						</div>

						<div class="row row-foot show-grid">
							<div class="col-md-7">
								<div class="form-group">
									<label class="control-label col-xs-2">상세탐색</label>
									<div class="col-xs-8">
										<input class="form-control" value="${params.kwd }" placeholder="검색어를 입력하세요" type="text" name="kwd" id="pfKwd" onkeypress="if(event.keyCode==13) {detailPFSearch(); return false;}">
									</div>
									<div class="col-xs-2">
										<button class="btn btn-default btn-search" type="button" onclick="javascript:detailPFSearch();"><i class="fa fa-search"></i></button>
									</div>
								</div>
							</div>
							<div class="col-md-5">
								<div class="form-group">
									<label class="control-label col-xs-3">시험일시</label>
									<div class="col-xs-4">
										<div class="hasinput icon-addonr">
											<input id="pfStartDate" type="text" placeholder="YYYY.MM." class="form-control datepicker"  data-dateformat="yy.mm" value="${ params.startPublishYm}">
										</div>
									</div>
									<div class="col-xs-1 center hipen">~</div>
									<div class="col-xs-4">
										<input id="pfEndDate" type="text" placeholder="YYYY.MM." class="form-control datepicker"  data-dateformat="yy.mm" value="${ params.endPublishYm}">
									</div>
								</div>
							</div>
						</div>

					</fieldset>
					</form>
				</div>

			</article>
			
		</div>
	</section>
	<!-- DB END -->
	
	
	<!-- section start -->
	<section id="widget-grid" class="">

		<!-- 보일러 진단 DB 결과 -->
		<c:if test="${params.category eq 'BOILER' }">
			<jsp:include page="./result/pfBoiler.jsp" />
		</c:if>
		
		<!-- 가스터빈 진단 DB 결과 -->
		<c:if test="${params.category eq 'GT_TURBINE' }">
			<jsp:include page="./result/pfGtTurbine.jsp" />
		</c:if>
		
		<!-- 증기터빈 진단 DB 결과 -->
		<c:if test="${params.category eq 'ST_TURBINE' }">
			<jsp:include page="./result/pfStTurbine.jsp" />
		</c:if>
		
		<!-- 발전기 예방진단 진단 DB 결과 -->
		<c:if test="${params.category eq 'GEN_PREV' }">
			<jsp:include page="./result/pfGenPrev.jsp" />
		</c:if>
		
		<!-- 발전기 절연진단 진단 DB 결과 -->
		<c:if test="${params.category eq 'GEN_INS' }">
			<jsp:include page="./result/pfGenIns.jsp" />
		</c:if>
		
		<!-- 발전기 누설흡습 진단 DB 결과 -->
		<c:if test="${params.category eq 'KEPRI' }">
			<jsp:include page="./result/pfKepri.jsp" />
		</c:if>

		<!-- 성능 진단 DB 결과 -->
		<c:if test="${params.category eq 'PERFORM' }">
			<jsp:include page="./result/pfPerform.jsp" />
		</c:if>
	
	</section>
	<!-- section end -->
	
</div>
	
<!-- 지식 DB 탐색 상세페이지 Modal창 -->
<c:choose>
	<c:when test="${params.category eq 'KEPRI' }">
		<jsp:include page="../../common/modal/detailPFKepriReport.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="../../common/modal/detailPFReport.jsp" />
	</c:otherwise>
</c:choose>



<script src="<c:url value="/js/konan/common/common.js" />"></script>
<script src="<c:url value="/js/plugin/bootstrap-wizard/jquery.bootstrap.wizard.min.js" />"></script>
<script src="<c:url value="/js/plugin/fuelux/wizard/wizard.min.js" />"></script>
<script src="<c:url value="/js/contents/wizard.js" />"></script>
<script src="<c:url value="/js/konan/kepri/problemFocus.js" />"></script>
<script>
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