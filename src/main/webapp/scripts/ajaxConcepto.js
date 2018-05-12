
function limpiarDatosNuevoConcepto(){
	$('.has-error').hide();
	$('#editarConcepto').hide();
	$('#guardarConcepto').show();
	$('#tituloC').val("");
}

function guardarConcepto(){
	toastr.clear()
	$('.has-error').hide();
	var tituloC = $('#tituloC').val();
	var conceptoId = $('#conceptoId').val();
	var presupuestoId = $('#presupuestoId').val();
	var clienteId = $('#clienteId').val();
	
	if(titulo==""){
		$("#tituloC").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("El nombre del concepto es obligatorio");
    	return false;
	}
	
	var json = {"tituloC":tituloC,"conceptoId":conceptoId,"presupuestoId":presupuestoId,"clienteId":clienteId};
	$.ajax({
	    
		url : "gestor/concepto/nuevoConcepto.do",
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
	    	alertaError("Se ha producido un error al guardar el concepto.");
	    }
	});
	
}