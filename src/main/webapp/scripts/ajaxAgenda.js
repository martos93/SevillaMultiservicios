function verAgendas(empleadoId) {
	$.ajax({
		url : "gestor/agenda/verAgendas.do?empleadoId=" + empleadoId,
		type : "GET",
		data : empleadoId,
		success : function(data) {
			$('body').html(data);
		},
		error : function(data) {
			alertaError("Se ha producido un error al encontrar las agendas.");
		}
	});
}

function verAgenda(agendaId) {
	$("#agendaId").val(agendaId);
}

function addEntrada() {
	toastr.clear()
	$('.has-error').hide();
	var agendaId = $("#agendaId").val();
	var fecha = $("#fecha").val();
	var entradaTexto = $("#entradaAgendaTexto").val();
	var error = false;

	if (fecha == "") {
		$("#fechaSpan").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = true;
	}
	if (entradaTexto == "") {
		$("#entradaAgendaTexto").after('<span style="color:red" class=\"glyphicon glyphicon-remove form-control-feedback\ has-error"></span>');
		error = true;
	}
	if (error) {
		alertaError("Debe completar los campos obligatorios.");
		return false;
	}
	var json = {
		"fecha" : fecha,
		"entradaTexto" : entradaTexto,
		"agendaId" : agendaId
	};

	$.ajax({

		url : "empleado/agenda/guardarEntrada.do",
		type : "POST",
		data : JSON.stringify(json),
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Accept", "application/json");
			xhr.setRequestHeader("Content-Type", "application/json");
		},
		success : function(data) {
			$('body').html(data);
			$("body").removeClass("modal-open");
			alertaExito("Se ha guardado correctamente la entrada.");
		},
		error : function(data) {
			alertaError("Se ha producido un error al guardar la entrada.");
		}
	});
}
