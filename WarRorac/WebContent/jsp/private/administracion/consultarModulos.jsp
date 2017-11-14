<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"   	 value="administracion" />
	<jsp:param name="menuSubItem"    value="modulos" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/administracion/consultarModulos.js" type="text/javascript"></script>

<spring:message code="administracion.modulo"            	var="modulo"/>
<spring:message code="administracion.modulos"     		var="modulos"/>
<spring:message code="administracion.nombreModulo"   		var="nombreModulos"/>
<spring:message code="administracion.pantallaAsignada" 		var="pantallaAsignada"/>
<spring:message code="administracion.nuevoModulo" 		var="nuevoModulo"/>
<spring:message code="administracion.detalle"			var="detalle"/>
<spring:message code="administracion.seleccioneModulo"		var="seleccioneModulo"/>
<spring:message code="administracion.descripcionModulo"     	var="descripcionModulos"/>

<spring:message code="administracion.faltaSeleccionarModulo"     	var="faltaSeleccionarModulo"/>
<spring:message javaScriptEscape="true" code="administracion.faltaModulo"     			var="faltaModulo"/>
<spring:message code="administracion.seleccioneModulo"     		var="seleccioneModulo"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${modulo}</span> - ${modulos}
</div>


<form action="modificarModuloInit.do" name="modificar" id="modificar" method="post">
<input id="faltaSeleccionarModulo" type="hidden" value="${faltaSeleccionarModulo}"/>
	<input id="faltaModulo" type="hidden" value="${faltaModulo}"/>
	<input id="seleccioneModulo" type="hidden" value="${seleccioneModulo}"/>

<div class="frameTablaVariasColumnas">
	<div class="titleTablaVariasColumnas">${modulos} <span class="textosin">- ${seleccioneModulo}</span></div>
		<div class="contentTablaVariasColumnas" style="height:300px;overflow:auto;">
			<table>
				<tr>
					<th width="100" colspan="2" class="text_izquierda">${nombreModulos}</th>
					<th width="250" class="text_centro" scope="col">${descripcionModulos}</th>
				</tr>
			
				<tr>
			
					<Td colspan="3" class="special"></Td>
				</tr>
				<tbody>
					<c:forEach var="regs" items="${todosModulos}">
					<tr class="odd2">
						<td width="20"><input name="idModulo" type="radio" class="Campos"
						id="idModulo" value=${regs.idModulo}></input></td>
						<td width="120" class="text_izquierda">${regs.nombreModulo}</td>
						<td width="120" class="text_izquierda">${regs.descripcionModulo}</td>
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
					<td class="izq"><a href="#" id="detalleModulo" name="detalleModulo">${detalle}</td>
				</tr>
			</table>
		</div>
	</div>
</form>
<form action="altaModuloInit.do" id="altaModulo" name="altaModulo" method="post">
<div class="PiePag"><button name="btnGuardaModulo" id="btnGuardaModulo" type="submit">${nuevoModulo}</button></div>
</form>
<jsp:include page="../myFooter.jsp" flush="true"/>
