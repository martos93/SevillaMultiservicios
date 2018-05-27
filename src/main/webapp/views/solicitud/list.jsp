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
		<display:column title="Fecha">
		<input type="hidden" id="clienteSolicitud_${solicitud_rowNum}" value="${solicitud.cliente.id}">
		<input type="hidden" id="solicitudId_${solicitud_rowNum}" value="${solicitud.id}">
		
			<fmt:formatDate value="${solicitud.fechaCreacion}"
				pattern="dd/MM/yyyy HH:mm" />
		</display:column>
		<display:column property="tipoTrabajo.descripcion" title="Tipo de trabajo" />
		<display:column property="descripcion" title="Descripción" />

		<display:column title="Cantidad estimada">
			<div id="cantSol_${solicitud_rowNum}">${solicitud.cantidad}</div>
			<script> formateaCantidadSoli('${solicitud.cantidad}','${solicitud_rowNum}')</script>
		</display:column>

		<display:column title="Estado">
			<jstl:if test="${solicitud.estado == 'ENVIADO'}">
				<p style="color: green; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="Se ha enviado la solicitud."
						class="glyphicon glyphicon-envelope"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'ACEPTADO'}">
				<p style="color: green; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="La solicitud ha sido aceptada. Pronto recibirá el presupuesto."
						class="glyphicon glyphicon-ok"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'RECHAZADO'}">
				<p style="color: red; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="El gestor ha rechazado la solicitud."
						class="glyphicon glyphicon-remove"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'PENDIENTE'}">
				<p style="color: orange; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="El gestor está creando el presupuesto."
						class="glyphicon glyphicon-envelope"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'TERMINADO'}">
				<p style="color: green; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="Se ha terminado el presupuesto. A la espera de su aceptación o rechazo por parte del cliente."
						class="glyphicon glyphicon-flag"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'ACEPTADO_CLIENTE'}">
				<p style="color: green; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="El presupuesto ha sido aceptado por el cliente"
						class="glyphicon glyphicon-ok"></span>
				</p>
			</jstl:if>
			<jstl:if test="${solicitud.estado == 'RECHAZADO_CLIENTE'}">
				<p style="color: red; font-size: 15px; margin-left: 20px">
					<span data-toggle="tooltip" title="El presupuesto ha sido rechazado por el cliente."
						class="glyphicon glyphicon-remove"></span>
				</p>
			</jstl:if>
		</display:column>

		<display:column title="Ver">
		<security:authorize access="hasRole('GESTOR')">
			<jstl:if
				test="${solicitud.estado == 'ACEPTADO' || solicitud.estado == 'PENDIENTE'}">
				<button onclick="modificarPresupuesto('${solicitud.presupuestoTemporal}')"style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Editar" style="color:#bf1200;"><span class='glyphicon glyphicon-pencil'></span></a></button>
		</jstl:if>
		
		<jstl:if test="${solicitud.estado == 'TERMINADO' || solicitud.estado == 'ACEPTADO_CLIENTE'}">
			 <button onclick="verPresupuestoCliente('${solicitud.presupuesto.id}')"style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Ver" style="color:#bf1200;"><span class='glyphicon glyphicon-search'></span></a></button>
       	 </jstl:if>
				
			</security:authorize>
			
			
			<security:authorize access="hasRole('CLIENTE')">
			<jstl:if
				test="${solicitud.presupuesto.id != 0 && solicitud.presupuesto.id!=null}">
				<button onclick="verPresupuestoCliente('${solicitud.presupuesto.id}')"
					style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
					type="button" class="btn btn-link">
					<a data-toggle="tooltip" data-placement="top"
						title="Ver Presupuesto" style="color: #bf1200;"><span
						class='glyphicon glyphicon-file'></span></a>
				</button>

			</jstl:if>
			</security:authorize>
			
		</display:column>

		<security:authorize access="hasRole('GESTOR')">
			<display:column>
				<jstl:if test="${solicitud.motivoRechazo == null && solicitud.estado=='ENVIADO'}">
				
				 <button data-toggle="modal" data-target="#modalRechazo"
				 onclick="limpiarModalRechazo('${solicitud.id}')" title="Rechazar"
				 style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;"
				 type="button" class="btn btn-link">
				 <a title="Rechazar"  style="color: #bf1200;" data-toggle="tooltip" data-placement="top" >
				 <span class='glyphicon glyphicon-remove'></span></a></button>
       </jstl:if>
			
				<jstl:if test="${solicitud.estado == 'ENVIADO'}">
				<button onclick="limpiarDatosCrearPresupuestoSolicitud('${solicitud.id}','${solicitud.tipoTrabajo.id}',${solicitud_rowNum })"
					data-toggle="modal" data-target="#modalPresupuesto"
					style="margin: -9px -16px -2px -6px; outline: none; color: #bf1200;"
					type="button" class="btn btn-link">
					<a data-toggle="tooltip" data-placement="top"
						title="Crear presupuesto" style="color: #bf1200;"><span
						class='glyphicon glyphicon-list-alt'></span></a>
				</button>
				</jstl:if>
			</display:column>

		</security:authorize>
	
		<display:column title="Motivo de rechazo">
		<jstl:if test="${fn:length(solicitud.motivoRechazo)>0}">
			
				<b>${solicitud.motivoRechazo}</b>
		
		</jstl:if>
	</display:column>


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
				<input type="hidden" id="solicitudId"/>
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

<div class="modal fade" id="modalPresupuesto" tabindex="-1"
	role="dialog" aria-labelledby="modalPresupuestoLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalPresupuestoLabel">Nuevo
					Presupuesto</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioPresupuesto"
					modelAttribute="presupuestoForm">
					<input type="hidden" id="clienteSolicitudId">
					<input type="hidden" id="solicitudIdPresupuestoC">
					<div class="form-group">
						<div class="row">
							<div class="col-md-8 col-md-offset-0">
								<label for="titulo">Titulo:</label>
								<input class="form-control" value="${presupuestoForm.titulo}" id="tituloPresupuesto" />
							</div>
							<div class="col-md-4 col-md-offset-0">
								<form:label path="codigo" for="codigo">Código: </form:label>
								<form:input path="codigo" id="codigo" cssClass="form-control" />
							</div>
						</div>
						<div class="row">
							<div class="col-md-4 col-md-offset-0">
								<form:label path="direccionObra" for="direccionObra">Dirección obra: </form:label>
								<form:input path="direccionObra" id="direccionObra"
									cssClass="form-control" />
							</div>
							<div class="col-md-4 col-md-offset-0">
								<form:label path="localidad" for="localidad">Localidad: </form:label>
								<form:input path="localidad" id="localidad"
									cssClass="form-control" />
							</div>
							<div class="col-md-4 col-md-offset-0">
								<form:label path="provincia" for="provincia">Provincia: </form:label>
								<form:input path="provincia" id="provincia"
									cssClass="form-control" />
							</div>
						</div>

						<div class="row">

							<div class="col-md-3 col-md-offset-0">
								<form:label path="codigoPostal" for="codigoPostal">Código Postal: </form:label>
								<form:input path="codigoPostal" id="codigoPostal"
									cssClass="form-control" />
							</div>
							<div class="col-md-5 col-md-offset-0">
								<form:label path="tipoTrabajo" for="codigoPostal">Tipo de trabajo: </form:label>
								<input type="hidden" class="form-control" id="tipoTrabajoPresupuestoId" />
								<form:select path="tipoTrabajo" cssClass="form-control" id="tipoTrabajoPresupuesto"
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
					onclick="crearPresupuestoSolicitud()">Crear</button>
			</div>
		</div>

	</div>
</div>

<!-- Modal rechazo-->
<div class="modal fade" id="modalRechazo" tabindex="-1" role="dialog"
	aria-labelledby="modalRechazoLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title">Rechazar</h4>
			</div>
			<div class="modal-body">
				
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 col-md-offset-0">
								<label  for="rechazo">Motivo de rechazo:</label>
								<input id="rechazo" class="form-control" />
								<input type="hidden" id="solicitudIdRechazo">
							</div>
						</div>
					</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="rechazarSolicitud()">Rechazar</button>
				
			</div>
		</div>
	</div>
</div>
<script src="scripts/ajaxSolicitud.js"></script>
