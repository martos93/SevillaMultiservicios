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
<div class="container">
<form:form id="formularioCliente" modelAttribute="clienteForm">
				<input type=hidden id="clienteId" name="clienteId">
					 <fieldset class="form-group">
    <legend>Registro de cliente</legend>
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

					</fieldset>
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
				<div class="row">
				<div class="col-md-5 col-md-offset-0">
				<button id="guardar" type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="registrarCliente()">Guardar</button></div>
				</div>
				</div>