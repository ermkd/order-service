package com.boolment.jobapp.repositoryinterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boolment.jobapp.entity.EmployerEntity;

public interface EmployerRepository extends JpaRepository<EmployerEntity, Long> {

	void deleteByEmployerUid(String employerUid);

	EmployerEntity findByEmployerUid(String employerUid);

}
