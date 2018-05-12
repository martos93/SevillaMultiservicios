
function limpiarDatosNuevoConcepto(){
	$('.has-error').hide();
	$('#editarConcepto').hide();
	$('#guardarConcepto').show();
	$('#tituloC').val("");
}

//función para eliminar conceptos
function eliminarConcepto(conceptoId,presupuestoId) {
	debugger;
	if(confirm("Se borrará el concepto y todas sus tareas asociadas. Una vez borrado no podrá recuperar los datos. ¿Está seguro?")){
		$.ajax({
		    url : "gestor/concepto/eliminarConcepto.do?conceptoId="+conceptoId+"&presupuestoId="+presupuestoId,
		    type: "GET",
		    data: conceptoId,
		    success : function(data) {
		    	$('body').html(data);
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al borrar el concepto.");
		    }
		});
		
	}
	
	
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