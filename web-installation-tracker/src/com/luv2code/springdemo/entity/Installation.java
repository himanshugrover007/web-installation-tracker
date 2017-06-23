package com.luv2code.springdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.luv2code.springdemo.dao.TimeStamped;
import com.luv2code.springdemo.validation.BlankOrPattern;

@Entity
@Table(name = "installations")
public class Installation implements TimeStamped{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "ip")
	@NotEmpty
	private String ip;

	@Column(name = "environment_type")
	@NotEmpty
	private String environmentType;

	@Column(name = "version")
	@NotEmpty
	private String version;
	
	@Column(name = "middleware_location")
	@NotEmpty
	private String middlewareLocation;

	@Column(name = "schema_prefix")
	@NotEmpty
	private String schemaPrefix;

	@Column(name = "admin_server_HTTPPort")
	@Pattern(regexp="[\\d]{4}")
	private String adminServerHTTPPort;

	@Column(name = "admin_server_HTTPSPort")
	@Pattern(regexp="[\\d]{4}")
	private String adminServerHTTPSPort;

	@Column(name = "managed_server_HTTPPort")
	@Pattern(regexp="[\\d]{4}")
	private String managedServerHTTPPort;

	@Column(name = "managed_server_HTTPSPort")
	@Pattern(regexp="[\\d]{4}")
	private String managedServerHTTPSPort;
	
	@Column(name = "managed_server_2_HTTPPort")
	@BlankOrPattern(regexp="[\\d]{4}", message = "{BlankOrPattern.installation.managedServer2HTTPPort}")
	private String managedServer2HTTPPort;

	@Column(name = "managed_server_2_HTTPSPort")
	@BlankOrPattern(regexp="[\\d]{4}", message = "{BlankOrPattern.installation.managedServer2HTTPSPort}")
	private String managedServer2HTTPSPort;
	
	@Column(name = "status")
	@NotEmpty
	private String status;

	@Column(name = "installed_by")
	private String installedBy;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "lastupdated_date")
	private Date lastUpdatedDate;
	
	@Column(name = "vncport")
	@Pattern(regexp="[\\d]{1}")
	private String vncPort;
	
	@Column(name = "bits_location")
	@NotEmpty
	private String bitsLocation;
	
	public Installation() {

	}

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

	public String getEnvironmentType() {
		return environmentType;
	}

	public void setEnvironmentType(String environmentType) {
		this.environmentType = environmentType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMiddlewareLocation() {
		return middlewareLocation;
	}

	public void setMiddlewareLocation(String middlewareLocation) {
		this.middlewareLocation = middlewareLocation;
	}

	public String getSchemaPrefix() {
		return schemaPrefix;
	}

	public void setSchemaPrefix(String schemaPrefix) {
		this.schemaPrefix = schemaPrefix;
	}

	public String getAdminServerHTTPPort() {
		return adminServerHTTPPort;
	}

	public void setAdminServerHTTPPort(String adminServerHTTPPort) {
		this.adminServerHTTPPort = adminServerHTTPPort;
	}

	public String getAdminServerHTTPSPort() {
		return adminServerHTTPSPort;
	}

	public void setAdminServerHTTPSPort(String adminServerHTTPSPort) {
		this.adminServerHTTPSPort = adminServerHTTPSPort;
	}

	public String getManagedServerHTTPPort() {
		return managedServerHTTPPort;
	}

	public void setManagedServerHTTPPort(String managedServerHTTPPort) {
		this.managedServerHTTPPort = managedServerHTTPPort;
	}

	public String getManagedServerHTTPSPort() {
		return managedServerHTTPSPort;
	}

	public void setManagedServerHTTPSPort(String managedServerHTTPSPort) {
		this.managedServerHTTPSPort = managedServerHTTPSPort;
	}

	public String getManagedServer2HTTPPort() {
		return managedServer2HTTPPort;
	}

	public void setManagedServer2HTTPPort(String managedServer2HTTPPort) {
		this.managedServer2HTTPPort = managedServer2HTTPPort;
	}

	public String getManagedServer2HTTPSPort() {
		return managedServer2HTTPSPort;
	}

	public void setManagedServer2HTTPSPort(String managedServer2HTTPSPort) {
		this.managedServer2HTTPSPort = managedServer2HTTPSPort;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInstalledBy() {
		return installedBy;
	}

	public void setInstalledBy(String installedBy) {
		this.installedBy = installedBy;
	}
	
	public String getVncPort() {
		return vncPort;
	}

	public void setVncPort(String vncPort) {
		this.vncPort = vncPort;
	}

	public String getBitsLocation() {
		return bitsLocation;
	}

	public void setBitsLocation(String bitsLocation) {
		this.bitsLocation = bitsLocation;
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
