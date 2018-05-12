//Funcion para crear un presupuesto
function crearPresupuesto(id){
	$.ajax({
	    url : "gestor/presupuesto/crearPresupuesto.do?clienteId="+id,
	    type: "GET",
	    data: id,
	    success : function(data) {
	    	$('body').html(data);
	    	},      
	    error : function(){
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