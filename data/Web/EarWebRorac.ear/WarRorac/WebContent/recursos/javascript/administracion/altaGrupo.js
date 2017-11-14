$(document).ready(function(){
	
	$('#nombreRequerido').hide();
	$('#descripcionRequerido').hide();
	$('#pantallaRequerida').hide();
	
    $('#btnAltaGrupo').click(function(){
    	var error = false;
    	if ($('#nombreGrupo').val().trim() === '' || $('#nombreGrupo').val().length > 80) {
    		$('#nombreRequerido').show();
            error = true;
        }else{
        	$('#nombreRequerido').hide();
        }
    	if ($('#descripcionGrupo').val().length === 0 || $.trim($('#descripcionGrupo').val()) === '' || $('#descripcionGrupo').val().length > 300 ) {
    		$('#descripcionRequerido').show();
            error = true;
        }else{
        	$('#descripcionRequerido').hide();
        }
    	if($('input[name="pantallaActiva"]').is(':checked')){
    		$('#pantallaRequerida').hide();
        }else{
        	$('#pantallaRequerida').show();
            error = true;
        }
    	
    	if(error === true){
    		jAlert($('#gralCamposObligatorios').val(), $('#gralFaltanCampos').val(), 'Alerta', $('#gralVerifique').val());
    	}else{
            $('#altaGrupo').submit();
    	}
    	
    });
    
    $("#regresar").click(function() {
    	$('#altaGrupo').attr('action', 'consultarGrupos.do');
        $('#altaGrupo').submit();
	});
});