<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="../private/myHeader.jsp" flush="true"/>
		
		<spring:message code="general.sesionExistente" var="user"/>
		<center>El usuario ${user} ya cuenta con una sesión iniciada</center>
		
<jsp:include page="../private/myFooter.jsp" flush="true"/>