package com.boolment.jobapp.repositoryinterface;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boolment.jobapp.entity.ContactEntity;

public interface ContactReposotiryInterface extends JpaRepository<ContactEntity, Long> {

}
