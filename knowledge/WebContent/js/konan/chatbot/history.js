$( function() {
	datepicker.init('startdate');
	datepicker.init('enddate');
});

var dialog = {};

dialog.goIntent = function() {
	var entities = new Array();
	$("#nelistUl").find(":checkbox:checked").map(function() {
		if ($.inArray(this.value, entities) < 0 ) {
			entities.push(this.value);
		}
	});
	
	location.href = CONTEXTPATH + "admin/intent.bot?keywords=" + encodeURIComponent(encodeURIComponent(entities.join(" ")));
};

dialog.cellRenderer = {};
dialog.cellRenderer.replyyn = function(value, item){
	var $cell = $("<td />").addClass(this.name).data("field", this);
	var $icon = $("<i aria-hidden=\"true\" class=\"fa\" />").addClass(value=="Y"?"fa-microphone":"fa-microphone-slash").addClass("replyyn_"+value);
	$cell.append($icon).attr({"title" : value == "Y" ? msg_reply : msg_noreply});
	return $cell;
};

dialog.changetype = function(value) {
	console.log(value);
	
	$(".tab_dialog li").removeClass("active");
	$(".tab_dialog_"+value).addClass("active");
	
	$(".select-questiontype").hide();
	$(".dialog_"+value).show();
	
	$("#dialog-input-request").focus();
};

dialog.getlist = function(startdate, enddate) {
	blocking.on();
	
	var griddata = [];
	
	$.ajax({
    	url: CONTEXTPATH + "api/domains/"+DOMAIN+"/dialog", 
        dataType:"json",
        type:"get",
        contentType:"application/json;charset=utf-8",
        data: {
        	type : $("#dialogType option:selected").val(), 
        	startdate : startdate,
        	enddate : enddate,
        	dialogUser : $("#dialog_user").val(),
		    minScore : $("#min_score").val(),
		    maxScore : $("#max_score").val(),
		    dialogQuestion : $("#dialogQuestion").val()
        },
        cache:false
    })
    .done( function(ret) {
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

dialog.test = function(griditem) {
	var testText = griditem.question;
	if(($.trim(testText)) == "") {
		layer.warning(msg_question_empty, function() {
			var $selectedRow = $("#dialog_grid").find("."+GRID_ROW_SELECTED);
			$("#dialog_grid").jsGrid("editItem", $selectedRow.data(GRID_ROW_DATA_KEY));
			$("#btn_json, #btn_intentmng").attr("disabled", true);
		});
		return;
	}
	
	$("#dialog-input-request").val(testText);
	dialog.analysis.ask();
};

dialog.analysis = {};
dialog.analysis.ask = function() {
	$("#dialog-input-result").empty();
	$("#jsonViewer").data("json", {});
	dialog.analysis.nlp.empty();
	$("#intents_grid").refreshGrid();
	
	var testText = $("#dialog-input-request").val();
	if(($.trim(testText)) == "") {
		layer.warning(msg_question_empty, function() {
			$("#dialog-input-request").focus();
			$("#btn_json, #btn_intentmng").attr("disabled", true);
		});
		return;
	}

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
    	
    	$("#jsonViewer").data("json", ret);
    	$("#btn_json").attr("disabled", (ret && ret.score > -1)? false : true)
    	
    	var contextResponse = {
        	contents: ret.payload.contents,
        	contextpath: CONTEXTPATH,
        	functionName : "dialog.analysis.answer"
        };
		var $response =  dialogTemplate.render(contextResponse);
    	var div = $("<div />").attr("id", "conv_result_row")
		    			.append($("<div />").addClass("result-box")
		    			.append($("<div />").addClass("result-arrow-up"))
		    			.append($("<div />").addClass("bubble").html($response)));
    	
    	$("#dialog-input-result").append(div);
    	div.slideDown(500);
		
    	dialog.analysis.nlp.show(ret);
    });
};

dialog.analysis.answer = function(type, text, value) {
	layer.info("Button Click Event :: [" + type + ", " + text + ", " + value + "]");
};

dialog.analysis.json = {};
dialog.analysis.json.show = function() {
	if(jsonviewer == null) {
		jsonviewer = new JsonEditor('#jsondata', {});
	}
	var jsondata = $("#jsonViewer").data("json");
	console.log("show json", jsondata);
	
	if(jsondata && jsondata.score > -1) {
		jsonviewer.load(jsondata);
		$("#jsonViewer div,ul,li,span").prop("contentEditable", false);
		layer.show($("#jsonViewer"));
	}
};

dialog.analysis.json.close = function() {
	layer.hide($("#jsonViewer"));
}

dialog.analysis.nlp = {};
dialog.analysis.nlp.empty = function() {
	$("#keywordlistUl").empty();
	$("#nelistUl").empty();
	$("#semanticlistUl").empty();
};

dialog.analysis.nlp.show = function(data) {
	var kwdData = [];
	var ner2Data = [];
	var sfxData = [];
	
	if(data.question != null) {
		var _qdata = data.question;
		if(_qdata.kwd && _qdata.kwd.length > 0) kwdData = _qdata.kwd.split(" ");
		if(_qdata.cat && _qdata.cat.length > 0) ner2Data = _qdata.cat.split(" ");
		if(_qdata.sfx && _qdata.sfx.length > 0) sfxData = _qdata.sfx.split(" ");
	}	
	
	$("#span_targetdomain").parent().hide();
	$("#targetdomain").val(DOMAIN);
	$("#btn_intentmng").attr("disabled", true);
	var selectNer2Data = [];
	
	if(data.answer) {
		if(data.answer.domain_id && data.answer.domain_id.length > 0) {
			$("#targetdomain").val(data.answer.domain_id);
		} 
		if(data.answer && data.answer.cat && data.answer.cat.length > 0) {
			selectNer2Data = data.answer.cat.split(" ");
		}
	} 
	
	kwdData.forEach(function(value) {
		$("#keywordlistUl").append($("<li class='green' /> ").text(value));
	});
	
	//$("#keywordlistUl").scrollTop(25);
	
	for(var i in ner2Data) {
		$("#nelistUl").append($("<li class='blue' /> ")
							.append($("<input type='checkbox' value='" + ner2Data[i] + "' />").addClass("checkbox ner").prop("checked", (selectNer2Data.indexOf(ner2Data[i])>-1)).on("click", function(){dialog.analysis.similarlist();}))
							.append($("<span/>").text(ner2Data[i])));
	}
	
	for(var i in sfxData) {
		$("#semanticlistUl").append($("<li class='indigo' /> ")
							.append($("<input type='checkbox' />").addClass("checkbox sfx").prop("checked", false).on("click", function(){dialog.analysis.similarlist();}))
							.append($("<span/>").text("$"+sfxData[i])));
	}
	
	if(selectNer2Data.length > 0) {
		dialog.analysis.similarlist();
	}
};

dialog.analysis.similarlist = function() {
	blocking.on();
	$("#span_targetdomain").html($("#targetdomain").val()).parent().show();
	$("#btn_intentmng").attr("disabled", ($("#targetdomain").val() == DOMAIN)? false: true);
	
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
    params.targetdomain = $("#targetdomain").val();
    
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
		if(ret.data.length == 0) {
			$("#span_targetdomain").parent().hide();
			$("#btn_intentmng").attr("disabled", true);
		}
		$("#intents_grid").refreshGrid(ret.data);
	})
	.fail(function(xhr){
		intentList.printEmpty(xhr.responseText);
		layer.error(xhr.responseText, blocking.off());
	})
	.always(function() {
		blocking.off();
	});
};