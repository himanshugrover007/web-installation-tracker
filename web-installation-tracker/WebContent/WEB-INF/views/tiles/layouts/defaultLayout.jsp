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

#coolMenu {
    font-family: Arial;
    font-size: 12px;
    background: #2f8be8;
}
#coolMenu > li > a {
    color: #fff;
    font-weight: bold;
}
#coolMenu > li:hover > a {
    background: #f09d28;
    color: #000;
}

#coolMenu ul {
    background: #f09d28;
}
#coolMenu ul li a {
    color: #000;
}
#coolMenu ul li:hover a {
    background: #ffc97c;
}
</style>
</head>
 
<body>
    <table width="100%">
        <tr>
            <td colspan="2">
                <tiles:insertAttribute name="header" />
            </td>
        </tr>
        <tr>
            <td>
                 <tiles:insertAttribute name="menu" />
             </td>
        </tr>
        <tr>
           
            <td width="100%" style="padding: 6px;">
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