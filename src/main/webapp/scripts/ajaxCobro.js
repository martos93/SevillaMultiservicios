function resumenFinanciero(presupuestoId){
	$.ajax({
	    url : "gestor/cobro/resumenFinanciero.do?presupuestoId="+presupuestoId,
	    type: "GET",
	    data: presupuestoId,
	    success : function(data) {
	    	$('body').html(data);
	    	},      
	    error : function(){
	    	alertaError("Se ha producido un error al mostrar el resumen financiero.");
	    }
	});
}