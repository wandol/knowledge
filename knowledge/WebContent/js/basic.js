/*******************************************************************************
 * 파 일 명 : basic.js
 * 내   용 : Template3을 구동하기 위한 js 
 ******************************************************************************/


$(function() {
	
	// ***** dialog title start *****
	$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
		_title : function(title) {
			if (!this.options.title) {
				title.html("&#160;");
			} else {
				title.html(this.options.title);
			}
		}
	}));
	// ***** dialog title end *****
	
	$('.starlist a').click(function(){
        $(this).parent().children("a").removeClass("on");  /* 별점의 on 클래스 전부 제거 */ 
        $(this).addClass("on").prevAll("a").addClass("on"); /* 클릭한 별과, 그 앞 까지 별점에 on 클래스 추가 */
        return false;
    });
});




 
