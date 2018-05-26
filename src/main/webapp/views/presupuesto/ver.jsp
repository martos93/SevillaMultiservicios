
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
	<form:label path="titulo" for="titulo">Titulo: </form:label><form:input disabled="true" path="titulo" id="titulo" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="codigo" for="codigo">Código: </form:label><form:input disabled="true" path="codigo" id="codigo" cssClass="form-control" />
	</div>
	<div class="col-md-3 col-md-offset-0">
	<form:label path="direccionObra" for="direccionObra">Dirección obra: </form:label><form:input disabled="true" path="direccionObra" id="direccionObra" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="localidad" for="localidad">Localidad: </form:label><form:input disabled="true" path="localidad" id="localidad" cssClass="form-control" />
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="provincia" for="provincia">Provincia: </form:label><form:input disabled="true" path="provincia" id="provincia" cssClass="form-control" />
	</div>
</div><br>
<div class="row">
<div class="col-md-3 col-md-offset-0">
<form:label path="codigoPostal" for="codigoPostal">Código postal: </form:label><form:input disabled="true" path="codigoPostal" id="codigoPostal" cssClass="form-control" />
</div>
<div class="col-md-3 col-md-offset-0">
<input type="hidden" id="tipoTrabajoId" value="${tipoTrabajoId}"/>
<form:label path="tipoTrabajo" for="codigoPostal">Tipo de trabajo: </form:label><form:select disabled="true" path="tipoTrabajo" cssClass="form-control" items="${tiposTrabajo}"  itemLabel="descripcion" itemValue="id">
		
	</form:select>
</div>
<div class="col-md-2 col-md-offset-0">
	<form:label path="fechaInicioS" for="fechaInicioS">Fecha Presupuesto: </form:label><form:input disabled="true" path="fechaInicioS" id="fechaInicioS" cssClass="form-control" style="cursor:default"/>
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="fechaObraS" for="fechaObraS">Fecha Inicio Obra: </form:label>
	
<div class="form-group" id="fechaSpan">
								
								<form:input disabled="true" path="fechaObraS" id="fechaObraS" cssClass="form-control" style="cursor: default;" data-position="right top" />
							</div>
	</div>
	<div class="col-md-2 col-md-offset-0">
	<form:label path="fechaFinS" for="fechaFinS">Fecha Fin Obra: </form:label>
	 <div class="form-group" id="fechaSpanS">
								
								<form:input disabled="true" path="fechaFinS" id="fechaFinS" cssClass="form-control" style="cursor: default;" data-position="right top" />
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
        <td>&nbsp ${tarea.descripcion}
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
<style>
.panel-heading {
	background-color: #bf1200 !important;
	color: #f5f5f5 !important";
}
</style>
<div class="panel panel-default">
		<div class="panel-heading">
			<font style="color: white"><b>Observaciones</b></font>
		</div>
		<div class="panel-body">
		<input id="observaciones" type="hidden" value="${observaciones}" class="form-control" type="text"/>
		
			<div class="row">
				<div class="col-md-12 col-md-offset-0">
        			<jstl:if test="${fn:length(observaciones)>0}">
					<div id="observacionesPres"><script>formateaObservacionesVer()</script></div>
					</jstl:if>
				</div>
			</div>
		</div>
	</div>
	
	<jstl:if test="${presupuestoForm.aceptado ==null}">
	
	<button type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: green !important;border-color: green !important;"
				onclick="aceptarRechazarPresupuesto(${presupuestoForm.id},1)">Aceptar presupuesto
				<span class="glyphicon glyphicon-ok"></span>
				</button>
				
				<button type="button" class="btn btn-danger"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				onclick="aceptarRechazarPresupuesto(${presupuestoForm.id},0)">Rechazar presupuesto
				<span class="glyphicon glyphicon-remove"></span>
				</button>
</jstl:if>
</div>


</form:form>











