package com.dac.welang.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Dialogue {
	private Long id;
	private String language;
	private int likeCount=0;
	private List<Conversation> conversations;
	
	
	
	public Dialogue() {
		
	}
	
	
	


	public Dialogue(String language, List<Conversation> conversations) {
		this.language = language;
		this.conversations = conversations;
	}





	public Dialogue(Long id, String language, int likeCount, List<Conversation> conversations) {
		this.id = id;
		this.language = language;
		this.likeCount = likeCount;
		this.conversations = conversations;
	}



	@Id
	@GeneratedValue
	@Column(name="dialogue_id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="dialogue_language")
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="dialogue_id", nullable=false)
	public List<Conversation> getConversations() {
		return conversations;
	}
	public void setConversations(List<Conversation> conversations) {
		this.conversations = conversations;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	
	
	
	
	
}
