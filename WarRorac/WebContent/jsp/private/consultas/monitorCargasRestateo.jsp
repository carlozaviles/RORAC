<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="cargasManuales" />
	<jsp:param name="menuSubItem"    value="monitorCargas" />	
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="monitorCargasRestateo.tituloPagina" var="titulo"/>
<spring:message code="monitorCargasRestateo.tituloTabla" var="tituloTabla"/>
<spring:message code="monitorCargasRestateo.mensajeNombreInsumo" var="nombreInsumo"/>
<spring:message code="monitorCargasRestateo.mensajeEstatus" var="estatus"/>
<spring:message code="monitorCargasRestateo.mensajeRestCliente" var="restCliente"/>
<spring:message code="monitorCargasRestateo.mensajeRestActivo" var="restActivo"/>
<spring:message code="monitorCargasRestateo.mensajeRestPasivo" var="restPasivo"/>
<spring:message code="monitorCargasRestateo.mensajeRestComisiones" var="restComisiones"/>
<spring:message code="monitorCargasRestateo.mensajeRestFondos" var="restFondos"/>
<spring:message code="monitorCargasRestateo.mensajeRestContingentes" var="restContingentes"/>
<spring:message code="monitorCargasRestateo.mensajeDetalleError" var="mensajeDetalleError"/>

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
			
				<c:set var="posicion" value="1"/>
				<c:forEach items="${insumos}" var="listaInsumos">
					<c:choose>
						<c:when test="${posicion == 1}">
							<tr class="odd"><td colspan="4" class="text_centro "><span class="tituloInterno">${restCliente}</span><td></tr>
						</c:when>
						<c:when test="${posicion == 2}">
							<tr class="odd"><td colspan="4" class="text_centro "><span class="tituloInterno">${restActivo}</span><td></tr>
						</c:when>
						<c:when test="${posicion == 3}">
							<tr class="odd">
								<td colspan="4" class="text_centro"><span class="tituloInterno">${restPasivo}</span></td>
							</tr>
						</c:when>
						<c:when test="${posicion == 4}">
							<tr class="odd">
								<td colspan="4" class="text_centro"><span class="tituloInterno">${restContingentes}</span></td>
							</tr>
						</c:when>
						<c:when test="${posicion == 5}">
							<tr class="odd">
								<td colspan="4" class="text_centro"><span class="tituloInterno">${restFondos}</span></td>
							</tr>
						</c:when>
						<c:when test="${posicion == 6}">
							<tr class="odd">
								<td colspan="4" class="text_centro"><span class="tituloInterno">${restComisiones}</span></td>
							</tr>
						</c:when>
					</c:choose>			
					<c:if test="${not empty listaInsumos}">
						<c:set var="cont" value="2"/>
						<c:forEach items="${listaInsumos}" var="registro">
							<c:set var="cont" value="${(cont % 2) + 1}"/>
							<tr class="odd${cont}">
								<td>${registro.nombreInterfaz}_${registro.fechaAlta}</td>
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
								<td>
									<c:if test="${registro.estatus == 'ERROR'}">
										<a href="javascript:muestraDetalleError('${registro.detalleError}')">${mensajeDetalleError}</a>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<tr>
						<td colspan="4" class="special"></td>
					</tr>
					<c:set var="posicion" value="${posicion + 1}"/>
				</c:forEach>
					
			</tbody>
		</table>
	</div>
</div>

<jsp:include page="../myFooter.jsp" flush="true"/>