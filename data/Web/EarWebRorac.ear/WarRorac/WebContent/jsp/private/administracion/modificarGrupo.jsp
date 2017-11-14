<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"   	 value="administracion" />
	<jsp:param name="menuSubItem"    value="grupos" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/administracion/modificarGrupo.js" type="text/javascript"></script>

<spring:message code="administracion.modulo"            	var="modulo"/>
<spring:message code="administracion.formularioModifica"	var="formularioModifica"/>
<spring:message code="administracion.modificarGrupo"  		var="modificarGrupo"/>
<spring:message code="administracion.camposObligatorios"     	var="camposObligatorios"/>
<spring:message code="administracion.datosGrupo"     		var="datosGrupo"/>
<spring:message code="administracion.nombreGrupo"     		var="nombreGrupo"/>
<spring:message code="administracion.descripcion"     		var="descripcion"/>
<spring:message code="administracion.pantallas"     		var="pantallas"/>
<spring:message code="administracion.nombrePantalla"   		var="nombrePantalla"/>
<spring:message code="administracion.pantallaAsignada" 		var="pantallaAsignada"/>
<spring:message code="administracion.guardarGrupo" 		var="guardarGrupo"/>
<spring:message code="administracion.cancelar" 			var="cancelar"/>
<spring:message code="administracion.borrar" 			var="borrar"/>
<spring:message code="administracion.divSeleccionarPantalla" 		var="divSeleccionarPantalla"/>
<spring:message code="administracion.divIngresarNombre" 			var="divIngresarNombre"/>
<spring:message code="administracion.divIngresarDescripcion" 			var="divIngresarDescripcion"/>

<spring:message code="administracion.gralCamposObligatorios"     	var="gralCamposObligatorios"/>
<spring:message code="administracion.gralFaltanCampos"     			var="gralFaltanCampos"/>
<spring:message code="administracion.gralVerifique"     		var="gralVerifique"/>


<div class="pageTitleContainer">
   <span class="pageTitle">${modulo}</span> - ${formularioModifica}
</div>

<form name="modificarGrupo" id="modificarGrupo" method="post">
<input type="hidden" name="idGrupo" id="idGrupo" value="<c:out value="${grupo.idGrupo}"/>"/>
	<input id="gralCamposObligatorios" type="hidden" value="${gralCamposObligatorios}"/>
	<input id="gralFaltanCampos" type="hidden" value="${gralFaltanCampos}"/>
	<input id="gralVerifique" type="hidden" value="${gralVerifique}"/>
<div class="frameFormularioB" id="divFormulario">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${formularioModifica} - <span class="textosin">${modificarGrupo}</span></div>
			<table>
				<tbody>
					<tr>
						<td colspan="4" class="ind">${camposObligatorios}</td>
					</tr>
					<tr>
						<th colspan="4" class="text_izquierda">${datosGrupo}<span
							class="textosin">.......................................................................................................</span></th>
					</tr>
					<tr>
						<td width="154" class="odd">${nombreGrupo}:</td>
						<td colspan="3"><input name="nombreGrupo"
							type="text" class="Campos_Des" id="nombreGrupo" maxlength="80" value="<c:out value="${grupo.nombreGrupo}"/>"/></td><div style="color:#FF0000;" id="nombreRequerido">${divIngresarNombre}</div>
					</tr>
					<tr>
						<td class="odd">${descripcion}:</td>
						<td colspan="3"><textarea rows="6" cols="50" name="descripcionGrupo" id="descripcionGrupo"><c:out value="${grupo.descripcionGrupo}"/></textarea>(300 caracteres m&aacute;ximo)</td><div style="color:#FF0000;" id="descripcionRequerido">${divIngresarDescripcion}</div>
					</tr>
				</tbody>
			</table>
			
		</div>
		<div class="framePieContenedor">
			
		</div>
</div>

<div class="frameTablaVariasColumnas" id="divPantallas">
	<div class="titleTablaVariasColumnas">${pantallas}</div>
		<div class="contentTablaVariasColumnas" style="height:300px;overflow:auto;">
			<table>
				<tr>
					<th width="300" class="text_izquierda">${nombrePantalla}</th>
					<th width="50" class="text_centro" scope="col">${pantallaAsignada}</th>
				</tr>
			
				<tr>
			
					<Td colspan="2" class="special"></Td>
				</tr>
				<tbody><div style="color:#FF0000;" id="pantallaRequerida">${divSeleccionarPantalla}</div>
					<c:forEach var="pantalla" items="${todasPantallas}">
					<tr class="odd2">
						<td class="text_izquierda">${pantalla.nombrePantalla}</td>
						<td class="text_izquierda"><input type="checkbox" name="pantallaActiva" value="${pantalla.idPantalla}"
							<c:if test="${pantalla.pantallaSeleccionada}">checked</c:if>/>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
	</div>
</div>
<div class="PiePag"><button name="btnModificaGpo" id="btnModificaGpo" type="button">${guardarGrupo}</button><button name="btnBorrarGpo" id="btnBorrarGpo" type="button">${borrar}</button><button name="regresar" id="regresar" type="button">${cancelar}</button></div>
</form>
<jsp:include page="../myFooter.jsp" flush="true"/>
