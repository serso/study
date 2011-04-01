<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<table cellpadding="0" cellspacing="0" border="0" width="100%" class="table-content">
	<tr class="tr-content">
		<td class="td-content">
			<%-- top menu --%>
			<a href="<c:url value="/"/>">Home</a>
			<c:if test="${applicationUser != null}">
				| <a href="<c:url value="/logout.do"/>">Log Out</a>
			</c:if>
		</td>
	</tr>
	<c:if test="${applicationUser != null}">
		<%--@elvariable id="applicationUser" type="org.solovyev.study.model.User"--%>
	<tr class="tr-content">
		<td class="td-content">
			<c:url var="myProfileUrl" value="/user/view.do">
				<c:param name="userId" value="${applicationUser.id}"/>
			</c:url>
			<a href="${myProfileUrl}">My Profile</a>
			<sec:authorize access="hasAnyRole('developer','administrator','school_employee')">
				| <a href="<c:url value="/user/management.do"/>">Users</a>
				| <a href="<c:url value="/partner/management.do"/>">Partners</a>
			</sec:authorize>
			<sec:authorize access="hasRole('developer')">
				| <a href="<c:url value="/test.action.do"/>">Test page</a>
			</sec:authorize>
		</td>
	</tr>
	</c:if>
	<tr class="tr-content">
		<td class="td-content">