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

<script>
	var tabla = crearTabla('solicitud');
	tabla.dataTable( {
				"aoColumns": ["bSortable": false]
				} );
</script>


<div class="container">
	<security:authorize access="hasRole('GESTOR')">
		<h4>Mostrando todas las solicitudes</h4>
	</security:authorize>

	<security:authorize access="hasRole('CLIENTE')">
		<h4>Mostrando tus solicitudes</h4>
	</security:authorize>

</div>


<div class="container">
	<security:authorize access="hasRole('CLIENTE')">
		<div class="row">
			<div class="col-md-2 col-md-offset-5">
				<button type="button" class="btn btn-danger" data-toggle="modal"
					data-target="#modalSolicitud"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="limpiarDatosCrearSolicitud()">
					<span class="glyphicon glyphicon-plus"></span> Nueva Solicitud
				</button>
			</div>
		</div>
		<br>
	</security:authorize>




	<display:table name="solicitudes" id="solicitud" class="table">

		<display:column property="titulo" title="Titulo" />
<security:authorize access="hasRole('GESTOR')">
		<display:column property="fechaCreacion" title="Fecha">
		<fmt:formatDate value="${solicitud.fechaCreacion}"
				pattern="dd/MM/yyyy HH:mm" />
		</display:column>
</security:authorize>
		<display:column property="descripcion" title="Descripción" />

		<display:column title="Cantidad estimada">
			<div id="cantSol">${solicitud.cantidad}</div>
			<script> formateaCantidadSoli('${solicitud.cantidad}')</script>
		</display:column>

		<display:column title="Estado">
			<jstl:if test="${solicitud.estado == 'ENVIADO'}">
				<p style="color: green; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="Enviado"
						class="glyphicon glyphicon-envelope"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'ACEPTADO'}">
				<p style="color: green; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="Aceptado"
						class="glyphicon glyphicon-ok"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'RECHAZADO'}">
				<p style="color: red; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="Rechazado"
						class="glyphicon glyphicon-remove"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'PENDIENTE'}">
				<p style="color: orange; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="Enviado"
						class="glyphicon glyphicon-envelope"></span>
				</p>
			</jstl:if>
		</display:column>

		<display:column title="Ver">
			<jstl:if
				test="${solicitud.presupuesto.id != 0 && solicitud.presupuesto.id!=null}">
				<button onclick="verPresupuesto('${solicitud.presupuesto.id}')"
					style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
					type="button" class="btn btn-link">
					<a data-toggle="tooltip" data-placement="top"
						title="Ver Presupuesto" style="color: #bf1200;"><span
						class='glyphicon glyphicon-file'></span></a>
				</button>

			</jstl:if>
		</display:column>
		
		<security:authorize access="hasRole('GESTOR')">
		<display:column title="Crear presupuesto" style="text-align:center">
		<button onclick="crearPresupuesto('${solicitud.id}')"
					style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
					type="button" class="btn btn-link">
					<a data-toggle="tooltip" data-placement="top"
						title="Crear presupuesto" style="color: #bf1200;"><span
						class='glyphicon glyphicon-list-alt'></span></a>
				</button>
		</display:column>
		
		</security:authorize>

		<jstl:if test="${fn:length(solicitud.motivoRechazo)>0}">
			<display:column title="Motivo de rechazo">
				<b>${solicitud.motivoRechazo}</b>
			</display:column>
		</jstl:if>



	</display:table>
</div>

<!-- Modal solicitud-->
<div class="modal fade" id="modalSolicitud" tabindex="-1" role="dialog"
	aria-labelledby="modalSolicitudLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalSolicitudLabel">Nueva
					Solicitud</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioPresupuesto" modelAttribute="solicitudForm">
					<div class="form-group">
						<div class="row">
							<div class="col-md-8 col-md-offset-0">
								<form:label path="titulo" for="titulo">Titulo:</form:label>
								<form:input cssClass="form-control" path="titulo" />
							</div>
							<div class="col-md-4 col-md-offset-0">
								<form:label path="cantidad" for="cantidad">Cantidad: </form:label>
								<form:input path="cantidad" id="cantidad"
									onchange="formateaCantidadSol()" cssClass="form-control" />
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 col-md-offset-0">
								<form:label path="descripcion" for="descripcion">Descripción: </form:label>
								<form:textarea path="descripcion" id="descripcion"
									cssClass="form-control" />
							</div>

						</div>
						<div class="row">
							<div class="col-md-5 col-md-offset-0">
								<form:label path="tipoTrabajoId" for="tipoTrabajoId">Tipo de trabajo: </form:label>
								<form:select path="tipoTrabajoId" cssClass="form-control"
									items="${tiposTrabajo}" itemLabel="descripcion" itemValue="id">

								</form:select>
							</div>
						</div>
					</div>

				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardarConcepto" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="crearSolicitud()">Crear</button>
			</div>
		</div>

	</div>
</div>
<script src="scripts/ajaxSolicitud.js"></script>
