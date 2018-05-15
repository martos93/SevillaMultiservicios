
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
<%int var=0;%>
<form:form id="formularioConcepto"  modelAttribute="presupuestoForm">
			
<div class="container">
<div class="row">
<div class="col-md-4 col-md-offset-0">
	<form:label path="titulo" for="titulo">Titulo: </form:label><form:input path="titulo" id="titulo" cssClass="form-control" />
	</div>
	<div class="col-md-1 col-md-offset-0">
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
	
</div>
<div class="row">
<div class="col-md-12 col-md-offset-0">
	<br>
	<c:choose>
    <c:when test="${presupuestoForm.aceptado}">
	<label><b>Aceptado:</b><a data-toggle="tooltip" data-placement="top" title="El presupuesto ha sido aceptado por el cliente." style="color:green;"><span style="margin-left:5px;font-size:18px;" class='glyphicon glyphicon-ok'></span></a></label>
     </c:when>    
    <c:otherwise>
    <label><b>Aceptado:</b><a data-toggle="tooltip" data-placement="top" title="El presupuesto no ha sido aceptado por el cliente." style="color:red;"><span style="margin-left:5px;font-size:18px;" class='glyphicon glyphicon-remove'></span></a></label>
    
    </c:otherwise>
</c:choose>
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
				<label>Fecha:</label> <fmt:formatDate value="${presupuestoForm.fechaInicio}" pattern="dd/MM/yyyy" />
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

				<button type="button" class="btn btn-danger"  data-toggle="modal"
				data-target="#modalConcepto"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				onclick="limpiarDatosNuevoConcepto()">Añadir concepto
				<span class="glyphicon glyphicon-pencil"></span>
				</button>
				
				<button type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				onclick="guardarDatosPresupuesto(${presupuestoForm.id},${cliente.id})">Guardar presupuesto
				<span class="glyphicon glyphicon-floppy-save"></span>
				</button>
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

