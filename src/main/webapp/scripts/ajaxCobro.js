function resumenFinanciero(presupuestoId) {
	$.ajax({
		url : "gestor/cobro/resumenFinanciero.do?presupuestoId=" + presupuestoId,
		type : "GET",
		data : presupuestoId,
		success : function(data) {
			$('body').html(data);
		},
		error : function() {
			alertaError("Se ha producido un error al mostrar el resumen financiero.");
		}
	});
}

function limpiarDatosCobro() {
	$('.has-error').hide();
	$('#editar').hide();
	$('#guardar').show();
	$('#liquidado').val("");
	$('#fecha').val("");

	$('#modalCobroLabel').show();
	$('#modalCobroLabelEdit').hide();
}

function editarCobro(cobroId) {
	$('.has-error').hide();
	$('#editar').show();
	$('#guardar').hide();
	$('#modalCobroLabel').hide();
	$('#modalCobroLabelEdit').show();

	$.ajax({
		url : "gestor/cobro/editarCobro.do?cobroId=" + cobroId,
		type : "GET",
		data : cobroId,
		success : function(data) {
			$('#fecha').val(data.fechaS);
			$('#cobroId').val(data.cobroId);
			var liq = formateaNum(data.liquidado);
			$('#liquidado').val(liq);

		},
		error : function() {
			alertaError("Se ha producido un error al editar el concepto.");
		}
	});

}

function actualizaLiqCobro() {
	var liquidado = $('#liquidado').val() * 1;
	if (String(liquidado).includes("-")) {
		$('#liquidado').val(String(liquidado).replace("-", ""));
	}
	if (String(liquidado).includes(".")) {
		$('#liquidado').val(String(liquidado).split("\.")[0]);
	}
	liquidado = $('#liquidado').val() * 1;
	if (liquidado !== null && liquidado > 0) {
		liquidado = formateaNum(liquidado);
		$('#liquidado').val(String(liquidado));
	}
}

function formateaValoresCobro() {
	var manoObra = $("#manoObra").html();
	var material = $("#material").html();
	var subCont = $("#subCont").html();
	var presupuestado = $("#presupuestado").html();
	var addFactura = $("#addFactura").html();
	var margenManiobra = $("#margenManiobra").html();

	manoObra = formateaNum(parseFloat(manoObra));
	material = formateaNum(parseFloat(material));
	subCont = formateaNum(parseFloat(subCont));
	presupuestado = formateaNum(parseFloat(presupuestado));
	addFactura = formateaNum(parseFloat(addFactura));
	margenManiobra = formateaNum(parseFloat(margenManiobra));
	
	$("#manoObra").html("&nbsp;"+manoObra+" €");
	$("#material").html("&nbsp;"+material+" €");
	$("#subCont").html("&nbsp;"+subCont+" €");
	$("#presupuestado").html("&nbsp;"+presupuestado+" €");
	$("#addFactura").html("&nbsp;"+addFactura+" €");
	$("#margenManiobra").html("&nbsp;"+margenManiobra+" €");

	var filas = $("#tablaCobros tbody tr").length;
	var val;
	var variables = ["liquidado_","pendiente_","total_"]
	debugger
	for(var j=0; j<= variables.length-1;j++){
		for(var i =1;i<=filas;i++){
			val = $("#"+variables[j]+i).html();
			val = formateaNum(parseFloat(val));
			$("#"+variables[j]+i).html("&nbsp;"+val+" €");
		}
	}
}

$(document).ready(function() {
    $("#liquidado").keydown(function (e) {
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

function guardarCobro(){
	toastr.clear()
	$('.has-error').hide();
	var fecha = $('#fecha').val();
	var liquidado = $('#liquidado').val();
	var presupuestoId = $('#presupuestoId').val();
	var clienteId = $('#clienteId').val();
	var error = false;
	
	if(liquidado==""){
		$("#liquidado").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = true;
	}
	if(fecha==""){
		$("#fireDate").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = true;
	}
	liquidado = liquidado.replace(/\./g , "");
	liquidado = liquidado.replace(/,/g,"\.");
	liquidado = parseFloat(liquidado);
	window.alert(liquidado);
	if(error){
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	
	var json = {"fechaS":fecha,"presupuestoId":presupuestoId,"clienteId":clienteId,"liquidado":liquidado};
	$.ajax({
	    
		url : "gestor/cobro/nuevoCobro.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	alertaExito("Se ha guardado correctamente el cobro.");
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar el cobro.");
	    }
	});
	
}