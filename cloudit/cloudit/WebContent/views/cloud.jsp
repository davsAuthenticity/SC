<%@ taglib prefix="c" uri ="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="google-signin-client_id" content="619431868263-mjprd0u7gj0udamvhb9qradptg1n9cb6.apps.googleusercontent.com">
<title>Insert title here</title>
<link rel="shortcut icon" href="favicon.ico"/>
<link rel="stylesheet" href="css/reset.css"/>
<link rel="stylesheet" href="css/styles.css"/>
<link href="https://fonts.googleapis.com/css?family=Titillium+Web" rel="stylesheet">
</head>
<body>
	<div class="cloud">
		<div class="top"></div>
		<div class="middle">
			<div class="upload-area">
				<div class="left-side">
					
				</div>
				<div class="center-area">
					<div class="file-upload">
					<form action="googlecloud" method="post" name="putFile" id="putFile"
				        enctype="multipart/form-data">
				        <input type="file" name="myFile" id="fileName">
				        <input type="hidden" id="userId" name="userId" value="${uid}" />
				        <input type="submit" value="Upload" class="upload-button">
				    </form>
				    <br>
						<button id="btnViewLink" name="btnViewLink" onclick="openFile()" class="view-button">Open File</button>
					<br>
					</div>
					<br>
					<div class="file-list">
						<div class="block-file">
							<label>List of Uploaded Files</label>
								<br>
									<select id="filelist" name="filelist" size="5" class="qr" onchange="openUploadedFile(this)">
										<c:forEach var="files" items="${files}">
											<option value="${files.id}">${files.directUrl}</option>
										</c:forEach>
									</select>
						</div>
					</div>
				</div>
				<div class="right-side">
					<button id="back" class="back-to-profile" onclick="navigateToProfile();">Back</button>
				</div>
			</div>
		</div>
		<div class="bottom"></div>
	</div>
	<input type="hidden" id="directUrl" name="directUrl" value="${directUrl}">
    <script src="https://apis.google.com/js/platform.js" async defer></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <script>
    
	    /*function handleFiles(files) {
	        for (var i = 0; i < files.length; i++) {
	            var file = files[i];
	            var filename = file.name;
	            $.getJSON("cloudupload", {
	                    filename: filename,
	                    content_type: file.type
	                },
	                uploadCallback(file)
	            );
	        }
	    }*/
	    
	    function onLoad() 
		{
		      var url = document.getElementById("directUrl").value;
		      
		      if(url == '0')
		      {
		    	  document.getElementById('btnViewLink').disabled = true;
		      }
		      esle
		      {
		    	  document.getElementById('btnViewLink').disabled = false;
		      }
		}
	    
	    function openFile()
	    {
	    	var url = document.getElementById("directUrl").value;
	    	
	    	window.open(url, "myWindow", 'width=800,height=600');
	    	//window.close();
	    }
	    
	    function openUploadedFile(link)
	    {	    
	    	var url = link.options[link.selectedIndex].text;
	    	window.open(url, "myWindow", 'width=800,height=600');
	    	//window.close();
	    	//alert(link);
	    }
	    
	    function handleFiles() {

	            var filename = document.getElementById("file").value;
	            
	            //alert(filename);
	            
	            $.ajax({
	            	type: "post",
					url: "cloudupload", 
					data: { filename: filename },
					success: function(data){ 
					alert(data);
					},
					error: function(){
					alert("error");
					}
	    		});

			//alert("hello");
	    }
	    
	    
	    
	    var uploadCallback = function (uploadedFile) {
            return function (data) {
                var url = data['url'];
                var key = data['key'];
                upload(url, uploadedFile, key);
            }
        };
        
        function upload(url, file, key) {
            $.ajax({
                url: url,
                type: 'PUT',
                data: file,
                contentType: file.type,
                success: function () {
                    $('#messages').append('<p>' + Date().toString() + ' : ' + file.name + ' ' + '<span id="' + key + '"' + '></span></p>');
                    //postUploadHandler(key)
                },
                error: function (result) {
                    console.log(result);
                },
                processData: false
            });
        }
        
        /*function postUploadHandler(key) {
            $.post('/postdownload/',
                {
                    key: key
                })
        }*/
    
    	function cloudUpload()
		{
    		var redirectUrl = 'cloudupload'; 
    		
			var filename = document.forms["putFile"]["file"].value;
			
			var form = $('<form action="' + redirectUrl + '" method="post">' +
					'<input type="text" name="fileTempName" value="' +filename +'" />' +
					'</form>');
					
				$('body').append(form);
				form.submit();
		}
    	
	    function setDownloadDefaults() {
        var url = location.search;
        var bucketArg = url.match(/bucket=[^&]*&/);
        if (bucketArg !== null) {
          document.getElementById("bucket").value = bucketArg.shift().slice(7, -1);
        }
        var fileArg = url.match(/fileName=[^&]*&/);
        if (fileArg !== null) {
          document.getElementById("fileName").value = fileArg.shift().slice(9, -1);
        }
      }
      function changeGetPath() {
        var bucket = document.forms["getFile"]["bucket"].value;
        var filename = document.forms["getFile"]["fileName"].value;
        if (bucket == null || bucket == "" || filename == null || filename == "") {
          alert("Both Bucket and FileName are required");
          return false;
        } else {
          document.submitGet.action = "/gcs/" + bucket + "/" + filename;
        }
      }
      function uploadFile() {
        var bucket = document.forms["putFile"]["bucket"].value;
        var filename = document.forms["putFile"]["fileName"].value;
        if (bucket == null || bucket == "" || filename == null || filename == "") {
          alert("Both Bucket and FileName are required");
          return false;
        } else {
          var postData = document.forms["putFile"]["content"].value;
          document.getElementById("content").value = null;
          var request = new XMLHttpRequest();
          request.open("POST", "/gcs/" + bucket + "/" + filename, false);
          request.setRequestHeader("Content-Type", "text/plain;charset=UTF-8");
          request.send(postData);
        }
      }
      
      function navigateToProfile()
	  {
		var redirectBack = "/";
					
			//using jQuery to post data to the server dynamically
		var form = $('<form action="' + redirectBack + '" method="post">' +
			'</form>');
				
		$('body').append(form);
		form.submit();
	  }
    </script>
</body>
</html>