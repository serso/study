<%@ page import="org.solovyev.study.controllers.partner.PartnerSearchController" %>
<%@ page import="org.solovyev.study.model.Gender" %>
<%@ page import="org.solovyev.study.model.partner.Partner" %>
<%@ page import="org.solovyev.study.model.partner.PartnerRole" %>
<%@ page import="org.solovyev.study.model.PartnerType" %>
<%@ page import="org.solovyev.study.resources.Config" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: Apr 17, 2010
  Time: 10:24:06 PM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<%@ include file="/WEB-INF/include/header.h" %>
	<title>Partner search</title>

	<style type="text/css">
		@import "<c:url value="/resources/css/table.css"/>";
	</style>
	<script type="text/javascript" src="<c:url value="/resources/javascript/jquery-1.4.2.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/javascript/jquery.dataTables.js"/>"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#partner-table').dataTable();
		});
	</script>

</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<div id="container">
<div id="row">
<div id="cell">
	<table cellpadding="0" cellspacing="0" border="0">
		<%--@elvariable id="partners" type="java.util.List"--%>
		<c:if test="${partners != null}">
			<tr>
				<td>
					<form:form commandName="<%=PartnerSearchController.PARTNER_SELECTOR_MODEL%>">
						<%--@elvariable id="partners" type="java.util.List"--%>
						<table class="display" id="partner-table">
							<thead>
							<tr>
								<th>&nbsp;</th>
								<th>&nbsp;</th>
								<th>Full Name / Company Name</th>
								<th>Partner Roles</th>
								<th>Linked users</th>
								<th>Partner Type</th>
								<th>Location</th>
								<th>Creation Date</th>
								<th>Creator</th>
								<th>Modification Date</th>
								<th>&nbsp;</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${partners}" var="partner" varStatus="partnerStatus">
								<%--@elvariable id="partner" type="org.solovyev.study.model.partner.Partner"--%>
								<tr>
									<td>${partnerStatus.index + 1}</td>
									<td>
										<c:choose>
											<c:when test="${!partnerSelector.multiSelectAvailable}">
												<form:radiobutton path="indexes" value="${partnerStatus.index}"/>
											</c:when>
											<c:otherwise>
												<form:checkbox path="indexes" value="${partnerStatus.index}"/>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<c:url var="partnerUrl" value="/partner/view.do">
											<c:param name="partnerId" value="${partner.id}"/>
										</c:url>
										<a onclick="sendRequest('${partnerUrl}', {'backRouteAction': '/partner/back.to.management.do'}, 'POST');">
												${partner.fullName}
										</a>
									</td>
									<td>${partner.partnerRoles}</td>
									<td>
										<c:forEach items="${partner.linkedUsers}" var="linkedUser"
												   varStatus="linkedUsersStatus">
											<%--@elvariable id="linkedUser" type="org.solovyev.study.model.user.User"--%>
											<c:url value="/user/view.do" var="viewUserUrl">
												<c:param name="userId" value="${linkedUser.id}"/>
											</c:url>
											<a onclick="sendRequest('${viewUserUrl}', {'backRouteAction': '/partner/back.to.management.do'}, 'POST');">${linkedUser.username}</a>
											<c:if test="${!linkedUsersStatus.last}">, </c:if>
										</c:forEach>
									</td>
									<td>${partner.partnerType}</td>
									<c:set var="location" value="${null}"/>

									<c:set var="mainAddress" value="${partner.mainAddress}"/>
									<c:if test="${mainAddress != null}">
										<c:set var="location" value="${mainAddress.country}, ${mainAddress.city}"/>
									</c:if>

									<td>
										&nbsp;<c:if test="${location != null}">${location}</c:if>
									</td>
									<td>
										<fmt:formatDate value="${partner.creationDate}"
														pattern="<%=Config.DATE_AND_TIME_PATTERN%>"/></td>
									<td>${partner.creatorId}</td>
									<td>
										<fmt:formatDate value="${partner.modificationDate}"
														pattern="<%=Config.DATE_AND_TIME_PATTERN%>"/></td>
									<td class="do_not_wrap">
										<c:url value="/partner/edit.do" scope="request" var="editPartnerUrl">
											<c:param name="partnerId" value="${partner.id}"/>
										</c:url>
										<c:url value="/partner/delete.do" scope="request" var="deletePartnerUrl">
											<c:param name="partnerId" value="${partner.id}"/>
										</c:url>
										<c:url var="partnerUrl" value="/partner/view.do">
											<c:param name="partnerId" value="${partner.id}"/>
										</c:url>
										<a onclick="sendRequest('${partnerUrl}', {'backRouteAction': '/partner/back.to.management.do'}, 'POST');" class="img">
											<img src="<c:url value='/resources/images/actions/view.png'/>" alt="View" border="0">
										</a>
										<a onclick="sendRequest('${editPartnerUrl}', {'backRouteAction': '/partner/back.to.management.do'}, 'POST');" class="img">
											<img src="<c:url value='/resources/images/actions/edit.png'/>" alt="Edit" border="0">
										</a>
										<a href="${deletePartnerUrl}" class="img">
											<img src="<c:url value='/resources/images/actions/delete.png'/>" alt="Delete" border="0">
										</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</form:form>
				</td>
			</tr>
		</c:if>
		<tr>
			<td>
				<table class="table-form" width="100%">
					<tr class="tr-form">
						<td class="td-form">

							<%@ include file="/WEB-INF/include/back_button.h" %>

							<c:if test="${fn:length(partners) > 0}">
								<c:forEach items="${partnerSelector.buttons}" var="button" varStatus="buttonStatus">
									<%--@elvariable id="button" type="org.solovyev.common.html.Button"--%>
									<input type="button" id="selectPartnerButton_${buttonStatus.index}"
										   value="${button.value}"
										   onclick="submitForm('selectPartnerButton_${buttonStatus.index}', '<%=PartnerSearchController.PARTNER_SELECTOR_MODEL%>', '<c:url value="${button.action}"/>', 'POST');"/>
								</c:forEach>
							</c:if>
						</td>
						<td class="td-form" style="text-align:right;">
							<input type="button" id="createButton" value="New partner"
								   onclick="pressButtonWithParams('createButton', '<c:url value="/partner/create.do"/>', 'POST', {'backRouteAction': '<c:url value="/partner/back.to.management.do"/>'});"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<div id="cell">
	&nbsp;
</div>

<%--@elvariable id="partnerSelector" type="org.solovyev.study.model.SelectorImpl"--%>
<div id="cell" style="vertical-align:top;">
	<form:form commandName="<%=PartnerSearchController.PARTNER_SEARCH_PARAMS_MODEL%>">
		<table class="table-form">
			<tr class="tr-form">
				<th class="th-form" colspan="2">Search</th>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
					<form:label path="firstName">First Name</form:label>
				</td>
				<td class="td-form">
					<form:input path="firstName" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
					<form:label path="lastName">Last Name</form:label>
				</td>
				<td class="td-form">
					<form:input path="lastName" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form">
					<form:label path="genders">Gender</form:label>
				</td>
				<td class="td-form">
					<form:select path="genders" items="<%=Gender.values()%>" cssClass="form-select"
								 cssErrorClass="error form-select"/>
				</td>
			</tr>
			<tr>
				<td class="td-form">
					<form:label path="companyName">Company Name</form:label>
				</td>
				<td class="td-form">
					<form:input path="companyName" cssClass="form-input" cssErrorClass="error form-input"/>
				</td>
			</tr>
			<tr>
				<td class="td-form">
					<form:label path="partnerRoles">Partner Roles</form:label>
				</td>
				<td class="td-form">
					<form:select path="partnerRoles" items="<%=PartnerRole.values()%>" multiple="true"
								 cssClass="form-select" cssErrorClass="error form-select"/>
				</td>
			</tr>
			<tr>
				<td class="td-form">
					<form:label path="partnerTypes">Partner Types</form:label>
				</td>
				<td class="td-form">
					<form:select path="partnerTypes" items="<%=PartnerType.values()%>" multiple="true"
								 cssClass="form-select" cssErrorClass="error form-select"/>
				</td>
			</tr>
			<tr>
				<td class="td-form">
					<form:label path="country">Country</form:label>
				</td>
				<td class="td-form">
					<form:input path="country" cssClass="form-select" cssErrorClass="error form-select"/>
				</td>
			</tr>
			<tr>
				<td class="td-form">
					<form:label path="city">City</form:label>
				</td>
				<td class="td-form">
					<form:input path="city" cssClass="form-select" cssErrorClass="error form-select"/>
				</td>
			</tr>
			<tr class="tr-form">
				<td class="td-form" colspan="2">
					<input id="searchButton" name="searchButton" type="button" value="Search"
						   onclick="submitForm('searchButton', '<%=PartnerSearchController.PARTNER_SEARCH_PARAMS_MODEL%>', '<c:url value="/partner/search.do"/>', 'POST');"/>
				</td>
			</tr>
		</table>
	</form:form>
</div>
</div>
</div>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>