package com.dac.welang.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class ChatContent {
	@Id
	@GeneratedValue
	private Long id;
	private Long fromId;
	private Long toId;
	private String message;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="America/Phoenix")
	private Date sentDate;
	private boolean seen;
	
	
	
	public ChatContent() {
	}


	public ChatContent(Long fromId, Long toId, String message, Date sentDate) {
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
		this.sentDate = sentDate;
	}


	public ChatContent(Long fromId, Long toId, String message, Date sentDate, boolean seen) {
		this.fromId = fromId;
		this.toId = toId;
		this.message = message;
		this.sentDate = sentDate;
		this.seen = seen;
	}
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getFromId() {
		return fromId;
	}


	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}


	public Long getToId() {
		return toId;
	}


	public void setToId(Long toId) {
		this.toId = toId;
	}


	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public boolean isSeen() {
		return seen;
	}
	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	
	
	
	
	
}
