<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: Apr 19, 2010
  Time: 11:11:54 PM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>Edit Partner</title>
	<%@ include file="/WEB-INF/include/header.h" %>
	<%@ include file="/WEB-INF/include/partner.form.javascript.h" %>
</head>
<body onload="checkPartnerDetailsForRole()">
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>
<c:set var="editPartner" value="true"/>
<%@ include file="/WEB-INF/jsp/partner/include/partner.form.h" %>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>