<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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

<script src="scripts/ajaxCliente.js"></script>
<script src="scripts/ajaxPresupuesto.js"></script>
<script src="scripts/ajaxGasto.js"></script>
<link href="styles/datepicker.css" rel="stylesheet" type="text/css">
<script src="scripts/datepicker/datepicker.min.js"></script>

<script src="scripts/datepicker/i18n/datepicker.es.js"></script>

<script>
	crearTabla('gasto');
</script>

<div class="container">
	<h1>
		Hoja de gastos del presupuesto: <b>${codigoPresupuesto}</b>
	</h1>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
			<button type="button" class="btn btn-danger" data-toggle="modal"
				style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
				data-target="#modalGasto" onclick="limpiarDatosGasto()">
				<span class="glyphicon glyphicon-plus"></span> Nuevo Gasto
			</button>
		</div>
	</div>
	<br>

	<jstl:if test="${fn:length(gastos)>0}">
		<display:table name="gastos" htmlId="tablaGastos" id="gasto" sort="external" defaultsort="3"
			requestURI="${requestURI}" class="table dt-responsive nowrap">

			<display:column class="cantidad_${gasto_rowNum}" title="Cantidad">
		${gasto.cantidad} 
		</display:column>

			<display:column property="concepto" title="Concepto" />

			<display:column title="Fecha" >
				<fmt:formatDate value="${gasto.fecha}" pattern="dd/MM/yyyy" />
			</display:column>

			<display:column property="observaciones" title="Observaciones" />

			<display:column property="proveedor" title="Proveedor" />

<display:column property="tipo" title="Tipo de gasto" />
			<display:column sortable="disabled">
				<button onclick="editarGasto(${gasto.id})" data-toggle="modal"
					data-target="#modalGasto"
					style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
					type="button" class="btn btn-link">
					<a data-toggle="tooltip" data-placement="top" title="Editar"
						style="color: #bf1200;"><span
						class='glyphicon glyphicon-pencil'></span></a>
				</button>
				<button onclick="eliminarGasto(${gasto.id})"
					style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
					type="button" class="btn btn-link">
					<a data-toggle="tooltip" data-placement="top" title="Eliminar"
						style="color: #bf1200;"><span
						class='glyphicon glyphicon-remove'></span></a>
				</button>
			</display:column>

		</display:table>
	</jstl:if>
</div>
<script>formateaTablaGastos();</script>

<!-- Modal Gasto-->
<div class="modal fade" id="modalGasto" tabindex="-1" role="dialog"
	aria-labelledby="modalGastoLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalGastoLabel">Nuevo Gasto</h4>
				<h4 class="modal-title" id="modalGastoLabelEdit">Editar Gasto</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioGasto" modelAttribute="gastoForm">
					<input type=hidden id="presupuestoId" name="presupuestoId"
						value="${presupuestoId}">
					<input type=hidden id="gastoId" name="gastoId">

					<div class="row">
						<div class="col-md-5 col-md-offset-0">
							<form:label path="cantidad" for="cantidad">Cantidad</form:label>
							<form:input cssClass="form-control" path="cantidad"
								onchange="actualizaCantidadGasto()" />
						</div>
						<div class="col-md-5 col-md-offset-1">
							<form:label path="concepto" for="concepto">Concepto:</form:label>
							<form:input id="concepto" path="concepto" cssClass="form-control" />

						</div>
					</div>

					<div class="row">
						<div class="col-md-5 col-md-offset-0">
							<form:label path="fecha" for="fecha">Fecha:</form:label>
							<div class="form-group" id="fechaSpan">
								<div class='input-group date' id='fechaD'>

									<form:input disabled="true" path="fecha" id="fecha"
										cssClass="form-control datepicker-here"
										style="cursor: default;" data-position="right top" />
									<span class="input-group-addon" id="fireDate"
										style="cursor: pointer;"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-5 col-md-offset-1">
							<form:label path="proveedor" for="proveedor">Proveedor:</form:label>
							<form:input type="proveedor" path="proveedor"
								cssClass="form-control" />
						</div>
					</div>
					<div class="row">
						<div class="col-md-11 col-md-offset-0">
							<form:label path="tipo" for="tipo">Tipo de gasto:</form:label>
							<form:select path="tipo" name="tipo" class="form-control"
								id="tipo">
								<option value="Material" selected>Material</option>
								<option value="Mano de obra">Mano de obra</option>
								<option value="Subcontratación">Subcontratación</option>
							</form:select>
						</div>
					</div>
					<div class="row">
						<div class="col-md-11 col-md-offset-0">
							<form:label path="observaciones" for="observaciones">Observaciones:</form:label>
							<form:input type="observaciones" path="observaciones"
								cssClass="form-control" />
						</div>
					</div>


				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardar" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="guardarGasto()">Guardar</button>
				<button id="editar" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="modificarGasto()">Editar</button>
			</div>
		</div>
	</div>
</div>


<script>
//Initialization
$('#fecha').datepicker({ language: 'es', maxDate: new Date() });
// Access instance of plugin
$('#fecha').data('datepicker');
dp = $('#fecha').datepicker().data('datepicker');
$('#fireDate').on('click', function() {
dp.show();
$('#fecha').focus();
});
$("#datepickers-container").addClass("desplazar");
</script>