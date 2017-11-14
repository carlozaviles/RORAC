<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:message code="monitorCargas.mensajeCargasManuales" var="cargasManuales"/>
<spring:message code="monitorCargas.mensajeInputsOutputsFinales" var="inputsOutputsFinales"/>
<spring:message code="monitorCargas.mensajeDetalleError" var="mensajeDetalleError"/>

<c:set var="posicion" value="1"/>
<c:forEach items="${insumos}" var="listaInsumos">
	<c:choose>
		<c:when test="${posicion == 1}">
			<tr class="odd"><td colspan="4" class="text_centro "><span class="tituloInterno">${cargasManuales}</span><td></tr>
		</c:when>
		<c:when test="${posicion == 2}">
			<tr class="odd">
				<td colspan="4" class="text_centro"><span class="tituloInterno">${inputsOutputsFinales}</span></td>
			</tr>
		</c:when>
	</c:choose>			
	<c:if test="${not empty listaInsumos}">
		<c:set var="cont" value="2"/>
		<c:forEach items="${listaInsumos}" var="registro">
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