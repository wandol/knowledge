<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/tagLib.jsp" %>
<!-- MAIN CONTENT -->
<div id="content">
	
	<div class="page-title">
		<div id="spot">
			<div class="page-info">
				지식 관리
			</div>
			<div class="page-location">
				<span class="home">
					<i class="xi-home-o"></i>
					빌전운전 전문가 지식
				</span>
				<span>지식 관리</span>
				<span>
					지식 관리
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
							<ul class="filedv">
								<li class="txtwd">
									<div class="showtxt disabled">
										<c:choose>
											<c:when test="${cate eq 'GT_TURBINE' }">가스터빈</c:when>
											<c:when test="${cate eq 'ST_TURBINE' }">증기터빈</c:when>
											<c:when test="${cate eq 'GEN_PREV' }">발전기-예방진단</c:when>
											<c:when test="${cate eq 'GEN_INS' }">발전기-절연진단</c:when>
											<c:when test="${cate eq 'KEPRI' }">발전기-누설흡습</c:when>
											<c:when test="${cate eq 'PERFORM' }">성능</c:when>
											<c:otherwise>보일러</c:otherwise>
										</c:choose>
									</div>
								</li>
								<li><a href="#" class="font-md u-link" onclick="javascript:fileDownload('${fileNm}','${cate}','${md5_key}', '${pageName }');">${fileNm }</a></li>
								<li>${reportNm }</li>
								<li>${publishYm }</li>
								<li>${reporter }</li>
							</ul>
						</div>

					</fieldset>
					</form>
				</div>

			</article>
			
		</div>
	</section>

	<section id="widget-grid">
		<input type="hidden" id="repoCategory" value="${cate }" />
		<input type="hidden" id="repoPowerCompNm" value="${powerCompNm }" />
		<input type="hidden" id="repoPowerStNm" value="${powerStNm }" />
		<input type="hidden" id="repoStNo" value="${stNo }" />
		<input type="hidden" id="repoMd5Key" value="${md5_key }" />
		
		<input type="hidden" id="repoRidx" value="${repo_idx }" />
		<input type="hidden" id="repoEidx" value="${equip_idx }" />
		<input type="hidden" id="repoRkind" value="${repo_kind }" />
		<input type="hidden" id="repoDept" value="${dept }" />
		<input type="hidden" id="repoStForm" value="${st_form }" />
		<input type="hidden" id="repoBuiltYm" value="${built_ym }" />
		<input type="hidden" id="repoProductor" value="${productor }" />
		<input type="hidden" id="repoFuel" value="${fuel }" />
		<input type="hidden" id="repoFilepath" value="${filepath }" />
		<input type="hidden" id="repoReportNM" value="${reportNm }" />
		<input type="hidden" id="repoFileNM" value="${fileNm }" />
		<input type="hidden" id="repoPublishYM" value="${publishYm }" />
		<input type="hidden" id="repoReporter" value="${reporter }" />

		<div class="row">
			<article class="col-xs-12">
				<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
					<header class="ui-sortable-handle">
						<h2>지식DB 조회</h2>
					</header>
					<div class="widget-body">

							<table id="dt_basic_detailRepo" class="display table table-striped table-bordered table-hover" width="100%">
								<colgroup>
									<col style="width:4%">
									<col style="width:7%">
									<col style="width:7%">
									<col style="width:7%">
									<col>
									<col style="width:15%">
									<col style="width:15%">
									<col style="width:12%">
									<col style="width:12%">
								</colgroup>
								<thead>			                
									<tr>
										<th data-class="data1">No</th>
										<th data-class="data2">발전사</th>
										<th data-class="data3">발전소</th>
										<th data-class="data4">호기</th>
										<th data-class="data5">위치</th>
										<th data-class="data6">현상</th>
										<th data-class="data7">대책</th>
										<th data-class="data8">이미지</th>
										<th data-class="data9">Edit</th>
									</tr>
								</thead>
								<tbody>
									<c:choose>
										<c:when test="${resultTotal > 0 }">
											<c:forEach var="item" items="${result }" varStatus="loop">
												<tr role="row" class="odd">
													<td class="center">${item.GUBUN_NO + 1 }</td>
													<td style="display: none;">${item.MD5_KEY }</td>
													<td>${item.POWER_COMP_NM }</td>
													<td>${item.POWER_ST_NM }</td>
													<td>${item.ST_NO }</td>
													<td>${item.PARTNAME }</td>
													<td><c:out value="${fn:replace(item.SYMPTOM_KWD, '|', ',')}" /></td>
													<td><c:out value="${fn:replace(item.ACTION_KWD, '|', ',')}" /></td>
													<td>
														<c:set var="imgTxts" value="${ fn:split(item.IMG_TXTS, '|')}"/>
														<c:set var="imgFiles" value="${ fn:split(item.IMG_FILES, '|')}"/>
														<c:set var="imgCount" value="0"/>
														<c:forEach var="imgFileArr" items="${imgFiles}" varStatus="firLoop">
															<c:set var="imgFileArr2" value="${ fn:split(imgFileArr, '`')}"/>
															<c:forEach var="img_arr" items="${imgFileArr2}" varStatus="secLoop">
																<c:if test="${not empty img_arr }">
																	<c:set var="imgCount" value="${imgCount + 1}" />
																	<img src='<c:url value="/comm/displayImg.do?fileName=${img_arr }" />' alt="${imgTxts[firLoop.index] }" class="thum_img" onclick="javascript:showImage(this.src,this.alt);"/>
																	<c:if test="${imgCount % 10 == 0}">
																		<br>
																	</c:if>
																</c:if>
															</c:forEach>
														</c:forEach>
														<input type="hidden" name="knowledgeImgFiles" id="knowledgeImgFiles_${loop.index }" value="${item.IMG_FILES }" />
														<input type="hidden" name="knowledgeImgTxts" id="knowledgeImgTxts_${loop.index }" value="${item.IMG_TXTS }" />
													</td>		
													<td style="display: none;">${item.PART2BODY }</td>	
													<td style="display: none;"></td>					
													<td>
														<a name="repoEdit" class="btn btn-mod btn-haf" href="javascript:void(0);"><i class="fa fa-pencil"></i> 수정</a>
														<a name="repoDel" class="btn btn-mod btn-haf" href="javascript:void(0);"><i class="fa fa-trash-o"></i> 삭제</a>
													</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<tr>
												<td colspan="9" style="text-align: center;">분석 된 데이터가 없습니다.</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
							<div class="col-xs-12">
								<div class="btBtn pull-right pgright" style="margin-top: 1px;">
									<a name="repoSave" class="btn btn-scondary" href="#">적용</a>
									<a class="btn btn-tmod" href="<c:url value="/admin/repository.do?category=${cate }"/> ">취소</a>
								</div>
							</div>
					</div>
				</div>
			</article>
		</div>
		<script type="text/javascript">
			$(function() {
				
				var KNOWLEDGE_INFO = {
						repo_idx: "",
						equip_idx: "",
						repo_kind: "",
						dept: "",
						st_form: "",
						built_ym: "",
						productor: "",
						fuel: "",
						filepath : "",
						report_nm: "",
						file_nm: "",
						publish_ym: "",
						reporter: "",
						md5_key: "",
						gubun_no: "",
						power_comp_nm: "",
						power_st_nm: "",
						st_no: "",
						partName: "",
						symptom_kwd: "",
						action_kwd: "",
						img_files: "",
						part2body: ""
				}
				
				function makeKnowledgeBody(data) {
					
					var _makeBody = "<tbody>";	

					_makeBody += "<tr style=\"display:none;\">";
					_makeBody += "<td>";
					_makeBody += "<input class=\"form-control\" type=\"hidden\" id=\"user_id\" name=\"user_id\" value=\"" + data.md5_key + "\">";
					_makeBody += "</td>";
					_makeBody += "</tr>";
					
					_makeBody += "<tr>";
					_makeBody += "<th>발전사</th>";
					_makeBody += "<td><input class=\"form-control\" type=\"text\" id=\"dept\" name=\"dept\" value=\"" + data.power_comp_nm + "\" disabled></td>";
					_makeBody += "<th>사업소</th>";
					_makeBody += "<td><input class=\"form-control\" type=\"text\" id=\"part_div\" name=\"part_div\" value=\"" + data.power_st_nm + "\" disabled></td>";
					_makeBody += "</tr>";

					_makeBody += "<tr>";
					_makeBody += "<th>호기</th>";
					_makeBody += "<td><input class=\"form-control\" type=\"text\" id=\"\" name=\"dept\" value=\"" + data.st_no + "\" disabled></td>";
					_makeBody += "<th>위치</th>";
					_makeBody += "<td><input class=\"form-control\" type=\"text\" id=\"knowledgePartName\" name=\"knowledgePartName\" value=\"" + data.partName + "\"></td>";
					_makeBody += "</tr>";
					
					_makeBody += "<tr>";
					_makeBody += "<th>현상</th>";
					_makeBody += "<td><input class=\"form-control\" type=\"text\" id=\"knowledgeSymptomKwd\" name=\"knowledgeSymptomKwd\" value=\"" + data.symptom_kwd + "\"></td>";
					_makeBody += "<th>대책</th>";
					_makeBody += "<td><input class=\"form-control\" type=\"text\" id=\"knowledgeActionKwd\" name=\"knowledgeActionKwd\" value=\"" + data.action_kwd + "\"></td>";
					_makeBody += "</tr>";
					
					var _textareaLength = 1;
					if ( data.part2body != "" && data.part2body != undefined ) {
						_textareaLength = data.part2body.split("\n").length;
					}
					
					_makeBody += "<tr>";
					_makeBody += "<th>원문</th>";
					_makeBody += "<td colspan=\"3\"><textarea class=\"form-control\" type=\"text\" id=\"knowledgeImg\" name=\"knowledgeImg\" rows=\"10\">" + data.part2body + "</textarea></td>";
					_makeBody += "</tr>";

					_makeBody += "<tr>";
					_makeBody += "<th>이미지명</th>";
					_makeBody += "<td colspan=\"3\">";
					_makeBody += "<ul class=\"thumlst_edit\" id=\"imgArea\">";
					
					var img_files_out = data.img_files.split("|");
					var img_txts_out = data.img_txts.split("|");
					if(img_files_out.length > 1){
						for ( var j = 0; j < img_files_out.length; j++ ) {
							var img_files_in = img_files_out[j].split("`");	
							if(img_files_in.length > 1){
								for ( var i = 0; i < img_files_in.length; i++ ) {
									_makeBody += "<li>";	
									_makeBody += "<a name='imgRm' class=\"remove_img\">X</a>";
									_makeBody += "<img src='/knowledge/comm/displayImg.do?fileName=" + img_files_in[i] + "' alt=\"" + img_txts_out[j] + "\" class=\"thum_img_edit\" onclick=\"javascript:showImage(this.src,this.alt);\"/>";
									_makeBody += "<input type=\"text\" value=\"" + img_txts_out[j] + "\"/>";
									_makeBody += "</li>";
								}
							}else{
								_makeBody += "<li>";	
								_makeBody += "<a name='imgRm' class=\"remove_img\">X</a>";
								_makeBody += "<img src='/knowledge/comm/displayImg.do?fileName=" + img_files_in + "' alt=\"" + img_txts_out[j] + "\" class=\"thum_img_edit\" onclick=\"javascript:showImage(this.src,this.alt);\"/>";
								_makeBody += "<input type=\"text\" value=\"" + img_txts_out[j] + "\"/>";
								_makeBody += "</li>";
							}
						}
					}else{
						var img_files_in = data.img_files.split("`");
						if(img_files_in.length > 1){
							for ( var i = 0; i < img_files_in.length; i++ ) {
								_makeBody += "<li>";	
								_makeBody += "<a name='imgRm' class=\"remove_img\">X</a>";
								_makeBody += "<img src='/knowledge/comm/displayImg.do?fileName=" + img_files_in[i] + "' alt=\"" + data.img_txts + "\" class=\"thum_img_edit\" onclick=\"javascript:showImage(this.src,this.alt);\"/>";
								_makeBody += "<input type=\"text\"  value=\"" + data.img_txts + "\"/>";
								_makeBody += "</li>";
							}
						}else{
							if(data.img_files != ''){
								_makeBody += "<li>";	
								_makeBody += "<a name='imgRm' class=\"remove_img\">X</a>";
								_makeBody += "<img src='/knowledge/comm/displayImg.do?fileName=" + data.img_files + "' alt=\"" + data.img_txts + "\" class=\"thum_img_edit\" onclick=\"javascript:showImage(this.src,this.alt);\"/>";
								_makeBody += "<input type=\"text\"  value=\"" + data.img_txts + "\"/>";
								_makeBody += "</li>";
							}
						}
					}
					
					_makeBody += "</ul>";
					_makeBody += "</td>";
					_makeBody += "</tr>";
					
					_makeBody += "</tbody>";
					
					return _makeBody;
				}

				// 이미지 삭제 아이콘 클릭시
				$(document).on("click","a[name='imgRm']",function(event){	
					var selectRmImg = $(this).closest('li');
					selectRmImg.remove();
				});
				
				// 수정 버튼 클릭 시
				$(document).on("click","a[name='repoEdit']",function(event){	
					var currentRow = $(this).closest('tr');
					var indexList = currentRow.find('td:eq(0)').text();
					KNOWLEDGE_INFO.gubun_no = currentRow.find('td:eq(0)').text();
					KNOWLEDGE_INFO.md5_key = currentRow.find('td:eq(1)').text();
					KNOWLEDGE_INFO.power_comp_nm = currentRow.find('td:eq(2)').text();
					KNOWLEDGE_INFO.power_st_nm = currentRow.find('td:eq(3)').text();
					KNOWLEDGE_INFO.st_no = currentRow.find('td:eq(4)').text();
					KNOWLEDGE_INFO.partName = currentRow.find('td:eq(5)').text();
					KNOWLEDGE_INFO.symptom_kwd = currentRow.find('td:eq(6)').text();
					KNOWLEDGE_INFO.action_kwd = currentRow.find('td:eq(7)').text();
					KNOWLEDGE_INFO.part2body = currentRow.find('td:eq(9)').text();
					
				 	var img_files = currentRow.find('td:eq(8)')[0].children;
					for ( var i=0; i < img_files.length; i++ ) {
						if ( img_files[i].getAttribute("name") == "knowledgeImgFiles" ) {
							KNOWLEDGE_INFO.img_files = img_files[i].value;;
						}else if(img_files[i].getAttribute("name") == "knowledgeImgTxts" ){
							KNOWLEDGE_INFO.img_txts = img_files[i].value;;
						}
					} 
					
					var _body = makeKnowledgeBody(KNOWLEDGE_INFO);		
					var _title = "지식 DB 수정";
					
					COMMON_MODAL_OPTION.width = 1000;
					imgListModal.show(_title, _body, COMMON_MODAL_OPTION, CONFIRM, function() {
						
						var _category = $('#repoCategory option:selected').val();
						var _partname = $('#knowledgePartName').val();
						var _symptom_kwd = $('#knowledgeSymptomKwd').val();
						var _action_kwd = $('#knowledgeActionKwd').val();

						var _chg_img_files = [];
						var _chg_img_txts = [];
						var _img_txts = document.querySelectorAll("#imgArea > li > input[type=text]");
						var _img_files = document.querySelectorAll("#imgArea > li > img");
						var chg_img_html = "";
						for ( var i=0; i < _img_txts.length; i++ ) {
							_chg_img_txts.push(_img_txts[i].value);
						}
						for ( var i=0; i < _img_files.length; i++ ) {
							//	class 제거
							_img_files[i].classList.value = "thum_img";
							// 	get html
							chg_img_html +=  _img_files[i].outerHTML;
							_chg_img_files.push(_img_files[i].src.substr(_img_files[i].src.indexOf("=") + 1));
						}

						chg_img_html += "<input type=\"hidden\" name=\"knowledgeImgFiles\" id=\"knowledgeImgFiles_" + (Number(indexList) - 1) + "\" value=\""+_chg_img_files.join("|")+"\" />";
						chg_img_html += "<input type=\"hidden\" name=\"knowledgeImgTxts\" id=\"knowledgeImgTxts_" + (Number(indexList) - 1) + "\" value=\""+_chg_img_txts.join("|")+"\" />";
						
// 						$("#knowledgeImgFiles_" + (Number(indexList) - 1) ).val(_chg_img_files.join("|"));
// 						$("#knowledgeImgTxts_" + (Number(indexList) - 1) ).val(_chg_img_txts.join("|"));

						currentRow.find('td:eq(8) > img').remove();
						currentRow.find('td:eq(8)')[0].innerHTML = chg_img_html;

						
						currentRow.find('td:eq(5)')[0].innerText = _partname;
						currentRow.find('td:eq(6)')[0].innerText = _symptom_kwd;
						currentRow.find('td:eq(7)')[0].innerText = _action_kwd;
						currentRow.find('td:eq(10)')[0].innerText = "udt";	// 수정 flag
						
					});
				});
		
				//삭제 버튼 클릭 시
				$("a[name='repoDel']").on('click', function() {
		
					var currentRow = $(this).closest('tr');
					console.log(currentRow);
					
					message.show(message.warning, "지식 DB를 삭제 하시겠습니까?", ALERT, function() {
						currentRow.css('text-decoration', 'line-through');
						currentRow.css('text-decoration-color', 'orange');
						currentRow.css('color', 'orange');
						currentRow.find('td:eq(10)')[0].innerText = "del";	// 삭제 flag
					});
				});
				
				//저장 버튼 클릭 시
				$("a[name='repoSave']").on('click', function() {
					var _list = [];		
					var _trList =$('#dt_basic_detailRepo tbody tr');
					var _category = $('#repoCategory').val();
					var _md5_key = "";
					
					for ( var i=0; i<_trList.length; i++ ) {		
						var _tr = _trList[i];
						var _obj = {};
						
						_md5_key = _tr.children[1].innerText;
						
						_obj.gubun_no = _tr.children[0].innerText;
						_obj.md5_key = _tr.children[1].innerText;
						_obj.partname = _tr.children[5].innerText;
						_obj.symptom_kwd = _tr.children[6].innerText;
						_obj.action_kwd = _tr.children[7].innerText;
						_obj.part2body = _tr.children[9].innerText;
						_obj.img_files = $('#knowledgeImgFiles_' + i).val() == undefined ? "" : $('#knowledgeImgFiles_' + i).val();
						_obj.img_txts = $('#knowledgeImgTxts_' + i).val() == undefined ? ""  : $('#knowledgeImgTxts_' + i).val();
						_obj.status = _tr.children[10].innerText;	// 수정인지 삭제인지 구분값
						
						//메타데이터
						_obj.power_comp_nm = _tr.children[2].innerText;
						_obj.power_st_nm = _tr.children[3].innerText;
						_obj.st_no = _tr.children[4].innerText;
						_obj.repo_idx = $('#repoRidx').val();
						_obj.equip_idx = $('#repoEidx').val();
						_obj.repo_kind = $('#repoRkind').val();
						_obj.dept = $('#repoDept').val();
						_obj.st_form = $('#repoStForm').val();
						_obj.built_ym = $('#repoBuiltYm').val();
						_obj.productor = $('#repoProductor').val();
						_obj.fuel = $('#repoFuel').val();
						_obj.filepath = $('#repoFilepath').val();
						_obj.report_nm = $('#repoReportNM').val();
						_obj.file_nm = $('#repoFileNM').val();
						_obj.publish_ym = $('#repoPublishYM').val();
						_obj.reporter = $('#repoReporter').val();
						
						_list.push(_obj);
					}
					
					$.ajax({
						url: context + "/admin/repository/" + _category + "/" + _md5_key,
				        data: {
				        	"jsonObj": JSON.stringify(_list)
				        },
				        type: 'POST',
				        dataType: 'json',
				        success: function(data) {
				        	if ( data.message == "success" ) {
				        		message.show(message.info, "수정사항이 반영되었습니다.", ALERT, function() {
									location.href = context + "/admin/repository/" + _category + "/" + _md5_key;
								});
				        	} else {
				        		message.show(message.warning, "수정사항 반영이 실패되었습니다.(message :" + data.message + ")", ALERT);
				        	}
				        },
				        error: function(error) {
							console.log(error);
						}
				   });
				});
				
				//뒤로가기 버튼 클릭 시
				$("a[name='repoBack']").on('click', function() {
					CATEGORY = $('#repoCategory option:selected').val();
					
					location.href = context + "/admin/repository.do";
				});
			
			});
		</script>
	</section>

</div>