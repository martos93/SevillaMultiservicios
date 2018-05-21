function limpiarDatosNuevoConcepto(){
	$('.has-error').hide();
	$('#editarConcepto').hide();
	$('#guardarConcepto').show();
	$('#tituloC').val("");
	$('#modalConceptoLabel').show();
	$('#modalConceptoLabelEdit').hide();
}


//función para eliminar conceptos
function eliminarConcepto(conceptoId,presupuestoId) {
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

//función para editar conceptos
function editarConcepto(conceptoId) {
	$('.has-error').hide();
	$('#editarConcepto').show();
	$('#guardarConcepto').hide();
	$('#modalConceptoLabel').hide();
	$('#modalConceptoLabelEdit').show();
	
		$.ajax({
		    url : "gestor/concepto/editarConcepto.do?conceptoId="+conceptoId,
		    type: "GET",
		    data: conceptoId,
		    success : function(data) {
		    	$('#tituloC').val(data.tituloC);
		    	$('#conceptoId').val(data.conceptoId);
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al editar el concepto.");
		    }
		});
		
}

function modificarConcepto(){
	toastr.clear()
	$('.has-error').hide();
	var tituloC = $('#tituloC').val();
	var conceptoId = $('#conceptoId').val();
	var presupuestoId = $('#presupuestoId').val();
	var clienteId = $('#clienteId').val();
	
	if(tituloC==""){
		$("#tituloC").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
    	alertaError("El nombre del concepto es obligatorio");
    	return false;
	}
	
	var json = {"tituloC":tituloC,"conceptoId":conceptoId,"presupuestoId":presupuestoId,"clienteId":clienteId};
	$.ajax({
	    
		url : "gestor/concepto/modificarConcepto.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	$( "body" ).removeClass( "modal-open" );
	    	alertaExito("Se ha modificado correctamente el concepto.");
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al modificar el concepto.");
	    }
	});
	
}

function guardarConcepto(){
	toastr.clear()
	$('.has-error').hide();
	var tituloC = $('#tituloC').val();
	var conceptoId = $('#conceptoId').val();
	var presupuestoId = $('#presupuestoId').val();
	var clienteId = $('#clienteId').val();
	
	if(tituloC==""){
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
	    	$( "body" ).removeClass( "modal-open" );
	    	alertaExito("Se ha guardado correctamente el concepto.");
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar el concepto.");
	    }
	});
	
}

function actualizaPreciosConceptos(id,total){
	total = total*1;
	total = formateaNum(total);
	if(total!="0,00"){
	$('#totalConcepto_'+id).html("<b>"+total+" €</b>");
	}
}