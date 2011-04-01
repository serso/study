<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%--
  ~ Copyright (c) 2009-2010. Created by serso.
  ~
  ~ For more information, please, contact serso1988@gmail.com.
  --%>

<%--@elvariable id="messageCollector" type="org.solovyev.study.resources.MessageCollector"--%>
<c:if test="${messageCollector != null && fn:length(messageCollector.messages) > 0}">

	<table class="message-box-${messageCollector.errorLevel}">

		<c:forEach items="${messageCollector.messages}" var="message">
			<%--@elvariable id="message" type="org.solovyev.common.definitions.Message"--%>
			<c:if test="${message != null}">
				<tr class="message">
					<td>
						<s:message code="${message.messageCode}" arguments="${message.arguments}"/>
					</td>
				</tr>
			</c:if>
		</c:forEach>

		<% ((MessageCollector) pageContext.getAttribute("messageCollector")).clear(); %>
	</table>

	<br>
</c:if>