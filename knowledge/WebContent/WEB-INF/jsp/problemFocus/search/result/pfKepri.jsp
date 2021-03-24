<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../common/tagLib.jsp" %>
<div class="row">
	<article class="col-xs-12">
		<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
			<header class="ui-sortable-handle">
				<h2>탐색결과</h2><div class="search-rezult">/  ${pfTotal }건</div>
			</header>
			<div class="widget-body">

					<table id="dt_basic" class="display table table-striped table-bordered table-hover" width="100%">
						<colgroup>
							<col style="width:8%;">
							<col style="width:8%;">
							<col style="width:8%;">
							<col>
							<col style="width:6%;">
							<col>
							<col>
							<col>
							<col>
							<col>
						</colgroup>
						<thead>			                
							<tr  class="tr_center">
								<th data-class="data2">발전사</th>
								<th data-hide="data3">사업소</th>
								<th data-hide="data4">호기</th>
								<th data-hide="data5">보고서명</th>
								<th data-hide="data6">작성일</th>
								<th data-hide="data7">전공시험누설량</th>
								<th data-hide="data8">전공시험허용량</th>
								<th data-hide="data9">압력시험누설량</th>
								<th data-hide="data10">압력시험허용량</th>
								<th data-hide="data10">상세보기</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${pfList }" varStatus="loop">
								<tr>
									<td>${result.POWER_COMP_NM }</td>
									<td>${result.POWER_ST_NM }</td>
									<td>${result.ST_NO }</td>
									<td>${result.REPORT_NM }</td>
									<td class="center">${result.PUBLISH_YM }</td>
									<td class="center">${result.vaccum_dn_leak }</td>
									<td class="center">${result.vaccum_dn_leak_avail }</td>
									<td class="center">${result.press_dn_leak }</td>
									<td class="center">${result.press_dn_leak_avail }</td>
									<td><button id="dialog_link3" class="btn btn-default btn-search" type="button" onclick="javascript:detailPFKepriReport('${result.MD5_KEY}')">보기</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<jsp:include page="../../../common/paging.jsp">
						<jsp:param name="firstPageNo" value="${paging.firstPageNo }"/>
						<jsp:param name="prevPageNo" value="${paging.prevPageNo }"/>
						<jsp:param name="startPageNo" value="${paging.startPageNo }"/>
						<jsp:param name="pageNo" value="${paging.pageNo }"/>
						<jsp:param name="endPageNo" value="${paging.endPageNo }"/>
						<jsp:param name="nextPageNo" value="${paging.nextPageNo }"/>
						<jsp:param name="finalPageNo" value="${paging.finalPageNo }"/>
						<jsp:param name="menu" value="problemFocus"/>
					</jsp:include>
			</div>
		</div>
	</article>
</div>