<%@ page import="org.solovyev.study.model.Gender" %>
<%@ page import="org.solovyev.study.model.partner.Partner" %>
<%@ page import="org.solovyev.study.model.partner.PartnerRole" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: May 31, 2010
  Time: 9:59:58 PM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>View user</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<%--@elvariable id="partner" type="org.solovyev.study.model.partner.Partner"--%>
<jsp:useBean id="partner" type="org.solovyev.study.model.partner.Partner" beanName="partner" scope="request"/>

<table class="table-form">
	<c:choose>
		<c:when test="${partner.naturalPerson}">
			<%--natural person case--%>
			<%@ include file="include/natural.person.view.h" %>
		</c:when>
		<c:when test="<%=partner.isActsAs(PartnerRole.school)%>">
			<%--legal person case--%>
			<%@ include file="include/school.view.h" %>
		</c:when>
	</c:choose>
	<%@ include file="include/partner.main.address.h" %>
	<tr>
		<td>
			<%@ include file="/WEB-INF/include/back_button.h" %>
		</td>
		<td class="td-form">
			<c:url var="editUrl" value="/partner/edit.do">
				<c:param name="partnerId" value="${partner.id}"/>
			</c:url>
			<c:url var="backRouteUrl" value="/partner/back.to.view.do">
				<c:param name="partnerId" value="${partner.id}"/>
			</c:url>
			<input id="editButton" type="button" value="Edit" onclick="pressButtonWithParams('editButton','${editUrl}', 'POST', {'backRouteAction':'${backRouteUrl}'});"/>
		</td>
	</tr>
</table>

</body>
</html>