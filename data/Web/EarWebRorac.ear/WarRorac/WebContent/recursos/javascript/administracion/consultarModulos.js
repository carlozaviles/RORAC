$(document).ready(function(){
    
	if($('input[name="idModulo"]').length){
		$('#detalleModulo').show();
	}else{
		$('#detalleModulo').hide();
	}
	
	$('#detalleModulo').click(function(){  
    	if($('input[name="idModulo"]').is(':checked')){
    		$('#modificar').submit();
        }else{
        	jError($('#faltaSeleccionarModulo').val(), $('#faltaModulo').val(), 'Error', $('#seleccioneModulo').val());
        }    	
    });
    
    
});