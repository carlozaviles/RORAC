<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="tablasParametros" />
</jsp:include>

<spring:message code="abcTablaParametros.principal.titulo.${operacion}" var="titulo"/>
<spring:message code="abcTablaParametros.principal.tituloTabla.${operacion}" var="tituloTabla"/>
<spring:message code="abcTablaParametros.principal.nombreFiltrosExtraccion" var="filtrosExtraccion"/>
<spring:message code="abcTablaParametros.principal.nombreADNRetail" var="adnRetail"/>
<spring:message code="abcTablaParametros.principal.nombreFlagNeteo" var="flagNeteo"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<div class="frameTablaEstandar">
    <div class="titleTablaEstandar">${tituloTabla}</div>
	<div class="contentTablaEstandar">
		<table class="contenido">
			<tr><td class="special"></td></tr>
			<tbody>
				<tr class="odd1">
					<td class="text_centro">
						<c:if test="${operacion == 'alta'}">
							<a href="altaParametros.do?param=fe">${filtrosExtraccion}</a>
						</c:if>
						<c:if test="${operacion == 'consulta'}">
							<a href="consultarFiltrosExtraccion.do">${filtrosExtraccion}</a>
						</c:if>
					</td>	
				</tr>
				<tr class="odd2">
					<td class="text_centro">
						<c:if test="${operacion == 'alta'}">
							<a href="altaParametros.do?param=ar">${adnRetail}</a>
						</c:if>
						<c:if test="${operacion == 'consulta'}">
							<a href="consultaAdnRetail.do">${adnRetail}</a>
						</c:if>
					</td>
				</tr>
				<c:if test="${operacion == 'consulta'}">
				<tr class="odd1">
					<td class="text_centro">
							<a href="consultaFlagNeteo.do">${flagNeteo}</a>
					</td>
				</tr>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<jsp:include page="../myFooter.jsp" flush="true"/>