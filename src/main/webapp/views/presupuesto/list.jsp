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
<script>
	crearTabla('presupuesto');
</script>

<div class="container">
<h4>Mostrando los presupuestos del usuario: <b>${cliente.userAccount.username}</b></h4>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
			<button type="button" class="btn btn-danger" 
				onclick="crearPresupuesto('${cliente.id}')">
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
		 <fmt:formatDate value="${presupuesto.fechaInicio}" pattern="dd-MM-yyyy" />
		</display:column>

		<display:column property="localidad" title="Localidad" />

		<display:column property="provincia" title="Provincia" />
		
		<display:column>
		<a class="btn btn-secondary" data-toggle="tooltip"
				data-placement="top" title="Modificar">
				<button type="button" class="btn btn-danger"
				onclick="modificarPresupuesto('${presupuesto.id}')">
				<span class="glyphicon glyphicon-pencil"></span>
				</button></a>
		</display:column>

		


	</display:table>
</div>

