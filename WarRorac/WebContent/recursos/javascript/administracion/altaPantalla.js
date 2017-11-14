$(document).ready(function(){
	
	$('#nombreRequerido').hide();
	$('#descripcionRequerido').hide();
	$('#moduloRequerido').hide();
	
    $('#btnAltaPantalla').click(function(){
    	var error = false;
    	if ($('#nombrePantalla').val().trim() === '' || $('#nombrePantalla').val().length > 80) {
    		$('#nombreRequerido').show();
            error = true;
        }else{
        	$('#nombreRequerido').hide();
        }
    	if ($('#descripcionPantalla').val().length === 0 || $.trim($('#descripcionPantalla').val()) === '' || $('#descripcionPantalla').val().length > 300 ) {
    		$('#descripcionRequerido').show();
            error = true;
        }else{
        	$('#descripcionRequerido').hide();
        }
    	if($('input[name="moduloActivo"]').is(':checked')){
    		$('#moduloRequerido').hide();
        }else{
        	$('#moduloRequerido').show();
            error = true;
        }
    	
    	if(error === true){
    		jAlert($('#gralCamposObligatorios').val(), $('#gralFaltanCampos').val(), 'Alerta', $('#gralVerifique').val());
    	}else{
            $('#altaPantalla').submit();
    	}
    	
    });
    
    $("#regresar").click(function() {
    	$('#altaPantalla').attr('action', 'consultarPantallas.do');
        $('#altaPantalla').submit();
	});
});