package com.dac.welang.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.dac.welang.model.Notification;

/**
 * Service class for sending notification messages.
 */
@Service
public class MessageService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  
  /**
   * Send notification to users subscribed on channel "/user/queue/notify".
   *
   * The message will be sent only to the user with the given username.
   * 
   * @param notification The notification message.
   * @param username The username for the user to send notification.
   */
  public void notify(Notification notification, String username) {
    messagingTemplate.convertAndSendToUser(
      username, 
      "/topic/app",
      notification
    );
    return;
  }
  
} // class NotificationService
