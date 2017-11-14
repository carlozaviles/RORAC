<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"   	 value="administracion" />
	<jsp:param name="menuSubItem"    value="pantallas" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/administracion/consultaPantalla.js" type="text/javascript"></script>

<spring:message code="administracion.modulo"            	var="modulo"/>
<spring:message code="administracion.pantallas"     		var="pantallas"/>
<spring:message code="administracion.nombrePantalla"   		var="nombrePantalla"/>
<spring:message code="administracion.pantallaAsignada" 		var="pantallaAsignada"/>
<spring:message code="administracion.nuevaPantalla" 		var="nuevaPantalla"/>
<spring:message code="administracion.detalle"			var="detalle"/>
<spring:message code="administracion.seleccionePantalla"	var="seleccionePantalla"/>
<spring:message code="administracion.descripcionPantalla"     	var="descripcionPantalla"/>

<spring:message code="administracion.faltaSeleccionarPantalla"     	var="faltaSeleccionarPantalla"/>
<spring:message javaScriptEscape="true" code="administracion.faltaPantalla"     			var="faltaPantalla"/>
<spring:message code="administracion.seleccionePantalla"     		var="seleccionePantalla"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${modulo}</span> - ${pantallas}
</div>


<form action="modificarPantallaInit.do" name="modificar" id="modificar" method="post">

	<input id="faltaSeleccionarPantalla" type="hidden" value="${faltaSeleccionarPantalla}"/>
	<input id="faltaPantalla" type="hidden" value="${faltaPantalla}"/>
	<input id="seleccionePantalla" type="hidden" value="${seleccionePantalla}"/>

<div class="frameTablaVariasColumnas">
	<div class="titleTablaVariasColumnas">${pantallas} <span class="textosin">- ${seleccionePantalla}</span></div>
		<div class="contentTablaVariasColumnas" style="height:300px;overflow:auto;">
			<table>
				<tr>
					<th width="100" colspan="2" class="text_izquierda">${nombrePantalla}</th>
					<th width="250" class="text_centro" scope="col">${descripcionPantalla}</th>
				</tr>
			
				<tr>
			
					<Td colspan="3" class="special"></Td>
				</tr>
				<tbody>
					<c:forEach var="regs" items="${todasPantallas}">
					<tr class="odd2">
						<td width="20"><input name="idPantalla" type="radio" class="Campos"
						id="idPantalla" value=${regs.idPantalla}></input></td>
						<td width="120" class="text_izquierda">${regs.nombrePantalla}</td>
						<td width="120" class="text_izquierda">${regs.descripcionPantalla}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</div>
<div class="framePieContenedor">
		<div class="contentPieContenedor">
			<table>
				<tr>
					<td class="izq"><a href="#" id="detallePantalla" name="detallePantalla">${detalle}</td>
				</tr>
			</table>
		</div>
	</div>
</form>
<form action="altaPantallaInit.do" id="altaPantalla" name="altaPantalla" method="post">
<div class="PiePag"><button name="btnGuardaPerfil" id="btnGuardaPerfil" onclick="">${nuevaPantalla}</button></div>
</form>
<jsp:include page="../myFooter.jsp" flush="true"/>
