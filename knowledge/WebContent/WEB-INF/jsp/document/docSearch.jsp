<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tagLib.jsp" %>
<!-- MAIN CONTENT -->
<div id="content">
	<form id="docForm" action="docSearch.do" method="get">
		<input type="hidden" name="kwd" id="his_docKwd" value="${paramVO.kwd }" />
		<input type="hidden" name="pageNum" id="his_docPageNum" value="${paramVO.pageNum }" />
		<input type="hidden" name="pageSize" id="his_docPageSize" value="${paramVO.pageSize }" />
		<input type="hidden" name="fields" id="his_docFields" value="${paramVO.pageSize }" />
		<input type="hidden" name="kwdList" id="his_docKwdList" value="${paramVO.pageSize }" />
	</form>
	
	<div class="page-title">
		<div id="spot">
			<div class="page-info">
				발전문서
			</div>
			<div class="page-location">
				<span class="home">
					<i class="xi-home-o"></i>
					빌전운전 전문가 지식
				</span>
				<span>발전문서 관리</span>
				<span>
					발전문서 검색
				</span>
			</div>
		</div>
	</div>
	
	
	<section id="widget-grid">
		<div class="row">

			<article class="col-xs-12 col-sm-12 col-md-12 col-lg-12">

				<div class="u-box">
					<form class="form-horizontal">
					<fieldset>
						<legend class="hidden">search</legend>
						<div class="row row-none">
							<div class="col-xs-6 col-sm-6 col-md-7">
								<div class="form-group">
									<div class="col-xs-8 col-sm-8 col-md-8">
										<select class="form-control" id="srcFields" style="width:19%;float:left;" title="검색대상 필드">
												<option value="" <c:if test="${empty params.fields}">selected="selected"</c:if>>전체</option>
												<option value="CAT_NM" <c:if test="${params.fields eq 'CAT_NM' }">selected="selected"</c:if>>분야</option>
												<option value="TTL" <c:if test="${params.fields eq 'TTL' }">selected="selected"</c:if>>제목</option>
												<option value="ABSTRACT" <c:if test="${params.fields eq 'ABSTRACT' }">selected="selected"</c:if>>내용</option>
											</select>
										<input class="form-control" style="width:80%;float:right;" placeholder="검색어를 입력하세요" type="text" id="docKwd" value="${params.kwd }"  onkeypress="if(event.keyCode==13) {docSearch(); return false;}">
									</div>
									<div class="col-xs-4 col-sm-4 col-md-2">
										<button class="btn btn-default btn-search" type="button" onclick="javascript:docSearch();"><i class="fa fa-search"></i></button>
									</div>
								</div>
							</div>
							<div class="col-xs-12 col-md-3"></div>
							<div class="col-xs-12 col-md-2">
								<div class="form-group">
									<label class="control-label col-xs-6 text-right">목록개수</label>
									<div class="col-xs-6 pull-right">
										<select class="form-control" id="docLimit">
											<option value="10">10</option>
											<option value="20">20</option>
										</select>
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
		
	<div class="row">
		<article class="col-xs-12">
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle">
					<h2>조회결과</h2><div class="search-rezult">/ ${docTotal } 건</div>
				</header>
				<div class="widget-body">
	
					<!-- superbox start -->
					<div class="row">
	
						<div id="gallery1_t" class="page">
							<div class="listDt2">
								<div class="tblwrap">
									<table class="tblT2 vtopTbl">
									<colgroup>
										<col style="width:5%;">
										<col style="width:5%;">
										<col style="width:5%;">
										<col>
										<col style="width:8%;">
										<col style="width:8%;">
										<col style="width:20%;">
										<col style="width:5%;">
									</colgroup>
									<thead>
									<tr>
										<th>선택</th>
										<th>No.</th>
										<th>분야</th>
										<th>제목</th>
										<th>등록일자</th>
										<th>등록자</th>
										<th>키워드</th>
										<th>파일목록</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="item" items="${docList }" varStatus="loop">
										<tr>
											<td class="center">
												<div class="checkbox">
													<label>
														<c:if test="${sessionScope.login_user.user_nm == 'admin' or sessionScope.login_user.user_nm == item.REG_NM}">
															<input type="checkbox" class="checkbox style-0" name="docCheck">
														</c:if>
														<%-- <c:if test="${sessionScope.login_user.user_nm != 'admin' and sessionScope.login_user.user_nm != item.REG_NM}">
															<input type="checkbox" class="checkbox style-0" name="docCheck" disabled="disabled">
														</c:if> --%>
													  <span></span>
													</label>
													<input type="hidden" name="docId" value="${item.DOC_IDX }">
												</div>
											</td>
											<%-- <td>${(docTotal - (params.pageNum-1)*10) - loop.index }</td> --%>
											<td>${((params.pageNum-1)*10) + (loop.index+1) }</td>
											<td class="tleft">${item.CAT_NM }</td>
											<td class="tleft">
												<div class="sbjhd">${item.TTL }</div>
<!-- 												<dl class="listDetail"> -->
<%-- 													<dt style="background : none;"><a href="#">${item.ABSTRACT } <span class="ar"></span></a></dt> --%>
<!-- 														<dd> -->
<!-- 														<ul> -->
<!-- 															<li><strong>제목</strong> 터빈분야 정밀 진단 진단보고서</li> -->
<!-- 															<li><strong>요약(설명)</strong> 시뮬레이션은 사용자 질의에 대한 자연어 분석 결과 및 의도에 대한 답변 목록을 제공하여 즉답형/시나리오형  -->
<!-- 															구성에 대한 검사 및 답변의 정확성을 확인하는 기능을 제공</li> -->
<!-- 															<li><strong>키워드</strong> 침식, Crack, 블레이드, GE, 협의회</li> -->
<!-- 														</ul> -->
<!-- 														</dd> -->
<!-- 												</dl> -->
											</td>
											<td>${item.OPERATE_TM }</td>
											<td>${item.REG_NM }</td>
											<td class="tleft">
												<c:forTokens var="keyword" items="${item.KWD }" delims="|">
													<a href="#" onclick="javascript:docSearchKwdList('${keyword}')"><span class="keywd">${keyword }</span></a>
												</c:forTokens>
											</td>
											<td>
												<div class="input-group-btn">
													<c:set var="fileSplitSize" value="${fn:length(fn:split(item.FILE_LIST,'|'))}" />
													<c:if test="${fileSplitSize > 0 }">
														<a class="btn btn-mod btn-full dropdown-toggle" data-toggle="dropdown" tabindex="-1" href="#"><i class="fa fa-download"></i> 다운로드</a>
														<ul class="dropdown-menu pull-right" role="menu">
															<c:if test="${fileSplitSize > 1 }">
															<li class="alldown"><a href="javascript:void(0);" onclick="javascript:allFileDownLoad('GEN_DOC','${item.DOC_IDX}','${item.TTL}');">일괄저장</a></li>
															</c:if>
															<c:forTokens var="file" items="${item.FILE_LIST }" delims="|">
																<li><a href="javascript:void(0);" onclick="javascript:fileDownload('${file }', 'GEN_DOC', '${item.DOC_IDX }', '${pageName }');"><b>ㆍ&nbsp;&nbsp;</b>${file }</a></li>
															</c:forTokens>
														</ul>
													</c:if>
												</div>
											</td>
										</tr>
									</c:forEach>
									</tbody>
									</table>
								</div>
								<div class="paging">
									<ul class="pagination">
										<li class="paginate_button previous disabled" id="dt_basic_previous"><a href="javascript:gotoPage(${paging.firstPageNo}, '${menu }')" aria-controls="dt_basic" data-dt-idx="0" tabindex="0">Previous</a></li>
										<c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
									        <c:choose>
									            <c:when test="${i eq params.pageNum}"><li class="paginate_button active"><a href="#" aria-controls="dt_basic" data-dt-idx="${i }" tabindex="0">${i }</a></li></c:when>
									            <c:otherwise><li class="paginate_button "><a href="javascript:gotoPage(${i}, '${menu }')" aria-controls="dt_basic" data-dt-idx="${i}" tabindex="0">${i}</a></li></c:otherwise>
									        </c:choose>
									    </c:forEach>
										<li class="paginate_button next" id="dt_basic_next"><a href="#" aria-controls="dt_basic" data-dt-idx="3" tabindex="0">Next</a></li>
									</ul>
									<div class="btBtn pull-right">
										<a class="btn btn-primary" href="<c:url value="/document/docAdd.do"/> ">등록</a>
										<a class="btn btn-mod" href="javascript:void(0);" onclick="javascript:docUpdatePage();">수정</a>
										<a class="btn btn-mod" href="javascript:void(0);" onclick="javascript:docDelete();">삭제</a>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- superbox end -->
				
				</div>
			</div>
		</article>
	</div>
</div>
<script src="<c:url value="/js/konan/kepri/document.js" />"></script>