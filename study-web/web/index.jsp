<%@ page import="org.springframework.security.web.WebAttributes" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: Sep 27, 2009
  Time: 1:01:43 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>Home</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>

<%-- setting page context variables --%>

<c:set var="passwordInputName" value="<%=UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY%>"/>
<%--@elvariable id="passwordInputName" type="java.lang.String"--%>

<c:set var="usernameInputName" value="<%=UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY%>"/>
<%--@elvariable id="usernameInputName" type="java.lang.String"--%>

<c:set var="username_attr_name" value="<%=UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY%>"/>
<c:set var="lastUsernameValue" value="${sessionScope[username_attr_name]}"/>
<%--@elvariable id="lastUsernameValue" type="java.lang.String"--%>

<c:if test="${applicationUser == null}">
	<c:set var="onload" value="onload='document.login_form.${usernameInputName}.focus();'"/>
</c:if>
<body ${onload}>
<%@ include file="/WEB-INF/include/content_start.h" %>

<%-- if user is not logged in and login failed then we have to print a message --%>
<c:if test="${applicationUser == null && paramValues['failed'] != null}">
	<%
		MessageCollector messageCollector = (MessageCollector) pageContext.getAttribute("messageCollector");
		if (messageCollector == null) {
			//message collector might be null if session is not created yet
			messageCollector = new MessageCollector();
			pageContext.setAttribute("messageCollector", messageCollector);
		}
		//getting login exception
		Object exception = pageContext.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (exception instanceof AuthenticationException) {
			//adding exception
			messageCollector.addMessage(MessageCodes.msg0010.name(), MessageType.error, ((AuthenticationException) exception).getMessage());
		}
	%>
</c:if>

<%@ include file="/WEB-INF/include/message_box.h" %>

<c:if test="${applicationUser == null}">
	<form name="login_form" action="/login.do" method="POST">
		<table class="table-form">
			<tr class="tr-form">
				<td class="td-form"><label for="${usernameInputName}">User:</label></td>
				<td class="td-form"><input type='text' name='${usernameInputName}' id='${usernameInputName}'
				                           value='${lastUsernameValue}'></td>
			</tr>
			<tr class="tr-form">
				<td class="td-form"><label for="${passwordInputName}">Password:</label></td>
				<td class="td-form"><input type='password' name='${passwordInputName}' id='${passwordInputName}'/></td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">&nbsp;</td>
				<td class="td-form"><input name="submit" type="submit" value="Log In"/></td>
			</tr>
		</table>
	</form>
</c:if>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>