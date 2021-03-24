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
					발전문서 등록
				</span>
			</div>
		</div>
	</div>
	
	<section id="widget-grid" class="norcont">
		<div class="row">
			<article class="col-xs-12">	
				<div class="jarviswidget" id="wid-id-0">
					<div class="widget-body">
						<div class="form-rgt">
							<form class="form-horizontal" id="docForm">
								<fieldset>
									<legend class="hidden">발전문서 등록</legend>
					
									<div class="form-group">
										<label class="col-md-2 control-label">카테고리</label>
										<div class="col-md-10">
											<select class="form-control" id="cat_idx" name="cat_idx">
											</select>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">제목</label>
										<div class="col-md-10">
											<input class="form-control" type="text" id="docTitle" name="docTitle">
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">요약(설명)</label>
										<div class="col-md-10">
											<textarea class="form-control" placeholder="Textarea" rows="4" id="docContents" name="docContents"></textarea>
											<span class="help-block">공백 포함 500자 이내</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">키워드</label>
										<div class="col-md-10">
											<input class="form-control" type="text" id="docKeyword" name="docKeyword">
											<span class="help-block">등록 시 키워드 3개 이상 필수,  키워드는 공백 또는 ,로 분리</span>
										</div>
									</div>
									<div class="form-group">
										<label class="col-md-2 control-label">등록파일</label>
										<div class="col-md-10">
											<div class="filelist">
												<input multiple="multiple" type="file" class="btn btn-default" id="files" name="files">
											</div>
											<div class="btBtn">
												<a class="btn btn-primary" href="javascript:documentAdd();">등록</a>
												<a class="btn btn-mod" href="<c:url value="/document/docSearch.do"/> ">취소</a>
											</div>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
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
		
		
		ajax.data = {'listType':'select'};
		ajax.url = context + "/document/category";
		ajax.type = "GET";
		ajax.success = function(data) {

			var _optionHTML = "";
			if ( data.message == "ok" ) {
				
				for ( var i=0; i<data.list.length; i++ ) {
					_optionHTML += "<option value=\"" + data.list[i].cat_idx + "\">" + data.list[i].cat_nm + "</td>";
				}
				
				
			}
			
			$('#cat_idx').append(_optionHTML);
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