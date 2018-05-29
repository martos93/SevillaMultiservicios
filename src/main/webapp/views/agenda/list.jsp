<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="styles/datepicker.css" rel="stylesheet" type="text/css">
<script src="scripts/datepicker/datepicker.min.js"></script>

<script src="scripts/datepicker/i18n/datepicker.es.js"></script>
<div class="container">
	<h4>
		Mostrando las agendas del usuario: <b>${empleado.userAccount.username}</b>
	</h4>
</div>

<div class="container">

	<display:table name="agendas" id="agenda" requestURI="${requestURI}"
		class="table">

		<display:column title="Presupuesto">
		${agenda.presupuesto.codigo } - ${agenda.presupuesto.titulo}<button data-toggle="modal"
				data-target="#modalAgenda" onclick="verAgenda('${agenda.id}')"
				style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
				type="button" class="btn btn-link">
				<a data-toggle="tooltip" data-placement="top" title="Ver agenda"
					style="color: #bf1200;"><span
					class='glyphicon glyphicon-list-alt'></span></a>
			</button>

		</display:column>


	</display:table>
</div>

<style>
.modal {@media (min-width : @screen-sm-min) {text-align: center ;

        &:before {
	content: '';
	height: 100%;
	width: 1px;
	display: inline-block;
	vertical-align: middle;
}

.modal-dialog {
	text-align: left;
	margin: 10px auto;
	display: inline-block;
	vertical-align: middle;
}

}
}
.modal-dialog {
	margin-top: 345.5px;
	width: 90%;
}

.modal-content {
	width: 90%;
	height:90%;
    margin-right: 14px;
}

.modal-body {
	max-height: calc(100% - 120px);
	overflow-y: scroll;
}
</style>

<div class="modal fade" id="modalAgenda" tabindex="-1" role="dialog"
	aria-labelledby="modalAgendaLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalPresupuestoLabel">Entradas en
					la agenda</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioAgenda" modelAttribute="agendaForm">
					<form:input type="hidden" path="agendaId" />
					<security:authorize access="hasRole('EMPLEADO')">
						<div class="row">
							<div class="col-md-3 col-md-offset-0">
								<label for="fechaS">Fecha:</label>
								<div class="form-group" id="fechaSpan">
									<div class='input-group date' id='fechaD'>

										<input disabled="disabled" id="fecha"
											class="form-control datepicker-here" style="cursor: default;"
											data-position="right top" /> <span class="input-group-addon"
											id="fireDate" style="cursor: pointer;"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</div>
							</div>
							<div class="col-md-8 col-md-offset-0">
								<label for="entradaAgendaTexto">Entrada:</label> <input
									id="entradaAgendaTexto" type="text" class="form-control" />
							</div>
							<div class="col-md-1 col-md-offset-0">
								<br>
								<button type="button" class="btn btn-danger" data-toggle="modal"
									data-target="#modalPresupuesto"
									style="color: #fff !important; margin-top: 5px; background-color: #bf1200 !important; border-color: #bf1200 !important;"
									onclick="addEntrada()">
									<span class="glyphicon glyphicon-plus"></span> Añadir
								</button>
							</div>
						</div>
					</security:authorize>
					<div class="form-group">
						
						<div class="row">
							<div id="entradas">
							</div>
						</div>

					</div>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>

			</div>
		</div>

	</div>
</div>
<script>
	var fecha = $('#fecha').val();
	//Initialization
	$('#fecha').datepicker({
		language : 'es',
		maxDate : new Date()
	});
	// Access instance of plugin
	$('#fecha').data('datepicker');
	dp = $('#fecha').datepicker().data('datepicker');
	$('#fireDate').on('click', function() {
		dp.show();
		$('#fecha').focus();
	});
	$("#datepickers-container").addClass("desplazar");
	$('#fecha').val(fecha);
</script>