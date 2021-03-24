<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/tagLib.jsp" %>

<!-- MAIN CONTENT -->
<div id="content">
	
	<div class="page-title">
		<div id="spot">
			<div class="page-info">
				텍스트분석
			</div>
			<div class="page-location">
				<span class="home">
					<i class="xi-home-o"></i>
					빌전운전 전문가 지식
				</span>
				<span>텍스트분석 가시화</span>
			</div>
		</div>
	</div>
	
	<section id="widget-grid" class="stepwrap">
		<div class="row">
			<article class="col-sm-12">
				<div class="jarviswidget" id="wid-id-2" data-widget-editbutton="false" data-widget-deletebutton="false">

					<div>
						<div class="fuelux">
							<div class="wizard">
								<ul class="steps">
									<li data-target="#step1" class="active"><span class="badge badge-info">1</span>자동분류<span class="chevron"></span></li>
									<li data-target="#step2"><span class="badge">2</span>텍스트 추출<span class="chevron"></span></li>
									<li data-target="#step3"><span class="badge">3</span>문단 분리<span class="chevron"></span></li>
									<li data-target="#step4"><span class="badge">4</span>문장 분리<span class="chevron"></span></li>
									<li data-target="#step5"><span class="badge">5</span>품사 태깅<span class="chevron"></span></li>
									<li data-target="#step6"><span class="badge">6</span>시멘틱<span class="chevron"></span></li>
									<li data-target="#step7"><span class="badge">7</span>개체명<span class="chevron"></span></li>
								</ul>
								<div class="actions">
									<button type="button" class="btn btn-prev" onclick="javascript:prevStep();">
										<i class="fa fa-caret-left"></i>Prev
									</button>
									<button type="button" class="btn btn-next" onclick="javascript:nextStep();">
										Next<i class="fa fa-caret-right"></i>
									</button>
								</div>
							</div>
							<div class="step-content">
								<form class="form-horizontal" id="fuelux-wizard" method="post">
									<div class="step-pane active" id="step1">
										<h3>STEP1 - 자동 분류</h3>
										<fieldset>
										<div class="stepinr">
											<div class="form-group">
												<label class="col-md-2 control-label">파일 업로드</label>
												<div class="col-md-10">
													<input type="file" class="btn btn-default" id="textVisualizeFile">
												</div>
											</div>
											<div class="form-group">
												<div class="col-md-2"></div>
												<div class="col-md-10">
													<a class="btn btn-primary" href="#" onclick="javascript:getMeta();">자동분류</a>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<select class="form-control" id="textCategory">		
												  		<option value="BOILER">보일러</option>			  	
													  	<option value="GT_TURBINE">가스터빈</option>
													  	<option value="ST_TURBINE">증기터빈</option>
													  	<option value="GEN_PREV">발전기-예방진단</option>
													  	<option value="GEN_INS">발전기-절연진단</option>
													  	<option value="KEPRI">발전기-누설흡습</option>
													</select>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<select class="form-control" id="textRepokind">												
														<option value="all">종류</option>
														<option value="정밀진단">정밀진단</option>
														<option value="고장진단">고장진단</option>
													</select>
												</div>
												<input type="hidden" id="hidReportNM">
												<input type="hidden" id="hidPowerCompNM">
												<input type="hidden" id="hidPowerStNM">
												<input type="hidden" id="hidStNo">
												<input type="hidden" id="hidReporter">
												<input type="hidden" id="hidPublishYM">
												<input type="hidden" id="hidClassfication">
												<input type="hidden" id="hidFileName">
											</div>
										</div>
										</fieldset>
									</div>

									<div class="step-pane" id="step2">
										<h3>STEP2 - 텍스트 추출</h3>
										<fieldset>
										<div class="stepinr">
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tCategory"></div>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tRepokind"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">발전사</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerCompNM"></div>
												</div>
												<label class="col-md-2 control-label">사업소</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerStNM"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">호기</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tStNo"></div>
												</div>
												<label class="col-md-2 control-label">보고서명</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReportNM"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">작성자</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReporter"></div>
												</div>
												<label class="col-md-2 control-label">작성일</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPublishYM"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">추출된 원문</label>
												<div class="col-md-10">
													<div class="showtxt">
														<textarea class="form-control" rows="30" id="step2Filebody">
														</textarea>
													</div>
												</div>
											</div>
										</div>
										</fieldset>
									</div>

									<div class="step-pane" id="step3">
										<h3>STEP3 - 문단 분리</h3>
										<fieldset>
										<div class="stepinr">
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tCategory"></div>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tRepokind"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">발전사</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerCompNM" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">사업소</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerStNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">호기</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tStNo" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">보고서명</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReportNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">작성자</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReporter" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">작성일</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPublishYM" disabled="disabled"></div>
												</div>
											</div>
										</div>
										<div id="step3Result">
										</div>
										<div id="step3More" style="text-align: center;">
											<button class="btn btn-default" type="button" onclick="javascript:drawContents(3);">더보기</button>
										</div>
										<input type="hidden" id="step3_offset">
										</fieldset>
									</div>
									
									<div class="step-pane" id="step4">
										<h3>STEP4 - 문장 분리</h3>
										<fieldset>
										<div class="stepinr">
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tCategory"></div>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tRepokind"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">발전사</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerCompNM" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">사업소</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerStNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">호기</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tStNo" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">보고서명</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReportNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">작성자</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReporter" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">작성일</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPublishYM" disabled="disabled"></div>
												</div>
											</div>
										</div>
										<div id="step4Result">
										</div>
										<div id="step4More" style="text-align: center;">
											<button class="btn btn-default" type="button" onclick="javascript:drawContents(4);">더보기</button>
										</div>
										<input type="hidden" id="step4_offset">
										</fieldset>
									</div>

									<div class="step-pane" id="step5">
										<h3>STEP5 - 형태소 분석</h3>
										<fieldset>
										<div class="stepinr">
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tCategory"></div>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tRepokind"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">발전사</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerCompNM" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">사업소</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerStNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">호기</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tStNo" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">보고서명</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReportNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">작성자</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReporter" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">작성일</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPublishYM" disabled="disabled"></div>
												</div>
											</div>
										</div>
										<div id="step5Result">
										</div>
										<div id="step5More" style="text-align: center;">
											<button class="btn btn-default" type="button" onclick="javascript:drawContents(5);">더보기</button>
										</div>
										<input type="hidden" id="step5_offset">
										</fieldset>
									</div>

									<div class="step-pane" id="step6">
										<h3>STEP6 - 시맨틱 패턴</h3>
										<fieldset>
										<div class="stepinr">
											<div class="leftSwitch">
												<div class="switch-str">모아보기</div>
												<span class="onoffswitch">
													<input type="checkbox" name="start_interval" class="onoffswitch-checkbox" id="st6" onchange="javascript:switchOpt(this.checked, 6);">
													<label class="onoffswitch-label" for="st6"> 
														<span class="onoffswitch-inner" data-swchon-text="ON" data-swchoff-text="OFF"></span> 
														<span class="onoffswitch-switch"></span> 
													</label> 
												</span>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tCategory"></div>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tRepokind"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">발전사</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerCompNM" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">사업소</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerStNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">호기</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tStNo" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">보고서명</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReportNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">작성자</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReporter" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">작성일</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPublishYM" disabled="disabled"></div>
												</div>
											</div>
										</div>
										<div id="step6Result">
										</div>
										<div id="step6More" style="text-align: center;">
											<button class="btn btn-default" type="button" onclick="javascript:drawContents(6);">더보기</button>
										</div>
										<input type="hidden" id="step6_offset">
										</fieldset>
									</div>

									<div class="step-pane" id="step7">
										<h3>STEP7 - 개체명</h3>
										<fieldset>
										<div class="stepinr">
											<div class="leftSwitch">
												<div class="switch-str">모아보기</div>
												<span class="onoffswitch">
													<input type="checkbox" name="start_interval" class="onoffswitch-checkbox" id="st7" onchange="javascript:switchOpt(this.checked, 7);">
													<label class="onoffswitch-label" for="st7"> 
														<span class="onoffswitch-inner" data-swchon-text="ON" data-swchoff-text="OFF"></span> 
														<span class="onoffswitch-switch"></span> 
													</label> 
												</span>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">분야</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tCategory"></div>
												</div>
												<label class="col-md-2 control-label">보고서 종류</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" disabled="disabled" name="tRepokind"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">발전사</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerCompNM" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">사업소</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPowerStNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">호기</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tStNo" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">보고서명</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReportNM" disabled="disabled"></div>
												</div>
											</div>
											<div class="form-group">
												<label class="col-md-2 control-label">작성자</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tReporter" disabled="disabled"></div>
												</div>
												<label class="col-md-2 control-label">작성일</label>
												<div class="col-md-4">
													<div class="showtxt"><input class="form-control input-sm" type="text" name="tPublishYM" disabled="disabled"></div>
												</div>
											</div>
										</div>
										<div id="step7Result">
										</div>
										<div id="step7More" style="text-align: center;">
											<button class="btn btn-default" type="button" onclick="javascript:drawContents(7);">더보기</button>
										</div>
										<input type="hidden" id="step7_offset">
										</fieldset>
									</div>
								</form>
							</div>
						</div>
					</div>

				</div>
			</article>

		</div>
	</section>

</div>
<script src="<c:url value="/js/konan/kepri/analysis.js" />"></script>