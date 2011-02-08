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
			<a href="/">Home</a>
			<c:if test="${applicationUser != null}">
				| <a href="/logout.do">Log Out</a>
			</c:if>
		</td>
	</tr>
	<c:if test="${applicationUser != null}">
		<%--@elvariable id="applicationUser" type="org.solovyev.study.model.User"--%>
	<tr class="tr-content">
		<td class="td-content">
			<s:url var="myProfileUrl" value="/user/view.do">
				<s:param name="userId" value="${applicationUser.id}"/>
			</s:url>
			<a href="${myProfileUrl}">My Profile</a>
			<sec:authorize access="hasAnyRole('developer','administrator','school_employee')">
				| <a href="/user/management.do">Users</a>
				| <a href="/partner/management.do">Partners</a>
			</sec:authorize>
			<sec:authorize access="hasRole('developer')">
				| <a href="/test.action.do">Test page</a>
			</sec:authorize>
		</td>
	</tr>
	</c:if>
	<tr class="tr-content">
		<td class="td-content">