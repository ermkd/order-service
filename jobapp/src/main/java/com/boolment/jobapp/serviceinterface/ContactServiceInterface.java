package com.boolment.jobapp.serviceinterface;

import java.util.List;

import com.boolment.jobapp.entity.ContactEntity;

public interface ContactServiceInterface {

	ContactEntity addContact(ContactEntity contactEntity);

	List<ContactEntity> getContactList();

}
