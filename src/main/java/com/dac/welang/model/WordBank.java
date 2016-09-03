package com.dac.welang.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WordBank {
	@Id
	@GeneratedValue
	private int id;
	private String languageId;
	private String phrase;
	private String tag;
	
	
	
	
	public WordBank() {
	}


	public WordBank(String phrase, String tag) {
		this.phrase = phrase;
		this.tag = tag;
	}
	
	
	public WordBank(String languageId, String phrase, String tag) {
		super();
		this.languageId = languageId;
		this.phrase = phrase;
		this.tag = tag;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}
	
	
	
	
}
