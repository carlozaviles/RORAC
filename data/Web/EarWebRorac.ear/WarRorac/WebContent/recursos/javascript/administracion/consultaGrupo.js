$(document).ready(function(){
    
	if($('input[name="idGrupo"]').length){
		$('#detalleGrupo').show();
	}else{
		$('#detalleGrupo').hide();
	}
	
	$('#detalleGrupo').click(function(){  
    	if($('input[name="idGrupo"]').is(':checked')){
    		$('#modificar').submit();
        }else{
        	jError($('#faltaSeleccionarGrupo').val(), $('#faltaGrupo').val(), 'Error', $('#seleccioneGrupo').val());
        }    	
    });
    
    
});