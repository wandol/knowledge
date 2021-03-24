<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ui-dialog -->
<div id="detailReport">
	<div class="diagwrap">
	<table class="tblT1">
	<colgroup>
		<col style="width:15%"><col style="width:35%"><col style="width:15%"><col style="width:35%">
	</colgroup>
	<tbody>
	<tr>
		<th>보고서명</th>
		<td id="dReportNm"></td>
		<th>발전사</th>
		<td id="dPowerComp"></td>
	</tr>
	<tr>
		<th>분야</th>
		<td id="dCate">보일러 정밀진단</td>
		<th>작성자</th>
		<td id="dReporter">책임전문원 김용찬, 책임연구원 정남근, 차장 오경환</td>
	</tr>
	<tr>
		<th>분석내용</th>
		<td colspan="3">
			<div class="sliderwap">
				<div id="dProblemFocus" class="bxslider">
				</div>
			</div>

		</td>
	</tr>
	<tr>
		<th>연관현상</th>
		<td colspan="3" id="dSymptom"></td>
	</tr>
	</tbody>
	</table>
	</div>
</div>