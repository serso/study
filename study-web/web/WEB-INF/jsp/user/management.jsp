<%@ page import="org.solovyev.study.controllers.user.UserSearchController" %>
<%@ page import="org.solovyev.study.model.UserRole" %>
<%@ page import="org.solovyev.study.resources.Config" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: Mar 27, 2010
  Time: 6:47:40 PM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>User search</title>
	<style type="text/css">
		@import "/resources/css/table.css";
	</style>
	<%@ include file="/WEB-INF/include/header.h" %>
	<script type="text/javascript" src="/resources/javascript/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="/resources/javascript/jquery.dataTables.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#users-table').dataTable();
		});
	</script>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<%--@elvariable id="userSelector" type="org.solovyev.study.model.SelectorImpl"--%>

<table cellpadding="0" cellspacing="0" border="0">
	<tr>
		<td>
			<form:form commandName="<%=UserSearchController.USER_SEARCH_PARAMS_MODEL%>">
				<table class="table-form">
					<tr class="tr-form">
						<th class="th-form" colspan="2">Search</th>
					</tr>
					<tr class="tr-form">
						<td class="td-form">
							<form:label path="username">Username</form:label>
						</td>
						<td class="td-form">
							<form:input path="username" cssClass="form-input" cssErrorClass="error form-input"/>
						</td>
					</tr>
					<tr class="tr-form">
						<td class="td-form">
							<form:label path="email">Email</form:label>
						</td>
						<td class="td-form">
							<form:input path="email" cssClass="form-input" cssErrorClass="error form-input"/>
						</td>
					</tr>
					<tr class="tr-form">
						<td class="td-form">
							<form:label path="userRoles">User Roles</form:label>
						</td>
						<td class="td-form">
							<form:select path="userRoles" items="<%=UserRole.values()%>" cssClass="form-select" cssErrorClass="error form-select"/>
						</td>
					</tr>
					<tr class="tr-form">
						<td class="td-form">&nbsp;</td>
						<td class="td-form">
							<input type="submit" value="Search" id="searchButton" onclick="submitForm('searchButton', '<%=UserSearchController.USER_SEARCH_PARAMS_MODEL%>', '/user/search.do', 'POST')"/>
						</td>
					</tr>
				</table>
			</form:form>
		</td>
	</tr>
	<%--@elvariable id="users" type="java.util.List"--%>
	<c:if test="${users != null}">
		<tr>
			<td>
				<form:form commandName="<%=UserSearchController.USER_SELECTOR_MODEL%>">
					<table class="display" id="users-table">
						<thead>
						<tr>
							<th>&nbsp;</th>
							<th>&nbsp;</th>
							<th>Username</th>
							<th>Email</th>
							<th>User Roles</th>
							<th>Linked partners</th>
							<th>Location</th>
							<th>Creation Date</th>
							<th>Creator</th>
							<th>Modification Date</th>
							<th>Is account enabled</th>
							<th>&nbsp;</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${users}" var="user" varStatus="userStatus">
							<%--@elvariable id="user" type="org.solovyev.study.model.User"--%>
							<tr>
								<td>${userStatus.index + 1}</td>
								<td>
									<c:choose>
										<c:when test="${!userSelector.multiSelectAvailable}">
											<form:radiobutton path="indexes" value="${userStatus.index}"/>
										</c:when>
										<c:otherwise>
											<form:checkbox path="indexes" value="${userStatus.index}"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>
									<s:url value="/user/view.do" var="viewUserUrl">
										<s:param name="userId" value="${user.id}"/>
									</s:url>
									<a onclick="sendRequest('${viewUserUrl}', {'backRouteAction': '/user/back.to.management.do'}, 'POST');">
											${user.username}
									</a>
								</td>
								<td>${user.email}</td>
								<td>${user.userRoles}</td>
								<td>
									<c:forEach items="${user.linkedPartners}" var="linkedPartner" varStatus="linkedPartnersStatus">
										<%--@elvariable id="linkedPartner" type="org.solovyev.study.model.Partner"--%>
										${linkedPartner.fullName}
										<c:if test="${!linkedPartnersStatus.last}">, </c:if>
									</c:forEach>
								</td>
								<td>
									<c:set var="location" value="${null}"/>

									<c:if test="${fn:length(user.linkedPartners) > 0}">
										<c:set var="mainAddress" value="${user.linkedPartners[0].mainAddress}"/>
										<c:if test="${mainAddress != null}">
											<c:set var="location" value="${mainAddress.country}, ${mainAddress.city}"/>
										</c:if>
									</c:if>
									&nbsp;<c:if test="${location != null}">${location}</c:if>
								</td>
								<td>
									<fmt:formatDate value="${user.creationDate}" pattern="<%=Config.DATE_AND_TIME_PATTERN%>"/></td>
								<td>${user.creatorId}</td>
								<td>
									<fmt:formatDate value="${user.modificationDate}" pattern="<%=Config.DATE_AND_TIME_PATTERN%>"/></td>
								<td>${user.enabled}</td>
								<td class="do_not_wrap">
									<s:url value="/user/edit.do" scope="request" var="editUserUrl">
										<s:param name="userId" value="${user.id}"/>
									</s:url>
									<s:url value="/user/delete.do" scope="request" var="deleteUserUrl">
										<s:param name="userId" value="${user.id}"/>
									</s:url>
									<s:url value="/user/enable.do" scope="request" var="enableUserUrl">
										<s:param name="userId" value="${user.id}"/>
										<s:param name="enable" value="${!user.enabled}"/>
									</s:url>
									<s:url value="/user/view.do" var="viewUserUrl">
										<s:param name="userId" value="${user.id}"/>
									</s:url>

									<a onclick="sendRequest('${viewUserUrl}', {'backRouteAction': '/user/back.to.management.do'}, 'POST');" class="img">
										<img src="/resources/images/actions/view.png" alt="View" border="0">
									</a>

									<a onclick="sendRequest('${editUserUrl}', {'backRouteAction': '/user/back.to.management.do'}, 'POST');" class="img">
										<img src="/resources/images/actions/edit.png" alt="Edit" border="0">
									</a>
									<c:choose>
										<c:when test="${user.enabled}">
											<a href="${enableUserUrl}" class="img">
												<img src="/resources/images/actions/disable.png" alt="Disable" border="0">
											</a>
										</c:when>
										<c:otherwise>
											<a href="${enableUserUrl}" class="img">
												<img src="/resources/images/actions/enable.png" alt="Enable" border="0">
											</a>
										</c:otherwise>
									</c:choose>
									<a href="${deleteUserUrl}" class="img">
										<img src="/resources/images/actions/delete.png" alt="Delete" border="0">
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
						<c:if test="${fn:length(users) > 0}">
							<c:forEach items="${userSelector.buttons}" var="button" varStatus="buttonStatus">
								<%--@elvariable id="button" type="org.solovyev.common.html.Button"--%>
								<input type="button" id="selectUserButton_${buttonStatus.index}" value="${button.value}"
								       onclick="submitForm('selectUserButton_${buttonStatus.index}', '<%=UserSearchController.USER_SELECTOR_MODEL%>', '${button.action}', 'POST');"/>
							</c:forEach>
						</c:if>
					</td>
					<sec:authorize access="hasAnyRole('administrator','school_employee','developer')">
						<td class="td-form" align="right" style="text-align:right;">
							<input type="button" id="createButton" value="New user" onclick="pressButtonWithParams('createButton', '/user/create.do', 'POST', {'backRouteAction': '/user/back.to.management.do'});"/>							
						</td>
					</sec:authorize>
				</tr>
			</table>
		</td>
	</tr>
</table>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>
