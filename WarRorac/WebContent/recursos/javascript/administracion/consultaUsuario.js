$(document).ready(function(){
    
	if($('input[name="idUsuario"]').length){
		$('#detalleUsuario').show();
	}else{
		$('#detalleUsuario').hide();
	}
	
	$('#detalleUsuario').click(function(){  
    	if($('input[name="idUsuario"]').is(':checked')){
    		$('#modificar').submit();
        }else{
        	jError($('#faltaSeleccionarUsuario').val(), $('#faltaUsuario').val(), 'Error', $('#seleccioneUsuario').val());
        }    	
    });
    
    
});