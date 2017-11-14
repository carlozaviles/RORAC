/**
 * Valida el formulario de cargas manuales,
 */
function validaCargaManualForm(){
	var archivoACargar = document.forms["cargaManualForm"]["file"].value;
	if(!validaCadenaVacia(archivoACargar, 'Archivo')){
		return;
	}
	document.forms["cargaManualForm"].submit();
}

/**
 * Actualizacion de los insumos en el monitor del motor
 */
function actualizaInsumos(){
	var forma = document.forms["ejecucionMotorForm"];
	forma["tipoEjecucion"].value = "3";
	forma.submit();
}

/**
 * Valida los insumos rorac.
 */
function validaInsumosMotor(){
	var forma = document.forms["ejecucionMotorForm"];
	forma["tipoEjecucion"].value = "2";
	if(forma["finalidad"].value === 'NONE'){
		alert("El campo " + finalidad + " debe de ser informado.");
		return;
	}
	if(forma["mes"].value === 'NONE'){
		alert("El campo " + campoMes + " debe de ser informado.");
		return;
	}
	if(forma["anio"].value === 'NONE'){
		alert("El campo " + campoAnio + " debe de ser informado.");
		return;
	}
	if(!(validaCadenaVacia(forma["valorN"].value, campoN) && validaFormatoNumero(forma["valorN"].value, campoN) 
			&& validaValoresCampoNumerico("N", forma["valorN"].value, 0, 100))){
		return;
	}
	forma.submit();
}

/**
 * Muestra mensaje de confirmacion al usuario para registrar la ejecucion del motor RORAC.
 */
function confirmaEjecucionMotor(){
	var confirmacionUsuario = confirm(preguntaConfirmacion);
	if(confirmacionUsuario){
		var forma = document.forms["ejecucionMotorForm"];
		forma["tipoEjecucion"].value = "1";
		forma.submit();
	}
}

/**
 * Funcion para validar los limites inferior y superior de un campo
 * 
 * @param campo el valor del campo a validar
 * @param limiteInferior limite inferior permitido
 * @param limiteSuperior limite superior permitido
 * @returns {Boolean} true si se el valor de campo se encuentra entre limiteInferior y limiteSuperior, false en otro caso
 */
function validaValoresCampoNumerico(campo,valor, limiteInferior, limiteSuperior){
	if(valor >= limiteInferior && valor <= limiteSuperior){
		return true;
	}
	alert("El campo " + campo + " debe ser un valor entre " + limiteInferior + " y " + limiteSuperior);
	return false;
}

/**
 * Muestra el mensaje de confirmacion al usuario para realizar el registro de la operacion de Carga de Inputs/Outputs Finales.
 */
function confirmacionCargaIO(){
	if(!document.forms["cargaIOForm"]["activoActualizar"].checked && 
			!document.forms["cargaIOForm"]["activoEliminar"].checked &&
			!document.forms["cargaIOForm"]["pasivoActualizar"].checked &&
			!document.forms["cargaIOForm"]["pasivoEliminar"].checked){
		alert("Se debe seleccionar al menos una operaci\u00f3n.");
		return;
	}
	var confirmacionUsuario = confirm("Desea enviar la solicitud de proceso");
	if(confirmacionUsuario){
		document.forms["cargaIOForm"].submit();
	}
}

/**
 * Llama funciones de validacion sobre un campo en especifico.
 */
function esCampoNoValido(campo, nombreCampo, longitud, esNumerico){
	if(esNumerico){
		return !(validaCadenaVacia(campo, nombreCampo) && validaLongitudMaxima(campo, longitud, nombreCampo) 
				&& validaFormatoNumero(campo, nombreCampo));
	}else{
		return !(validaCadenaVacia(campo, nombreCampo) && validaLongitudMaxima(campo, longitud, nombreCampo));
	}
}

/**
 * Valida que el campo tenga un valor de 0 o 1
 */
function esCampoBanderaNoValido(campo, nombreCampo){
	var patron = new RegExp("^[0-1]+$");
	if(!patron.test(campo)){
		alert("El campo " + nombreCampo + " debe ser informado con un valor de 0 \u00f3 1");
		return true;
	}
	return false;
}

/**
 * Valida el formulario de alta y modificaion de Adn Retail.
 */
function validaFormAdnRetail(){
	var idSegmento = document.forms["adnRetailForm"]["idSegmentoLocal"].value;
	if(esCampoNoValido(idSegmento, nombreIdSegmento, 10, false)){
		return;
	}
	var descripcion = document.forms["adnRetailForm"]["descripcion"].value;
	if(esCampoNoValido(descripcion, nombreDescripcion, 50, false)){
		return;
	}
	var flagRetail = document.forms["adnRetailForm"]["flagRetail"].value;
	if(esCampoBanderaNoValido(flagRetail, nombreFlagRetail)){
		return;
	}
	var confirmacionUsuario = confirm(mensajeConfirmacion + ' ' + idSegmento + '?');
	if(confirmacionUsuario){
		document.forms["adnRetailForm"].submit();
	}
}

/**
 * Valida los formularios de alta y modificacion del tipo de filtro ADNLocal.
 */
function validaFormAdnLocal(){
	if(!(validaFormAdnLocalSubParte1() && validaFormAdnLocalSubParte2())){
		return;
	}
	var adnLocalForm = document.forms["adnLocalForm"];
	var idAdnLocal = adnLocalForm["idAdnLocal"].value;
	
	var confirmacionUsuario = confirm(mensajeConfirmacion + " " + idAdnLocal + "?");
	if(confirmacionUsuario){
		adnLocalForm.submit();
	}
}

function validaFormAdnLocalSubParte1(){
	var adnLocalForm = document.forms["adnLocalForm"];
	var idAdnLocal = adnLocalForm["idAdnLocal"].value;
	if(esCampoNoValido(idAdnLocal, nombreIdAdnLocal, 10, false)){
		return false;
	}
	var descripcion = adnLocalForm["descripcion"].value;
	if(esCampoNoValido(descripcion, nombreDescripcion, 50, false)){
		return false;
	}
	var flagActivo = adnLocalForm["flagActivo"].value;
	if(esCampoBanderaNoValido(flagActivo, nombreFlagActivo)){
		return false;
	}
	var flagPasivo = adnLocalForm["flagPasivo"].value;
	if(esCampoBanderaNoValido(flagPasivo, nombreFlagPasivo)){
		return false;
	}
	
	return true;
}

function validaFormAdnLocalSubParte2(){
	var adnLocalForm = document.forms["adnLocalForm"];
	var flagFondos = adnLocalForm["flagFondos"].value;
	if(esCampoBanderaNoValido(flagFondos, nombreFlagFondos)){
		return false;
	}
	var flagComiciones = adnLocalForm["flagComiciones"].value;
	if(esCampoBanderaNoValido(flagComiciones, nombreFlagComiciones)){
		return false;
	}
	var flagContingentes = adnLocalForm["flagContingentes"].value;
	if(esCampoBanderaNoValido(flagContingentes, nombreFlagContingentes)){
		return false;
	}
	
	var flagAjustes = adnLocalForm["flagAjustes"].value;
	if(esCampoBanderaNoValido(flagAjustes, nombreFlagAjustes)){
		return false;
	}
	var flagInternegocios = adnLocalForm["flagInternegocios"].value;
	if(esCampoBanderaNoValido(flagInternegocios, nombreFlagInternegocios)){
		return false;
	}
	
	return true;
}

/**
 * Valida los formularios de alta y modificacion para el tipo de filtro Producto Gestion.
 */
function validaFormProductoGestion(){
	if(!(validaFormProductoGestionSubParte1() && validaFormProductoGestionSubParte2())){
		return;
	}
	var productoGestionForm = document.forms["productoGestionForm"];
	var idProductoGestion = productoGestionForm["idProductoGestion"].value;
	var confirmacionUsuario = confirm(mensajeConfirmacion + ' ' + idProductoGestion + '?');
	if(confirmacionUsuario){
		productoGestionForm.submit();
	}
}

function validaFormProductoGestionSubParte1(){
	var productoGestionForm = document.forms["productoGestionForm"];
	var idProductoGestion = productoGestionForm["idProductoGestion"].value;
	if(esCampoNoValido(idProductoGestion, nombreIdProductoGestion, 10, false)){
		return false;
	}
	var descripcion = productoGestionForm["descripcion"].value;
	if(esCampoNoValido(descripcion, nombreDescripcion, 50, false)){
		return false;
	}
	var flagActivo = productoGestionForm["flagActivo"].value;
	if(esCampoBanderaNoValido(flagActivo, nombreFlagActivo)){
		return false;
	}
	var flagPasivo = productoGestionForm["flagPasivo"].value;
	if(esCampoBanderaNoValido(flagPasivo, nombreFlagPasivo)){
		return false;
	}
	return true;
}

function validaFormProductoGestionSubParte2(){
	var productoGestionForm = document.forms["productoGestionForm"];
	var flagFondos = productoGestionForm["flagFondos"].value;
	if(esCampoBanderaNoValido(flagFondos, nombreFlagFondos)){
		return false;
	}
	var flagComiciones = productoGestionForm["flagComiciones"].value;
	if(esCampoBanderaNoValido(flagComiciones, nombreFlagComiciones)){
		return false;
	}
	var flagContingentes = productoGestionForm["flagContingentes"].value;
	if(esCampoBanderaNoValido(flagContingentes, nombreFlagContingentes)){
		return false;
	}
	var flagAjustes = productoGestionForm["flagAjustes"].value;
	if(esCampoBanderaNoValido(flagAjustes, nombreFlagAjustes)){
		return false;
	}
	var flagInternegocios = productoGestionForm["flagInternegocios"].value;
	if(esCampoBanderaNoValido(flagInternegocios, nombreFlagInternegocios)){
		return false;
	}
	return true;
}

/**
 * Valida los formularios de alta y modificacion para el tipo de filtro FlagNeteo.
 */
function validaFormFlagNeteo(){
	var valor = document.forms["flagNeteoForm"]["valor"].value;
	if(esCampoBanderaNoValido(valor, nombreValor)){
		return;
	}
	
	var confirmacionUsuario = confirm(mensajeConfirmacion + " " + valor + "?");
	if(confirmacionUsuario){
		document.forms["flagNeteoForm"].submit();
	}
}

/**
 * Valida el formulario del lanzador de Reproceso.
 */
function validaFormReproceso(){
	var forma = document.forms["reprocesoForm"];
	if(forma["indiceArchivo"].value === 'NONE'){
		alert("El campo " + archivo + " debe de ser informado.");
		return;
	}
	if(forma["mes"].value === 'NONE'){
		alert("El campo " + campoMes + " debe de ser informado.");
		return;
	}
	if(forma["anio"].value === 'NONE'){
		alert("El campo " + campoAnio + " debe de ser informado.");
		return;
	}
	var confirmacionUsuario = confirm("Est\u00E1 seguro de lanzar la operaci\u00F3n de reproceso sobre al archivo " 
			+ forma["indiceArchivo"].options[forma["indiceArchivo"].selectedIndex].innerHTML 
			+ " en el periodo " + forma["anio"].value + "/" + forma["mes"].value);
	if(confirmacionUsuario){
		forma.submit();
	}
}

/**
 * Valida el formulario para el lanzador de aprovisionamiento historico.
 */
function validaFormAprovisionamiento(){
	var forma = document.forms["aprovisionamientoForm"];
	if(forma["indiceArchivo"].value === 'NONE'){
		alert("El campo " + archivo + " debe de ser informado.");
		return;
	}
	if(forma["mes"].value === 'NONE'){
		alert("El campo " + campoMes + " debe de ser informado.");
		return;
	}
	if(forma["anio"].value === 'NONE'){
		alert("El campo " + campoAnio + " debe de ser informado.");
		return;
	}
	var confirmacionUsuario = confirm("Est\u00E1 seguro de lanzar la operaci\u00F3n de aprovisionamiento hist\u00F3rico sobre el archivo " 
			+ forma["indiceArchivo"].options[forma["indiceArchivo"].selectedIndex].innerHTML + " en el periodo " 
			+ forma["anio"].value + "/" + forma["mes"].value);
	if(confirmacionUsuario){
		forma.submit();
	}
}

/**
 * Valida que un campo no sea nulo ni cadena vacia.
 */
function validaCadenaVacia(campo, nombreCampo){
	if(campo === null 
			|| campo === undefined
			|| trimTexto(campo).length === 0){
		alert("El campo " + nombreCampo + " debe ser informado.");
		return false;
	}
	return true;
}

/**
 * Valida que una cadena no sobrepase una longitud maxima.
 */
function validaLongitudMaxima(campo, longitud, nombreCampo){
	if(trimTexto(campo).length > longitud){
		alert("El campo " + nombreCampo + " permite una longitud de caracteres maxima de " + longitud);
		return false;
	}
	return true;
}

/**
 * Funcion trim compatible con IE6
 * 
 * @param cadena
 * @returns cadena sin espacios al incio y al final
 */
function trimTexto(cadena){
	var retorno=cadena.replace(/^\s+/g,'');
	retorno=retorno.replace(/\s+$/g,'');
	return retorno;
}

/**
 * Valida que un campo sea numerico.
 */
function validaFormatoNumero(campo, nombreCampo){
	var patron = new RegExp("^[0-9]+$");
	if(!patron.test(campo)){
		alert("El campo " + nombreCampo + " debe ser num\u00e9rico.");
		return false;
	}
	return true;
}

/**
 * Muestra un cuadro de dialogo con el detalle de error en la pagina de monitoreo de cargas.
 */
function muestraDetalleError(detalleError){
	jError('', detalleError, 'Error', '');
}

/**
 * Valida que el usuario haya seleccionado registros en las pantallas de consulta de Tablas de Parametros.
 */
function validaEliminacionRegistrosFE(indice){
	var forms = document.getElementsByTagName("form");
	var seleccionados = forms[indice]['listaOpciones'];
	var haySeleccionados = false;
	if(seleccionados === undefined){
		alert(mensajeNoRegistros);
		return;
	}
	if(seleccionados.length === undefined){
		haySeleccionados = seleccionados.checked;
	}else {
		for(var i= 0; i < seleccionados.length; i++){
			if(seleccionados[i].checked){
				haySeleccionados = true;
				break;
			}
		}
	}
	if(haySeleccionados){
		var confirmacionUsuario = confirm(confirmacionEliminacion);
		if(confirmacionUsuario){
			forms[indice].submit();
		}
	}else{
		alert(mensajeNoRegistros);
		return;
	}
}

/**
 * Valida que el usuario haya elegido archivos a eliminar en las pantallas de consulta de Tablas de Parametros.
 */
function validaEliminacionRegistros(){
	var seleccionados = document.forms['registrosAEliminar']['listaOpciones'];
	var haySeleccionados = false;
	if(seleccionados === undefined){
		alert(mensajeNoRegistros);
		return;
	}
	if(seleccionados.length === undefined){
		haySeleccionados = seleccionados.checked;
	}else {
		for(var i= 0; i < seleccionados.length; i++){
			if(seleccionados[i].checked){
				haySeleccionados = true;
				break;
			}
		}
	}
	if(haySeleccionados){
		var confirmacionUsuario = confirm(confirmacionEliminacion);
		if(confirmacionUsuario){
			document.forms['registrosAEliminar'].submit();
		}
	}else{
		alert(mensajeNoRegistros);
		return;
	}
}

/**
 * Valida el formulario de consulta de Inputs y Outpus Finales por contrato.
 */
function validaConsultaIOForm(){
	var numCotrato = document.forms["consultaIOForm"]["numeroContrato"].value;
	if(!(validaCadenaVacia(numCotrato, 'Contrato') && validaLongitudMaxima(numCotrato, 50, 'Contrato'))){
		return;
	}
	if(document.forms["consultaIOForm"]["idArchivo"].value === 'NONE'){
		alert("Se debe elegir el archivo a consultar.");
		return;
	}
	if(document.forms["consultaIOForm"]["mes"].value === 'NONE'){
		alert("El campo " + campoMes + " debe de ser informado.");
		return;
	}
	if(document.forms["consultaIOForm"]["anio"].value === 'NONE'){
		alert("El campo " + campoAnio + " debe de ser informado.");
		return;
	}
	document.forms["consultaIOForm"].submit();
}
