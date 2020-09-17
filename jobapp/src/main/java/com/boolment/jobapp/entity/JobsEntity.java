package com.boolment.jobapp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = TablesNames.JOBS)
public class JobsEntity extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -6447994035025854993L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long id;

	private String jobUid;

	private String jobTitle;

	private String jobType;

	private int minSalary;

	private int maxSalary;

	private int minExperience;

	private int maxExperience;

	private boolean jobStatus;

	private String jobAddress;

	private String jobCity;

	private String jobState;

	private String jobZipCode;

	private String jobCountry;

	@Column(columnDefinition = "text")
	private String requirements;

	private String qualificationCourse;

	private int openings;

	@Column(columnDefinition = "text")
	private String jobSummary;

	@Column(columnDefinition = "text")
	private String jobDescription;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "employer_pk_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private EmployerEntity employer;

}
