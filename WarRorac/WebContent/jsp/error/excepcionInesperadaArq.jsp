<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../private/myHeader.jsp" flush="true"/>
<jsp:include page="../private/myMenu.jsp" flush="true"/>

<spring:message code="error.${codeError}.tipoError" var="tipoError"/>
<spring:message code="error.${codeError}.tituloError" var="titulo"/>
<spring:message code="error.${codeError}.descripcionError" var="descripcion"/>
<spring:message code="error.mensaje.codigoError" var="mensajeCodError"/>

<div class="pageTitleContainer">
	<span class="pageTitle">${titulo}</span>
</div>
<br>
<div class="frameSimpleContainer">
	<div class="titleSimpleContainer">${tipoError}</div>
	<div class="contentSimpleContainer">
		<span>${descripcion}</span>
		<br>
		<br>
		<span>${mensajeCodError}&nbsp;${codeError}</span>
	</div>
</div>
<jsp:include page="../private/myFooter.jsp" flush="true"/>


