<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- layer popup -->
<div id="detailPFKepriReport">
	<div class="diagwrap">

		<table class="tblV1 tablefx">
		<colgroup>
			<col style="width:13.5%">
			<col style="width:13.5%">
			<col style="width:13.5%">
			<col style="width:17%">
			<col style="width:13.5%">
			<col>
		</colgroup>
		<tbody>
		<tr>
			<th>발전소명</th>
			<td colspan="3" id="pfKepriPowerNavi"></td>
			<th>작성일</th>
			<td id="pfKepriPublishYM"></td>
		</tr>
		<tr>
			<th rowspan="6">분석내용</th>
			<td rowspan="3" class="th_c1">시험결과</td>
			<td class="th_c2">구분</td>
			<td colspan="2" class="th_c2">누설량</td>
			<td class="th_c2">허용량</td>
		</tr>
		<tr>
			<td class="th_c3">진공시험</td>
			<td colspan="2" id="pfKepriVaccum"></td>
			<td id="pfKepriVaccumAvail"></td>
		</tr>
		<tr>
			<td class="th_c3">압력시험</td>
			<td colspan="2" id="pfKepriPress"></td>
			<td id="pfKepriPressAvail"></td>
		</tr>
		<tr>
			<td rowspan="3" class="th_c1">의견</td>
			<td colspan="4" id="pfKepriComment1"></td>
		</tr>
		<tr>
			<td colspan="4" id="pfKepriComment2"></td>
		</tr>
		<tr>
			<td colspan="4" id="pfKepriComment3"></td>
		</tr>
		<tr>
			<th>보고서명</th>
			<td colspan="5" id="pfKepriReportNM"></td>
		</tr>
		<tr>
			<th>코멘트</th>
			<td colspan="5">
			
				<div class="replayhead">
					<div class="row">
						<div class="col-xs-12">
							<div class="dvStar"><span></span></div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-10">
						<input class="form-control" type="text" placeholder="의견을 입력해 주세요" id="pfCommentInput">
						</div>
						<div class="col-xs-2" id="pfKepriCommentAdd">
<!-- 							<button class="btn btn-default btn-primary btn-full" type="button" onclick="javascript:detailPFCommentAdd('GT_TURBINE','32f947073c997e159b1869d05377a9c6_3', '0')">등록</button> -->
						</div>
					</div>
				</div>

				<div class="sliderwap3">
					<div id="datailreplySlider" class="bxslider">

					</div>
				</div>

			</td>
		</tr>
		</table>

	</div>
</div>

<!-- //layer popup -->