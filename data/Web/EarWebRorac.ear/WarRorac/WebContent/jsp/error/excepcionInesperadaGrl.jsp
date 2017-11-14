<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../private/myHeader.jsp" flush="true"/>
<jsp:include page="../private/myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="principal" />
</jsp:include>

<spring:message code="error.general.tipoError" var="tipoError"/>
<spring:message code="error.general.tituloError" var="titulo"/>
<spring:message code="error.general.descripcionError" var="descripcion"/>	
	
<div class="pageTitleContainer">
	<span class="pageTitle">${titulo}</span>
</div>
<br>		
<div class="frameSimpleContainer">
	<div class="titleSimpleContainer">${tipoError}</div>
	<div class="contentSimpleContainer">
		${descripcion}
	</div>
</div>
		
<jsp:include page="../private/myFooter.jsp" flush="true"/>