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
<script>
crearTablaBusqueda('cliente');
</script>
<style>
.dataTables_filter input:focus {
    outline: none !important;
    border-color: #FF0000;
    box-shadow: 0 0 10px #719ECE;

}
.dataTables_filter input {
     width: 75%;
    height: 25px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.42857143;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #ccc;
    border-radius: 4px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
    -webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
    float: right;
    margin-bottom: 5px;
};
</style>
<div class="container">
<h1>Seleccione un cliente</h1>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
			<button type="button" class="btn btn-danger" data-toggle="modal"
			style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				data-target="#modalCliente" onclick="limpiarDatos()">
				<span class="glyphicon glyphicon-plus"></span> Nuevo Cliente
			</button>
		</div>
	</div>
	<br>


	<display:table name="clientes" id="cliente"
		requestURI="${requestURI}" class="table dt-responsive nowrap">

		<spring:message code="cliente.dni" var="dni" />
		<display:column property="identificacion" title="${dni}" />

		<spring:message code="cliente.nombre" var="nombre" />
		<display:column property="nombre" title="${nombre}" />

		<spring:message code="cliente.apellidos" var="apellidos" />
		<display:column property="apellidos" title="${apellidos}" />

		<spring:message code="cliente.codigoPostal" var="codigoPostal" />
		<display:column property="codigoPostal" title="${codigoPostal}" />

		<spring:message code="cliente.direccion" var="direccion" />
		<display:column property="direccion" title="${direccion}" />

		<spring:message code="cliente.localidad" var="localidad" />
		<display:column property="localidad" title="${localidad}" />

		<spring:message code="cliente.provincia" var="provincia" />
		<display:column property="provincia" title="${provincia}" />

		<display:column sortable="disabled">
				<button onclick="limpiarDatosEdit();editarCliente(${cliente.id})" data-toggle="modal" data-target="#modalCliente" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Editar" style="color:#bf1200;"><span class='glyphicon glyphicon-pencil'></span></a></button>
       			<button onclick="verPresupuestos(${cliente.id})" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Ver Presupuestos" style="color:#bf1200;"><span class='glyphicon glyphicon-file'></span></a></button>
       
				
		</display:column>

	</display:table>
</div>


<!-- Modal -->
<div class="modal fade" id="modalCliente" tabindex="-1" role="dialog"
	aria-labelledby="modalClienteLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalClienteLabel">Nuevo Cliente</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioCliente" modelAttribute="clienteForm">
				<input type=hidden id="clienteId" name="clienteId">
					<div class="form-group">
						<div class="row">
							<div class="col-md-5 col-md-offset-0">
								<form:label path="email" for="email">Email</form:label>
								<form:input cssClass="form-control" path="email" />
							</div>
							<div class="col-md-5 col-md-offset-1">
							<form:label id="usuariolabel" path="usuario" for="usuario">Usuario:</form:label>
								<form:input id="usuario" path="usuario" cssClass="form-control" />
								
							</div>
						</div>

						<div class="row">
							<div class="col-md-5 col-md-offset-0">
								<form:label id="pwdlabel" path="password" for="password">Contrase&ntilde;a:</form:label>
								<form:input type="password" path="password"
									cssClass="form-control" />
							</div>
							<div class="col-md-5 col-md-offset-1">
								<form:label id="pwdrlabel" path="passwordRepeat" for="passwordRepeat">Repita contrase&ntilde;a:</form:label>
								<form:input type="password" path="passwordRepeat"
									cssClass="form-control" />
							</div>
						</div>

					</div>
					<hr />
					<div class="form-group">
						<div class="row">
							<div class="col-md-5 col-md-offset-0">
								<form:label path="identificacion" for="identificacion">DNI:</form:label>
								<form:input cssClass="form-control" path="identificacion" />
							</div>
							<div class="col-md-5 col-md-offset-1">
								<form:label path="localidad" for="localidad">Referencia Catastral:</form:label>
						<form:input cssClass="form-control" path="refCatastro" />
							</div>
						</div>

						<div class="row">
							<div id="divNombre" class="col-md-5 col-md-offset-0">
								<form:label path="nombre" for="nombre">Nombre:</form:label>
								<form:input cssClass="form-control" path="nombre" />
							</div>
							<div class="col-md-5 col-md-offset-1">
								<form:label path="apellidos" for="apellidos">Apellidos:</form:label>
								<form:input cssClass="form-control" path="apellidos" />
							</div>

						</div>
						<div class="row">
						<div class="col-md-5 col-md-offset-0">
								<form:label path="telefono" for="telefono">Telefono:</form:label>
								<form:input cssClass="form-control" path="telefono" />
							</div>
						</div>

						<div class="row">
							<div class="col-md-5 col-md-offset-0">
								<form:label path="direccion" for="direccion">Dirección:</form:label>
								<form:input cssClass="form-control" path="direccion" />
							</div>
							<div class="col-md-5 col-md-offset-1">
								<form:label path="codigoPostal" for="codigoPostal">Código Postal:</form:label>
								<form:input cssClass="form-control" path="codigoPostal" />
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-5 col-md-offset-0">
								<form:label path="localidad" for="localidad">Localidad:</form:label>
						<form:input cssClass="form-control" path="localidad" />
							</div>
							<div class="col-md-5 col-md-offset-1">
								<form:label path="localidad" for="provincia">Provincia:</form:label>
						<form:input cssClass="form-control" path="provincia" />
							</div>
						</div>
						
						</div>
						
						
						
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardar" type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="guardarCliente()">Guardar</button>
					<button id="editar" type="button" class="btn btn-danger"
					style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="modificarCliente()">Editar</button>
			</div>
		</div>
	</div>
</div>


