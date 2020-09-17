package com.boolment.jobapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.boolment.jobapp.util.TablesNames;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name=TablesNames.USER)
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -65891283856365308L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false, updatable=false)
	private Long id;
	
	private String userId;
	
	private String firstName;
	
	private String lastName;
	
	private String userName;
	
	private String password;
	
	private String email;
	
	private String profileImageUrl;
	
	private Date lastLoginDate;  
	
	private Date lastLoginDateDisplay;
	
	private Date joinDate;
	
	private String roles; // List of roles to user ROLE_USER{ read, edit }, ROLE_EMPLOYER ROLE_EMPLOYEE, ROLE_ADMIN {delete, read, update, created}
	
	private String[] authorities;
	
	private boolean isActive;
	
	private boolean isNotLocked;
	
	
	

}
