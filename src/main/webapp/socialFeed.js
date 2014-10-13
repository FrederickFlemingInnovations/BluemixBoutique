




// get some data, convert to JSON

function getTweets(name){
	dojo.xhrGet({
	    url:"Twitter?query=" +name,
	    handleAs:"json",
	    load: function(data){
	    	console.log(data);
	    	document.getElementById("tweetsDiv").innerHTML = "";
	        for(var i in data){
	           console.log("key", i, "value", data[i].text);
	           addTweet(data[i]);

	           
	        }
	    }
	});
}

function checkForClosedOrders(){
	//console.log(document.frames['TradeMainContent']);
	dojo.xhrGet({
	    url:"app?hiddenAction=checkClosedOrders",
	    handleAs:"text",
	    load: function(data){
	        if(data==="true")
	        	window.location = "app?action=home";
	    }
	    }
	);
}

function addTweet(tweet){
	var imgsrc = "images/bird_gray_32.png";
	if(tweet.picture){
		imgsrc = tweet.picture;
	}

    	dojo.create("div", { class:'social-item', innerHTML: "<img src = '"+imgsrc+"'> <div class='social-post'><b>"+ tweet.screenname + "</b>&nbsp;"+tweet.text+"</div>" }, document.getElementById("tweetsDiv"));
}

function clearTweets(){
	document.getElementById("tweetsDiv").innerHTML = "";
}

function drawTweets(name){
	
	clearTweets();
	getTweets(name);
};


	
