<div style="background: #E0E0E0; height: 120px; padding: 5px;">
	<div style="float: left">
		<table>
			<tr>
				<td><a href="${pageContext.request.contextPath}/installation/list"><img
			class="logo"
			src="${pageContext.request.contextPath}/resources/img/Linux-icon.png"></a></td>
				<td><h2>APPLICATION FOR INSTALLATION TRACKING</h2></td>				
			</tr>
		</table>		
	</div>
	
	<div style="float: right; padding: 10px; text-align: right;">
 	    <table>
        <tbody>
            <tr>
                <td>
                    <input type="text" size="30" maxlength="1000" value="" id="textBoxSearch" onkeyup="tableSearch.search(event);" />
                    <input type="button" value="Search" onclick="tableSearch.runSearch();" />
                </td>
            </tr>
        </tbody>
    </table><br><br><br>
 	     <a href="${pageContext.request.contextPath}/logout.do">Logout</a>
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


