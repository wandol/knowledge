<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="dataTables_paginate">
	<ul class="pagination">
		<li class="paginate_button first" id="dt_basic_first"><a href="javascript:gotoPage(${paging.firstPageNo}, '${menu }')" aria-controls="dt_basic" data-dt-idx="0" tabindex="0">First</a></li>
		<li class="paginate_button previous" id="dt_basic_previous"><a href="javascript:gotoPage(${paging.prevPageNo}, '${menu }')" aria-controls="dt_basic" data-dt-idx="0" tabindex="0">Previous</a></li>
		<c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
	        <c:choose>
	            <c:when test="${i eq params.pageNum}"><li class="paginate_button active"><a href="#" aria-controls="dt_basic" data-dt-idx="${i }" tabindex="0">${i }</a></li></c:when>
	            <c:otherwise><li class="paginate_button "><a href="javascript:gotoPage(${i}, '${menu }')" aria-controls="dt_basic" data-dt-idx="${i}" tabindex="0">${i}</a></li></c:otherwise>
	        </c:choose>
	    </c:forEach>		
		<li class="paginate_button next" id="dt_basic_next"><a href="javascript:gotoPage(${paging.nextPageNo}, '${menu }')" aria-controls="dt_basic" data-dt-idx="3" tabindex="0">Next</a></li>
		<li class="paginate_button last" id="dt_basic_last"><a href="javascript:gotoPage(${paging.finalPageNo}, '${menu }')" aria-controls="dt_basic" data-dt-idx="4" tabindex="0">Last</a></li>
	</ul>
</div>