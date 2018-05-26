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

function formateaCantidadSoli(num,i){
	var cant = formateaNum(parseFloat($("#cantSol_"+i).html()));
	$("#cantSol_"+i).html(cant+" €");
}

function crearSolicitud(){
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

function limpiarDatosCrearPresupuestoSolicitud(id,tipoTrabajoId,i){
	$('#titulo').val("");
	$('#codigo').val("");
	$('#direccionObra').val("");
	$('#localidad').val("");
	$('#provincia').val("");
	$('#solicitudId').val(id);
	$('#tipoTrabajoPresupuesto').val(tipoTrabajoId);
	$('#tipoTrabajoPresupuestoId').val(tipoTrabajoId);
	$('#tipoTrabajoPresupuesto').prop('disabled', 'disabled');
	var solicitudId = $('#solicitudId_'+i).val();
	$('#solicitudIdPresupuestoC').val(solicitudId);
	var clienteId = $('#clienteSolicitud_'+i).val();
	$('#clienteSolicitudId').val(clienteId);
	
	
}

function crearPresupuestoSolicitud(){
	toastr.clear();
	$('.has-error').hide();
	var tituloPresupuesto = $('#tituloPresupuesto').val();
	var codigo = $('#codigo').val();
	var direccionObra = $('#direccionObra').val();
	var localidad = $('#localidad').val();
	var provincia = $('#provincia').val();
	var codigoPostal = $('#codigoPostal').val();
	var tipoTrabajo = $('#tipoTrabajoPresupuestoId').val();
	var clienteId = $('#clienteSolicitudId').val();
	var solicitudId = $('#solicitudIdPresupuestoC').val();
	
	var validaciones = [tituloPresupuesto,codigo,direccionObra,localidad,provincia,codigoPostal];
	var nombres = ["tituloPresupuesto","codigo","direccionObra","localidad","provincia","codigoPostal"];
	var error = false;
	var i;
	for (i = 0; i < validaciones.length; i++) { 
		if(validaciones[i]==""){
			$("#"+nombres[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
	    	error = true;
		}
	}
	
	if(error==true){
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	
	var json = {
			"titulo":tituloPresupuesto,
			"codigo":codigo,
			"direccionObra":direccionObra,
			"localidad":localidad,
			"provincia":provincia,
			"clienteId":clienteId,
			"codigoPostal":codigoPostal,
			"tipoTrabajo":tipoTrabajo,
			"solicitudId":solicitudId
	}
	
	$.ajax({
	    url : "gestor/presupuesto/crearPresupuesto.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	$( "body" ).removeClass( "modal-open" );
	    	alertaExito("Se ha creado correctamente el presupuesto.");
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al crear el presupuesto.");
	    }
	});
}

function rechazarSolicitud(){
	var motivoRechazo = $('#rechazo').val();
	var solicitudId = $("#solicitudIdRechazo").val();
	var json = {
			"id":solicitudId,
			"motivoRechazo":motivoRechazo
	}
	
	$.ajax({
	    url : "gestor/solicitud/rechazarSolicitud.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	$("body").removeClass( "modal-open" );
	    	alertaExito("Se ha rechazado la solicitud.");
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al rechazar la solicitud.");
	    }
	});
	
}

function limpiarModalRechazo(solicitudId){
	$("#solicitudIdRechazo").val(solicitudId);
	$("#rechazo").val("");
	
}

function verPresupuestoCliente(presupuestoId){
	$.ajax({
	    url : "presupuesto/verPresupuesto.do?presupuestoId="+presupuestoId,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	$("body").removeClass( "modal-open" );
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al mostrar el presupuesto.");
	    }
	});
}