//Funcion para crear un presupuesto
function crearPresupuesto(clienteId){
	toastr.clear()
	$('.has-error').hide();
	var titulo = $('#titulo').val();
	var codigo = $('#codigo').val();
	var direccionObra = $('#direccionObra').val();
	var localidad = $('#localidad').val();
	var provincia = $('#provincia').val();
	
	var validaciones = [titulo,codigo,direccionObra,localidad,provincia];
	var nombres = ["titulo","codigo","direccionObra","localidad","provincia"];
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
			"clienteId":clienteId
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
	
	var validaciones = [titulo,codigo,direccionObra,localidad,provincia];
	var nombres = ["titulo","codigo","direccionObra","localidad","provincia"];
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
			"clienteId":clienteId
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
	    	alertaExito("Se ha guardado correctamente el presupuesto.");
	    	},      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar el presupuesto.");
	    }
	});
	
}