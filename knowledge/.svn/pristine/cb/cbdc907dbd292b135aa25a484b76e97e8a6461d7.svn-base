<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/tagLib.jsp" %>
<script>
// function detailSearch(){
	
// 	var _searchOpt = $('#repoSearchOpt option:selected').val();
// 	var _powerCompOpt = $('#repoPowerCompOpt option:selected').val();
// 	var _searchKwd = $('#repoKwd').val();
	
// 	_searchKwd = _searchKwd + "#@@#" + _searchOpt + "#@@#" + _powerCompOpt;
	
// 	TABLE.dataTable().api().search(_searchKwd).draw();
// }
function detailSearch(){
	var _cate = $('#repoCate option:selected').val();
	var _searchOpt = $('#repoSearchOpt option:selected').val();
	var _powerCompOpt = $('#repoPowerCompOpt option:selected').val();
	var _searchKwd = $('#repoKwd').val();
	
	document.getElementById("his_category").value = _cate;
	document.getElementById("pageNum").value = 1;
	document.getElementById("his_srchFd").value = _searchOpt;
	document.getElementById("his_powerComp").value = _powerCompOpt;
	document.getElementById("his_kwd").value = _searchKwd;
	
	document.getElementById("historyForm").action = "repository.do";
	document.getElementById("historyForm").submit();
}

function searchDetailRepo(cate,key){
	location.href = context + "/admin/repository/" + cate + "/" + key;
}
</script>

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
					파일 선택
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
								<select class="form-control" id="repoCate" >
									<option value="BOILER" <c:if test="${params.category eq 'BOILER' }">selected="selected"</c:if>>보일러</option>
									<option value="GT_TURBINE" <c:if test="${params.category eq 'GT_TURBINE' }">selected="selected"</c:if>>가스터빈</option>
									<option value="ST_TURBINE" <c:if test="${params.category eq 'ST_TURBINE' }">selected="selected"</c:if>>증기터빈</option>
									<option value="GEN_PREV" <c:if test="${params.category eq 'GEN_PREV' }">selected="selected"</c:if>>발전기-예방진단</option>
									<%-- <option value="GEN_INS" <c:if test="${category eq 'GEN_INS' }">selected="selected"</c:if>>발전기-절연진단</option>
									<option value="KEPRI" <c:if test="${category eq 'KEPRI' }">selected="selected"</c:if>>발전기-누설/흡습</option>
									<option value="PERFORM" <c:if test="${category eq 'PERFORM' }">selected="selected"</c:if>>성능</option> --%>
								</select>
							</div>
							<div class="col-xs-4 col-sm-4 col-md-2">
								<select class="form-control" id="repoPowerCompOpt">
									<option value="all" <c:if test="${params.powerComp eq 'all' }">selected="selected"</c:if>>전체</option>
									<option value="남동발전" <c:if test="${params.powerComp eq '남동발전' }">selected="selected"</c:if>>남동발전</option>
									<option value="남부발전" <c:if test="${params.powerComp eq '남부발전' }">selected="selected"</c:if>>남부발전</option>
									<option value="동서발전" <c:if test="${params.powerComp eq '동서발전' }">selected="selected"</c:if>>동서발전</option>
									<option value="서부발전" <c:if test="${params.powerComp eq '서부발전' }">selected="selected"</c:if>>서부발전</option>
									<option value="중부발전" <c:if test="${params.powerComp eq '중부발전' }">selected="selected"</c:if>>중부발전</option>
									<option value="해외사업운영처" <c:if test="${params.powerComp eq '해외사업운영처' }">selected="selected"</c:if>>해외사업운영처</option>
									<option value="기타" <c:if test="${params.powerComp eq '기타' }">selected="selected"</c:if>>기타</option>
								</select>
							</div>
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
										<button id="repoSearch" class="btn btn-default btn-search" type="button" onclick="javascript:detailSearch();"><i class="fa fa-search"></i></button>
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
									<col>
									<col style="width:15%">
									<col style="width:5%">
									<col style="width:15%">
									<col style="width:25%">
								</colgroup>
								<thead>			                
									<tr>
										<th data-class="data1">No</th>
										<th data-class="data2">파일명</th>
										<th data-class="data3">보고서명</th>
										<th data-class="data4">건수</th>
										<th data-class="data5">작성일</th>
										<th data-class="data6">작성자</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="result" items="${resultData }" varStatus="loop">
										<tr style="cursor:pointer;" onclick="javscript:searchDetailRepo('${params.category}','${result.MD5_KEY }')">
											<c:set var="writerList" value="${fn:split(result.REPORTER,'|') }"/>
											<td data-class="data1">${((params.pageNum-1)*10) + (loop.index+1)}</td>
											<td data-class="data2">${result.REPO_FILE_NM }</td>
											<td data-class="data3">${result.REPORT_NM }</td>
											<td data-class="data4">${result.CNT }</td>
											<td data-class="data5">${result.PUBLISH_YM }</td>
											<td data-class="data6">
											<c:forEach var="writer" items="${writerList}" varStatus="status">
												<c:if test="${writer ne ' '}">${writer}<br/></c:if>
											</c:forEach>
											</td>
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
		<script src="<c:url value="/js/bootstrapForms.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/jquery.dataTables.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/dataTables.colVis.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/dataTables.tableTools.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatables/dataTables.bootstrap.min.js" />"></script>
		<script src="<c:url value="/js/plugin/datatable-responsive/datatables.responsive.min.js" />"></script>
		<script src="<c:url value="/js/konan/admin/admin.js" />"></script>
	</section>

</div>