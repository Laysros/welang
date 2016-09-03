package com.dac.welang.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Notification {

  private String message="";
  private Long fromWhom=0L;
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="America/Phoenix")
  private Date sentDate;
  
  


public Notification(Long fromWhom) {
	this.fromWhom = fromWhom;
}

public Notification(String message, Long fromWhom) {
	this.message = message;
	this.fromWhom = fromWhom;
}

public Notification(String message, Long fromWhom, Date sentDate) {
	super();
	this.message = message;
	this.fromWhom = fromWhom;
	this.sentDate = sentDate;
}

public Notification (String content) {
    this.message = content;
  }


public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public Long getFromWhom() {
	return fromWhom;
}

public void setFromWhom(Long fromWhom) {
	this.fromWhom = fromWhom;
}

public Date getSentDate() {
	return sentDate;
}

public void setSentDate(Date sentDate) {
	this.sentDate = sentDate;
}


}
