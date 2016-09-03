package com.dac.welang.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_conversation")
public class Conversation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String title;
	private String tag;
    @ElementCollection
    @Column(length=1024)
	private List<String> contentA;
    @ElementCollection
    @Column(length=1024) 
	private List<String> contentB;
	
	
	
	
	public Conversation() {
	}
	public Conversation(String title, String tag, List<String> contentA, List<String> contentB) {
		this.title = title;
		this.tag = tag;
		this.contentA = contentA;
		this.contentB = contentB;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public List<String> getContentA() {
		return contentA;
	}
	public void setContentA(List<String> contentA) {
		this.contentA = contentA;
	}
	public List<String> getContentB() {
		return contentB;
	}
	public void setContentB(List<String> contentB) {
		this.contentB = contentB;
	}
	
	
	
	
}
