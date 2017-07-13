<div
	style="background: #022852; height: 100px; padding: 5px; min-width: 500px; text-align: center; margin-left: auto; margin-right: auto; overflow: hidden; position: relative;">

	<!--    <img  src="public/_images/ok_kutu.jpg" alt="kutu" /> 
    <h3 style="float: left; width: 50px; color: #FFF; font-size: 18px;margin:0">Jobs</h3> -->

	<!-- <div style="width:auto;"> -->
	<a style="position: absolute; top: 5px; left: 10px;"
		href="${pageContext.request.contextPath}/installation/list?deleted=0"><img class="logo"
		src="${pageContext.request.contextPath}/resources/img/BconeLogo_white.png"></a>
	<h2 style="color: white;">INSTALLATION
		TRACKER</h2>
	<div style="position: absolute; top: 1em; right: 1em; padding: 10px;">

                    <input type="text" size="30" maxlength="1000" value="" id="textBoxSearch" onkeyup="tableSearch.search(event);" />
                    <input type="button" value="Search" onclick="tableSearch.runSearch();" />
			 	     <div style="position: absolute; top: 3em; right: 1em; padding: 10px;">
			 	     	<a style="color: white;" href="${pageContext.request.contextPath}/logout.do">Logout</a>
			 	     </div>
   </div>
</div>
	
	


<script language="javascript" type="text/javascript">
        //define the table search as an object, which can implement both functions and properties
        window.tableSearch = {};

        //initialize the search, setup the current object
        tableSearch.init = function() {
            //define the properties I want on the tableSearch object
            this.Rows = document.getElementById('data').getElementsByTagName('TR');
            this.RowsLength = tableSearch.Rows.length;
            this.RowsText = [];
            
            //loop through the table and add the data to
            for (var i = 0; i < tableSearch.RowsLength; i++) {
                this.RowsText[i] = (tableSearch.Rows[i].innerText) ? tableSearch.Rows[i].innerText.toUpperCase() : tableSearch.Rows[i].textContent.toUpperCase();
            }
        }

        //onlys shows the relevant rows as determined by the search string
        tableSearch.runSearch = function() {
            //get the search term
            this.Term = document.getElementById('textBoxSearch').value.toUpperCase();
            
            //loop through the rows and hide rows that do not match the search query
            for (var i = 0, row; row = this.Rows[i], rowText = this.RowsText[i]; i++) {
                row.style.display = ((rowText.indexOf(this.Term) != -1) || this.Term === '') ? '' : 'none';
            }
        }

        //runs the search
        tableSearch.search = function(e) {
            //checks if the user pressed the enter key, and if they did then run the search
            var keycode;
            if (window.event) { keycode = window.event.keyCode; }
            else if (e) { keycode = e.which; }
            else { return false; }
            if (keycode == 13) {
                tableSearch.runSearch();
            }
            else { return false; }
        }
    </script>


