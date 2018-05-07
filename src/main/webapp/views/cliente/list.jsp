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

<script src="scripts/ajaxCliente.js"></script>
<script>
	crearTabla('cliente');
</script>

<jstl:if test="${success == true}">
	<script>
	var mensaje = '${mensaje}';
		alertaExito(mensaje);
	</script>
</jstl:if>

<jstl:if test="${error == true}">
	<script>
	var mensaje = '${mensaje}';
		alertaError(mensaje);
	</script>
</jstl:if>

<div class="container">
<h1>Seleccione un cliente</h1>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
			<button type="button" class="btn btn-danger" data-toggle="modal"
				data-target="#modalCliente" onclick="limpiarDatos()">
				<span class="glyphicon glyphicon-plus"></span> Nuevo Cliente
			</button>
		</div>
	</div>
	<br>


	<display:table name="clientes" id="cliente"
		requestURI="${requestURI}" class="table">

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
				<a data-toggle="tooltip"
				data-placement="top" title="Editar">
				<button type="button" class="btn btn-success"  data-toggle="modal"
				data-target="#modalCliente"
				onclick="editarCliente(${cliente.id})">
				<span class="glyphicon glyphicon-pencil"></span>
				</button></a>
				<a class="btn b
				tn-secondary" data-toggle="tooltip"
				data-placement="top" title="Eliminar">
				<button type="button" class="btn btn-danger"
				onclick="eliminarCliente(${cliente.id})">
				<span class="glyphicon glyphicon-remove"></span>
				</button></a>
				
					<a class="btn btn-secondary" data-toggle="tooltip"
				data-placement="top" title="Ver presupuestos">
				<button type="button" class="btn btn-danger"
				onclick="verPresupuestos(${cliente.id})">
				<span class="glyphicon glyphicon-file"></span>
				</button></a>
				
				
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
								<form:label path="direccion" for="direccion">Direcci&oacute;n:</form:label>
								<form:input cssClass="form-control" path="direccion" />
							</div>
							<div class="col-md-5 col-md-offset-1">
								<form:label path="codigoPostal" for="codigoPostal">C&oacute;digo Postal:</form:label>
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
					onclick="guardarCliente()">Guardar</button>
					<button id="editar" type="button" class="btn btn-danger"
					onclick="modificarCliente()">Editar</button>
			</div>
		</div>
	</div>
</div>


