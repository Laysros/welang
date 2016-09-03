

  /**
   * Open the web socket connection and subscribe the "/notify" channel.
   */
  function connect() {

    // Create and init the SockJS object
    var socket = new SockJS('/sendChat');
    var stompClient = Stomp.over(socket);

    // Subscribe the '/notify' channell
    stompClient.connect({}, function(frame) {
      stompClient.subscribe('/user/queue/notifyMessage', function(notification) {

        // Call the notify function when receive a notification
        notify(JSON.parse(notification.body).message, JSON.parse(notification.body).fromWhom);

      });
    });
    
    return;
  } // function connect
  function connect2() {

	    // Create and init the SockJS object
	    var socket = new SockJS('/sendChat');
	    var stompClient = Stomp.over(socket);

	    // Subscribe the '/notify' channell
	    stompClient.connect({}, function(frame) {
	      stompClient.subscribe('/user/queue/notifyAddFriend', function(notification) {
	    	  
	        // Call the notify function when receive a notification
	    	  if(JSON.parse(notification.body).fromWhom == '-99'){
	    		  notifySettingDialogue(JSON.parse(notification.body).message);  
	    	  }else{
	    		  notifyAddFriend(JSON.parse(notification.body).fromWhom);  
	    	  }
	    	  

	      });
	    });
	    
	    return;
	  } // function connect
	  
  /**
   * Display the notification message.
   */
  function notify(message, fromWhom) {
	  	var response = document.getElementById("message_list_" + fromWhom);
	  	var chatCount = document.getElementById("non-read-message" + fromWhom);
	  	chatCount.innerHTML++;
	  	
		 var li = document.createElement('li');
		 var sp = document.createElement('span');
		 sp.className= 'left';
		 sp.innerHTML  = message;
		 li.appendChild(sp);
         response.appendChild(li);
         var d = getCurrentTime();
         setMessage(d, message,"left");
         
         
         var scroll = document.getElementById('messages'
					+ fromWhom);
			scroll.scrollTop = scroll.scrollHeight;
			scroll.animate({
				scrollTop : scroll.scrollHeight
			});
			var style = document.getElementById(fromWhom).style.display;
			if(style == "block"){
				chatCount.innerHTML="0";
				commandSetSeenMessage(fromWhom);
			}
    return;
  }
  
  function notifySettingDialogue(message){
	  document.getElementById("dialogue-content-a").innerHTML = message;
  }
  
  function addZero(i) {
	    if(i.length == 1) {
	        i = "0" + i;
	    }
	    return i;
	}

	function getCurrentTime() {
	    var d = new Date();
	    var h = addZero(d.getHours());
	    var m = addZero(d.getMinutes());
	    return h + ":" + m;
	}
  function notifyAddFriend(fromWhom) {
	  var request = document.getElementById("num-request");
	  request.style.color = 'red';
	  var num = request.textContent;
	  num = parseInt(num);
	  num+=1;
	  request.textContent = num;
  return;
}
  
  $(document).ready(function() {    
    // Start the web socket connection.
    connect();
    connect2();
  });
