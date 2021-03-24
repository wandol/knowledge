<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/tagLib.jsp" %>

<!-- chatbot -->
<link rel="stylesheet" type="text/css" href="<c:url value="/css/konan/chatbot_new/chatbot.css" />"></link>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/konan/chatbot_new/chat/konanbot-style.css" />"></link>
<style>

#test_btn1{ border-top-left-radius: 5px; border-bottom-left-radius: 5px; margin-right:0px; } 
#test_btn2{ margin-left:0px; border-left : 0px; border-right : 0px;} 
#test_btn3{ border-top-right-radius: 5px; border-bottom-right-radius: 5px; margin-left:0px; } 
#btn_group button{ border: 1px solid skyblue; background-color: rgba(0,0,0,0); color: skyblue; padding: 5px 9px; margin-bottom : 10px;} 
#btn_group button:hover{ color:white; background-color: skyblue; }


.toggler {min-width: 400px; max-width: 890px; right:200px; position:absolute;top:5%;overflow : hidden;}
#effect { width: 100%; height: 135px;  position: relative;  right:-200px; top:10px;}
#eff_cont { width:97%; float:right; font: 28px/1.6 NanumBrushWeb; }

/* VR 골라보기 리스트*/
.vrlist .scroll{max-height: 730px; overflow: auto;}
.vrlist .part-name:hover {background-color: #cae8f4; cursor:pointer}
.vrlist .part-out{background: url(../css/konan/chatbot_new/img/ico_out.png) no-repeat 0 0;width: 28px;height: 28px;float: right; margin: 11px 15px;cursor:pointer}}
 
/* 상세화면 */
.jb-table {border: 1px solid #bcbcbc; display: table;width: 100%;margin-top:3px;}
.jb-table-row {display: table-row;}
.jb-table-cell {display: table-cell;vertical-align: middle;padding-left:3px;}
.jb-table-cell.title {display: table-cell;vertical-align: middle;background-color: cornsilk; width:90px;font-weight:500;}
.jb-table-cell.content {width:155px;}
.jb-content {padding:3px;margin-top:3px; border: 2px solid #bcbcbc;}
.jb-table,.jb-content	{font-size: 14px;}
.jb-table,.jb-content img {cursor: pointer;}
.jb-table,.jb-table-row,.jb-table-cell {border: 1px solid #bcbcbc;}

</style>
<script src="<c:url value="/js/konan/chatbot/chat/admin.js" />"></script>
<script>

/**
* VR 클릭시 조회 및 이벤트 동작
*/
function getPartList(model,locate,repflag){
	var fn_done;
	
	//VR 골라보기 클릭시 리스트 html로 구현
	var fn_makehtml = function(ret){
		var _trHTML = "";
		var num=0;
		
		$.each(ret.result, function(){
			num++;
			_trHTML +="<tr>";
			_trHTML +="	<td class=\"center\">" + num + "</td>";
			_trHTML +="	<td>" + this["part2_cd"] + "</td>";
			_trHTML +="	<td>" + this["part3_cd"] + "</td>";
			_trHTML +="	<td class=\"part-name\" onclick=\"callVR('" + model + "','" + this["part4_cd"] + "');\"><span>" + this["part4_nm"] + "</span></td>";
			_trHTML +="</tr> ";
		});
		$("#vrpartlist").html(_trHTML);
	};
	
	// vr 보기 클릭시
	var fn_viewVR = function(ret){
		$.each(ret.result, function(){
			callVR(model, this["part4_cd"]);
			return false;
		});
	};
	
	if(repflag == "Y"){
		fn_done = fn_viewVR;
	}else{
		fn_done = fn_makehtml;
	}
	
	$.ajax({
		url : context + "/comm/getVRPartInfo.do",
		data : {'cate':model, 'partname':locate, 'repflag': repflag} , 
		dataType : "JSON",
		type : "GET",
		//contentType: "application/x-www-form-urlencoded; charset=utf-8",
		//xhrFields: {
		//    withCredentials: true
		//},
		async: false,
		cache: false,
		beforeSend: function() {
			if(repflag != "Y"){
				$("#vrpartlist").html('');
			}
	    }
	})
	.done(fn_done)
	.fail(function(xhr){
		console.log(xhr.responseText);
	});	
}

/**
* AR/VR API 호출
**/
function callVR(model, part){
	var model;
	var part;
	var url = "http://idpp.kepco.co.kr/arvr/rest/main/{model}";
	
	url = url.replace("{model}",model);
	url += "/part/"+part;
	console.log(url);
	window.open(url, '_blank');
}

var before_img ="";
var imgOriginal = new Image(); 
var loadcount =0;
var viewyn;
var imgObj;

function getImageSize(src,imgObj) {
	 
    var img = new Image();
    
    img.src = src;
    
    _width= img.width;
    _height= img.height;
 
    var ary =new Array();
    ary.push(_width);
    ary.push(_height);  
    
    return ary;
}

/**
 * 슬라이드 이벤트
 */
function setSlideView(viewyn,imgObj,imgOriginal){
	if(imgOriginal != undefined && ! imgOriginal.complete ) {
		
		loadcount++;
		setTimeout("setSlideView(viewyn,imgObj,imgOriginal);", 100); 
        return ;
         
	}	
	
	var img_size_ary;
	if(imgOriginal != undefined){
		var img_src = $("#cont_img").attr("src");
		
		if(before_img!=img_src && $("#effect").attr("viewyn")=='on') { 
					
			before_img = img_src;
			return;
		}
		
		img_size_ary = getImageSize(img_src,imgObj);//z-index:100000;
		
		before_img = img_src;
	}
	
	
	var z = 0;
	
	var chat_zindex = $("#trychat_wrapper").css("z-index");
	var chat_width = $("#chat-message").width();
	var chat_top = $("#chat-message").position().top;
	var cont_width;
	
	if(imgOriginal != undefined){
		cont_width = img_size_ary[0];
		$("#eff_cont").children(".vrlist").css("display","none");	
		$("#cont_img").css("display","block");
	}else{
		cont_width = 858;
		$("#eff_cont").children(".vrlist").css("display","block");
		$("#cont_img").css("display","none");
	}
	
	$( ".toggler" ).css("z-index",chat_zindex+1);
	$( ".toggler" ).css("top",chat_top+15+"px");
	$( ".toggler" ).css("right",chat_width+15+"px");
	
	if(viewyn=='off') {
		$( ".toggler" ).css("display","block");
		$( ".toggler" ).width(cont_width+'px');
		$( "#eff_cont" ).width((cont_width)+'px');
		$( "#effect" ).css("right",'-'+(cont_width+20)+'px');
		$( "#effect" ).animate({right: 0}, 500 );
		$( "#effect" ).attr("viewyn","on");
		
	}else{
		$( "#effect" ).animate({right: '-'+($( "#effect" ).width()+20)+'px'}, 500 );
		$( "#effect" ).attr("viewyn","off");
	}
	
	//viewyn = $("#effect").attr("viewyn");
}

/**
 * VR 보기 클릭시
 */
function viewVR(model,locate){
	getPartList(model,locate,"Y");
}


/**
 * VR 골라보기 클릭 
 */
function part_select(model,locate){
	if($("#cont_img").css("display") == "block"){
		$("#effect").animate({right: '-'+($(".toggler").width()+20)+'px'}, 500 );
		$("#effect").attr("viewyn","off");
	}
	
	viewyn = $("#effect").attr("viewyn");
	
	if(viewyn == "off"){
		// 파트리스트 출력
		getPartList(model,locate);
	}
	
	setTimeout("setSlideView(viewyn);",500);
}


/**
* 설비정보 클릭
*/
function equip_info(cate,locate){
	if($("#eff_cont").children(".vrlist").css("display") == "block"){
		$("#effect").animate({right: '-'+($(".toggler").width()+20)+'px'}, 500 );
		$("#effect").attr("viewyn","off");
	}
	
	viewyn = $("#effect").attr("viewyn");
	var img_src ="";
	
	if(cate.indexOf('boiler')!=-1) img_src = "../img/boiler_equip.png";
	else if(cate.indexOf('turbin')!=-1) img_src = "../img/steamTurbine_equip.png";
	else if(cate.indexOf('gen')!=-1) img_src = "../img/gen_equip.png";
	
	$("#cont_img").attr("src",img_src);
	imgOriginal.src = $("#cont_img").attr("src");
	var imgObj = $("#cont_img");
	setTimeout("setSlideView(viewyn,imgObj,imgOriginal);",500);
}

/*
* 토글 삭제
*/
function toggler_close(){
	if($("#effect").attr("viewyn")=='on') {
		$("#effect").animate({right: '-'+($(".toggler").width()+20)+'px'}, 0 );
		$( ".toggler" ).css('display','none' );
		$("#effect").attr("viewyn","off");
	}
}

</script>
<div class="chat-container transition">
	<div id="chat-message" class="header" >
   	  <div class="head">
           <h1 class="h1">
           	  <a class="modal-title ellipsis ellipsis-a">
           		발전운전 QnA
           	  </a>
           	  <!-- <span>${ sessionScope.login_user.user_nm }</span> -->
           	
           </h1>
          <ul>
			<li><a class="ico-out" data-dismiss="modal" aria-label="Close" onclick="javascript:chatLayer.hide();toggler_close();"></a></li>
		  </ul>
      </div>
      <div class="chat-wrap">
      	<div class="chat" id="chat-history">
        	<ul class="message"></ul>
      	</div>
      </div>
       <div class="write-form">
         <textarea id="chat-textarea" rows="1" placeholder="<spring:message code="msg.message.enter" />" ></textarea>
         <span class="send" id="chat-send"><span></span></span>
       </div>
   </div> 
</div> 


<script type="text/javascript">
//var MSG_SPEAK_MESSAGE = "<spring:message code="msg.message.speak" />";

var USER = "<c:out value="${sessionScope.login_user.user_nm}" />";
$(document).ready(function() {
	
	$('#chat-history').on('click', 'button[value]:not([onclick])', function(event) {
		var targetObj = $(event.target);
        chat.addMessage( targetObj.val(), targetObj.text());
	});
	
	chat.init('KEPRI', USER, '');
	
	$(document).click(function(e){
		if ( $('#trychat_wrapper').is(e.target)||$('#cont_img').is(e.target) ) { //&& !$('.toggler').has(e.target)
		// code
			toggler_close();
		}
	});
});
</script>

<script src="<c:url value="/js/konan/chatbot/chat/speech.js"/>"></script>