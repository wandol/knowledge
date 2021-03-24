<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../common/tagLib.jsp" %>
<div class="row">
	<article class="col-xs-12">
		<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
			<header class="ui-sortable-handle">
				<h2>탐색결과</h2><div class="search-rezult">/  ${performTotal }건</div>
			</header>
			<div class="widget-body" style="overflow-x: scroll;">

					<table id=pfPerformTBL class="display table table-striped table-bordered table-hover" width="100%">
						<thead>			                
							<tr>
								<!-- 성능 컬럼 -->
								<c:forEach var="colName" items="${performCol }" varStatus="loop">
									<c:choose>
										<c:when test="${colName eq 'POWER_COMP_NM' }">
											<th style="width: 100px;">발전사</th>
										</c:when>
										<c:when test="${colName eq 'POWER_ST_NM' }">
											<th style="width: 100px;">사업소</th>
										</c:when>
										<c:when test="${colName eq 'ST_NO' }">
											<th style="width: 100px;">호기</th>
										</c:when>
										<c:when test="${colName eq 'TEST_ITEM' }">
											<th style="width: 300px;">테스트 항목</th>
										</c:when>
										<c:when test="${fn:indexOf(colName, '_') > -1 }">
											<c:set var="colNameArr" value="${fn:split(colName, '_') }" />
											<th style="width: 100px;">${colNameArr[0] } ${colNameArr[1] }</th>
										</c:when>
										<c:otherwise>
											<th style="width: 100px;">${colName }</th>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<!-- 성능 데이터 -->
							<c:forEach var="item" items="${performList }" varStatus="loop">
								<tr>
									<c:forEach var="colName" items="${performCol }" varStatus="looptwo">
										<c:if test="${looptwo.index  eq 3 }">
											<c:set var="hilight" value="<mark>${params.kwd }</mark>"/>
											<td><c:out value="${fn:replace(item.get(colName), params.kwd, hilight)}" escapeXml="false"/></td>
										</c:if>
										<c:if test="${looptwo.index  ne 3 }">
											<td>${item.get(colName) }</td>
										</c:if>
									</c:forEach>
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

<!-- 					<div class="chart-layer" style="display:none;"> -->
<!-- 						<div class="imgdv"><img src="../images/common/ex_chart.png" alt=""></div> -->
<!-- 					</div> -->

			</div>
		</div>
	</article>
</div>