package com.boolment.jobapp.serviceimpl;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boolment.jobapp.entity.JobsEntity;

public interface JobRepository extends JpaRepository<JobsEntity, Long> {

	JobsEntity findByJobUid(String jobUid);

	void deleteByJobUid(String jobUid);

}
