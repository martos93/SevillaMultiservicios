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

<script>
	crearTabla('empleado');
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
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
			<button type="button" class="btn btn-danger" data-toggle="modal"
			style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				
				data-target="#modalEmpleado" onclick="limpiarDatos()">
				<span class="glyphicon glyphicon-plus"></span> Nuevo Empleado
			</button>
		</div>
	</div>
	<br>


	<display:table name="empleados" id="empleado"
		requestURI="${requestURI}" class="table">

		<spring:message code="empleado.dni" var="dni" />
		<display:column property="identificacion" title="${dni}" />

		<spring:message code="empleado.nombre" var="nombre" />
		<display:column property="nombre" title="${nombre}" />

		<spring:message code="empleado.apellidos" var="apellidos" />
		<display:column property="apellidos" title="${apellidos}" />

		<spring:message code="empleado.codigoPostal" var="codigoPostal" />
		<display:column property="codigoPostal" title="${codigoPostal}" />

		<spring:message code="empleado.direccion" var="direccion" />
		<display:column property="direccion" title="${direccion}" />

		<spring:message code="empleado.localidad" var="localidad" />
		<display:column property="localidad" title="${localidad}" />

		<spring:message code="empleado.provincia" var="provincia" />
		<display:column property="provincia" title="${provincia}" />

		<display:column sortable="disabled">
				<button onclick="editarEmpleado(${empleado.id})"
				data-toggle="modal" data-target="#modalEmpleado"
				style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;"
				type="button" class="btn btn-link">
				<a data-toggle="tooltip" data-placement="top" title="Editar"
				style="color:#bf1200;"><span class='glyphicon glyphicon-pencil'>
				</span></a></button>
				
				<button data-toggle="tooltip"
				onclick="eliminaEmpleado('${empleado.id}')"
				data-placement="top" title="Eliminar"
				style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button"
				class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Eliminar"
				style="color:#bf1200;"><span class='glyphicon glyphicon-remove'></span></a></button>
      
        
        
		</display:column>

	</display:table>
</div>


<!-- Modal -->
<div class="modal fade" id="modalEmpleado" tabindex="-1" role="dialog"
	aria-labelledby="modalEmpleadoLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalEmpleadoLabel">Nuevo Empleado</h4>
			<h4 class="modal-title" id="modalEmpleadoLabelEdit">Editar Empleado</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioEmpleado" modelAttribute="empleadoForm">
				<input type=hidden id="empleadoId" name="empleadoId">
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
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="guardarEmpleado()">Guardar</button>
					<button id="editar" type="button" class="btn btn-danger"
					style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="modificarEmpleado()">Editar</button>
			</div>
		</div>
	</div>
</div>


