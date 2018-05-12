
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script src="scripts/ajaxConcepto.js"></script>

<form:form id="formularioConcepto"  modelAttribute="presupuestoForm">
		<div class="container">
		${presupuestoForm.aceptado}
		</div>		
<div class="container">
<div class="row">
<div class="col-md-2 col-md-offset-4">
	<form:label path="titulo" for="titulo">Titulo: </form:label><form:input path="titulo" id="titulo" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="codigo" for="titulo">Código: </form:label><form:input path="codigo" id="codigo" cssClass="form-control" />
	</div>
	</div>	
<br><br>
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<img src="images/logo-sms.jpg" class="img-responsive center-block"
				height="250px" width="250px" alt="Sevilla Multiservicios" />
		</div>
		<div class="col-md-5 col-md-offset-1">
		<div class="row">
				<label>Fecha:</label> <fmt:formatDate value="${presupuestoForm.fechaInicio}" pattern="dd-MM-yyyy" />
			</div>
			<div class="row">
				<label>Cliente:</label> ${cliente.nombre} ${cliente.apellidos}
			</div>
			<div class="row">
				<label>DNI: </label> ${cliente.identificacion}
			</div>
			<div class="row">
				<label>Dirección: </label> ${cliente.direccion}
			</div>
			<div class="row">
				<label>Código Postal: </label> ${cliente.codigoPostal}
			</div>
			<div class="row">
				<label>Localidad: </label> ${cliente.localidad}
			</div>
			<div class="row">
				<label>Provincia: </label> ${cliente.provincia}
			</div>
			<div class="row">
				<label>Ref. Catastral: </label> ${cliente.refCatastro}
			</div>

		</div>
	</div>

	<div class="row">
	
	<div class="table-responsive">
  <table class="table">
	 <thead>
      <tr>
        <th>Conceptos</th>
        <th>Uds.</th>
        <th>Precio Ud.</th>
        <th>Subtotal</th>
        <th>Importe</th>
      </tr>
    </thead>
    <tbody>
    <jstl:forEach items="${presupuestoForm.conceptos}" var="concepto">
    <tr>
        <td><b>${concepto.titulo}</b>
        <button data-toggle="tooltip" data-placement="top" title="Editar" style="margin:-9px -16px -2px -12px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><span class='glyphicon glyphicon-pencil'></span></button>
        <button data-toggle="tooltip" data-placement="top" title="Eliminar" style="margin:-9px -16px -2px -12px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><span class='glyphicon glyphicon-remove'></span></button>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td><b>${concepto.total}</b></td>
      </tr>
      <jstl:forEach items="${concepto.tareas}" var="tarea">
      <tr>
        <td>&nbsp ${tarea.descripcion}</td>
        <td>&nbsp ${tarea.unidades}</td>
        <td>&nbsp ${tarea.precioUnidad}</td>
        <td>&nbsp ${tarea.subTotal}</td>
        <td id="totalPres"></td>
      </tr>
      </jstl:forEach>
    </jstl:forEach>
     <tr>
     <td> </td>
     <td> </td>
     <td> </td>
     <td> </td>
     <td> </td>
     </tr>
      <tr>
        <td><b>*Este presupuesto no incluye el 21% de IVA.</b></td>
        <td></td>
        <td></td>
        <td><b>TOTAL PRESUPUESTO<b></td>
        <td><b>${totalPresupuesto}</b></td>
      </tr>
    </tbody>
  </table>
</div>
	
	</div>

</div>
</form:form>

<div class="container">
<a data-toggle="tooltip"
				data-placement="top" title="Nuevo concepto">
				<button type="button" class="btn btn-danger"  data-toggle="modal"
				data-target="#modalConcepto"
				onclick="limpiarDatosCrearConcepto()">Añadir concepto
				<span class="glyphicon glyphicon-pencil"></span>
				</button></a>
</div>

<!-- Modal concepto-->
<div class="modal fade" id="modalConcepto" tabindex="-1" role="dialog"
	aria-labelledby="modalConceptoLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalConceptoLabel">Nuevo Concepto</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioConcepto" modelAttribute="conceptoForm">
				<input type=hidden id="conceptoId" name="conceptoId" value="${conceptoForm.conceptoId}">
				<input type=hidden id="presupuestoId" name="presupuestoId" value="${conceptoForm.presupuestoId}">
				<input type=hidden id="clienteId" name="clienteId" value="${conceptoForm.clienteId}">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 col-md-offset-0">
								<form:label path="tituloC" for="tituloC">Concepto:</form:label>
								<form:input cssClass="form-control" path="tituloC" />
							</div>
						</div>
					</div>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardar" type="button" class="btn btn-danger"
					onclick="guardarConcepto()">Guardar</button>
					<button id="editar" type="button" class="btn btn-danger"
					onclick="modificarConcepto()">Editar</button>
			</div>
		</div>
	</div>
</div>
