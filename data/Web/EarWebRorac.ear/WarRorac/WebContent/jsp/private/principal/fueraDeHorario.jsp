<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="principal" />
</jsp:include>


		
		<spring:message code="general.nombreAplicacion" var="app"/>
		<spring:message code="general.bienvenido"       var="welcome"/>
		
	
		<div class="pageTitleContainer">
			<span class="pageTitle">Modulo Principal</span> - Fuera de Horario
		</div>
		
		
<jsp:include page="../myFooter.jsp" flush="true"/>

