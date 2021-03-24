// 공통 변수

var context = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
var optionAll = "<option value='all'>전체</option>";
var dateReg = /[0-9]{4}.[0-9]{2}./;	// 날짜포맷 체크
var ALERT = "alert";
var CONFIRM = "confirm";
var ajax = {
		type: "GET",
		url: "",
		data: {},
		success: {},
		error: {},
		done: {},
		beforeSend : function() {
			$('#loading').show();
		},
		complete: function() {
			$('#loading').hide();
		}
};

var optionData = {
		isPF: "N",	// 지식DB인지 체크
		cate: "",
		powerCompName: "all",	// 발전사
		powerStName: "all",		// 사업소
		stNo: "all",			// 호기
		partName: "all",		// 부위
		symptomKwd: "all",		// 현상
		actionKwd: "all"		// 대책
};

var modalOption = {
		width: '',
		height: ''
}

//slider
var kepriSlider;
var pfSlider;
var pfCommentSlider;

//common modal opt
var COMMON_MODAL_OPTION = {
		width: 600,
		height: 660
}