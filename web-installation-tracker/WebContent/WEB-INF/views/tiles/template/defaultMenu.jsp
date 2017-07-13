<style>
h1 {
  font-family: Helvetica;
  font-weight: 100;
}
body {
  color:#333;
  text-align:center;
  font-family: arial;
}

.nav {
	margin: 0px;
	padding: 0px;
	list-style: none;
}

.nav li {
	float: left;
	width: 160px;
	position: relative;
}

.nav li a {
	background: #333;
	color: #fff;
	display: block;
	padding: 7px 8px;
	text-decoration: none;
	border-top: 1px solid #069;
}

.nav li a:hover {
	color: #069;
}

/*=== submenu ===*/

.nav ul {
	display: none;
	position: absolute;
	margin-left: 0px;
	list-style: none;
	padding: 0px;
}

.nav ul li {
	width: 160px;
	float: left;
}

.nav ul a {
	display: block;
	height: 50px;
	padding: 7px 8px;
	color: #fff;
	text-decoration: none;
	border-bottom: 1px solid #222;
}

.nav ul li a:hover {
	color: #069;
}
</style>

<script type="text/javascript">
$(document).ready(
  /* This is the function that will get executed after the DOM is fully loaded */
  function () {
    /* Next part of code handles hovering effect and submenu appearing */
    $('.nav li').hover(
      function () { //appearing on hover
        $('ul', this).fadeIn();
      },
      function () { //disappearing on hover
        $('ul', this).fadeOut();
      }
    );
  }
);
</script>
<body>
	<div class="navigation">
		<ul class="nav">
			<li><a href="#">Installation</a>
				<ul>
					<li><a
						href="${pageContext.request.contextPath}/installation/list?deleted=0">Available For Use</a></li>
					<li><a
						href="${pageContext.request.contextPath}/installation/list?deleted=1">Marked For Deletion</a></li>
					<li><a
						href="${pageContext.request.contextPath}/installation/showFormForAdd">Add
							Detail</a></li>
				</ul></li>
			<li><a href="#">Server Info</a>
				<ul>
					<li><a href="${pageContext.request.contextPath}/ip/list">Open
							Ports and IP Details</a></li>
					<li><a
						href="${pageContext.request.contextPath}/ip/showFormForAdd">Add
							Detail</a></li>
				</ul></li>
		</ul>
	</div>
</body>