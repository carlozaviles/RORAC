<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags"    prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<spring:message code="general.ayuda"               var="ayuda"/>
		<spring:message code="general.nombreAplicacion"    var="title"/>
		<spring:message code="general.usuario"             var="user"/>
		<spring:message code="general.ultimoAcceso"        var="fechaUltimoAcceso"/>
		<spring:message code="general.clave"               var="password"/>
		<spring:message code="general.idioma"              var="languageSelect"/>
		<spring:message code="general.acceso"              var="acceso"/>
		<spring:message code="general.ingles"              var="languageSpanish"/>
		<spring:message code="general.español"             var="languageEnglish"/>
		<spring:message code="general.salir"               var="salir"/>
		<spring:message code="general.labelLinkHeader1"    var="labelLinkHeader1"/>
		<spring:message code="general.labelLinkHeader2"    var="labelLinkHeader2"/>
		<spring:message code="general.labelLinkHeader3"    var="labelLinkHeader3"/>
		<spring:message code="general.labelLinkHeader4"    var="labelLinkHeader4"/>
		<spring:message code="general.labelLinkHeader5"    var="labelLinkHeader5"/>
		<spring:message code="general.alerts.alert"    	   var="alertsAlert"/>
		<spring:message code="general.alerts.error"    	   var="alertsError"/>
		<spring:message code="general.alerts.info"    	   var="alertsInfo"/>
		<spring:message code="general.alerts.ayuda"    	   var="alertsAyuda"/>
		<spring:message code="general.alerts.confirmar"    var="alertsConfirmar"/>
		<spring:message code="general.alerts.aviso"    	   var="alertsAviso"/>
		<spring:message code="general.mensaje"			   var="mensajes"/>
		<spring:message code="general.separador.fecha"	   var="fechaSeparador"/>

		<title>${title}</title>
		<script type="text/javascript">
			var jqueryAlerta 		= '${alertsAlert}';
			var jqueryError 		= '${alertsError}';
			var jqueryInfo 			= '${alertsInfo}';
			var jqueryAyuda 		= '${alertsAyuda}';
			var jqueryConfirmar 	= '${alertsConfirmar}';
			var jqueryAviso 		= '${alertsAviso}';
			var linkAyuda    		= '';
		</script>
		<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/calendar/calendar.css" rel="stylesheet" type="text/css" />
		<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/dialogBox/jquery.alerts.css" rel="stylesheet" type="text/css"/>
		
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/global.js" type="text/javascript"></script>
		
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery-1.2.6.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery.ui.draggable.js" type="text/javascript"></script>		
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery.alerts.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery.bgiframe.js" type="text/javascript"></script>
		
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/calendar/calendar.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/calendar/calendarSetup.js" type="text/javascript" ></script>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/calendar/calendarES.js" type="text/javascript" ></script>
		
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/menu/dynamicMenu.js" type="text/javascript"></script>
		
		<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/menu/estilos.css"            rel="stylesheet" type="text/css">
		<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/menu/elementos_interfaz.css" rel="stylesheet" type="text/css">
		
	</head>

	<input id="contextPath" type="hidden" value="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}" />
	<div id="allContent">
		<div id="header">
			
			
			<c:if test="${not empty LyFBean.logoCorporativo}">
  				<a href="${LyFBean.linkLogoCorporativo}" target="_top" title="${LyFBean.linkLogoCorporativo}">
  					<img id="logoHeader" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/${LyFBean.logoCorporativo}" alt="Santander">
  				</a>
 			</c:if>
 			
  			<c:if test="${not empty LyFBean.logoAplicacion}">
  				<a href="${LyFBean.linkLogoAplicacion}">
  					<img id="logoApplication" width="107" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/${LyFBean.logoAplicacion}" alt="${ArchitechSession.nombreAplicacion}">
  				</a>
  			</c:if>

			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			
			<br>
			
			<div id="globalMenu">
				
				<c:if test="${not empty LyFBean.linkHeader5}">
					<a href="${LyFBean.linkHeader5}" class="linkGlobal" target="_blank" title="Link hacia ${labelLinkHeader5}">${labelLinkHeader5}</a>
				</c:if>
				<c:if test="${not empty LyFBean.linkHeader4}">
					<a href="${LyFBean.linkHeader4}" class="linkGlobal" target="_blank" title="Link hacia ${labelLinkHeader4}">${labelLinkHeader4}</a>
				</c:if>
				<c:if test="${not empty LyFBean.linkHeader3}">
					<a href="${LyFBean.linkHeader3}" class="linkGlobal" target="_blank" title="Link hacia ${labelLinkHeader3}">${labelLinkHeader3}</a>
				</c:if>
				<c:if test="${not empty LyFBean.linkHeader2}">
					<a href="${LyFBean.linkHeader2}" class="linkGlobal" target="_blank" title="Link hacia ${labelLinkHeader2}">${labelLinkHeader2}</a>
				</c:if>
				<c:if test="${not empty LyFBean.linkHeader1}">
					<a href="${LyFBean.linkHeader1}" class="linkGlobal" target="_blank" title="Link hacia ${labelLinkHeader1}">${labelLinkHeader1}</a>
				</c:if>
				
				<a href="${LyFBean.linkSalirApp}" class="button" id="buttonSalir" target="_top">${salir}</a>
				
			</div>
			<br><br>
			
  			<c:if test="${not empty LyFBean.logoNombreApp}">
  				<a href="${LyFBean.linkLogoNombreApp}">
  					<img id="nameApplication" width="139" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/menu/${LyFBean.logoNombreApp}" alt="${ArchitechSession.nombreAplicacion}">
  				</a>
  			</c:if>
  			
		</div>
		
		<div id="top03">
		
			<div class="left-container2">
				<c:if test="${not empty ArchitechSession.nombre}">
					<b>${user}:&nbsp;</b>${ArchitechSession.nombre}<br>
				</c:if> 
				<c:if test="${not empty ArchitechSession.ultimoAcceso}">
					<b>${fechaUltimoAcceso}:&nbsp;</b>${ArchitechSession.ultimoAcceso}<br>
				</c:if>
			</div>

			<div class="right-container2">
				(${ArchitechSession.usuario})<BR>
        		${LyFBean.getFecha(fechaSeparador)} - ${LyFBean.hora}
        		<BR>
				<!--<a id="helpLink" href="" target="_new" onclick="javascript:goHelp();">
					<img id="messageHelp" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/ayuda.jpg">
				</a>-->
			</div>
			
			<c:if test="${LyFBean.usaMensajes=='1'}">
			<div class="message-container">
				<a id="messageLink" href="${LyFBean.linkAvisos}" target="_parent">
  					<img id="messageImage" src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/img/email.gif">
  				</a><span>${LyFBean.numAvisos}&nbsp;${mensajes}</span>
			</div>
			</c:if>

		</div>
				
		
