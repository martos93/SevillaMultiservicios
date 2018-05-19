<%--
 * layout.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="shortcut icon" href="favicon.ico"/> 

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="styles/bootstrap.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="scripts/toastr.js"></script>
<script src="scripts/alertas.js"></script>
<script src="scripts/modales.js"></script>
<script src="scripts/ajaxTarea.js"></script>
<script src="scripts/ajaxConcepto.js"></script>
<script src="scripts/ajaxPresupuesto.js"></script>
<script src="scripts/ajaxEmpleado.js"></script>
<script src="scripts/ajaxGasto.js"></script>
<script src="scripts/ajaxCobro.js"></script>

<script src="http://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>

<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen" type="text/css" />
<link rel="stylesheet" href="styles/displaytag.css" type="text/css">
<link rel="stylesheet" href="styles/toastr.css" type="text/css">
<link rel="stylesheet" href="styles/jquery.dataTables.min.css" type="text/css">


<script>
$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})
	$('.dropdown-toggle').dropdown();
	</script>
<title><tiles:insertAttribute name="title" ignore="true" /></title>

</head>

<body>
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
	<div>
		<tiles:insertAttribute name="header" />
	</div>
	<div>
		<h1>
			<tiles:insertAttribute name="title" />
		</h1>
		<tiles:insertAttribute name="body" />	
	</div>
	<div>
		<tiles:insertAttribute name="footer" />
	</div>

</body>
</html>