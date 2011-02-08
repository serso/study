<%@ page import="org.solovyev.study.controllers.partner.PartnerCreateEditController" %>
<%@ page import="org.solovyev.study.model.*" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="partner" type="org.solovyev.study.model.Partner"--%>
<%--@elvariable id="editPartner" type="java.lang.Boolean"--%>

<c:choose>
	<c:when test="${editPartner}">
		<c:set var="action" value="/partner/save.edited.do"/>
		<c:set var="backRouteAction" value="/partner/back.to.edit.do"/>
	</c:when>
	<c:otherwise>
		<c:set var="action" value="/partner/save.new.do"/>
		<c:set var="backRouteAction" value="/partner/back.to.create.do"/>
	</c:otherwise>
</c:choose>

<table class="table-form">

	<tr class="tr-form">
		<c:choose>
			<c:when test="${editPartner}">
				<td class="td-form">Partner Type</td>
				<td class="td-form">${partner.partnerType}</td>
				<td class="td-form">&nbsp;</td>
			</c:when>
			<c:otherwise>
				<td class="td-form"><label for="partnerType">Partner Type</label></td>
				<td class="td-form">

					<form name="partnerTypeForm" action="/partner/create.do" method="POST">
						<select id="partnerType" class="form-select" name="type"
								onchange="document.partnerTypeForm.submit();">
							<c:forEach items="<%=PartnerType.values()%>" var="type">
								<option ${partner.partnerType == type ? "selected" : ""}>${type}</option>
							</c:forEach>
						</select>
					</form>

				</td>
				<td class="td-form">&nbsp;</td>
			</c:otherwise>
		</c:choose>
	</tr>

	<form:form commandName="<%=PartnerCreateEditController.PARTNER_MODEL%>">
		<form:hidden path="id"/>

		<c:choose>
			<c:when test="${partner.naturalPerson}">
				<%@ include file="/WEB-INF/jsp/partner/include/natural.person.form.h" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/WEB-INF/jsp/partner/include/legal.person.form.h" %>
			</c:otherwise>
		</c:choose>

		<tr class="tr-form">
			<td class="td-form">
				<form:label path="partnerRoles">Partner Roles</form:label>
			</td>
			<td class="td-form">
				<form:select path="partnerRoles" items="${partner.applyablePartnerRoles}"
							 multiple="${partner.naturalPerson?'true':'false'}" cssClass="form-select"
							 cssErrorClass="error form-select"
							 onchange="checkPartnerDetailsForRole()"/>
			</td>
			<td class="td-form">
				&nbsp;<form:errors path="partnerRoles"/>
			</td>
		</tr>

		<c:set var="mainAddress" value="${partner.mainAddress}"/>
		<c:if test="${mainAddress != null}">
			<tr class="tr-form">
				<td class="td-form">
					Country
				</td>
				<td class="td-form">
						${mainAddress.country}
				</td>
				<td class="td-form">
					&nbsp;
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
					City
				</td>
				<td class="td-form">
						${mainAddress.city}
				</td>
				<td class="td-form">
					&nbsp;
				</td>
			</tr>
		</c:if>

		<tr class="tr-form">
			<td class="td-form">
				&nbsp;
			</td>
			<td class="td-form">
				<input id="editAddressesButton" type="button" value="Edit addresses"
					   onclick="submitFormWithParams('editAddressesButton', '<%=PartnerCreateEditController.PARTNER_MODEL%>', '/partner/edit.addresses.do', 'POST', {'backRouteAction': '${backRouteAction}'})"/>
			</td>
			<td class="td-form">
				&nbsp;
			</td>
		</tr>

		<tr class="tr-form">
			<td class="td-form">
				&nbsp;
			</td>
			<td class="td-form">
				<input id="editSchoolDetails" type="button" value="Edit details"
					   onclick="submitFormWithParams('editSchoolDetails', '<%=PartnerCreateEditController.PARTNER_MODEL%>', '/partner/edit.details.do', 'POST', {'backRouteAction': '${backRouteAction}'})"/>
			</td>
			<td class="td-form">
				&nbsp;
			</td>
		</tr>

		<tr class="tr-form">
			<td class="td-form">
				<%@ include file="/WEB-INF/include/back_button.h" %>
			</td>
			<td class="td-form" colspan="2">
				<input id="saveButton" type="submit" value="Save"
					   onclick="submitForm('saveButton', '<%=PartnerCreateEditController.PARTNER_MODEL%>', '${action}', 'POST')"/>
			</td>
		</tr>

	</form:form>
</table>