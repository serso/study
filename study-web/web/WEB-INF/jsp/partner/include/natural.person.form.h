<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.solovyev.study.controllers.partner.PartnerCreateEditController" %>

<%--@elvariable id="editPartner" type="java.lang.Boolean"--%>


<tr class="tr-form">
	<td class="td-form">
		<form:label path="firstName">First Name</form:label>
	</td>
	<td class="td-form">
		<form:input path="firstName" cssClass="form-input" cssErrorClass="error form-input"/>
	</td>
	<td class="td-form">
		&nbsp;<form:errors path="firstName"/>
	</td>
</tr>
<tr class="tr-form">
	<td class="td-form">
		<form:label path="lastName">Last Name</form:label>
	</td>
	<td class="td-form">
		<form:input path="lastName" cssClass="form-input" cssErrorClass="error form-input"/>
	</td>
	<td class="td-form">
		&nbsp;<form:errors path="lastName"/>
	</td>
</tr>
<tr class="tr-form">
	<td class="td-form">
		<form:label path="birthdate">Birth Date</form:label>
	</td>
	<td class="td-form">
		<form:input path="birthdate" cssClass="form-input" cssErrorClass="error form-input"/>
	</td>
	<td class="td-form">
		&nbsp;<form:errors path="birthdate"/>
	</td>
</tr>
<tr class="tr-form">
	<td class="td-form">
		<form:label path="gender">Gender</form:label>
	</td>
	<td class="td-form">
		<form:select path="gender" items="<%=Gender.values()%>" cssClass="form-select" cssErrorClass="error form-select"/>
	</td>
	<td>
		&nbsp;<form:errors path="gender"/>
	</td>
</tr>