package com.boolment.jobapp.entity;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class HttpResponse {
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="MM-dd-yyyy hh:mm:ss", timezone="America/New_York")
	private Date timeStamp;
	
	private int httpStatusCode; //200
	
	private HttpStatus httpStatus;
	
	private String reason;
	
	private String message;
	

}
