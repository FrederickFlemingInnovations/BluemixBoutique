<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel="stylesheet" href="style.css">
</head>
<script src="http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js"
    data-dojo-config="async: false"></script>

<body>

<div id="fade"></div>
	<script type="text/javascript" src="util.js"></script>
	<script type="text/javascript" src="store.js"></script>
	<script type="text/javascript" src="socialFeed.js"></script>
	<script type="text/javascript" src="store_comments.js"></script>
	
	<div id = "loginBox" class = "popupBox">
		<TABLE width="" class = "simpleborder">
			<TBODY>
				<TR>
					<TD width="2%" bgcolor="" rowspan="3"></TD>
					<TD width="100%"><B>Log in</B>
					</br>&nbsp;</br>
					
					</TD>
				</TR>
				<TR>
					<TD align="left"><FONT size="-1">Username &nbsp; &nbsp; &nbsp;&nbsp;
					&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Password </FONT></TD>
				</TR>
				<TR>
					<TD align="right">
	
					<FORM action="LoginServlet" method="POST"><INPUT size="10" type="text"
						name="uid" value="uid:0"> &nbsp; &nbsp; &nbsp; &nbsp; <INPUT
						size="10" type="password" name="passwd" value="xxx"> &nbsp;</br> 
						 <INPUT type="button" onclick="document.getElementById('loginBox').style.display='none';document.getElementById('fade').style.display='none'" value="Cancel">
						<INPUT type="submit" value="Log in">
						</br><INPUT type="hidden" name="action"
						value="login"></FORM>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</div>

	
		<div id = "addressBox" class = "popupBox">
		<TABLE width="" class = "simpleborder">
			<TBODY>
				<TR>
					<TD width="2%" bgcolor="" rowspan="3"></TD>
					<TD width="100%"><B>Ship To</B>
					</br>&nbsp;</br>
					
					</TD>
				</TR>
				<TR>
					<TD align="left"><FONT >Address:</FONT></TD>
				</TR>
				<TR>
					<TD align="left" colspan=2>
	
					<FORM action="" method="POST"><INPUT size="30" type="text"
						name="address" value="IBM Dr. Durham, NC"> &nbsp; &nbsp; &nbsp; &nbsp; 
						<br> <INPUT type="button" onclick="document.getElementById('addressBox').style.display='none';document.getElementById('fade').style.display='none'" value="Cancel">
						<INPUT type="submit" disabled value="Validate">
						</br><INPUT type="hidden" name="action"
						value="login">&nbsp; <i>Not implemented yet!</i></FORM>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</div>

<jsp:include page = "header.jsp"/>



	<p class="description">This sample demonstrates communication to SQLDB and Cloudant services</p>

	<div class="sectionHeader">
		<div class="sectionName"><span class = "blue">Catalog</span>
			(from sqldb)</div>
		<hr>
	</div>
	<div class="catalog">
		<table class="menu" id="notes">
			<!-- 
	        <div class="box" style=""><b>African Orchid</b></br>
				<img class = "thumbnail" src = "images/sqldb.jpg">
	        </div>
			-->
		</table>
		<div id="loading"><br>Loading...</div>
	</div>
	<div class="itemDetail" id = "itemDetail" style= "visibility:hidden;">
		<table>
			<tr>
				<td style="vertical-align:top;">
					<div class="box" style="">
						<img id="itemDetailPic" class = "imageLarge" src="images/bluesky.jpg" />
					</div>
					Select your color:
					<select>
  <option value="volvo">Blue</option>
</select>
					<h2><span>$</span><span id = "itemDetailPrice"></span></h2>
					<a class="button" onclick="document.getElementById('addressBox').style.display='block';document.getElementById('fade').style.display='block'" data-dojo-attach-point="topJoinButton">Validate Shipping</a>
				</td>
				<td class="itemDetailMiddle">
					<div class="itemName" id="itemName">Tulips</div>
					<div class="itemDescription" id = "itemDescription"></div> <br>
					<div  class="sectionHeader">
						<div class="sectionName"><span class = "blue">Comments</span> (from Cloudant)</div>
						<hr>
					</div>
					<br>
					<div  id = "commentsArea" class="Grid-cell comment">
                		Loading...
					</div>
					<div   class="Grid-cell comment">
						
                		<input class="newComment" type="textarea" onkeypress="newComment(this);" />
					</div>
					

				</td>
				<td class="itemDetailRight">
					<div data-dojo-attach-point="sectionHeader" class="sectionHeader">
						<div data-dojo-attach-point="sectionHeaderName"
							class="sectionName"><span class = "blue">Social Feed</span>&nbsp;(Twitter)</div>
						<hr>
				</div> </br>
				
<DIV align = "left"><img src = "images/bird_gray_32.png"/><b>&nbsp;Social Feed</b></div>


			<div id = "tweetsDiv" class="Grid-cell social">
                <div class="social-item">
                    <img src="images/profile1.jpg">
                    <div class="social-post">
                       omg, they grow soo fast, so pretty!
                    </div>
                </div>
                <div class="social-item">
                    <img src="images/profile2.jpg">
                    <div class="social-post">
                        Needs a lot of sun
                    </div>
                </div>
            </div>
                


				</td>
			</tr>
		</table>

	</div>
	<!-- 
    <div onClick = "toggleServiceInfo()"class="create-app tile masonrySelector" style="margin: 0 auto; width: 278px; "  >
 		<div class="plus-button"><div class="plus-sign">+</div></div>
		<span>Add a language</span>
	</div>
   -->
   
</body>

</html>