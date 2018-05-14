var fechaRegex = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;

// Función para ver los gastos de un presupuesto
function verGastos(id){
	$.ajax({
	    url : "gestor/gasto/listAll.do?presupuestoId="+id,
	    type: "GET",
	    data: id,
	    success : function(data) {
	    	$('body').html(data);
	    	},      
	    error : function(){
	    	alertaError("Se ha producido un error al cargar la hoja de gastos.");
	    }
	});
}

function editarGasto(gastoId) {
	$('.has-error').hide();
	$('#editar').show();
	$('#modalGastoLabel').hide();
	$('#modalGastoLabelEdit').show();
	$('#guardar').hide();
	var presupuestoId = $('#presupuestoId').val();
		$.ajax({
		    url : "gestor/gasto/editarGasto.do?gastoId="+gastoId+"&presupuestoId="+presupuestoId,
		    type: "GET",
		    data: gastoId,
		    success : function(data) {
		    	var cantidad = formateaNum(data.cantidad);
		    	$('#cantidad').val(cantidad);
		    	$('#concepto').val(data.concepto);
		    	$('#fecha').val(new Date(fecha));
		    	$('#proveedor').val(data.proveedor);
		    	$('#observaciones').val(data.observaciones);
		    	$('#presupuestoId').val(presupuestoId);
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al editar el gasto.");
		    }
		});
}

function limpiarDatosGasto(){
	$('.has-error').hide();
	$('#cantidad').val("");
	$('#concepto').val("");
	$('#fecha').val("");
	$('#observaciones').val("");
	$('#proveedor').val("");
	$('#editar').hide();
	$('#modalGastoLabelEdit').hide();
	$('#modalGastoLabel').show();
	$('#guardar').show();
}

function guardarGasto() {
	toastr.clear();
	debugger;
	$('.has-error').hide();
	var cantidad = $('#cantidad').val();
	var concepto = $('#concepto').val();
	var fecha = $('#fecha').val();
	var proveedor = $('#proveedor').val();
	var observaciones = $('#observaciones').val();
	var presupuestoId = $('#presupuestoId').val();
	
	var error = 'false';
	
	var validaciones = [cantidad,concepto,fecha,proveedor,observaciones];
	var nombresAtributos = ["cantidad","concepto","fecha","proveedor","observaciones"];
	
	validaciones.forEach(function (atributo, i, validaciones) {
	    if(atributo==""){
	    	$("#"+nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
	    	error = 'true';
	    	
	    }else{
	    	if(nombresAtributos[i]=="fecha" && !fechaRegex.test(fecha)){
	    		$("#"+nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		    	error = 'true';
	    		alertaError("Formato de fecha no válido.");
	    	}
	    	
	    }
	});
	
	if(error=='true'){
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	
	cantidad = cantidad.split(".");
	cantidad = cantidad.join('');
	cantidad = cantidad.replace(",","\.");
	if(cantidad.split(".")[1].length>2){
		alertaError("Formato no válido de cantidad.");
		return false;
	}
	var json = {"cantidad":cantidad,
			"concepto":concepto,
			"fecha":fecha,
			"proveedor":proveedor,
			"presupuestoId":presupuestoId,
			"observaciones":observaciones
			};
	
	$.ajax({
		url : "gestor/gasto/nuevoGasto.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	alertaExito("Se ha guardado correctamente el gasto.");
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar el gasto.");
	    }
	});
	        
	}

function actualizaCantidadGasto(){
	debugger;
	var cantidad = $('#cantidad').val()*1;
	if(String(cantidad).includes("-")){
		$('#cantidad').val(String(cantidad).replace("-",""));
	}
	if(String(cantidad).includes(".")){
		$('#cantidad').val(String(cantidad).split("\.")[0]);
	}
	cantidad = $('#cantidad').val()*1;
	if(cantidad !== null && cantidad>0){
	cantidad = formateaNum(cantidad);
	$('#cantidad').val(String(cantidad));
	}
}

$(document).ready(function() {
    $("#cantidad").keydown(function (e) {
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