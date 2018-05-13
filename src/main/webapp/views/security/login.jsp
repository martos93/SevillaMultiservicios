
<%--
 * login.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${showError == true}">
	<script>
		alertaError("La contraseña y el usuario no coinciden");
	</script>
</jstl:if>


<div class="container">
<div class="row">
 <div class="col-md-2 col-md-offset-5">
	<form:form action="j_spring_security_check"
		modelAttribute="credentials" class="form-signin">
		
		<div class="form-group">
			<label for="username">Usuario:</label>
			<form:input path="username" type="text" class="form-control"
				id="username" placeholder="Introduce usuario" name="username" />
		</div>

		<div class="form-group">
			<label for="pwd">Contraseña:</label>
			<form:input path="password" type="password" class="form-control btn-signin"
				id="pwd" placeholder="Introduce contraseña" name="pwd" />
		</div>
		<center><button type="submit" class="btn btn-danger" style=" color: #fff !important;background-color: #bf1200 !important;border-color: #bf1200 !important;">Entrar</button></center>
	</form:form>
	</div>
	</div>
</div>