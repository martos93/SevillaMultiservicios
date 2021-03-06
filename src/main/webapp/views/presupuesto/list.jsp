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
crearTablaBusqueda('presupuesto');
$("#cliente_filter").html("<label style='float:left'>Buscar:</label><input type='search' aria-controls='cliente'>");
</script>
<style>
.dataTables_filter input:focus {
    outline: none !important;
    border-color: #FF0000;
    box-shadow: 0 0 10px #719ECE;

}
.dataTables_filter input {
   display: block;
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
	<h4>
		Mostrando los presupuestos del usuario: <b>${cliente.userAccount.username}</b>
	</h4>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
			<button type="button" class="btn btn-danger" data-toggle="modal"
				data-target="#modalPresupuesto"
				style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				onclick="limpiarDatosCrearPresupuesto()">
				<span class="glyphicon glyphicon-plus"></span> Nuevo Presupuesto
			</button>
		</div>
	</div>
	<br>


	<display:table name="presupuestos" id="presupuesto"
		requestURI="${requestURI}" class="table">

		<display:column property="codigo" title="Código" />

		<display:column property="titulo" title="Titulo" />

		<display:column title="Iniciado">
			<fmt:formatDate value="${presupuesto.fechaInicio}"
				pattern="dd/MM/yyyy HH:mm" />
		</display:column>

		<display:column property="localidad" title="Localidad" />

		<display:column property="provincia" title="Provincia" />

		<display:column>
		<jstl:if test="${presupuesto.solicitud!=null}">
	
			 <button onclick="verPresupuestoCliente('${presupuesto.id}')"style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Ver" style="color:#bf1200;"><span class='glyphicon glyphicon-search'></span></a></button>
      
		
		
       </jstl:if>
       
       
       
       <jstl:if test="${presupuesto.solicitud==null}">
         <jstl:if test="${presupuesto.factura==null && presupuesto.albaran == null}">
       <button onclick="modificarPresupuesto('${presupuesto.id}')"style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Editar" style="color:#bf1200;"><span class='glyphicon glyphicon-pencil'></span></a></button>
       	 </jstl:if>
       	  <jstl:if test="${presupuesto.factura!=null || presupuesto.albaran!=null}">
       	  	 <button onclick="verPresupuestoCliente('${presupuesto.id}')"style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Ver" style="color:#bf1200;"><span class='glyphicon glyphicon-search'></span></a></button>
       	  </jstl:if>
       </jstl:if>
       <button onclick="resumenFinanciero('${presupuesto.id}')" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Resumen financiero" style="color:#bf1200;"><span class='glyphicon glyphicon-piggy-bank'></span></a></button>
         <button onclick="verGastos('${presupuesto.id}')" style="margin:-9px -16px -2px -6px;outline: none;color:#bf1200;" type="button" class="btn btn-link"><a data-toggle="tooltip" data-placement="top" title="Hoja de gastos" style="color:#bf1200;"><span class='glyphicon glyphicon-eur'></span></a></button>
       
       	
		</display:column>




	</display:table>
</div>

<!-- Modal presupuesto-->
<div class="modal fade" id="modalPresupuesto" tabindex="-1" role="dialog"
	aria-labelledby="modalPresupuestoLabel">
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
					<div class="form-group">
						<div class="row">
							<div class="col-md-8 col-md-offset-0">
								<form:label path="titulo" for="titulo">Titulo:</form:label>
								<form:input cssClass="form-control" path="titulo" />
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
<form:label path="tipoTrabajo" for="codigoPostal">Tipo de trabajo: </form:label><form:select path="tipoTrabajo" cssClass="form-control" items="${tiposTrabajo}"  itemLabel="descripcion" itemValue="id">
		
	</form:select>
</div>
					</div>
						
						
					</div>
				</form:form>
			</div>
			<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
			<button id="guardarConcepto" type="button" class="btn btn-danger"
			style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				
				onclick="crearPresupuesto('${cliente.id}')">Crear</button>
		</div>
		</div>
		
	</div>
</div>

