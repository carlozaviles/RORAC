$(document).ready(function(){
	
	$('#nombreRequerido').hide();
	$('#descripcionRequerido').hide();
	
    $('#btnGuardaModulo').click(function(){
    	var error = false;
    	if ($('#nombreModulo').val().trim() === '' || $('#nombreModulo').val().length > 80) {
    		$('#nombreRequerido').show();
            error = true;
        }else{
        	$('#nombreRequerido').hide();
        }
    	if ($('#descripcionModulo').val().length === 0 || $.trim($('#descripcionModulo').val()) === '' || $('#descripcionModulo').val().length > 300 ) {
    		$('#descripcionRequerido').show();
            error = true;
        }else{
        	$('#descripcionRequerido').hide();
        }
    	
    	if(error === true){
    		jAlert($('#gralCamposObligatorios').val(), $('#gralFaltanCampos').val(), 'Alerta', $('#gralVerifique').val());
    	}else{
    		$('#modificarModulo').attr('action', 'modificarModulo.do');
            $('#modificarModulo').submit();
    	}
    	
    });
    $('#btnBorrarModulo').click(function(){
        $('#modificarModulo').attr('action', 'borrarModulo.do');
        $('#modificarModulo').submit();
    });
    
    $("#regresar").click(function() {
    	$('#modificarModulo').attr('action', 'consultarModulos.do');
        $('#modificarModulo').submit();
	});
});