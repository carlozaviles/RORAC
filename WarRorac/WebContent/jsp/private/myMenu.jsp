	<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ page import="mx.isban.agave.configuracion.Configuracion"%>

	<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/global.js"           type="text/javascript"></script>
	<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/menu/dynamicMenu.js" type="text/javascript"></script>
	<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/menu/estilos.css"            rel="stylesheet" type="text/css">
	<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/menu/elementos_interfaz.css" rel="stylesheet" type="text/css">

	<spring:message code="menu.menuItemOne"    						var="principal"/>
	
	<spring:message code="horarios.menuHorarios"  					var="menuHorarios"/>
	<spring:message code="horarios.subMenuAdminHorarios" 			var="subMenuAdminHorarios"/>
	<spring:message code="horarios.subMenuConsulHorarios"			var="subMenuConsulHorarios"/>
	
	<spring:message code="sesiones.menuSesiones"  					var="menuSesiones"/>
	<spring:message code="sesiones.subMenuAdminSesiones" 			var="subMenuAdminSesiones"/>

	<spring:message code="menu.menuConfig"    						var="menuConfig"/>
	<spring:message code="menu.submenuConfig_ConsTemplates"  		var="submenuConfig_ConsTemplates"/>
	<spring:message code="menu.submenuConfig_ConsAplicacion"		var="submenuConfig_ConsAplicacion"/>
	<spring:message code="menu.submenuConfig_ConsHost"  			var="submenuConfig_ConsHost"/>
	<spring:message code="menu.submenuConfig_AsigHost"  			var="submenuConfig_AsigHost"/>
	<spring:message code="menu.submenuConfig_AsigTemplates"  		var="submenuConfig_AsigTemplates"/>
	<spring:message code="menu.submenuConfig_ModifParams"  			var="submenuConfig_ModifParams"/>
	<spring:message code="menu.submenuConfig_ConsAmbitos"  			var="submenuConfig_ConsAmbitos"/>
	
	<spring:message code="menu.menuLogging"    						var="menuLogging"/>
	<spring:message code="menu.subMenuLogging_nivelTrace"  			var="subMenuLogging_nivelTrace"/>
	<spring:message code="menu.subMenuLogging_activacionFiltro"		var="subMenuLogging_activacionFiltro"/>
	<spring:message code="menu.subMenuLogging_consultaLogs"			var="subMenuLogging_consultaLogs"/>
	
	<spring:message code="menu.subMenuModAyudas"  		   			var="subMenuModAyudas"/>
	<spring:message code="menu.subMenuModAyudasAppSer"     			var="subMenuModAyudasAppSer"/>
	<spring:message code="menu.subMenuModAyudasImgDoc"   			var="subMenuModAyudasImgDoc"/>
	<spring:message code="menu.subMenuModAyudasAyuTem"   			var="subMenuModAyudasAyuTem"/>
	
	<spring:message code="menu.menuItemAdmonCmpSMSEmail" 			var="cmpSMSEmail"/>
	<spring:message code="menu.ItemConsultaAplicacion" 	 			var="consultaAplicacion"/>
	
	<spring:message code="monitoreo.menu.menuMonitoreo"  				var="menuMonitoreo"/>
	<spring:message code="monitoreo.menu.subMenuConsultaFiltros" 		var="subMenuConsultaFiltros"/>
	<spring:message code="monitoreo.menu.subMenuConsultaNotificaciones" var="subMenuConsultaNotificacion"/>
	<spring:message code="monitoreo.menu.subMenuConsultaSlaNCumplidos" 	var="subMenuConsultaSlaNCumplidos"/>
	<spring:message code="monitoreo.menu.subMenuTopTPromedios" 			var="subMenuTopTPromedios"/>
	<spring:message code="monitoreo.menu.subMenuConsultaSLAS" 			var="subMenuConsultaSLAS"/>
	<spring:message code="monitoreo.menu.subMenuTableroControl" 		var="subMenuTableroControl"/>
	<spring:message code="monitoreo.menu.subMenuDisponibilidad" 		var="subMenuDisponibilidad"/>
	<spring:message code="monitoreo.menu.subMenuConsultaAlarma" 		var="subMenuConsultaAlarma"/>
	<spring:message code="monitoreo.menu.subMenuConsTrackingOpers" 		var="subMenuConsTrackingOpers"/>
	<spring:message code="monitoreo.menu.submenuEstadisticasFlujo" 		var="submenuEstadisticasFlujo"/>
	<spring:message code="monitoreo.menu.submenuTiempoPromFlujo" 		var="submenuTiempoPromFlujo"/>
	<spring:message code="monitoreo.menu.submenuGestionFlujos" 			var="submenuGestionFlujos"/>
	<spring:message code="monitoreo.menu.submenuConsEjecProceso" 		var="submenuConsEjecProceso"/>
	<spring:message code="monitoreo.menu.submenuConsolidadoOper" 		var="submenuConsolidadoOper"/>
	<spring:message code="monitoreo.menu.submenuTopEstadisticas" 		var="submenuTopEstadisticas"/>
	<spring:message code="monitoreo.menu.submenuGestionAlias" 			var="submenuGestionAlias"/>
	<spring:message code="monitoreo.menu.submenuConsRendimiento" 		var="submenuConsRendimiento"/>
	<spring:message code="monitoreo.menu.submenuConfigEntorno" 			var="submenuConfigEntorno"/>
	<spring:message code="monitoreo.menu.submenuEntorno" 				var="submenuEntorno"/>

	<spring:message code="menu.menuPrincipalCargas" 					var="menuPrincipalCargas"/>
	<spring:message code="menu.submenuCargaInputs" 						var="submenuCargaInputs"/>
	<spring:message code="menu.submenuMonitorCargas" 					var="submenuMonitorCargas"/>
	<spring:message code="menu.submenuMonitorCargasRestateo" 			var="submenuMonitorCargasRestateo"/>
	<spring:message code="menu.menuPrincipalLogs" 						var="menuPrincipalLogs"/>
	<spring:message code="menu.submenuLogs" 							var="submenuLogs"/>
	<spring:message code="menu.submenuValidaciones" 					var="submenuValidaciones"/>
	<spring:message code="menu.submenuValidacionesRestateo" 			var="submenuValidacionesRestateo"/>
	<spring:message code="menu.menuPrincipalParametros" 				var="menuPrincipalParametros"/>
	<spring:message code="menu.submenuConsultaTablas" 					var="submenuConsultaTablas"/>
	<spring:message code="menu.submenuAltaTablas" 						var="submenuAltaTablas"/>
	<spring:message code="menu.menuPrincipalInterfacesFinales" 			var="menuPrincipalInterfacesFinales"/>
	<spring:message code="menu.submenuEdInterfacesFinales" 				var="submenuEdInterfacesFinales"/>
	<spring:message code="menu.submenuLanzadorMotor" 					var="submenuLanzadorMotor"/>
	<spring:message code="menu.menuPrincipalAprovisionamiento" 			var="menuPrincipalAprovisionamiento"/>
	<spring:message code="menu.submenuLanzadorAprovisionamiento" 		var="submenuLanzadorAprovisionamiento"/>
	<spring:message code="menu.menuPrincipalReproceso" 					var="menuPrincipalReproceso"/>
	<spring:message code="menu.submenuLanzadorReproceso" 				var="submenuLanzadorReproceso"/>
	<spring:message code="menu.menuPrincipalConsultas" 					var="menuPrincipalConsultas"/>
	<spring:message code="menu.submenuConsultaPorContrato" 				var="submenuConsultaPorContrato"/>
	<spring:message code="menu.submenuValidacionesMinimas" 				var="submenuValidacionesMinimas"/>

	<spring:message code="menu.menuPrincipalAdministracion" 				var="menuPrincipalAdministracion"/>
	<spring:message code="menu.submenuGrupo" 						var="submenuGrupo"/>
	<spring:message code="menu.submenuUsuarios" 						var="submenuUsuarios"/>
	<spring:message code="menu.submenuPantallas" 						var="submenuPantallas"/>
	<spring:message code="menu.submenuModulo" 						var="submenuModulo"/>
	
	<!-- <body onload="initialize('${param.menuItem}', '${param.menuSubitem}'); addMenuItem('eight','Mi opcion dinamica','','', 'true', 'true'); disabledMenuItem('three'); disabledMenuItem('fiveDotTwo');"> -->
<body onload="initialize('${param.menuItem}', '${param.menuSubitem}'); enabledMenuItems('${LyFBean.idsMenuPerfil}', '${LyFBean.tipoMenu}', '${LyFBean.tipoIdsMenu}'); estableceAyuda('${param.helpPage}')">
	<div id="top04">
		<c:if test="${LyFBean.menuHabilitado}">
			<div class="frameMenuContainer">
				<ul id="mainMenu">
				<!-- Menu Principal RORAC -->
					<li id="principal" class="startMenuGroup"> <a href="javascript:selectMenuItem('principal')"><span>RORAC</span></a></li>
				<c:forEach var="modulo" items="${sessionScope.modulosPermitidos}">
					<!-- Menu RORAC -->
					<c:if test = "${modulo.nombreModulo == 'CARGAS MANUALES'}">
					<li id="cargasManuales" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('cargasManuales')"><span>${menuPrincipalCargas}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'CARGA DE INSUMOS'}">
							<li id="cargaInsumos">      		<a href="../inputs/cargasManuales.do">    &gt;<span class="subMenuText">${submenuCargaInputs}</span></a></li>
							</c:if>
							<c:if test = "${pantalla.nombrePantalla == 'MONITOR DE CARGAS'}">								
							<li id="monitorCargas">      		<a href="../consultas/monitorCargas.do">    &gt;<span class="subMenuText">${submenuMonitorCargas}</span></a></li>
							</c:if>
							<c:if test = "${pantalla.nombrePantalla == 'MONITOR DE CARGAS RESTATEO'}">								
							<li id="monitorCargasRestateo">		<a href="../consultas/monitorCargasRestateo.do">    &gt;<span class="subMenuText">${submenuMonitorCargasRestateo}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li> 
					</c:if>
					<c:if test = "${modulo.nombreModulo == 'DESCARGA DE LOGS DE ERROR'}">
					<li id="descargaLogs" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('descargaLogs')"><span>${menuPrincipalLogs}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'LOGS'}">
							<li id="logs">      		<a href="../lanzadores/logsErrores.do">    &gt;<span class="subMenuText">${submenuLogs}</span></a></li>
							</c:if>
							<c:if test = "${pantalla.nombrePantalla == 'VALIDACIONES'}">	
							<li id="validaciones">      <a href="../lanzadores/logsValidaciones.do">    &gt;<span class="subMenuText">${submenuValidaciones}</span></a></li>
							</c:if>
							<c:if test = "${pantalla.nombrePantalla == 'VALIDACIONES RESTATEO'}">	
							<li id="validacionesRestateo">      <a href="../lanzadores/logsValidacionesRestateo.do">    &gt;<span class="subMenuText">${submenuValidacionesRestateo}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					<c:if test = "${modulo.nombreModulo == 'TABLAS DE PARAMETROS'}">
					<li id="tablasParametros" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('tablasParametros')"><span>${menuPrincipalParametros}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'CONSULTA'}">
							<li id="consultaTablas">    <a href="../consultas/consultaTablasParametros.do">    &gt;<span class="subMenuText">${submenuConsultaTablas}</span></a></li>
							</c:if>
							<c:if test = "${pantalla.nombrePantalla == 'ALTA'}">	
							<li id="altaTablas">        <a href="../consultas/altaTablasParametros.do">    &gt;<span class="subMenuText">${submenuAltaTablas}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					<c:if test = "${modulo.nombreModulo == 'EDICION,CARGA Y EJECUCION DE INPUTS Y OUTPUTS FINALES'}">
					<li id="interfacesFinales" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('interfacesFinales')"><span>${menuPrincipalInterfacesFinales}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'EDICION DE INPUTS Y OUTPUTS FINALES'}">
							<li id="edicionInterfacesFinales">    <a href="../lanzadores/cargaIOFinales.do">    &gt;<span class="subMenuText">${submenuEdInterfacesFinales}</span></a></li>
							</c:if>
							<c:if test = "${pantalla.nombrePantalla == 'LANZADOR DE MOTOR RORAC'}">	
							<li id="lanzadorMotor">               <a href="../lanzadores/menuMotorRorac.do">    &gt;<span class="subMenuText">${submenuLanzadorMotor}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					<c:if test = "${modulo.nombreModulo == 'APROVISIONAMIENTO HISTORICO'}">
					<li id="aprovisionamientoHistorico" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('aprovisionamientoHistorico')"><span>${menuPrincipalAprovisionamiento}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'LANZADOR APROVISIONAMIENTO HISTORICO'}">
							<li id="lanzadorAprovisionamiento">    <a href="../lanzadores/muestraMenuAprovisionamiento.do">    &gt;<span class="subMenuText">${submenuLanzadorAprovisionamiento}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					<c:if test = "${modulo.nombreModulo == 'REPROCESO INPUTS FINALES'}">
					<li id="reprocesoInputs" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('reprocesoInputs')"><span>${menuPrincipalReproceso}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'LANZADOR DE REPROCESOS'}">
							<li id="lanzadorReproceso">    <a href="../lanzadores/muestraMenuReproceso.do">    &gt;<span class="subMenuText">${submenuLanzadorReproceso}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					<c:if test = "${modulo.nombreModulo == 'CONSULTA DE INPUTS Y OUTPUTS'}">
					<li id="consultas" class="withSubMenus startMenuGroup"><a href="javascript:selectMenuItem('consultas')"><span>${menuPrincipalConsultas}</span></a>
						<ul>
							<c:forEach var="pantalla" items="${modulo.pantallas}">
							<c:if test = "${pantalla.nombrePantalla == 'INPUTS Y OUTPUTS POR CONTRATO'}">
							<li id="consultaPorContrato">    <a href="../consultas/menuConsultaIOFinales.do">    &gt;<span class="subMenuText">${submenuConsultaPorContrato}</span></a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
				</c:forEach>
					
				</ul>
			
				<div id="menuFooter">
					<div></div>
				</div>
	
			</div>
		</c:if>
	</div>
<!-- </div>  -->
<!-- </body> -->
<!-- </html> -->
<div id="content_container">
