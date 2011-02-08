<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ page import="org.solovyev.common.definitions.MessageType" %>
<%@ page import="org.solovyev.study.resources.ApplicationContextProvider" %>
<%@ page import="org.solovyev.study.resources.MessageCodes" %>
<%@ page import="org.solovyev.study.resources.MessageCollector" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:set var="applicationUser" value="<%=ApplicationContextProvider.getApplicationUser()%>"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>

<%-- print out all session attributes --%>
<%--<%
		  if (pageContext.getSession() != null) {
			  Enumeration enumeration = pageContext.getSession().getAttributeNames();
			  String el;
			  while (enumeration.hasMoreElements()) {
				  el = (String) enumeration.nextElement();
	  %>
	  <%=el + "->" + pageContext.getSession().getAttribute(el).toString() + "(" + pageContext.getSession().getAttribute(el).getClass() + ")</br>"%>
	  <%
			  }
		  }
	  %>--%>