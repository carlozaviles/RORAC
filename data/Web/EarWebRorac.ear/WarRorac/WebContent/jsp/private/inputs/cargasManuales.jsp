<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../myHeader.jsp" flush="true"/>
<jsp:include page="../myMenu.jsp" flush="true">
	<jsp:param name="menuItem"    value="cargasManuales" />
	<jsp:param name="menuSubItem"    value="cargaInsumos" />
</jsp:include>

<script src="${pageContext.servletContext.contextPath}/recursos/javascript/principal/validacionesJsRorac.js" type="text/javascript"></script>

<spring:message code="inputs.tituloPagina"             var="titulo"/>
<spring:message code="inputs.tituloTabla"              var="tituloTabla"/>
<spring:message code="inputs.instruccionCarga"         var="instruccion"/>
<spring:message code="inputs.etiquetaSeleccionArchivo" var="examinar"/>
<spring:message code="inputs.etiquetaCargaArchivo"     var="cargar"/>


<div class="pageTitleContainer">
   <span class="pageTitle">${titulo}</span>
</div>
<br/>
<div class="frameFormularioB">
	<div class="contentFormularioB">
	<div class="titleFormularioB">${tituloTabla }</div>
		<form action="cargarInput.do" name="cargaManualForm" method="POST" enctype="multipart/form-data">
			<table>
				<tbody>
				<tr>
					<th colspan="4" class="text_izquierda">
						${instruccion}
					</th>
				</tr>
		         <tr>
		             <td width="154" class="odd"><input type="file" name="file"/></td>
		         </tr>
		         <tr>
		             <td colspan="4" align="center">
		             	<a href="javascript:validaCargaManualForm()" class="boton">${cargar}</a>
		             </td>
		         </tr>
		         </tbody>
		     </table>
		</form>
	</div>
</div>
<c:if test="${not empty inputCargado}">
 	<spring:message code="inputs.confirmacionCarga" var="confirmacion"/>
 	<script type="text/javascript">
		jInfo("", "${confirmacion}", "Info", "");
	</script>  	
</c:if>
<c:if test="${not empty codigoError}">
 	<spring:message code="error.${codigoError}.descripcionError" var="mensajeError"/>
 	<script type="text/javascript">
		jError("", "${mensajeError}", "Error", "");
	</script>
</c:if>

<jsp:include page="../myFooter.jsp" flush="true"/>