<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>

<html>

<head>
<title>Server Details</title>
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
			<th>Public IP</th>
			<th>Public Domain Name</th>
			<th>Public Open Ports</th>
			<th>Action</th>
		</tr>
		<tbody id="data">
			<!-- loop over and print our customers -->
			<c:forEach var="ipinfodetails" items="${ipInfoDetailsList}">

				<!-- construct an "update" link with customer id -->
				<c:url var="updateLink" value="/ip/showFormForUpdate">
					<c:param name="id" value="${ipinfodetails.id}" />
				</c:url>

				<!-- construct an "delete" link with customer id -->
				<c:url var="deleteLink" value="/ip/delete">
					<c:param name="id" value="${ipinfodetails.id}" />
				</c:url>
				
				<c:set var="keyString">${ipinfodetails.ip}</c:set>
				<c:set var="valueString">${hashMapInUseAndAvailablePortsPerIP[keyString]}</c:set>
				
				<tr>
					<td>${ipinfodetails.ip}</td>
					<td>${ipinfodetails.publicIp}</td>
					<td>${ipinfodetails.publicDomainName}</td>
					<td><b>In-Use :</b> <font color="white" style="background-color: red;">${hashMapInUseAndAvailablePortsPerIP[keyString].portInUse}</font><br>
						<b>Available :</b> <font color="white" style="background-color: green;">${hashMapInUseAndAvailablePortsPerIP[keyString].portAvailable}</font>
					</td>
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









