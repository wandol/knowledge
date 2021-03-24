/**
 * 분석에 대한 처리를 담당하는 JS
 * 1. 텍스트분석 > 시뮬레이션
 */

var paraList = [];
var sentenceList = [];
var posTagList = [];
var symanticList = [];
var symanticListToMatch = [];
var nerList = [];
var nerListToMatch = [];
var swSymantic = false;
var swNer = false;

var analysis = {
		isMeta: false,
		isSetMeta: false,
		isParagraph: false,
		isSentence: false,
		isPosTag: false,
		isSymantic: false,
		isNer: false,
		getMeta: function() {
			var _data = new FormData();
			
			_data.append("file", $('#textVisualizeFile').prop('files')[0]);
			
			$.ajax({
	            type: "POST",
	            enctype: 'multipart/form-data',
	            url: context + "/analysis/meta",
	            data: _data,
	            dataType: "json",
	            processData: false,
	            contentType: false,
	            cache: false,
	            timeout: 600000,
	            beforeSend: function () {
	            	$('#loading').show();
	            },
	            success: function (data) {
	            	
	            	if ( data.message != undefined && data.message != "" ) {
	            		analysis.isMeta = false;		// 메타정보 성공여부. Next step으로 넘어가기 위한 안전장치. false일 경우 Next step으로 못 넘어간다.
	            		message.show(message.warning, "파일에서 원문 추출을 실패하였습니다. (" + data.message + ")", ALERT);
	            	} else {
	            		
	            		var _filebody = data.fileBody;
	            		var _meta = data.metaInfo;
	            		var _fileName = data.fileName;
	            		var _cate = "";
	            		var _kind = "";
	                	
	                	message.show(message.noti, "메타정보 추출이 완료 되었습니다.", ALERT, function() {
	                		
	                		if ( _filebody.classfication.indexOf("가스터빈") > -1 ) {
	                    		_cate = "GT_TURBINE";
	                    		_kind = _filebody.classfication.split("#")[1];
	                    	} else if ( _filebody.classfication.indexOf("증기터빈") > -1 ) {
	                    		_cate = "ST_TURBINE";
	                    		_kind = _filebody.classfication.split("#")[1];
	                    	} else if ( _filebody.classfication.indexOf("발전기#예방진단") > -1 ) {
	                    		_cate = "GEN_PREV";
	                    	} else if ( _filebody.classfication.indexOf("발전기#절연진단") > -1 ) {
	                    		_cate = "GEN_INS";
	                    	} else if ( _filebody.classfication.indexOf("발전기#누설흡습") > -1 ) {
	                    		_cate = "KEPRI";
	                    	} else if ( _filebody.classfication.indexOf("성능") > -1 ) {
	                    		_cate = "PERFORM";
	                    	} else if ( _filebody.classfication.indexOf("보일러") > -1 ){
	                    		_cate = "BOILER";
	                    		_kind = _filebody.classfication.split("#")[1];
	                    	} else {
	                    		_cate = "BOILER";
	                    		_kind = "정밀진단";
	                    	}
	                		
	                		$('#textCategory').val(_cate);
	                		
	                		$('#hidReportNM').val(_meta.report_nm);
	                		$('#hidPowerCompNM').val(_meta.company);
	                		$('#hidPowerStNM').val(_meta.power_st);
	                		$('#hidStNo').val(_meta.st_no);
	                		$('#hidReporter').val(_meta.tester);
	                		$('#hidPublishYM').val(_meta.test_day);
	                		$('#hidClassfication').val(_filebody.classfication);
	                		$('#hidFileName').val(_fileName);
	                		$('#step2Filebody').html(_filebody.fileBody);
	                		
	                		if ( _kind != "" && _kind != null ) {
	                			$('#textRepokind').val(_kind);
	                			$('#textRepokind').removeAttr('disabled');
	                		} else {
	                			// _kind값이 없을땐 초기화 및 선택 못하게 막기
	                			$('#textRepokind').val("all");
	                			$('#textRepokind').attr('disabled', 'true');
	                		}
	                		
	                		$("input[name='textRepokind']").val(_meta.report_nm);
	                		
	                		analysis.isMeta = true;		// 메타정보 성공여부. Next step으로 넘어가기 위한 안전장치. false일 경우 Next step으로 못 넘어간다.
						});
	                	
	            	}
	            },
	            error: function (e) {
	            	message.show(message.warning, "업로드 실패", ALERT);
	            },
	            complete:function(){
	            	$('#loading').hide(); 
	            }
		});
	},
	paragraph: function() {
		
		$.ajax({
            type: "POST",
            url: context + "/analysis/paragraph",
            data: {
            	'classfication': $('#hidClassfication').val(),
            	'filebody': $('#step2Filebody').text()
            },
   		 	dataType: "json",
            beforeSend: function () {
            	$('#loading').show();
            },
            success: function (data) {
            	
            	if ( data.message == "success" ) {
            		$('#step3Result').empty();	// 데이터를 그리기 전 그려질 Div 초기화
            		
            		paraList = data.paraList;
            		drawContents(3, 0);
            		
            		// 문단 나누기 성공시 true로 변경
            		analysis.isParagraph = true;
            	} else {
            		// 문단 나누기 성공시 true로 변경
            		analysis.isParagraph = false;
            		message.show(message.warning, "파일원문에서 문단 추출을 실패하였습니다. (" + data.message + ")", ALERT);
            	}
            },
            error: function (e) {
            	message.show(message.noti, "업로드 실패", ALERT);
            },
            complete:function(){
            	$('#loading').hide(); 
            }
		});
	},
	sentence: function() {
		$.ajax({
            type: "POST",
            url: context + "/analysis/sentence",
            data: {
            	'paraList': paraList,
            	'fileName': $('#hidFileName').val()
            },
   		 	dataType: "json",
            beforeSend: function () {
            	$('#loading').show();
            },
            success: function (data) {
            	if ( data.message == "success" ) {
            		$('#step4Result').empty();	// 데이터를 그리기 전 그려질 Div 초기화
            		
            		sentenceList = data.sentenceList;
            		drawContents(4, 0);
            		
            		// 문장 나누기 성공시 true로 변경
            		analysis.isSentence = true;
            	} else {
            		// 문장 나누기 성공시 true로 변경
            		analysis.isSentence = false;
            		message.show(message.warning, "문단에서 문장 추출을 실패하였습니다. (" + data.message + ")", ALERT);
            	}
            },
            error: function (e) {
            	message.show(message.noti, "업로드 실패", ALERT);
            },
            complete:function(){
            	$('#loading').hide(); 
            }
		});
	},
	posTag: function() {
		$.ajax({
            type: "POST",
            url: context + "/analysis/posTag",
            data: {
            	'sentenceList': sentenceList
            },
   		 	dataType: "json",
            beforeSend: function () {
            	$('#loading').show();
            },
            success: function (data) {
            	if ( data.message == "success" ) {
            		$('#step5Result').empty();	// 데이터를 그리기 전 그려질 Div 초기화
            		
            		posTagList = data.posTagList;
            		drawContents(5, 0);
            		
            		// 문장 나누기 성공시 true로 변경
            		analysis.isPosTag = true;
            	} else {
            		// 문장 나누기 성공시 true로 변경
            		analysis.isPosTag = false;
            		message.show(message.warning, "형태소 분석을 실패하였습니다. (" + data.message + ")", ALERT);
            	}
            },
            error: function (e) {
            	message.show(message.noti, "업로드 실패", ALERT);
            },
            complete:function(){
            	$('#loading').hide(); 
            }
		});
	},
	symantic: function() {
		$.ajax({
            type: "POST",
            url: context + "/analysis/symantic",
            data: {
            	'sentenceList': sentenceList,
            	'category': $('#textCategory option:selected').val()
            },
   		 	dataType: "json",
            beforeSend: function () {
            	$('#loading').show();
            },
            success: function (data) {
            	if ( data.message == "success" ) {
            		$('#step6Result').empty();	// 데이터를 그리기 전 그려질 Div 초기화
            		
            		symanticList = data.symantic.symanticList;
            		symanticListToMatch = data.symantic.symanticListToMatch;
            		drawContents(6, 0, false);
            		
            		// 문장 나누기 성공시 true로 변경
            		analysis.isSymantic = true;
            	} else {
            		// 문장 나누기 성공시 true로 변경
            		analysis.isSymantic = false;
            		message.show(message.warning, "시맨틱 매칭이 실패 하였습니다. (" + data.message + ")", ALERT);
            	}
            },
            error: function (e) {
            	message.show(message.noti, "업로드 실패", ALERT);
            },
            complete:function(){
            	$('#loading').hide(); 
            }
		});
	},
	ner: function() {
		$.ajax({
            type: "POST",
            url: context + "/analysis/ner",
            data: {
            	'sentenceList': sentenceList,
            	'classfication': $('#hidClassfication').val(),
            	'fileNm': $('#hidFileName').val()
            },
   		 	dataType: "json",
            beforeSend: function () {
            	$('#loading').show();
            },
            success: function (data) {
            	
            	if ( data.message == "success" ) {
            		$('#step7Result').empty();	// 데이터를 그리기 전 그려질 Div 초기화
            		
            		nerList = data.ner;
//            		symanticListToMatch = data.symantic.symanticListToMatch;
            		drawContents(7, 0, false);
            		
            		// 문장 나누기 성공시 true로 변경
            		analysis.isNer = true;
            	} else {
            		// 문장 나누기 성공시 true로 변경
            		analysis.isNer = false;
            		message.show(message.warning, "개체명 추출이 실패 하였습니다. (" + data.message + ")", ALERT);
            	}
            },
            error: function (e) {
            	message.show(message.warning, "업로드 실패", ALERT);
            },
            complete:function(){
            	$('#loading').hide(); 
            }
		});
	}
};

$(document).ready(function() {
	$(".wizard .actions .btn").click(function(){
		var chg = $(".steps").find('.active').index();
		$(".step-content").find(".step-pane").removeClass('active');
		$(".step-content .step-pane").eq(chg).addClass('active');
	});
});

function nextStep() {
	var _stepList = $("ul.steps").find("li");
	var _activeStep = -1;
	
	for ( var i=0; i<_stepList.length; i++ ) {
		if ( _stepList[i].className == "active" ) {
			_activeStep = i+1;
		}
	};
	
	if ( (_activeStep) == _stepList.length ) message.show(message.info, "마지막 화면 입니다.", ALERT);
	else {	// process 처리 부분
		
		switch ( (_activeStep + 1) ) {
			case 2 :
				
				// 메타정보 셋팅
				if ( analysis.isMeta ) {
					var _category = $('#textCategory option:selected').val();
					var _repoKind = $('#textRepokind option:selected').val() == "all" ? "" : $('#textRepokind option:selected').val();
					var _reportNM = $('#hidReportNM').val();
            		var _powerCompNM = $('#hidPowerCompNM').val();
            		var _powerStNM = $('#hidPowerStNM').val();
            		var _stNo = $('#hidStNo').val();
            		var _reporter = $('#hidReporter').val();
            		var _publishYM = $('#hidPublishYM').val();
            		
            		// 자동분류 단계에서 이미 메타정보를 추출하여 history input에 담아놓은 데이터를 텍스트 추출 단계에 노출
            		$("input[name='tCategory']").val(getCategory(_category));
            		$("input[name='tRepokind']").val(_repoKind);
            		$("input[name='tPowerCompNM']").val(_powerCompNM);
            		$("input[name='tPowerStNM']").val(_powerStNM);
            		$("input[name='tStNo']").val(_stNo);
            		$("input[name='tReportNM']").val(_reportNM);
            		$("input[name='tReporter']").val(_reporter);
            		$("input[name='tPublishYM']").val(_publishYM);
            		
            		analysis.isSetMeta = true;
				} else {
					message.show(message.warning, "파일 업로드가 되지 않았습니다.", ALERT);
					return false;
				}
				break;
			case 3 :
				
				if ( analysis.isSetMeta ) {
					
					// 텍스트 추출 단계에서 수정한 메타 정보를 다시 재 셋팅해준다.
					$("input[name='tPowerCompNM']").val($("input[name='tPowerCompNM']").val());
            		$("input[name='tPowerStNM']").val($("input[name='tPowerStNM']").val());
            		$("input[name='tStNo']").val($("input[name='tStNo']").val());
            		$("input[name='tReportNM']").val($("input[name='tReportNM']").val());
            		$("input[name='tReporter']").val($("input[name='tReporter']").val());
            		$("input[name='tPublishYM']").val($("input[name='tPublishYM']").val());
					
					analysis.paragraph();
				} else {
					message.show(message.warning, "메타 정보 추출 및 파일 원문 추출이 실패되었습니다.", ALERT);
					return false;
				}
				
				break;
			case 4 :
				
				if ( analysis.isParagraph ) {
					analysis.sentence();
				} else {
					message.show(message.warning, "파일 원문에서 문단 추출이 실패되었습니다.", ALERT);
					return false;
				}
				break;
			case 5 :
				
				if ( analysis.isSentence ) {
					analysis.posTag();
				} else {
					message.show(message.warning, "문단에서 문장 분리가 실패되었습니다.", ALERT);
					return false;
				}
				break;
			case 6 :
				
				if ( analysis.isPosTag ) {
					analysis.symantic();
				} else {
					message.show(message.warning, "형태소 분석이 실패되었습니다.", ALERT);
					return false;
				}
				break;
			case 7 :
				
				if ( analysis.isSymantic ) {
					analysis.ner();
				} else {
					message.show(message.warning, "시맨틱 분석이 실패되었습니다.", ALERT);
					return false;
				}
				break;
			default : 
				_isSuccess = false;
		}
		
		_stepList[_activeStep-1].classList.remove("active");
		_stepList[_activeStep].classList.add("active");
	}
}

function prevStep() {
	var _stepList = $("ul.steps").find("li");
	var _activeStep = -1;
	
	for ( var i=0; i<_stepList.length; i++ ) {
		if ( _stepList[i].className == "active" ) {
			_activeStep = i-1;
		}
	};
	
	if ( (_activeStep) < 0 ) message.show(message.info, "첫 화면 입니다.", ALERT);
	else {	// process 처리 부분
		
		_stepList[_activeStep+1].classList.remove("active");
		_stepList[_activeStep].classList.add("active");
	}
}

function getMeta() {
	// 메타정보 추출 전 파일이 정상적인지 확인.
	var _file = $('#textVisualizeFile')[0].files[0];
	if ( $('#textVisualizeFile').val() == "" ||  _file.name == undefined || _file.size == null || _file.name == "") {
		message.show(message.warning, "첨부된 파일이 없습니다.", ALERT);
		return false;
	} else if ( _file.size == undefined || _file.size == null || _file.size == "") {
		message.show(message.warning, "첨부된 파일 사이즈가 0 입니다.", ALERT);
		return false;
	}
	
	analysis.getMeta();
}

// 전체보기 or 추출 된 항목들만 볼 것인지
function switchOpt(pValue, pStepNum) {
	var _opt = pValue;
	
	// STEP 6 - 시맨틱
	if ( pStepNum == 6 ) {
		$('#step6Result').empty();
		drawContents(pStepNum, 0, _opt);
	} else if ( pStepNum == 7 ) {
		$('#step7Result').empty();
		drawContents(pStepNum, 0, _opt);
	}
}

// 셀렉트박스에서 "문장", "품사태깅", "시맥틱" 등을 선택하면 그에 맞는 값으로 셋팅
function chgDiv(pId, pValue) {
	var _target = $('#' + pId)[0];
	_target.innerHTML = pValue;
}

// drawContents(stepNum, offset, opt) : 각 스텝에 맞는 데이터를 그려준다.
// 1. stepNum : 각 스텝에 맞는 HTML을 그리기 위해 step번호를 받아온다.
// 2. offset : HTML을 몇번째 데이터부터 그릴지 정하는 값
// 3. opt : 옵션값
function drawContents(stepNum, offset, opt) {
	var _resultHTML = "";
	var _offset = Number(offset);
	var _limit = 0;
	
	if ( stepNum == 3 ) {              // 문단 나누기
		
		if ( isNaN(_offset) ) _offset = Number($('#step3_offset').val());
		
		_limit = (_offset + 10) < paraList.length ? (_offset + 10) : paraList.length;
		for ( var i=_offset; i<_limit; i++ ) {
			
			_resultHTML += "<div class=\"step-str\">";
			_resultHTML += "<strong>문단 " + (i+1) + "</strong> <span class=\"line\"></span>";
			_resultHTML += "</div>";
			_resultHTML += "<div class=\"stepinr\">";
			_resultHTML += "<div class=\"form-group\">";
			_resultHTML += "<label class=\"col-md-2 control-label\">문단</label>";
			_resultHTML += "<div class=\"col-md-10\">";
			_resultHTML += "<div class=\"showtxt disabled\">" + paraList[i].split("@@")[0] + "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			
			if ( (i+1) == paraList.length ) $('#step3More').css('display','none');
		}
		
		$('#step3_offset').val(_limit);
		$('#step3Result').append(_resultHTML);
	} else if ( stepNum == 4 ) {              // 문장 나누기
		
		if ( isNaN(_offset) ) _offset = Number($('#step4_offset').val());
		
		_limit = (_offset + 10) < sentenceList.length ? (_offset + 10) : sentenceList.length;
		for ( var i=_offset; i<_limit; i++ ) {
			
			_resultHTML += "<div class=\"step-str\">";
			_resultHTML += "<strong>문장 " + (i+1) + "</strong> <span class=\"line\"></span>";
			_resultHTML += "</div>";
			_resultHTML += "<div class=\"stepinr\">";
			_resultHTML += "<div class=\"form-group\">";
			_resultHTML += "<label class=\"col-md-2 control-label\">문장</label>";
			_resultHTML += "<div class=\"col-md-10\">";
			_resultHTML += "<div class=\"showtxt disabled\">" + sentenceList[i] + "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			
			if ( (i+1) == sentenceList.length ) $('#step4More').css('display','none');
		}
		$('#step4_offset').val(_limit);
		$('#step4Result').append(_resultHTML);
	} else if ( stepNum == 5 ) {              // POS TAG(형태소 분석)
		
		if ( isNaN(_offset) ) _offset = Number($('#step5_offset').val());
		
		_limit = (_offset + 10) < posTagList.length ? (_offset + 10) : posTagList.length;
		for ( var i=_offset; i<_limit; i++ ) {
			
			_resultHTML += "<div class=\"step-str\">";
			_resultHTML += "<strong>문장 " + (i+1) + "</strong> <span class=\"line\"></span>";
			_resultHTML += "</div>";
			_resultHTML += "<div class=\"stepinr\">";
			_resultHTML += "<div class=\"form-group\">";
			_resultHTML += "<label class=\"col-md-2 control-label\">문장</label>";
			_resultHTML += "<div class=\"col-md-10\">";
			_resultHTML += "<div class=\"showtxt disabled\">" + posTagList[i].sentence + "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			_resultHTML += "<div class=\"form-group\">";
			_resultHTML += "<div class=\"col-md-2\"></div>";
			_resultHTML += "<div class=\"col-md-10\">";
			_resultHTML += "<div class=\"showtxt\" style=\"border: 1px solid #ccc;\">" + posTagList[i].resultTag + "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			_resultHTML += "</div>";
			
			if ( (i+1) == posTagList.length ) $('#step5More').css('display','none');
		}
		$('#step5_offset').val(_limit);
		$('#step5Result').append(_resultHTML);
	} else if ( stepNum == 6 ) {              // POS TAG(형태소 분석)
		
		// 더보기 버튼 클릭 시 시작 번호와 opt값 셋팅
		if ( isNaN(_offset) ) {
			_offset = Number($('#step6_offset').val());
			opt = swSymantic;
		}
		
		_limit = (_offset + 10) < symanticList.length ? (_offset + 10) : symanticList.length;
		for ( var i=_offset; i<_limit; i++ ) {
			
			var _subSymanticList = symanticList[i];
			var _symanticResult = "";
			var _sentence = sentenceList[i];
			
			if ( opt && _subSymanticList.result.length > 0 ) {	// 매칭 된 것만 보기
				
				_resultHTML += "<div class=\"step-str\">";
				_resultHTML += "<strong>문장 " + (i+1) + "</strong> <span class=\"line\"></span>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"stepinr\">";
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\">";
				_resultHTML += "<select class=\"form-control input-sm\" onchange=\"chgDiv('step6Div" + i + "', this.value);\">";
				_resultHTML += "<option value=\"" + _sentence + "\">문장</option>";
				_resultHTML += "<option value=\"" + posTagList[i].resultVal + "\">품사태깅</option>";
				_resultHTML += "</select>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt disabled\" id=\"step6Div" + i + "\">" + _sentence + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\"></div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt\" style=\"border: 1px solid #ccc;\">" + symanticList[i].pattern_hilight_tag + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "</div>";
			} 
			else if ( !opt ){	// 전체 보기
				if ( _subSymanticList.result.length > 0 ) {
					for ( var j=0; j<_subSymanticList.result.length; j++ ) {
						_symanticResult += _subSymanticList.result[j].pattern_hilight + " >> <span class='symantic'>" + _subSymanticList.result[j].hitrange + "</span><br>";
						
						_sentence = _sentence.replace(_subSymanticList.result[j].hitrange, "<span class='sentence-hilight'>" + _subSymanticList.result[j].hitrange + "</span>");
					}
				}
				
				// 시맨틱 패턴이 매칭 된 부분을 하이라이팅 처리한 것을 새로운 key를 추가하여 값을 담아둔다. 
				// 재사용 하기 위함
				symanticList[i].pattern_hilight_tag = _symanticResult == undefined ? "" : _symanticResult;
				
				_resultHTML += "<div class=\"step-str\">";
				_resultHTML += "<strong>문장 " + (i+1) + "</strong> <span class=\"line\"></span>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"stepinr\">";
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\">";
				_resultHTML += "<select class=\"form-control input-sm\" onchange=\"chgDiv('step6Div" + i + "', this.value);\">";
				_resultHTML += "<option value=\"" + _sentence + "\">문장</option>";
				_resultHTML += "<option value=\"" + posTagList[i].resultVal + "\">품사태깅</option>";
				_resultHTML += "</select>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt disabled\" id=\"step6Div" + i + "\">" + _sentence + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\"></div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt\" style=\"border: 1px solid #ccc;\">" + symanticList[i].pattern_hilight_tag + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "</div>";
			}
			
			swSymantic = opt;	// 스위치 버튼 상태값
			if ( (i+1) == _symanticResult.length ) $('#step6More').css('display','none');
		}
		$('#step6_offset').val(_limit);
		$('#step6Result').append(_resultHTML);
	} else if ( stepNum == 7 ) {              // POS TAG(형태소 분석)
		
		// 더보기 버튼 클릭 시 시작 번호와 opt값 셋팅
		if ( isNaN(_offset) ) {
			_offset = Number($('#step7_offset').val());
			opt = swNer;
		}
		
		_limit = (_offset + 10) < nerList.length ? (_offset + 10) : nerList.length;
		for ( var i=_offset; i<_limit; i++ ) {
			
			var _subSymanticList = symanticList[i];
			var _symanticResult = "";
			var _sentence = sentenceList[i];
			
			// Ner 처리 변수
			var _nerList = nerList[i].split("@@");
			var _symNer = "";
			var _actNer = "";
			
			// nerList의 값에서 @@@@ 값이 있으면 현상과 대책에 대한 개체명이 없다고 정의.
			if ( opt && (_nerList[0] != "" || _nerList[1] != "") ) {	// 매칭 된 것만 보기
				
				// split한 _nerList의 값 중 0, 1번째의 값이 현상과 대책
				for ( var j=0; j<2; j++ ) {
					if ( _nerList[j].indexOf("|") > -1 ) {
						var _tempNer = _nerList[j].split("|");
						for ( var k=0; k<_tempNer.length; k++ ) {
							
							if ( _tempNer[k] != "" ) _sentence = _sentence.replace(_tempNer[k], "<span class='ner-hilight'>" + _tempNer[k] + "</span>");
						}
					} else {
						if ( _nerList[j] != "" ) _sentence = _sentence.replace(_nerList[j], "<span class='ner-hilight'>" + _nerList[j] + "</span>");
					}
				}
				
				_symNer = _nerList[0];
				_actNer = _nerList[1];
				
				_resultHTML += "<div class=\"step-str\">";
				_resultHTML += "<strong>문장 " + (i+1) + "</strong> <span class=\"line\"></span>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"stepinr\">";
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\">";
				_resultHTML += "<select class=\"form-control input-sm\" onchange=\"chgDiv('step7Div" + i + "', this.value);\">";
				_resultHTML += "<option value=\"" + _sentence + "\">문장</option>";
				_resultHTML += "<option value=\"" + posTagList[i].resultVal + "\">품사태깅</option>";
				_resultHTML += "<option value=\"" + symanticList[i].pattern_hilight_tag + "\">시맨틱</option>";
				_resultHTML += "</select>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt disabled\" id=\"step7Div" + i + "\">" + _sentence + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\"></div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt\" style=\"border: 1px solid #ccc;\">" + _symNer + "<br>" + _actNer + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "</div>";
			} 
			else if ( !opt ){	// 전체 보기
				
				if ( nerList[i].indexOf("@@") ) {
					var _nerList = nerList[i].split("@@");
					
					// split한 _nerList의 값 중 0, 1번째의 값이 현상과 대책
					for ( var j=0; j<2; j++ ) {
						if ( _nerList[j].indexOf("|") > -1 ) {
							var _tempNer = _nerList[j].split("|");
							for ( var k=0; k<_tempNer.length; k++ ) {
								if ( _tempNer[k] != "" ) _sentence = _sentence.replace(_tempNer[k], "<span class='ner-hilight'>" + _tempNer[k] + "</span>");
							}
						} else {
							if ( _nerList[j] != "" ) _sentence = _sentence.replace(_nerList[j], "<span class='ner-hilight'>" + _nerList[j] + "</span>");
						}
					}
					
					_symNer = _nerList[0];
					_actNer = _nerList[1];
				}
				
				_resultHTML += "<div class=\"step-str\">";
				_resultHTML += "<strong>문장 " + (i+1) + "</strong> <span class=\"line\"></span>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"stepinr\">";
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\">";
				_resultHTML += "<select class=\"form-control input-sm\" onchange=\"chgDiv('step7Div" + i + "', this.value);\">";
				_resultHTML += "<option value=\"" + _sentence + "\">문장</option>";
				_resultHTML += "<option value=\"" + posTagList[i].resultVal + "\">품사태깅</option>";
				_resultHTML += "<option value=\"" + symanticList[i].pattern_hilight_tag + "\">시맨틱</option>";
				_resultHTML += "</select>";
				_resultHTML += "</div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt disabled\" id=\"step7Div" + i + "\">" + _sentence + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "<div class=\"form-group\">";
				_resultHTML += "<div class=\"col-md-2\"></div>";
				_resultHTML += "<div class=\"col-md-10\">";
				_resultHTML += "<div class=\"showtxt\" style=\"border: 1px solid #ccc;\">" + _symNer + "<br>" + _actNer + "</div>";
				_resultHTML += "</div>";
				_resultHTML += "</div>";
				
				_resultHTML += "</div>";
			}
			
			swNer = opt;	// 스위치 버튼 상태값
//			if ( (i+1) == nerList.length ) $('#step7More').css('display','none');
		}
		$('#step7_offset').val(_limit);
		$('#step7Result').append(_resultHTML);
	}
}