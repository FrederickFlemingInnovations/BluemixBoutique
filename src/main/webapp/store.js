// index.js

var REST_DATA = 'api/itemlist';
var REST_ENV = 'api/dbinfo';
var KEY_ENTER = 13;
var selectedItem = "";
//Gets used to populate the DB *if* its empty.
var defaultItems = [
	{itemNumber:'F0001', name: 'Moon', imgsrc:'bluemoon.jpg', price: '110000000000000', description:'Here is a once in a blue moon opportunity to get your very own blue moon. It suddenly appeared before you! Get it before it turns to gold.'},
	{itemNumber:'F0002', name: 'Sky', imgsrc:'bluesky.jpg', price: '0.00', description:'I see nothing but blue sky, smiling at me. Get yours now. Go out. Look up.'},
	{itemNumber:'F0003', name: 'Spruce trees', imgsrc:'bluespruce.jpg', price: '199.00', description:'Get yours now! Plant it in the yard and have it ready for a holiday.'},
	{itemNumber:'F0004', name: 'Berries', imgsrc:'blueberries.jpg', price: '2.99', description:'Everybodys favourte berry! Get yours now while they are fresh'},
	{itemNumber:'F0005', name: 'Jeans', imgsrc:'bluejeans.jpg', price: '46.00', description: 'Comfortable clothing you can wear every day! Always in fashion.'},
	{itemNumber:'F0006', name: 'Cheese', imgsrc:'bluecheese.jpg', price: '12.00', description: 'Smells funky,  Tastes great.  Excellent with a nice bottle of red wine.'}
];

function loadItems(){
	console.log("Load Items");
	xhrGet(REST_DATA, function(data){
		console.log("Load Items result: " , data);
		document.getElementById("loading").innerHTML = "";
		var receivedItems = data.body || [];
		var items = [];
		var i;
		// Make sure the received items have correct format
		for(i = 0; i < receivedItems.length; ++i){
			var item = receivedItems[i];
			if(item && 'id' in item && 'name' in item){
				items.push(item);
			}
		}
		var hasItems = items.length;
		console.log("hasItems: " , hasItems);
		for(i = 0; i < items.length; ++i){
			addItem(items[i], !hasItems);
		}
		if(!hasItems){ //no items, populate it with default.
			items = defaultItems;
			function save(){
				if(!items.length){
					loadItems();
				}
				if(items.length){
					saveChange(items.shift(), save);
				}


			}
			save();
			console.log("here");

		}
	}, function(err){
		document.getElementById("loading").innerHTML = "";
		showDB2Error();
		console.error(err);
	});
}

function addItem(item, isNew){
	var row = document.createElement('tr');
	row.className = "box";
	var id = item && item.id;
	if(id){
		row.setAttribute('data-id', id);
	}
	row.innerHTML = "<span>namegoeshere</span>&nbsp;<a class = 'deleteItem' onclick='deleteItem(this)'><b>x</b></a></br><img class = 'thumbnail'  onclick='showDetail(this)' src = 'images/"+item.imgsrc+"'>"
	var table = document.getElementById('notes');
	table.appendChild(row);
	var textarea = row.firstChild;
	console.log("textarea: ", textarea);
	if(item){
		textarea.innerHTML = item.name;
	}
	row.isNew = !item || isNew;
	textarea.focus();
}

function showDB2Error(){
	var row = document.createElement('tr');
	row.className = "box";
	row.innerHTML = "<h3 style=\"color:red\">Unable to communicate with SQL Database. </br> </h3>Verify that a sqldb service with name \"bluemixboutiquesql\" is bound to this application. <br> A re-push of the application is required after binding the service to a existing application.";
	var table = document.getElementById('notes');
	table.appendChild(row);
}


function showDetail(catalognode){
	var row = catalognode.parentNode;
	xhrGet(REST_DATA + '?id=' + row.getAttribute('data-id'), function(data){
		document.getElementById('itemName').innerHTML = data.name;
		document.getElementById("itemDetailPic").src = "images/" + data.imgsrc;
		document.getElementById("itemDescription").innerHTML = data.description;
		document.getElementById("itemDetailPrice").innerHTML = data.price;

		document.getElementById("itemDetail").style.visibility="visible";
		selectedItem = data.itemNumber;
		loadComments(data.itemNumber);
		drawTweets(data.name + "");
	});
}

function deleteItem(deleteBtnNode){
	var row = deleteBtnNode.parentNode;
	console.log("deleting row: ", row);
	row.parentNode.removeChild(row);
	xhrDelete(REST_DATA + '?id=' + row.getAttribute('data-id'), function(){
	}, function(err){
		console.error(err);
	});
}

function onKey(evt){
	if(evt.keyCode == KEY_ENTER && !evt.shiftKey){
		evt.stopPropagation();
		evt.preventDefault();
		var row = evt.target.parentNode.parentNode;
		if(row.nextSibling){
			row.nextSibling.firstChild.firstChild.focus();
		}else{
			addItem();
		}
	}
}

function saveChange(data, callback){
console.log("items: " , data);

		xhrPost(REST_DATA, data, function(item){
			callback && callback();
		}, function(err){
			console.error(err);
		});

}

function toggleServiceInfo(){
	var node = document.getElementById('dbserviceinfo');
	node.style.display = node.style.display == 'none' ? '' : 'none';
}


loadItems();

