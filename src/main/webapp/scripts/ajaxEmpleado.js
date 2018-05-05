// Inicializar los tooltip en caso de que los haya
$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})

// función para eliminar empleados
function eliminaEmpleado(id) {
	
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
	
// Función para guardar empleados
function guardarEmpleado() {
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
	var user = $('#usuario').val();
	var error = 'false';
	
	if(psw=="" || pswr=="" || psw!=pswr){
		$("#password").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		$("#passwordRepeat").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = 'true';
	}
	if(nombre==""){
		$("#nombre").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = 'true';
	}
	
	
	
	if(error=='true'){
		return false;
	}
	
	var json = {"nombre":nombre,
			"apellidos":apellidos,
			"codigoPostal":codigoPostal,
			"direccion":direccion,
			"localidad":localidad,
			"provincia":provincia,
			"identificacion":identificacion,
			"email":email};
	
	
	$.ajax({
	    dataType : "json",
	    url : "gestor/empleado/nuevoEmpleado.do",
	    type: "POST",
	    data: JSON.stringify(json),
	    beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
	    success : function(empleado) {
	    	$('body').html(data);
	    },      
	    error : function(){
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
	$('#identificacion').val("");
	$('#nombre').val("");
	$('#apellidos').val("");
	$('#codigoPostal').val("");
	$('#direccion').val("");
	$('#localidad').val("");
	$('#provincia').val("");
	$('#email').val("");
}
