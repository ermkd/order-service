package com.boolment.jobapp.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boolment.jobapp.entity.ContactEntity;
import com.boolment.jobapp.repositoryinterface.ContactReposotiryInterface;
import com.boolment.jobapp.serviceinterface.ContactServiceInterface;


@Service
public class ContactInterfaceImpl implements ContactServiceInterface {

	
	@Autowired
	ContactReposotiryInterface contactRepositoryInterface;
	
	@Override
	public ContactEntity addContact(ContactEntity contactEntity) {
		
		return contactRepositoryInterface.save(contactEntity);
	}

	@Override
	public List<ContactEntity> getContactList() {
	
		return contactRepositoryInterface.findAll();
	}

}
