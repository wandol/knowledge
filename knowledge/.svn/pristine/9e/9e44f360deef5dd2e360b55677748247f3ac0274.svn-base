/**
 * dashboard 페이지에 대한 js
 * 
 * 변수 명명 규칙 : 1. 글로벌 변숭의 경우 앞에 g를 붙인다.
 *              2. 파라미터의 경우 앞에 p를 붙인다
 *              3. 내부변수의 경우 앞에 _를 붙인다.
 *              4. HTML을 만드는 변수의 경우 끝에 HTML을 붙인다
 */

var gListLimit = 10; // 최대 리스트 출력 개수 기본값
var loginUrl = $('#his_loginUrl').val();

$(document).ready(function() {
	// 최초 대시보드 로드
	dashboardInit();
	
	// 버튼 클릭시
	$('.dashButtonDiv > div').children('a').click(function(){
		var _value = $(this).attr('val');
		var _chartType = $(this).attr('chartType');
		var _cate = $(this).parents('.dashButtonDiv').attr('category');
		var _limit = 5;
		
		$(this).siblings().removeClass('btn-select').addClass('btn-default');
		$(this).addClass('btn-select');
		
		if(_value == "sym"){
			if(_chartType == "bar"){
				setSymptom(_cate,"bar",_limit);
			}else{
				setSymptom(_cate, 'pie', _limit);
			}
		}else if(_value == "act"){
			if(_chartType == "bar"){
				setAction(_cate,"bar",_limit);
			}else{
				setAction(_cate,"pie",_limit);
			}
		}else if(_value == "comp"){
			setCompName(_cate,_chartType,_limit);
		}else if(_value == "powerSt"){
			setPowerStName(_cate,_chartType,_limit);
		}else{
			if(_chartType == "bar"){
				setPartName(_cate,"bar",_limit);
			}else{
				setPartName(_cate,"pie",_limit);
			}
		}
	
	});
	
});

function dashboardInit(){
	//보일러
	setPartName('BOILER','pie',5);
	
	//가스터빈
	setPartName('GT_TURBINE','pie',5);
	
	//증기터빈
	setPartName('ST_TURBINE','pie',5);
		
	//발전기 예방진단
	setPartName('GEN_PREV','bar',5);
	
	//발전기 절연진단
	setCompName('GEN_INS','hBar',5);

	//발전기 누설흡수
	setCompName('KEPRI','line',5);
}

/**
 * 진단 부위
 * @param pCate : 카테고리
 * @param pType : 출력 형태
 * @param pLimit : 그래프 출력 개수
 * @returns
 */
function setPartName(pCate, pType, pLimit, pOptionData){
	var _titleText = "";
	var _optionData = {};
		_optionData.cate = pCate;
		_optionData.job = "part";
		_optionData.source = "part";
		
	if(pOptionData != undefined){
		if(pOptionData.symptomKwd != undefined && pOptionData.symptomKwd != '' ){
			_titleText =  pOptionData.symptomKwd;
			_optionData.symptomKwd = pOptionData.symptomKwd;
		}
		
		if(pOptionData.actionKwd != undefined && pOptionData.actionKwd != '' ){
			_titleText =  pOptionData.actionKwd;
			_optionData.actionKwd = pOptionData.actionKwd;
		}
	}
	
	ajax.data = _optionData;
	ajax.type = "GET";
	ajax.url = context + "/dashboard/getResultData.do";
	
	ajax.success =  function(pData) {
		var _graphData = [];
		var _dataFieldName = "";
		var _partname = "";
			
		if(pCate == "BOILER"){
			_dataFieldName = "PARTNAME";
		}else{
			_dataFieldName = "PARTNAME_KWD";
		}

		if(pData.isPartName == "success"){
			for (var i=0; i < pData.partNameList.length && i < gListLimit; i++) {
				if(i==0) _partname = pData.partNameList[i][_dataFieldName];
				
				_graphData[i] = {
						label : pData.partNameList[i][_dataFieldName],
						data : pData.partNameList[i].count
				}
			}
			_optionData.partName = _partname;
			
			if(pType == "pie"){
				drawPieChart(_graphData, pCate, pLimit, _optionData);
				setSymptom(pCate, 'list', pLimit, _optionData);
			}else if(pType == "bar"){
				drawBarChart(_graphData, pCate, pLimit, _optionData);
			}else if(pType == "hBar"){
				drawHorizonBarChart(_graphData, pCate, pLimit, _optionData);
			}else{
				drawTableList(_graphData,pCate,_titleText,'진단부위',_optionData);
				if(pOptionData.source == "act"){
					setSymptom(pCate, 'net', pLimit, _optionData);
				}else{
					setAction(pCate, 'radar', pLimit, _optionData);
				}
				
			}
		}
	};;
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {				
			message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = loginUrl;
			});
		}
	}
	
	// ajax 실행
	$.ajax(ajax);
}

/**
 * 현상
 * @param pCate : 카테고리
 * @param pType : 출력 형태
 * @param pLimit : 그래프 출력 개수
 * @returns
 */
function setSymptom(pCate, pType, pLimit, pOptionData){
	var _optionData = {};
		_optionData.cate = pCate;
		_optionData.job = "sym";
		_optionData.source = "sym";
		
	if(pOptionData != undefined){
		if(pOptionData != undefined && pOptionData.partName != '' ){
			_optionData.partName = pOptionData.partName;
		}
		
		if(pOptionData.actionKwd != undefined && pOptionData.actionKwd != '' ){
			_optionData.actionKwd = pOptionData.actionKwd;
		}
	}
		
	ajax.data = _optionData;
	ajax.type = "GET";
	ajax.url = context + "/dashboard/getResultData.do";
	
	ajax.success =  function(pData) {
		var _graphData = [];
		var _symtomkwd = "";
		
		if(pData.isSymptom == "success"){
			for (var i=0; i < pData.symptomKwdList.length && i < gListLimit; i++) {
				if(i==0) _symtomkwd = pData.symptomKwdList[i].SYMPTOM_KWD;
				
				_graphData[i] = {
						label : pData.symptomKwdList[i].SYMPTOM_KWD,
						data : pData.symptomKwdList[i].count
				}
			}
			
			
			if(pType == "pie"){
				_optionData.symptomKwd = _symtomkwd; // 필터링용 첫번째 값
				drawPieChart(_graphData,pCate,pLimit,_optionData);
				setPartName(pCate, 'list', pLimit, _optionData);
			}else if(pType == "net"){
				_optionData.symptomKwd = _symtomkwd; // 필터링용 첫번째 값
				drawNetworkMapChart(_graphData,pCate,_optionData);
			}else if(pType == "bar"){
				drawBarChart(_graphData, pCate, pLimit, _optionData);
			}else{
				drawTableList(_graphData,pCate,pOptionData.partName,'현상',_optionData);
				callDendrogramData(pCate,pOptionData.partName);
			}
		}
	};;
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {				
			message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = loginUrl;
			});
		}
	}
	
	// ajax 실행
	$.ajax(ajax);
}

/**
 * 대책 데이터
 * @param pCate : 카테고리
 * @param pType : 출력 형태
 * @param pLimit : 그래프 출력 개수
 * @returns
 */
function setAction(pCate, pType, pLimit, pOptionData){
	var _optionData = {};
		_optionData.cate = pCate;
		_optionData.job = "act";
		_optionData.source = "act";
		
	if(pOptionData != undefined){
		if(pOptionData.partName != undefined && pOptionData.partName != '' ){
			_optionData.partName = pOptionData.partName;
		}
		
		if(pOptionData.symptomKwd != undefined && pOptionData.symptomKwd != '' ){
			_optionData.symptomKwd = pOptionData.symptomKwd;
		}
	}
	
	ajax.data = _optionData;
	ajax.type = "GET";
	ajax.url = context + "/dashboard/getResultData.do";
	
	ajax.success =  function(pData) {
		var _graphData = [];
		var _actionKwd = "";
		
		if(pData.isAction == "success"){
			
			for (var i = 0; i < pData.actionKwdList.length && i < gListLimit; i++) {
				if(i==0) _actionKwd = pData.actionKwdList[i].ACTION_KWD;
				
				_graphData[i] = {
						label : pData.actionKwdList[i].ACTION_KWD,
						data : pData.actionKwdList[i].count
				}
			}
			
			if(pType == "pie"){
				_optionData.actionKwd = _actionKwd;
				drawPieChart(_graphData,pCate,pLimit,_optionData);
				setPartName(pCate, 'list', pLimit, _optionData);
			}else if(pType == "bar"){
				drawBarChart(_graphData, pCate, pLimit, _optionData);
			}else if(pType == "radar"){
				_optionData.actionKwd = _actionKwd;
				drawRadarChart(_graphData,pCate,_optionData);
			}else{
				drawTableList(_graphData,pCate,_actionKwd,'진단부위',_optionData);
			}
		}
	};
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {				
			message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = loginUrl;
			});
		}
	}
	
	// ajax 실행
	$.ajax(ajax);
}

/**
 * 발전사
 * @param pCate : 카테고리
 * @param pType : 출력 형태
 * @param pLimit : 그래프 출력 개수
 * @returns
 */
function setCompName(pCate, pType, pLimit, pOptionData){
	var _titleText = "";
	var _optionData = {};
		_optionData.cate = pCate;
		_optionData.job = "comp";
		_optionData.source = "part";
		
	if(pOptionData != undefined){
		if(pOptionData.symptomKwd != undefined && pOptionData.symptomKwd != '' ){
			_titleText =  pOptionData.symptomKwd;
			_optionData.symptomKwd = pOptionData.symptomKwd;
		}
		
		if(pOptionData.actionKwd != undefined && pOptionData.actionKwd != '' ){
			_titleText =  pOptionData.actionKwd;
			_optionData.actionKwd = pOptionData.actionKwd;
		}
	}
	
	ajax.data = _optionData;
	ajax.type = "GET";
	ajax.url = context + "/dashboard/getResultData.do";
	
	ajax.success =  function(pData) {
		var _graphData = [];
		var _dataFieldName = "";
		var _partname = "";
			
		if(pCate == "BOILER"){
			_dataFieldName = "PARTNAME";
		}else{
			_dataFieldName = "POWER_COMP_NM";
		}

		if(pData.isPowerComp == "success"){
			for (var i=0; i < pData.powerCompList.length && i < gListLimit; i++) {
				if(i==0) _partname = pData.powerCompList[i][_dataFieldName];
				
				_graphData[i] = {
						label : pData.powerCompList[i][_dataFieldName],
						data : pData.powerCompList[i].count
				}
			}
			_graphData.sort(function(a, b) { 
			    return Number(a.data) > Number(b.data) ? -1 : Number(a.data) < Number(b.data) ? 1 : 0;
			});
			_optionData.partName = _partname;
			
			if(pType == "pie"){
				drawPieChart(_graphData, pCate, pLimit, _optionData);
				setSymptom(pCate, 'list', pLimit, _optionData);
			}else if(pType == "bar"){
				drawBarChart(_graphData, pCate, pLimit, _optionData);
			}else if(pType == "hBar"){
				drawHorizonBarChart(_graphData, pCate, pLimit, _optionData);
			}else if(pType == "line"){
				drawLineChart(_graphData, pCate, pLimit, _optionData);
			}else{
				drawTableList(_graphData,pCate,_titleText,'진단부위',_optionData);
				if(pOptionData.source == "act"){
					setSymptom(pCate, 'net', pLimit, _optionData);
				}else{
					setAction(pCate, 'radar', pLimit, _optionData);
				}
				
			}
		}
	};;
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {				
			message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = loginUrl;
			});
		}
	}
	
	// ajax 실행
	$.ajax(ajax);
}

/**
 * 사업소
 * @param pCate : 카테고리
 * @param pType : 출력 형태
 * @param pLimit : 그래프 출력 개수
 * @returns
 */
function setPowerStName(pCate, pType, pLimit, pOptionData){
	var _titleText = "";
	var _optionData = {};
		_optionData.cate = pCate;
		_optionData.job = "powerSt";
		_optionData.source = "part";
		
	if(pOptionData != undefined){
		if(pOptionData.symptomKwd != undefined && pOptionData.symptomKwd != '' ){
			_titleText =  pOptionData.symptomKwd;
			_optionData.symptomKwd = pOptionData.symptomKwd;
		}
		
		if(pOptionData.actionKwd != undefined && pOptionData.actionKwd != '' ){
			_titleText =  pOptionData.actionKwd;
			_optionData.actionKwd = pOptionData.actionKwd;
		}
	}
	
	ajax.data = _optionData;
	ajax.type = "GET";
	ajax.url = context + "/dashboard/getResultData.do";
	
	ajax.success =  function(pData) {
		var _graphData = [];
		var _dataFieldName = "";
		var _partname = "";
			
		if(pCate == "BOILER"){
			_dataFieldName = "PARTNAME";
		}else{
			_dataFieldName = "POWER_ST_NM";
		}

		if(pData.isPowerSt == "success"){
			for (var i=0; i < pData.powerStList.length && i < gListLimit; i++) {
				if(i==0) _partname = pData.powerStList[i][_dataFieldName];
				
				_graphData[i] = {
						label : pData.powerStList[i][_dataFieldName],
						data : pData.powerStList[i].count
				}
			}
			_optionData.partName = _partname;
			// 내림차순 정렬
			_graphData.sort(function(a, b) { 
			    return Number(a.data) > Number(b.data) ? -1 : Number(a.data) < Number(b.data) ? 1 : 0;
			});
			
			if(pType == "pie"){
				drawPieChart(_graphData, pCate, pLimit, _optionData);
				setSymptom(pCate, 'list', pLimit, _optionData);
			}else if(pType == "bar"){
				drawBarChart(_graphData, pCate, pLimit, _optionData);
			}else if(pType == "line"){
				drawLineChart(_graphData, pCate, pLimit, _optionData);
			}else if(pType == "hBar"){
				drawHorizonBarChart(_graphData, pCate, pLimit, _optionData);
			}else{
				drawTableList(_graphData,pCate,_titleText,'진단부위',_optionData);
				if(pOptionData.source == "act"){
					setSymptom(pCate, 'net', pLimit, _optionData);
				}else{
					setAction(pCate, 'radar', pLimit, _optionData);
				}
				
			}
		}
	};;
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {				
			message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = loginUrl;
			});
		}
	}
	
	// ajax 실행
	$.ajax(ajax);
}
// 진단부위에 대한 현상 데이터.
function callDendrogramData(pCate,pPartName){
	var _optionData = {};
		_optionData.cate = pCate;
		_optionData.job = "part_sym";
		_optionData.partName = pPartName; 	//	source 대신 진단부위를 넘겨 현상 리스트를 가져온다.
		
	ajax.data = _optionData;
	ajax.type = "GET";
	ajax.url = context + "/dashboard/getResultData.do";
	
	ajax.success =  function(pData) {
		var _graphData = [];
		var _symtomkwd = "";
		if(pData.isPartSym == "success"){
			_graphData.push({
						id: "super0",
						name: pPartName,
						value: "1000",
						parent: ""
			});
			for (var i=0; i < pData.parSymKwdList.length; i++) {
				//if(i==0) _symtomkwd = pData.parSymKwdList[i].name;
				
				_graphData[i + 1] = {
						id: pData.parSymKwdList[i].id,
						name: pData.parSymKwdList[i].name,
						value: pData.parSymKwdList[i].value,
						parent: pData.parSymKwdList[i].parent
				}
			}
			
			
			drawDendrogramChart(_graphData,pCate,pPartName);
		}
	};
	
	ajax.error = function(xhr, ajaxOptions) {
		if ( xhr.status == 901 ) {				
			message.show(message.warning, "세션이 끊겼습니다. 로그인창으로 이동합니다.", ALERT, function() {
				location.href = loginUrl;
			});
		}
	}
	
	// ajax 실행
	$.ajax(ajax);
	
}


function drawPieChart(pData,pCate,pLimit,pOptionData) {
	pData = pData.slice(0,pLimit);
	
	var _graphOption = {};
		_graphOption.pInnerRadius = 1;
		_graphOption.legendShow = false;
		_graphOption.pie = {
	            show: true,
	            radius: 1,
	            label: {
	                show: true,
	                radius: 3/5,
	                formatter: function(label, series){
	                    return '<div style="font-size:8pt;text-align:center;color:white;"><span>'+label+'</span><br/>'+Math.round(series.percent)+'%</div>';
	                },
	                threshold: 0.1
	                //,background: { opacity: 0.8 }
	            }
			};
	
	var func = function(event, pos, obj){
		if (!obj){return;}
		var _optionData = {};
			_optionData.cate = pCate;
		
		var _partName = "";
		var _symptomKwd = "";
		var _actionKwd = "";
		
		if(pOptionData.job == "sym"){
			_optionData.symptomKwd = obj.series.label;
			_optionData.source = "sym";
			setPartName(pCate, 'list', pLimit, _optionData);
		}else if(pOptionData.job == "act"){
			_optionData.actionKwd = obj.series.label;
			_optionData.source = "act";
			setPartName(pCate, 'list', pLimit, _optionData);
		}else{
			_optionData.partName = obj.series.label;
			_optionData.source = "act";
			setSymptom(pCate, 'list', pLimit, _optionData);
		}
	};
	
	
	drawFlot.pieChart(pData, "dash_" + pCate + "_PieDiv", _graphOption, func);
}

function drawBarChart(pData,pCate,pLimit, pOptionData){
	var _graphData = [];
	for (var i = 0; i < pData.length; i++) {
		_graphData.push([pData[i].label, parseInt(pData[i].data),$colorList[i]]);
	}
	
	drawFlot.barChart(_graphData, "dash_" + pCate + "_Div", $color_first);
	
	$("#dash_" + pCate + "_Div").bind("plothover", function(event, pos, item) {
	    if(item) {
	        document.body.style.cursor = 'pointer';
	    } else {
	        document.body.style.cursor = 'default';
	    }
	});
	
	// 클릭이벤트 처리.
	$("#dash_" + pCate + "_Div").bind("plotclick", function (event, pos, item) {
		if (item) {
			var index = item.dataIndex;
			var data = item.series.data[index][0];
			var type = pOptionData.job;
			if(type=="sym") gotoPagePfSearch(pCate,"",data,"");
			else if(type=="act") gotoPagePfSearch(pCate,"","",data);
			else if(type=="part") gotoPagePfSearch(pCate,data,"","");
			else gotoPagePfSearch(pCate,"","","");
		}
	});	
}

function drawHorizonBarChart(pData,pCate,pLimit, pOptionData){
	var _depthList = pData;
	var _graphData = [];
	for (var i = 0; i < _depthList.length; i++) {
		_graphData.push([parseInt(_depthList[i].data),_depthList[i].label]);
	}
	drawFlot.horizonBarChart(_graphData, "dash_" + pCate + "_Div", $color_second);
	
	$("#dash_" + pCate + "_Div").bind("plothover", function(event, pos, item) {
	    if(item) {
	        document.body.style.cursor = 'pointer';
	    } else {
	        document.body.style.cursor = 'default';
	    }
	});
	// 클릭이벤트 처리.
	$("#dash_" + pCate + "_Div").bind("plotclick", function (event, pos, item) {
		if (item) {
			var index = item.dataIndex;
			var data = item.series.data[index][1];
			var type = pOptionData.job;
			if(type=="powerSt") gotoPagePfSearchNoImg(pCate,"",data);
			else gotoPagePfSearchNoImg(pCate,data,"");
		}
	});	
}

function drawDendrogramChart(pData,pCate,pPartName) {
	var _depthList = pData;
	var _graphData = [];
	for (var i = 0; i < _depthList.length; i++) {
		_graphData[i] = {
			id : _depthList[i].id,
			name : _depthList[i].name,
			value : _depthList[i].value,
			parent : _depthList[i].parent			
		}
	}
	
	var func = function(d){
		if(d.depth == 2){
			var _cate = pCate;
			var _partName = d.parent.parent.data.name;
			var _symptomKwd = d.parent.data.name;
			var _actionKwd = d.data.name;
	    	
			gotoPagePfSearch(_cate,_partName,_symptomKwd,_actionKwd);
		}else{
			var dd = d;
			console.log(d);
		}
	};
	
	// 그래프 네비게이터
	$('#dash_' + pCate + '_BottomTitle').html("<span class=\"txt-deco btn-st1\">" + pPartName + "</span> &gt; <span class=\"dashTTL\">현상  &gt; 조치결과</span>");
	drawDavif.dendrogramChart(_graphData,"dash_" + pCate + "_BottomDiv",func);
}

function drawRadarChart(pData,pCate,pOptionData) {
	var _graphData = [];
	
	for (var i = 0; i < pData.length; i++) {
		_graphData[i] = {
			name : pOptionData.partName,
			date : pData[i].label,
			value : pData[i].data
		}
	}
	
	var func = function(d){
		var _cate = pOptionData.cate;
		var _partName = pOptionData.partName;
		var _symptomKwd = pOptionData.symptomKwd;
		var _actionKwd = "";
		
		if(typeof d == 'string'){
			_actionKwd = d;
		}else{
			_actionKwd = d.date;
		}
		
		gotoPagePfSearch(_cate,_partName,_symptomKwd,_actionKwd);
	};
	
	$('#dash_' + pCate + '_BottomTitle').html("<span class=\"txt-deco btn-st1\">" + pOptionData.symptomKwd + "</span> &gt; <span class=\"txt-deco btn-st2\">" + pOptionData.partName + "</span> &gt; <span class=\"dashTTL\">조치결과</span>");
	drawDavif.radarChart(_graphData, "dash_" + pCate + "_BottomDiv", func);
	
}

function drawNetworkMapChart(pData,pCate,pOptionData) {
	
	var _depthList = pData;
	var _graphData = [];
	
	for (var i = 0; i < pData.length; i++) {
		_graphData[i] = {
			target : pOptionData.partName,
			source : pData[i].label,
			value : pData[i].data
		}
	}
	
	var func = function(d){
		var _cate = pOptionData.cate;
		var _partName = pOptionData.partName;
		var _actionKwd = pOptionData.actionKwd;
		var _symptomKwd = d.id;
		
		gotoPagePfSearch(_cate,_partName,_symptomKwd,_actionKwd);
	};
	
	$('#dash_' + pCate + '_BottomTitle').html("<span class=\"txt-deco btn-st1\">" + pOptionData.actionKwd + "</span> &gt; <span class=\"txt-deco btn-st2\">" + pOptionData.partName + "</span> &gt; <span class=\"dashTTL\">현상</span>");
	drawDavif.networkMapChart(_graphData, "dash_" + pCate + "_BottomDiv", func);
	
}

function drawLineChart(pData,pCate,pLimit, pOptionData) {
	var _depthList = pData;
	var _graphData = [];
	
	for (var i = 0; i < _depthList.length; i++) {
		_graphData.push([_depthList[i].label, parseInt(_depthList[i].data)]);
	}
	var _Option = {};
		_Option.xaxis = {
			mode: "categories",
			tickDecimals: 0
		};
		_Option.tooltipOpts = {
			cssClass: "flotTip",
	        content: function(label,x,y){
	            return x + "<br>" + y +" 건";
	        },
			defaultTheme : false,
			shifts: {
				x: 0,
				y: -20
			}
		};
	drawFlot.lineChart([{data:_graphData}],"dash_" + pCate + "_Div",_Option);
	
	$("#dash_" + pCate + "_Div").bind("plothover", function(event, pos, item) {
	    if(item) {
	        document.body.style.cursor = 'pointer';
	    } else {
	        document.body.style.cursor = 'default';
	    }
	});
	
	// 클릭이벤트 처리.
	$("#dash_" + pCate + "_Div").bind("plotclick", function (event, pos, item) {
		if (item) {
			var index = item.dataIndex;
			var data = item.series.data[index][0];
			var type = pOptionData.job;
			if(type=="powerSt") gotoPagePfSearchNoImg(pCate,"",data);
			else gotoPagePfSearchNoImg(pCate,data,"");
		}
	});		
}

function drawTableList(pData,pCate,pTitleText,pSubTitle,pOptionData){
	var _makeHtml = "";
	var _partName = "";
	var _job = "";
	var _data = "";
	var _addClass = "";
	
	if(pOptionData.symptomKwd != undefined && pOptionData.symptomKwd != '' ){
		_job = "sym";
		_data = pOptionData.symptomKwd;
		_addClass = "cursor";
	}else if(pOptionData.actionKwd != undefined && pOptionData.actionKwd != '' ){
		_job = "act";
		_data = pOptionData.actionKwd;
		_addClass = "cursor";
	}
	
	for (var i = 0; i < pData.length && i < gListLimit; i++) {
		_makeHtml += "<li>" +
				"<span class='" + (i < 3?'rank-top':'rank-etc') + "'>" + (i+1) + "</span>" +
				"<span class='rank-content " + _addClass + "' onclick=\"clickTableList('" + pCate + "','" + _job + "','" + pData[i].label  + "','" + _data + "');\" title='" + pData[i].label + "'>" + pData[i].label + "</span>" +
				"<span class='rank-num'>" + pData[i].data + "</span></li>";
	}
	
	$('#dash_' + pCate + '_ListTitle' ).html("<span class=\"txt-deco btn-st1\">" + pTitleText + "</span><span>의 " + pSubTitle + "</span>");
	$('#dash_' + pCate + '_List' ).children('ul').html(_makeHtml);
}

function clickTableList(pCate, pJob, pTableData, pPieData){
	var _optionData = {};
		_optionData.cate = pCate;
	
	if(pJob == "sym"){
		_optionData.symptomKwd = pPieData;
		_optionData.partName = pTableData;
		setAction(pCate, 'radar', 5, _optionData);
	}else if(pJob == "act"){
		_optionData.actionKwd = pPieData;
		_optionData.partName = pTableData;
		setSymptom(pCate, 'net', 5, _optionData);
	}else{
		
	}
}

/**
	그래프 클릭시 페이지 이동
 */
function gotoPagePfSearch(pCate,pPartname, pSymptomKwd,pActionKwd){
	$('#his_category').val(pCate);
	$('#his_partName').val(pPartname);
	$('#his_symptomKwd').val(pSymptomKwd);
	$('#his_actionKwd').val(pActionKwd);
	$('#his_isImg').val("Y");
	$('#historyForm').attr("action",context+"/problemFocus/pfSearch.do");
	$('#historyForm').submit();
}

/**
	그래프 절연진단, 누설흡습 그래프 클릭시 이동.
 */
function gotoPagePfSearchNoImg(pCate,pPowerComp,pPowerSt){
	$('#his_category').val(pCate);
	$('#his_powerComp').val(pPowerComp);
	$('#his_powerSt').val(pPowerSt);
	$('#his_isImg').val("N");
	$('#historyForm').attr("action",context+"/problemFocus/pfSearch.do");
	$('#historyForm').submit();
}