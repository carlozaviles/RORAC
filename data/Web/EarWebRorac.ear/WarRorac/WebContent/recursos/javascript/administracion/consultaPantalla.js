$(document).ready(function(){
    
	if($('input[name="idPantalla"]').length){
		$('#detallePantalla').show();
	}else{
		$('#detallePantalla').hide();
	}
	
	$('#detallePantalla').click(function(){  
    	if($('input[name="idPantalla"]').is(':checked')){
    		$('#modificar').submit();
        }else{
        	jError($('#faltaSeleccionarPantalla').val(), $('#faltaPantalla').val(), 'Error', $('#seleccionePantalla').val());
        }    	
    });
    
    
});