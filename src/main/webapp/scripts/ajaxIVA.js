// Inicializar los tooltip en caso de que los haya
$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})


// función para modificar empleados
function guardarIVA() {
	toastr.clear()
	var ivaID = $('#ivaID').val();
	var porcentaje = $('#porcentaje').val();
	
	if(porcentaje==""){
		alertaError("El IVA no puede estar vacio");
		return false;
	}
	if(porcentaje.includes(".")||porcentaje.includes("-")){
		alertaError("IVA no válido");
		return false;
	}
	
	var json = {"porcentaje":porcentaje,
			"ivaID":ivaID};
	
	$.ajax({
		url : "gestor/iva/modificarIVA.do",
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
	    	alertaError("Se ha producido un error al modificar el IVA.");
	    }
	});
	        
	}
