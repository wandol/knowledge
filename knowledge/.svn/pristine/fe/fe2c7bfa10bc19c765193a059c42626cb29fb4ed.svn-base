$( function() {
	datepicker.init('logdate');
});

var dialog = {};
dialog.changetype = function(value) {
	console.log(value);
	
	$(".tab_dialog li").removeClass("active");
	$(".tab_dialog_"+value).addClass("active");
	
	$(".select-questiontype").hide();
	$(".dialog_"+value).show();
	
	$("#dialog-input-request").focus();
};

dialog.getlist = function(targetdate) {
	blocking.on();
	
	var griddata = [];
	$.getJSON(CONTEXTPATH + "api/domains/"+DOMAIN+"/dialog", {type : $("#dialogType option:selected").val(), date : targetdate}, function(ret) {
		console.log(ret);
		if(ret.code == 200)
			griddata = ret.data;
	})
	.fail(function(xhr) {
		layer.error(xhr.responseText);
	})
	.always(function() {
		$("#dialog_grid").refreshGrid(griddata);
		$("#dialog_grid .jsgrid-grid-body").scrollTop(0);
		blocking.off();
	});
};

dialog.test = {};
dialog.test.grid = function(item) {
	var testText = item.str;
	if(($.trim(testText)) == "") {
		layer.warning(msg_question_empty, function() {
			var $selectedRow = $("#dialog_grid").find("."+GRID_ROW_SELECTED);
			$("#dialog_grid").jsGrid("editItem", $selectedRow.data(GRID_ROW_DATA_KEY));
		});
		return;
	}
	
	$("#dialog-input-request").val(testText);
	$("#questiontype2").prop("checked", true);
	dialog.changetype("input");
	dialog.test.input();
};

dialog.test.input = function() {
	var testText = $("#dialog-input-request").val();
	if(($.trim(testText)) == "") {
		layer.warning(msg_question_empty, function() {
			$("#dialog-input-request").focus();
		});
		return;
	}
	dialog.analysis.ask(testText);
};

dialog.analysis = {};
dialog.analysis.ask = function(testText) {
	$("#conv_result_row").remove();

    var params = {};
    params.domain = DOMAIN;
    params.user = USER_ID;
    params.isadmin = "true";
    params.text = testText;

    $.ajax({
    	url: CONTEXTPATH + "chat/message", 
        dataType:"json",
        type:"post",
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(params),
        processData:false
    })
    .done( function(ret) {
    	console.log(ret);
    	
    	var contextResponse = {
        	contents: ret.payload.contents,
        	contextpath: CONTEXTPATH,
        	functionName : "dialog.analysis.answer"
        };
    	
    	var template = $.getTemplateStr("dialog-response");
		var $response =  template.render(contextResponse);
    	
    	var div = $("<div />").attr("id", "conv_result_row")
    			.append($("<div />").addClass("result-box")
    			.append($("<div />").addClass("result-arrow-up"))
    			.append($("<div />").addClass("bubble").html($response)));
    	
    	$("#dialog-input-result").append(div);
    	div.slideDown(500);
		
    	dialog.nlp.show(ret);
    });
};

dialog.analysis.answer = function(type, text, value) {
	alert(type + ", " + text + ", " + value);
}

dialog.nlp = {};
dialog.nlp.show = function(data) {
	
	var _qdata = data.question;
	
	var kwdData = [];
	if(_qdata.kwd && _qdata.kwd.length > 0) kwdData = _qdata.kwd.split(" ");
	var ner2Data = [];
	if(_qdata.cat && _qdata.cat.length > 0) ner2Data = _qdata.cat.split(" ");
	var sfxData = [];
	if(_qdata.sfx && _qdata.sfx.length > 0) sfxData = _qdata.sfx.split(" ");
	
	var selectNer2Data = [];
	if(data.answer && data.answer.cat && data.answer.cat.length > 0) selectNer2Data = data.answer.cat.split(" ");
	
	$("#keywordlistUl").empty();
	kwdData.forEach(function(value) {
		$("#keywordlistUl").append($("<li class='green' /> ").text(value));
	});
	
	$("#keywordlistUl").scrollTop(25);
	
	$("#nelistUl").empty();
	for(var i in ner2Data) {
		$("#nelistUl")
			.append($("<li class='blue' /> ")
							.append($("<input type='checkbox' />").addClass("checkbox ner").prop("checked", (selectNer2Data.indexOf(ner2Data[i])>-1)).on("click", function(){dialog.search();}))
							.append($("<span/>").text(ner2Data[i])));
	}
	
	$("#semanticlistUl").empty();
	
	for(var i in sfxData) {
		$("#semanticlistUl")
			.append($("<li class='indigo' /> ")
							.append($("<input type='checkbox' />").addClass("checkbox sfx").prop("checked", false).on("click", function(){dialog.search();}))
							.append($("<span/>").text("$"+sfxData[i])));
	}
	
	if(selectNer2Data.length > 0) {
		dialog.search();
	}
	else {
		intentList.printEmpty(msg_error_nokeyword);
	}
};

dialog.search = function() {
	var i = 0, j=0;
	var tmp;
	var nerArr = [], sfxArr=[];
	$("input.checkbox:checked").each(function() {
		tmp = $(this).next().text();
		if($(this).hasClass("ner")) nerArr[i++] = tmp;
		if($(this).hasClass("sfx")) {
			if(tmp.indexOf("$") == 0) tmp = tmp.substr(1);
			sfxArr[j++] = tmp;
		}
	});
	
	dialog.analysis.print(nerArr, sfxArr);
};

dialog.analysis.print = function(nerArr, sfxArr) {
	blocking.on();
	
	$("#ne_grid").refreshGrid();
	
	if(nerArr.length > 1)
		nerArr = removeOverlap(nerArr);
	if(sfxArr.length > 1)
		sfxArr = removeOverlap(sfxArr);
	
	console.log("similar intents", nerArr, sfxArr);
	
	var params = {};
    params.domain = DOMAIN;
    params.user = USER_ID;
    params.ners = nerArr;
    params.sfxs = sfxArr;

    $.ajax({
    	url: CONTEXTPATH + "api/domains/"+DOMAIN+"/intents?type=similar", 
        dataType:"json",
        type:"post",
        contentType:"application/json;charset=utf-8",
        data: JSON.stringify(params),
        processData:false
    })
    .done( function(ret) {
		console.log(ret);
		$("#intents_grid").refreshGrid(ret.data);
	})
	.fail(function(xhr){
		intentList.printEmpty(ret.message);
		layer.error(xhr.responseText, blocking.off());
	})
	.always(function() {
		blocking.off();
	});
};