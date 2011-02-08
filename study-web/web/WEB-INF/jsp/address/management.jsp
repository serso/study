<%@ page import="org.solovyev.study.controllers.address.AddressCreateEditController" %>
<%@ page import="org.solovyev.study.model.AddressType" %>

<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>Address management</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<%--@elvariable id="addressContainer" type="org.solovyev.study.model.AddressContainer"--%>

<form:form commandName="<%=AddressCreateEditController.ADDRESS_CONTAINER_MODEL%>" action="/address/save.do" method="POST">
	<table class="table-form">
		<c:forEach items="${addressContainer.addresses}" var="address" varStatus="addressesStatus">
			<%--@elvariable id="address" type="org.solovyev.study.model.Address"--%>

			<s:url var="deleteAddressUrl" value="/address/remove.do">
				<s:param name="index" value="${addressesStatus.index}"/>
			</s:url>

			<tr class="tr-form">
				<td class="th-form" colspan="4">
			   	    &nbsp;
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form" rowspan="7">
					<form:radiobutton path="mainAddressIndex" value="${addressesStatus.index}" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   Address type
				</td>
				<td class="td-form">
					<form:select path="addresses[${addressesStatus.index}].addressType" items="<%=AddressType.values()%>"  cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].addressType"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   Country
				</td>
				<td class="td-form">
					<form:input path="addresses[${addressesStatus.index}].country" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].country"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   City
				</td>
				<td class="td-form">
					<form:input path="addresses[${addressesStatus.index}].city" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].city"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   Street
				</td>
				<td class="td-form">
					<form:input path="addresses[${addressesStatus.index}].street" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].street"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   House No.
				</td>
				<td class="td-form">
					<form:input path="addresses[${addressesStatus.index}].house" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].house"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   Apartment No.
				</td>
				<td class="td-form">
					<form:input path="addresses[${addressesStatus.index}].apartment" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].apartment"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   Postal Code
				</td>
				<td class="td-form">
					<form:input path="addresses[${addressesStatus.index}].postalCode" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
				<td class="td-form">
				   	<form:errors path="addresses[${addressesStatus.index}].postalCode"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
				   &nbsp;
				</td>
				<td class="td-form">
					<a onclick="submitForm(null, '<%=AddressCreateEditController.ADDRESS_CONTAINER_MODEL%>', '${deleteAddressUrl}', 'POST')">
						Remove
					</a>
				</td>
				<td class="td-form">
					&nbsp;
				</td>
			</tr>
		</c:forEach>
		<tr class="tr-form">
			<td class="td-form">
				&nbsp;
			</td>
			<td class="td-form">
				<input type="button" value="Add address" id="addAddressButton" onclick="submitForm('addAddressButton', '<%=AddressCreateEditController.ADDRESS_CONTAINER_MODEL%>', '/address/add.do', 'POST')">
			</td>
			<td class="td-form">
				&nbsp;
			</td>
		</tr>
		<tr class="tr-form">
			<td class="td-form">
				<%@ include file="/WEB-INF/include/back_button.h" %>
			</td>
			<td class="td-form">
				<input type="submit" value="Save">
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