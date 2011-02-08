<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--
  User: serso
  Date: Mar 20, 2010
  Time: 12:19:56 AM
--%>
<%@ include file="/WEB-INF/include/top.h" %>
<html>
<head>
	<title>Create User</title>
	<%@ include file="/WEB-INF/include/header.h" %>
</head>
<body>
<%@ include file="/WEB-INF/include/content_start.h" %>
<%@ include file="/WEB-INF/include/message_box.h" %>
<c:set var="editUser" value="false"/>
<%@ include file="/WEB-INF/jsp/user/user.form.h" %>
<%@ include file="/WEB-INF/include/content_end.h" %>
</body>
</html>