<%@ page import="org.solovyev.study.model.Partner" %>
<%@ page import="org.solovyev.study.model.PartnerRole" %>
<%@ page import="org.solovyev.study.controllers.school.SchoolDetailsController" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: May 8, 2010
  Time: 7:11:38 PM
--%>

<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>Edit School Details</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<%--@elvariable id="school" type="org.solovyev.study.model.LegalPerson"--%>
<%--@elvariable id="schoolDetails" type="org.solovyev.study.model.SchoolDetails"--%>

<form:form commandName="<%=SchoolDetailsController.SCHOOL_DETAILS_MODEL%>">
	<table class="table-form">
		<tr class="tr-form">
			<td class="th-form">
				School name
			</td>
			<td class="td-form">
					${school.companyName}
			</td>
			<td class="td-form">
				&nbsp;
			</td>
		</tr>
		<tr class="tr-form">
			<td class="th-form">
				<form:label path="schoolNumber">School number</form:label>
			</td>
			<td class="td-form">
				<form:input path="schoolNumber" cssClass="form-input" cssErrorClass="error form-input"/>
			</td>
			<td class="td-form">
				&nbsp;<form:errors path="schoolNumber"/>
			</td>
		</tr>
		<tr class="tr-form">
			<td class="td-form">
				&nbsp;
				<%@ include file="/WEB-INF/include/back_button.h" %>
			</td>
			<td class="td-form">
				<input type="submit" value="Save" id="saveButton" onclick="submitForm('saveButton', '<%=SchoolDetailsController.SCHOOL_DETAILS_MODEL%>', '/school/details/save.do', 'POST');"/>
			</td>
			<td class="td-form">
				&nbsp;
			</td>
		</tr>
	</table>
</form:form>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>