<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.solovyev.study.controllers.partner.PartnerCreateEditController" %>
<%@ page import="org.solovyev.study.model.Gender" %>

<%--@elvariable id="partner" type="org.solovyev.study.model.Partner"--%>
<%--@elvariable id="editPartner" type="java.lang.Boolean"--%>

<tr class="tr-form">
	<td class="td-form">
		<form:label path="companyName">Company Name</form:label>
	</td>
	<td class="td-form">
		<form:input path="companyName" cssClass="form-input" cssErrorClass="error form-input"/>
	</td>
	<td class="td-form">
		&nbsp;<form:errors path="companyName"/>
	</td>
</tr>
<tr class="tr-form">
	<td class="td-form">
		<form:label path="incorporationDate">Incorporation Date</form:label>
	</td>
	<td class="td-form">
		<form:input path="incorporationDate" cssClass="form-input" cssErrorClass="error form-input"/>
	</td>
	<td class="td-form">
		&nbsp;<form:errors path="incorporationDate"/>
	</td>
</tr>