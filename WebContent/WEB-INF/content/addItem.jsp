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
 
<s:form action="admin">
 
      <s:textfield name="itemBean.id"  label="Id"  />
      <s:textfield name="itemBean.author" label="Author" />
      <s:textfield name="itemBean.thumbnailUrl" label="ThumbnailUrl" />
      <s:textfield name="itemBean.title"  label ="Title"/>  
      <s:textfield name="itemBean.location"  label="Location"  />
       
      <s:submit />
       
</s:form> 
  
</body>
</html>