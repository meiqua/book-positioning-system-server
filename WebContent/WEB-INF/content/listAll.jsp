<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>all items</title>
</head>
<body>
<h3>all items </h3>
<table width="640" border="1">
	<s:iterator value="items" var="b">
		<tr>
			<td><s:property value="id"/></td>
			<td><s:property value="title"/></td>
			<td><s:property value="author"/></td>
			<td><s:property value="thumbnailUrl"/></td>
			<td><s:property value="location"/></td>
			<td><s:property value="state"/></td>
			<td><a href="${pageContext.request.contextPath}/struts2/deleteItem?id=${b.id}">delete</a></td>
		</tr>
	</s:iterator>
</table>
</body>
</html>
