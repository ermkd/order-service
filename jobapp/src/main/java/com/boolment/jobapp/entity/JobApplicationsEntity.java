package com.boolment.jobapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import com.boolment.jobapp.util.TablesNames;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name = TablesNames.APPLIED_JOBS)
public class JobApplicationsEntity extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2187611117694228093L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	private String jobAppUid;

	private String fullName;

	private String email;

	private String phoneNo;

	private String highestEducation;

	private String appliedPost;

	@Lob
	private byte[] resume;
	

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "job_pk_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private JobsEntity jobsEntity;

}
