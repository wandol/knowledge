<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./tagLib.jsp" %>
<form id="historyInfoForm">	
	<!-- url 정보 -->
	<spring:eval expression="@global['login.url']" var="loginUrl" />
	<spring:eval expression="@global['ksf.url']" var="adminUrl" />
	<spring:eval expression="@global['chat.url']" var="chatbotUrl" />
	<spring:eval expression="@global['sso.url']" var="ssoUrl" />

	<input type="hidden" id="his_loginUrl" value='${loginUrl }'>
	<input type="hidden" id="his_adminUrl" value='${adminUrl }'>
	<input type="hidden" id="his_chatbotUrl" value='${chatbotUrl }'>
	<input type="hidden" id="his_ssoUrl" value='${ssoUrl }'>
</form>