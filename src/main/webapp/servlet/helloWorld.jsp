<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://xmlns.jcp.org/jsp/jstl/core" %>	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>JEETrain Hello World (JSP)</title>
</head>
<body>
	<h1>Hello World of JSPs!</h1>
	<form id="helloWorldForm" action="/jeetrain-presentation/helloServlet" method="post">
		<label for="name">Please enter your name:</label> 
		<input name="name" type="text" /> 
		<input name="ok" type="submit" value="OK" />
	</form>
	<c:if test="${model != null}">
		<p>Hello <c:out value="${model.name}"/></p>
		<p>Welcome to the world of Java Server Pages</p>
	</c:if>
</body>
</html>