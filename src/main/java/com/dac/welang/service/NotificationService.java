package com.dac.welang.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dac.welang.model.Notification;
import com.dac.welang.model.UserAccount;

/**
 * Service class for sending notification messages.
 */
@Service
public class NotificationService {
  
  // The SimpMessagingTemplate is used to send Stomp over WebSocket messages.
  @Autowired
  private SimpMessagingTemplate messagingTemplate;
  
  private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender javaMailSender){
		this.javaMailSender = javaMailSender;
	}
  
	@Async
	public void sendContactMessage(UserAccount user, String subject, String message) throws InterruptedException{
		
		System.out.println("Sleeping now...");
        Thread.sleep(10000);
        System.out.println("Sending email...");
        
        SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("panda220v@gmail.com");
		mail.setFrom(user.getEmail());
		mail.setSubject(subject);
		mail.setText("From: " + user.getUsername() + "\nEmail:"+ user.getEmail() + "\n\n"+message);
		javaMailSender.send(mail);
		
		System.out.println("Email Sent!");
	}
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
      "/queue/notify", 
      notification
    );
    return;
  }

	public void notifyMessage(Notification notification, String toWhom) {
		messagingTemplate.convertAndSendToUser(
					toWhom, 
			      "/queue/notifyMessage", 
			      notification
			    );
			    return;
		
	}
	
	public void notifyAddFriend(Notification notification, String toWhom) {
		messagingTemplate.convertAndSendToUser(
					toWhom, 
			      "/queue/notifyAddFriend", 
			      notification
			    );
			    return;		
	}
	
	public void notifyDialogue(Notification notification, String toWhom) {
		System.out.println("SEnding NOtification to " + toWhom);
		messagingTemplate.convertAndSendToUser(
					toWhom, 
			      "/queue/notifyAddFriend", 
			      notification
			    );
			    return;		
	}

  
}
