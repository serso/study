<%@ page import="org.solovyev.study.model.PartnerRole" %>
<script type="text/javascript">
function checkPartnerDetailsForRole() {
	var partnerRoles = new Object();
	<c:forEach items="<%=PartnerRole.values()%>" var="partnerRole" varStatus="partnerRoleStatus">
	partnerRoles["${partnerRole}"] = '${partnerRole.partnerDetailsApplicable}';
	</c:forEach>

	var selectedPartnerRole = document.getElementById('partnerRoles').value;

	if (partnerRoles[selectedPartnerRole] == 'true') {
		enableInputById('editSchoolDetails');
	} else {
		disableInputById('editSchoolDetails');
	}
}
</script>
