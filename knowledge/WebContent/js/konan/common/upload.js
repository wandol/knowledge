var UPLOAD_INFO = {
		upLoadReportName : '',
		upLoadPowerComp : '',
		upLoadPowerSt : '',
		upLoadStNo : '',
		upLoadPowerType : '',
		upLoadPublishYM : '',
		upLoadRepoKind : '',
		file : ''
};

var upload = {
	show : function(id, formId) {
		// Upload Form 초기화
		$('#' + formId)[0].reset();
		// Upload Modal show.
		$('#' + id).dialog('open');
	},
	close : function(id) {
		
		$('#' + id).dialog('close');
	},
	setOption : function(optData) {
		console.log("upload.js");
		optData.isPF = "N";
		optData.cate = $('#upLoadPowerType option:selected').val();
		
		ajax.data = optData;
		ajax.type = "GET";
		ajax.url = context + "/comm/getOptionList.do";
		
		ajax.success = function(data) {

			if ( data.isPowerComp == "success" && data.powerCompList.length > 0 ) { commonUploadDrawOption("upLoadPowerComp", data.powerCompList, "POWER_COMP_NM", optData.powerCompName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isPowerSt == "success" && data.powerStList.length > 0 ) { commonUploadDrawOption("upLoadPowerSt", data.powerStList, "POWER_ST_NM", optData.powerStName); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
			if ( data.isStNo == "success" && data.stNoList.length > 0 ) { commonUploadDrawOption("upLoadStNo", data.stNoList, "ST_NO", optData.stNo); } // 셀렉트 박스 ID, 리스트 data, 데이터 조회 할 key, 선택 된 값
			
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
	},
	getMeta : function() {	// 메타정보 추출
		var _data = new FormData();
		_data.append("file", $('#file').prop('files')[0]);
		
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
            	$('#uploadLoading').show();
            },
            success: function (data) {
            	
            	if ( data.message != undefined && data.message != "" ) {
            		message.show(message.warning, "파일에서 원문 추출을 실패하였습니다. (" + data.message + ")", ALERT);
            	} else {
            		
            		var _filebody = data.fileBody;
            		var _meta = data.metaInfo;
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
                    	}
                		
                		$('#upLoadPowerType').val(_cate);
                		
                		// 발전사, 사업소, 호기값 파라미터
                		optionData.powerCompName = _meta.company;
                		optionData.powerStName = _meta.power_st;
                		optionData.stNo = _meta.st_no + "호기";
                		
                		// 발전사, 사업소, 호기값 셋팅
                		upload.setOption(optionData);
                		
                		$('#upLoadReportName').val(_meta.report_nm);
                		$('#upLoadPublishYM').val(_meta.test_day);
                		if ( _kind != "" && _kind != null ) {
                			$('#upLoadRepoKind').val(_kind);
                			$('#upLoadRepoKind').removeAttr('disabled');
                		} else {
                			// _kind값이 없을땐 초기화 및 선택 못하게 막기
                			$('#upLoadRepoKind').val("all");
                			$('#upLoadRepoKind').attr('disabled', 'true');
                		}
                    	
                		$("input[name='upLoadRepoKind']").val(_meta.report_nm);
					});
            	}
            },
            error: function (e) {
            	message.show(message.noti, "업로드 실패");
            },
            complete:function(){
            	$('#btn_upload').css('cursor', 'pointer');
            	$('#btn_upload').removeAttr('disabled');
            	$('#btn_upload').attr('title','[업로드] 클릭시 지식분석을 시작합니다.');
            	$('#uploadLoading').hide(); 
            }
		});
	},
	upload : function(param, id) {
		
		$.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: context + "/analysis/upload",
            data: param,
            dataType: "json",
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            beforeSend: function () {
            	$('#uploadLoading').show();
            },
            success: function (data) {
            	
            	if ( data.uploadResult == "ok" ) {
	            	message.show(message.success, "업로드 성공!", ALERT, function() {
            			$('#' + id).dialog('close');
					});
	            	
            	}else{
            		if ( data.message == "duplicate" ) {
            			message.show(message.warning, "중복 된 파일이 있습니다.<br>확인을 누르시면 기존 데이터는 삭제 되고 재 분석 됩니다.<br>계속 진행 하시겠습니까?", CONFIRM, function() {
            			
            				var _formData = new FormData();
            				_formData.append('upLoadReportName', data.uploadInfo.upLoadReportName);
            				_formData.append('upLoadPowerComp', data.uploadInfo.upLoadPowerComp);
            				_formData.append('upLoadPowerSt', data.uploadInfo.upLoadPowerSt);
            				_formData.append('upLoadStNo', data.uploadInfo.upLoadStNo);
            				_formData.append('upLoadPowerType', data.uploadInfo.upLoadPowerType);
            				_formData.append('upLoadPublishYM', data.uploadInfo.upLoadPublishYM);
            				_formData.append('upLoadRepoKind', data.uploadInfo.upLoadRepoKind);
            				_formData.append('fileName', data.uploadInfo.fileName);
            				_formData.append('fileSize', data.uploadInfo.fileSize);
            				_formData.append('md5FileNmAndSize', data.uploadInfo.md5FileNmAndSize);
            				_formData.append('isRework', "Y");
            				
            				upload.upload(_formData, id);
						});
            		} else {
            			message.show(message.warning, "업로드 실패(" + data.message + ")", ALERT);
            		}
            		
            	}
            },
            complete:function(){
            	$('#uploadLoading').hide(); 
            },
            error: function (e) {
            	console.log(e);
            	message.show(message.warning, "업로드 실패", ALERT);
            }
        });
		
		
	},
	validCheck : function(data) {
		var _reportName = data.upLoadReportName;
		var _uploadPowerComp = data.upLoadPowerComp;
		var _uploadPowerST = data.upLoadPowerSt;
		var _uploadStNo = data.upLoadStNo;
		var _uploadPowerType = data.upLoadPowerType;
		var _uploadPublishYM = data.upLoadPublishYM;
		var _uploadRepoKind = data.upLoadRepoKind;
		var _uploadFile = data.file;
		
		if ( !validInputText("보고서명", _reportName) ) { message.show(message.warning, "보고서명을 입력하세요", ALERT); return false; }
		if ( !validSelectBox("발전사", _uploadPowerComp) ) { message.show(message.warning, "발전사를 선택하세요.", ALERT); return false; }
		if ( !validSelectBox("사업소", _uploadPowerST) ) { message.show(message.warning, "사업소를 선택하세요.", ALERT); return false; }
		if ( !validSelectBox("호기", _uploadStNo) ) { message.show(message.warning, "호기를 선택하세요.", ALERT); return false; }
		if ( !validSelectBox("분야", _uploadPowerType) ) { message.show(message.warning, "분야를 선택하세요.", ALERT); return false; }
		if ( !validInputText("출판년도", _uploadPublishYM) ) { message.show(message.warning, "출판년도를 입력하세요.", ALERT); return false; }
		
		// 날짜 포맷 체크
		if ( !validDateFormat(_uploadPublishYM) ) return false;
		
		// 발전기(절연,예방,누설흡습),성능의 경우 정밀,고장으로 구분되어 지지 않음.
		if ( _uploadPowerType != "GEN_PREV" && _uploadPowerType != "GEN_INS" && _uploadPowerType != "KEPRI" && _uploadPowerType != "PERFORM") {
			if ( _uploadRepoKind == "" || _uploadRepoKind == null || _uploadRepoKind == undefined ) {
				message.show(message.warning, "보고서 종류를 선택하세요.", ALERT);
				return false;
			}
		}
		
		// 첨부파일 체크
		if ( _uploadFile.name == "" || _uploadFile.name == null || _uploadFile.name == undefined ) {
			message.show(message.warning, "첨부된 파일이 없습니다.", ALERT);
			return false;
		} else if ( _uploadFile.size == "" || _uploadFile.size == null || _uploadFile.size == undefined ) {
			message.show(message.warning, "첨부된 파일 사이즈가 0 입니다.", ALERT);
			return false;
		}
		
		$('#file-name').val(_uploadFile.name);
		
		return true;
	}
}

// upload창 open
function showUpload() {
	//보고서 업로드창 호출
	makeUploadDialog();
	
	upload.show("upload", "uploadDialogForm");
	$('#btn_upload').attr('disabled','true');
	return false;
}

// upload창 생성
function makeUploadDialog(){

	// 페이지 로딩 시 history form에서 데이터를 불러온다. 
	// 1. option 데이터 조회 조건
	// 2. option 데이터 새로 select box에 그릴 때 selected 상태 처리용
	optionData.powerCompName = "all";
	optionData.powerStName = "all";
	optionData.stNo = "all";
	
	// 검색 옵션 초기화
	upload.setOption(optionData);
	
	// 업로드 Modal 셋팅
	$("#upload").dialog({
		autoOpen : false,
		width : 1000,
		resizable : false,
		modal : true,
		title : "<div class='widget-header'><h4>보고서 업로드</h4></div>",
		buttons : [{
			html : "<i class='fa fa-download'></i>&nbsp; 문서정보 추출",
			"class" : "btn btn-default",
			click : function() {
				
				// 메타정보 추출 전 파일이 정상적인지 확인.
				var _file = $('#file')[0].files[0];
				if ( _file == undefined || _file.name == "" || _file.name == null || _file.name == undefined ) {
					message.show(message.warning, "첨부된 파일이 없습니다.", ALERT);
					return false;
				} else if ( _file.size == "" || _file.size == null || _file.size == undefined ) {
					message.show(message.warning, "첨부된 파일 사이즈가 0 입니다.", ALERT);
					return false;
				}
				
				upload.getMeta();
			}
		},{
			html : "<i class='fa fa-upload'></i>&nbsp; 업로드",
			"class" : "btn btn-default",
			id : "btn_upload",
			click : function() {
				var _form = $('#uploadDialogForm')[0];
				var _data = new FormData(_form);
				
				// IE에서 Get 함수가 없어서 하나씩 꺼낸 뒤 vaild check
				UPLOAD_INFO.upLoadReportName = $('#upLoadReportName').val();
				UPLOAD_INFO.upLoadPowerComp = $('#upLoadPowerComp').val();
				UPLOAD_INFO.upLoadPowerSt = $('#upLoadPowerSt').val();
				UPLOAD_INFO.upLoadStNo = $('#upLoadStNo').val();
				UPLOAD_INFO.upLoadPowerType = $('#upLoadPowerType').val();
				UPLOAD_INFO.upLoadPublishYM = $('#upLoadPublishYM').val();
				UPLOAD_INFO.upLoadRepoKind = $('#upLoadRepoKind').val();
				UPLOAD_INFO.file = $('#file')[0].files[0];
				
				var _isValid = upload.validCheck(UPLOAD_INFO);
				if ( _isValid ) {
					var _isUpload = upload.upload(_data, "upload");
				}
				
			}
		},{
			html : "<i class='fa fa-times'></i>&nbsp; 닫기",
			"class" : "btn btn-default",
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
}

$(document).ready(function() {
	
	// 로딩바 감추기
	$('#uploadLoading').hide();
	
	
	$('#btn_upload').attr('disabled','true');
	$('#btn_upload').attr('title','[문서정보  추출]을 먼저 진행하세요.');
	
	// 분야 선택 시
	$('#upLoadPowerType').change(function() {
		// 선택한 분야
		var _category = this.value;
		
		if (_category != "BOILER" && _category != "GT_TURBINE" && _category != "ST_TURBINE" ) {
			$('#upLoadRepoKind').val('all');
			$('#upLoadRepoKind').attr('disabled', 'true');
		} else {
			$('#upLoadRepoKind').removeAttr('disabled');			
		}
	});
	
	// 발전사 선택 시
	$('#upLoadPowerComp').change(function() {
		// 선택한 발전사
		var _powerComp = this.value;
		optionData.powerCompName = _powerComp;
		optionData.powerStName = "all";
		optionData.stNo = "all";
		
		upload.setOption(optionData);
	});
	
	// 사업소 선택 시
	$('#upLoadPowerSt').change(function() {
		// 선택한 사업소
		var _powerSt = this.value;
		optionData.powerCompName = $('#upLoadPowerComp').val();
		optionData.powerStName = _powerSt;
		optionData.stNo = "all";
		
		upload.setOption(optionData);
	});
	
	// 호기 선택 시
	$('#upLoadStNo').change(function() {
		// 선택한 호기
		var _stNo = this.value;
		optionData.powerCompName = $('#upLoadPowerComp').val();
		optionData.powerStName = $('#upLoadPowerSt').val();
		optionData.stNo = _stNo;
		
		upload.setOption(optionData);
	});
	
	$('#upload').hide();
});
