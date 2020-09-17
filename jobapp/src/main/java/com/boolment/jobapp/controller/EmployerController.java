package com.boolment.jobapp.controller;

import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boolment.jobapp.entity.EmployerEntity;
import com.boolment.jobapp.entity.HttpResponse;
import com.boolment.jobapp.entity.JobsEntity;
import com.boolment.jobapp.serviceinterface.EmployerServiceInterface;
import com.boolment.jobapp.util.ConstantVariables;

@RestController
@RequestMapping(path = "/admin/employer")
public class EmployerController {

	@Autowired
	private EmployerServiceInterface employerServiceInterface;

	@PostMapping(value = "/create")
	public ResponseEntity<EmployerEntity> createEmployer(@RequestBody EmployerEntity employerEntity) {
		EmployerEntity newEmployer = employerServiceInterface.createEmployer(employerEntity);
		return new ResponseEntity<>(newEmployer, HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<EmployerEntity>> getEmployerList() {
		List<EmployerEntity> employerList = employerServiceInterface.getEmployersList();
		return new ResponseEntity<>(employerList, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{employerUid}")
	public ResponseEntity<HttpResponse> deleteEmployer(@PathVariable("employerUid") String employerUid) {
		employerServiceInterface.deleteEmployer(employerUid);
		return response(HttpStatus.NO_CONTENT, ConstantVariables.DELETE_SUCCESS);
	}

	@PutMapping("/update/{employerUid}")
	public ResponseEntity<EmployerEntity> updateEmployer(@PathVariable("employerUid") String employerUid,
			@RequestBody EmployerEntity updatedEmployer) {
		EmployerEntity updated = employerServiceInterface.updateEmployer(employerUid, updatedEmployer);
		return new ResponseEntity<>(updated, HttpStatus.OK);

	}

	@PostMapping("/jobs/create/{employerUid}")
	public ResponseEntity<EmployerEntity> createEmployerJob(@PathVariable("employerUid") String employerUid,
			@RequestBody JobsEntity jobEntity) {
		EmployerEntity employerJobModel = employerServiceInterface.createEmployerJob(employerUid, jobEntity);
		return new ResponseEntity<EmployerEntity>(employerJobModel, HttpStatus.OK);
	}

	@GetMapping("/jobs")
	public ResponseEntity<List<JobsEntity>> getJobsList() {
		List<JobsEntity> jobsList = employerServiceInterface.getJobsList();
		return new ResponseEntity<>(jobsList, HttpStatus.OK);
	}

	@PutMapping("job/update/{jobUid}")
	public ResponseEntity<JobsEntity> updateJobById(@PathVariable("jobUid") String jobUid,
			@RequestBody JobsEntity jobEntity) {
		JobsEntity updated = employerServiceInterface.updateJobById(jobUid, jobEntity);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@DeleteMapping("/job/delete/{jobUid}")
	public ResponseEntity<HttpResponse> deleteJobByUid(@PathVariable("jobUid") String jobUid) {
		employerServiceInterface.deleteJobById(jobUid);
		return response(HttpStatus.NO_CONTENT, ConstantVariables.DELETE_SUCCESS);
	}
	
//	
//	@GetMapping("/jobs/{jobUid}")
//	public ResponseEntity<JobsEntity> getJobDetails(@PathVariable("jobUid") String jobUid)
//	{
//		JobsEntity jobDetails = employerServiceInterface.getJobDetails(jobUid);
//		return new ResponseEntity<>(jobDetails, HttpStatus.OK);
//	}
	
	
	
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		HttpResponse body = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
		return new ResponseEntity<>(body, httpStatus);
	}

}
