package com.boolment.jobapp.serviceinterface;

import java.util.List;

import com.boolment.jobapp.entity.JobApplicationsEntity;

public interface JobAppServiceInterface {

	JobApplicationsEntity jobApply(String jobApplicationUid, JobApplicationsEntity jobAppEntity);

	List<JobApplicationsEntity> getJobsApplicationList();

}
