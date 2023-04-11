package com.umapp.core.utils;

import java.time.LocalDateTime;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
@Audited
public class UmBaseEntity {
	private static final long serialVersionUID = 1L;

	@Column(name="creator")
	private String creator;
	
	@Column(name="creator_id")
	private Integer creatorId;
	
	@Column(name="created_time")
	private LocalDateTime createdTime;
	
	@Column(name="last_modifier")
	private String lastModifier;
	
	@Column(name="last_modifier_id")
	private Integer lastModifierId;
	
    @Column(name="last_modifier_time")
    private LocalDateTime lastModifiedTime;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime ;
	}

	@PrePersist
	public void setCreatedTime() {
		this.createdTime = LocalDateTime.now();
		this.creator= "Admin Team";
		this.creatorId= 1;
		this.lastModifiedTime = LocalDateTime.now() ;
		this.lastModifier="Admin Team";
		this.lastModifierId=1;
	}

	public String getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Integer getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(Integer lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public LocalDateTime getLastModifiedTime() {
		return lastModifiedTime ;
	}

	@PreUpdate
	public void setLastModifiedTime() {
		this.lastModifiedTime = LocalDateTime.now() ;
		this.lastModifier="Admin Team";
		this.lastModifierId=1;
	}

}
