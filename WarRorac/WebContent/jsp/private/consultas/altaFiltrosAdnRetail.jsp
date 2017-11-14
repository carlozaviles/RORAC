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

<spring:message code="abcTablaParametros.adnRetail.titulo.${operacion}" var="titulo"/>
<spring:message code="abcTablaParametros.adnRetail.tituloTabla.${operacion}" var="tituloTabla"/>
<spring:message code="abcTablaParametros.adnRetail.idSegmentoLocal" var="idSegmentoLocal"/>
<spring:message code="abcTablaParametros.adnRetail.descripcion" var="descripcion"/>
<spring:message code="abcTablaParametros.adnRetail.banca" var="banca"/>
<spring:message code="abcTablaParametros.adnRetail.select.particulares" var="particulares"/>
<spring:message code="abcTablaParametros.adnRetail.select.empresas" var="empresas"/>
<spring:message code="abcTablaParametros.adnRetail.select.instituciones" var="instituciones"/>
<spring:message code="abcTablaParametros.adnRetail.select.bmg" var="bmg"/>
<spring:message code="abcTablaParametros.adnRetail.flagRetail" var="flagRetail"/>
<spring:message code="abcTablaParametros.adnRetail.accion.${operacion}" var="accion"/>
<spring:message code="abcTablasParametros.general.confirmacionAlta" var="mensajeConfirmacionAlta"/>
<spring:message code="abcTablasParametros.general.confirmacionModificacion" var="mensajeConfirmacionModificacion"/>

<script type="text/javascript">
	var operacion = "${operacion}";
	var nombreIdSegmento = "${idSegmentoLocal}";
	var nombreDescripcion = "${descripcion}";
	var nombreFlagRetail = "${flagRetail}";
	var mensajeConfirmacion = (operacion === 'alta')? "${mensajeConfirmacionAlta}" : "${mensajeConfirmacionModificacion}";
</script>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTabla}</div>
		<form:form action="${operacion}AdnRetail.do" method="POST"  commandName="adnRetailForm">
			<table>
				<tbody>
					<tr>
						<td class="odd">${idSegmentoLocal}:</td>
						<c:if test="${operacion == 'alta'}">
							<td><form:input path="idSegmentoLocal"/></td>
						</c:if>
						<c:if test="${operacion == 'modifica'}">
							<td><input type="text" value="${valorIdSegmento}" disabled/></td>
							<form:hidden path="idSegmentoLocal"/>
						</c:if>	
					</tr>
					<tr>
						<td class="odd">${descripcion}:</td>
						<td><form:textarea path="descripcion" rows="2" cols="20"/></td>
					</tr>
					<tr>
						<td class="odd">${banca}:</td>
						<td>
							<form:select path="banca">
								<form:option value="${particulares}" label="${particulares}"/>
								<form:option value="${empresas}" label="${empresas}" />
								<form:option value="${instituciones}" label="${instituciones}"/>
								<form:option value="${bmg}" label="${bmg}"/>
							</form:select>
						</td>
					</tr>
					<tr>
						<td class="odd">${flagRetail}:</td>
						<td><form:input path="flagRetail"/></td>
					</tr>
				</tbody>
			</table>
			<div>
				<a href="javascript:validaFormAdnRetail()" class="boton">${accion}</a>
			</div>
		</form:form>
	</div>
</div>

<c:if test="${not empty confirmacionAlta}">
	<spring:message code="abcTablaParametros.adnRetail.confirmacion.alta" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionAlta}", "Info", "");
	</script>
</c:if>
<c:if test="${not empty confirmacionModificacion}">
	<spring:message code="abcTablaParametros.adnRetail.confirmacion.modifica" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionModificacion}", "Info", "");
	</script>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true"/>