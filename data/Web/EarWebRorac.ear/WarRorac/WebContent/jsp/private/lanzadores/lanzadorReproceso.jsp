<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="reprocesoInputs" />
	<jsp:param name="menuSubItem"    value="lanzadorReproceso" />
</jsp:include>

<spring:message code="reprocesos.tituloPagina" var="titulo"/>
<spring:message code="reprocesos.tituloTabla" var="tituloTabla"/>
<spring:message code="reprocesos.instruccionSeleccionArchivo" var="instruccionSeleccionArchivo"/>
<spring:message code="reprocesos.instruccionSeleccionPeriodo" var="instruccionSeleccionPeriodo"/>
<spring:message code="reprocesos.mes" var="mes"/>
<spring:message code="reprocesos.anio" var="anio"/>
<spring:message code="reprocesos.accionReproceso" var="accionReproceso"/>
<spring:message code="reproceso.interfaces.inputFinalActivo" var="inputFinalActivo"/>
<spring:message code="reproceso.interfaces.inputFinalPasivo" var="inputFinalPasivo"/>
<spring:message code="general.mes.enero" var="enero"/>
<spring:message code="general.mes.febrero" var="febrero"/>
<spring:message code="general.mes.marzo" var="marzo"/>
<spring:message code="general.mes.abril" var="abril"/>
<spring:message code="general.mes.mayo" var="mayo"/>
<spring:message code="general.mes.junio" var="junio"/>
<spring:message code="general.mes.julio" var="julio"/>
<spring:message code="general.mes.agosto" var="agosto"/>
<spring:message code="general.mes.septiembre" var="septiembre"/>
<spring:message code="general.mes.octubre" var="octubre"/>
<spring:message code="general.mes.noviembre" var="noviembre"/>
<spring:message code="general.mes.diciembre" var="diciembre"/>
<spring:message code="general.select.vacio" var="seleccione"/>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<script type="text/javascript">
	var archivo = "Archivo";
	var campoMes = "${mes}";
	var campoAnio = "${anio}"; 
</script>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<br/>
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTabla}</div>
		<form:form action="lanzaReproceso.do" method="POST"  commandName="reprocesoForm">
			<table class="tablaFormulario">
				<tbody>
					<tr>
						<td class="odd">${instruccionSeleccionArchivo}:</td>
						<td colspan="4">
							<form:select path="indiceArchivo">
								<form:option value="NONE" label="${seleccione }"/>
								<form:option value="1" label="${inputFinalActivo}"/>
								<form:option value="2" label="${inputFinalPasivo}"/>
							</form:select>
						</td>	
					</tr>
					<tr>
						<td class="odd">${instruccionSeleccionPeriodo}</td>
						<td>${mes}:</td>
						<td>
							<form:select path="mes">
								<form:option value="NONE" label="${seleccione}"/>
								<form:option value="01" label="${enero}"/>
								<form:option value="02" label="${febrero }"/>
								<form:option value="03" label="${marzo }"/>
								<form:option value="04" label="${abril }"/>
								<form:option value="05" label="${mayo }"/>
								<form:option value="06" label="${junio }"/>
								<form:option value="07" label="${julio }"/>
								<form:option value="08" label="${agosto }"/>
								<form:option value="09" label="${septiembre }"/>
								<form:option value="10" label="${octubre }"/>
								<form:option value="11" label="${noviembre }"/>
								<form:option value="12" label="${diciembre }"/>
							</form:select>
						</td>
						<td>${anio}:</td>
						<td>
							<form:select path="anio">
								<form:options items="${listaAnios}"/>
							</form:select>
						</td>
					</tr>
				</tbody>
			</table>
			<div>
				<a href="javascript:validaFormReproceso()" class="boton">${accionReproceso}</a>
			</div>
		</form:form>
	</div>
</div>

<c:if test="${not empty periodo}">
	<spring:message code="reprocesos.confirmacion" var="confirmacion"/>
	<c:choose>
		<c:when test="${archivo == '1'}">
			<c:set var="interfaz" value="${inputFinalActivo}"/>
		</c:when>
		<c:when test="${archivo == '2'}">
			<c:set var="interfaz" value="${inputFinalPasivo}"/>
		</c:when>
	</c:choose>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${interfaz}" + " del periodo " + "${periodo}", "Info", "");
	</script>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true"/>