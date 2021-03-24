<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../tagLib.jsp" %>
<!-- ui-dialog -->
<style type = "text/css"> <!-- 로딩바스타일 -->
body
{
 text-align: center;
 margin: 0 auto;
}
#uploadLoading
{
 position: absolute;
 left: 50%;
 top: 50%;
 background: #ffffff;
}
#btn_upload{cursor: not-allowed;}
</style>
<div id="upload">
	<form method="POST" enctype="multipart/form-data" id="uploadDialogForm">

		<fieldset>
			<div class="diagwrap">
				<table class="tblT1">
					<colgroup>
						<col style="width:15%"><col style="width:35%"><col style="width:15%"><col style="width:35%">
					</colgroup>
					<tbody>
						<!-- 1 floor -->
						<tr>
							<th>보고서명</th>
							<td colspan="3"><input class="form-control" type="text" placeholder="Search" id="upLoadReportName" name="upLoadReportName"></td>
						</tr>
						<!-- 1 floor -->
						
						<!-- 2 floor -->
						<tr>
							<th>분야</th>
							<td>
								<select class="form-control" id="upLoadPowerType" name="upLoadPowerType">
									<option value="BOILER">보일러</option>			  	
								  	<option value="GT_TURBINE">가스터빈</option>
								  	<option value="ST_TURBINE">증기터빈</option>
								  	<option value="GEN_PREV">발전기-예방진단</option>
								  	<option value="GEN_INS">발전기-절연진단</option>
								  	<option value="KEPRI">발전기-누설흡습</option>
								</select>
							</td>
							<th>발전사</th>
							<td>
								<select class="form-control" id="upLoadPowerComp" name="upLoadPowerComp">
									<option value="all">발전사</option>
								</select>
							</td>
						</tr>
						<!-- 2 floor -->
						
						<!-- 3 floor -->
						<tr>
							<th>사업소</th>
							<td>
								<select class="form-control" id="upLoadPowerSt" name="upLoadPowerSt">
									<option value="all">사업소</option>
								</select>
							</td>
							<th>호기</th>
							<td>
								<select class="form-control" id="upLoadStNo" name="upLoadStNo">
									<option value="all">호기</option>
								</select>
							</td>
						</tr>
						<!-- 3 floor -->
						
						<!-- 4 floor -->
						<tr>
							<th>출판년도</th>
							<td>
								<input class="form-control" type="text" placeholder="YYYY.MM" placeholder="YYYY.MM." id="upLoadPublishYM" name="upLoadPublishYM">
							</td>
							<th>보고서종류</th>
							<td>
								<select class="form-control" id="upLoadRepoKind" name="upLoadRepoKind">											
									<option value="all">종류</option>
									<option value="정밀진단">정밀진단</option>
									<option value="고장진단">고장진단</option>
								</select>
							</td>
						</tr>		
						<!-- 4 floor -->	
						
						<!-- 5 floor -->
						<tr>
							<th>첨부파일</th>
							<td colspan="3">
								<input class="form-control" type="file" id="file" name="file" style="width: 100%;">
								<input type="hidden" id="isRework" name="isRework" value="N" /> 
							</td>
						</tr>		
						<!-- 5 floor -->	
					</tbody>
				</table>
			</div>
		</fieldset>

	</form>
	<div id="uploadLoading">
		<img src="<c:url value="/images/select2-spinner.gif" />" alt="loading">
	</div>
</div>