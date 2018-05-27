function limpiarDatosNuevoConceptoFactura(){
	$('.has-error').hide();
	$('#editarConcepto').hide();
	$('#guardarConcepto').show();
	$('#tituloC').val("");
	$('#modalConceptoLabel').show();
	$('#modalConceptoLabelEdit').hide();
}

function guardarConceptoFactura(){
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
	    
		url : "factura/nuevoConcepto.do",
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

function actualizaPreciosConceptosFactura(id,total){
	debugger
	total = total*1;
	total = formateaNum(total);
	if(total!="0,00"){
	$('#totalConceptoF_'+id).html("<b>"+total+" €</b>");
	}
}

function eliminarConceptoFactura(conceptoId,presupuestoId) {
	if(confirm("Se borrará el concepto y todas sus tareas asociadas. Una vez borrado no podrá recuperar los datos. ¿Está seguro?")){
		$.ajax({
		    url : "factura/eliminarConcepto.do?conceptoId="+conceptoId+"&presupuestoId="+presupuestoId,
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

function editarConceptoFactura(conceptoId) {
	$('.has-error').hide();
	$('#editarConcepto').show();
	$('#guardarConcepto').hide();
	$('#modalConceptoLabel').hide();
	$('#modalConceptoLabelEdit').show();
	
		$.ajax({
		    url : "factura/editarConcepto.do?conceptoId="+conceptoId,
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

function modificarConceptoFactura(){
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
	    
		url : "factura/modificarConcepto.do",
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

function limpiarDatosCrearTareaFactura(conceptoId){
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
}

function editarTareaFactura(tareaId) {
	$('.has-error').hide();
	$('#editarTarea').show();
	$('#guardarTarea').hide();
	
		$.ajax({
		    url : "factura/editarTarea.do?tareaId="+tareaId,
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

function modificarTareaFactura(){
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
	    
		url : "factura/modificarTarea.do",
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

function eliminarTareaFactura(conceptoId,presupuestoId,tareaId) {
	if(confirm("Se borrará la tarea seleccionada. Una vez borrada no podrá recuperar los datos. ¿Está seguro?")){
		$.ajax({
		    url : "factura/eliminarTarea.do?conceptoId="+conceptoId+"&presupuestoId="+presupuestoId+"&tareaId="+tareaId,
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

function guardarTareaFactura(){
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
	    
		url : "factura/nuevaTarea.do",
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

function actualizaSubtotalTareaFactura(){
	var precioUd = $('#precioUnidad').val();
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
	
	if(String(precioUd).includes(",")){
		$('#precioUnidad').val(String(precioUd).split("\,")[0]+"."+String(precioUd).split("\,")[1]);
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

function actualizaPreciosFactura(id,precioUnidad,subTotal){
	debugger
	precioUnidad = precioUnidad*1;
	precioUnidad = formateaNum(precioUnidad);
	subTotal = subTotal*1;
	subTotal = formateaNum(subTotal);
	$('#precioUdF_'+id).html("&nbsp "+precioUnidad+" €");
	$('#subTotalF_'+id).html("&nbsp "+subTotal+" €");
	
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

function formateaTotalPresupFactura(){
	var totalPresupuesto = $('#totalPresupuestoHidden').val()*1;
	var totalPresupuestoIVA = $('#ivaHidden').val()*1;
	var totalPresupuestoConIVA = $('#totalPresupuestoIVAHidden').val()*1;
	 
	 totalPresupuesto = formateaNum(totalPresupuesto);
	 totalPresupuestoIVA = formateaNum(totalPresupuestoIVA);
	 totalPresupuestoConIVA = formateaNum(totalPresupuestoConIVA);
	 if(totalPresupuesto!="0,00"){
	 $('#totalPresupuesto').html("<b>"+totalPresupuesto+" €</b>");
	 }
	 if(totalPresupuestoIVA!="0,00"){
		 $('#totalPresupuestoIVA').html("<b>"+totalPresupuestoIVA+" €</b>");
		 }
	 if(totalPresupuestoConIVA!="0,00"){
		 $('#totalPresupuestoConIVA').html("<b>"+totalPresupuestoConIVA+" €</b>");
		 }
}

function terminarFactura(facturaId) {
	if(confirm("Se creará la factura correspondiente. Una vez creada no podrá editarse. ¿Está seguro?")){
		$.ajax({
		    url : "factura/terminarFactura.do?facturaId="+facturaId,
		    type: "GET",
		    data: facturaId,
		    success : function(data) {
		    	debugger
		    	$('body').html(data);
		    	},      
		    error : function(){
		    	debugger
		    	alertaError("Se ha producido un error al terminar la factura.");
		    }
		});	
	}
		
}