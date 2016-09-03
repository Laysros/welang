package com.dac.welang.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name="user_settings")
public class UserSettings{
	private Integer id;
	private String profileImage = "profile.png";
	private String description;
	@DateTimeFormat(pattern="mm/dd/yyyy")
	private Date dateOfBirth;
	private String nativeLanguage;
	private Date joinDate = new Date();
	private List<Language>foreignLanguages;
	
	
	public UserSettings() {
		
	}

	public UserSettings(String description, List<Language> foreignLanguages) {
		this.description = description;
		this.foreignLanguages = foreignLanguages;
	}
	public UserSettings(String profile_image, String description,Timestamp dateOfBirth, String nativeLanguage,
			List<Language> foreignLanguages) {
		this.profileImage = profile_image;
		this.description = description;
		this.dateOfBirth = dateOfBirth;
		this.nativeLanguage = nativeLanguage;
		this.foreignLanguages = foreignLanguages;
	}
	public UserSettings(String profile_image, String description,Date dateOfBirth, String nativeLanguage) {
		this.profileImage = profile_image;
		this.description = description;
		this.dateOfBirth = dateOfBirth;
		this.nativeLanguage = nativeLanguage;
	}

	public UserSettings(Integer id, String profile_image, String description,Date dateOfBirth, String nativeLanguage,
			List<Language> foreignLanguages) {
		this.id = id;
		this.profileImage = profile_image;
		this.description = description;
		this.dateOfBirth = dateOfBirth;
		this.nativeLanguage = nativeLanguage;
		this.foreignLanguages = foreignLanguages;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="settings_id")
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profile_image) {
		this.profileImage = profile_image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getNativeLanguage() {
		return nativeLanguage;
	}
	public void setNativeLanguage(String nativeLanguage) {
		this.nativeLanguage = nativeLanguage;
	}
	

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="settings_id", nullable=false)
	public List<Language> getForeignLanguages() {
		return foreignLanguages;
	}

	public void setForeignLanguages(List<Language> foreignLanguages) {
		this.foreignLanguages = foreignLanguages;
	}
	

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}


	
	
	
	
}
