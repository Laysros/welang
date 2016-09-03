

  /**
   * Open the web socket connection and subscribe the "/notify" channel.
   */
  function connect() {

    // Create and init the SockJS object
    var socket = new SockJS('/sendChat');
    var stompClient = Stomp.over(socket);

    // Subscribe the '/notify' channell
    stompClient.connect({}, function(frame) {
      stompClient.subscribe('/user/queue/notify', function(notification) {

        // Call the notify function when receive a notification
        notify(JSON.parse(notification.body).content);

      });
    });
    
    return;
  } // function connect
  
  /**
   * Display the notification message.
   */
  function notify(message) {         
         $("#notifications-area").append(message + "\n");
    return;
  }
  
  $(document).ready(function() {    
    // Start the web socket connection.
    connect();
    
  });
