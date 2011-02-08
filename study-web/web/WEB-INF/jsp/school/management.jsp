<%@ page import="org.solovyev.study.model.Partner" %>
<%@ page import="org.solovyev.study.model.PartnerRole" %>
<%@ page import="org.solovyev.study.resources.Config" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: May 7, 2010
  Time: 10:32:09 PM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>School</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>
<%--@elvariable id="school" type="org.solovyev.study.model.LegalPerson"--%>
<c:set var="schoolDetails" value="<%=((Partner)pageContext.getSession().getAttribute("school")).getDetails().get(PartnerRole.school)%>"/>
<%--@elvariable id="schoolDetails" type="org.solovyev.study.model.SchoolDetails"--%>

<table class="table-form">
	<tr class="tr-form">
		<td class="th-form">
			School name
		</td>
		<td class="td-form">
			${school.companyName}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			School number
		</td>
		<td class="td-form">
			${schoolDetails.schoolNumber}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			Incorporation date
		</td>
		<td class="td-form">
			<fmt:formatDate value="${school.incorporationDate}" pattern="<%=Config.DATE_PATTERN%>"/>
		</td>
	</tr>
	<tr class="tr-form">
		<td class="td-form">
			&nbsp;
			<%@ include file="/WEB-INF/include/back_button.h" %>
		</td>
		<td class="td-form">
			<input type="button" value="Edit details" id="editButton" onclick="pressButtonWithParams('editButton', '/school/details/edit.do', 'POST', {'backRouteAction': '/school/back.to.management.do'});"/>
		</td>
	</tr>
</table>

<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>