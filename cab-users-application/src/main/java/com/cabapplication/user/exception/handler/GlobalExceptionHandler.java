package com.cabapplication.user.exception.handler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	LocalDate currentDate=LocalDate.now();
	File errorFile= new File("D:/logger/"+currentDate+"-"+"ADMIN"+" - Exception"+".txt");
	
	private ErrorDetails logException(Exception ex, WebRequest request) {
		//log here
		return new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getDescription(false),null,null);
	}
	
	@ExceptionHandler(value = MalformedURLException.class)
	public ErrorDetails handleMalformedUrlEx(Exception ex, WebRequest request) {
		return logException(ex, request);
	}
	
	@ExceptionHandler(value = InterruptedException.class)
	public ErrorDetails handleInterruptedEx(Exception ex, WebRequest request) {
		return logException(ex, request);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ErrorDetails exceptionHandling(Exception ex, WebRequest request) {
		return logException(ex, request);
	}
	
	
	public boolean writeToExceptionFile(Exception ex,HttpServletRequest request) throws IOException
	{
	boolean result=false;
	try(PrintWriter writer=new PrintWriter(new FileWriter(errorFile,true)))
	{
	
	ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),ex.getMessage(),request.getMethod(),request.getRequestURI(),ex.getClass().descriptorString());
		

	writer.println(errorDetails);
	result=true;
	}catch(Exception e)
	{
	e.printStackTrace();
	}
	return result;
	}
	
	
	
}

