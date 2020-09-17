package com.boolment.jobapp.serviceinterface;

import java.util.List;

import com.boolment.jobapp.entity.EmployerEntity;
import com.boolment.jobapp.entity.JobsEntity;

public interface EmployerServiceInterface {

	EmployerEntity createEmployer(EmployerEntity employerEntity);

	List<EmployerEntity> getEmployersList();

	void deleteEmployer(String employerUid);

	EmployerEntity updateEmployer(String employerUid,EmployerEntity updatedEmployer);

	EmployerEntity createEmployerJob(String employerUid, JobsEntity jobEntity);

	List<JobsEntity> getJobsList();

	JobsEntity updateJobById(String jobUid, JobsEntity jobEntity);

	void deleteJobById(String jobUid);

	JobsEntity getJobDetails(String jobUid);

}
