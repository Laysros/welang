package com.dac.welang.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserRelationship {
	@Id
	@GeneratedValue
	private Long id;
	private Long addFrom;
	private Long addTo;
	private boolean confirmed;
	private Date confirmedDate;
	
	public UserRelationship() {
	}


	public UserRelationship(Long addFrom, Long addTo) {
		this.addFrom = addFrom;
		this.addTo = addTo;
	}


	public UserRelationship(Long addFrom, Long addTo, boolean confirmed) {
		this.addFrom = addFrom;
		this.addTo = addTo;
		this.confirmed = confirmed;
	}
	
	
	public UserRelationship(Long addFrom, Long addTo, boolean confirmed, Date confirmedDate) {
		this.addFrom = addFrom;
		this.addTo = addTo;
		this.confirmed = confirmed;
		this.confirmedDate = confirmedDate;
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAddFrom() {
		return addFrom;
	}
	public void setAddFrom(Long addFrom) {
		this.addFrom = addFrom;
	}
	public Long getAddTo() {
		return addTo;
	}
	public void setAddTo(Long addTo) {
		this.addTo = addTo;
	}
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public Date getConfirmedDate() {
		return confirmedDate;
	}
	public void setConfirmedDate(Date confirmedDate) {
		this.confirmedDate = confirmedDate;
	}
}
