<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tagLib.jsp" %>

<!-- MAIN CONTENT -->
<div id="content">
	
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
					카테고리 관리
				</span>
			</div>
		</div>
	</div>
	
	<section id="widget-grid" class="norcont">
		<div class="row">
		<article class="col-xs-12">
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle">
					<h2>조회결과</h2><div class="search-rezult"></div>
				</header>
				<div class="widget-body">
					<!-- superbox start -->
					<div class="row">

						<div id="gallery1_t" class="page">
							<div class="listDt2">
								<div class="tblwrap">
									<table class="tblT2" id="docCategoryTBL">
									<colgroup>
										<col style="width:8%">
										<col style="width:8%">
										<col>
										<col style="width:15%">
										<col style="width:15%">
										<col style="width:15%">
										<col style="width:15%">
										<col style="width:8%">
									</colgroup>
									<thead>
									<tr>
										<th>선택</th>
										<th>No.</th>
										<th>카테고리명</th>
										<th>순번</th>
										<th>사용여부</th>
										<th>등록건수</th>
										<th>등록 파일수</th>
										<th></th>
									</tr>
									</thead>
									<tbody>
					
									</tbody>
									</table>
									<div class="btBtn">
										<a class="btn btn-primary" href="#" onclick="javascript:categoryAddDiv();">추가</a>
										<a class="btn btn-default" href="#" onclick="javascript:categoryDelete();">삭제</a>
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
	</section>

</div>

<script src="<c:url value="/js/konan/kepri/document.js" />"></script>
<script>
$(document).ready(function() {
	
	function initCategory() {
		
		
		ajax.data = {};
		ajax.url = context + "/document/category";
		ajax.type = "GET";
		ajax.success = function(data) {
			var _total_count = data.list.length;
			if ( data.message == "ok" ) {
				var _trHTML = "";
				
				for ( var i=0; i<_total_count; i++ ) {
					
					var _useYN = "";
					var _useTitle = "사용안함";
					
					_trHTML += "<tr class=\"add\">";
					_trHTML += "<td>";
					_trHTML += "<div class=\"checkbox\"  style=\"text-align: center;\">";
					_trHTML += "<label>";
					_trHTML += "<input type=\"checkbox\" class=\"checkbox style-0\" name=\"categoryCheck\">";
					_trHTML += "<span></span>";
					_trHTML += "</label>";
					_trHTML += "<input type=\"hidden\" value='" + data.list[i].cat_idx + "'>";
					_trHTML += "</div>";
					_trHTML += "</td>";
					_trHTML += "<td><span>" + (i + 1) + "</span><div style=\"display: none;\"><input class=\"form-control\" type=\"text\" value=\"" + data.list[i].cat_idx + "\" /></div></td>";
					_trHTML += "<td class=\"tleft\"><span>" + data.list[i].cat_nm + "</span><div style=\"display: none;\"><input class=\"form-control\" type=\"text\" value=\"" + data.list[i].cat_nm + "\" /></div></td>";
					_trHTML += "<td><span>" + data.list[i].order_no + "</span><div style=\"display: none;\"><input class=\"form-control\" type=\"text\" value=\"" + data.list[i].order_no + "\" /></div></td>";
					_trHTML += "<td>";
					_trHTML += "<div class=\"checkbox\">";
					_trHTML += "<label>";
					
					if ( data.list[i].use == "Y" ) {
						_useYN = "checked";
						_useTitle = "사용함";
					}
					
					_trHTML += "<input type=\"checkbox\" class=\"checkbox style-0\" onclick=\"javascript:checkUseYN(this);\" " + _useYN + " disabled>";
					_trHTML += "<span>" + _useTitle + "</span>";
					_trHTML += "</label>";
					_trHTML += "</div>";
					_trHTML += "</td>";
					_trHTML += "<td><a href=\"javascript:void(0);\" onclick=\"javascript:findCategoryData('"+data.list[i].cat_nm+"');\">" + data.list[i].row_count + "</a></td>";
					_trHTML += "<td>" + data.list[i].file_count + "</td>";
					_trHTML += "<td><div><a class=\"btn btn-mod btn-full\" href=\"javascript:void(0);\" onclick=\"javascript:categoryUpdate(this);\"><i class=\"fa  fa-pencil\"></i> 수정</a></div>";
					_trHTML += "<div style=\"display: none;\"><a class=\"btn btn-mod btn-full\" href=\"javascript:void(0);\" onclick=\"javascript:categoryUpdateSave(this);\"><i class=\"fa  fa-pencil\"></i> 적용</a></div></td>";
					_trHTML += "</tr>";
				}
				
				$('#docCategoryTBL tbody').append(_trHTML);
				$('.search-rezult').text("/ "+_total_count + "건");
			} else {
				
			}
		};
		
		ajax.error = function(xhr, ajaxOptions) {
			if ( xhr.status == 901 ) {
				message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			}
		}
		
		// ajax 실행
		$.ajax(ajax);
	}
	
	initCategory();
});
</script>