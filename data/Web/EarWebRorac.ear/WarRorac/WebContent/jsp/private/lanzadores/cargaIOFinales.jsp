<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="interfacesFinales" />
	<jsp:param name="menuSubItem"    value="edicionInterfacesFinales" />
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="cargaIO.tituloPrincipal" var="titulo"/>
<spring:message code="cargaIO.tituloTabla" var="tituloTabla"/>
<spring:message code="cargaIO.instruccion.seleccionArchivo" var="seleccionArchivo"/>
<spring:message code="cargaIO.inputFinalActivo" var="inputFinalActivo"/>
<spring:message code="cargaIO.inputFinalPasivo" var="inputFinalPasivo"/>
<spring:message code="cargaIO.operacion.actualizar" var="actualizar"/>
<spring:message code="cargaIO.operacion.eliminar" var="eliminar"/>
<spring:message code="cargaIO.ejecutar" var="ejecutar"/>
<spring:message code="cargaIO.operacionEnProceso" var="enProceso"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<br/>
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTabla}</div>
		<form action="actualizaInterfaz.do" method="post" name="cargaIOForm">
			<table class="tablaFormulario">
				<tbody>
					<tr>
						<th class="odd">${seleccionArchivo}:</th>
						<td align="center">${actualizar}</td>
						<td align="center">${eliminar}</td>
					</tr>
					<tr>
						<th>${inputFinalActivo}</th>
						<td align="center"><input type="checkbox" name="activoActualizar"/></td>
						<td align="center"><input type="checkbox" name="activoEliminar"/></td>
					</tr>					
					<tr>
						<th>${inputFinalPasivo}</th>
						<td align="center"><input type="checkbox" name="pasivoActualizar"/></td>
						<td align="center"><input type="checkbox" name="pasivoEliminar"/></td>
					</tr>
				</tbody>  
			</table>
			<div>
				<a href="javascript:confirmacionCargaIO()" class="boton">${ejecutar}</a>
			</div>
		</form>
	</div>
</div>
<c:if test="${success}">
	<spring:message code="cargaIO.confirmacionOperacion" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}", "Info", "");
	</script>
</c:if>
<c:if test="${failed}">
	<spring:message code="cargaIO.operacionEnProceso" var="operacionEnProceso"/>
	<script type="text/javascript">
		jError("", "${operacionEnProceso}", "Error", "");
	</script>
</c:if>
<jsp:include page="../myFooter.jsp" flush="true"/>
