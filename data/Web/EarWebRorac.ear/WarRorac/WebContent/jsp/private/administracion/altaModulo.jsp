<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"   	 value="administracion" />
	<jsp:param name="menuSubItem"    value="modulos" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/administracion/altaModulo.js" type="text/javascript"></script>

<spring:message code="administracion.modulo"            	var="tipoModulo"/>

<spring:message code="administracion.pantallas"			var="pantallas"/>
<spring:message code="administracion.modificarModulo"     	var="modificarModulo"/>
<spring:message code="administracion.datosPantalla"    		var="datosPantalla"/>
<spring:message code="administracion.nombrePantalla"     	var="nombrePantalla"/>

<spring:message code="administracion.camposObligatorios"     	var="camposObligatorios"/>
<spring:message code="administracion.descripcionModulo"     	var="descripcionModulo"/>

<spring:message code="administracion.guardarModulo" 		var="guardarModulo"/>
<spring:message code="administracion.cancelar"	 		var="cancelar"/>
<spring:message code="administracion.borrar" 			var="borrar"/>

<spring:message code="administracion.modulos" 			var="modulos"/>
<spring:message code="administracion.nombreModulo" 		var="nombreDelModulo"/>
<spring:message code="administracion.moduloAsignado" 		var="moduloAsignado"/>
<spring:message code="administracion.seleccioneModulo" 		var="seleccioneModulo"/>
<spring:message code="administracion.urlPantalla" 		var="urlPantalla"/>

<spring:message code="administracion.divIngresarNombre" 			var="divIngresarNombre"/>
<spring:message code="administracion.divIngresarDescripcion" 			var="divIngresarDescripcion"/>

<spring:message code="administracion.gralCamposObligatorios"     	var="gralCamposObligatorios"/>
<spring:message code="administracion.gralFaltanCampos"     			var="gralFaltanCampos"/>
<spring:message code="administracion.gralVerifique"     		var="gralVerifique"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${tipoModulo}</span> - ${modulos}
</div>


<form action="" id="modificarModulo" name="modificarModulo" method="post">
<input type="hidden" name="idModulo" id="idModulo" value="<c:out value="${modulo.idModulo}"/>"/>
	<input id="gralCamposObligatorios" type="hidden" value="${gralCamposObligatorios}"/>
	<input id="gralFaltanCampos" type="hidden" value="${gralFaltanCampos}"/>
	<input id="gralVerifique" type="hidden" value="${gralVerifique}"/>
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${modulos} - <span class="textosin">${modificarModulo}</span></div>
			<table>
				<tbody>
					<tr>
						<td colspan="4" class="ind">${camposObligatorios}</td>
					</tr>
					<tr>
						<th colspan="4" class="text_izquierda">${modificarModulo}<span
							class="textosin">.......................................................................................................</span></th>
					</tr>
					<tr>
						<td width="154" class="odd">${nombreDelModulo}:</td>
						<td colspan="3"><input name="nombreModulo"
							type="text" class="Campos_Des" maxlength="80" id="nombreModulo" /></td><div style="color:#FF0000;" id="nombreRequerido">${divIngresarNombre}</div>
					</tr>
					<tr>
						<td class="odd">${descripcionModulo}:</td>
						<td colspan="3"><textarea rows="4" cols="50" id="descripcionModulo" name="descripcionModulo"></textarea>(300 caracteres m&aacute;ximo)</td><div style="color:#FF0000;" id="descripcionRequerido">${divIngresarDescripcion}</div>
					</tr>
				</tbody>
			</table>
			
		</div>
		<div class="framePieContenedor">
			
		</div>
</div>

<div class="PiePag"><button name="btnGuardaModulo" id="btnGuardaModulo" type="button">${guardarModulo}</button><button name="regresar" id="regresar" type="button">${cancelar}</button></div>
</form>
<jsp:include page="../myFooter.jsp" flush="true"/>
