package com.boolment.jobapp.repositoryinterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boolment.jobapp.entity.JobApplicationsEntity;

public interface JobApplicationRepository extends JpaRepository<JobApplicationsEntity, Long> {

}
