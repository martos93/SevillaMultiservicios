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

<script src="scripts/ajaxIVA.js"></script>

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
		
		
		
<form:form id="formularioIVA" modelAttribute="ivaForm">
 
<div class="input-group">
 <form:input path="ivaID" type="hidden" name="ivaID" value=""/>
  <form:input path="porcentaje" type="number" cssClass="form-control" aria-describedby="basic-addon2"/>
  <span class="input-group-addon" id="basic-addon2">%</span>
</div>					
</form:form>
</div>
	</div>
	<br>
	<div class="row">
		<div class="col-md-2 col-md-offset-5">
<button id="guardar" type="button" class="btn btn-danger"
style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;"
				
					onclick="guardarIVA()">Guardar</button>
		
</div></div>
		
</div>
