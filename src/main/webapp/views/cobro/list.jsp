<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<div id="body">

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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="scripts/ajaxCliente.js"></script>
<script src="scripts/ajaxPresupuesto.js"></script>
<script src="scripts/ajaxGasto.js"></script>
<link href="styles/datepicker.css" rel="stylesheet" type="text/css">
<script src="scripts/datepicker/datepicker.min.js"></script>

<script src="scripts/datepicker/i18n/datepicker.es.js"></script>

<style>
.panel-heading {
	background-color: #bf1200 !important;
	color: #f5f5f5 !important";
}
</style>

<br>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-0">
			<img src="images/logo-sms.jpg" class="img-responsive center-block"
				height="250px" width="250px" alt="Sevilla Multiservicios" />
		</div>
		<div class="col-md-6 col-md-offset-0">
			<br>
			<div class="panel panel-default">
				<div class="panel-heading">
					<b><font style="color: white">Resumen financiero del
							presupuesto: <small><b>${codigoPresupuesto}</b></small>
					</font> </b>
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-6 col-md-offset-0">
							<label>Fecha de presupuesto:</label>
							${presupuestoForm.fechaInicioS}
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 col-md-offset-0">
							<label>Fecha inicio de obra:</label>
							${presupuestoForm.fechaObraS}
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 col-md-offset-0">
							<label>Fecha fin de obra:</label> ${presupuestoForm.fechaFinS}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<b><font style="color: white">Datos del cliente</font></b>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-5 col-md-offset-0">
					<label>Cliente:</label> ${cliente.nombre} ${cliente.apellidos}
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-0">
					<label>CIF / DNI:</label> ${cliente.identificacion}
				</div>
				<div class="col-md-4 col-md-offset-0">
					<label>Email:</label> ${cliente.email}
				</div>
				<div class="col-md-4 col-md-offset-0">
					<label>Telefono:</label> ${cliente.email}
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<font style="color: white"><b>Datos del trabajo</b></font>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-5 col-md-offset-0">
					<label>Dirección:</label> ${presupuestoForm.direccionObra}
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 col-md-offset-0">
					<label>Localidad:</label> ${presupuestoForm.localidad}
				</div>
				<div class="col-md-4 col-md-offset-0">
					<label>Provincia:</label> ${presupuestoForm.provincia}
				</div>
				<div class="col-md-4 col-md-offset-0">
					<label>C.P.:</label> ${presupuestoForm.codigoPostal}
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-md-offset-0">
					<label>Tipo de trabajo:</label> ${presupuestoForm.tipoTrabajoS}
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<font style="color: white"><b>Personal</b></font>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12 col-md-offset-0">
					<label>Dirección de obra:</label><button data-toggle="modal" data-target="#modalInvolucrados" title="Añadir" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-placement="top" data-toggle="tooltip" title="Añadir" style="color:#bf1200"><span class='glyphicon glyphicon-plus'></span></a></button>
        			
        			<jstl:if test="${fn:length(direccionObra)>0}">
					<div id="personalObra"><script>formateaPersonalObra()</script></div>
					</jstl:if>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 col-md-offset-0">
					<label>Empleados:</label><button data-toggle="modal" data-target="#modalEmpleados" onclick="" title="Añadir" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-placement="top" data-toggle="tooltip" title="Añadir" style="color:#bf1200"><span class='glyphicon glyphicon-plus'></span></a></button>
					<br>
					<ul>
						<jstl:forEach items="${empleadosPresupuesto}" var="empleado">
							<li>${empleado.nombre} ${empleado.apellidos}  <button data-toggle="tooltip" onclick="eliminarEmpleadoPresupuesto('${empleado.id}')" data-placement="top" title="Eliminar" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><span class='glyphicon glyphicon-remove'></span></a></button>
       </li>
						</jstl:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<font style="color: white"><b>Datos económicos</b></font>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Presupuestado:</label> <div id="presupuestado"> ${presupuestado}</div>
				</div>
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Añadidos:</label>  <div id="addFactura">${addFactura}</div>
				</div>
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Total:</label>  <div id="totalPresupuesto">${addFactura+presupuestado}</div>
				</div>
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Margen de maniobra:</label>

					<c:choose>
						<c:when test="${margenNegativo}">
							<b><font color="red"><div id="margenManiobra">${margenManiobra}</div></font></b>
						</c:when>
						<c:otherwise>
 <div id="margenManiobra">           ${margenManiobra}</div>
         </c:otherwise>
					</c:choose>

				</div>
			</div>
			<div class="row">
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Mano de obra:</label> <div id="manoObra">${manoObra}</div>
				</div>
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Materiales:</label> <div id="material">${material}</div>
				</div>
				<div class="col-md-3 col-md-offset-0">
					<label style="float:left">Subcontrataciones:</label> <div id="subCont">${subCont}</div>
				</div>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-2 col-md-offset-5">
				<button type="button" class="btn btn-danger" data-toggle="modal"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					data-target="#modalCobro" onclick="limpiarDatosCobro()">
					<span class="glyphicon glyphicon-plus"></span> Nuevo Movimiento
				</button>
			</div>
		</div>
		<br>

		<jstl:if test="${fn:length(cobros)>0}">
			<display:table name="cobros" htmlId="tablaCobros" id="cobro"
				sort="external" defaultsort="1" requestURI="${requestURI}"
				class="table dt-responsive nowrap">

				<display:column title="Fecha">
					<fmt:formatDate value="${cobro.fecha}" pattern="dd/MM/yyyy" />
				</display:column>

				<display:column
					title="Liquidado ">
		<div id="liquidado_${cobro_rowNum}">${cobro.liquidado} </div>
		</display:column>

				<display:column 
					title="Pendiente cobro">
		<div id="pendiente_${cobro_rowNum}">${cobro.pendiente}</div>  
		</display:column>

				<display:column title="Total">
		<div id="total_${cobro_rowNum}">${cobro.total} </div>
		</display:column>


				<display:column sortable="disabled">
					<button onclick="editarCobro(${cobro.id})" data-toggle="modal"
						data-target="#modalCobro"
						style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
						type="button" class="btn btn-link">
						<a data-toggle="tooltip" data-placement="top" title="Editar"
							style="color: #bf1200;"><span
							class='glyphicon glyphicon-pencil'></span></a>
					</button>
					<button onclick="eliminarCobro(${cobro.id},${presupuestoId})"
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
	
</div>

<!-- Modal Cobro-->
<div class="modal fade" id="modalCobro" tabindex="-1" role="dialog"
	aria-labelledby="modalCobroLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalCobroLabel">Nuevo Cobro</h4>
				<h4 class="modal-title" id="modalCobroLabelEdit">Editar Cobro</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioCobro" modelAttribute="cobroForm">
					<input type=hidden id="presupuestoId" name="presupuestoId"
						value="${presupuestoId}">
					<input type=hidden id="cobroId" name="cobroId">

					<div class="row">
						<div class="col-md-5 col-md-offset-1">
							<form:label path="fechaS" for="fechaS">Fecha:</form:label>
							<div class="form-group" id="fechaSpan">
								<div class='input-group date' id='fechaD'>

									<form:input disabled="true" path="fechaS" id="fecha"
									value="${cobroForm.fechaS}"
										cssClass="form-control datepicker-here"
										style="cursor: default;" data-position="right top" />
									<span class="input-group-addon" id="fireDate"
										style="cursor: pointer;"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
						<div class="col-md-5 col-md-offset-0">
							<form:label path="liquidado" for="liquidado">Liquidado:</form:label>
							<form:input onChange="actualizaLiqCobro()" id="liquidado" path="liquidado" cssClass="form-control" />

						</div>
					</div>

				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardar" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="guardarCobro()">Guardar</button>
				<button id="editar" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="modificarCobro()">Editar</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal Involucrados-->
<div class="modal fade" id="modalInvolucrados" tabindex="-1" role="dialog"
	aria-labelledby="modalCobroLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalCobroLabelEdit">Editar Dirección de obra</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioDireccion" modelAttribute="presupuestoForm">

					<div class="row">
						<div class="col-md-12 col-md-offset-0">
							<label for="fechaS">Dirección de obra:</label>
							<div class="form-group">
									<input id="direccionObra" class="form-control" type="text" aria-describedby="ayuda" value="${direccionObra}"/>
									<small id="ayuda" class="form-text text-muted">Introduzca los involucrados en la obra separados por comas ","</small>
							</div>
						</div>
					</div>

				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardarDireccionObra" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="guardarDireccionObra()">Guardar</button>
			</div>
		</div>
	</div>
</div>

<!-- Modal empleados-->
<div class="modal fade" id="modalEmpleados" tabindex="-1" role="dialog"
	aria-labelledby="modalCobroLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalCobroLabelEdit">Añadir empleados</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioDireccion">

					<div class="row">
						<div class="col-md-12 col-md-offset-0">
							<label for="fechaS">Empleado:</label>
							<div class="form-group">
									<select class="form-control" id="empleadoPres">
									<jstl:forEach items="${empleados}" var="empleado">
										<option value="${empleado.id}">${empleado.nombre} ${empleado.apellidos}</option>
									</jstl:forEach>
									</select>
									
							</div>
							<script>compruebaEmpleados()</script>
						</div>
					</div>

				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardarEmpleados" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="guardarEmpleados()">Guardar</button>
			</div>
		</div>
	</div>
</div>

<script>
var fecha = $('#fecha').val();
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
$('#fecha').val(fecha);
</script>

<script>formateaValoresCobro();</script>
</div>