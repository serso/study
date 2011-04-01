<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="org.solovyev.study.model.partner.Partner" %>
<%@ page import="org.solovyev.study.model.partner.PartnerRole" %>
<%@ page import="org.solovyev.study.resources.Config" %>
<%@ page import="org.solovyev.study.model.partner.LegalPerson" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="partner" type="org.solovyev.study.model.partner.LegalPerson"--%>
<c:set var="school" value="${partner}"/>
<%--@elvariable id="school" type="org.solovyev.study.model.partner.LegalPerson"--%>

<c:set var="schoolDetails" value="<%=partner.getDetails().get(PartnerRole.school)%>"/>
<%--@elvariable id="schoolDetails" type="org.solovyev.study.model.SchoolDetails"--%>

<tr class="tr-form">
	<td class="th-form">
		School name
	</td>
	<td class="td-form">
		${school.companyName}
	</td>
</tr>
<tr class="tr-form">
	<td class="th-form">
		School number
	</td>
	<td class="td-form">
		${schoolDetails.schoolNumber}
	</td>
</tr>
<tr class="tr-form">
	<td class="th-form">
		Incorporation date
	</td>
	<td class="td-form">
		<fmt:formatDate value="${school.incorporationDate}" pattern="<%=Config.DATE_PATTERN%>"/>
	</td>
</tr>