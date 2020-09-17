package com.boolment.jobapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boolment.jobapp.entity.JobApplicationsEntity;
import com.boolment.jobapp.serviceinterface.JobAppServiceInterface;

@RestController
public class JobApplicationController {

	@Autowired
	private JobAppServiceInterface jobAppServiceInterface;
	
	
	@PostMapping("/jobs/{jobApplicationUid}/apply")
	public ResponseEntity<JobApplicationsEntity> jobApply(
			@PathVariable("jobApplicationUid") String jobApplicationUid, 
			@RequestParam("fullName") String fullName,
			@RequestParam("email") String email,
			@RequestParam("phoneNo") String phoneNo,
			@RequestParam("highestEducation") String highestEducation,
			@RequestParam("appliedPost") String appliedPost,
			@RequestParam(value = "resume", required= false) MultipartFile resume) throws IOException 
	{
		

		JobApplicationsEntity loadDate = new JobApplicationsEntity();
		loadDate.setFullName(fullName);
		loadDate.setEmail(email);
		loadDate.setPhoneNo(phoneNo);
		loadDate.setHighestEducation(highestEducation);
		loadDate.setAppliedPost(appliedPost);
		loadDate.setResume(resume.getBytes());
		JobApplicationsEntity savedJobApp = jobAppServiceInterface.jobApply(jobApplicationUid, loadDate);
		
		return new ResponseEntity<>(savedJobApp, HttpStatus.OK);

	}
	
	
	@GetMapping("/admin/applied-jobs")
	public ResponseEntity<List<JobApplicationsEntity>> getJobsList() {
		List<JobApplicationsEntity> jobsApplicationList = jobAppServiceInterface.getJobsApplicationList();
		return new ResponseEntity<>(jobsApplicationList, HttpStatus.OK);
	}
	
}
