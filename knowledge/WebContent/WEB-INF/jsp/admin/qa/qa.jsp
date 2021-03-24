<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/tagLib.jsp" %>
<!-- MAIN CONTENT -->
<div id="content">
	
	<div class="page-title">
		<div id="spot">
			<div class="page-info">
				파일 선택
			</div>
			<div class="page-location">
				<span class="home">
					<i class="xi-home-o"></i>
					빌전운전 전문가 지식
				</span>
				<span>지식 관리</span>
				<span>
					Q/A 관리
				</span>
			</div>
		</div>
	</div>
	<section id="allSearch" class="">
		<div class="row">

			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="u-box">
					<form class="form-horizontal">
					<fieldset>
						<legend class="hidden">search</legend>

						<div class="row row-none">
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="repoSearchOpt" >
									<option value="" <c:if test="${params.srchFd eq '' }">selected="selected"</c:if>>전체</option>
									<option value="REPO_FILE_NM_TXT" <c:if test="${params.srchFd eq 'REPO_FILE_NM_TXT' }">selected="selected"</c:if>>파일명</option>
									<option value="REPORT_NM" <c:if test="${params.srchFd eq 'REPORT_NM' }">selected="selected"</c:if>>보고서명</option>
									<option value="REPORTER_TXT" <c:if test="${params.srchFd eq 'REPORTER_TXT' }">selected="selected"</c:if>>작성자</option>
									<option value="PUBLISH_YM" <c:if test="${params.srchFd eq 'PUBLISH_YM' }">selected="selected"</c:if>>작성일</option>
								</select>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-4">
								<div class="form-group">
									<div class="col-xs-8 col-sm-8 col-md-8">
										<input class="form-control" id="repoKwd" value="${params.kwd }" placeholder="검색어를 입력하세요" type="text" onkeypress="if(event.keyCode==13) {detailSearch(); return false;}">
									</div>
									<div class="col-xs-4 col-sm-4 col-md-4">
										<button id="qaSearch" class="btn btn-default btn-search" type="button" onclick="javascript:detailSearch();"><i class="fa fa-search"></i></button>
									</div>
								</div>
							</div>
							<div class="col-xs-6 col-sm-6 col-md-4" style="float: right;">
								<button id="qaSearch" class="btn btn-default btn-search" type="button" onclick="javascript:addQaPop();">질문셋 등록</button>
							</div>
						</div>

					</fieldset>
					</form>
				</div>

			</article>
			
		</div>
	</section>

	<section id="widget-grid">
		<input type="hidden" id="his_repoCate" value="${category }" />

		<div class="row">
			<article class="col-xs-12">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<h2>파일선택</h2>
					</header>
					<div class="widget-body">

							<table id="dt_basic_repository" class="display table table-striped table-bordered table-hover" width="100%">
								<colgroup>
									<col style="width:5%">
									<col style="width:10%">
									<col style="width:15%">
									<col>
									<col style="width:25%">
									<col style="width:20%">
								</colgroup>
								<thead>			                
									<tr>
										<th data-class="data1">No</th>
										<th data-class="data2">카테고리</th>
										<th data-class="data3">의도</th>
										<th data-class="data4">질문</th>
										<th data-class="data5">답변</th>
										<th data-class="data6">참조근거</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="result" items="${QaList }" varStatus="loop">
										<tr style="cursor:pointer;" onclick="javscript:searchDetailRepo('${params.category}','${result.MD5_KEY }')">
											<td data-class="data1">${((params.pageNum-1)*10) + (loop.index+1)}</td>
											<td data-class="data2">${result.category }</td>
											<td data-class="data3">${result.REPORT_NM }</td>
											<td data-class="data4">${result.question }</td>
											<td data-class="data5">${result.answer }</td>
											<td data-class="data6">${repo_cont}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						<jsp:include page="../../common/paging.jsp">
							<jsp:param name="firstPageNo" value="${paging.firstPageNo }"/>
							<jsp:param name="prevPageNo" value="${paging.prevPageNo }"/>
							<jsp:param name="startPageNo" value="${paging.startPageNo }"/>
							<jsp:param name="pageNo" value="${paging.pageNo }"/>
							<jsp:param name="endPageNo" value="${paging.endPageNo }"/>
							<jsp:param name="nextPageNo" value="${paging.nextPageNo }"/>
							<jsp:param name="finalPageNo" value="${paging.finalPageNo }"/>
							<jsp:param name="menu" value="${menu }"/>
						</jsp:include>
					</div>
				</div>
			</article>
		</div>
		<jsp:include page="../../common/modal/qaAdd.jsp" />
		<script src="<c:url value="/js/bootstrapForms.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/jquery.dataTables.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/dataTables.colVis.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/dataTables.tableTools.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/dataTables.bootstrap.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatable-responsive/datatables.responsive.min.js" />"></script>
		<script src="<c:url value="/js/konan/admin/admin.js" />"></script>
		<script src="<c:url value="/js/konan/admin/qa.js" />"></script>
	</section>

</div>