<%@ page import="org.solovyev.study.model.User" %>
<%@ page import="org.solovyev.study.model.UserRole" %>
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
	<c:set var="onload" value="onload=\"document.login_form.${usernameInputName}.focus();\""/>
</c:if>
<body ${onload}>
<%@ include file="/WEB-INF/include/content_start.h" %>

<%
	try {
		// The Servlet spec guarantees this attribute will be available
		Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

		if (exception != null) {
			if (exception instanceof ServletException) {
				// It's a ServletException: we should extract the root cause
				ServletException sex = (ServletException) exception;
				Throwable rootCause = sex.getRootCause();
				if (rootCause == null)
					rootCause = sex;
				out.println("** Root cause is: " + rootCause.getMessage());
				rootCause.printStackTrace(new java.io.PrintWriter(out));
			} else {
				// It's not a ServletException, so we'll just show it
				exception.printStackTrace(new java.io.PrintWriter(out));
			}
		}

		User applicationUser = (User) pageContext.getSession().getAttribute("applicationUser");

		if (applicationUser != null && applicationUser.hasAnyRoles(UserRole.developer)) {
			// Display cookies
			out.println("\nCookies:\n");
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					out.println(cookies[i].getName() + "=[" + cookies[i].getValue() + "]");
				}
			}
		}

	} catch (Exception ex) {
		ex.printStackTrace(new java.io.PrintWriter(out));
	}
%>

<%@ include file="/WEB-INF/include/back_button.h" %>

<%@ include file="/WEB-INF/include/message_box.h" %>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>