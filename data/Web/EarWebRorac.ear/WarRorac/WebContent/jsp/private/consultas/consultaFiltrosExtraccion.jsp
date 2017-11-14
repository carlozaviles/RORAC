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

<spring:message code="abcTablaParametros.filtrosExtraccion.titulo" var="titulo"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tituloTablaAdnLocal" var="tituloTablaAdnLocal"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tituloTablaProductoGestion" var="tituloTablaProductoGestion"/>
<spring:message code="abcTablaParametros.accion.aceptar" var="aceptar"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.idAdnLocal" var="idAdnLocal"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaProductoGestion.idProductoGestion" var="idProductoGestion"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.descripcion" var="descripcion"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.banca" var="banca"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagActivo" var="flagActivo"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagPasivo" var="flagPasivo"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagFondos" var="flagFondos"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagComiciones" var="flagComiciones"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagContingentes" var="flagContingentes"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagAjustes" var="flagAjustes"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagInternegocios" var="flagInternegocios"/>
<spring:message code="abcTablaParametros.accion.eliminar" var="eliminar"/>
<spring:message code="abcTablasParametros.general.confirmacionUsuario.eliminacion" var="confirmaEliminacion"/>
<spring:message code="abcTablasParametros.general.mensajeUsuario.noRegistrosSeleccionados" var="mensajeNoRegistros"/>

<script type="text/javascript">
	var confirmacionEliminacion = "${confirmaEliminacion}";
	var mensajeNoRegistros = "${mensajeNoRegistros}";
</script>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<br/>
<div class="frameTablaEstandar">
	<div class="titleTablaEstandar">
		${tituloTablaAdnLocal}
	</div>
	<div class="contentTablaEstandar">
		<form:form action="eliminaRegistro.do" method="POST" commandName="registrosAEliminar">
			<table class="contenido">
				<thead>
					<tr>
						<th>${idAdnLocal}</th>
						<th>${descripcion}</th>
						<th>${banca}</th>
						<th>${flagActivo}</th>
						<th>${flagPasivo}</th>
						<th>${flagFondos}</th>
						<th>${flagComiciones}</th>
						<th>${flagContingentes}</th>
						<th>${flagAjustes}</th>
						<th>${flagInternegocios}</th>
						<th>${eliminar}</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty listaAdnLocal}">
						<c:set var="cont" value="2"/>	
						<c:set var="urlConsulta" value="muestraAdnLocal.do?id="/>
						<c:forEach items="${listaAdnLocal}" var="registro">
							<c:set var="cont" value="${(cont % 2) + 1}"/>
							<tr class="odd${cont}">
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.idAdnLocal}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.descripcion}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.banca}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagActivo}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagPasivo}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagFondos}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagComiciones}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagContingentes}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagAjustes}</a></td>
								<td><a href="${urlConsulta}${registro.idAdnLocal}">${registro.flagInternegocios}</a></td>
								<td><form:checkbox path="listaOpciones" value="${registro.idAdnLocal}"/></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<form:hidden path="idFiltro" value="adnLocal"/>
			<div>
				<a href="javascript:validaEliminacionRegistrosFE(0)" class="boton">${aceptar}</a>
			</div>
		</form:form>
	</div>
</div>
<br/>
<div class="frameTablaEstandar">
	<div class="titleTablaEstandar">
		${tituloTablaProductoGestion}
	</div>
	<div class="contentTablaEstandar">
		<form:form action="eliminaRegistro.do" method="POST" commandName="registrosAEliminar" onsubmit="return validaEliminacionRegistrosFE('registrosAEliminar', 1)">
			<table class="contenido">
				<thead>
					<tr>
						<th>${idProductoGestion}</th>
						<th>${descripcion}</th>
						<th>${flagActivo}</th>
						<th>${flagPasivo}</th>
						<th>${flagFondos}</th>
						<th>${flagComiciones}</th>
						<th>${flagContingentes}</th>
						<th>${flagAjustes}</th>
						<th>${flagInternegocios}</th>
						<th>${eliminar}</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty listaProductoGestion}">
						<c:set var="cont" value="2"/>	
						<c:set var="urlConsulta" value="muestraProductoGestion.do?id="/>
						<c:forEach items="${listaProductoGestion}" var="registro">
							<c:set var="cont" value="${(cont % 2) + 1}"/>
							<tr class="odd${cont}">
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.idProductoGestion}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.descripcion}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagActivo}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagPasivo}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagFondos}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagComiciones}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagContingentes}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagAjustes}</a></td>
								<td><a href="${urlConsulta}${registro.idProductoGestion}">${registro.flagInternegocios}</a></td>
								<td><form:checkbox path="listaOpciones" value="${registro.idProductoGestion}"/></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<form:hidden path="idFiltro" value="productoGestion"/>
			<div>
				<a href="javascript:validaEliminacionRegistrosFE(1)" class="boton ">${aceptar}</a>
			</div>
		</form:form>
	</div>
</div>

<jsp:include page="../myFooter.jsp" flush="true"/>