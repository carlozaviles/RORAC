<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<jsp:include page="../myHeader.jsp" flush="true" />
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem" value="interfacesFinales" />
	<jsp:param name="menuSubItem" value="lanzadorMotor" />
</jsp:include>

<script
	src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js"
	type="text/javascript"></script>

<spring:message code="ejecucionMotor.tituloPagina" var="titulo" />
<spring:message code="ejecucionMotor.tituloTabla" var="tituloTabla" />
<spring:message code="ejecucionMotor.instruccion" var="instruccion" />
<spring:message code="ejecucionMotor.validaInsumos" var="validaInsumos" />
<spring:message code="ejecucionMotor.accion" var="accion" />
<spring:message code="ejecucionMotor.seleccioneFinalidad"
	var="seleccioneFinalidad" />
<spring:message code="ejecucionMotor.finalidadUno" var="finalidadUno" />
<spring:message code="ejecucionMotor.finalidadDos" var="finalidadDos" />
<spring:message code="ejecucionMotor.mensaje.usuario" var="pregunta" />
<spring:message code="ejecucionMotor.seleccionePeriodo"
	var="seleccionePeriodo" />
<spring:message code="ejecucionMotor.introduceDivisa"
	var="introduceDivisa" />
<spring:message code="ejecucionMotor.actualizaBoton" var="actualizaBoton" />
<spring:message code="ejecucionMotor.introduceN" var="introduceN" />
<spring:message code="ejecucionMotor.mes" var="introduceMes" />
<spring:message code="ejecucionMotor.anio" var="introduceAnio" />
<spring:message code="ejecucionMotor.insumosRorac" var="insumosRorac" />
<spring:message code="ejecucionMotor.insumosCorporativos"
	var="insumosCorporativos" />
<spring:message code="ejecucionMotor.statusMotorRorac"
	var="statusMotorRorac" />
<spring:message code="general.mes.enero" var="enero" />
<spring:message code="general.mes.febrero" var="febrero" />
<spring:message code="general.mes.marzo" var="marzo" />
<spring:message code="general.mes.abril" var="abril" />
<spring:message code="general.mes.mayo" var="mayo" />
<spring:message code="general.mes.junio" var="junio" />
<spring:message code="general.mes.julio" var="julio" />
<spring:message code="general.mes.agosto" var="agosto" />
<spring:message code="general.mes.septiembre" var="septiembre" />
<spring:message code="general.mes.octubre" var="octubre" />
<spring:message code="general.mes.noviembre" var="noviembre" />
<spring:message code="general.mes.diciembre" var="diciembre" />
<spring:message code="general.select.vacio" var="seleccione" />

<script type="text/javascript">
	var finalidad = "Finalidad";
	var campoMes = "${introduceMes}";
	var campoAnio = "${introduceAnio}";
	var campoN = "N";
	var preguntaConfirmacion = '${pregunta}';
</script>

<div class="pageTitleContainer">
	<span class="pageTitle">${titulo}</span>
</div>
<br />
<div class="frameFormularioB">
	<div class="contentFormularioB">
		<div class="titleFormularioB">${tituloTabla}</div>
		<form:form action="ejecutaMotorRorac.do" method="POST"
			commandName="ejecucionMotorForm">
 			<c:set var="contador" value="0" />
			<c:set var="lanzador" value="0" />
			<table>
				<tbody>
					<tr>
						<td class="odd">${seleccioneFinalidad}:</td>
						<td colspan="2"><form:select path="finalidad">
								<form:option value="NONE" label="${seleccione}"/>
								<form:option value="1" label="${finalidadUno}"/>
								<form:option value="2" label="${finalidadDos}"/>
						</form:select></td>
					</tr>
					<tr>
						<td class="odd">${seleccionePeriodo}</td>
						<td>${introduceMes}:</td>
						<td><form:select path="mes">
								<form:option value="NONE" label="${seleccione}" />
								<form:option value="01" label="${enero}" />
								<form:option value="02" label="${febrero }" />
								<form:option value="03" label="${marzo }" />
								<form:option value="04" label="${abril }" />
								<form:option value="05" label="${mayo }" />
								<form:option value="06" label="${junio }" />
								<form:option value="07" label="${julio }" />
								<form:option value="08" label="${agosto }" />
								<form:option value="09" label="${septiembre }" />
								<form:option value="10" label="${octubre }" />
								<form:option value="11" label="${noviembre }" />
								<form:option value="12" label="${diciembre }" />
							</form:select></td>
						<td>${introduceAnio}:</td>
						<td><form:select path="anio">
								<form:options items="${listaAnios}" />
							</form:select></td>
					</tr>
					<tr>
						<td class="odd">${introduceDivisa}:</td>
						<td colspan="2"><input type="text" name="divisaDisabled" disabled="disabled" value="MXN"/>
						<input id="divisa" name="divisa" type="hidden" value="MXN"/></td>
					</tr>
					<tr>
						<td class="odd">${introduceN}:</td>
						<td colspan="2"><input type="text" name="valorN" value="${ejecucionMotorForm.valorN}"/></td>
					</tr>
					<tr>
						<td colspan="5">
							<div>
								<a href="javascript:validaInsumosMotor()" class="boton">${validaInsumos}</a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="frameTablaEstandar">
				<div class="contentTablaEstandar">
				<table class="contenido">
					<tr>
						<td colspan="3" class="special"></td>
					</tr>
					<tbody>					
						<c:set var="posicion" value="1" />
						<c:forEach items="${insumos}" var="listaInsumos">
							<c:choose>
								<c:when test="${posicion == 1}">
									<tr class="odd">
										<td colspan="3" class="text_centro "><span
											class="tituloInterno">${insumosRorac}</span>
										<td>
									</tr>
								</c:when>
								<c:when test="${posicion == 2}">
									<tr class="odd">
										<td colspan="3" class="text_centro"><span
											class="tituloInterno">${insumosCorporativos}</span></td>
									</tr>
								</c:when>
								<c:when test="${posicion == 3}">
									<tr class="odd">
										<td colspan="3" class="text_centro"><span
											class="tituloInterno">${statusMotorRorac}</span></td>
									</tr>
								</c:when>
							</c:choose>
							<c:if test="${not empty listaInsumos}">
								<c:set var="cont" value="2" />
								<c:forEach items="${listaInsumos}" var="registro">
									<c:set var="cont" value="${(cont % 2) + 1}" />
									<tr class="odd${cont}">
										<td>${registro.nombreInterfaz}</td>
										<td><c:choose>
												<c:when test="${registro.estatus == 'OK'}">
													<c:set var="contador" value="${contador + 1}" />
													<img height="20" width="20"
														src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_verde.png"
														alt="OK" />
												</c:when>
												<c:when test="${registro.estatus == 'ERROR'}">
													<img height="20" width="20"
														src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_rojo.png"
														alt="Error" />
												</c:when>
												<c:when test="${registro.estatus == 'NO INICIADO' && not empty registro.operacion}">
													<c:set var="lanzador" value="${lanzador + 1}" />
													<img height="20" width="20"
														src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_amarillo.png"
														alt="No Cargado" />
												</c:when>
												<c:when test="${registro.estatus == 'NO CARGADO' || registro.estatus == 'NO INICIADO' || registro.estatus == 'EN PROCESO'}">
													<img height="20" width="20"
														src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/cuadro_amarillo.png"
														alt="No Cargado" />
												</c:when>
											</c:choose></td>
										<td>${registro.estatus}</td>
									</tr>
								</c:forEach>
							</c:if>
							<tr>
								<td colspan="3" class="special"></td>
							</tr>
							<c:set var="posicion" value="${posicion + 1}" />
						</c:forEach>
					</tbody>
				</table>
					<div align="center">
						<img height="15" width="15" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/rorac/refresh.gif" alt="Actualizar los estatus de los insumos" />
						<a href="javascript:actualizaInsumos()" class="link">${actualizaBoton}</a>
					</div>
					</div>
				</div>
				
				<table>
				<tbody>
					<tr>
						<th colspan="5" class="text_izquierda">${instruccion}</th>
					</tr>
				</tbody>
			</table>
			<div>
			<c:choose>
 				<c:when test="${contador == 14 && lanzador == 1}"> 
					<a href="javascript:confirmaEjecucionMotor()" class="boton">${accion}</a>
 				</c:when> 
 				<c:otherwise>
					<a class="botonDisabled">${accion}</a>
 				</c:otherwise>
			</c:choose>
			</div>
			<input id="tipoEjecucion" name="tipoEjecucion" type="hidden" value="3"/>
		</form:form>
	</div>
</div>

<c:if test="${not empty validacion}">
	<c:if test="${validacion eq 'fail'}">	
		<spring:message code="ejecucionMotor.validacion" var="validacion" />
		<script type="text/javascript">
			jInfo("", "${validacion}", "Info", "");
		</script>
	</c:if>
	<c:if test="${validacion eq 'ok'}">	
		<spring:message code="ejecucionMotor.inicio.validacion" var="inicio" />
		<script type="text/javascript">
			jInfo("", "${inicio}", "Info", "");
		</script>
	</c:if>
</c:if>
<c:if test="${not empty confirmacion}">
	<spring:message code="ejecucionMotor.confirmacion" var="confirmacion" />
	<script type="text/javascript">
		jInfo("", "${confirmacion}", "Info", "");
	</script>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true" />