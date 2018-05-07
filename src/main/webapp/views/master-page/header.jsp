<%--
 * header.jsp
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#">Sevilla Multiservicios</a>
    </div>
    <ul class="nav navbar-nav">
	  <security:authorize access="hasRole('GESTOR')">
      <li class="dropdown">
	     <a class="dropdown-toggle" data-toggle="dropdown" href="#">Presupuestos
        <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="gestor/cliente/listAll.do">Ver presupuestos</a></li>
			<li role="separator" class="divider"></li>
            <li><a href="#">Solicitudes de presupuestos</a></li>
            <li><a href="#">Nuevo presupuesto</a></li>
        </ul>
	  </li>
      <li><a href="gestor/empleado/listAll.do">Empleados</a></li>
	  <li><a href="#">Control Económico</a></li>
	   </security:authorize>
    </ul>
	<security:authorize access="isAnonymous()">
    <ul class="nav navbar-nav navbar-right">
      <li><a href="security/login.do"><span class="glyphicon glyphicon-user"></span> Entrar</a></li>
      <li><a href="security/register.do"><span class="glyphicon glyphicon-log-in"></span> Registro</a></li>
    </ul>
	</security:authorize>
	
	<security:authorize access="isAuthenticated()">
	  <ul class="nav navbar-nav navbar-right">
       <li><a href="#">(<security:authentication property="principal.username" />)</a></li>
      <li><a href="j_spring_security_logout"><span class="glyphicon glyphicon-log-in"></span> Salir</a></li>
    </ul>
	</security:authorize>
  </div>
</nav>

<div>
	<img src="images/logo-sms.jpg" class="img-responsive center-block" height="266px" width="496px" alt="Sevilla Multiservicios" />
</div>
