// admin 페이지 전역변수
var TABLE;
var INFO;
var CATEGORY;
var responsiveHelper_dt_basic = undefined;
var breakpointDefinition = {
	tablet : 1024,
	phone : 480
};

$(document).ready(function() {
	
//	var category = $('#his_repoCate').val();
//	
//	// Repository 테이블 셋팅
//	function getReportList(cate, startNum) {
//		
//		if ( CATEGORY != null && CATEGORY != "" ) { cate = CATEGORY; }
//		
//		TABLE = $('#dt_basic_repository').dataTable({
//			displayStart: startNum,
//			destroy: true,
//			pageLength: 10,
//			pagingType : "full_numbers",
//            bPaginate: true,
//            bLengthChange: false,
//            responsive: true,
//            bAutoWidth: false,
//            processing: true,
//            ordering: true,
//            bServerSide: true,
//            searching: true,
//            sAjaxSource : context + "/admin/repository/" + cate,
//            sServerMethod: "GET",
//            columns : [
//            	{data: "NO"},
//                {data: "REPO_FILE_NM"},
//                {data: "REPORT_NM"},
//                {data: "CNT"},
//                {data: "PUBLISH_YM"},
//                {
//                	data: "REPORTER",
//                	render: function ( data, type, row ) {
//                		
//                		var _reporter = data;
//                		if ( data.length > 30 ) {
//                			_reporter = data.substring(0, 30) + "...";
//                		}
//                		
//                		return _reporter;
//                	}
//                }
//            ],
//            aoColumnDefs : [{
//                'bSortable' : false,
//                'aTargets' : [0,1,2,3,4,5]
//            }],
//            sDom : 'tpri',
//			preDrawCallback : function() {
//				// Initialize the responsive datatables helper once.
//				if (!responsiveHelper_dt_basic) {
//					responsiveHelper_dt_basic = new ResponsiveDatatablesHelper($('#dt_basic_repository'), breakpointDefinition);
//				}
//			},
//			rowCallback : function(nRow) {
//				responsiveHelper_dt_basic.createExpandIcon(nRow);
//			},
//			drawCallback : function(oSettings) {
//				responsiveHelper_dt_basic.respond();
//			}
//
//        });
//	};
//	
//	
//	$('#dt_basic_repository tbody').on( 'click', 'tr', function () {
//        var _clickData = TABLE.api().rows(this).data();
//        var _category = $('#repoCate option:selected').val();
//        
//        INFO = TABLE.api().page.info();
//        console.log(INFO);
//        document.location.hash = "#" + INFO.page;
//        
//        // 선택 한 row의 지식DB를 보여줄 페이지로 이동
//        location.href = context + "/admin/repository/" + _category + "/" + _clickData[0].MD5_KEY;
//    });
//	
//	
////	$("#repoCate").on('change', function() {
////		
////		location.href = context + "/admin/repository.do?category=" + this.value;
////	});
//		
//	if ( document.location.hash ) {
//		var hashLocation = document.location.hash.split("#")[1];
//		getReportList(category, Number(hashLocation + '0'));
//	} else {
//		// 테이블 초기화
//		getReportList(category, 0);
//	}
	
	
});