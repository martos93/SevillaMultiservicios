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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<script src="scripts/ajaxCliente.js"></script>
<script src="scripts/ajaxPresupuesto.js"></script>
<script src="scripts/ajaxGasto.js"></script>
<link href="styles/datepicker.css" rel="stylesheet" type="text/css">
<script src="scripts/datepicker/datepicker.min.js"></script>

<script src="scripts/datepicker/i18n/datepicker.es.js"></script>

<style>
.panel-heading{
background-color: #bf1200 !important;
color: #f5f5f5 !important";
}
</style>

<script>
	crearTabla('cobro');
</script>

<br>
<div class="container">
<div class="row">
	<div class="col-md-6 col-md-offset-0">
		<img src="images/logo-sms.jpg" class="img-responsive center-block"
			height="250px" width="250px" alt="Sevilla Multiservicios" />
	</div>
	<div class="col-md-6 col-md-offset-0"><br>
	<div class="panel panel-default">
	<div class="panel-heading"><b><font style="color:white">Resumen financiero del presupuesto: <small><b>${codigoPresupuesto}</b></small></font>
		</b></div>
		<div class="panel-body">
		<div class="row">
		<div class="col-md-6 col-md-offset-0">
			<label>Fecha de presupuesto:</label>
			<fmt:formatDate value="${presupuestoForm.fechaInicio}"
				pattern="dd/MM/yyyy" />
</div>
		</div>
		<div class="row">
		<div class="col-md-6 col-md-offset-0">
			<label>Fecha inicio de obra:</label>
			<fmt:formatDate value="${presupuestoForm.fechaObra}"
				pattern="dd/MM/yyyy" />
		</div></div>
		<div class="row">
		<div class="col-md-6 col-md-offset-0">
			<label>Fecha fin de obra:</label>
			<fmt:formatDate value="${presupuestoForm.fechaFin}"
				pattern="dd/MM/yyyy" />
		</div></div>
	</div>
	</div>
	</div>
</div>

<div class="panel panel-default">
<div class="panel-heading"><b><font style="color:white">Datos del cliente</b></font></div>
<div class="panel-body">
<div class="row">
<div class="col-md-5 col-md-offset-0">
<label>Cliente:</label> ${cliente.nombre} ${cliente.apellidos}
	</div>
</div>
<div class="row">
<div class="col-md-4 col-md-offset-0">
<label>CIF / DNI:</label> ${cliente.identificacion}
	</div>
	<div class="col-md-4 col-md-offset-0">
<label>Email:</label> ${cliente.email}
	</div>
	<div class="col-md-4 col-md-offset-0">
<label>Telefono:</label> ${cliente.email}
	</div>
</div></div>
</div>

<div class="panel panel-default">
<div class="panel-heading"><font style="color:white"><b>Datos del trabajo</b></font></div>
<div class="panel-body">
<div class="row">
<div class="col-md-5 col-md-offset-0">
<label>Dirección:</label> ${presupuestoForm.direccionObra}
	</div>
</div>
<div class="row">
<div class="col-md-4 col-md-offset-0">
<label>Localidad:</label> ${presupuestoForm.localidad}
	</div>
	<div class="col-md-4 col-md-offset-0">
<label>Provincia:</label> ${presupuestoForm.provincia}
	</div>
	<div class="col-md-4 col-md-offset-0">
<label>C.P.:</label> 
	</div>
</div>
<div class="row">
<div class="col-md-12 col-md-offset-0">
<label>Tipo de trabajo:</label> 
	</div>
</div></div></div>

<div class="panel panel-default">
<div class="panel-heading"><font style="color:white"><b>Personal</b></font></div>
<div class="panel-body">
<div class="row">
<div class="col-md-12 col-md-offset-0">
<label>Dirección de obra:</label>
<div id="personalObra"></div>
	</div>
</div>
<div class="row">
<div class="col-md-12 col-md-offset-0">
<label>Empleados:</label><br>
<ul>
<jstl:forEach items="${presupuesto.empleados}" var="empleado">
<li>${empleado.nombre} ${empleado.apellidos}</li>
</jstl:forEach>
</ul>
	</div>
</div>
</div></div>

<div class="panel panel-default">
<div class="panel-heading"><font style="color:white"><b>Datos económicos</b></font></div>
<div class="panel-body">
<div class="row">
<div class="col-md-4 col-md-offset-0">
<label>Presupuestado:</label> ${presupuestado}
</div>
<div class="col-md-4 col-md-offset-0">
<label>Añadidos:</label> ${addFactura}
</div>
<div class="col-md-4 col-md-offset-0">
<label>Margen de maniobra:</label> 

<c:choose>
         <c:when test = "${margenNegativo}">
            <b><font color="red">${margenManiobra}</font></b>
         </c:when>
         <c:otherwise>
            ${margenManiobra}
         </c:otherwise>
      </c:choose>

</div>
</div>
<div class="row">
<div class="col-md-4 col-md-offset-0">
<label>Mano de obra:</label> ${manoObra}
</div>
<div class="col-md-4 col-md-offset-0">
<label>Materiales:</label> ${material}
</div>
<div class="col-md-4 col-md-offset-0">
<label>Subcontrataciones:</label> ${subCont}
</div>
</div>
</div></div>

</div>