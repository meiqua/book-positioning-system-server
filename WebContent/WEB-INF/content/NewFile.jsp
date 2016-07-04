<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Register</title>
</head>
<body>
<h3>Register by completing this form.</h3>
 
<s:form action="/struts2/dispatch">
 
      <s:textfield name="key"  label="query"  />
      <s:textfield name="content" label="%E6%96%B9%E6%A1%88" />
       
      <s:submit />
       
</s:form> 
  
</body>
</html>