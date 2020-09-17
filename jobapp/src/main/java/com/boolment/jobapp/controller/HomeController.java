package com.boolment.jobapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boolment.jobapp.entity.JobsEntity;
import com.boolment.jobapp.serviceinterface.EmployerServiceInterface;

@RestController
@RequestMapping(path = "/")
public class HomeController {
	
	@Autowired
	private EmployerServiceInterface employerServiceInterface;

	

	@GetMapping("jobs/{jobUid}")
	public ResponseEntity<JobsEntity> getJobDetails(@PathVariable("jobUid") String jobUid)
	{
		System.out.println("======job fetching called from job home controller=="+jobUid);
		JobsEntity jobDetails = employerServiceInterface.getJobDetails(jobUid);
		System.out.println(jobDetails.toString());
		return new ResponseEntity<>(jobDetails, HttpStatus.OK);
	}
	
	

}
