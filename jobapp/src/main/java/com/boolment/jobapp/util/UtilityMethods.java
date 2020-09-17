package com.boolment.jobapp.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

@Component
public class UtilityMethods {
	
	public static final int TEN_NUMBERIC = 10; 
	
	public String generateUid() {
		return RandomStringUtils.randomNumeric(TEN_NUMBERIC);
	}

}
