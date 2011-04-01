<%@ page import="org.solovyev.study.controllers.partner.PartnerSearchController" %>
<%@ page import="org.solovyev.study.model.partner.Partner" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.solovyev.study.model.partner.NaturalPerson" %>
<%@ page import="org.solovyev.study.model.partner.LegalPerson" %>
<%@ page import="org.solovyev.study.controllers.test.TestController" %>
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
							<%--@elvariable id="partner" type="org.solovyev.study.model.partner.Partner"--%>
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
									<c:url value="/partner/edit.do" scope="request" var="editPartnerUrl">
										<c:param name="partnerId" value="${partner.id}"/>
									</c:url>

									<c:url value="/partner/delete.do" scope="request" var="deletePartnerUrl">
										<c:param name="partnerId" value="${partner.id}"/>
									</c:url>

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