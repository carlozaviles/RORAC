<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"   	 value="administracion" />
	<jsp:param name="menuSubItem"    value="grupos" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/administracion/consultaGrupo.js" type="text/javascript"></script>

<spring:message code="administracion.modulo"            var="modulo"/>
<spring:message code="administracion.tituloGrupo"    	var="tituloGrupo"/>
<spring:message code="administracion.grupo"         	var="grupo"/>
<spring:message code="administracion.descripcion"    	var="descripcion"/>
<spring:message code="administracion.nuevoGrupo"     	var="nuevoGrupo"/>
<spring:message code="administracion.seleccione"     	var="seleccione"/>
<spring:message code="administracion.detalle"     	var="detalle"/>

<spring:message code="administracion.faltaSeleccionarGrupo"     	var="faltaSeleccionarGrupo"/>
<spring:message javaScriptEscape="true" code="administracion.faltaGrupo"     			var="faltaGrupo"/>
<spring:message code="administracion.seleccioneGrupo"     		var="seleccioneGrupo"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${modulo}</span> - ${tituloGrupo}
</div>
<form action="modificarGrupoInit.do" name="modificar" id="modificar" method="post">
	<input id="faltaSeleccionarGrupo" type="hidden" value="${faltaSeleccionarGrupo}"/>
	<input id="faltaGrupo" type="hidden" value="${faltaGrupo}"/>
	<input id="seleccioneGrupo" type="hidden" value="${seleccioneGrupo}"/>

	<div class="frameTablaVariasColumnas">
	<div class="titleTablaVariasColumnas">${grupo} <span class="textosin">- ${seleccione}</span></div>
		<div class="contentTablaVariasColumnas" style="height:300px;overflow:auto;">
			<table>
				<tr>
					<th width="122" colspan="2" class="text_izquierda">${grupo}</th>
					<th width="223" class="text_centro" scope="col">${descripcion}</th>
				</tr>
			
				<tr>
					<Td colspan="3" class="special"></Td>
				</tr>
				<tbody>
					<c:forEach var="regs" items="${registros}">
					<tr class="odd2">
						<td width="20"><input name="idGrupo" type="radio" class="Campos"
						id="idGrupo" value=${regs.idGrupo}></input></td>
						<td width="100" class="text_izquierda">${regs.nombreGrupo}</td>
						<td width="140" class="text_izquierda">${regs.descripcionGrupo}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
	<div class="framePieContenedor">
		<div class="contentPieContenedor">
			<table>
				<tr>
					<td class="izq"><a href="#" id="detalleGrupo" name="detalleGrupo">${detalle}</a></td>
				</tr>
			</table>
		</div>
	</div>
</div>
</form>
<form action="altaGrupoInit.do" method="post" name="altaGrupo" id="altaGrupo">
<div class="PiePag"><button name="btnAltaPerfil" id="btnAltaPerfil" type="submit">${nuevoGrupo}</button></div>
</form>
<jsp:include page="../myFooter.jsp" flush="true"/>
