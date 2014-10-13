var COMMENTS_DATA = 'CommentsServlet';
function loadComments(){

	xhrGet(COMMENTS_DATA + "?action=getComments&itemNumber=" + selectedItem, function(data){
		var commentsArea = document.getElementById("commentsArea")
		commentsArea.innerHTML = "";

		for(i = 0; i < data.length; ++i){
			var commentItem = document.createElement('div');
			commentItem.setAttribute('class', 'comment-item');
			var commentPost = document.createElement('div');
			commentPost.setAttribute('class', 'comment-post');


			commentPost.innerHTML = data[i].comment;

			commentItem.appendChild(commentPost);
			commentsArea.appendChild(commentItem);
		}

	}, function(err){
		console.error(err);
		var commentsArea = document.getElementById("commentsArea")
		commentsArea.innerHTML = "<h3 style=\"color:red\">Unable to communicate with Cloudant. </br> </h3>Verify that the nosqldb service with name \"bluemixboutiquesql\" is bound to this application. <br> A re-push of the application is required after binding the service to a existing application.";
	});

}


function newComment(comment) {

	var key = window.event.keyCode;

    if (key == 13) { //enter key
    	xhrGet(COMMENTS_DATA + "?action=addComment&itemNumber="+selectedItem+"&comment=" + comment.value , function(data){
    		loadComments();
    	});
    	comment.value = "";
        return false;
    }
    else {
        return true;
    }
}