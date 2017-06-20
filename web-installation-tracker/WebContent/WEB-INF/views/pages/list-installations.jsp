<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>

<html>

<head>
<title>List Installations</title>
</head>
<style type="text/css">
/* 
Generic Styling, for Desktops/Laptops 
*/
table {
	width: 100%;
	border-collapse: collapse;
}
/* Zebra striping */
tr:nth-of-type(odd) {
	/* background: #eee;  */
	
}

th {
	background: #333;
	color: white;
	font-weight: bold;
}

td, th {
	/* padding: 6px;  */
	/*  border: 1px solid #ccc;  */
	text-align: left;
}
</style>

<body onload="tableSearch.init();">
	<table class="table" border="1">
		<tr>
			<th>IP</th>
			<th>Env.<br>Type
			</th>
			<th>VNC Port<br>
				Console Links
			</th>
			<th>Middleware Location<br>Status<br>Version<br>Bits Location</th>
			<th>Schema<br>Prefix
			</th>
			<th>Adm HTTP Port<br>
			Adm SSL Port<br>
			Man HTTP Port<br>
			Man SSL Port
			</th>		
			<th>Installed By<br>
				Members Using
			</th>
			<th>Action</th>
		</tr>
		<tbody id="data">
			<!-- loop over and print our customers -->
			<c:forEach var="tempInstallation" items="${installations}">

				<!-- construct an "update" link with customer id -->
				<c:url var="updateLink" value="/installation/showFormForUpdate">
					<c:param name="installationId" value="${tempInstallation.id}" />
				</c:url>

				<!-- construct an "delete" link with customer id -->
				<c:url var="deleteLink" value="/installation/delete">
					<c:param name="installationId" value="${tempInstallation.id}" />
				</c:url>
				
				<c:url var="markForUseFlag" value="/installation/useInstallation">
					<c:param name="installationId" value="${tempInstallation.id}" />
				</c:url>
				
				<c:url var="markForReleaseFlag" value="/installation/releaseInstallation">
					<c:param name="installationId" value="${tempInstallation.id}" />
				</c:url>

				<c:url var="console"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/console" />
				<c:url var="emconsole"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/em" />
				<c:url var="icsconsole"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/ics" />

				<c:set var="keyString">${tempInstallation.id}</c:set>
				<c:set var="valueString">${hashMapUserDetailsForEachInstallation[keyString]}</c:set>
				<c:set var="username">${loginForm.username}</c:set>

				<tr>
					<td>${tempInstallation.ip}<br>
					<c:if test="${not fn:containsIgnoreCase(valueString, username)}">
				        <a href="${markForUseFlag}">Use</a>
				    </c:if>
				    <c:if test="${fn:containsIgnoreCase(valueString, username)}">
				       <a href="${markForReleaseFlag}">Release</a>
				    </c:if>
					</td>
					<td>${tempInstallation.environmentType}</td>
					<td>VNC Port - ${tempInstallation.vncPort}<br>
						<a target="_blank" href="<c:out value="${console}"/>">Admin</a><br>
						<a target="_blank" href="<c:out value="${emconsole}"/>">EM</a><br>
						<a target="_blank" href="<c:out value="${icsconsole}"/>">ICS</a>
					</td>					
					<td>${tempInstallation.middlewareLocation}<br>
						Status - <c:if test="${tempInstallation.status == 'A'}">
							ACTIVE
						</c:if>
						<c:if test="${tempInstallation.status == 'I'}">
							INACTIVE
						</c:if><br>
						Version - ${tempInstallation.version}<br>
						Bits - ${tempInstallation.bitsLocation}
					</td>
					<td>${tempInstallation.schemaPrefix}</td>
					<td>Adm HTTP Port: ${tempInstallation.adminServerHTTPPort}<br>
						Adm SSL Port: ${tempInstallation.adminServerHTTPSPort}<br>
						Man HTTP Port: ${tempInstallation.managedServerHTTPPort}<br>
						Man SSL Port: ${tempInstallation.managedServerHTTPSPort}
					</td>
					<td><b>Installed By: ${fn:substringBefore(tempInstallation.installedBy, ".")}</b><br><br>
					<b>Members Using:</b><br>
					<c:forTokens var="token" items="${valueString}"
			                     delims=",">
			            <c:out value="${token}"/><br>
			        </c:forTokens></td>					
					<td>
						<!-- display the update link --> <a href="${updateLink}">Update</a><br>
						<a href="${deleteLink}"
						onclick="if (!(confirm('Are you sure you want to delete this Installation?'))) return false">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

</body>

</html>









