<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="linkedPartner" type="org.solovyev.study.model.Partner"--%>

<c:set var="mainAddress" value="${partner.mainAddress}"/>
<%--@elvariable id="mainAddress" type="org.solovyev.study.model.Address"--%>

<c:if test="${mainAddress != null}">
	<tr class="tr-form">
		<td class="th-form">
			Country
		</td>
		<td class="td-form">
			${mainAddress.country}
		</td>
	</tr>
	<tr class="tr-form">
		<td class="th-form">
			City
		</td>
		<td class="td-form">
			${mainAddress.city}
		</td>
	</tr>
</c:if>