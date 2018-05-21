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
//	$('#precioUnidad').prop("type","number");
//	$('#precioUnidad').prop("min","0");
}

function editarTarea(tareaId) {
	$('.has-error').hide();
	$('#editarTarea').show();
	$('#guardarTarea').hide();
	
		$.ajax({
		    url : "gestor/tarea/editarTarea.do?tareaId="+tareaId,
		    type: "GET",
		    data: tareaId,
		    success : function(data) {
		    	$('#descripcion').val(data.descripcion);
		    	$('#unidades').val(data.unidades);
		    	var precioUd = formateaNum(data.precioUnidad);
		    	$('#precioUnidad').val(precioUd);
		    	var subTotal=formateaNum(data.subTotal);
		    	$('#subTotal').val(subTotal);
		    	$('#tareaId').val(data.tareaId);
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al editar la tarea.");
		    }
		});
}

function modificarTarea(){
	toastr.clear()
	$('.has-error').hide();
	var descripcion = $('#descripcion').val();
	var conceptoId = $('#conceptoId').val();
	var presupuestoId = $('#presupuestoId').val();
	var precioUnidad = $('#precioUnidad').val();
	var unidades = $('#unidades').val();
	var subTotal = $('#subTotal').val();
	var tareaId = $('#tareaId').val();
	var error = false;
	subTotal = subTotal.split(".");
	subTotal = subTotal.join('');
	subTotal = subTotal.replace(",","\.");
	precioUnidad = precioUnidad.split(".");
	precioUnidad = precioUnidad.join('');
	precioUnidad = precioUnidad.replace(",","\.");
	if(precioUnidad.split(".")[1].length>2){
		alertaError("Formato no válido de precio de unidad.");
		return false;
	}
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
			"conceptoId":conceptoId,
			"tareaId":tareaId};
	
	$.ajax({
	    
		url : "gestor/tarea/modificarTarea.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	$( "body" ).removeClass( "modal-open" );
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar la tarea.");
	    }
	});
	
}

//función para eliminar tareas
function eliminarTarea(conceptoId,presupuestoId,tareaId) {
	if(confirm("Se borrará la tarea seleccionada. Una vez borrada no podrá recuperar los datos. ¿Está seguro?")){
		$.ajax({
		    url : "gestor/tarea/eliminarTarea.do?conceptoId="+conceptoId+"&presupuestoId="+presupuestoId+"&tareaId="+tareaId,
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
	subTotal = subTotal.split(".");
	subTotal = subTotal.join('');
	subTotal = subTotal.replace(",","\.");
	precioUnidad = precioUnidad.split(".");
	precioUnidad = precioUnidad.join('');
	precioUnidad = precioUnidad.replace(",","\.");
	if(precioUnidad.split(".")[1].length>2){
		alertaError("Formato no válido de precio de unidad.");
		return false;
	}
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
	    	$( "body" ).removeClass( "modal-open" );
	    	alertaExito("Se ha guardado correctamente la tarea.");
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar la tarea.");
	    }
	});
	
}

function actualizaSubtotalTarea(){
	var precioUd = $('#precioUnidad').val()*1;
	var unidades = $('#unidades').val();
	if(String(unidades).includes("-")){
		$('#unidades').val(unidades.replace("-",""));
	}
	if(String(unidades).includes(".")){
		$('#unidades').val(unidades.split(".")[0]);
	}
	if(String(unidades).includes(",")){
		$('#unidades').val(unidades.split(",")[0]);
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
	var subTotal = (precioUd*unidades);
	subTotal = formateaNum(subTotal);
		$('#subTotal').val(subTotal);
	
	precioUd = formateaNum(precioUd);
	$('#precioUnidad').val(precioUd);
	}
}

function formateaNum(n){
	var value = n.toLocaleString('de-DE',{ minimumFractionDigits: 2 });
	return value;
}

function actualizaPrecios(id,precioUnidad,subTotal){
	precioUnidad = precioUnidad*1;
	precioUnidad = formateaNum(precioUnidad);
	subTotal = subTotal*1;
	subTotal = formateaNum(subTotal);
	$('#precioUd_'+id).html("&nbsp "+precioUnidad+" €");
	$('#subTotal_'+id).html("&nbsp "+subTotal+" €");
	
}

$(document).ready(function() {
    $("#precioUnidad").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
             // Allow: Ctrl/cmd+A
            (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+C
            (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+X
            (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
});