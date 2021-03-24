<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- ui-dialog -->
<div id="qAManage">
	<div class="row">
		<article class="col-xs-12">
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle" style="background-color: #3159a1;border-color: #3159a1;color: #fff;">
					<div id="qaTitle">보고서 대상 파일 추출</div>
				</header>
				<div class="widget-body pfGrapDiv-pl40">
					<form method="POST" enctype="multipart/form-data" id="qaUploadForm">
						<input class="form-control" type="file" id="qaFile" onchange="javascript:qaFileCheck(this)" name="file" style="width: 100%;">
					</form>
				</div>
			</div>
		</article>
		<article class="col-xs-6">
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle" style="background-color: #3159a1;border-color: #3159a1;color: #fff;">
					<div id="qaTitle">정밀진단 및 고장 보고서 원문</div>
				</header>
				<div class="widget-body pfGrapDiv-pl40">
					<textarea class="row" style="height: 650px; width:760px;">
					</textarea>
				</div>
			</div>
		</article>
		<article class="col-xs-6">
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle" style="background-color: #3159a1;border-color: #3159a1;color: #fff;">
					<div id="">질문</div>
				</header>
				<div class="widget-body pfGrapDiv-pl40">
					<!-- superbox start -->
					<input type="text" style="width: 100%;height: 50px;"/>
				</div>
			</div>
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle" style="background-color: #3159a1;border-color: #3159a1;color: #fff;">
					<div id="">답변</div>
				</header>
				<div class="widget-body pfGrapDiv-pl40">
					<!-- superbox start -->
					<input type="text" style="width: 100%;height: 50px;"/>
				</div>
			</div>
			<div class="jarviswidget" id="wid-id-0" data-widget-colorbutton="false" data-widget-editbutton="false" data-widget-deletebutton="false" data-widget-fullscreenbutton="false" data-widget-custombutton="false" data-widget-sortable="false">
				<header class="ui-sortable-handle" style="background-color: #3159a1;border-color: #3159a1;color: #fff;">
					<div id="">참조근거</div>
				</header>
				<div class="widget-body pfGrapDiv-pl40">
					<!-- superbox start -->
					<textarea class="row" style="height: 283px; width:760px;">
					</textarea>
				</div>
			</div>
		</article>
	</div>
	<div class="btBtn pull-right">
		<a class="btn btn-primary" href="/knowledge/document/docAdd.do ">입력</a>
		<a class="btn btn-mod" href="javascript:void(0);" onclick="javascript:docUpdatePage();">수정</a>
		<a class="btn btn-mod" href="javascript:void(0);" onclick="javascript:docDelete();">삭제</a>
	</div>
</div>