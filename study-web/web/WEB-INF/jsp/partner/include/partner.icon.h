<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="partner" type="org.solovyev.study.model.partner.NaturalPerson"--%>

<c:choose>
	<c:when test="${partner.naturalPerson}">
		<c:choose>
			<c:when test="${partner.gender == male}">
				<img src="<c:url value='/resources/images/user_icons/male.png'/>" alt="male">
			</c:when>
			<c:otherwise>
				<img src="<c:url value='/resources/images/user_icons/female.png'/>" alt="female">
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<img src="<c:url value='/resources/images/user_icons/company.png'/>" alt="company">
	</c:otherwise>
</c:choose>