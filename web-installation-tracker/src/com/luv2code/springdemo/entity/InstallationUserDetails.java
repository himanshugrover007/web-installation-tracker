package com.luv2code.springdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.luv2code.springdemo.dao.TimeStamped;

@Entity
@Table(name = "installation_user_details")
public class InstallationUserDetails implements TimeStamped{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "installation_id")
	private int installationId;

	@Column(name = "installation_user")
	private String installationUser;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "lastupdated_date")
	private Date lastUpdatedDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	

	public int getInstallationId() {
		return installationId;
	}

	public void setInstallationId(int installationId) {
		this.installationId = installationId;
	}

	public String getInstallationUser() {
		return installationUser;
	}

	public void setInstallationUser(String installationUser) {
		this.installationUser = installationUser;
	}
	
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	

}
