$(document).ready(function(){

	$('#grupoRequerido').hide();
	
    $('#btnGuardaUsuario').click(function(){
    	var error = false;
    	
    	if($('input[name="idGrupo"]').is(':checked')){
    		$('#grupoRequerido').hide();
        }else{
        	$('#grupoRequerido').show();
            error = true;
        }
    	
    	if(error === true){
    		jAlert($('#gralCamposObligatorios').val(), $('#gralFaltanCampos').val(), 'Alerta', $('#gralVerifique').val());
    	}else{
    		$('#modificarUsuario').attr('action', 'modificarUsuario.do');
            $('#modificarUsuario').submit();
    	}
    	
    });
    
    $("#btnCancelar").click(function() {
    	$('#modificarUsuario').attr('action', 'consultarUsuarios.do');
        $('#modificarUsuario').submit();
	});
});