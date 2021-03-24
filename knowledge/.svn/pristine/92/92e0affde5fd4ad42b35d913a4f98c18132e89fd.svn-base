/**
 * 발전문서 페이지에 대한 js
 * 
 * 변수 명명 규칙 : 1. 파라미터의 경우 앞에 p를 붙인다
 *              2. HTML을 만드는 변수의 경우 끝에 HTML을 붙인다
 *              3. 내부변수의 경우 앞에 _를 붙인다.
 */

var DOCUMENT_INFO = {
		cat_idx: '',
		title: '',
		contents: '',
		keyword: '',
		files : ''
}

$(document).ready(function() {
});

function checkUseYN(pItem){
	var _check = pItem.checked;
	var _label = pItem.parentElement;
	var _children = _label.children;
	
	if(_check){
		_children[1].textContent = "사용함";
	}else{
		_children[1].textContent = "사용안함";
	}
}

function findCategoryData(cateNm){
	location.href = context + "/document/docSearch.do?&fields=CAT_NM&kwd=" + encodeURIComponent(cateNm);
}

// 발전문서 > 카테고리 관리 > 추가
function categoryAddDiv() {
	var _trList = $('#docCategoryTBL tbody').find("tr");
	var _addTrHTML = "";
	
	_addTrHTML += "<tr class=\"add\">";
	_addTrHTML += "<td>";
	_addTrHTML += "<div class=\"checkbox\"  style=\"text-align: center;\">";
	_addTrHTML += "<label>";
	_addTrHTML += "<input type=\"checkbox\" class=\"checkbox style-0\">";
	_addTrHTML += "<span></span>";
	_addTrHTML += "</label>";
	_addTrHTML += "</div>";
	_addTrHTML += "</td>";
	_addTrHTML += "<td>" + (_trList.length + 1) + "</td>";
	_addTrHTML += "<td class=\"tleft\"><input class=\"form-control\" type=\"text\" name=\"cat_nm\"></td>";
	_addTrHTML += "<td><input class=\"form-control\" type=\"text\" name=\"order_no\"></td>";
	_addTrHTML += "<td>";
	_addTrHTML += "<div class=\"checkbox\">";
	_addTrHTML += "<label>";
	_addTrHTML += "<input type=\"checkbox\" class=\"checkbox style-0\" onclick=\"javascript:checkUseYN(this);\">";
	_addTrHTML += "<span>사용안함</span>";
	_addTrHTML += "</label>";
	_addTrHTML += "</div>";
	_addTrHTML += "</td>";
	_addTrHTML += "<td></td>";
	_addTrHTML += "<td></td>";
	_addTrHTML += "<td><a class=\"btn btn-mod btn-full\" href=\"javascript:void(0);\" onclick=\"javascript:categoryAdd(this);\"><i class=\"fa  fa-pencil\"></i> 등록</a></td>";
	_addTrHTML += "</tr>";
	
	$('#docCategoryTBL tbody').append(_addTrHTML);
	$("input[name='cat_nm']:last").focus();	//마지막 생성 된 카테고리명(input)에 마우스 커서 focus
	
	
}

// 발전문서 > 카테고리 수정 상태로 변경
function categoryUpdate(pItem) {
	var _tr = pItem.parentElement.parentElement.parentElement;
	var _td = _tr.children;
	
	// 카테고리명 수정 상태로 변경
	_td[2].children[0].hidden = true;
	_td[2].children[1].style.display = "block";

	// 순번 수정 상태로 변경
	_td[3].children[0].hidden = true;
	_td[3].children[1].style.display = "block";
	
	// 사용여부 체크박스 disable 해제
	_td[4].children[0].children[0].children[0].disabled = false;
	
	// 버튼 수정 -> 적용 변경
	_td[7].children[0].style.display = "none";
	_td[7].children[1].style.display = "block";
}

// 발전문서 > 카테고리 수정 반영
function categoryUpdateSave(pItem) {
	var _tr = pItem.parentElement.parentElement.parentElement;
	var _td = _tr.children;
	
	var _checked = _td[4].children[0].children[0].children[0].checked;
	var _checkYN = "";
	
	if ( _checked ) _checkYN = "Y";
	else _checkYN = "N";
	
	ajax.data = {
			'cat_idx': _td[0].children[0].children[1].value,
			'cat_nm': _td[2].children[1].children[0].value,
			'order_no': _td[3].children[1].children[0].value,
			'use': _checkYN
	};
	ajax.url = context + "/document/categoryUpdate";
	ajax.type = "POST";
	ajax.success = function(data) {
		if ( data.message == "ok" ) {
			message.show(message.info, "카테고리 수정이 성공하였습니다.", ALERT, function() {
				location.href = context + "/document/categoryManage.do";
			});
		} else {
			message.show(message.warning, "카테고리 수정이 실패되었습니다.", ALERT);
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

// 발전문서 > 카테고리 추가 로직
function categoryAdd(pItem) {
	var _tr = pItem.parentElement.parentElement;
	var _td = _tr.children;
	
	ajax.data = {
			'cat_nm': _td[2].children[0].value,
			'order_no': _td[3].children[0].value
	};
	ajax.url = context + "/document/category";
	ajax.type = "POST";
	ajax.success = function(data) {
		if ( data.message == "ok" ) {
			message.show(message.info, "카테고리 등록이 성공하였습니다.", ALERT, function() {
				location.href = context + "/document/categoryManage.do";
			});
		} else {
			message.show(message.warning, "카테고리 등록이 실패되었습니다.", ALERT);
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

// 카테고리 삭제
function categoryDelete() {
	var _checkLen = $("input:checkbox[name=categoryCheck]:checked").length;
	
	if ( _checkLen == 0 ) {
		message.show(message.warning, "삭제 할 대상을 선택하여 주시기 바랍니다.", ALERT);
	} else {
		message.show(message.success, "삭제를 진행하시겠습니까?", CONFIRM, function() {
			var _check = $("input:checkbox[name=categoryCheck]:checked");
			
			var _list = [];
			for ( var i=0; i<_check.length; i++ ) {
				var _obj = {};
				
				_obj.cat_idx = _check[i].parentElement.nextElementSibling.value;
				_list.push(_obj);
			}
			
			ajax.data = {
				"jsonObj": JSON.stringify(_list)
			};
			
			ajax.url = context + "/document/categoryDelete";
			ajax.type = "POST";
			ajax.success = function(data) {
				if ( data.message == "ok" ) {
					message.show(message.success, "삭제가 완료되었습니다.", ALERT, function() {
						location.href = context + "/document/categoryManage.do";
					});
				} else {
					message.show(message.warning, message.message, ALERT);
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
		});
	}
}

// 발전문서 등록
function documentAdd() {
	
	var _form = $('#docForm')[0];
	var _data = new FormData(_form);
	var _isValid = false;
	
	DOCUMENT_INFO = {
			cat_idx: '',
			title: '',
			contents: '',
			keyword: '',
			files : ''
	};
	
	DOCUMENT_INFO.cat_idx = $('#cat_idx option:selected').val();
	DOCUMENT_INFO.title = $('#docTitle').val();
	DOCUMENT_INFO.contents = $('#docContents').val();
	DOCUMENT_INFO.keyword = $('#docKeyword').val();
	DOCUMENT_INFO.files = $('#files')[0].files;
	
	_isValid = documentValid(DOCUMENT_INFO);
	
	if ( _isValid ) {
		$.ajax({
			type: "POST",
			enctype: 'multipart/form-data', // 필수
			url: context + "/document/document",
			data: _data,
			processData: false,
			contentType: false,
			cache: false,
	        timeout: 600000,
	        beforeSend: function () {
				$('#loading').show();
	        },
			success: function(data) {
				if ( data.message == "ok" ) {
					message.show(message.success, "등록이 완료 되었습니다.", ALERT, function() {
						location.href = context + "/document/docSearch.do";
					});
				}
			},
			error: function(e) {
				message.show(message.warning, "등록이 실패 되었습니다.", ALERT);
			},
			complete: function() {
				$('#loading').hide();
			}
		});
	}
	
}

// 발전문서 등록 시 입력데이터 체크
function documentValid(pDocument) {
	if ( pDocument.cat_idx == "" || pDocument.cat_idx == null ) { message.show(message.warning, "카테고리 정보가 없습니다.", ALERT); return false; }
	
	if ( pDocument.title == "" || pDocument.title == null ) { message.show(message.warning, "제목이 비어 있습니다.", ALERT); return false; }
	
	// 요약
	if ( pDocument.contents == "" || pDocument.contents == null ) { message.show(message.warning, "요약(설명)이 비어있습니다..", ALERT); return false; }
	else if ( pDocument.contents.length > 500 ) { message.show(message.warning, "요약(설명)이 500자를 넘었습니다.", ALERT); return false; }
	
	// 키워드
	if ( pDocument.keyword == "" || pDocument.keyword == null ) { 
		message.show(message.warning, "키워드가 비어있습니다.", ALERT); 
		return false; 
	}
	else if ( pDocument.keyword.indexOf(",") < 0 && pDocument.keyword.indexOf(" ") < 0 ) { 
		message.show(message.warning, "키워드를 3개 이상 입력 바랍니다.", ALERT); 
		return false; 
	} else {
		var _length = 0;
		if ( pDocument.keyword.indexOf(" ") > -1) {
			_length = pDocument.keyword.split(" ").length;
		} else {
			_length = pDocument.keyword.split(",").length;
		}
		
		if ( _length < 3 ) {
			message.show(message.warning, "키워드를 3개 이상 입력 바랍니다.", ALERT);
			return false;
		}
	}
	
	// 첨부파일
	if ( pDocument.files.length < 1 ) {
		message.show(message.warning, "첨부파일이 비어있습니다.", ALERT);
		return false;
	}
	
	return true;
}

//발전문서 수정 시 체크 항목 수 검사
function docUpdatePage() {
	var _checkLen = $("input:checkbox[name=docCheck]:checked").length;
	
	if ( _checkLen > 1 ) {
		message.show(message.warning, "체크 항목 수가 많습니다. 하나만 선택 바랍니다.", ALERT);
	} else if ( _checkLen < 1 ) {
		message.show(message.warning, "체크 된 항목이 없습니다. 수정 할 대상을 선택 해 주시기 바랍니다.", ALERT);
	} else {

		var _check = $("input:checkbox[name=docCheck]:checked");
		var _checkId = _check[0].parentElement.nextElementSibling.value;
		
		// 수정 페이지로 이동
		location.href = context + "/document/docModify.do?doc_idx=" + _checkId;
	}
}

//발전문서 수정
function docUpdate() {
	
	var _idx = $('#doc_mod_idx').val();
	
	ajax.data = {
			'cat_idx': $('#cat_mod_idx option:selected').val(),
			'cat_nm': $('#cat_mod_idx option:selected').text(),
			'docTitle': $('#doc_mod_title').val(),
			'docContents': $('#doc_mod_contents').val(),
			'docKeyword': $('#doc_mod_keyword').val()
	};
	
	ajax.url = context + "/document/document/" + _idx;
	ajax.type = "POST";
	ajax.success = function(data) {
		if ( data.message == "ok" ) {
			message.show(message.success, "수정이 완료되었습니다.", CONFIRM, function() {
				location.href = context + "/document/docSearch.do";
			});
		} else {
			message.show(message.warning, message.message, ALERT);
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
}

// 발전문서 삭제
function docDelete() {
	var _checkLen = $("input:checkbox[name=docCheck]:checked").length;
	
	if ( _checkLen == 0 ) {
		message.show(message.warning, "삭제 할 대상을 선택하여 주시기 바랍니다.", ALERT);
	} else {
		message.show(message.success, "삭제를 진행하시겠습니까?", CONFIRM, function() {
			var _check = $("input:checkbox[name=docCheck]:checked");
			
			var _list = [];
			for ( var i=0; i<_check.length; i++ ) {
				var _obj = {};
				
				_obj.doc_idx = _check[i].parentElement.nextElementSibling.value;
				_list.push(_obj);
			}
			
			ajax.data = {
				"jsonObj": JSON.stringify(_list)
			};
			
			ajax.url = context + "/document/documentDelete";
			ajax.type = "POST";
			ajax.success = function(data) {
				if ( data.message == "ok" ) {
					message.show(message.success, "삭제가 완료되었습니다.", ALERT, function(){
						location.href = context + "/document/docSearch.do";
					});
				} else {
					message.show(message.warning, message.message, ALERT);
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
		});
	}
}

// 발전문서 검색
function docSearch(pKwd) {
	
	var _kwd = "";
	if ( pKwd == null || pKwd == "" || pKwd == undefined ) _kwd = $('#docKwd').val();
	else {
		_kwd = pKwd;
	}
	
	var _limit = $('#docLimit').val();
	
	// select 선택된 값 가져오기.
	const selectedField = document.getElementById("srcFields").value;
	
	// 검색 데이터 set
	$('#his_docKwd').val(_kwd);
	$('#his_docPageNum').val(1);
	$('#his_docPageSize').val(_limit);
	$('#his_docFields').val(selectedField);
	
	// 검색용 form submit
	$('#docForm').submit();
}

// 발전문서 검색 ( 키워드 클릭 검색.)
function docSearchKwdList(kwdList) {
	
	var _limit = $('#docLimit').val();
	
	// 검색 데이터 set
	$('#his_docKwd').val('');
	$('#his_docPageNum').val(1);
	$('#his_docPageSize').val(_limit);
	$('#his_docKwdList').val(kwdList);
	
	// 검색용 form submit
	$('#docForm').submit();
}