<%@ page import="org.solovyev.study.model.Gender" %>
<%@ page import="org.solovyev.study.resources.Config" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: May 4, 2010
  Time: 11:40:36 PM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>View user</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>

<%--@elvariable id="user" type="org.solovyev.study.model.User"--%>

<c:set var="linkedPartner" value="${null}"/>
<c:if test="${fn:length(user.linkedPartners) > 0}">
	<c:set var="linkedPartner" value="${user.linkedPartners[0]}"/>
	<%--@elvariable id="linkedPartner" type="org.solovyev.study.model.Partner"--%>
</c:if>

<c:set var="male" value="<%=Gender.male.name()%>"/>

<table class="table-form">
	<c:if test="${linkedPartner != null}">
		<tr class="tr-form">
			<td class="th-form">
				&nbsp;
			</td>
			<td class="td-form">
				<c:set var="partner" value="${linkedPartner}"/>				
				<%@ include file="/WEB-INF/jsp/partner/include/partner.icon.h"%>
			</td>
		</tr>
	</c:if>
	<tr class="tr-form">
		<td class="th-form">
			Username
		</td>
		<td class="td-form">
			${user.username}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			Email
		</td>
		<td class="td-form">
			${user.email}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			User roles
		</td>
		<td class="td-form">
			${user.userRoles}
		</td>
	</tr>

	<c:if test="${linkedPartner != null}">
		<c:choose>
			<c:when test="${linkedPartner.naturalPerson}">
				<%--@elvariable id="linkedPartner" type="org.solovyev.study.model.NaturalPerson"--%>
				<tr class="tr-form">
					<td class="th-form">
						First Name
					</td>
					<td class="td-form">
							${linkedPartner.firstName}
					</td>
				</tr>
				<tr class="tr-form">
					<td class="th-form">
						Last Name
					</td>
					<td class="td-form">
							${linkedPartner.lastName}
					</td>
				</tr>
				<tr class="tr-form">
					<td class="th-form">
						Birthdate
					</td>
					<td class="td-form">
						<fmt:formatDate value="${linkedPartner.birthdate}" pattern="<%=Config.DATE_PATTERN%>"/>
					</td>
				</tr>
				<tr class="tr-form">
					<td class="th-form">
						Gender
					</td>
					<td class="td-form">
							${linkedPartner.gender}
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<%--@elvariable id="linkedPartner" type="org.solovyev.study.model.LegalPerson"--%>
				<tr class="tr-form">
					<td class="th-form">
						Company Name
					</td>
					<td class="td-form">
							${linkedPartner.companyName}
					</td>
				</tr>
				<tr class="tr-form">
					<td class="th-form">
						Incorporation Date
					</td>
					<td class="td-form">
						<fmt:formatDate value="${linkedPartner.incorporationDate}" pattern="<%=Config.DATE_PATTERN%>"/>
					</td>
				</tr>
			</c:otherwise>
		</c:choose>
		<c:set var="partner" value="${linkedPartner}"/>						 
		<%@ include file="/WEB-INF/jsp/partner/include/partner.main.address.h"%>
	</c:if>
	<tr class="tr-form">
		<td class="td-form">
			<%@ include file="/WEB-INF/include/back_button.h" %>
		</td>
		<td class="td-form">
			<s:url var="editUrl" value="/user/edit.do">
				<s:param name="userId" value="${user.id}"/>
			</s:url>
			<s:url var="backRouteUrl" value="/user/back.to.view.do">
				<s:param name="userId" value="${user.id}"/>
			</s:url>
			<input id="editButton" type="button" value="Edit" onclick="pressButtonWithParams('editButton','${editUrl}', 'POST', {'backRouteAction':'${backRouteUrl}'});"/>
		</td>
	</tr>
</table>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>