// Variables regex
var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;

// Inicializar los tooltip en caso de que los haya
$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})

// función para abrir el modal de editar empleados
function editarEmpleado(id) {
	$('#password').hide();
	$('#passwordRepeat').hide();
	$('#usuario').hide();
	$('#pwdlabel').hide();
	$('#pwdrlabel').hide();
	$('#usuariolabel').hide();
	$('#editar').show();
	$('#guardar').hide();
	$('#empleadoId').val(id);
	$('#modalEmpleadoLabelEdit').show();
	$('#modalEmpleadoLabel').hide();
	
	editar = true;
		$.ajax({
		    url : "gestor/empleado/editarEmpleado.do?empleadoId="+id,
		    type: "GET",
		    data: id,
		    success : function(data) {
		    	$('#identificacion').val(data.identificacion);
		    	$('#nombre').val(data.nombre);
		    	$('#apellidos').val(data.apellidos);
		    	$('#codigoPostal').val(data.codigoPostal);
		    	$('#direccion').val(data.direccion);
		    	$('#localidad').val(data.localidad);
		    	$('#provincia').val(data.provincia);
		    	$('#email').val(data.email);
		    	debugger
		    	$('#telefono').val(data.telefono);
		    	$( "body" ).removeClass( "modal-open" );
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al encontrar el empleado.");
		    }
		});
}

// función para modificar empleados
function modificarEmpleado() {
	debugger
	toastr.clear()
	$('.has-error').hide();
	var identificacion = $('#identificacion').val();
	var nombre = $('#nombre').val();
	var apellidos = $('#apellidos').val();
	var codigoPostal = $('#codigoPostal').val();
	var direccion = $('#direccion').val();
	var localidad = $('#localidad').val();
	var provincia = $('#provincia').val();
	var email = $('#email').val();
	var empleadoId = $('#empleadoId').val();
	var telefono = $('#telefono').val();
	var error = 'false';
	
	var validaciones = [identificacion,nombre,apellidos,codigoPostal,direccion,localidad,provincia,email,telefono];
	var nombresAtributos = ["identificacion","nombre","apellidos","codigoPostal","direccion","localidad","provincia","email","telefono"];
	
	validaciones.forEach(function (atributo, i, validaciones) {
	    if(atributo==""){
	    	$("#"+nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
	    	error = 'true';
	    	
	    }else{
	    	if(nombresAtributos[i]=="email" && !emailRegex.test(email)){
	    		$("#"+nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		    	error = 'true';
	    		alertaError("Formato de correo no válido");
	    	}
	    	if(nombresAtributos[i]=="identificacion"){
	    		var cmpdni = compruebaDni(identificacion);
	    		if(cmpdni==0){
	    			error = 'true';
	    		}
	    	}
	    	if (nombresAtributos[i] == "telefono") {
				if (atributo.length != 9) {
					$("#" + nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
					alertaError("Formato de telefono no válido. Debe tener 9 dígitos.");
					error = 'true';
				}
			}
	    }
	});
	
	if(error=='true'){
		alertaError("Debe completar los campos obligatorios");
		return false;
	}
	
	var json = {"nombre":nombre,
			"apellidos":apellidos,
			"codigoPostal":codigoPostal,
			"direccion":direccion,
			"localidad":localidad,
			"provincia":provincia,
			"identificacion":identificacion,
			"email":email,
			"empleadoId":empleadoId,
			"telefono":telefono};
	
	$.ajax({
		url : "gestor/empleado/modificarEmpleado.do",
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
	    	alertaError("Se ha producido un error al modificar el empleado.");
	    }
	});
	        
	}


	
// función para eliminar empleados
function eliminaEmpleado(id) {
	if(confirm("Se borrará el empleado seleccionado junto con sus agendas asociadas. Una vez borrado no podrá recuperar los datos. ¿Está seguro?")){
		$.ajax({
		    url : "gestor/empleado/eliminaEmpleado.do?empleadoId="+id,
		    type: "GET",
		    data: id,
		    success : function(data) {
		    	$('body').html(data);
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al borrar el empleado.");
		    }
		});
	}
}
	
// Función para guardar empleados
function guardarEmpleado() {
	debugger
	toastr.clear()
	$('.has-error').hide();
	var identificacion = $('#identificacion').val();
	var nombre = $('#nombre').val();
	var apellidos = $('#apellidos').val();
	var codigoPostal = $('#codigoPostal').val();
	var direccion = $('#direccion').val();
	var localidad = $('#localidad').val();
	var provincia = $('#provincia').val();
	var email = $('#email').val();
	var psw = $('#password').val();
	var pswr = $('#passwordRepeat').val();
	var usuario = $('#usuario').val();
	var telefono = $('#telefono').val();
	var error = 'false';
	
	var validaciones = [identificacion,nombre,apellidos,codigoPostal,direccion,localidad,provincia,email,usuario,telefono];
	var nombresAtributos = ["identificacion","nombre","apellidos","codigoPostal","direccion","localidad","provincia","email","usuario","telefono"];
	
	if(psw=="" || pswr=="" || psw!=pswr){
		$("#password").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		$("#passwordRepeat").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		
		alertaError("Las contraseñas deben coincidir");
		error = 'true';
	}else if(psw.length<8){
		$("#password").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		$("#passwordRepeat").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
			alertaError("La contraseña debe tener al menos 8 caracteres");
			error = 'true';
	}
	
	validaciones.forEach(function (atributo, i, validaciones) {
	    if(atributo==""){
	    	$("#"+nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
	    	error = 'true';
	    	
	    }else{
	    	if(nombresAtributos[i]=="email" && !emailRegex.test(email)){
	    		$("#"+nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		    	error = 'true';
	    		alertaError("Formato de correo no válido");
	    	}
	    	if(nombresAtributos[i]=="identificacion"){
	    		var cmpdni = compruebaDni(identificacion);
	    		if(cmpdni==0){
	    			error = 'true';
	    		}
	    	}
	    	if (nombresAtributos[i] == "telefono") {
				if (atributo.length != 9) {
					$("#" + nombresAtributos[i]).after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
					alertaError("Formato de telefono no válido. Debe tener 9 dígitos.");
					error = 'true';
				}
			}
	    }
	});
	
	if(error=='true'){
		alertaError("Debe completar los campos obligatorios");
		return false;
	}
	
	var json = {"nombre":nombre,
			"apellidos":apellidos,
			"codigoPostal":codigoPostal,
			"direccion":direccion,
			"localidad":localidad,
			"provincia":provincia,
			"identificacion":identificacion,
			"email":email,
			"usuario":usuario,
			"password":psw,
			"passwordRepeat":pswr,
			"telefono":telefono};
	
	$.ajax({
		url : "gestor/empleado/nuevoEmpleado.do",
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
	    	alertaError("Se ha producido un error al guardar el empleado.");
	    }
	});
	        
	}



function crearTabla(id){
$(document).ready(function() {
    $('#'+id).DataTable( {searching:false,
        "language": {
            "lengthMenu": "Mostrar _MENU_ resultados.",
            "zeroRecords": "No se encontraron resultados.",
            "infoEmpty": "No se encontraron resultados.",
            "info": "_START_ de _END_ resultados (Total: _TOTAL_)",
            paginate: {
                next: "Siguiente",
                previous: "Anterior" 
              }
        },"columnDefs": [
        	   { orderable: false, targets: -1 }
        	   ]
    } );
} );
}

function limpiarDatos(){
	$('.has-error').hide();
	$('#password').val("");
	$('#passwordRepeat').val("");
	$('#usuario').val("");
	$('#identificacion').val("");
	$('#nombre').val("");
	$('#apellidos').val("");
	$('#codigoPostal').val("");
	$('#direccion').val("");
	$('#localidad').val("");
	$('#provincia').val("");
	$('#email').val("");
	$('#editar').hide();
	$('#modalEmpleadoLabelEdit').hide();
	$('#modalEmpleadoLabel').show();
	
	$('#password').show();
	$('#passwordRepeat').show();
	$('#usuario').show();
	$('#pwdlabel').show();
	$('#pwdrlabel').show();
	$('#usuariolabel').show();
	$('#guardar').show();
}

function compruebaDni(dni) {
	  var numero
	  var letr
	  var letra
	  var expresion_regular_dni
	 
	  expresion_regular_dni = /^\d{8}[a-zA-Z]$/;
	 
	  if(expresion_regular_dni.test (dni) == true){
	     numero = dni.substr(0,dni.length-1);
	     letr = dni.substr(dni.length-1,1);
	     numero = numero % 23;
	     letra='TRWAGMYFPDXBNJZSQVHLCKET';
	     letra=letra.substring(numero,numero+1);
	    if (letra!=letr.toUpperCase()) {
	    	$("#identificacion").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
	    	alertaError('Dni erroneo, la letra del NIF no se corresponde');
	    	return 0;
	     }
	  }else{
		  $("#identificacion").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		  alertaError('Dni erroneo, formato no válido');
		  return 0;
	   }
	}

$(document).ready(function() {
    $("#telefono").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
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