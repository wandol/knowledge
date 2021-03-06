<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/tagLib.jsp" %>

<div class="row">
	<article class="col-xs-12">
		<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
			<header class="ui-sortable-handle">
				<h2>탐색결과</h2><div class="search-rezult">/  ${turbineTotal }건</div>
			</header>
			<div class="widget-body">

				<!-- superbox start -->
				<div class="row">

					<div id="gallery1_t" class="page">
						<div class="listDt2 listDt2-padd">
							<ul>
								<c:forEach var="result" items="${turbineList}" varStatus="status">
									<li>
									<dl class="listDt">
										<dt>
											<c:set var="imgList" value="${fn:replace(result.IMG_FILES, '|', '`')}"/>
											<c:set var="imgTxtList" value="${fn:replace(result.IMG_TXTS, '|', '`')}"/>
											<c:set var="img" value="${fn:split(imgList,'`')[0]}" />
											<c:set var="imgLength" value="${fn:length(fn:split(imgList,'`'))}" />
											<c:if test="${not empty img }">
												<img src="../comm/displayImg.do?fileName=${img }" data-img="<c:url value="/images/superbox/superbox-full-1.jpg" />" class="superbox-img" onclick="javascript:showImageList('${result.IMG_FILES }','${fn:replace(fn:replace(result.IMG_TXTS, '\'', ''),'\"','')}')">
												<span class="num">${imgLength}</span>
											</c:if>
										</dt>
											<dd>
											<ul class="srhrzt">
												<li>
												<strong class="font-lg" onclick="javascript:detailReport('${result.MD5_KEY}')" style="cursor:pointer;">
<%-- 													<a href="#" class="font-md u-link" onclick="javascript:fileDownload('${result.REPO_FILE_NM}','${params.category }','${result.MD5_KEY }', '${pageName }');"> --%>
														${result.REPORT_NM }
<!-- 													</a> -->
												</strong> | <span class="font-date">${result.PUBLISH_YM }</span> | 
<%-- 												<a href="#" id="dialog_link" class="btn btn-info btn-sm" onclick="javascript:detailReport('${result.MD5_KEY}')">상세보기</a> --%>
												<p class="srhtxt font-md ellipsis" onclick="javascript:detailReport('${result.MD5_KEY}')" style="cursor:pointer;">${result.FILEBODY} </p>
												<p class="lnk-bot">
													<a href="#" class="font-md u-link" onclick="javascript:fileDownload('${result.REPO_FILE_NM}','${params.category }','${result.MD5_KEY }', '${pageName }');">
														
														<!-- 파일 확장자 별 아이콘  -->
														<c:set var="fileSplit" value="${fn:split(result.REPO_FILE_NM,'.')}" />
														<c:set var="fileSplitSize" value="${fn:length(fn:split(result.REPO_FILE_NM,'.'))}" />
														<c:set var="ext" value="${fileSplit[fileSplitSize - 1]}" />
														<c:choose>
															<c:when test="${ext eq 'HWP' || ext eq 'hwp' }">
																<img src="<c:url value="/images/common/ico_han.png" />" alt="한글"> 
															</c:when>
															<c:when test="${ext eq 'PDF' || ext eq 'pdf' }">
																<img src="<c:url value="/images/common/ico_pdf.png" />" alt="PDF"> 
															</c:when>
															<c:when test="${ext eq 'xlsx' || ext eq 'xls' }">
																<img src="<c:url value="/images/common/ico_excel.png" />" alt="엑셀"> 
															</c:when>
															<c:otherwise>
																<img src="" alt="기타"> 
															</c:otherwise>
														</c:choose>
														
														${result.REPO_FILE_NM }
													</a>
													<c:if test="${result.SIMILAR_YN eq 'Y'}">
														<a href="#" class="btn btn_nor btn-sm" onclick="javascript:getMemechecker('${result.MD5_KEY}');"><i class="fa fa-exchange"></i> 유사문서</a>
													</c:if>
												</p>
												</li>
											</ul>
											</dd>
									</dl>
								</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<jsp:include page="../../common/paging.jsp">
						<jsp:param name="firstPageNo" value="${paging.firstPageNo }"/>
						<jsp:param name="prevPageNo" value="${paging.prevPageNo }"/>
						<jsp:param name="startPageNo" value="${paging.startPageNo }"/>
						<jsp:param name="pageNo" value="${paging.pageNo }"/>
						<jsp:param name="endPageNo" value="${paging.endPageNo }"/>
						<jsp:param name="nextPageNo" value="${paging.nextPageNo }"/>
						<jsp:param name="finalPageNo" value="${paging.finalPageNo }"/>
					</jsp:include>
				</div>
				<!-- superbox end -->
			
			</div>
		</div>
	</article>
</div>