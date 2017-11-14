<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="cargasManuales" />
	<jsp:param name="menuSubItem"    value="monitorCargas" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="monitorCargas.tituloPagina" var="titulo"/>
<spring:message code="monitorCargas.tituloTabla" var="tituloTabla"/>
<spring:message code="monitorCargas.mensajeNombreInsumo" var="nombreInsumo"/>
<spring:message code="monitorCargas.mensajeEstatus" var="estatus"/>
<spring:message code="monitorCargas.mensajeCargasManuales" var="cargasManuales"/>
<spring:message code="monitorCargas.mensajeInputsOutputsFinales" var="inputsOutputsFinales"/>
<spring:message code="monitorCargas.mensajeInsumosIniciales" var="insumosIniciales"/>
<spring:message code="monitorCargas.mensajeFecha" var="mensajeFecha"/>
<spring:message code="monitorCargas.mensajeDetalleError" var="mensajeDetalleError"/>

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
					<th>${nombreInsumo}</th>
					<th colspan="3">${estatus}</th>
				</tr>
			</thead>
			<tr>
				<td colspan="4" class="special"></td>
			</tr>
			<tbody>
			
				<jsp:include page="listadoCargas.jsp" flush="true"/>
					
				<tr class="odd">
					<td colspan="3" class="text_centro"><span class="tituloInterno">${insumosIniciales}</span></td>
					<td class="text_centro"><span class="tituloInterno">${mensajeFecha}</span></td>
				</tr>
				<c:if test="${not empty estatusCargas.cargasIniciales}">
					<c:set var="cont" value="2"/>
					<c:forEach items="${estatusCargas.cargasIniciales}" var="registro">
					    <c:set var="cont" value="${(cont % 2) + 1}"/>
						<tr class="odd${cont}">
							<td>${registro.nombreInterfaz}</td>
							<td>
								<c:choose>
									<c:when test="${registro.estatus == 'OK'}">
										<img height="20" width="20" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_verde.png" alt="OK"/>
									</c:when>
									<c:when test="${registro.estatus == 'ERROR'}">
										<img height="20" width="20" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_rojo.png" alt="Error"/>
									</c:when>
									<c:when test="${registro.estatus == 'NO CARGADO'}">
										<img height="20" width="20" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_amarillo.png" alt="No Cargado"/>
									</c:when>
								</c:choose>
							</td>
							<td>${registro.estatus}</td>
							<td>${registro.fechaAlta}</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="../myFooter.jsp" flush="true"/>