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

<spring:message code="abcTablaParametros.filtrosExtraccion.titulo.${operacion}" var="titulo"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.${operacion}" var="tituloTablaAdnLocal"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.idAdnLocal" var="idADNLocal"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.descripcion" var="descripcion"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.banca" var="banca"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.select.particulares" var="particulares"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.select.empresas" var="empresas"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.select.instituciones" var="instituciones"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.select.bmg" var="bmg"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagActivo" var="flagActivo"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagPasivo" var="flagPasivo"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagFondos" var="flagFondos"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagComiciones" var="flagComiciones"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagContingentes" var="flagContingentes"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagAjustes" var="flagAjustes"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.flagInternegocios" var="flagInternegocios"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaAdnLocal.accionAdnLocal.${operacion}" var="accionAdnLocal"/>

<spring:message code="abcTablaParametros.filtrosExtraccion.tablaProductoGestion.${operacion}" var="tituloTablaProductoGestion"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaProductoGestion.idProductoGestion" var="idProductoGestion"/>
<spring:message code="abcTablaParametros.filtrosExtraccion.tablaProductoGestion.accionProductoGestion.${operacion}" var="accionProductoGestion"/>

<spring:message code="abcTablasParametros.general.confirmacionAlta" var="mensajeConfirmacionAlta"/>
<spring:message code="abcTablasParametros.general.confirmacionModificacion" var="mensajeConfirmacionModificacion"/>

<script type="text/javascript">
	var operacion = "${operacion}";
	var nombreIdAdnLocal = "${idADNLocal}";
	var nombreDescripcion = "${descripcion}";
	var nombreBanca = "${banca}";
	var nombreFlagActivo = "${flagActivo}";
	var nombreFlagPasivo = "${flagPasivo}";
</script>

<script type="text/javascript">
	var nombreFlagFondos = "${flagFondos}";
	var nombreFlagComiciones = "${flagComiciones}";
	var nombreFlagContingentes = "${flagContingentes}";
	var nombreIdProductoGestion = "${idProductoGestion}";
	var nombreFlagAjustes = "${flagAjustes}";
	var nombreFlagInternegocios = "${flagInternegocios}";
	
	var mensajeConfirmacion = (operacion === 'alta')? "${mensajeConfirmacionAlta}" : "${mensajeConfirmacionModificacion}";
</script>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<c:if test="${not empty adnLocalForm}">
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTablaAdnLocal}</div>
		<form:form action="${operacion}AdnLocal.do" method="POST"  commandName="adnLocalForm">
			<table>
				<tbody>
					<tr>
						<td class="odd">${idADNLocal}:</td>
						<c:if test="${operacion == 'alta'}">
							<td><form:input path="idAdnLocal"/></td>
						</c:if>
						<c:if test="${operacion == 'modifica'}">
							<td><input type="text" value="${valorIdAdnLocal}" disabled/></td>
							<form:hidden path="idAdnLocal"/>
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
						<td class="odd">${flagActivo}:</td>
						<td><form:input path="flagActivo"/></td>
					</tr>
					<tr>
						<td class="odd">${flagPasivo}:</td>
						<td><form:input path="flagPasivo"/></td>
					</tr>
					<tr>
						<td class="odd">${flagFondos}:</td>
						<td><form:input path="flagFondos"/></td>
					</tr>
					<tr>
						<td class="odd">${flagComiciones}:</td>
						<td><form:input path="flagComiciones"/></td>
					</tr>
					<tr>
						<td class="odd">${flagContingentes}:</td>
						<td><form:input path="flagContingentes"/></td>
					</tr>
					<tr>
						<td class="odd">${flagAjustes}:</td>
						<td><form:input path="flagAjustes"/></td>
					</tr>
					<tr>
						<td class="odd">${flagInternegocios}:</td>
						<td><form:input path="flagInternegocios"/></td>
					</tr>
				</tbody>
			</table>
			<div>
				<a href="javascript:validaFormAdnLocal()" class="boton">${accionAdnLocal}</a>
			</div>
		</form:form>
	</div>
</div>
</c:if>
<c:if test="${not empty confirmacionAltaAdnLocal}">
	<spring:message code="abcTablaParametros.adnLocal.confirmacion.alta" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionAltaAdnLocal}", "Info", "");
	</script>
</c:if>
<c:if test="${not empty confirmacionModificacionAdnLocal}">
	<spring:message code="abcTablaParametros.adnLocal.confirmacion.modifica" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionModificacionAdnLocal}", "Info", "");
	</script>
</c:if>
<c:if test="${not empty productoGestionForm}">
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTablaProductoGestion}</div>
		<form:form action="${operacion}ProductoGestion.do" method="POST"  commandName="productoGestionForm">
			<table>
				<tbody>
					<tr>
						<td class="odd">${idProductoGestion}:</td>
						<c:if test="${operacion == 'alta'}">
							<td><form:input path="idProductoGestion"/></td>
						</c:if>
						<c:if test="${operacion == 'modifica'}">
							<td><input type="text" value="${valorIdProductoGestion}" disabled/></td>
							<form:hidden path="idProductoGestion"/>
						</c:if>
					</tr>
					<tr>
						<td class="odd">${descripcion}:</td>
						<td><form:textarea path="descripcion" rows="2" cols="20"/></td>
					</tr>
					<tr>
						<td class="odd">${flagActivo}:</td>
						<td><form:input path="flagActivo"/></td>
					</tr>
					<tr>
						<td class="odd">${flagPasivo}:</td>
						<td><form:input path="flagPasivo"/></td>
					</tr>
					<tr>
						<td class="odd">${flagFondos}:</td>
						<td><form:input path="flagFondos"/></td>
					</tr>
					<tr>
						<td class="odd">${flagComiciones}:</td>
						<td><form:input path="flagComiciones"/></td>
					</tr>
					<tr>
						<td class="odd">${flagContingentes}:</td>
						<td><form:input path="flagContingentes"/></td>
					</tr>
					<tr>
						<td class="odd">${flagAjustes}:</td>
						<td><form:input path="flagAjustes"/></td>
					</tr>
					<tr>
						<td class="odd">${flagInternegocios}:</td>
						<td><form:input path="flagInternegocios"/></td>
					</tr>
				</tbody>
			</table>
			<div>
				<a href="javascript:validaFormProductoGestion()" class="boton">${accionProductoGestion}</a>
			</div>
		</form:form>
	</div>
</div>
</c:if>
<c:if test="${not empty confirmacionAltaProductoGestion}">
	<spring:message code="abcTablaParametros.productoGestion.confirmacion.alta" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionAltaProductoGestion}", "Info", "");
	</script>
</c:if>
<c:if test="${not empty confirmacionModificacionProductoGestion}">
	<spring:message code="abcTablaParametros.productoGestion.confirmacion.modifica" var="confirmacion"/>
	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${confirmacionModificacionProductoGestion}", "Info", "");
	</script>
</c:if>
<jsp:include page="../myFooter.jsp" flush="true"/>