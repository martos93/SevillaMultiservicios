//Funcion para crear un presupuesto
function crearPresupuesto(clienteId){
	toastr.clear()
	$('.has-error').hide();
	var titulo = $('#titulo').val();
	var codigo = $('#codigo').val();
	var direccionObra = $('#direccionObra').val();
	var localidad = $('#localidad').val();
	var provincia = $('#provincia').val();
	var codigoPostal = $('#codigoPostal').val();
	var tipoTrabajo = $('#tipoTrabajo').val();
	
	var validaciones = [titulo,codigo,direccionObra,localidad,provincia,codigoPostal];
	var nombres = ["titulo","codigo","direccionObra","localidad","provincia","codigoPostal"];
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
			"titulo":titulo,
			"codigo":codigo,
			"direccionObra":direccionObra,
			"localidad":localidad,
			"provincia":provincia,
			"clienteId":clienteId,
			"codigoPostal":codigoPostal,
			"tipoTrabajo":tipoTrabajo
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

//Funcion para modificar un presupuesto
function modificarPresupuesto(id){
	$.ajax({
	    url : "gestor/presupuesto/modificarPresupuesto.do?presupuestoId="+id,
	    type: "GET",
	    data: id,
	    success : function(data) {
	    	$('body').html(data);
	    	$( "body" ).removeClass( "modal-open" );
	    	var ttid = $( "#tipoTrabajoId" ).val();
	    	$( "#tipoTrabajo" ).val(ttid);
	    	},      
	    error : function(){
	    	alertaError("Se ha producido un error al modificar el presupuesto.");
	    }
	});
}

function formateaTotalPresup(){
	 var totalPresupuesto = $('#totalPresupuestoHidden').val()*1
	 totalPresupuesto = formateaNum(totalPresupuesto);
	 if(totalPresupuesto!="0,00"){
	 $('#totalPresupuesto').html("<b>"+totalPresupuesto+" €</b>");
	 }
}

function limpiarDatosCrearPresupuesto(){
	$('#titulo').val("");
	$('#codigo').val("");
	$('#direccionObra').val("");
	$('#localidad').val("");
	$('#provincia').val("");
}

function guardarDatosPresupuesto(presupuestoId,clienteId){
	$('.has-error-pres').hide();
	var titulo = $('#titulo').val();
	var codigo = $('#codigo').val();
	var direccionObra = $('#direccionObra').val();
	var localidad = $('#localidad').val();
	var provincia = $('#provincia').val();
	var fechaFinS = $('#fechaFinS').val();
	var fechaObraS = $('#fechaObraS').val();
	var codigoPostal = $('#codigoPostal').val();
	var tipoTrabajo = $('#tipoTrabajo').val();
	
	var validaciones = [titulo,codigo,direccionObra,localidad,provincia,codigoPostal];
	var nombres = ["titulo","codigo","direccionObra","localidad","provincia","codigoPostal"];
	var error = false;
	var i;
	for (i = 0; i < validaciones.length; i++) { 
		if(validaciones[i]==""){
			$("#"+nombres[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error-pres"></span>');
	    	error = true;
		}
	}
	
	if(error==true){
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	
	var json = {
			"titulo":titulo,
			"codigo":codigo,
			"direccionObra":direccionObra,
			"localidad":localidad,
			"provincia":provincia,
			"id":presupuestoId,
			"clienteId":clienteId,
			"fechaFinS":fechaFinS,
			"fechaObraS":fechaObraS,
			"codigoPostal":codigoPostal,
			"tipoTrabajo":tipoTrabajo,
	}
	
	$.ajax({
	    url : "gestor/presupuesto/guardarDatosPresupuesto.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    	$( "body" ).removeClass( "modal-open" );
	    	var ttid = $( "#tipoTrabajoId" ).val();
	    	$( "#tipoTrabajo" ).val(ttid);
	    	alertaExito("Se ha guardado correctamente el presupuesto.");
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar el presupuesto.");
	    }
	});
	
}

function guardarObservaciones(){
	var observacion = $('#observacion').val();
	var presupuestoId = $('#presupuestoId').val();
	var error = false;
	if(observacion==""){
		$("#observacion").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = true;
	}
	if(error){
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	var json = {"observaciones":observacion,"id":presupuestoId};
	$.ajax({
	    url : "gestor/presupuesto/guardarObservaciones.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },	    
	    success : function(data) {
	    	$('body').html(data);
	    	$("body").removeClass("modal-open");
	    	},      
	    error : function(){
	    	alertaError("Se ha producido un error al guardar las observaciones.");
	    }
	});
	
}

function formateaObservaciones(){
	
	var observaciones = $("#observaciones").val();
	if(observaciones.length>0){
		var split = observaciones.split(",");
		var html = "<ul>";
		for(var i=0;i<split.length;i++){
			html+="<li>"+split[i]+ "<button data-toggle=\"tooltip\" onclick=\"eliminarObservacion('"+i+"')\" data-placement=\"top\" title=\"Eliminar\" style=\"margin:-9px -16px -2px -6px;outline: none;color:#bf1200;\" type=\"button\" class=\"btn btn-link\"><span class='glyphicon glyphicon-remove'></span></a></button>"+"</li>";
		}
		html += "</ul>";
		$("#observacionesPres").html(html);	
	}
	
}

function formateaObservacionesVer(){
	var observaciones = $("#observaciones").val();
	if(observaciones.length>0){
		var split = observaciones.split(",");
		var html = "<ul>";
		for(var i=0;i<split.length;i++){
			html+="<li>"+split[i]+"</li>";
		}
		html += "</ul>";
		$("#observacionesPres").html(html);	
	}
}

function eliminarObservacion(i){
	var presupuestoId = $('#presupuestoId').val();
	
	$.ajax({
	    url : "gestor/presupuesto/eliminarObservacion.do?indice="+i+"&presupuestoId="+presupuestoId,
	    type: "GET",
	    data: i,
	    success : function(data) {
	    	$('body').html(data);
	    	$("body").removeClass("modal-open");
	    	},      
	    error : function(){
	    	alertaError("Se ha producido un error al borrar la observacion.");
	    }
	});
}

function enviarPresupuestoCliente(solicitudId){
	
	if(window.confirm("Una vez enviado el presupuesto al cliente no podrá volver a modificarse. ¿Está seguro?")){

		var presupuestoId = $('#presupuestoId').val();
		$.ajax({
		    url : "gestor/presupuesto/enviarPresupuesto.do?solicitudId="+solicitudId+"&presupuestoId="+presupuestoId,
		    type: "GET",
		    data: solicitudId,
		    success : function(data) {
		    	debugger
		    	if(data.includes("No puede enviar")){
		    		$('body').html(data);
		    	}else{
		    		verPresupuestoCliente(presupuestoId);
		    	}
		    	
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al enviar el presupuesto.");
		    }
		});	
	}
}

function aceptarRechazarPresupuesto(presupuestoId,valor){
	
	$.ajax({
	    url : "presupuesto/aceptarRechazarPresupuesto.do?presupuestoId="+presupuestoId+"&valor="+valor,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	if(valor==1){
	    		alertaExito("Se ha aceptado el presupuesto.");
	    	}else{
	        	alertaExito("Se ha rechazado el presupuesto.");
	    	}
		    	
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al rechazar la solicitud.");
	    }
	});
	
}

function crearFactura(presupuestoId){
	
	$.ajax({
	    url : "presupuesto/crearFactura.do?presupuestoId="+presupuestoId,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	
	    	
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al crear la factura.");
	    }
	});
	
}

function crearAlbaran(presupuestoId){
	
	$.ajax({
	    url : "presupuesto/crearAlbaran.do?presupuestoId="+presupuestoId,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	
	    	
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al crear el albarán.");
	    }
	});
	
}

function verAlbaran(presupuestoId){
	
	$.ajax({
	    url : "albaran/verAlbaran.do?presupuestoId="+presupuestoId,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al ver el albarán.");
	    }
	});
	
}

function verFactura(presupuestoId){
	
	$.ajax({
	    url : "factura/verFactura.do?presupuestoId="+presupuestoId,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al ver la factura.");
	    }
	});
	
}