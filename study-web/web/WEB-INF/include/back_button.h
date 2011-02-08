<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="backButton" type="org.solovyev.common.html.Button"--%>
<c:if test="${backButton != null && fn:length(backButton.action) > 0}">
	<input id="backButton" type="button" value="${backButton.value}" onclick="pressButton('backButton','${backButton.action}', 'POST');"/>
</c:if>