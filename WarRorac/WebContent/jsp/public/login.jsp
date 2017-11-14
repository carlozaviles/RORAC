<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"       prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"     content="text/html; charset=ISO-8859-1">
		<meta http-equiv=" content-language" content="es-MX">

		<spring:message code="general.acceso"              var="acceso"/>
		<spring:message code="general.nombreAplicacion"    var="aplicacion"/>
		<spring:message code="general.usuario"             var="usuario"/>
		<spring:message code="general.clave"               var="clave"/>
		<spring:message code="general.seleccioneIdioma"    var="language"/>
		<spring:message code="general.entrar"              var="login"/>
		<spring:message code="general.español"             var="languageSpanish"/>
		<spring:message code="general.ingles"              var="languageEnglish"/>
		<spring:message code="general.selecionOpcion"      var="opcionLan"/>
		<spring:message code="codeError.${codError}"       var="descError" text="EJECUCIONEXITOSA"/>
		
		<title>${acceso}&nbsp;${aplicacion}</title>
		
		<link href="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/css/dialogBox/jquery.alerts.css" rel="stylesheet" type="text/css"/>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/global.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery-1.2.6.js" type="text/javascript"></script>
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery.ui.draggable.js" type="text/javascript"></script>		
		<script src="${pageContext.servletContext.contextPath}/lf/${LyFBean.lookAndFeel}/js/dialogBox/jquery.alerts.js" type="text/javascript"></script>
				
		<script>
		
			function cambiarIdioma(){
    			//window.location = "inicio.home";
    			document.forms["login"].action = "login.go";
    			document.forms["login"].submit();
			}
		</script>
		
		
	</head>
	<body>
	
	
		<form name="login" action="../principal/inicio.do" method="post">
			<table align="center">
				<tr>
					<td colspan="2" align="center">${acceso}&nbsp;${aplicacion}</td>
				</tr>
				<tr>
					<td>${usuario}</td>
					<td><input type="text" name="user"  size="20" value="${user}"></td>
				</tr>
				<tr>
					<td>${clave}</td>
					<td><input type="password" name="password" size="20" value="${password}"></td>
				</tr>
				<tr>
					<td>${language}</td>
					<td>
						<select name="siteLanguage" onchange="cambiarIdioma();">
							<c:choose>
								<c:when test="${fn:containsIgnoreCase('es' , siteLanguage)}">
									<option value="es" selected="selected">--${opcionLan}</option>
									<option value="en_US" >${languageEnglish}</option>
									<option value="es_MX">${languageSpanish}</option>
								</c:when>
								<c:when test="${fn:containsIgnoreCase('en_US' , siteLanguage)}">
									<option value="es" >--${opcionLan}</option>
									<option value="en_US" selected="selected">${languageEnglish}</option>
									<option value="es_MX" >${languageSpanish}</option>
								</c:when>
								<c:when test="${fn:containsIgnoreCase('es_MX' , siteLanguage)}">
									<option value="es" >--${opcionLan}</option>
									<option value="en_US" >${languageEnglish}</option>
									<option value="es_MX" selected="selected">${languageSpanish}</option>
								</c:when>
							</c:choose>
							
						</select>
						
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit" name="login" value="${login}" size="20"></td>
				</tr>
			
			</table>	
			    
		</form>
	</body>
	
	<c:if test="${!fn:containsIgnoreCase('EJECUCIONEXITOSA',descError)}">
		<script>
			jError('${descError}','${codError}');
		</script>
	</c:if>
	
</html>