$(function() {
  $('#uds').on('keydown', '#unidades', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||(/65|67|86|88/.test(e.keyCode)&&(e.ctrlKey===true||e.metaKey===true))&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});
$(function() {
  $('#pud').on('keydown', '#precioUnidad', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||(/65|67|86|88/.test(e.keyCode)&&(e.ctrlKey===true||e.metaKey===true))&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault()});
});

function limpiarDatosCrearTarea(conceptoId){
	$('.has-error').hide();
	$('#editarTarea').hide();
	$('#guardarTarea').show();
	$("#subtotal").prop( "disabled", true );
	$('#conceptoId').val(conceptoId);
}

function actualizaSubtotalTarea(){
	var precioUd = $('#precioUnidad').val();
	var unidades = $('#unidades').val();
	
	if(precioUd !== null && unidades !== null && precioUd>0 && unidades > 0){
	$('#subTotal').val(precioUd*unidades);
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
	
	
	if(descripcion==""){
		$("#descripcion").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("Debe indicar una descripción para la tarea");
	}
	if(precioUnidad=="" || precioUnidad<0){
		$("#precioUnidad").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("Debe indicar un precio válido");
	}
	if(unidades=="" || unidades<0){
		$("#unidades").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("Debe indicar un valor valido");
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