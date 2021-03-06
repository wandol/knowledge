/**
 * Problem 분석 페이지에 대한 js
 * pfAnalysis
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
	function drawPfAOption(optData) {
		
		optData.isPF = "Y";
		optData.cate = $('#his_category').val();
		
		ajax.data = optData;
		ajax.type = "GET";
		ajax.url = context + "/comm/getOptionList.do";
		
		ajax.success = function(data) {
			
			var _partName = getPartName(optData.cate);
			
			if ( data.isPartName == "success" && data.partNameList.length > 0 ) { commonDrawOption("pfAPartName", data.partNameList, _partName, optData.partName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isPowerComp == "success" && data.powerCompList.length > 0 ) { commonDrawOption("pfAPowerComp", data.powerCompList, "POWER_COMP_NM", optData.powerCompName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isPowerSt == "success" && data.powerStList.length > 0 ) { commonDrawOption("pfAPowerSt", data.powerStList, "POWER_ST_NM", optData.powerStName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isStNo == "success" && data.stNoList.length > 0 ) { commonDrawOption("pfAStNo", data.stNoList, "ST_NO", optData.stNo); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			// 진단부위 첫번째 item으로 select
			var _partNameVal = $('#pfAPartName option:selected').val();
			if ( _partNameVal == "all" ) {
				$('#pfAPartName option:eq(1)').prop("selected", true);
				_partNameVal = $('#pfAPartName option:selected').val();
			}
			
			// 분석 페이지 초기화
			drawChart(_partNameVal);
		};
		
		ajax.error = function(xhr, ajaxOptions) {
			if ( xhr.status == 901 ) {				
				message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			}
		}
		
		// ajax 실행
		$.ajax(ajax);
	};
	
	// 발전사 선택 시
	$('#pfAPowerComp').change(function() {
		// 선택한 발전사
		var _powerComp = this.value;
		optionData.powerCompName = _powerComp;
		optionData.powerStName = "all";
		optionData.stNo = "all";
		optionData.partName = "all";
		optionData.symptomKwd = "all";
		optionData.actionKwd = "all";
		
		drawPfAOption(optionData);
	});
	
	// 사업소 선택 시
	$('#pfAPowerSt').change(function() {
		// 선택한 사업소
		var _powerSt = this.value;
		optionData.powerCompName = $('#pfAPowerComp').val();
		optionData.powerStName = _powerSt;
		optionData.stNo = "all";
		optionData.partName = "all";
		
		drawPfAOption(optionData);
	});
	
	// 호기 선택 시
	$('#pfAStNo').change(function() {
		// 선택한 호기
		var _stNo = this.value;
		optionData.powerCompName = $('#pfAPowerComp').val();
		optionData.powerStName = $('#pfAPowerSt').val();
		optionData.stNo = _stNo;
		optionData.partName = "all";
		
		drawPfAOption(optionData);
	});
	
	// 진단 부위 선택 시
	$('#pfAPartName').change(function() {
		// 선택한 부위
		var _partName = this.value;
		optionData.powerCompName = $('#pfAPowerComp').val();
		optionData.powerStName = $('#pfAPowerSt').val();
		optionData.stNo = $('#pfAStNo').val();
		optionData.partName = _partName;
		
		drawPfAOption(optionData);
	});
	
	
	//////////////////////////////////////////////////////////////////
	//////////////////////////////진단 DB 분석
	//////////////////////////////////////////////////////////////////
	function drawChart(partName) {
		
		var _category = $('#pfACategory option:selected').val();
		var _powerComp = $('#pfAPowerComp option:selected').val();
		var _powerSt = $('#pfAPowerSt option:selected').val();
		var _stNo = $('#pfAStNo option:selected').val();
		var _partname = $('#pfAPartName option:selected').val();
		
		ajax.data = {
				'powerComp': _powerComp,
				'powerSt': _powerSt,
				'stNo': _stNo,
				'partName': partName
		};
		ajax.type = "GET";
		ajax.url = context + "/problemFocus/analysis/" + _category;
		
		ajax.success = function(data) {
			
			// flot chart 클릭 이벤트 제거
			$("#pfASymptom").unbind("plotclick");
			$("#pfActionChart").unbind("plotclick");
			var _isData = false;
			
			var _compN = "";
			if(_powerComp == "all") _compN = " > " + chkValue(_powerComp);
			var _stN = "";
			if(_powerSt == "all") _stN = " > " + chkValue(_powerSt);
			var _stNoN = "";
			if(_stNo == "all") _stNoN = " > " + chkValue(_stNo);
			
			var _navigation = getCategory(_category) + " > " + chkValue(_partname) + _compN + _stN + _stNoN;
			
			// 현상 - barchart
			if ( data.isSymptomData == "yes" ) {
				$('#pfASymptomDiv').removeClass('col-xs-12').addClass('col-xs-6');
				drawSymptom(data.symptomKwdList, "<b>[현상]</b> " + _navigation, _category, partName);
				_isData = true;
			} else {
				$('#pfASymptom').empty();
				$('#pfASymptomDiv').css("display", "none");
				$('#pfAActionDiv').removeClass('col-xs-6').addClass('col-xs-12');
				$('#pfASymptomTTL').html("");
			}
			
			// 대책 - barchart
			if ( data.isActionData == "yes" ) {
				$('#pfAActionDiv').removeClass('col-xs-12').addClass('col-xs-6');
				drawAction(data.actionKwdList,  "<b>[대책]</b> " + _navigation, _category, partName);
				_isData = true;
			} else {
				$('#pfAAction').empty();
				$('#pfAActionDiv').css("display", "none");
				$('#pfASymptomDiv').removeClass('col-xs-6').addClass('col-xs-12');
				$('#pfAActionTTL').html("");
			}
			
			// 날짜별 건수 - linechart
			if ( data.isPublicData == "yes" ) {
				$('#pfAPublishArticle').removeClass('col-xs-12').addClass('col-xs-8');
				// 날짜별 데이터 차트 그리기
				drawPublish(data.publishYMList,  "<b>[지식등록건수 추이]</b> " + _navigation);				
				_isData = true;
			
			} else {
				$('#pfAPublish').empty();
				$('#pfAPublishArticle').css("display", "none");
				$('#pfADepthArticle').removeClass('col-xs-4').addClass('col-xs-12');
				$('#pfAPublishTTL').html("");
			}
			
			// 발전소, 사업소, 호기 별 건수 - piechart
			if ( data.isDepthData == "yes" ) {
				$('#pfADepthArticle').removeClass('col-xs-12').addClass('col-xs-4');
				// 뎁스 데이터 차트 그리기
				var _genNavigation = getCategory(_category) + " > " + chkValue(_partname);
				drawDepth(data.depthList, "<b>[발전사별 진단 DB 건 수]</b> " +  _genNavigation);
				_isData = true;
				
			} else {
				$('#pfADepth').empty();
				$('#pfADepthArticle').css("display", "none");
				$('#pfAPublishArticle').removeClass('col-xs-8').addClass('col-xs-12');
				$('#pfADepthTTL').html("");
			}
			
			if ( !_isData ) {
				message.show(message.warning, "데이터가 없습니다.", ALERT);
			}
		};
		
		ajax.error = function(xhr, ajaxOptions) {
			if ( xhr.status == 901 ) {				
				message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
					location.href = $('#his_loginUrl').val();
				});
			}
		}
		
		// ajax 실행
		$.ajax(ajax);
	};
	
	// 현상 bar차트 그리기
	function drawSymptom(pData, pNavi, pCategory, pPartName) {
		var _symptomList = [];
		
		for ( var i=0; i<pData.result.length; i++ ) {
			_symptomList.push([pData.result[i].SYMPTOM_KWD.replace("_", "<br>"), parseInt(pData.result[i].count)]);
		}

		$('#pfASymptomDiv').css('display', 'block');
		drawFlot.barChart(_symptomList, "pfASymptom", $color_second);
		
		$('#pfASymptomTTL').html(pNavi);
		
		// flot chart Click Event Create
		$("#pfASymptom").bind("plotclick", function(event, pos, item) {
			if (item) {
				var index = item.dataIndex;
				var data = item.series.data[index];
				getSymptomToAction(pCategory, pPartName, data[0], "SYMPTOM");
			}
		});
	}
	
	// 대책 bar차트 그리기
	function drawAction(pData, pNavi, pCategory, pPartName) {
		var _actionList = [];
		
		for ( var i=0; i<pData.result.length; i++ ) {
			_actionList.push([pData.result[i].ACTION_KWD.replace("_", "<br>"), parseInt(pData.result[i].count)]);
		}

		$('#pfAActionDiv').css('display', 'block');
		drawFlot.barChart(_actionList, "pfAAction", $color_first);
		
		$('#pfAActionTTL').html(pNavi);
		
		// flot chart Click Event Create
		$("#pfAAction").bind("plotclick", function(event, pos, item) {
			if (item) {
				var index = item.dataIndex;
				var data = item.series.data[index];
				getSymptomToAction(pCategory, pPartName, data[0], "ACTION");
			}
		});
	}
	
	// 날짜별 line 차트 그리기
	function drawPublish(pData, pNavi) {
		var _data;
		var publishYMList = pData;
		var publishYM = [];
		
		for ( var i=0; i<publishYMList.result.length; i++ ) {
			
			if ( publishYMList.result[i].PUBLISH_YM.indexOf(".") > -1 ) {
				// [날짜 포맷] 월이 1자리 인 경우 0을 붙여준다.
				var _publishSplit = publishYMList.result[i].PUBLISH_YM.split(".");
				publishYMList.result[i].PUBLISH_YM = _publishSplit[0];
			} else {
				publishYMList.result[i].PUBLISH_YM = publishYMList.result[i].PUBLISH_YM;
			}
			
			// [날짜 포맷] 마지막에 . 체크하여 없으면 붙여준다.
//			if ( publishYMList.result[i].PUBLISH_YM.charAt(publishYMList.result[i].PUBLISH_YM.length - 1) != "." ) {
//				publishYMList.result[i].PUBLISH_YM = publishYMList.result[i].PUBLISH_YM + ".";
//			}
		}
		
		var newArr = publishYMList.result.reduce(function(a, b){
			var cnt = a.length;
			if ( cnt === 0 || a[cnt - 1].PUBLISH_YM != b.PUBLISH_YM ){
				a.push(b);
			}
			else {
				a[cnt - 1].count = Number(a[cnt - 1].count) + Number(b.count) + "";
			}
			return a;
		},[]);
		
		var newArr2 = newArr.sort(function(a, b) {
			return a.PUBLISH_YM < b.PUBLISH_YM ? -1 : a.PUBLISH_YM > b.PUBLISH_YM ? 1: 0;
		});
		
		for ( i=0; i<newArr2.length; i++ ) {
			var tempDate = newArr2[i].PUBLISH_YM.split(".");
			var d = new Date(tempDate[0]);
			publishYM.push([d.getTime(), newArr2[i].count]);
		}
		_data = [
			{data: publishYM, label: " 날짜 | 지식등록건수" }
		];
		
		$('#pfAPublishArticle').css('display', 'block');
		drawFlot.lineChart(_data, "pfAPublish");
		
		$('#pfAPublishTTL').html(pNavi);
	}
//	function drawPublish(pData, pNavi) {
//		var _data;
//		var publishYMList = pData;
//		var publishYM = [];
//		
//		for ( var i=0; i<publishYMList.result.length; i++ ) {
//			
//			if ( publishYMList.result[i].PUBLISH_YM.indexOf(".") > -1 ) {
//				// [날짜 포맷] 월이 1자리 인 경우 0을 붙여준다.
//				var _publishSplit = publishYMList.result[i].PUBLISH_YM.split(".");
//				if ( _publishSplit[1].length == 1 ) {
//					publishYMList.result[i].PUBLISH_YM = _publishSplit[0] + ".0" + _publishSplit[1] + ".";
//				}
//			} else {
//				publishYMList.result[i].PUBLISH_YM = publishYMList.result[i].PUBLISH_YM + ".01.";
//			}
//			
//			// [날짜 포맷] 마지막에 . 체크하여 없으면 붙여준다.
//			if ( publishYMList.result[i].PUBLISH_YM.charAt(publishYMList.result[i].PUBLISH_YM.length - 1) != "." ) {
//				publishYMList.result[i].PUBLISH_YM = publishYMList.result[i].PUBLISH_YM + ".";
//			}
//		}
//		
//		var newArr = publishYMList.result.reduce(function(a, b){
//			var cnt = a.length;
//			if ( cnt === 0 || a[cnt - 1].PUBLISH_YM != b.PUBLISH_YM ){
//				a.push(b);
//			}
//			else {
//				a[cnt - 1].count = Number(a[cnt - 1].count) + Number(b.count) + "";
//			}
//			return a;
//		},[]);
//		
//		var newArr2 = newArr.sort(function(a, b) {
//			return a.PUBLISH_YM < b.PUBLISH_YM ? -1 : a.PUBLISH_YM > b.PUBLISH_YM ? 1: 0;
//		});
//		
//		for ( i=0; i<newArr2.length; i++ ) {
//			var tempDate = newArr2[i].PUBLISH_YM.split(".");
//			var d = new Date(tempDate[0],tempDate[1]);
//			publishYM.push([d.getTime(), newArr2[i].count]);
//		}
//		console.log(publishYM);
//		_data = [
//			{data: publishYM, label: " 날짜 | 지식등록건수" }
//		];
//		
//		$('#pfAPublishArticle').css('display', 'block');
//		drawFlot.lineChart(_data, "pfAPublish");
//		
//		$('#pfAPublishTTL').html(pNavi);
//	}
	// Depth 도넛 차트 그리기
	function drawDepth(pData, pNavi) {
		
		var depthList = pData;
		var data_pie = [];
		var series = Math.floor(Math.random() * 10) + 1;
		for (var i = 0; i < depthList.result.length; i++) {
			data_pie[i] = {
				label : depthList.result[i].data,
				data : depthList.result[i].count
			}
		}

		$('#pfADepthArticle').css('display', 'block');
		drawFlot.pieChart(data_pie, "pfADepth");
		
		$('#pfADepthTTL').html(pNavi);
	}
	
	// 현상, 대책 클릭 시 관련 된 대책, 현상 List불러오기
	function getSymptomToAction(pCategory, pPartName, pKeyword, pGubun) {
		
		// 키워드가 2줄로 되어있는 걸 _로 붙여준다.
		var _keyword = pKeyword.replace("<br>", "_");
		
		$.ajax({	
			type: "GET",
			url: context + "/problemFocus/symptomToActionTop10",
			data: {
				"cate" : pCategory,
				"powerComp": $('#pfAPowerComp option:selected').val(),
				"powerSt": $('#pfAPowerSt option:selected').val(),
				"stNo": $('#pfAStNo option:selected').val(),
				"partName" : pPartName,
				"keyword" : _keyword,
				"gubun": pGubun
			},
			dataType: "json",
			success: function(data) {
				
				if ( data.result.length > 0 ) {
					var _data = data.result;
					var _dataList = [];
					var _height = 400;
					var _overflowY = "hidden";
					var _loopCount = _data.length >= 10 ? 10 : _data.length;
					
					for ( var i=0; i<_loopCount; i++ ) {
						var _obj = {};
						_obj.id = pGubun + "-" + pKeyword.replace("<br>", "_") + "-" + (i+1) + "";
						_obj.name = _data[i].data.replace(/_/gi, " ");
						_obj.value = Number(_data[i].count);
						_dataList.push(_obj);
					}
					
					var _makeTitle = "";
					var _makeBody = "<tbody>";	
					_makeBody += "<tr >";
					_makeBody += "<td colspan=\"3\"><div id=\"chart\" style=\"height: " + _height + "px; overflow-y: " + _overflowY + "; overflow-x: hidden;\"></div></td>";
					_makeBody += "</tr>";
					_makeBody += "</tbody>";
					
					if ( pGubun == "ACTION" ) {
						_makeTitle = "대책 : ";
					} else {
						_makeTitle = "현상 : ";
					}
					_makeTitle += pKeyword.replace("<br>", " ");
					
					var _title = _makeTitle;
					
					COMMON_MODAL_OPTION = {
							width : 600
					}
					commonModal.show(_title, _makeBody, COMMON_MODAL_OPTION, ALERT);
					
					drawDavif.horizonBarChart(_dataList, "chart");
				} else {
					var _gubunStr = "";
					var _extractStr = "";
					if ( gubun == "ACTION" ) {
						_extractStr = "대책";
						_gubunStr = "현상";
					} else {
						_extractStr = "현상";
						_gubunStr = "대책";
					}
					message.show(message.warning, "추출 된 " + _gubunStr + "의 " + _extractStr + " 단어가 없습니다.", ALERT);
				}
				
			}
		});
	}
	
	
	// 페이지 로딩 시 history form에서 데이터를 불러온다. 
	// 1. option 데이터 조회 조건
	// 2. option 데이터 새로 select box에 그릴 때 selected 상태 처리용 
	optionData.partName = $('#his_partName').val();
	optionData.powerCompName = $('#his_powerComp').val();
	optionData.powerStName = $('#his_powerSt').val();
	optionData.stNo = $('#his_stNo').val();
	
	// 검색 옵션 초기화
	drawPfAOption(optionData);
});

function chgCategory(pValue) {
	location.href = context + "/problemFocus/pfAnalysis.do?category=" + pValue;
}