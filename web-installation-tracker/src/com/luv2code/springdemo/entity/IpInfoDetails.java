package com.luv2code.springdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.validator.constraints.NotEmpty;

import com.luv2code.springdemo.dao.TimeStamped;

@Entity
@Table(name = "ip_info_details")
public class IpInfoDetails implements TimeStamped{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "ip")
	@NotEmpty
	private String ip; 
	
	@Column(name = "public_ip")
	private String publicIp; 
	
	@Column(name = "public_ports")
	private String publicPorts; 
	
	@Column(name = "public_domain_name")
	private String publicDomainName;
	
	@Column(name = "deleted")
	private int deleted;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "lastupdated_date")
	private Date lastUpdatedDate;

	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "updated_by")
	private String updatedBy;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getPublicPorts() {
		return publicPorts;
	}

	public void setPublicPorts(String publicPorts) {
		this.publicPorts = publicPorts;
	}

	public String getPublicDomainName() {
		return publicDomainName;
	}

	public void setPublicDomainName(String publicDomainName) {
		this.publicDomainName = publicDomainName;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
