<%@ page import="org.solovyev.study.resources.Config" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="partner" type="org.solovyev.study.model.partner.NaturalPerson"--%>


<c:set var="male" value="<%=Gender.male.name()%>"/>

	<tr class="tr-form">
		<td class="th-form">
			&nbsp;
		</td>
		<td class="td-form">
			<%@ include file="/WEB-INF/jsp/partner/include/partner.icon.h" %>
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			First name
		</td>
		<td class="td-form">
			${partner.firstName}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			Last name
		</td>
		<td class="td-form">
			${partner.lastName}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			Birthdate
		</td>
		<td class="td-form">
			<fmt:formatDate value="${partner.birthdate}" pattern="<%=Config.DATE_PATTERN%>"/>
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			Gender
		</td>
		<td class="td-form">
			${partner.gender}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			Partner roles
		</td>
		<td class="td-form">
			${partner.partnerRoles}
		</td>
	</tr>