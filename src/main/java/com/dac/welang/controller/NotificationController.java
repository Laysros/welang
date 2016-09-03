package com.dac.welang.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dac.welang.model.Notification;
import com.dac.welang.service.NotificationService;

@Controller
public class NotificationController {

  @Autowired
  private NotificationService notificationService;

  /**
   * GET  /  -> show the index page.
   */
  @RequestMapping("/notification_page")
  public String index() {
    return "notification_page";
  }

  /**
   * GET  /notifications  -> show the notifications page.
   */
  @RequestMapping("/notifications")
  public String notifications() {
    return "notifications";
  }

  /**
   * POST  /some-action  -> do an action.
   * 
   * After the action is performed will be notified UserA.
   */
  @RequestMapping(value = "/some-action", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<?> someAction() {

    // Do an action here
    // ...
    
    // Send the notification to "UserA" (by username)
    notificationService.notify(
      new Notification("hello"), // notification object
      "UserA"                    // username
    );
    
    // Return an http 200 status code
    return new ResponseEntity<>(HttpStatus.OK);
  }

} // class MainController
