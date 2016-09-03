package com.dac.welang.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_languages")
public class Language {
	
	private int id;
	private String language;
	private int level;
	
	public Language(){
	}
	
	

	public Language(String language) {
		super();
		this.language = language;
	}



	public Language(String language, int level) {
		this.language = language;
		this.level = level;
	}
	


	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	@Column(name="user_language")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


	@Column(name="language_level")
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof UserAccount){
			Language lang = (Language) obj;
			return this.language.equals(lang.getLanguage());
		}
		return false;
	}



	@Override
	public String toString() {
		return "Language [id=" + id + ", language=" + language + ", level=" + level + "]";
	}
	
	
}
