<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./tagLib.jsp" %>
<form id="historyForm" action="search.do" method="GET">	
	<input type="hidden" id="his_category" name="category" value="<c:out value="${params.category}" />"/>	
	<input type="hidden" id="his_kwd" name="kwd" value="<c:out value="${params.kwd}" />"/>			
	<input type="hidden" id="pageNum" name="pageNum" value="<c:out value="${params.pageNum}" />"/>	
	<input type="hidden" id="sort" name="sort" value="<c:out value="${params.sort}" />"/>	
	
	<input type="hidden" id="his_fields" name="fields" value="<c:out value="${params.fields}" />"/>
	<input type="hidden" id="his_powerComp" name="powerComp" value="<c:out value="${params.powerComp}" />"/>	
	<input type="hidden" id="his_powerSt" name="powerSt" value="<c:out value="${params.powerSt}" />"/>	
	<input type="hidden" id="his_stNo" name="stNo" value="<c:out value="${params.stNo}" />"/>	
	<input type="hidden" id="his_partName" name="partName" value="<c:out value="${params.partName}" />"/>
	<input type="hidden" id="his_partMid" name="partMid" value="<c:out value="${params.partMid}" />"/>
	<input type="hidden" id="his_symptomKwd" name="symptomKwd" value="<c:out value="${params.symptomKwd}" />"/>
	<input type="hidden" id="his_actionKwd" name="actionKwd" value="<c:out value="${params.actionKwd}" />"/>
	<input type="hidden" id="his_startPublishYm" name="startPublishYm" value="<c:out value="${params.startPublishYm}" />"/>
	<input type="hidden" id="his_endPublishYm" name="endPublishYm" value="<c:out value="${params.endPublishYm}" />"/>
	<input type="hidden" id="his_isImg" name="isImg" value="<c:out value="${params.isImg}" />"/>
	<input type="hidden" id="his_srchFd" name="srchFd" value="<c:out value="${params.srchFd}" />"/>
<%-- 	<input type="hidden" id="his_startDate" name="startDate" value="<c:out value="${params.startDate}" />"/>	 --%>
<%-- 	<input type="hidden" id="his_endDate" name="endDate" value="<c:out value="${params.endDate}" />"/>	 --%>
	
<%-- 	<input type="hidden" id="date" name="date" value="<c:out value="${params.date}" />"/>	 --%>
<%-- 	<input type="hidden" id="his_repoKind" name="repoKind" value="<c:out value="${params.repoKind}" />"/> --%>
<%-- 	<input type="hidden" id="his_powerType" name="powerType" value="<c:out value="${params.powerType}" />"/>	 --%>	
	
	<!-- 결과내 재탐색 추가 -->
<%-- 	<input type="hidden" id="his_resrch" name="reSrchFlag" value="${params.reSrchFlag}"/> --%>
<%-- 	<input type="hidden" id="hstr_preKwds" name="preKwds" value="${params.preKwds}"/> --%>
	<!-- 결과내 재탐색 추가 -->
	
	<!-- 가중치 추가 -->
<%-- 	<input type="hidden" id="his_weight" name="weight" value="${params.weight}"/> --%>
	<!-- 가중치 추가 -->
	
<%-- 	<input type="hidden" id="his_pfKwd" name="pfKwd" value="<c:out value="${params.pfKwd}" />"/> --%>
</form>