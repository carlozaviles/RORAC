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

<spring:message code="abcTablaParametros.flagNeteo.titulo.consulta" var="titulo"/>
<spring:message code="abcTablaParametros.flagNeteo.tituloTabla.consulta" var="tituloTabla"/>
<spring:message code="abcTablaParametros.flagNeteo.campo" var="flagNeteo"/>

<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<br/>
<div class="frameTablaEstandar">
	<div class="titleTablaEstandar">
		${tituloTabla}
	</div>
	<div class="contentTablaEstandar">
			<table class="contenido">
				<thead>
					<tr>
						<th width="50%">${flagNeteo}</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty listaFlagNeteo}">
						<c:set var="cont" value="2"/>	
						<c:set var="urlConsulta" value="muestraFlagNeteo.do?id="/>
						<c:forEach items="${listaFlagNeteo}" var="registro">
							<c:set var="cont" value="${(cont % 2) + 1}"/>
							<tr class="odd${cont}">
								<td><a href="${urlConsulta}${registro.idRegistro}">${registro.valor}</a></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
	</div>
</div>

<jsp:include page="../myFooter.jsp" flush="true"/>