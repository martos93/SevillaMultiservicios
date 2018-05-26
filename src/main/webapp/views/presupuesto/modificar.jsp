
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
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
 <link href="styles/datepicker.css" rel="stylesheet" type="text/css">
        <script src="scripts/datepicker/datepicker.min.js"></script>

        <script src="scripts/datepicker/i18n/datepicker.es.js"></script>

<%int var=0;%>
<form:form id="formularioConcepto"  modelAttribute="presupuestoForm">
			
<div class="container">
<div class="row">
	<div class="col-md-3 col-md-offset-0">
	<form:label path="titulo" for="titulo">Titulo: </form:label><form:input path="titulo" id="titulo" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="codigo" for="codigo">Código: </form:label><form:input path="codigo" id="codigo" cssClass="form-control" />
	</div>
	<div class="col-md-3 col-md-offset-0">
	<form:label path="direccionObra" for="direccionObra">Dirección obra: </form:label><form:input path="direccionObra" id="direccionObra" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="localidad" for="localidad">Localidad: </form:label><form:input path="localidad" id="localidad" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="provincia" for="provincia">Provincia: </form:label><form:input path="provincia" id="provincia" cssClass="form-control" />
	</div>
</div><br>
<div class="row">
<div class="col-md-3 col-md-offset-0">
<form:label path="codigoPostal" for="codigoPostal">Código postal: </form:label><form:input path="codigoPostal" id="codigoPostal" cssClass="form-control" />
</div>
<div class="col-md-3 col-md-offset-0">
<input type="hidden" id="tipoTrabajoId" value="${tipoTrabajoId}"/>
<form:label path="tipoTrabajo" for="codigoPostal">Tipo de trabajo: </form:label><form:select path="tipoTrabajo" cssClass="form-control" items="${tiposTrabajo}"  itemLabel="descripcion" itemValue="id">
		
	</form:select>
</div>
<div class="col-md-2 col-md-offset-0">
	<form:label path="fechaInicioS" for="fechaInicioS">Fecha Presupuesto: </form:label><form:input disabled="true" path="fechaInicioS" id="fechaInicioS" cssClass="form-control" style="cursor:default"/>
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="fechaObraS" for="fechaObraS">Fecha Inicio Obra: </form:label>
	
<div class="form-group" id="fechaSpan">
							 <div class='input-group date'>
								
								<form:input disabled="true" path="fechaObraS" id="fechaObraS" cssClass="form-control" style="cursor: default;" data-position="right top" />
								<span class="input-group-addon" id="fireDate" style="cursor: pointer;"> <span
									class="glyphicon glyphicon-calendar" ></span>
								</span>
							</div>
							</div>
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="fechaFinS" for="fechaFinS">Fecha Fin Obra: </form:label>
	 <div class="form-group" id="fechaSpanS">
							 <div class='input-group date'>
								
								<form:input disabled="true" path="fechaFinS" id="fechaFinS" cssClass="form-control" style="cursor: default;" data-position="right top" />
								<span class="input-group-addon" id="fireDateS" style="cursor: pointer;"> <span
									class="glyphicon glyphicon-calendar" ></span>
								</span>
							</div>
							</div>
</div>
</div>
<jstl:if test="${presupuestoForm.solicitudId!=null && presupuestoForm.solicitudId!=0 }">
<div class="row">
<div class="col-md-5 col-md-offset-0">
	<br>
	<jstl:if test="${presupuestoForm.aceptado !=null}">
	<c:choose>
    <c:when test="${presupuestoForm.aceptado}">
	<label><b>Estado:</b><a data-toggle="tooltip" data-placement="top" title="El presupuesto ha sido aceptado por el cliente." style="color:green;"><span style="margin-left:5px;font-size:18px;" class='glyphicon glyphicon-ok'></span></a></label>
     </c:when>    
    <c:otherwise>
    <label><b>Estado:</b><a data-toggle="tooltip" data-placement="top" title="El presupuesto ha sido rechazado por el cliente." style="color:red;"><span style="margin-left:5px;font-size:18px;" class='glyphicon glyphicon-remove'></span></a></label>
    
    </c:otherwise>
</c:choose>
</jstl:if>
	</div>
</div>
</jstl:if>
<br><br>
	<div class="row">
		<div class="col-md-5 col-md-offset-1">
			<img src="images/logo-sms.jpg" class="img-responsive center-block"
				height="250px" width="250px" alt="Sevilla Multiservicios" />
		</div>
		<div class="col-md-5 col-md-offset-1">
		<div class="row">
				<label>Fecha:</label> ${presupuestoForm.fechaInicioS}
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
        <th>Conceptos <button data-toggle="modal" 
onclick="limpiarDatosNuevoConcepto()"
data-target="#modalConcepto"
title="Añadir concepto"
style="margin:-9px -16px -2px -6px;outline: none;color:white;"
type="button"
class="btn btn-link">
<a data-placement="top" data-toggle="tooltip" title="Añadir concepto" style="color:white"><span class='glyphicon glyphicon-plus'></span></a></button></th>
        <th>Uds.</th>
        <th>Precio Ud.</th>
        <th>Subtotal</th>
        <th>Importe</th>
      </tr>
    </thead>
    <tbody>
    
    <jstl:forEach items="${presupuestoForm.conceptos}" var="concepto" varStatus="loopConcepto">
    
    <tr>
        <td><b>${concepto.titulo}</b>
        <button onclick="editarConcepto('${concepto.id}')" data-toggle="modal" data-target="#modalConcepto" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Editar" style="color:#bf1200;"><span class='glyphicon glyphicon-pencil'></span></a></button>
        <button data-toggle="tooltip" onclick="eliminarConcepto('${concepto.id}','${presupuestoForm.id}')" data-placement="top" title="Eliminar" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><span class='glyphicon glyphicon-remove'></span></a></button>
        <button data-toggle="modal" onclick="limpiarDatosCrearTarea('${concepto.id}')"	data-target="#modalTarea" title="Nueva tarea" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-placement="top" data-toggle="tooltip" title="Nueva tarea" style="color:#bf1200"><span class='glyphicon glyphicon-plus'></span></a></button>
        <script>actualizaPreciosConceptos('${loopConcepto.index}','${concepto.total}');</script>
        </td>
        <td></td>
        <td></td>
        <td></td>
        <td id="totalConcepto_${loopConcepto.index}"></td>
      </tr>
      <jstl:forEach items="${concepto.tareas}" var="tarea" varStatus="loop">
      <script>actualizaPrecios('<%=var %>','${tarea.precioUnidad}','${tarea.subTotal}');</script>
      <tr>
        <td>&nbsp ${tarea.descripcion}<button onclick="editarTarea('${tarea.id}')"  data-toggle="modal" data-target="#modalTarea" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Editar" style="color:#bf1200;"><span class='glyphicon glyphicon-pencil'></span></a></button>
        <button data-toggle="tooltip" onclick="eliminarTarea('${concepto.id}','${presupuestoForm.id}','${tarea.id}')" data-placement="top" title="Eliminar" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><span class='glyphicon glyphicon-remove'></span></button>
        </td>
        <td>&nbsp ${tarea.unidades}</td>
        <td id="precioUd_<%=var %>"></td>
        <td id="subTotal_<%=var %>"></td>
        <td id="totalPres"></td>
      </tr>
      <%var=var+1;%>
      </jstl:forEach>
      
    </jstl:forEach>
     <tr>
     <td> </td>
     <td> </td>
     <td> </td>
     <td> </td>
     <td> </td>
     </tr>
      <tr id="ultimaFila">
        <td><b>*Este presupuesto no incluye el 21% de IVA.</b></td>
        <td></td>
        <td></td>
        <td id="totalPresupuestoTabla"><b>TOTAL PRESUPUESTO</b> <input type=hidden id="totalPresupuestoHidden" name="titalPresupuestoHidden" value="${totalPresupuesto}">
		</td>
        <td id="totalPresupuesto"><b><script>formateaTotalPresup();</script></b></td>
      </tr>
    </tbody>
  </table>
</div>
	
	</div>

</div>
</form:form>


	
<div class="container">
<style>
.panel-heading {
	background-color: #bf1200 !important;
	color: #f5f5f5 !important";
}
</style>
<div class="panel panel-default">
		<div class="panel-heading">
			<font style="color: white"><b>Observaciones</b><button data-toggle="modal" data-target="#modalObservaciones" title="Añadir" style="margin:-9px -16px -2px -6px;outline: none;color:white;" type="button" class="btn btn-link"><a data-placement="top" data-toggle="tooltip" title="Añadir" style="color:white"><span class='glyphicon glyphicon-plus'></span></a></button></font>
		</div>
		<div class="panel-body">
			<div class="row">
				<div class="col-md-12 col-md-offset-0">
        			<jstl:if test="${fn:length(observaciones)>0}">
					<div id="observacionesPres"><script>formateaObservaciones()</script></div>
					</jstl:if>
				</div>
			</div>
		</div>
	</div>
				
				<button type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				onclick="guardarDatosPresupuesto(${presupuestoForm.id},${cliente.id})">Guardar presupuesto
				<span class="glyphicon glyphicon-floppy-save"></span>
				</button>

				<jstl:if test="${presupuesto.solicitud ==null && presupuesto.solicitudTemporal!=0}">
				<button type="button" class="btn btn-danger" onclick="enviarPresupuestoCliente('${presupuestoForm.solicitudId}')"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				onclick="">Enviar a cliente
				<span class="glyphicon glyphicon-envelope"></span>
				</button>
				</jstl:if>
</div>

<!-- Modal observaciones-->
<div class="modal fade" id="modalObservaciones" tabindex="-1" role="dialog"
	aria-labelledby="modalObservacionesLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalObservacionesLabelEdit">Añadir observaciones</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioObservaciones" modelAttribute="presupuestoForm">
<input id="observaciones" type="hidden" value="${observaciones}" class="form-control" type="text"/>
							
					<div class="row">
						<div class="col-md-12 col-md-offset-0">
							<label for="observacion">Observación:</label>
							<div class="form-group">
									<input id="observacion" class="form-control" type="text"/>
							</div>
						</div>
					</div>

				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardarObservaciones" type="button" class="btn btn-danger"
					style="color: #fff !important; background-color: #bf1200 !important; border-color: #bf1200 !important;"
					onclick="guardarObservaciones()">Guardar</button>
			</div>
		</div>
	</div>
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
				<h4 class="modal-title" id="modalConceptoLabelEdit">Modificar Concepto</h4>
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
				<button id="guardarConcepto" type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="guardarConcepto()">Guardar</button>
					<button id="editarConcepto" type="button" class="btn btn-danger"
					style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="modificarConcepto()">Editar</button>
			</div>
		</div>
	</div>
</div>
<!-- Modal Tarea-->
<div class="modal fade" id="modalTarea" tabindex="-1" role="dialog"
	aria-labelledby="modalTareaLabel">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h4 class="modal-title" id="modalTareaLabel">Nueva tarea</h4>
			</div>
			<div class="modal-body">
				<form:form id="formularioConcepto" modelAttribute="tareaForm">
				<input type=hidden id="conceptoId" name="conceptoId" value="">
				<input type=hidden id="presupuestoId" name="presupuestoId" value="${tareaForm.presupuestoId}">
				<input type=hidden id="tareaId" name="tareaId" value="${tareaForm.tareaId}">
					<div class="form-group">
						<div class="row">
							<div class="col-md-12 col-md-offset-0">
								<form:label path="descripcion" for="descripcion">Descripcion:</form:label>
								<form:input cssClass="form-control" path="descripcion" /><br>
							</div>
						</div>
						<div class="row">
							<div id="uds" class="col-md-4 col-md-offset-0">
								<form:label path="unidades" for="unidades">Unidades:</form:label>
								<form:input cssClass="form-control numeric" path="unidades" onchange="actualizaSubtotalTarea()"/>
							</div>
							<div id="pud" class="col-md-4 col-md-offset-0">
								<form:label path="precioUnidad" for="precioUnidad">Precio unidad:</form:label>
								<form:input cssClass="form-control" onchange="actualizaSubtotalTarea()" path="precioUnidad" />
							</div>
							<div  class="col-md-4 col-md-offset-0">
								<form:label path="subTotal" for="subTotal">Subtotal:</form:label>
								<form:input cssClass="form-control" path="subTotal" disabled="true" />
							</div>
						</div>
					</div>
				</form:form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
				<button id="guardarTarea" type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="guardarTarea()">Guardar</button>
					<button id="editarTarea" type="button" class="btn btn-danger"
					style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
					onclick="modificarTarea()">Editar</button>
			</div>
		</div>
	</div>
</div>

<script>
var fin = $('#fechaFinS').val();
$('#fechaFinS').datepicker({ language: 'es', maxDate: new Date() });
$('#fechaFinS').data('datepicker');
var dp2 = $('#fechaFinS').datepicker().data('datepicker');
$('#fireDateS').on('click', function() {
dp2.show();
$('#fechaFinS').focus();
});
$("#datepickers-container").addClass("desplazar");
$('#fechaFinS').val(fin);

var fechaObra = $('#fechaObraS').val();
$('#fechaObraS').datepicker({ language: 'es' });
$('#fechaObraS').data('datepicker');
var dp = $('#fechaObraS').datepicker().data('datepicker');
$('#fireDate').on('click', function() {
dp.show();
$('#fechaObraS').focus();
});
$("#datepickers-container").addClass("desplazar");
$('#fechaObraS').val(fechaObra);


</script>



