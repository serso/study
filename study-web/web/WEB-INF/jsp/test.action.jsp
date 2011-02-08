<%@ page import="org.solovyev.study.controllers.partner.PartnerSearchController" %>
<%@ page import="org.solovyev.study.model.Partner" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.solovyev.study.model.NaturalPerson" %>
<%@ page import="org.solovyev.study.model.LegalPerson" %>
<%@ page import="org.solovyev.study.controllers.test.TestController" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<script type="text/javascript" src="/resources/javascript/common.js"></script>
	<style type="text/css">
		@import "/resources/css/table.css";
	</style>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<%--@elvariable id="partnerSelector" type="org.solovyev.study.model.TestPartnerSelector"--%>
<%
	List<Partner> partners = new ArrayList<Partner>();
	Partner partner;
	for (int i = 0; i < 10; i++) {
		partner = new NaturalPerson(i);
		((NaturalPerson) partner).setFirstName("firstName" + i);
		((NaturalPerson) partner).setLastName("lastName" + i);
		partners.add(partner);
	}
	pageContext.setAttribute("partners", partners);
%>

<table cellpadding="0" cellspacing="0" border="0">

	<%--@elvariable id="partners" type="java.util.List"--%>


	<tr>
		<td>
			<form:form commandName="<%=PartnerSearchController.PARTNER_SELECTOR_MODEL%>">
				<%--@elvariable id="partners" type="java.util.List"--%>
				<c:if test="${partners != null}">
					<table class="display" id="partner-table">
						<thead>
						<tr>
							<th>&nbsp;</th>
							<th>&nbsp;</th>
							<th>Full Name / Company Name</th>
							<th>Partner Roles</th>
							<th>Partner Type</th>
							<th>Creation Date</th>
							<th>Creator</th>
							<th>Modification Date</th>
							<th>&nbsp;</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${partners}" var="partner" varStatus="partnerStatus">
							<c:set var="partner" value="${partner}"/>
							<%--@elvariable id="partner" type="org.solovyev.study.model.Partner"--%>
							<tr>
								<td>
									<c:choose>
										<c:when test="${!partnerSelector.multiPartnerSelectAvailable}">
											<form:radiobutton path="partnerIndexes"
											                  value="${partnerStatus.index}"/>
										</c:when>
										<c:otherwise>
											<form:checkbox path="partnerIndexes" value="${partnerStatus.index}"/>
										</c:otherwise>
									</c:choose>
								</td>
								<td>${partnerStatus.index + 1}</td>
								<td>${partner.fullName}</td>
								<td>${partner.partnerRoles}</td>
								<td>${partner.partnerType}</td>
								<td>${partner.creationDate}</td>
								<td>${partner.creatorId}</td>
								<td>${partner.modificationDate}</td>
								<td>
									<s:url value="edit.partner.do" scope="request" var="editPartnerUrl">
										<s:param name="partnerId" value="${partner.id}"/>
									</s:url>
									<s:url value="delete.partner.do" scope="request" var="deletePartnerUrl">
										<s:param name="partnerId" value="${partner.id}"/>
									</s:url>
									<a href="${editPartnerUrl}">Edit</a>&nbsp;
									<a href="${deletePartnerUrl}">Delete</a>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</c:if>
			</form:form>
		</td>
	</tr>
	<tr>
		<td>
			<%--@elvariable id="backAction" type="org.solovyev.study.model.Action"--%>
			<c:if test="${backAction != null}">
				<input type="button" id="backButton" value="Back"
				       onclick="pressButton('backButton', '${backAction.action}', 'GET');"/>
			</c:if>
			<c:if test="${partnerSelector != null}">
				<input type="button" id="selectPartnerButton" value="Select"
				       onclick="submitForm('selectPartnerButton', '<%=PartnerSearchController.PARTNER_SELECTOR_MODEL%>', '${partnerSelector.action}', 'POST');"/>
			</c:if>
		</td>
	</tr>
	<tr>
		<td>
			<form:form commandName="<%=TestController.TEST_OBJECT_MODEL%>" method="POST" action="${partnerSelector.action}">
				<%--@elvariable id="testObject" type="org.solovyev.study.controllers.test.TestObject"--%>
				<form:input path="property"/>
				<c:forEach items="${testObject.objects}" var="object" varStatus="testObjectsStatus">
					<br>${testObjectsStatus.index}.
					<form:input path="objects[${testObjectsStatus.index}].property"/>${object.property}
				</c:forEach>
				<input type="submit" value="submit"/>
			</form:form>
		</td>
	</tr>
	<tr>
		<td>
			<form action="/test.action.get.do?param=erwrwe" method="post">
				<input type="submit" value="Test params in get method"/>
			</form>
		</td>
	</tr>

</table>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>