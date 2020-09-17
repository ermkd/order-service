package com.boolment.jobapp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
@Table(name=TablesNames.CONTACT)
public class ContactEntity extends Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8224546816346544527L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="name can not be blank or empty")
	@Size(min=2, max = 50, message= "size should be between 2 to 50 character only")
	private String fullName;
	
	
	@Email
	private String email;
	
	@NotBlank
	private String subject;
	
	@NotBlank
	@Size(min=2, max = 500)
	private String message;
	
	
	
	
}
