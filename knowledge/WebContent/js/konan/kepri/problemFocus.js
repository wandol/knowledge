/**
 * Problem Focus 페이지에 대한 js
 * pfSearch, pfAnalysis
 * 
 * 변수 명명 규칙 : 1. 파라미터의 경우 앞에 p를 붙인다
 *              2. HTML을 만드는 변수의 경우 끝에 HTML을 붙인다
 *              3. 내부변수의 경우 앞에 _를 붙인다.
 */

var CATEGORY;
var PARTNAMEKEY;
$(document).ready(function() {
	
	CATEGORY = $('#his_category').val(); // 페이지 로딩 시 history form에서 카테고리를 가져온다.	
	PARTNAMEKEY = getPartName(CATEGORY); // 각 분야별로 partName KEY가 다름
	
	// 검색 옵션 초기화
	function drawPfOption(optData) {
		
		optData.isPF = "Y";
		optData.cate = $('#his_category').val();
		
		ajax.data = optData;
		ajax.type = "GET";
		ajax.url = context + "/comm/getOptionList.do";
		
		ajax.success = function(data) {
			
			var _partName = getPartName(optData.cate);
			
			if ( data.isPartName == "success" && data.partNameList.length > 0 ) { commonDrawOption("pfPartName", data.partNameList, _partName, optData.partName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isSymptom == "success" && data.symptomKwdList.length > 0 ) { commonDrawOption("pfSymptomKwd", data.symptomKwdList, "SYMPTOM_KWD", optData.symptomKwd); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isAction == "success" && data.actionKwdList.length > 0 ) { commonDrawOption("pfActionKwd", data.actionKwdList, "ACTION_KWD", optData.actionKwd); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값

			if ( data.isPowerComp == "success" && data.powerCompList.length > 0 ) { commonDrawOption("pfPowerComp", data.powerCompList, "POWER_COMP_NM", optData.powerCompName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isPowerSt == "success" && data.powerStList.length > 0 ) { commonDrawOption("pfPowerSt", data.powerStList, "POWER_ST_NM", optData.powerStName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isStNo == "success" && data.stNoList.length > 0 ) { commonDrawOption("pfStNo", data.stNoList, "ST_NO", optData.stNo); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
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
	$('#pfPowerComp').change(function() {
		// 선택한 발전사
		var _powerComp = this.value;
		optionData.powerCompName = _powerComp;
		optionData.powerStName = "all";
		optionData.stNo = "all";
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawPfOption(optionData);
	});
	
	// 사업소 선택 시
	$('#pfPowerSt').change(function() {
		// 선택한 사업소
		var _powerSt = this.value;
		optionData.powerCompName = $('#pfPowerComp').val();
		optionData.powerStName = _powerSt;
		optionData.stNo = "all";
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawPfOption(optionData);
	});
	
	// 호기 선택 시
	$('#pfStNo').change(function() {
		// 선택한 호기
		var _stNo = this.value;
		optionData.powerCompName = $('#pfPowerComp').val();
		optionData.powerStName = $('#pfPowerSt').val();
		optionData.stNo = _stNo;
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawPfOption(optionData);
	});
	
	// 진단 부위 선택 시
	$('#pfPartName').change(function() {
		// 선택한 부위
		var _partName = this.value;
		optionData.powerCompName = $('#pfPowerComp').val();
		optionData.powerStName = $('#pfPowerSt').val();
		optionData.stNo = $('#pfStNo').val();
		optionData.partName = _partName;
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawPfOption(optionData);
	});
	
	// 현상 선택 시
	$('#pfSymptomKwd').change(function() {
		// 선택한 현상
		var _symptomKwd = this.value;
		optionData.powerCompName = $('#pfPowerComp').val();
		optionData.powerStName = $('#pfPowerSt').val();
		optionData.stNo = $('#pfStNo').val();
		optionData.partName = $('#pfPartName').val();
		optionData.symptomKwd = _symptomKwd;
		optionData.actionKwd = "all";
		
		drawPfOption(optionData);
	});
	
	// 대책 선택 시
	$('#pfActionKwd').change(function() {
		// 선택한 부위
		var _actionKwd = this.value;
		optionData.powerCompName = $('#pfPowerComp').val();
		optionData.powerStName = $('#pfPowerSt').val();
		optionData.stNo = $('#pfStNo').val();
		optionData.partName = $('#pfPartName').val();
		optionData.symptomKwd = $('#pfSymptomKwd').val();
		optionData.actionKwd = _actionKwd;
		
		drawPfOption(optionData);
	});
	
	// 상세화면 Modal창 초기화
	$('#detailPFReport').dialog({
		autoOpen : false,
		width : "1060px",
		resizable : false,
		modal : true,
		buttons : [{
		}, {
		}]
	});
	
	// 상세화면 Modal창 초기화
	$('#detailPFKepriReport').dialog({
		autoOpen : false,
		width : "1060px",
		resizable : false,
		modal : true,
		buttons : [{
		}, {
		}]
	});
	
	// 지식 DB 탐색 > 성능 데이터 클릭
	$('#pfPerformTBL tbody tr').click(function() {
		var _bodyTR = $(this)[0].children;
		var _headTR = $('#pfPerformTBL thead').find('tr')[0].children;
		
		var _performData = [];
		
		
		// 4번째 부터 차트 데이터
		for ( var i=4; i<_bodyTR.length; i++ ) {
			var _obj = {};
			
			_obj.id = i;
			_obj.name = _headTR[i].innerText;
			_obj.value = _bodyTR[i].innerText == "-" ? 0 : Number(_bodyTR[i].innerText.replace(",", ""));
			_performData.push(_obj);
		}
		
		var _makeTitle = "성능 차트";
		var _makeBody = "<tbody>";	
		_makeBody += "<tr>";
		_makeBody += "<td id=\"pfPerformChart\">";
		
		_makeBody += "</td>";
		_makeBody += "</tr>";
		_makeBody += "</tbody>";
		
		COMMON_MODAL_OPTION.width = 1500;
		COMMON_MODAL_OPTION.height = 500;
		commonModal.show(_makeTitle, _makeBody, COMMON_MODAL_OPTION, ALERT);
		
		drawDavif.barChart(_performData, "pfPerformChart");
	});
	
	// 지식 DB 탐색 > 발전기 절연진단
	$('#pfGenInsTBL tbody tr').click(function() {
		var _bodyTR = $(this)[0].children;
		var _headTR = $('#pfGenInsTBL thead').find('tr')[0].children;
		
		var _genInsData = [];
		
		
		for ( var i=6; i<11; i++ ) {
			var _obj = {};
			
			_obj.id = i;
			_obj.name = _headTR[i].innerText;
			_obj.value = _bodyTR[i].innerText == "-" ? 0 : (Number(_bodyTR[i].innerText.replace(",", "")) / 2000) * 100;
			_genInsData.push(_obj);
		}
		
		var _makeTitle = "발전기 절연진단 차트";
		var _makeBody = "<tbody>";	
		_makeBody += "<tr>";
		_makeBody += "<td id=\"pfGenInsChart\">";
		
		_makeBody += "</td>";
		_makeBody += "</tr>";
		_makeBody += "</tbody>";
		
		COMMON_MODAL_OPTION.width = 900;
		COMMON_MODAL_OPTION.height = 500;
		commonModal.show(_makeTitle, _makeBody, COMMON_MODAL_OPTION, ALERT);
		
		drawDavif.barChart(_genInsData, "pfGenInsChart");
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
	commonSelectOption(PARTNAMEKEY, optionData.partName, "pf");
	commonSelectOption("SYMPTOM_KWD", optionData.symptomKwd, "pf");
	commonSelectOption("ACTION_KWD", optionData.actionKwd, "pf");
	
	// 검색 옵션 초기화
	drawPfOption(optionData);
	
});

//지식DB 탐색 > 상세탐색
function detailPFSearch(){
	var _kwd = $('#pfKwd').val();
	
	$('#his_kwd').val(_kwd);
	//	$('#recent').recent('add', kwd); // 최근 검색어 추가
	$('#pageNum').val(1);
	$('#his_powerComp').val($('#pfPowerComp').val());
	$('#his_powerSt').val($('#pfPowerSt').val());
	$('#his_stNo').val($('#pfStNo').val());
	$('#his_partName').val($('#pfPartName').val());
	$('#his_symptomKwd').val($('#pfSymptomKwd').val());
	$('#his_actionKwd').val($('#pfActionKwd').val());
	$('#his_startPublishYm').val($('#pfStartDate').val());
	$('#his_endPublishYm').val($('#pfEndDate').val());
	
	$('#historyForm').attr("action","pfSearch.do");
	$('#historyForm').submit();
}

//지식DB 탐색 > 상세화면
function detailPFReport(pId, pGubunNo) {
	var _cate = $('#his_category').val();
	var _pageName = '지식DB';
	
	// 상세페이지 초기화
	emptyDetailPfReport();
	
	if ( pId != null && pId != "" && pId != undefined ) {
		
		ajax.data = {};
		ajax.type = "GET";
		ajax.url = context + "/problemFocus/detail/" + _cate + "/" + pId + "_" + pGubunNo;
		ajax.success = function(data) {
			
			if ( data.message == "ok" ) {
				var _data = data.result;
				var tempString =  _data.result[0].PART2BODY.length > 400 ? "  ....." : "";
				var _pfPowerNavi = _data.result[0].POWER_COMP_NM + " " + _data.result[0].POWER_ST_NM + " " + _data.result[0].ST_NO;
				var _pfPublishYM = _data.result[0].PUBLISH_YM;
				var _pfPartName = splitPfKeyword(_data.result[0][PARTNAMEKEY], 'ty1');
				var _pfPartMid = splitPfKeyword(_data.result[0].PART_MID, 'ty2');
				var _pfSymptomKwd = splitPfKeyword(_data.result[0].SYMPTOM_KWD, 'ty5');;
				var _pfActionKwd = splitPfKeyword(_data.result[0].ACTION_KWD, 'ty5');;
				var _pfReportNM = _data.result[0].REPORT_NM;
				var _pfContent = _data.result[0].PART2BODY.substr(0,400) + tempString;
				var _pfImgFiles = makePfImgFilesHTML(_data.result[0].IMG_FILES, _data.result[0].IMG_TXTS);
				
				var _pfFileName = _data.result[0].FILENAME;
				var _pfMd5Key = _data.result[0].MD5_KEY;
				
				$('#pfDPowerNavi').html(_pfPowerNavi);
				$('#pfDPublishYM').html(_pfPublishYM);
				$('#pfDPartName').html(_pfPartName + _pfPartMid);
				$('#pfDSymptomKwd').html(_pfSymptomKwd.replace(/_/g, " "));
				$('#pfDActionKwd').html(_pfActionKwd.replace(/_/g, " "));
				$('#pfDReportNM').html(makeFileDownLinkHtml(_pfFileName,_cate,_pfMd5Key,_pfReportNM));
				$('#pfDContent').html(_pfContent);
				if ( _pfImgFiles != "" && _pfImgFiles != null ) {
					var imgArea = document.getElementById("modalImgId");
					imgArea.hidden = false;
					$('#pfImgSlide').html(_pfImgFiles);
				}else{
					var imgArea = document.getElementById("modalImgId");
					imgArea.hidden = true;
				}

				$('#pfCommentAdd').html("<button class=\"btn btn-default btn-primary btn-full\" type=\"button\" onclick=\"javascript:detailPFCommentAdd('" + _cate + "','" + _data.result[0].MD5_KEY + "', '" + _data.result[0].GUBUN_NO + "')\">등록</button>")
				
				var _pftitle = "<div class='widget-header'><h4>" + getCategory(_cate) + " 상세 내역</h4></div>";

				$('#detailPFReport').dialog({title: _pftitle});
				$('#detailPFReport').dialog('open');
				
				// img 데이터가 있을 때만 slider 생성
				if ( _pfImgFiles != "" && _pfImgFiles != null ) {
					if ( pfSlider != undefined ) { pfSlider.destroySlider(); }
					slider2();
				}
				
				detailPFCommentList(_cate, pId, pGubunNo);
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
};

//지식DB 탐색 > 누설흡습 상세화면
function detailPFKepriReport(pId) {
	var _cate = $('#his_category').val();
	
	if ( pId != null && pId != "" && pId != undefined ) {
		
		ajax.data = {};
		ajax.type = "GET";
		ajax.url = context + "/problemFocus/detail/" + _cate + "/" + pId;
		ajax.success = function(data) {
			
			if ( data.message == "ok" ) {
				
				var _data = data.result;
				
				var _pfPowerNavi = _data.result[0].POWER_COMP_NM + " " + _data.result[0].POWER_ST_NM + " " + _data.result[0].ST_NO;
				var _pfPublishYM = _data.result[0].PUBLISH_YM;
				var _pfReportNM = _data.result[0].REPORT_NM;
				var _pfVaccum = _data.result[0].vaccum_dn_leak;
				var _pfVaccumAvail = _data.result[0].vaccum_dn_leak_avail;
				var _pfPress = _data.result[0].press_dn_leak;
				var _pfPressAvail = _data.result[0].press_dn_leak_avail;
				var _pfComment1 = _data.result[0].vaccum_dn;
				var _pfComment2 = _data.result[0].press_dn;
				var _pfComment3 = _data.result[0].helium;
				
				var _pfFileName = _data.result[0].FILENAME;
				var _pfMd5Key = _data.result[0].MD5_KEY;
				
				$('#pfKepriPowerNavi').html(_pfPowerNavi);
				$('#pfKepriPublishYM').html(_pfPublishYM);
				$('#pfKepriVaccum').html(_pfVaccum);
				$('#pfKepriVaccumAvail').html(_pfVaccumAvail);
				$('#pfKepriPress').html(_pfPress);
				$('#pfKepriPressAvail').html(_pfPressAvail);
				$('#pfKepriReportNM').html(makeFileDownLinkHtml(_pfFileName,_cate,_pfMd5Key,_pfReportNM));
				$('#pfKepriComment1').html(_pfComment1);
				$('#pfKepriComment2').html(_pfComment2);
				$('#pfKepriComment3').html(_pfComment3);
				
				$('#pfKepriCommentAdd').html("<button class=\"btn btn-default btn-primary btn-full\" type=\"button\" onclick=\"javascript:detailPFCommentAdd('" + _cate + "','" + _data.result[0].MD5_KEY + "', '0')\">등록</button>")

				var _pftitle = "<div class='widget-header'><h4>" + getCategory(_cate) + " 상세 내역</h4></div>";

				$('#detailPFKepriReport').dialog({title: _pftitle});
				$('#detailPFKepriReport').dialog('open');
				
				// detailPFCommentList(카테고리, ID, 구분값)
				// 누설흡습의 경우 구분값은 무조건 0. 문서 하나당 추출 되는 지식은 모두 하나로 1:1 구조이다.
				// 따라서 구분값이 필요가 없기때문에 코멘트 테이블의 형태를 맞춰주기 위해 0으로 셋팅한다.
				detailPFCommentList(_cate, pId, 0);
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
};

//지식 DB 탐색 > Comment List 조회
function detailPFCommentList(pCate, pId, pGubun) {
	
	ajax.data = {};
	ajax.type = "GET";
	ajax.url = context + "/problemFocus/detailComment/" + pCate + "/" + pId + "_" + pGubun;
	ajax.success = function(data) {
		
		var _pfCommentHTML = "";
		
		if ( data.message == "ok" ) {
			
			var _data = data.result;
			for ( var i=0; i<_data.length; i++ ) {
				
				if ( isInt(i/2) ) {	// i값을 2으로 나누어 정수이면 true
					if ( _pfCommentHTML!= "" ) _pfCommentHTML += "</div></div></div>";
					
					_pfCommentHTML += "<div>";
					_pfCommentHTML += "<div class=\"starwrap\">";
					_pfCommentHTML += "<div class=\"replaylst\">";
					_pfCommentHTML += "<div class=\"row\">";
					_pfCommentHTML += "<div class=\"col-md-6\">";
					_pfCommentHTML += "<div class=\"ellipsis\">" + _data[i].comment + "</div>";
					_pfCommentHTML += "</div>";
					_pfCommentHTML += "<div class=\"col-xs-2 center\">" + _data[i].user_id + "</div>";
					_pfCommentHTML += "<div class=\"col-xs-2 center\"><div class=\"dvStar\"><span class=\"on" + _data[i].rating + "\"></span></div></div>";
					_pfCommentHTML += "<div class=\"col-xs-2 center\">" + _data[i].reg_dt + "</div>";
					_pfCommentHTML += "</div>";
				} else {
					_pfCommentHTML += "<div class=\"row\">";
					_pfCommentHTML += "<div class=\"col-md-6\">";
					_pfCommentHTML += "<div class=\"ellipsis\">" + _data[i].comment + "</div>";
					_pfCommentHTML += "</div>";
					_pfCommentHTML += "<div class=\"col-xs-2 center\">" + _data[i].user_id + "</div>";
					_pfCommentHTML += "<div class=\"col-xs-2 center\"><div class=\"dvStar\"><span class=\"on" + _data[i].rating + "\"></span></div></div>";
					_pfCommentHTML += "<div class=\"col-xs-2 center\">" + _data[i].reg_dt + "</div>";
					_pfCommentHTML += "</div>";
				}
			}
			
			$('#datailreplySlider').html(_pfCommentHTML);
			if ( pfCommentSlider != undefined ) { pfCommentSlider.destroySlider(); }
			slider3();
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

// 지식 DB 탐색 > 코멘트 등록
function detailPFCommentAdd(pCate, pId, pGubun) {
	var _rating = 1;
	var _comment = $('#pfCommentInput').val();
	
	ajax.data = {
		'rating': _rating,
		'comment': _comment
	}
	ajax.type = "POST";
	ajax.url = context + "/problemFocus/detailComment/" + pCate + "/" + pId + "_" + pGubun;;
	ajax.success = function(data) {
		if ( data.message == "ok" ) {
			
			message.show(message.noti, "등록이 완료 되었습니다.", ALERT, function() {
				// 2020.08.30 모달 확인버튼이 나오면 다시 작업필요
				$('#datailreplySlider').empty();
				$('#pfCommentInput').val('');
				detailPFCommentList(pCate, pId, pGubun);
			});
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

// 지식DB 탐색 > 상세화면 > 이미지 영역 처리
// 이미지 파일 배열 및 단일 데이터 HTML코드로 변환
function makePfImgFilesHTML(pImgFiles,pImgTxt) {
	
	var _pfImgFilesHTML = "";
	
	// imgFiles 데이터가 있을 경우
	if ( pImgFiles != "" && pImgFiles != null ) {
		var indexLoop = 0;
		
		// img가 여러장일 경우 10장씩 구분한다.
		if ( pImgFiles.indexOf("|") > -1 ){
			var _tImgFiles = pImgFiles.split(/\|/);
			var _tImgTxt = pImgTxt.split(/\|/);
			for ( var i=0; i < _tImgFiles.length; i++ ) {
							
				if(_tImgFiles[i].indexOf("`") > -1 ) {
					var _tImgFiles2 = _tImgFiles[i].split("`");
					for ( var j=0; j < _tImgFiles2.length; j++ ) {
						if ( isInt(indexLoop/10) ) {	// i값을 10으로 나누어 정수이면 true
							if ( _pfImgFilesHTML != "" ) _pfImgFilesHTML += "</ul></div>";
							_pfImgFilesHTML += "<div>";
							_pfImgFilesHTML += "<ul class=\"thumlst\">";
							_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + _tImgFiles2[j] + "\" alt=\" " + _tImgTxt[i] +"\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
							++indexLoop;
						} else {
							_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + _tImgFiles2[j] + "\" alt=\" " + _tImgTxt[i] +"\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
							++indexLoop;
						}
					}
				}else{
					if ( isInt(indexLoop/10) ) {	// i값을 10으로 나누어 정수이면 true
						if ( _pfImgFilesHTML != "" ) _pfImgFilesHTML += "</ul></div>";
						_pfImgFilesHTML += "<div>";
						_pfImgFilesHTML += "<ul class=\"thumlst\">";
						_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + _tImgFiles[i] + "\" alt=\" " + _tImgTxt[i] +"\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
						++indexLoop;
					} else {
						_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + _tImgFiles[i] + "\" alt=\" " + _tImgTxt[i] +"\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
						++indexLoop;
					}
				}
			}
		}else if(pImgFiles.indexOf("`") > -1 ) {
			var _tImgFiles = pImgFiles.split("`");
			for ( var i=0; i<_tImgFiles.length; i++ ) {
				if ( isInt(indexLoop/10) ) {	// i값을 10으로 나누어 정수이면 true
					if ( _pfImgFilesHTML != "" ) _pfImgFilesHTML += "</ul></div>";
					_pfImgFilesHTML += "<div>";
					_pfImgFilesHTML += "<ul class=\"thumlst\">";
					_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + _tImgFiles[i] + "\" alt=\" " + pImgTxt +"\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
					++indexLoop;
				} else {
					_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + _tImgFiles[i] + "\" alt=\" " + pImgTxt +"\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
					++indexLoop;
				}
			}
		} else {
			_pfImgFilesHTML += "<div>";
			_pfImgFilesHTML += "<ul class=\"thumlst\">";
			_pfImgFilesHTML += "<li><img src=\"../comm/displayImg.do?fileName=" + pImgFiles + "\" alt=\"" + pImgTxt + "\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\" onclick=\"javascript:showImage(this.src,this.alt);\"></li>";
			_pfImgFilesHTML += "</ul>";
			_pfImgFilesHTML += "</div>";
		}
	}
	
	return _pfImgFilesHTML;
}

/**
 * 파일을 다운로드 받을 수 있도록 Link 태그를만들어준다.
 * @returns
 */
function makeFileDownLinkHtml(pFileName, pCate, pMd5Key, pReportNm){
	var _pfFileDownHTML = "";
	
	_pfFileDownHTML += "<a href=\"#\" class=\"font-md u-link\" ";
	_pfFileDownHTML += "onclick=\"javascript:goSearchRepo('" + pCate + "','" + pReportNm + "');\">";
	_pfFileDownHTML += pReportNm;
	_pfFileDownHTML += "</a><button onclick=\"javascript:fileDownload('" + pFileName + "','" + pCate + "','" + pMd5Key + "', '지식DB');\"" ;
	_pfFileDownHTML += "class=\"btn btn-default btn-primary btn-full\" style=\"width: 87px;float: right;\">다운로드</button>";
		
	return _pfFileDownHTML;
	
}

/**
 * 	기술문서 탐색으로 페이지 이동.
 */
function goSearchRepo(pCate, pRepoNm){
	location.href = context + "/kepri/search.do?kwd=" + encodeURIComponent(pRepoNm) + "&category=" + pCate + "&pageNum=1&sort=r&fields=REPORT_NM";
}
//지식DB 탐색 > 상세화면 > 진단부위,현상,대책
//키워드 배열이나 단일 데이터 HTML코드로 변환
function splitPfKeyword(pKeyword, pClassName) {
	
	var _keyword = "";
	
	// imgFiles 데이터가 있을 경우
	if ( pKeyword != "" && pKeyword != null ) {
	
		// keyword가 여러개 일 경우
		if ( pKeyword.indexOf("|") > -1 ) {
			var _list = pKeyword.split("|");
			
			for ( var i=0; i<_list.length; i++ ) {
				_keyword += "<span class=\"txt-deco " + pClassName + "\" style=\"margin-right: 5px;\">" + _list[i] + "</span>";
			}
		} else {
			_keyword = "<span class=\"txt-deco " + pClassName + "\" style=\"margin-right: 5px;\">" + pKeyword + "</span>";
		}
	}
	
	return _keyword;
}

//지식DB 탐색 > 발전기 절연진단 이미지 팝업
function showGenInsImage(pImgFiles) {
	var _makeTitle = "발전기 절연진단 패턴 이미지";
	var _makeBody = "<tbody>";	
	_makeBody += "<tr>";
	_makeBody += "<td>";
	
	if ( pImgFiles.indexOf("`") > -1 ) {
		var _imgFiles = pImgFiles.split("`");
		for ( var i=0; i<_imgFiles.length; i++ ) {
			_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + _imgFiles[i] + "\" alt=\"...\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src,'');\">";
		}
	} else {
		_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + pImgFiles + "\" alt=\"...\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;\">";
	}
	
	_makeBody += "</td>";
	_makeBody += "</tr>";
	_makeBody += "</tbody>";
	
	COMMON_MODAL_OPTION.width = 600;
	COMMON_MODAL_OPTION.height = 300;
	commonModal.show(_makeTitle, _makeBody, COMMON_MODAL_OPTION, ALERT);
}

/**
 * 지식 DB탐색 > 이미지보기 > 더보기
 * 기술문서탐색 > 좌측이미지클릭
 * @param morebtn 더보기 버튼 클릭 이벤트 일경우에는 상세보기 링크를 연동 
 * @returns
 */
function showImgList(morebtn) {
	var _category = $('#his_category').val();
	var _pageNum = $('#pfImagePageNum').val() == "" ? 2 : $('#pfImagePageNum').val();
	var _powerComp = $('#his_powerComp').val();
	var _powerSt = $('#his_powerSt').val();
	var _stNo = $('#his_stNo').val();
	var _partName = $('#his_partName').val();
	var _symptomKwd = $('#his_symptomKwd').val();
	var _actionKwd = $('#his_actionKwd').val();
	
	ajax.data = {
			'pageNum': _pageNum,
			'pageSize': 10,
			'powerComp': _powerComp,
			'powerSt': _powerSt,
			'stNo': _stNo,
			'partName': _partName,
			'symptomKwd': _symptomKwd,
			'actionKwd': _actionKwd
	};
	ajax.type = "GET";
	ajax.url = context + "/problemFocus/pfImage/" + _category;
	
	ajax.success = function(data) {
		
		if ( data.message == "ok" ) {
			var _imgHTML = "";
			var _data = data.result.result;
			
			if ( _data.length > 0 ) {
				for ( var i=0; i<_data.length; i++ ) {
					var _imgList = _data[i].IMG_FILES.split("|");
					var _imgTxtList = _data[i].IMG_TXTS.split("|");
					for ( var j=0; j<_imgList.length; j++ ) {
						
						_imgTxtList[j] = _imgTxtList[j] == undefined ? "" : _imgTxtList[j];
						
						var _finalImgList = _imgList[j].split("`");
						for ( var k=0; k<_finalImgList.length; k++ ) {
							if ( _finalImgList[k].indexOf(".") > -1 ) {
								_imgHTML += "<div class=\"superbox-list\">";
								if(morebtn == "morelist"){
									_imgHTML += "<img src=\"../comm/displayImg.do?fileName=" + _finalImgList[k] + "\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\"  style=\"width: 170px; height: 190px;\" onclick=\"javascript:detailPFReport('" + _data[i].MD5_KEY + "','" + _data[i].GUBUN_NO + "');\">";	
								}else{
									_imgHTML += "<img src=\"../comm/displayImg.do?fileName=" + _finalImgList[k] + "\" data-img=\"../images/superbox/superbox-full-1.jpg\" class=\"superbox-img\"  style=\"width: 170px; height: 190px;\" onclick=\"javascript:showImage(this.src);\">";
								}
								
								_imgHTML += "<p>" + _imgTxtList[j] + "</p>";
								_imgHTML += "</div>";
							}
						}
					}
				}
				
				_pageNum = Number(_pageNum) + 1;
				
				$('#pfImageSearchDiv').append(_imgHTML);
				$('#pfImagePageNum').val(_pageNum);
			}
		}
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
}

function emptyDetailPfReport() {
	$('#pfDPowerNavi').empty();
	$('#pfDPublishYM').empty();
	$('#pfDPartName').empty();
	$('#pfDSymptomKwd').empty();
	$('#pfDActionKwd').empty();
	$('#pfDReportNM').empty();
	$('#pfDContent').empty();
	$('#pfImgSlide').empty();
}
