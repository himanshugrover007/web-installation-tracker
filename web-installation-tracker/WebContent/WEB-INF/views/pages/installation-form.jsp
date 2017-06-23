<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
<title>Add Installations</title>

</head>

<script type="text/javascript">
(function($) {
	$(document).ready(function(){		
	var v = $("#environmentType option:selected")[0].value;
			toggleFields();
			$('#environmentType').change(function() {
				var v = $("#environmentType option:selected")[0].value;
				toggleFields();
			});
		});
	})(jQuery);

	function toggleFields() {
		var v = $("#environmentType option:selected")[0].value;
		if (v === '' || v === 'ICS IC' || v === 'ICS EC') {
			$('.onpremise').hide();
		} else {
			$('.onpremise').show();
		}
	}
</script>


<body>
	<h2>Add Installation Details</h2>


	<form:form action="saveInstallation" modelAttribute="installation"
		method="POST">

		<!-- need to associate this data with customer id -->
		<form:hidden path="id" />

		<table>
			<tbody>
				<tr>
					<td><label>IP:</label></td>
					<td><form:select path="ip">
							<form:option value="" label="--- Select ---" />
							<form:options items="${ipList}" />
						</form:select> <form:errors path="ip" cssClass="error" /></td>

				</tr>
				<tr>
					<td><label>Environment Type:</label></td>
					<td><form:select path="environmentType" id="environmentType">
							<form:option value="" label="--- Select ---" />
							<form:options items="${environmentTypeList}" />
						</form:select> <form:errors path="environmentType" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><label>Status:</label></td>
					<td><form:select path="status">
							<form:option value="" label="--- Select ---" />
							<form:options items="${installationStatuses}" />
						</form:select> <form:errors path="status" cssClass="error" /></td>

				</tr>

				<tr>
					<td><label>Version:</label></td>
					<td><form:input path="version" size="25"/> <form:errors path="version"
							cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><label>Middleware Location</label></td>
					<td><form:input path="middlewareLocation" size="50"/> <form:errors
							path="middlewareLocation" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><label>Bits Location</label></td>
					<td><form:input path="bitsLocation" size="50"/> <form:errors
							path="bitsLocation" cssClass="error" /></td>
				</tr>				

				<tr>
					<td><label>Schema Prefix:</label></td>
					<td><form:input path="schemaPrefix" size="25"/> <form:errors
							path="schemaPrefix" cssClass="error" /></td>
				</tr>

				<tr>
					<td><label>Admin Server HTTP Port:</label></td>
					<td><form:input path="adminServerHTTPPort" size="25"  maxlength="4"/> <form:errors
							path="adminServerHTTPPort" cssClass="error" /></td>
				</tr>

				<tr>
					<td><label>Admin Server HTTPS Port:</label></td>
					<td><form:input path="adminServerHTTPSPort" size="25"  maxlength="4"/> <form:errors
							path="adminServerHTTPSPort" cssClass="error" /></td>
				</tr>

				<tr>
					<td><label class="onpremise">SOA -&nbsp</label><label>Managed Server HTTP Port:</label></td>
					<td><form:input path="managedServerHTTPPort" size="25" maxlength="4"/> <form:errors
							path="managedServerHTTPPort" cssClass="error" /></td>
				</tr>

				<tr>
					<td><label class="onpremise">SOA -&nbsp</label><label>Managed Server HTTPS Port:</label></td>
					<td><form:input path="managedServerHTTPSPort" size="25" maxlength="4"/> <form:errors
							path="managedServerHTTPSPort" cssClass="error" /></td>
				</tr>
				
				<tr class="onpremise">
					<td><label>OSB - Managed Server HTTP Port:</label></td>
					<td><form:input path="managedServer2HTTPPort" size="25" maxlength="4"/> <form:errors
							path="managedServer2HTTPPort" cssClass="error" /></td>
				</tr>

				<tr class="onpremise">
					<td><label>OSB - Managed Server HTTPS Port:</label></td>
					<td><form:input path="managedServer2HTTPSPort" size="25" maxlength="4"/> <form:errors
							path="managedServer2HTTPSPort" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><label>VNC Port:</label></td>
					<td><form:input path="vncPort" size="25"  maxlength="1"/> <form:errors
							path="vncPort" cssClass="error" /></td>
				</tr>

				<%-- <tr>
						<td><label>Installed By</label></td>
						<td><form:input path="installedBy" /></td>
					</tr>
 --%>
				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Save" class="save" /></td>
				</tr>


			</tbody>
		</table>


	</form:form>

	<p>
		<a href="${pageContext.request.contextPath}/installation/list">Back
			to List</a>
	</p>

</body>

</html>










