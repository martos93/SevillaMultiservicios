// Variables regex
var emailRegex = /^[-\w.%+]{1,64}@(?:[A-Z0-9-]{1,63}\.){1,125}[A-Z]{2,63}$/i;

// Función para ver los presupuestos
function verPresupuestos(id){
	$.ajax({
	    url : "gestor/presupuesto/verPresupuestos.do?clienteId="+id,
	    type: "GET",
	    data: id,
	    success : function(data) {
	    	$('body').html(data);
	    	},      
	    error : function(){
	    	alertaError("Se ha producido un error al encontrar los presupuestos.");
	    }
	});
}

// función para abrir el modal de editar clientes
function editarCliente(id) {
	$('#password').hide();
	$('#passwordRepeat').hide();
	$('#usuario').hide();
	$('#pwdlabel').hide();
	$('#pwdrlabel').hide();
	$('#usuariolabel').hide();
	$('#editar').show();
	$('#guardar').hide();
	$('#clienteId').val(id);
	
	editar = true;
		$.ajax({
		    url : "gestor/cliente/editarCliente.do?clienteId="+id,
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
		    	$('#refCatastro').val(data.refCatastro);
		    	
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al encontrar el cliente.");
		    }
		});
}

// función para modificar clientes
function modificarCliente() {
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
	var clienteId = $('#clienteId').val();
	var refCatastro = $('#refCatastro').val();
	var error = 'false';
	
	var validaciones = [identificacion,nombre,apellidos,codigoPostal,direccion,localidad,provincia,email,refCatastro];
	var nombresAtributos = ["identificacion","nombre","apellidos","codigoPostal","direccion","localidad","provincia","email","refCatastro"];
	
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
			"clienteId":clienteId,
			"refCatastro":refCatastro};
	
	$.ajax({
	    url : "gestor/cliente/modificarCliente.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al modificar el cliente.");
	    }
	});
	        
	}


	
// función para eliminar clientes
function eliminarCliente(id) {
	
	if(confirm("Se borrará el cliente seleccionado junto con sus datos asociados. ¿Está seguro?")){
		if(confirm("Una vez borrado no podrá recuperar los datos. ¿Está seguro?")){
		$.ajax({
		    url : "gestor/cliente/eliminaCliente.do?clienteId="+id,
		    type: "GET",
		    data: id,
		    success : function(data) {
		    	$('body').html(data);
		    	},      
		    error : function(){
		    	alertaError("Se ha producido un error al borrar el cliente.");
		    }
		});
		
	}
}
	
}
	
// Función para guardar clientes
function guardarCliente() {
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
	var refCatastro = $('#refCatastro').val();
	
	var error = 'false';
	
	var validaciones = [identificacion,nombre,apellidos,codigoPostal,direccion,localidad,provincia,email,usuario,refCatastro];
	var nombresAtributos = ["identificacion","nombre","apellidos","codigoPostal","direccion","localidad","provincia","email","usuario","refCatastro"];
	
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
			"refCatastro":refCatastro};
	
	$.ajax({
	    dataType : "json",
		url : "gestor/cliente/nuevoCliente.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(data) {
	    	$('body').html(data);
	    },      
	    error : function(data){
	    	alertaError("Se ha producido un error al guardar el cliente.");
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
	$('#refCatastro').val("");
	$('#password').show();
	$('#passwordRepeat').show();
	$('#usuario').show();
	$('#pwdlabel').show();
	$('#pwdrlabel').show();
	$('#usuariolabel').show();
	$('#guardar').show();
}

function limpiarDatosEdit(){
	$('.has-error').hide();
	$('#guardar').hide();
	$('#password').hide();
	$('#passwordRepeat').hide();
	$('#usuario').hide();
	$('#pwdlabel').hide();
	$('#pwdrlabel').hide();
	$('#usuariolabel').hide();
	$('#editar').show();
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
