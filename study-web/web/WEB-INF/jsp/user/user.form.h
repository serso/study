<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.solovyev.study.controllers.user.UserCreateEditController" %>
<%@ page import="org.solovyev.study.model.UserRole" %>

<%--@elvariable id="editUser" type="java.lang.Boolean"--%>
<%--@elvariable id="user" type="org.solovyev.study.model.User"--%>

<c:choose>
	<c:when test="${editUser}">
		<c:set var="action" value="/user/save.edited.do"/>
		<c:url var="backRouteAction" value="/user/back.to.edit.user.do"/>
		<c:set var="currentView" value="/user/edit"/>
	</c:when>
	<c:otherwise>
		<c:set var="action" value="/user/save.new.do"/>
		<c:url var="backRouteAction" value="/user/back.to.create.user.do"/>
		<c:set var="currentView" value="/user/create"/>
	</c:otherwise>
</c:choose>

<form:form commandName="<%=UserCreateEditController.USER_MODEL%>">
	<form:hidden path="id"/>
	<input type="hidden" name="currentView" value="${currentView}">
	<table class="table-form">
		<tr class="tr-form">   
			<td class="td-form">
				<form:label path="username">Username</form:label>
			</td>
			<td class="td-form">
				<form:input path="username" cssClass="form-input" cssErrorClass="error form-input"/>
			</td>
			<td class="td-form">
				&nbsp;<form:errors path="username"/>
			</td>
		</tr>
		<c:choose>
			<c:when test="${editUser}">
				<tr class="tr-form">
					<td class="td-form">
						<form:label path="password">Password</form:label>
					</td>
						<%--@elvariable id="user" type="org.solovyev.study.model.User"--%>
					<td class="td-form">
						<form:password path="password" disabled="${user.doNotChangePassword}" cssClass="form-input"
						               cssErrorClass="error form-input"/>
					</td>
					<td class="td-form">
						&nbsp;<form:errors path="password"/>
					</td>
				</tr>
				<tr class="tr-form">
					<td class="td-form">
						<form:label path="doNotChangePassword">Do not change old password</form:label>
					</td>
					<td class="td-form">
						<form:checkbox id="doNotChangePassword" path="doNotChangePassword"
						               onchange="disableInput('doNotChangePassword', 'password')"
						               cssClass="form-checkbox" cssErrorClass="error form-checkbox"/>
					</td>
					<td class="td-form">&nbsp;</td>
				</tr>
			</c:when>
			<c:otherwise>
				<tr class="tr-form">
					<td class="td-form">
						<form:label path="password">Password</form:label>
					</td>
					<td class="td-form">
						<form:password path="password" cssClass="form-input" cssErrorClass="error form-input"/>
					</td>
					<td class="td-form">
						&nbsp;<form:errors path="password"/>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
		<tr class="tr-form">
			<td class="td-form">
				<form:label path="email">Email</form:label>
			</td>
			<td class="td-form">
				<form:input path="email" cssClass="form-input" cssErrorClass="error form-input"/>
			</td>
			<td class="td-form">
				&nbsp;<form:errors path="email"/>
			</td>
		</tr>
		<tr class="tr-form">
			<td class="td-form">
				<form:label path="userRoles">User roles</form:label>
			</td>
			<td class="td-form">
				<form:select path="userRoles" items="<%=UserRole.values()%>" cssClass="form-select"
				             cssErrorClass="error form-select"/>
			</td>
			<td>
				&nbsp;<form:errors path="userRoles"/>
			</td>
		</tr>
		<tr class="tr-form">
			<td class="td-form">
				<form:label path="enabled">Is account enabled?</form:label>
			</td>
			<td class="td-form">
				<form:radiobuttons path="enabled" items="<%=new Boolean[]{Boolean.TRUE, Boolean.FALSE}%>"
				                   cssClass="form-radiobuttons" cssErrorClass="error form-radiobuttons"/>
			</td>
			<td>
				&nbsp;<form:errors path="enabled"/>
			</td>
		</tr>
		<c:forEach items="${user.linkedPartners}" var="linkedPartner" varStatus="linkedPartnerStatus">
			<%--@elvariable id="linkedPartner" type="org.solovyev.study.model.partner.Partner"--%>
			<tr class="tr-form">
				<td class="td-form">${linkedPartnerStatus.index == 0 ? 'Linked partners' : ''}</td>
				<td class="td-form">
						${linkedPartner.fullName} -> ${linkedPartner.partnerRoles}
					<a href="javascript:submitForm(null, '<%=UserCreateEditController.USER_MODEL%>', '/user/remove.linked.partner.do?partnerIndex=${linkedPartnerStatus.index}', null)">remove</a>
				</td>
				<td>
					&nbsp;
				</td>
			</tr>
		</c:forEach>
		<tr class="tr-form">
			<td class="td-form">&nbsp;</td>
			<td class="td-form">
				<input type="hidden" name="backRouteAction" value="${backRouteAction}">
				<input type="button" value="Add linked partner" id="addLinkedPartnerButton"
				       onclick="submitForm('addLinkedPartnerButton', '<%=UserCreateEditController.USER_MODEL%>', '/user/add.linked.partner.do', 'POST');"/>
			</td>
			<td>
				&nbsp;<form:errors path="linkedPartners"/>
			</td>
		</tr>
		<tr class="tr-form">
			<td class="td-form">
				<%@include file="/WEB-INF/include/back_button.h"%>
			</td>
			<td class="td-form" colspan="2">
				<input type="button" value="Save"  id="saveButton"
				       onclick="submitForm('saveButton', '<%=UserCreateEditController.USER_MODEL%>', '${action}', 'POST');"/>
			</td>
		</tr>
	</table>
</form:form>