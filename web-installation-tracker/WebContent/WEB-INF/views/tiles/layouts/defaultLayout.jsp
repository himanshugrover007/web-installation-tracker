<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title><tiles:getAsString name="title" /></title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<style type="text/css">
a.bg-primary {
	padding: 15px 10px;
}

a.bg-primary:hover, a.bg-primary:focus {
	color: white;
}

.tagline {
	margin-bottom: 100px;
}
a {
  font: bold italic 100%/1.4 Georgia, serif;
  color: #00688B;
}
</style>
</head>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<body>
    <table width="100%">
        <tr>
            <td colspan="2">
                <tiles:insertAttribute name="header" />
            </td>
        </tr>
        <tr>
            <td bgcolor="#022852">
                 <tiles:insertAttribute name="menu" />
             </td>
        </tr>
        <tr valign="top" height="350px">
           <!-- style="padding: 6px;" -->
            <td width="100%" style="padding: 0px 2px 2px 2px;">
                 <tiles:insertAttribute name="body" />
             </td>
        </tr>
        <tr>
            <td>
                 <tiles:insertAttribute name="footer" />
            </td>
        </tr>
    </table>
</body>
</html>