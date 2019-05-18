<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="google-signin-client_id" content="619431868263-mjprd0u7gj0udamvhb9qradptg1n9cb6.apps.googleusercontent.com">
<title>Home</title>
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="stylesheet" href="css/reset.css"/>
<link rel="stylesheet" href="css/styles.css"/>
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
</head>
<body>
	<div id="content" class="container">
		<header class="header">
			<div class="topleft">
				<input type="hidden" id="vLink" value="${param.vl}" />
				<input type="hidden" id="dLink" value="${param.dl}" />
				<input type="hidden" id="upLink" value="${directLink}" />
			</div>
			<div class="banner">
				<img class="banner-image" alt="Main-Banner" src="images/banner_new3.png">
			</div>	
			<div class="topright">
			
			</div>	
		</header>
		<main class="main">
			<div class="left">
				<div class="quote">
					The blockchain is custom-made for decentralizing trust and exchanging assets without central intermediaries. With the decentralization of trust, we will be able to exchange anything we own and challenge existing trusted authorities and custodians that typically held the keys to accessing our assets or verifying their authenticity.
					<br>
					<span class="author">- William Mougayar</span>
				</div>
			</div>
			<div class="middle">
			
			</div>
			<div class="right">
				<div class="sign-in">
					<div id="my-signin2" class="google-signin"></div>
				</div>
			</div>
		</main>
		<footer class="footer">
		
		</footer>
	</div>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
	<script>

		var vl = "";
		var dl = "";
		var ul = "";
	
		function onLoad() 
		{
		      gapi.load('auth2', function() {
		        gapi.auth2.init();
		      });
		}
		
	    function onSuccess(googleUser) {
			var profile = googleUser.getBasicProfile();
				
			var id = profile.getId();
			var name = profile.getName();
			var imageUrl = profile.getImageUrl();
			var email = profile.getEmail();
			var idTokenVal = googleUser.getAuthResponse().id_token;
			vl = document.getElementById("vLink").value;
			dl = document.getElementById("dLink").value;
			ul = document.getElementById("upLink").value;
			
			if(ul == "")
			{
				ul = '0';
			}
			
			var back = "no";
				
			var redirectUrl = 'login';
				
			//using jQuery to post data to the server dynamically
			var form = $('<form action="' + redirectUrl + '" method="post">' +
				'<input type="text" name="id" value="' +id +'" />' +
				'<input type="text" name="name" value="' +name +'" />' +
				'<input type="text" name="imageUrl" value="' +imageUrl +'" />' +
				'<input type="text" name="email" value="' +email +'" />' +
				'<input type="text" name="id_token" value="' +idTokenVal +'" />' +
				'<input type="text" name="back" value="' + back +'" />' +
				'<input type="text" name="viewLink" value="' + vl +'" />' +
				'<input type="text" name="downLink" value="' + dl +'" />' +
				'<input type="text" name="upLink" value="' + ul +'" />' +
				'</form>');
				
			$('body').append(form);
			form.submit();
	    }
	    function onFailure(error) {
	      console.log(error);
	    }
	    function renderButton() {
	      gapi.signin2.render('my-signin2', {
	        'scope': 'profile email',
	        'width': 240,
	        'height': 50,
	        'longtitle': true,
	        'theme': 'dark',
	        'onsuccess': onSuccess,
	        'onfailure': onFailure
	      });
	    }
	</script>
	<!-- <script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>-->
</body>
</html>