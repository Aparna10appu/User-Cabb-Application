package com.cabapplication.user.bl;

import java.net.MalformedURLException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.cabapplication.user.bo.EmployeeBO;
import com.cabapplication.user.dl.LoginDL;
import com.cabapplication.user.entity.Employee;
import com.cabapplication.user.entity.SPResponseEntity;
import com.cabapplication.user.entity.SPResponseEntity.Fields;
import com.cabapplication.user.entity.SPResponseEntity.Value;
import com.cabapplication.user.utils.JwtUtil;
import com.google.gson.Gson;
import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationException;
import com.microsoft.aad.adal4j.AuthenticationResult;

/**
 * Test class with default login credentials remove while deploying in server
 * **/
@Component
public class LoginBLTest {
		
	@Autowired
	private LoginDL loginDl;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String validateUser(@RequestBody EmployeeBO employeeBo, HttpServletRequest request) throws ExecutionException, InterruptedException, MalformedURLException {
		String jwt = null;
		
		Employee emp = this.loginDl.findEmployeeByMail(employeeBo.getEmployeeMail());

		if(emp == null) {
			if(checkUserInSP(employeeBo) == null) {
				throw new BadCredentialsException("Bad Credentials");
			} else {
				emp = this.loginDl.findEmployeeByMail(employeeBo.getEmployeeMail());
			}
		}
		
		if(emp.getIsBlocked() == 1 && emp.getIsAdmin()!=1) {
			throw new LockedException("Account Blocked");
		}
		
		try {
			//decode the base64 encoded password
			byte[] password = Base64.getDecoder().decode(employeeBo.getPassword());
			employeeBo.setPassword(new String(password));

			//=============authenticate from azure ad===================================//
		    Future<AuthenticationResult> result = 
		    		azureValidation(employeeBo.getEmployeeMail(), employeeBo.getPassword());

		    //generate jwt token if valid user
		    if(result.get().getAccessToken() != null) {
//		    	System.out.println(result.get().getAccessToken());
		    	jwt = "Bearer " + jwtUtil.generateToken(employeeBo.getEmployeeMail());
				
				/* setting manual authentication 
				 * pass null for password and empty list for authorities since we dont use it anywhere
				 * passing 3 parameters is important
				 * */
		    	UsernamePasswordAuthenticationToken authenticationToken 
									= new UsernamePasswordAuthenticationToken(employeeBo.getEmployeeMail(), null, Collections.emptyList());
		    	
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				
				//session timeout for 15 mins
				request.getSession().setMaxInactiveInterval(900);
				
				return jwt;
		    }
		  //=======================ad auth ends here ===========================================//		
		    
			//this exception occurs on invalid credentials or if account is locked
		} catch (BadCredentialsException | LockedException | UsernameNotFoundException | ExecutionException | AuthenticationException e) {
			throw e;
			
		} catch (MalformedURLException | InterruptedException e) {
			throw e;

		} catch (Exception e) {
			
		}
		
		return jwt;
	}
	
	private Future<AuthenticationResult> azureValidation(String mail, String password) throws MalformedURLException {
		String authorityURL = "https://login.windows.net/avam365test.onmicrosoft.com";

		// for testing we are passing default creds 
		mail = "thane@avam365test.onmicrosoft.com";
//		password = "Rov84743";
			
		AuthenticationContext context = new AuthenticationContext(authorityURL, false, Executors.newScheduledThreadPool(1));

	    Future<AuthenticationResult> result = 
	    		context.acquireToken("https://graph.microsoft.com", 
					"cf71fee4-0e23-463e-a862-919c2d6b05da", mail, password, null);
		
		return result;
	}
	
	/*
	 *If employee is not present in our DB check in sharepoint list and insert in our DB if present 
	*/
	private Employee checkUserInSP(EmployeeBO employee) throws InterruptedException, ExecutionException, MalformedURLException {
		
		//to get access token
		Future<AuthenticationResult> azureResponse = azureValidation("thane@avam365test.onmicrosoft.com", "Rov84743");
		String accessToken = azureResponse.get().getAccessToken();
//		System.out.println(accessToken);
		
		RestTemplate template = new RestTemplate();
		
		//microsoft graph uri to get list items 
		String graphUrl = "https://graph.microsoft.com/v1.0/sites/5c6fe8eb-4bb2-444d-98ad-a0404ff7e925/lists/JavaTestList/items?expand=fields(select=Title,EmployeeId,EmployeeName,PhoneNumber,Domain)&filter=fields/Title eq '"+ employee.getEmployeeMail() +"'";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "Bearer "+ accessToken);
		headers.add("Prefer", "HonorNonIndexedQueriesWarningMayFailRandomly");
		
		//(Rest call)
		ResponseEntity<String> spResponse1 = template.exchange(graphUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

		//convert json response to SPResponseEntityClass
		Gson gson = new Gson();
		SPResponseEntity resp1 = gson.fromJson(spResponse1.getBody(), SPResponseEntity.class);
		
		List<Fields> fieldsList = resp1.getValue().stream().map(Value::getFields).collect(Collectors.toList());
		
		if(fieldsList.isEmpty()) {
			return null;
		}
		
		Fields fields = fieldsList.get(0);
		
		Employee spEmp = Employee.builder().employeeId(fields.getEmployeeId())
							.employeeMail(fields.getTitle())
							.employeeName(fields.getEmployeeName())
							.phoneNumber(fields.getPhoneNumber()).build();
		
		return this.loginDl.addEmployee(spEmp);
	}
	
}
