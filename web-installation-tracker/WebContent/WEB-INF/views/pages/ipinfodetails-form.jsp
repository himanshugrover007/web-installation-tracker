<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>

<head>
<title>Add Installations</title>

</head>

<script type="text/javascript">
function validateForm()
{
	//alert ("Hi");
	var tnum=document.getElementById('publicPorts').value; //get value of textarea
	if (trimAll(document.getElementById('publicPorts').value) === '')
	{	 
	 return true; // I think you forgot this
	}
	if (wrongFormat(tnum)){
	 alert("Only numbers and commas are allowed in Public Ports");
	 document.getElementById('publicPorts').focus();
	 return false;
	}
}
function trimAll(sString)
{
    while (sString.substring(0,1) == ' ')
    {
        sString = sString.substring(1, sString.length);
    }
    while (sString.substring(sString.length-1, sString.length) == ' ')
    {
        sString = sString.substring(0,sString.length-1);
    }
return sString;
}

function wrongFormat(s){
if (s.match(/[^0-9,]/)) return true; // with letters /[^0-9A-Za-z,]/
if (!s.charAt(0).match(/[0-9]/) || !s.charAt(s.length-1).match(/[0-9]/)) return true;
if (s.match(/[,][,]/)) return true; // check if there are 2 commas in a row
}
</script>

<body>
	<h2>Add Server Public IP, Public Domain and Open Ports Details</h2>


	<form:form action="saveIpInfoDetails" modelAttribute="ipInfoDetails"
		method="POST" onsubmit="return validateForm()">

		<!-- need to associate this data with customer id -->
		<form:hidden path="id" />

		<table>
			<tbody>
				<tr>
					<td><label>IP</label></td>
					<td><form:select path="ip">
							<form:option value="" label="--- Select ---" />
							<form:options items="${ipList}" />
						</form:select> <form:errors path="ip" cssClass="error" /></td>

				</tr>
				<tr>
					<td><label>Public IP</label></td>
					<td><form:input path="publicIp" size="25"/> <form:errors path="publicIp"
							cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><label>Public Domain Name</label></td>
					<td><form:input path="publicDomainName" size="50"/> <form:errors
							path="publicDomainName" cssClass="error" /></td>
				</tr>	
				
				<tr>
					<td><label>Public Ports</label></td>
					<td><form:textarea path="publicPorts" id ="publicPorts" rows="4" cols="50"/> <form:errors
							path="publicPorts" cssClass="error" /></td>
				</tr>			

				<tr>
					<td><label></label></td>
					<td><input type="submit" value="Save" class="save" /></td>
				</tr>


			</tbody>
		</table>


	</form:form>

	<p>
		<a href="${pageContext.request.contextPath}/ip/list">Back
			to List</a>
	</p>

</body>

</html>










