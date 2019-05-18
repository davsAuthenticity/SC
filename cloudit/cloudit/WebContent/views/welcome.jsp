<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="google-signin-client_id" content="619431868263-mjprd0u7gj0udamvhb9qradptg1n9cb6.apps.googleusercontent.com">
<title>Welcome Member</title>
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="stylesheet" href="css/reset.css"/>
<link rel="stylesheet" href="css/styles.css"/>
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
</head>
<body>
	<div class="profile">
		<div class="top"></div>
		<div class="middle">
			<div class="main-area">
				<div class="user">
					<div class="user-data">
						<label id="userName">${userName}</label>
						<br>
						<br>
						<img class="profile-image" alt="Profile Picture" src="${profileImg}">
						<br>
						<input type="hidden" id="uploadLink" name="uploadLink" value="${upLink}" />
						<br>
						<label id="userTitle">${title}</label>
						<br>
						<br>
						<textarea id="txtNotes" rows="7" cols="40" style="resize: none" class="text-notes">${notes}</textarea>
						<br>
						<button class="qrcode-button" id="btnNotes" name="btnNotes" onclick="myNotes(document.getElementById('txtNotes').value);">Add Notes</button>
					</div>
					<div class="user-actions">
						<br>
						<br>
						<br>
						<button class="scan-button" onclick="navigateToScan();">Scan QR / Barcode</button>
						<br>
						<br>
						<button class="scan-button" id="googleCloud" onclick="cloudUpload();">Upload to Google Cloud</button>
					</div>
				</div>
				<div class="functions">
					<div class="qrcode-list">
						<div class="block-qr">
							<label>List of QR Codes</label>
							<br>
							<select id="qrlist" name="qrlist" size="5" class="qr">
							<c:forEach var="qrcodes" items="${products}">
								<option value="${qrcodes.id}">${qrcodes.productDetails}</option>
							</c:forEach>
							</select>
						</div>
					</div>
					<div class="barcode-list">
						<div class="block-bar">
							<label>List of Bar Codes</label>
							<br>
							<select id="barcodelist" name="barcodelist" size="5" class="bar">
							    <option value="1">Bar code Item #1</option>
							    <option value="2">Bar code Item #2</option>
							    <option value="3">Bar code Item #3</option>
							    <option value="4">Bar code Item #4</option>
							    <option value="5">Bar code Item #5</option>
							</select>
						</div>
					</div>
					<div class="qr-bar-area">
						<div class="generate">
							<div class="convert-text">
								Convert : <input type="text" id="textToConvert" size="30" >
							</div>
							<div class="qr-bar-buttons">
								<div class="qrcode-create">
									<button class="qrcode-button" onclick="createQR();">Create QR code</button>
								</div>
								<div class="barcode-create">
									<button class="barcode-button" onclick="createBAR();">Create Barcode</button>
								</div>
							</div>
						</div>
						<div class="generated-image">
							<img id="qr-code" alt="QR Code" src="viewqr">
						</div>
					</div>
				</div>
				<div class="signout-area">
					<button class="signout-button" onclick="signOut();">Sign Out</button>
				</div>
			</div>
		</div>
		<div class="bottom"></div>
	</div>
	<script src="https://apis.google.com/js/platform.js" async defer></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
	<script>
		var userIdentification = 0;
	
		/*function signOut()
		{
			var auth2 = gapi.auth2.getAuthInstance();
		    auth2.signOut().then(function () {
		      console.log('User signed out.');
		      window.location.href="http://localhost:8080/cloudit";
		    });
		}*/
		/*function onLoad() 
		{
		      gapi.load('auth2', function() {
		        gapi.auth2.init();
		      });
		}*/
		
		function onLoad() 
		{
			gapi.load('auth2', function() {

				  gapi.auth2.init({

				    client_id: '619431868263-mjprd0u7gj0udamvhb9qradptg1n9cb6.apps.googleusercontent.com',

				  }).then(function(){

				    auth2 = gapi.auth2.getAuthInstance();
				    //console.log(auth2.isSignedIn.get()); //now this always returns correctly        
					if(auth2.isSignedIn.get())
					{
						//console.log(auth2.currentUser.get().getId());
						userIdentification = auth2.currentUser.get().getId();
					}
				  });
				});
		}
		
		function signOut()
		{
			var auth2 = gapi.auth2.getAuthInstance();
		    auth2.signOut().then(function () {
		      console.log('User signed out.');
		      
		      var redirectScan = "signout";
				
			  //using jQuery to post data to the server dynamically
			  var form = $('<form action="' + redirectScan + '" method="get">' +
			  '</form>');
				
			  $('body').append(form);
			  form.submit();
		      
		    });
		}
		function navigateToScan()
		{
			var redirectScan = "scan";
			
			//var uid = document.getElementById("userId").value;
			
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectScan + '" method="post">' +
					'<input type="text" name="uId" value="' +userIdentification +'" />' +
			'</form>');
			
			$('body').append(form);
			form.submit();
		}
		
		/*function changeImage()
		{
			document.getElementById("qr-code").src = "images/default-qr.png";
		}*/
		
		function createQR()
		{
			var textval = document.getElementById("textToConvert").value;
			//var uid = document.getElementById("userId").value;
			
			if(textval != "")
			{
				qrCodes(textval, userIdentification);
				//alert(textval+" "+uid);
			}
		}
		
		function qrCodes(textBoxVal, uid)
		{
			var qrText = textBoxVal;
			
			$.ajax({
				type: "post",
				url: "createqr", 
				data: { qrCodeText : qrText, userId : uid},
				success: function(data){ 
					loadImage();
				},
				error: function(){
				alert("error");
				}
			}); 
		}
		
		function loadImage()
		{
			document.getElementById("qr-code").src ="viewqr?t="+new Date().getTime()+"&id="+userIdentification;
		}
		
		/*function createQR()
		{
			var redirectUrl = "createqr";
			
			var textval = document.getElementById("textToConvert").value;
			
			if(textval != "")
			{
				//using jQuery to post data to the server dynamically
				var form = $('<form action="' + redirectUrl + '" method="post">' +
						'<input type="text" name="textToConvert" value="' +textval +'" />' +
						'</form>');
				
				$('body').append(form);
				form.submit();
			}
		}*/
		
// 		function addNotes()
// 		{
// 			//alert("Add Notes");
// 			var redirectUrl = "notes";
			
// 			var notesval = document.getElementById("txtNotes").value;
			
// 			//using jQuery to post data to the server dynamically
// 			var form = $('<form action="' + redirectUrl + '" method="post">' +
// 					'<input type="text" name="notesText" value="' +notesval +'" />' +
// 					'</form>');
			
// 			$('body').append(form);
// 			form.submit();

// 		}
		
		
		function myNotes(textAreaValue)
		{
			//var uid = document.getElementById("userId").value;
			finalNotes(textAreaValue, userIdentification);
		}
		
		function finalNotes(textBoxVal, uid){
			
			var notes = textBoxVal;
				
			$.ajax({
				type: "post",
				url: "updatenotes", 
				data: { newNotes : notes, userId : uid},
				success: function(data){ 
				$('textarea#txtNotes').val(data);
				},
				error: function(){
				alert("error");
				}
			}); 
		}
		
		$(document).ready(function(){
		     
		     var clientId = "619431868263-vekp7678mh01g7s0fn2p7rgasvhdc2vs.apps.googleusercontent.com";
		     var redirect_uri = "https://psyched-edge-235319.appspot.com/drive";
		     var scope = "https://www.googleapis.com/auth/drive";
		     var url = "";


		     // this is event click listener for the button

		     $("#googleDrive").click(function(){

		        signIn(clientId,redirect_uri,scope,url);

		     });

		     function signIn(clientId,redirect_uri,scope,url){
		      
		        // the actual url to which the user is redirected to 

		        url = "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri="+redirect_uri
		        +"&prompt=consent&response_type=code&client_id="+clientId+"&scope="+scope
		        +"&access_type=offline";
		        
		        /*url = "https://accounts.google.com/o/oauth2/v2/auth?redirect_uri="+redirect_uri
		        +"&prompt=consent&response_type=code&client_id="+clientId+"&scope="+scope
		        +"&access_type=offline&userid="+userIdentification;*/

		        // this line makes the user redirected to the url

		        window.location = url;
		        
		        /*var form = $('<form action="' + url + '" method="get">' +
						'<input type="text" name="userid" value="' +userIdentification +'" />' + 
				'</form>');
				
				$('body').append(form);
				form.submit();*/
		     }
		});
		
		function cloudUpload() {
				
			var redirectUrl = 'drive';
			var uploadUrl = document.getElementById("uploadLink").value;
			
			if(uploadUrl == '')
			{
				uploadUrl = '0';
			}
				
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectUrl + '" method="post">' +
				'<input type="text" name="uId" value="' +userIdentification +'" />' +
				'<input type="text" name="directLink" value="' +uploadUrl +'" />' +
				'</form>');
				
			$('body').append(form);
			form.submit();
	    }
		
	</script>
	<!-- <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>-->
</body>
</html>