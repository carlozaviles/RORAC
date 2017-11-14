<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="tablasParametros" />
	<jsp:param name="menuSubItem"    value="altaTablas" />
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="abcTablaParametros.flagNeteo.titulo.${operacion}" var="titulo"/>
<spring:message code="abcTablaParametros.flagNeteo.tituloTabla.${operacion}" var="tituloTabla"/>
<spring:message code="abcTablaParametros.flagNeteo.valor" var="valor"/>
<spring:message code="abcTablaParametros.flagNeteo.accion.${operacion}" var="accion"/>

<spring:message code="abcTablasParametros.general.confirmacionModificacion" var="mensajeConfirmacionModificacion"/>

<script type="text/javascript">
	var operacion = "${operacion}";
	var nombreValor = "${valor}";
	
	var mensajeConfirmacion = "${mensajeConfirmacionModificacion}";
</script>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTabla}</div>
		<form:form action="${operacion}FlagNeteo.do" method="POST"  commandName="flagNeteoForm">
			<table>
				<tbody>
					<tr>
						<td class="odd">${valor}:</td>
						<td><form:input path="valor"/></td>	
					</tr>
				</tbody>
			</table>
			<form:hidden path="idRegistro"/>
			<div >
				<a href="javascript:validaFormFlagNeteo()" class="boton">${accion}</a>
			</div>
		</form:form>
	</div>
</div>
<c:if test="${not empty confirmacionModificacion}">
	<spring:message code="abcTablaParametros.flagNeteo.confirmacion.modifica" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionModificacion}", "Info", "");
	</script>
</c:if>
<jsp:include page="../myFooter.jsp" flush="true"/>