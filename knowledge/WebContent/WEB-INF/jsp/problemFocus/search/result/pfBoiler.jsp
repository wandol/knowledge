<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../../common/tagLib.jsp" %>

<div class="row">
	<article class="col-xs-12">
		<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
			<header class="ui-sortable-handle">
				<h2>탐색결과</h2><div class="search-rezult">/  ${pfTotal }건</div>
				<div id="gallerytab" class="btnr" role="menu">
					<a href="<c:url value="/problemFocus/pfSearch.do?category=BOILER"/> " id="gallery1" class="btn <c:if test="${params.isImg ne 'Y' }">on</c:if>"><i class="fa fa-list"></i>목록보기</a>
					<a href="<c:url value="/problemFocus/pfSearch.do?category=BOILER&isImg=Y"/> " id="gallery2" class="btn <c:if test="${params.isImg eq 'Y' }">on</c:if>"><i class="fa  fa-th-large"></i>이미지보기</a>
				</div>
			</header>
			<div class="widget-body">

				<!-- superbox start -->
				<div class="row">
					
					<c:if test="${params.isImg ne 'Y' }">
						<div id="gallery1_t" class="page">
							<c:forEach var="result" items="${pfList }" varStatus="loop">
								<div class="listDt col-xs-6">
									<dl>
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
											<a href="javascript:detailPFReport('${result.MD5_KEY }', '${result.GUBUN_NO }')">
												<ul>
													<li>발전소명 : ${result.POWER_COMP_NM }&nbsp;${result.POWER_ST_NM }&nbsp;${result.ST_NO }</li>
													<li>진단부위 : ${fn:replace(result.PARTNAME, '|', '  ,') }</li>
													<li>위치 : ${fn:replace(result.PART_MID, '|', '  ,') }</li>
													<li>현상 : ${fn:replace(result.SYMPTOM_KWD, '|', '  ,') }</li>
												</ul>
											</a>
											</dd>
									</dl>
								</div>
							</c:forEach>
						</div>
					</c:if>
					
					<c:if test="${params.isImg eq 'Y' }">
						<div id="gallery2_t" class="page">
							<div class="superbox col-sm-12" id="pfImageSearchDiv">
							
								<c:forEach var="result" items="${pfList }" varStatus="loop">
									<c:set var="imgList" value="${fn:split(result.IMG_FILES,'|')}" />
									<c:set var="imgTxtList" value="${fn:split(result.IMG_TXTS,'|')}" />
									
									<!-- '|' 구분자로 자른 List를 loop -->
									<c:forEach var="img" items="${imgList }" varStatus="loop2">
										<c:set var="imgTxt" value="${imgTxtList[loop2.index]}"/>
										<%
											String imgTxt = (String)pageContext.getAttribute("imgTxt");
											if ( imgTxt != null ) {
												pageContext.setAttribute("finalTxt", imgTxt);
											}
										%>
									
										<!-- 이미지에 `구분자가 있으면 loop -->
										<c:forTokens var="finalImg" items="${img }" delims="`">
											<c:if test="${fn:indexOf(finalImg, '.') > -1  }">
											<div class="superbox-list">
												<img src="../comm/displayImg.do?fileName=${finalImg }" data-img="../images/superbox/superbox-full-1.jpg" class="superbox-img" style="width: 170px; height: 190px;" onclick="javascript:detailPFReport('${result.MD5_KEY }', '${result.GUBUN_NO }');">
												<p>${finalTxt}</p>
											</div>
										</c:if>
										</c:forTokens>
									</c:forEach>
								</c:forEach>
								<div class="superbox-float"></div>
							</div>
							<div class="superbox-show" style="height:300px; display: none"></div>
						</div>
					</c:if>
				</div>
			
				<c:if test="${params.isImg ne 'Y' }">
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
				</c:if>
				
				<c:if test="${params.isImg eq 'Y' }">
					<div class="dataTables_paginate">
						<input type="hidden" id="pfImagePageNum">
						<button class="btn btn-default btn-search" type="button" onclick="javascript:showImgList('morelist');">더보기</button>
					</div>
				</c:if>
			</div>
		</div>
	</article>
</div>