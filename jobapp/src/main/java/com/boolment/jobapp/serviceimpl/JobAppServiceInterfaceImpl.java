package com.boolment.jobapp.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boolment.jobapp.entity.JobApplicationsEntity;
import com.boolment.jobapp.entity.JobsEntity;
import com.boolment.jobapp.repositoryinterface.JobApplicationRepository;
import com.boolment.jobapp.serviceinterface.EmployerServiceInterface;
import com.boolment.jobapp.serviceinterface.JobAppServiceInterface;
import com.boolment.jobapp.util.UtilityMethods;


@Service
public class JobAppServiceInterfaceImpl implements JobAppServiceInterface {
	@Autowired
	private EmployerServiceInterface employerServiceInterface;
	
	@Autowired
	private UtilityMethods utilityMethods;
	
	@Autowired
	private JobApplicationRepository jobApplicationRepository;


	@Override
	public JobApplicationsEntity jobApply(String jobApplicationUid, JobApplicationsEntity jobAppEntity) {
		
		JobsEntity fetchJob = employerServiceInterface.getJobDetails(jobApplicationUid);
		jobAppEntity.setJobAppUid(utilityMethods.generateUid());
		jobAppEntity.setJobsEntity(fetchJob);
		
		
		
		
		
		return jobApplicationRepository.save(jobAppEntity);
	}


	@Override
	public List<JobApplicationsEntity> getJobsApplicationList() {
		
		return jobApplicationRepository.findAll();
	}
	
	

}
