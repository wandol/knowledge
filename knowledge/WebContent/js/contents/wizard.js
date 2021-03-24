/*******************************************************************************
 * 파일명 : wizard.js 
 * 내 용 : wizard.html을 구동하기 위한 js
 ******************************************************************************/

$(function() {

	pageSetUp();

	$('#bootstrap-wizard-1').bootstrapWizard(
			{
				'tabClass' : 'form-wizard',
				'onNext' : function(tab, navigation, index) {
					var $valid = $("#wizard-1").valid();
					if (!$valid) {
						$validator.focusInvalid();
						return false;
					} else {
						$('#bootstrap-wizard-1').find('.form-wizard').children(
								'li').eq(index - 1).addClass('complete');
						$('#bootstrap-wizard-1').find('.form-wizard').children(
								'li').eq(index - 1).find('.step').html(
								'<i class="fa fa-check"></i>');
					}
				}
			});

	// fuelux wizard
	var wizard = $('.wizard').wizard();

	wizard.on('finished', function(e, data) {
		// $("#fuelux-wizard").submit();
		// console.log("submitted!");
		$.smallBox({
			title : "Congratulations! Your form was submitted",
			content : "<i class='fa fa-clock-o'></i> <i>1 seconds ago...</i>",
			color : "#5F895F",
			iconSmall : "fa fa-check bounce animated",
			timeout : 4000
		});

	});
});

var _gaq = _gaq || [];
_gaq.push([ '_setAccount', 'UA-XXXXXXXX-X' ]);
_gaq.push([ '_trackPageview' ]);

(function() {
	var ga = document.createElement('script');
	ga.type = 'text/javascript';
	ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl'
			: 'http://www')
			+ '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0];
	// 운연서버는 인터넷이 안되므로 필요 없음.
	//s.parentNode.insertBefore(ga, s);
})();
