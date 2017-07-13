<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>

<html>

<head>
<c:if test="${deleted=='0'}">
	<title>INSTALLATION LIST</title>
</c:if>
<c:if test="${deleted=='1'}">
	<title>INSTALLATION MARKED FOR DELETION</title>
</c:if>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js">
 </script>
<script>
 $(document).ready(function(){
 setInterval(function(){cache_clear()},10000);
 });
 function cache_clear()
{
 window.location.reload(true);
 // window.location.reload(); use this if you do not remove cache
}
</script> -->
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
	background: #00688B;
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
			<th>Admin and<br>
			Managed Servers<br>
			HTTP/HTTPS<br>
			Ports
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
				
				<c:url var="blockUnblockDemo" value="/installation/blockUnblockForDemo">
					<c:param name="installationId" value="${tempInstallation.id}" />
				</c:url>

				<c:url var="console"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/console" />
				<c:url var="emconsole"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/em" />
				<c:url var="icsconsole"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/ics" />
				<c:url var="ecicsconsole"
					value="http://${tempInstallation.ip}:${tempInstallation.managedServerHTTPPort}/ic/integration/home/faces/icslogin.jspx" />
				<c:url var="sbconsole"
					value="http://${tempInstallation.ip}:${tempInstallation.adminServerHTTPPort}/sbconsole" />
										
				<c:set var="keyString">${tempInstallation.id}</c:set>
				<c:set var="valueString">${hashMapUserDetailsForEachInstallation[keyString]}</c:set>
				<c:set var="username">${loginForm.username}</c:set>
				<c:set var="soaenvironment">
						<c:choose>         
					         <c:when test = "${tempInstallation.environmentType=='SOA 11G' || tempInstallation.environmentType=='SOA 12C'}">
					            SOA 
					         </c:when>					         
					         <c:otherwise>
					           Man
					         </c:otherwise>
					      </c:choose>
				</c:set>
				
					<c:if test="${tempInstallation.demoMachine=='0'}">
					   	 <tr>
					</c:if>
				  	<c:if test="${tempInstallation.demoMachine=='1'}">
					    	<tr style="background-color: #FF0000; color:white;">
					</c:if>
					<td>${tempInstallation.ip}<br>
					<c:if test="${deleted=='0'}">
						<c:if test="${not fn:containsIgnoreCase(valueString, username)}">
					        <a href="${markForUseFlag}">Use</a>
					    </c:if>
					    <c:if test="${fn:containsIgnoreCase(valueString, username)}">
					       <a href="${markForReleaseFlag}">Release</a>
					    </c:if>
				    </c:if>
					</td>
					<td>${tempInstallation.environmentType}</td>
					<td>VNC Port - ${tempInstallation.vncPort}<br>
						<a target="_blank" href="<c:out value="${console}"/>">Admin</a><br>
						<a target="_blank" href="<c:out value="${emconsole}"/>">EM</a><br>
						<c:if test="${tempInstallation.environmentType=='ICS IC'}">
				    		<a target="_blank" href="<c:out value="${icsconsole}"/>">ICS</a>
						</c:if>
						<c:if test="${tempInstallation.environmentType=='ICS EC'}">
				    		<a target="_blank" href="<c:out value="${ecicsconsole}"/>">ICS</a>
						</c:if>					
						
						<c:if test="${tempInstallation.environmentType=='SOA 12C' || tempInstallation.environmentType=='SOA 11G'}">
							<c:if test="${not empty tempInstallation.managedServer2HTTPPort && not empty tempInstallation.managedServer2HTTPSPort}">
				    		<a target="_blank" href="<c:out value="${sbconsole}"/>">Service Bus</a>
				    		</c:if>
						</c:if>						
					</td>					
					<td>${tempInstallation.middlewareLocation}<br>
						<b>Status -</b>  <c:if test="${tempInstallation.status == 'A'}">
							<font color="white" style="background-color: green;">ACTIVE</font>
						</c:if>
						<c:if test="${tempInstallation.status == 'I'}">
							<font color="white" style="background-color: red;">INACTIVE</font>
						</c:if><br>
						Version - ${tempInstallation.version}<br>
						Bits - ${tempInstallation.bitsLocation}
					</td>
					<td>${tempInstallation.schemaPrefix}</td>
					<td>Adm HTTP Port: ${tempInstallation.adminServerHTTPPort}<br>
						Adm SSL Port: ${tempInstallation.adminServerHTTPSPort}<br>
      					${soaenvironment} HTTP Port: ${tempInstallation.managedServerHTTPPort}<br>
						${soaenvironment} SSL Port: ${tempInstallation.managedServerHTTPSPort}<br>
						<c:if test="${tempInstallation.environmentType=='SOA 11G' || tempInstallation.environmentType=='SOA 12C'}">
						<c:if test="${not empty tempInstallation.managedServer2HTTPPort && not empty tempInstallation.managedServer2HTTPSPort}">
							OSB HTTP Port: ${tempInstallation.managedServer2HTTPPort}<br>
							OSB SSL Port: ${tempInstallation.managedServer2HTTPSPort}
							</c:if>
						</c:if>
					</td>
					<td> <c:if test="${tempInstallation.demoMachine=='1'}">
					    	 <b>Blocked for Demo By: ${fn:substringBefore(tempInstallation.demoUser, ".")}</b><br>
					    </c:if>
					<b>Installed By: ${fn:substringBefore(tempInstallation.installedBy, ".")}</b><br><br>
					<b>Members Using:</b><br>
					<c:forTokens var="token" items="${valueString}"
			                     delims=",">
			            <c:out value="${token}"/><br>
			        </c:forTokens></td>					
					<td>
						<!-- display the update link -->
						<c:if test="${deleted=='0'}">
						 <c:if test="${tempInstallation.demoMachine=='0'}">
						    	 <a href="${blockUnblockDemo}">Block For Demo</a><br>
						    </c:if>
						     <c:if test="${tempInstallation.demoMachine=='1'}">
						    	 <a href="${blockUnblockDemo}">Demo Complete</a><br>
						    </c:if>
							<a href="${updateLink}">Update</a><br>
							<a href="${deleteLink}"
						onclick="if (!(confirm('Are you sure you want to MARK this Installation for deletion?'))) return false">Delete</a>
						</c:if>
						<c:if test="${deleted=='1'}">
						<a href="${deleteLink}"
						onclick="if (!(confirm('Are you sure you want to PERMANENTLY delete this Installation?'))) return false">Delete</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>

	</table>

</body>

</html>









