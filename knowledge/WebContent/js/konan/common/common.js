	$(document).ready(function() {
	
	// ksf 자동완성
//	$("input[name='kwd']").autocomplete({ 
//		source: "http://127.0.0.1:8614/ksf/api/suggest?target=complete&domain=0&mode=s&max_count=10"
//	});
	
});

// 숫자가 정수인지 실수인지 체크
var isInt = function(n) { return parseInt(n) === n };

// select box 초기화
function initSelectBox(pId) {
	$('#' + pId).empty();
	$('#' + pId).prepend(optionAll);
	$('#' + pId + ' option:eq(0)').prop('selected', true);
}

// option 정보를 받아 Select box의 List를 만들어준다.
// select box에 count 표시 있음
function commonDrawOption(pId, pData, pKey, pSelVal, pCategory) {
	// param -> id : TAG의 id값
	// param -> data : option에 그려질 Data List
	// param -> key : Data의 key
	// param -> selVal : option selected 적용할 Data
	// param -> category : category에 따라서 문구가 달라진다.
	
	var _optionTTl = "";
	
	if ( pKey == "PARTNAME" || pKey == "PARTNAME_KWD" ) {
		if ( pCategory == "GEN_PREV" ) _optionTTL = "점검항목" ;
		else _optionTTL = "진단부위" ;
	}
	else if ( pKey == "SYMPTOM_KWD" ) _optionTTL = "현상" ;
	else if ( pKey == "ACTION_KWD" ) _optionTTL = "결과" ;
	else if ( pKey == "POWER_COMP_NM" ) _optionTTL = "발전사" ;
	else if ( pKey == "POWER_ST_NM" ) _optionTTL = "사업소" ;
	else if ( pKey == "ST_NO" ) _optionTTL = "호기" ;
	
	$('#' + pId).empty();
	$('#' + pId).append("<option value='all'>" + _optionTTL + "</option>");
	
	for ( var i=0; i<pData.length; i++ ) {
		
		if ( pSelVal == pData[i][pKey] ) $('#' + pId).append("<option value='" + pData[i][pKey] + "' selected>" + pData[i][pKey] + "(" + pData[i].count + ")</option>");
		else $('#' + pId).append("<option value='" + pData[i][pKey] + "'>" + pData[i][pKey] + "(" + pData[i].count + ")</option>");
	}
	
};

// option 정보를 받아 Select box의 List를 만들어준다.
// select box에 count 표시가 없음
function commonUploadDrawOption(pId, pData, pKey, pSelVal, pCategory) {
	// param -> id : TAG의 id값
	// param -> data : option에 그려질 Data List
	// param -> key : Data의 key
	// param -> selVal : option selected 적용할 Data
	// param -> category : category에 따라서 문구가 달라진다.
	
	var _optionTTl = "";
	var _selectVal = "";
	var _isSameVal = false;
	
	if ( pKey == "POWER_COMP_NM" ) _optionTTL = "발전사" ;
	else if ( pKey == "POWER_ST_NM" ) {
		_optionTTL = "사업소" ;
		
		// 메타 추출기능에서 value가 "화력"이란 단어를 붙여야 하는 케이스가 있음
		// 복합이란 단어가 포함이 되지 않을 때만 실행이 되며 서버에서 받아온 List와 비교하여 같은 값이 없을 경우 "화력"이란 단어를 붙임.
		for ( var i=0; i<pData.length; i++ ) {
			if ( pSelVal.indexOf("복합") < 0 ) {
				if ( pData[i][pKey] == pSelVal ) {
					_isSameVal = true;
				}
			}
		}
		
		if ( pSelVal.indexOf("복합") < 0 && !_isSameVal ) {
			pSelVal = pSelVal + "화력";
		}
	}
	else if ( pKey == "ST_NO" ) _optionTTL = "호기" ;
	
	$('#' + pId).empty();
	$('#' + pId).append("<option value='all'>" + _optionTTL + "</option>");
	
	for ( var j=0; j<pData.length; j++ ) {
		
		if ( pSelVal == pData[j][pKey] ) $('#' + pId).append("<option value='" + pData[j][pKey] + "' selected>" + pData[j][pKey] + "</option>");
		else $('#' + pId).append("<option value='" + pData[j][pKey] + "'>" + pData[j][pKey] + "</option>");
	}
	
};

// option 정보를 선택하면 선택한 옵션값을 보여준다.
function commonSelectOption(pOpt, pValue, pMenu) {
	
	var _class = "";
	var _id = $('div.selectBtn');

	if ( pValue != "all" && pValue != null && pValue != undefined ) {
		if ( pOpt == "PARTNAME" || pOpt == "PARTNAME_KWD" || pOpt == "POWER_COMP_NM") {
			_class = "btn-st1"; // skyblue
		} else if ( pOpt == "SYMPTOM_KWD" || pOpt == "POWER_ST_NM" ) {
			_class = "btn-st2";	// pink
		} else if ( pOpt == "ACTION_KWD" || pOpt == "ST_NO" ) {
			_class = "btn-st3";	// yellow
		} else {
			_class = "ty1";
		}
	}
	
	if ( _class != "" ) {
		var _btnHTML = "";
		
		_btnHTML += "<span class=\"txt-deco " + _class + "\" style=\" margin-left : 5px; margin-right: 5px;\">";
		_btnHTML += pValue;
		_btnHTML += "<a href=\"javascript:removeOpt('" + pOpt + "','" + pValue + "','" + pMenu + "')\">";
		_btnHTML += "<i class=\"fa fa-times\"></i>";
		_btnHTML += "</a>";
		_btnHTML += "</span>";
		
		_id.append(_btnHTML);
	}
};

// 카테고리 정보 return
function getCategory(pValue) {
	if ( pValue == "BOILER" ) {
		return "보일러";
	} else if ( pValue == "GT_TURBINE" ) {
		return "가스터빈";
	} else if ( pValue == "ST_TURBINE" ) {
		return "증기터빈";
	} else if ( pValue == "GEN_PREV" ) {
		return "발전기 예방진단";
	} else if ( pValue == "GEN_INS" ) {
		return "발전기 절연진단";
	} else if ( pValue == "KEPRI" ) {
		return "발전기 누설흡습";
	} else if ( pValue == "PERFORM" ) {
		return "성능";
	} else {
		return "No Category";
	}  
}

// 보고서 종류(정밀, 고장)
function getRepoKind(pCategory, pValue, pValue2) {
	if ( pCategory == "BOILER" ) {
		return pValue;
	} else if ( pCategory == "GT_TURBINE" || pCategory == "ST_TURBINE" ) {
		return pValue2;
	}
	
	return "";
}

// PARTNAME 필드 key값 return
function getPartName(pValue) {
	var _partName = "";
	
	switch ( pValue ){
		case "GT_TURBINE":
		case "ST_TURBINE":
		case "GEN_PREV":
		case "GEN_INS":
		case "KEPRI":
			_partName = "PARTNAME_KWD";
			break;
		default : 
			_partName = "PARTNAME";
	}
	
	return _partName;
}

//PART_MID 필드 key값 return
function getPartMid(pValue) {
	var _partMid = "";
	
	switch ( pValue ){
		case "GT_TURBINE":
		case "ST_TURBINE":
			_partMid = "PART2NAME_KWD2";
		case "GEN_PREV":
		case "GEN_INS":
		case "KEPRI":
			_partMid = "PART2NAME_KWD";
			break;
		default : 
			_partMid = "PART_MID";
	}
	
	return _partMid;
}

function chkValue(pValue) {
	if ( pValue == "all" ) {
		return "전체";
	}
	return pValue;
}

// date format 조정
function setDateFormat(pVal) {
	if ( pVal.length > 0 && pVal.charAt(pVal.length - 1) != "." ) {
		pVal = pVal + ".";
	}
	
	return pVal;
}

///////////////////////////////////////////////////////////// Validation
function validDateFormat(pVal) {
	
	if ( pVal != null && pVal != undefined && pVal != "" ) {
		if ( pVal.length == 8 ) {			
			if ( !dateReg.test(pVal) ) {
				message.show(message.warning, "날짜 포맷 오류.(YYYY.MM.)", ALERT);
				return false;
			} else {
				return true;
			}
		} else {
			message.show(message.warning, "날짜 포맷 오류.(YYYY.MM.)", ALERT);
			return false;
		}
	} else {
		return false;
	}
}

function validInputText(title, val) {
	if ( val == "" || val == null || val == undefined ) {
		return false;
	} else 
		return true;
}

function validSelectBox(title, val) {
	if ( val == "" || val == null || val == undefined || val == "all" ) {
		return false;
	} else 
		return true;
}


///////////////////////////////////////////////////////////// Common function
//선택한 옵션 "X"표시 클릭 시 옵션 재검색
function removeOpt(pKey, pValue, pMenu) {
	
	var _selectOpt = $('div.selectBtn').find("span");
	var _action = "";
	if ( pMenu == "pf" ) {
		
		var _partName = "";
		var _symptomKwd = "";
		var _actionKwd = "";
		
		for ( var i=0; i<_selectOpt.length; i++ ) {
			if ( _selectOpt[i].className.indexOf("btn-st1") > -1) _partName = _selectOpt[i].innerText;
			else if ( _selectOpt[i].className.indexOf("btn-st2") > -1 ) _symptomKwd = _selectOpt[i].innerText;
			else if ( _selectOpt[i].className.indexOf("btn-st3") > -1 ) _actionKwd = _selectOpt[i].innerText;
		}
		
		if ( pKey == "PARTNAME" || pKey == "PARTNAME_KWD") {
			_partName = "all";
			_symptomKwd = "all";
			_actionKwd = "all";
		} else if ( pKey == "SYMPTOM_KWD" ) {
			_symptomKwd = "all";
			_actionKwd = "all";
		} else if ( pKey == "ACTION_KWD" ) {
			_actionKwd = "all";
		}
		
		$('#his_partName').val(_partName);
		$('#his_symptomKwd').val(_symptomKwd);
		$('#his_actionKwd').val(_actionKwd);
		
		_action = "pfSearch.do";
	} else {
		var _powerCompNm = "";
		var _powerStNm = "";
		var _stNo = "";
		
		for ( var i=0; i<_selectOpt.length; i++ ) {
			if ( _selectOpt[i].className.indexOf("btn-st1") > -1) _powerCompNm = _selectOpt[i].innerText;
			else if ( _selectOpt[i].className.indexOf("btn-st2") > -1 ) _powerStNm = _selectOpt[i].innerText;
			else if ( _selectOpt[i].className.indexOf("btn-st3") > -1 ) _stNo = _selectOpt[i].innerText;
		}
		
		if ( pKey == "POWER_COMP_NM" ) {
			_powerCompNm = "all";
			_powerStNm = "all";
			_stNo = "all";
		} else if ( pKey == "POWER_ST_NM" ) {
			_powerStNm = "all";
			_stNo = "all";
		} else if ( pKey == "ST_NO" ) {
			_stNo = "all";
		}
		
		$('#his_powerComp').val(_powerCompNm);
		$('#his_powerSt').val(_powerStNm);
		$('#his_stNo').val(_stNo);
		
		_action = "search.do";
	}
	
	$('#historyForm').attr("action", _action);
	$('#historyForm').submit();
}

//페이지 이동
function gotoPage(pPageNum, pMenu) {
	
	// 진단DB
	if ( pMenu == "problemFocus" ) {
		$('#historyForm').attr("action","pfSearch.do");
	} else if(pMenu == "document"){
		$('#historyForm').attr("action","docSearch.do");
	} else if(pMenu == "repository"){
		$('#historyForm').attr("action","repository.do");
	}
	$('#pageNum').val(pPageNum);
	$('#historyForm').submit();
}

// 파일 다운로드
function fileDownload(pFileNM, pCategory, pKey, pPageNM) {
	location.href = context + "/comm/fileDownload.do?fileName=" + encodeURIComponent(pFileNM) + "&cate=" + pCategory + "&md5_key=" + pKey + "&pageName=" + encodeURIComponent(pPageNM);
}

// 일괄 다운로드
function allFileDownLoad(pCategory,pKey,pTitle){
	location.href = context + "/comm/allDownLoad.do?cate=" + pCategory + "&doc_idx=" + pKey + "&title=" + encodeURIComponent(pTitle);
}


// 엔진 관리기
//엔진 관리기화면으로 이동(자동로그인)
function goSearchAdmin() {
	
	var url = $('#his_adminUrl').val();
	var userID = "admin";
	var password = "konan415!";
	
	var cryptoMap = CryptoUtils.encrypt(password);
    
	var form = document.createElement("form");
    form.setAttribute("charset", "UTF-8");
    form.setAttribute("method", "POST");  //Post 방식
    form.setAttribute("target", "_blank");  //새창 띄우기
    form.setAttribute("action", "http://" + url + "/admin-webapp/session.do"); //요청 보낼 주소
    
    // todo
 	var hiddenTodoField = document.createElement("input");
 	hiddenTodoField.setAttribute("type", "hidden");
 	hiddenTodoField.setAttribute("name", "todo");
 	hiddenTodoField.setAttribute("value", "login");
    form.appendChild(hiddenTodoField);
    
    // id
    var hiddenIDField = document.createElement("input");
    hiddenIDField.setAttribute("type", "hidden");
    hiddenIDField.setAttribute("name", "userid");
    hiddenIDField.setAttribute("value", userID);
    form.appendChild(hiddenIDField);
    
    // password
    var hiddenPWField = document.createElement("input");
    hiddenPWField.setAttribute("type", "hidden");
    hiddenPWField.setAttribute("name", "encPassword");
    hiddenPWField.setAttribute("value", cryptoMap.encPassword);
    form.appendChild(hiddenPWField);
   
    // iv
    var hiddenIVField = document.createElement("input");
    hiddenIVField.setAttribute("type", "hidden");
    hiddenIVField.setAttribute("name", "iv");
    hiddenIVField.setAttribute("value", cryptoMap.iv);
    form.appendChild(hiddenIVField);
    
    // salt
    var hiddenSaltField = document.createElement("input");
    hiddenSaltField.setAttribute("type", "hidden");
    hiddenSaltField.setAttribute("name", "salt");
    hiddenSaltField.setAttribute("value", cryptoMap.salt);
    form.appendChild(hiddenSaltField);
    
	// clientLanguage
    var hiddenLangField = document.createElement("input");
    hiddenLangField.setAttribute("type", "hidden");
    hiddenLangField.setAttribute("name", "clientLanguage");
    hiddenLangField.setAttribute("value", "ko");
    form.appendChild(hiddenLangField);
	
    document.body.appendChild(form);
    form.submit();
};


function showImage(pImg,pTxt) {
	
//	pWidth = pWidth > 800 ? 800 : pWidth;
//	pHeight = pHeight > 700 ? 700 : pHeight ;
	
	var _makeTitle = "이미지";
	var _makeBody = "<tbody>";	
	_makeBody += "<tr>";
	_makeBody += "<td style=\"text-align: center;\">";
	
	_makeBody += "<img src=\"" + pImg + "\" alt=\"" + pTxt + "\" class=\"img-thumbnail\" style=\"width: 700px; height: 600px;\">";
	_makeBody += "<span>" + pTxt + "</span>";
	
	_makeBody += "</td>";
	_makeBody += "</tr>";
	_makeBody += "</tbody>";
	
	COMMON_MODAL_OPTION.width = 800;
	COMMON_MODAL_OPTION.height = 800;
	commonModal.show(_makeTitle, _makeBody, COMMON_MODAL_OPTION, ALERT);
	$("#commonModal").parents(".ui-dialog ").css("z-index","10015");
}

//지식DB 탐색 > 발전기 절연진단 이미지 팝업
function showImageListv2(pImgFiles) {
	var _makeTitle = "이미지 목록";
	var _makeBody = "<tbody>";	
	_makeBody += "<tr>";
	_makeBody += "<td>";
	
	if ( pImgFiles.indexOf("`") > -1 ) {
		var _imgFiles = pImgFiles.split("`");
		for ( var i=0; i <_imgFiles.length; i++ ) {
			if(_imgFiles[i] != "")
			_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + _imgFiles[i] + "\" alt=\"...\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src);\">";
		}
	} else {
		_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + pImgFiles + "\" alt=\"...\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src);\">";
	}
	
	_makeBody += "</td>";
	_makeBody += "</tr>";
	_makeBody += "</tbody>";
	
	COMMON_MODAL_OPTION.width = 807;
	//COMMON_MODAL_OPTION.height = 800;
	imgListModal.show(_makeTitle, _makeBody, COMMON_MODAL_OPTION, ALERT);
}
//지식DB 탐색 > 발전기 절연진단 이미지 팝업
function showImageList(pImgFiles,pImgTxt) {
	var _makeTitle = "이미지 목록";
	var _makeBody = "<tbody>";
	var _imgExt = "";	
	_makeBody += "<tr>";
	_makeBody += "<td>";
	
	if ( pImgFiles.indexOf("|") > -1 ) {
		var _imgFiles = pImgFiles.split("|");
		var _imgTxt = pImgTxt.split("|");
		for ( var i=0; i <_imgFiles.length ; i++ ) {
			if(_imgFiles[i] != ""){
				if(_imgFiles[i].indexOf("`") > - 1){
					var _imgFiles2 = _imgFiles[i].split("`");
					for ( var j=0; j <_imgFiles2.length; j++ ) {
						_imgExt = getExtensionOfFilename(_imgFiles2[j]);
						if(getAllowImgFile(_imgExt)){
							_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + _imgFiles2[j] + "\" alt=\""+_imgTxt[i]+"\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src,this.alt);\">";
						}
					}
				}else{
					_imgExt = getExtensionOfFilename(_imgFiles[i]);
					if(getAllowImgFile(_imgExt)){
						_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + _imgFiles[i] + "\" alt=\""+_imgTxt[i]+"\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src,this.alt);\">";
					}
				}
			}
		}
	}else if ( pImgFiles.indexOf("`") > -1 ) {
		var _imgFiles = pImgFiles.split("`");
		for ( var i=0; i <_imgFiles.length; i++ ) {
			if(_imgFiles[i] != "")
			_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + _imgFiles[i] + "\" alt=\"" + pImgTxt + "\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src,this.alt);\">";
		}
	} else {
		_makeBody += "<img src=\"../comm/displayImg.do?fileName=" + pImgFiles + "\" alt=\"" + pImgTxt + "\" class=\"img-thumbnail\" style=\"max-width:140px; height:auto;cursor:pointer;\" onclick=\"javascript:showImage(this.src,this.alt);\">";
	}
	
	_makeBody += "</td>";
	_makeBody += "</tr>";
	_makeBody += "</tbody>";
	
	COMMON_MODAL_OPTION.width = 807;
	//COMMON_MODAL_OPTION.height = 800;
	imgListModal.show(_makeTitle, _makeBody, COMMON_MODAL_OPTION, ALERT);
}

/**
 * 파일명에서 확장자명 추출
 * @param filename   파일명
 * @returns _fileExt 확장자명
 */
function getExtensionOfFilename(filename) {
 
    var _fileLen = filename.length;
    var _lastDot = filename.lastIndexOf('.');
 
    // 확장자 명만 추출한 후 소문자로 변경
    var _fileExt = filename.substring(_lastDot + 1, _fileLen).toLowerCase();
 
    return _fileExt;
}

/**
 * 이미지 확장자 필터.
 * @param fileext   파일명
 * @returns boolean 
 */
function getAllowImgFile(fileext) {
 	var allowExt = ["jpg","gif","bmp","tiff","png"];
    return allowExt.indexOf(fileext) >= 0;
}
