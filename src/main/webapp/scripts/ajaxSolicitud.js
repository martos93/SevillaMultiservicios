function limpiarDatosCrearSolicitud(){
	$("#descripcion").css("resize","none");
	$("#titulo").val("");
	$("#cantidad").val("");
	$("#descripcion").val("");
	$("#tipoTrabajoId").val($("#tipoTrabajoId option:first").val());
}

function formateaCantidadSol(){
	$("#cantidad").val(formateaNum(parseFloat($("#cantidad").val())));
	
}

function formateaCantidadSoli(num){
	var cant = formateaNum(parseFloat($("#cantSol").html()));
	$("#cantSol").html(cant+" €");
}

function crearSolicitud(){
	debugger
	toastr.clear();
	$('.has-error').hide();
	var titulo = $('#titulo').val();
	var cantidad = $('#cantidad').val();
	var descripcion = $('#descripcion').val();
	var tipoTrabajoId = $('#tipoTrabajoId').val();
	
	var error = false;
	cantidad = cantidad.split(".");
	cantidad = cantidad.join('');
	cantidad = cantidad.replace(",","\.");
	
	if(cantidad!="" && (cantidad.split(".")[1].length>2||cantidad.includes("-"))){
		alertaError("Formato no válido de cantidad.");
		return false;
	}
	
	var validaciones = [titulo,cantidad,descripcion];
	var nombres = ["titulo","cantidad","descripcion"];
	for (i = 0; i < validaciones.length; i++) { 
		if(validaciones[i]==""){
			$("#"+nombres[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
	    	error = true;
		}
	}
	if(error){
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	
	var json = {"titulo":titulo,
			"cantidad":cantidad,
			"descripcion":descripcion,
			"tipoTrabajoId":tipoTrabajoId
			};
	
	$.ajax({
	    
		url : "cliente/solicitud/crearSolicitud.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	$( "body" ).removeClass( "modal-open" );
	    	alertaExito("Se ha creado correctamente la solicitud.");
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar la solicitud.");
	    }
	});
	
}