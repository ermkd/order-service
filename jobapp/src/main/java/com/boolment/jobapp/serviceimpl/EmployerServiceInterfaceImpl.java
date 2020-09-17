package com.boolment.jobapp.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boolment.jobapp.entity.EmployerEntity;
import com.boolment.jobapp.entity.JobsEntity;
import com.boolment.jobapp.repositoryinterface.EmployerRepository;
import com.boolment.jobapp.serviceinterface.EmployerServiceInterface;
import com.boolment.jobapp.util.UtilityMethods;

@Service
@Transactional
public class EmployerServiceInterfaceImpl implements EmployerServiceInterface {

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UtilityMethods utilityMethods;

	@Override
	public EmployerEntity createEmployer(EmployerEntity employerEntity) {

		employerEntity.setEmployerUid(utilityMethods.generateUid());
		return employerRepository.save(employerEntity);
	}

	@Override
	public List<EmployerEntity> getEmployersList() {

		return employerRepository.findAll();
	}

	@Override
	public void deleteEmployer(String employerUid) {
		employerRepository.deleteByEmployerUid(employerUid);

	}

	@Override
	public EmployerEntity updateEmployer(String employerUid, EmployerEntity updatedEmployer) {

		EmployerEntity fetchEmployer = employerRepository.findByEmployerUid(employerUid);
		fetchEmployer.setEmployerName(updatedEmployer.getEmployerName());
		fetchEmployer.setEmployerEmail(updatedEmployer.getEmployerEmail());
		fetchEmployer.setEmployerMobileNo(updatedEmployer.getEmployerMobileNo());
		fetchEmployer.setEmployerSPOC(updatedEmployer.getEmployerSPOC());
		fetchEmployer.setEmployerAddress(updatedEmployer.getEmployerAddress());
		fetchEmployer.setEmployerLandMark(updatedEmployer.getEmployerLandMark());
		fetchEmployer.setEmployerCity(updatedEmployer.getEmployerCity());
		fetchEmployer.setEmployerState(updatedEmployer.getEmployerState());
		fetchEmployer.setEmployerPinCode(updatedEmployer.getEmployerPinCode());
		fetchEmployer.setEmployerCountry(updatedEmployer.getEmployerCountry());

		return employerRepository.save(fetchEmployer);
	}

	@Override
	public EmployerEntity createEmployerJob(String employerUid, JobsEntity jobEntity) {
		EmployerEntity fetchEmployer = employerRepository.findByEmployerUid(employerUid);
		jobEntity.setJobUid(utilityMethods.generateUid());
		jobEntity.setEmployer(fetchEmployer);
		jobRepository.save(jobEntity);
		return fetchEmployer;
	}

	@Override
	public List<JobsEntity> getJobsList() {
		
		return jobRepository.findAll();
	}

	@Override
	public JobsEntity updateJobById(String jobUid, JobsEntity jobEntity) {
	
		JobsEntity fetchJobEntity = jobRepository.findByJobUid(jobUid);
		fetchJobEntity.setJobTitle(jobEntity.getJobTitle());
		fetchJobEntity.setJobType(jobEntity.getJobType());
		fetchJobEntity.setMinSalary(jobEntity.getMinSalary());
		fetchJobEntity.setMaxSalary(jobEntity.getMaxSalary());
		fetchJobEntity.setMinExperience(jobEntity.getMinExperience());
		fetchJobEntity.setMaxExperience(jobEntity.getMaxExperience());
		fetchJobEntity.setJobStatus(jobEntity.isJobStatus());
		fetchJobEntity.setJobAddress(jobEntity.getJobAddress());
		fetchJobEntity.setJobCity(jobEntity.getJobCity());
		fetchJobEntity.setJobState(jobEntity.getJobState());
		fetchJobEntity.setJobZipCode(jobEntity.getJobZipCode());
		fetchJobEntity.setJobCountry(jobEntity.getJobCountry());
		
		return jobRepository.save(fetchJobEntity);
	}

	@Override
	public void deleteJobById(String jobUid) {
		jobRepository.deleteByJobUid(jobUid);
		
	}

	@Override
	public JobsEntity getJobDetails(String jobUid) {
		
		 
		
		return jobRepository.findByJobUid(jobUid);
	}

}
