<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="descargaLogs" />
	<jsp:param name="menuSubItem"    value="logs" />
</jsp:include>

<spring:message code="descarga.logs.tituloLogErrores" var="titulo"/>
<spring:message code="descarga.logs.tituloTablaLogErrores" var="tituloTabla"/>
<spring:message code="descarga.logs.tituloColumnaLog" var="columnaLog"/>
<spring:message code="descarga.logs.estatus" var="estatus"/>
<spring:message code="descarga.logs.generado" var="generado"/>
<spring:message code="descarga.logs.noGenerado" var="noGenerado"/>

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
					<th  class="text_izquierda" width="65%">${columnaLog}</th>
					<th colspan="2" class="text_centro">${estatus}</th>
				</tr>
			</thead>
			<tr>
				<td colspan="3" class="special"></td>
			</tr>
			<tbody>
				<c:if test="${not empty logsCruces}">
					<c:set var="cont" value="2"/> 
					<c:forEach items="${logsCruces}" var="registro">
						<c:set var="cont" value="${(cont % 2) + 1}"/>
						<tr class="odd${cont}">
							<td class="text_izquierda">${registro.nombreLog}</td>
							<c:if test="${registro.generado}">
								<td class="text_izquierda">${generado}</td>
								<td><a href="descargaLogError.do?log=${registro.idRegistroEstatus}&nombreLog=${registro.nombreLog}">
										<img height="20" width="20" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/Save-icon.png" alt="Enlace Carga"/>
									</a>
								</td>
							</c:if>
							<c:if test="${not registro.generado}">
								<td class="text_izquierda">${noGenerado}</td>
								<td></td>
							</c:if>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>
<br/>
<c:if test="${not empty nombreLog}">
	<spring:message code="descarga.logs.confirmacionDescarga" var="confirmacion"/>
 	<script type="text/javascript">
		jInfo("", "${confirmacion}" + " " + "${nombreLog}", "Info", "");
	</script>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true"/>
