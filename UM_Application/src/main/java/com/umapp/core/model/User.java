package com.umapp.core.model;

import org.hibernate.envers.Audited;

import com.umapp.core.utils.UmBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="USER")
@Data
@Audited
public class User extends UmBaseEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="USER_NAME")
	private String username;

	@Column(name="USER_EMAIL")
	private String userEmail;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="MIDDLE_NAME")
	private String middleName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="ADDRESS")
	private String ADDRESS;

}
