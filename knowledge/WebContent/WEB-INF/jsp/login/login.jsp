<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/tagLib.jsp" %>
<!-- LOGIN START -->
<div id="content" class="container">
	<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-5 col-lg-6 hidden-xs hidden-sm">
			<h1 class="txt-color-red login-header-big">발전운전 전문가지식 서비스</h1>
			<div class="hero">
				<div class="pull-left login-desc-box-l">
					<h4 class="paragraph-header">
						발전운전 전문가지식 서비스<br>회원이 아니신가요?<br>
					</h4>
					<h4 class="paragraph-header">
						가입을 하시면 좀 더 다양한 서비스를<br>이용하실 수 있습니다.
					</h4>
				</div>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-7 col-lg-6 floatRight">
			<div class="well no-padding">
				<form id="login-form" class="smart-form client-form" action="loginCheck.do">
					<header>
						Login
					</header>

					<fieldset>
						
						<section>
							<label class="label">ID</label>
							<label class="input"> <i class="icon-append fa fa-user"></i>
								<input type="text" name="id" id="id" value="admin">
								<b class="tooltip tooltip-top-right"><i class="fa fa-user txt-color-teal"></i> 아이디를 입력해 주세요.</b></label>
						</section>

						<section>
							<label class="label">Password</label>
							<label class="input"> <i class="icon-append fa fa-lock"></i>
								<input type="password" name="passwd" id="passwd" onkeypress="if(event.keyCode==13) {login(); return false;}" value="1234">
								<b class="tooltip tooltip-top-right"><i class="fa fa-lock txt-color-teal"></i> 비밀번호를 입력해 주세요.</b> </label>
							<div class="note">
								<a href="forgotpassword.html">Forgot ID or password?</a>
							</div>
						</section>

						<section>
							<label class="checkbox">
								<input type="checkbox" id="rememberId" name="remember">
								<i></i>아이디 기억하기</label>
						</section>
					</fieldset>
					<footer>
						<button class="btn btn-primary" type="button" onclick="javascript:login();">
							로그인
						</button>
					</footer>
				</form>
			</div>
		</div>
	</div>
</div>