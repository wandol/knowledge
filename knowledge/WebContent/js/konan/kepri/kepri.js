/**
 * Search 페이지에 대한 js
 * 
 * 변수 명명 규칙 : 1. 파라미터의 경우 앞에 p를 붙인다
 *              2. HTML을 만드는 변수의 경우 끝에 HTML을 붙인다
 *              3. 내부변수의 경우 앞에 _를 붙인다.
 */
//$(document).mouseup(function (e){
//  var LayerPopup = $("#detailReport");
//  var LayerPFPopup = $("#detailPFReport");
//  var memeModal = $("#memeModal");
//  
//  if(memeModal.has(e.target).length === 0){
//	  memeModal.dialog("close");
//  }
//  if(LayerPFPopup.has(e.target).length === 0){
//	  LayerPFPopup.dialog("close");
//  }
//  if(LayerPopup.has(e.target).length === 0){
//    LayerPopup.dialog("close");
//  }
//});

var CATEGORY;
var PARTNAMEKEY;
$(document).ready(function() {
	
	CATEGORY = $('#his_category').val(); // 페이지 로딩 시 history form에서 카테고리를 가져온다.	
	PARTNAMEKEY = getPartName(CATEGORY); // 각 분야별로 partName KEY가 다름
	
	// 검색 옵션 초기화
	function drawOption(optData) {
		
		optData.isPF = "N";
		optData.cate = $('#his_category').val();
		
		ajax.data = optData;
		ajax.type = "GET";
		ajax.url = context + "/comm/getOptionList.do";
		
		ajax.success = function(data) {
			var _partName = getPartName(optData.cate);
			
			if ( data.isPartName == "success" && data.partNameList.length > 0 ) { commonDrawOption("partName", data.partNameList, _partName, optData.partName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isSymptom == "success" && data.symptomKwdList.length > 0 ) { commonDrawOption("symptomKwd", data.symptomKwdList, "SYMPTOM_KWD", optData.symptomKwd); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isAction == "success" && data.actionKwdList.length > 0 ) { commonDrawOption("actionKwd", data.actionKwdList, "ACTION_KWD", optData.actionKwd); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값

			if ( data.isPowerComp == "success" && data.powerCompList.length > 0 ) { commonDrawOption("powerComp", data.powerCompList, "POWER_COMP_NM", optData.powerCompName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isPowerSt == "success" && data.powerStList.length > 0 ) { commonDrawOption("powerSt", data.powerStList, "POWER_ST_NM", optData.powerStName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isStNo == "success" && data.stNoList.length > 0 ) { commonDrawOption("stNo", data.stNoList, "ST_NO", optData.stNo); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
		};
		
		ajax.error = function(xhr, ajaxOptions) {
			if ( xhr.status == 901 ) {				
				message.show(MSG_TTL.NOTI, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			}
		}
		
		// ajax 실행
		$.ajax(ajax);
	};
	
	// 발전사 선택 시
	$('#powerComp').change(function() {
		// 선택한 발전사
		var _powerComp = this.value;
		optionData.powerCompName = _powerComp;
		optionData.powerStName = "all";
		optionData.stNo = "all";
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawOption(optionData);
	});
	
	// 사업소 선택 시
	$('#powerSt').change(function() {
		// 선택한 사업소
		var _powerSt = this.value;
		optionData.powerCompName = $('#powerComp').val();
		optionData.powerStName = _powerSt;
		optionData.stNo = "all";
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawOption(optionData);
	});
	
	// 호기 선택 시
	$('#stNo').change(function() {
		// 선택한 호기
		var _stNo = this.value;
		optionData.powerCompName = $('#powerComp').val();
		optionData.powerStName = $('#powerSt').val();
		optionData.stNo = _stNo;
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawOption(optionData);
	});
	
	// 진단 부위 선택 시
	$('#partName').change(function() {
		// 선택한 부위
		var _partName = this.value;
		optionData.powerCompName = $('#powerComp').val();
		optionData.powerStName = $('#powerSt').val();
		optionData.stNo = $('#stNo').val();
		optionData.partName = _partName;
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawOption(optionData);
	});
	
	// 현상 선택 시
	$('#symptomKwd').change(function() {
		// 선택한 현상
		var _symptomKwd = this.value;
		optionData.powerCompName = $('#powerComp').val();
		optionData.powerStName = $('#powerSt').val();
		optionData.stNo = $('#stNo').val();
		optionData.partName = $('#partName').val();
		optionData.symptomKwd = _symptomKwd;
		optionData.actionKwd = "all";
		
		drawOption(optionData);
	});
	
	// 대책 선택 시
	$('#actionKwd').change(function() {
		// 선택한 부위
		var _actionKwd = this.value;
		optionData.powerCompName = $('#powerComp').val();
		optionData.powerStName = $('#powerSt').val();
		optionData.stNo = $('#stNo').val();
		optionData.partName = $('#partName').val();
		optionData.symptomKwd = $('#symptomKwd').val();
		optionData.fields = $('#srcField').val();
		optionData.actionKwd = _actionKwd;
		
		drawOption(optionData);
	});
		
	// 상세화면 Modal 셋팅
	$("#detailReport").dialog({
		autoOpen : false,
		width : "1060px",
		resizable : false,
		modal : true,
		title : "<div class='widget-header'><h4>기술문서 탐색 상세보기</h4></div>"
	});
	
	// 유사도 Modal 셋팅
	$("#memeModal").dialog({
		autoOpen : false,
		width : 1000,
		resizable : false,
		modal : true,
		buttons : [{
			html : "<i class='fa fa-times'></i>&nbsp; 닫기",
			"class" : "btn btn-default",
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
	
	// 페이지 로딩 시 history form에서 데이터를 불러온다. 
	// 1. option 데이터 조회 조건
	// 2. option 데이터 새로 select box에 그릴 때 selected 상태 처리용 
	optionData.partName = $('#his_partName').val();
	optionData.symptomKwd = $('#his_symptomKwd').val();
	optionData.actionKwd = $('#his_actionKwd').val();
	optionData.powerCompName = $('#his_powerComp').val();
	optionData.powerStName = $('#his_powerSt').val();
	optionData.stNo = $('#his_stNo').val();
	
	// 선택한 옵션 화면에 노출
	// commonSelectOption(key, value, menu)
	commonSelectOption("POWER_COMP_NM", optionData.powerCompName, "search");
	commonSelectOption("POWER_ST_NM", optionData.powerStName, "search");
	commonSelectOption("ST_NO", optionData.stNo, "search");
	
	// 검색 옵션 초기화
	drawOption(optionData);
	
});

// 기술문서 탐색 > 상세탐색
function detailSearch(){
	
	var _isSearch = true;
	var _kwd = $('#kwd').val();
	
	$('#his_kwd').val(_kwd);
	$('#pageNum').val(1);
	$('#his_fields').val($('#srcFields option:selected').val());
	$('#his_powerComp').val($('#powerComp').val());
	$('#his_powerSt').val($('#powerSt').val());
	$('#his_stNo').val($('#stNo').val());
	$('#his_partName').val($('#partName').val());
	$('#his_symptomKwd').val($('#symptomKwd').val());
	$('#his_actionKwd').val($('#actionKwd').val());
	
	var _startDate = setDateFormat($('#startDate').val());
	var _endDate = setDateFormat($('#endDate').val());
	
	// 날짜 데이터가 있을 경우에만
	if ( (_startDate != "" && _startDate != null && _startDate != undefined) 
			&& (_endDate != "" && _endDate != null && _endDate != undefined) ) {
		
		_isSearch = validDateFormat(_startDate); // 날짜 포맷검사(yyyy.mm.)
		_isSearch = validDateFormat(_endDate); // 날짜 포맷검사(yyyy.mm.)
		
	}
	
	if ( _isSearch ) {
		$('#his_startPublishYm').val(_startDate);
		$('#his_endPublishYm').val(_endDate);
		$('#historyForm').attr("action","search.do");
		$('#historyForm').submit();
	}
}

// 기술문서 탐색 > 원본보기
function viewOriginFilebody(id) {
	var _cate = $('#his_category').val();
	
	if ( id != null && id != "" && id != undefined ) {
		
		ajax.url = context + "/kepri/filebody/" + _cate + "/" + id;
		ajax.type = "GET";
		ajax.success = function(data) {
			if ( data != null ) {
				
				if ( data.resultMsg == "etc" ) {
					messageBody = "등록되지 않은 카테고리 입니다.";
					message.show(message.noti, messageBody, ALERT);
				} else if ( data.resultMsg == "empty" ) {
					messageBody = "파일 원문이 없습니다.";
					message.show(message.noti, messageBody, ALERT);
				} else {
					var _ttl = "원본보기";
					var _body = "<tr>";
						_body += "<td style='width: 10%';>원본보기</td>";
					    _body += "<td>" + data.glossary.replace(/(\n|\r\n)/g, '<br>') + "</td>";
					    _body += "</tr>";
					
					modalOption.width = "1000";
					modalOption.height = "600";
					commonModal.show(_ttl, _body, modalOption, ALERT);
					
					$('#commonModal').scrollTop(0); // Modal창 scroll 초기화
					$('[data-toggle="tooltip"]').tooltip(); 
				}
				
			} else {
				messageBody = "분석 된 파일 원문이 없습니다.";
				message.show(message.noti, messageBody, ALERT);
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
	} else {
		messageBody = "KEY is not found.";
		message.show(message.noti, messageBody, ALERT);
	}
}

// 기술문서 탐색 > 상세보기
function detailReport(id) {
	var _cate = $('#his_category').val();
	
	// 상세페이지 초기화
	emptyDetailReport();
	
	if ( id != null && id != "" && id != undefined ) {
		
		ajax.url = context + "/kepri/detailReport/" + _cate + "/" + id;
		ajax.type = "GET";
		ajax.success = function(data) {
			
			if ( data.message == "ok" ) {
				var _reportNM = data.meta.report_nm;
				var _powerNavi = data.meta.power_comp_nm + " " + data.meta.power_st_nm + " " + data.meta.st_no;
				var _category = getCategory(data.meta.category) + " " + getRepoKind(data.meta.category, data.meta.repo_kind, data.meta.repo_kind2);
				var _reporter = data.meta.reporter;
				var _problemFocus = data.partList.length > 0 ? makeProblemFocusHTML(data.meta.category, data.partList) : "";
				
				$('#dReportNm').html(_reportNM);
				$('#dPowerComp').html(_powerNavi);
				$('#dCate').html(_category);
				$('#dReporter').html(_reporter);
				$('#dProblemFocus').html(_problemFocus);
				
				$('#detailReport').dialog('open');
				
				// 데이터가 있을 때만 slider 생성
				if ( _problemFocus != "" ) {
					destroySlider();
					slider();
				} else {
					destroySlider();
				}
				
				// 상세페이지 연관현상 영역
				makeSymptomChart(data.meta.category, id);
				
				return false;
			}
		};
		
		ajax.error = function(xhr, ajaxOptions) {
			if ( xhr.status == 901 ) {
				message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			}
		};
		
		// ajax 실행
		$.ajax(ajax);
	}
}

//기술문서 탐색 > 상세화면 > 지식 영역 처리
//데이터 HTML코드로 변환
function makeProblemFocusHTML(category, pData) {
	
	var _problemFocusHTML = "";
	var _partNameKey = getPartName(category);
	var _partMidKey = getPartMid(category);
	
	if (category == "BOILER" || category == "GT_TURBINE" || category == "ST_TURBINE" || category == "GEN_PREV") {
		
	
		// Problem Focus 데이터가 있을 경우
		if ( pData.length > 0 ) {
			
			for ( var i=0; i<pData.length; i++ ) {
				
				var _data = pData[i];
				var _tImgFiles = "";
				var _imgFile = "";
				var _imgTxt = "";
				if ( _data.IMG_FILES.indexOf("|") > -1 || _data.IMG_FILES.indexOf("`") > -1  ) {
					_tImgFiles = _data.IMG_FILES.split(/\||`/);
					_imgFile = _tImgFiles[0];
					_imgTxt = _data.IMG_TXTS.split("|")[0];
				} else {
					_imgFile = _data.IMG_FILES;
					_imgTxt = _data.IMG_TXTS;
				}
				
				if ( isInt(i/3) ) {	// i값을 3으로 나누어 정수이면 true
					if ( _problemFocusHTML != "" ) _problemFocusHTML += "</ul></div>";
					_problemFocusHTML += "<div>";
					_problemFocusHTML += "<ul class=\"listdt3\">";
					_problemFocusHTML += "<li>";
					_problemFocusHTML += "<dl>";
					_problemFocusHTML += "<dt>";
					
					if ( _imgFile != "" ) _problemFocusHTML += "<img src=\"../comm/displayImg.do?fileName=" + _imgFile + "\" alt=\"" + _imgTxt + "\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src, this.alt);\">";
					
					_problemFocusHTML += "</dt>";
					_problemFocusHTML += "<dd>";
					_problemFocusHTML += "<ul class=\"detailLink\" onclick=\"javascript:detailPFReport('" + _data.MD5_KEY + "', '" + _data.GUBUN_NO + "')\" title=\"지식DB 상세화면 보기\">";
					_problemFocusHTML += "<li>진단부위 : " + _data[_partNameKey].replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "<li>위치 : " + _data[_partMidKey].replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "<li>현상 : " + _data.SYMPTOM_KWD.replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "<li>대책 : " + _data.ACTION_KWD.replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "</ul>";
					_problemFocusHTML += "</dd>";
					_problemFocusHTML += "</dl>";
					_problemFocusHTML += "</li>";
					
				} else {
					_problemFocusHTML += "<li>";
					_problemFocusHTML += "<dl>";
					_problemFocusHTML += "<dt>";
					
					if ( _imgFile != "" ) _problemFocusHTML += "<img src=\"../comm/displayImg.do?fileName=" + _imgFile + "\" alt=\"" + _imgTxt + "\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src, this.alt);\">";
					
					_problemFocusHTML += "</dt>";
					_problemFocusHTML += "<dd>";
					_problemFocusHTML += "<ul onclick=\"javascript:detailPFReport('" + _data.MD5_KEY + "', '" + _data.GUBUN_NO + "')\">";
					_problemFocusHTML += "<li>진단부위 : " + _data[_partNameKey].replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "<li>위치 : " + _data[_partMidKey].replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "<li>현상 : " + _data.SYMPTOM_KWD.replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "<li>대책 : " + _data.ACTION_KWD.replace(/_/g, " ").replace(/\|/g, ",  ") + "</li>";
					_problemFocusHTML += "</ul>";
					_problemFocusHTML += "</dd>";
					_problemFocusHTML += "</dl>";
					_problemFocusHTML += "</li>";
				}
			}
			var _checkLastHTML = _problemFocusHTML.substr(_problemFocusHTML.length-6, 6);
			if ( _checkLastHTML.indexOf("</div>") < 0 ) _problemFocusHTML += "</ul></div>";
		}
	}
	
	return _problemFocusHTML;
}

//기술문서 탐색 > 상세화면 > 연관현상 처리
function makeSymptomChart(pCate, pId) {
	
	ajax.url = context + "/kepri/symptom/" + pCate + "/" + pId;
	ajax.type = "GET";
	ajax.success = function(data) {
		
		if ( data.message == "ok" ) {
			var _jsonData = [];
			
			var _symptomData = data.symptomList.result;
			var _symptomId = "dSymptom";
			if ( _symptomData.length > 0 ) {
				for ( var i=0; i<_symptomData.length; i++ ) {
					var _obj = {};
					_obj.id = i;
					_obj.text = _symptomData[i].SYMPTOM_KWD.replace("_", "");
					_obj.size = _symptomData[i].count;
					_obj.category = i; 
					_jsonData.push(_obj);
				}
				
				drawDavif.cloudChart(_jsonData, _symptomId);
			}
		}
			
	};
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {
			message.show(message.noti, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = $('#his_loginUrl').val();
			});
		}
	};
	
	// ajax 실행
	$.ajax(ajax);
	
}


// 기술문서 탐색 > 유사문서 클릭 시 클릭 한 문서와 유사한 문서를 3개를 가져온다.
// 화면에 노출되는 것은 유사도가 50%이상인 것만 노출
function getMemechecker(pId) {
	var _category = $('#his_category').val();
	
	$.ajax({
		url: context + "/comm/memechecker/" + _category + "/" + pId,
		type: "GET",
		dataType: "json",
        beforeSend: function () {
        	$('#loading').show();
        },
		success: function(data) {
			var _tableTag = "";
			
			if ( data.message == "success" ) {
				var _data = data.result;
				var _cate = data.cate;
				
				for ( var i=0; i<_data.length; i++ ) {
					
					var _score = Math.floor(_data[i].score / 10000 * 100);
					
					// 유사도가 50% 이상인 항목만
					if ( _score >= 50 ) {
						var _memCate = _data[i].docId.split("_")[0];
						var _md5Key = _data[i].docId.split("_")[1];
						
						_tableTag += "<tr>";
						_tableTag += "<td>" + (i+1) + "</td>";
						_tableTag += "<td class=\"tleft\"><a href=\"#\" onclick=\"javascript:fileDownload('" + _data[i].filename + "','" + _cate + "','" + _md5Key + "', '유사문서');\">" + _data[i].filename + "</a></td>";
						_tableTag += "<td>" + Math.floor(_data[i].score / 10000 * 100) + "%</td>";
						_tableTag += "</tr>";
					}
				}
				
				if ( _tableTag == "" ) {
					message.show(message.warning, "50%이상의 유사도를 가진 파일이 없습니다.", ALERT);
				} else {
					// 유사문서가 있으면 유사문서 modal show
					$('#memeTable tbody').html(_tableTag);
					$('#memeModal').dialog('open');
				}
			} else {
				message.show(message.warning, "유사문서가 없습니다.", ALERT);
			}
		},
		error: function(xhr, textStatus, error) {
			if ( xhr.status == 901 ) {
				message.show(MSG_TTL.NOTI, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			} else {
				message.show(message.warning, "유사문서가 없습니다.", ALERT);
			}
		},
		complete: function() {
			$('#loading').hide();
		}
	});
}

function emptyDetailReport() {
	$('#dReportNm').empty();
	$('#dPowerComp').empty();
	$('#dCate').empty();
	$('#dReporter').empty();
	$('#dProblemFocus').empty();
	$('#dSymptom').empty();
	destroySlider();
}