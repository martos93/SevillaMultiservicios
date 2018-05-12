function limpiarDatosCrearTarea(conceptoId){
	$('.has-error').hide();
	$('#editarTarea').hide();
	$('#guardarTarea').show();
	$('#descripcion').val("");
	$('#unidades').val("");
	$('#precioUnidad').val("");
	$('#subTotal').val("");
	$("#subtotal").prop( "disabled", true );
	$('#conceptoId').val(conceptoId);
	$('#unidades').prop("type", "number");
	$('#unidades').prop("min", "0");
	$('#precioUnidad').prop("type","number");
	$('#precioUnidad').prop("min","0");
}

function actualizaSubtotalTarea(){
	debugger;
	var precioUd = $('#precioUnidad').val()*1;
	var unidades = $('#unidades').val();

	if(String(unidades).includes("-")){
		$('#unidades').val(unidades.replace("-",""));
	}
	if(String(unidades).includes(".")){
		$('#unidades').val(unidades.split(".")[0]);
	}
	if(String(precioUd).includes("-")){
		$('#precioUnidad').val(String(precioUd).replace("-",""));
	}
	if(String(precioUd).includes(".")){
		$('#precioUnidad').val(String(precioUd).split("\.")[0]);
	}
	
	precioUd = $('#precioUnidad').val()*1;
	unidades = $('#unidades').val();
	if(precioUd !== null && unidades !== null && precioUd>0 && unidades > 0){
	$('#subTotal').val((precioUd*unidades).toFixed(2));
	$('#precioUnidad').val(precioUnidad.toFixed(2));
	}
}

function guardarTarea(){
	toastr.clear()
	$('.has-error').hide();
	var descripcion = $('#descripcion').val();
	var conceptoId = $('#conceptoId').val();
	var presupuestoId = $('#presupuestoId').val();
	var precioUnidad = $('#precioUnidad').val();
	var unidades = $('#unidades').val();
	var subTotal = $('#subTotal').val();
	var conceptoId = $('#conceptoId').val();
	var error = false;
	
	if(descripcion==""){
		$("#descripcion").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("Debe indicar una descripción para la tarea");
    	error = true;
	}
	if(precioUnidad=="" || precioUnidad<0){
		$("#precioUnidad").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("Debe indicar un precio válido");
    	error = true;
	}
	if(unidades=="" || unidades<0){
		$("#unidades").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("Debe indicar un valor valido");
    	error = true;
	}
	if(error){
		return false;
	}
	
	var json = {"descripcion":descripcion,
			"unidades":unidades,
			"precioUnidad":precioUnidad,
			"subTotal":subTotal,
			"presupuestoId":presupuestoId,
			"conceptoId":conceptoId};
	
	$.ajax({
	    
		url : "gestor/tarea/nuevaTarea.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar la tarea.");
	    }
	});
	
}