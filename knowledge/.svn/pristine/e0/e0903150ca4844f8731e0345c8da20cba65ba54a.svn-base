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

					<table id="pfGenInsTBL" class="table table-striped table-bordered table-hover" width="100%">
						<thead>			                
							<tr>
								<th data-hide="data1">NO</th>
								<th data-class="data2">보고서명</th>
								<th data-hide="data3">시험기간</th>
								<th data-hide="data4">구분</th>
								<th data-hide="data5">기기명</th>
								<th data-hide="data6">정격전압</th>
								<th data-hide="data7">절연저항</th>
								<th data-hide="data8">성극지수</th>
								<th data-hide="data8">교전전류</th>
								<th data-hide="data8">유전정접</th>
								<th data-hide="data8">부분방전</th>
								<th data-hide="data8">방전패턴</th>
								<th data-hide="data8">판정</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="result" items="${pfList}" varStatus="status">
								<tr>
									<td>${result.REPO_IDX }</td>
									<td>${result.REPORT_NM }</td>
									<td>${result.PUBLISH_YM }</td>
									<td>${result.GUBUN }</td>
									<td>${result.EQUIP_NM }</td>
									<td>${result.GEN_VOLT }</td>
									<td>${result.TEST_ITEM_1 }</td>
									<td>${result.TEST_ITEM_2 }</td>
									<td>${result.TEST_ITEM_3 }</td>
									<td>${result.TEST_ITEM_4 }</td>
									<td>${result.TEST_ITEM_5 }</td>
									<td class="center" onclick="event.cancelBubble=true">
										<c:if test="${not empty result.PATTERN_IMG}">
											<a class="btn btn-info btn-xs img" href="javascript:void(0);" onclick="javascript:showGenInsImage('${result.PATTERN_IMG }')">이미지</a>
<%-- 											<a class="btn btn-info btn-xs img" href="javascript:void(0);" onclick="javascript:moreImage('pfImage${status.index }')">이미지</a> --%>
										</c:if>
										<p style="display:none;" id="pfImage${status.index}">
											<c:if test="${not empty result.PATTERN_IMG}">
													<c:forTokens var="imgfile" items="${result.PATTERN_IMG}" delims="`">
													<img src="../comm/displayImg.do?fileName=${imgfile}" alt="..." class="img-thumbnail" style="max-width:140px; height:auto;" onclick="javascript:resizeImg('${imgfile}', this);">
													</c:forTokens>
											</c:if>
										</p>	
									</td>
									<td>${result.SUMMARY }</td>		
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
<!-- 					<div class="chart-layer"> -->
<!-- 						<div class="imgdv"><img src="../images/common/ex_chart1.png" alt=""></div> -->
<!-- 					</div> -->

			</div>
		</div>
	</article>
</div>