<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="consultas" />
	<jsp:param name="menuSubItem"    value="consultasPorContrato" />
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="consultaIOFinales.titulo" var="titulo"/>
<spring:message code="consultaIOFinales.tituloTabla" var="tituloTabla"/>
<spring:message code="consultaIOFinales.introduceNumeroContrato" var="introduceNumeroContrato"/>
<spring:message code="consultaIOFinales.seleccionArchivo" var="seleccionArchivo"/>
<spring:message code="consultaIOFinales.inputFinalActivo" var="inputFinalActivo"/>
<spring:message code="consultaIOFinales.inputFinalPasivo" var="inputFinalPasivo"/>
<spring:message code="consultaIOFinales.outputFinalActivo" var="outputFinalActivo"/>
<spring:message code="consultaIOFinales.outputFinalPasivo" var="outputFinalPasivo"/>
<spring:message code="consultaIOFinales.instruccionSeleccionPeriodo" var="instruccionSeleccionPeriodo"/>
<spring:message code="consultaIOFinales.mes" var="mes"/>
<spring:message code="consultaIOFinales.anio" var="anio"/>
<spring:message code="consultaIOFinales.mensajeEjecutar" var="mensajeEjecutar"/>
<spring:message code="consultaIOFinales.tituloResultadoConsulta" var="tituloResultadoConsulta"/>
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

<script type="text/javascript">
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
		<form action="consultaIOFinales.do" method="post" name="consultaIOForm">
			<table class="tablaFormulario">
				<tbody>
					<tr>
						<td colspan="2" class="odd">${introduceNumeroContrato}:</td>
						<td><input type="text" name="numeroContrato"/></td>
					</tr>
					<tr>
						<td colspan="2" class="odd">${seleccionArchivo}:</td>	
						<td colspan="2">
							<select name="idArchivo">
								<option value="NONE">${seleccione }</option>
								<option value="1">${inputFinalActivo}</option>
								<option value="2">${inputFinalPasivo}</option>
								<option value="3">${outputFinalActivo}</option>
								<option value="4">${outputFinalPasivo}</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="odd">${instruccionSeleccionPeriodo}</td>
						<td>${mes}:</td>
						<td>
							<select name="mes">
								<option value="NONE">${seleccione}</option>
								<option value="01">${enero}</option>
								<option value="02">${febrero}</option>
								<option value="03">${marzo}</option>
								<option value="04">${abril}</option>
								<option value="05">${mayo}</option>
								<option value="06">${junio}</option>
								<option value="07">${julio}</option>
								<option value="08">${agosto}</option>
								<option value="09">${septiembre}</option>
								<option value="10">${octubre}</option>
								<option value="11">${noviembre}</option>
								<option value="12">${diciembre}</option>
							</select>
						</td>
						<td>${anio}:</td>
						<td>
							<select name="anio">
								<c:forEach items="${listaAnios}" var="anio">
									<option value="${anio.key}">${anio.value}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</tbody>
			</table>
			<div>
				<a href="javascript:validaConsultaIOForm()" class="boton">${mensajeEjecutar}</a>
			</div>
		</form>
	</div>
</div>
<br/>
<c:if test="${not empty consultaEjecutada}">
	<c:choose>
		<c:when test="${consultaEjecutada == '1'}">
			<c:set var="nombreArchivo" value="${inputFinalActivo}"/>
		</c:when>
		<c:when test="${consultaEjecutada == '2'}">
			<c:set var="nombreArchivo" value="${inputFinalPasivo}"/>
		</c:when>
		<c:when test="${consultaEjecutada == '3'}">
			<c:set var="nombreArchivo" value="${outputFinalActivo}"/>
		</c:when>
		<c:when test="${consultaEjecutada == '4'}">
			<c:set var="nombreArchivo" value="${outputFinalPasivo}"/>
		</c:when>
	</c:choose> 
	<c:choose>
		<c:when test="${not empty resultadoConsulta}">
			<div class="frameFormularioConfirmacion">
				<div class="titleFormularioConfirmacion">
					${tituloResultadoConsulta}&nbsp;${nombreArchivo}
				</div>
				<c:forEach items="${resultadoConsulta}" var="registro">
				<div class="contentFormularioConfirmacion" style="border-bottom: 3px solid #F00;">
					<table>
						<tbody>
							<c:forEach items="${registro}" var="columna">
								<tr>
									<td class="odd">${columna.key}:</td>
									<td class="text_izquierda">${columna.value}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				</c:forEach>
			</div>
		</c:when>
		<c:when test="${empty resultadoConsulta}">
			<spring:message code="consultaIOFinales.mensajeNoCoincidencias" var="mensajeNoCoincidencia"/>
			<spring:message code="consultaIOFinales.mensajeNoCoincidencias2" var="mensajeNoCoincidencia2"/>
			<script type="text/javascript">
				jInfo("", "${mensajeNoCoincidencia}" + " " + "${nombreArchivo}" + " " + "${mensajeNoCoincidencia2}" + " " + "${contrato}", "Info", "");
			</script>
		</c:when>
	</c:choose>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true"/>