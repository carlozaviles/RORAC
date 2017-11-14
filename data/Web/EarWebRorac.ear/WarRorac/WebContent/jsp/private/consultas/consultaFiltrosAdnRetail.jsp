<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="tablasParametros" />
	<jsp:param name="menuSubItem"    value="consultaTablas" />
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="abcTablaParametros.adnRetail.titulo.consulta" var="titulo"/>
<spring:message code="abcTablaParametros.adnRetail.titulotabla.consulta" var="titulo"/>
<spring:message code="abcTablaParametros.accion.eliminar" var="eliminar"/>
<spring:message code="abcTablaParametros.accion.aceptar" var="aceptar"/>
<spring:message code="abcTablaParametros.adnRetail.idSegmentoLocal" var="idSegmentoLocal"/>
<spring:message code="abcTablaParametros.adnRetail.descripcion" var="descripcion"/>
<spring:message code="abcTablaParametros.adnRetail.banca" var="banca"/>
<spring:message code="abcTablaParametros.adnRetail.flagRetail" var="flagRetail"/>
<spring:message code="abcTablasParametros.general.confirmacionUsuario.eliminacion" var="confirmacionEliminacion"/>
<spring:message code="abcTablasParametros.general.mensajeUsuario.noRegistrosSeleccionados" var="mensajeNoRegistros"/>

<script type="text/javascript">
	var confirmacionEliminacion = "${confirmacionEliminacion}";
	var mensajeNoRegistros = "${mensajeNoRegistros}";
</script>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<br/>
<div class="frameTablaEstandar">
	<div class="titleTablaEstandar">
		${tituloTabla}
	</div>
	<div class="contentTablaEstandar">
		<form:form action="eliminaRegistro.do" method="POST" commandName="registrosAEliminar">
			<table class="contenido">
				<thead>
					<tr>
						<th>${idSegmentoLocal}</th>
						<th>${descripcion}</th>
						<th>${banca}</th>
						<th>${flagRetail}</th>
						<th>${eliminar}</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty listaAdnRetail}">
						<c:set var="cont" value="2"/>	
						<c:set var="urlConsulta" value="muestraAdnRetail.do?id="/>
						<c:forEach items="${listaAdnRetail}" var="registro">
							<c:set var="cont" value="${(cont % 2) + 1}"/>
							<tr class="odd${cont}">
								<td><a href="${urlConsulta}${registro.idSegmentoLocal}">${registro.idSegmentoLocal}</a></td>
								<td><a href="${urlConsulta}${registro.idSegmentoLocal}">${registro.descripcion}</a></td>
								<td><a href="${urlConsulta}${registro.idSegmentoLocal}">${registro.banca}</a></td>
								<td><a href="${urlConsulta}${registro.idSegmentoLocal}">${registro.flagRetail}</a></td>
								<td><form:checkbox path="listaOpciones" value="${registro.idSegmentoLocal}"/></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<form:hidden path="idFiltro" value="adnRetail"/>
			<div>
				<a href="javascript:validaEliminacionRegistros()" class="boton">${aceptar}</a>
			</div>
		</form:form>
	</div>
</div>
<c:if test="${not empty confirmacionBaja}">
	<spring:message code="abcTablaParametros.confirmacion.baja" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}", "Info", "");
	</script>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true"/>