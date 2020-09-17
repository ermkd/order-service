package com.boolment.jobapp.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

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
@Table(name=TablesNames.EMPLOYER)
@DynamicUpdate
public class EmployerEntity extends Auditable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1438023812825479643L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false, updatable=false)
	private Long id;
	
	private String employerUid;
	
	private String employerName;
	
	private String employerEmail;
	
	private String employerSPOC;
	
	private String employerMobileNo;
	
	private String employerAddress;
	
	private String employerLandMark;
	
	private String employerCity;
	
	private String employerState;
	
	private String employerPinCode;
	
	private String employerCountry;
	
	private String employerLogo;
	

	
	
	

}
