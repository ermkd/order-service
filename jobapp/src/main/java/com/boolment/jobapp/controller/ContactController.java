package com.boolment.jobapp.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boolment.jobapp.entity.ContactEntity;
import com.boolment.jobapp.entity.EmployerEntity;
import com.boolment.jobapp.entity.HttpResponse;
import com.boolment.jobapp.serviceinterface.ContactServiceInterface;

@RestController
public class ContactController {

	@Autowired
	private ContactServiceInterface contactServiceInterface;

	@PostMapping("/contact")
	public ResponseEntity<ContactEntity> addContactEnquiry(@RequestBody ContactEntity contactEntity) {
		System.out.println("data called"+contactEntity);
		
		ContactEntity contactEnitity = contactServiceInterface.addContact(contactEntity);
		return new ResponseEntity<ContactEntity>(contactEnitity, HttpStatus.OK);
		
		
	}

//	
//	@PostMapping
//	public ResponseEntity<Object> addContact(@RequestBody @Valid  ContactEntity contactEntity, BindingResult result )
//	{
//		
//		
//		if(result.hasErrors())
//		{
//			errors =  new HashMap<>();
//			for(FieldError error : result.getFieldErrors())
//			{
//				errors.put(error.getField(), error.getDefaultMessage());
//			}
//			
//			new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
//		}
//		return new ResponseEntity<>(contactServiceInterface.addContact(contactEntity), HttpStatus.OK);
//		
//		
//	}
	
	@GetMapping("/admin/enquiry")
	public ResponseEntity<List<ContactEntity>> getEnquiry()
	{
		List<ContactEntity> contactEnquiry = contactServiceInterface.getContactList();
		
		return new ResponseEntity<List<ContactEntity>>(contactEnquiry, HttpStatus.OK);
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {

		HttpResponse body = new HttpResponse(new Date(), httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());
		return new ResponseEntity<>(body, httpStatus);
	}

}
