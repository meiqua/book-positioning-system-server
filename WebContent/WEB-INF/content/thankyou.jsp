<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Registration Successful</title>
</head>
<body>
<h3>Thank you for registering .</h3>

<p>Your registration information: <s:property value="itemBean" /> </p>
 
<p><a href="<%=request.getContextPath()%>/struts2/addItem">Please register</a> for more register.</p>

<p><a href="<%=request.getContextPath()%>/struts2/listAll">list all items</a> for listing All.</p>
 
</body>
</html>