package com.bridgeIt.fundoo.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

//import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.response.ResponseToken;
import com.bridgeIt.fundoo.user.dto.LoginDto;
import com.bridgeIt.fundoo.user.dto.PasswordDto;
import com.bridgeIt.fundoo.user.dto.UserDto;
import com.bridgeIt.fundoo.user.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@RestController
@RequestMapping("/user")
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
public class UserController
{

  private final Path pathlocation=Paths.get("/home/mobicomp/UplodingImages");
 
	@Autowired
	private UserService userService;
	

	
	@PostMapping("/register")
	public ResponseEntity<Response>registration(@RequestBody UserDto userDto) throws UnsupportedEncodingException
	{
	
        Response response=userService.registration(userDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseToken>login(@RequestBody LoginDto loginDto) throws UnsupportedEncodingException 
	{
		ResponseToken response=userService.login(loginDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
  
	@GetMapping("/emailvalidation/{token}")
	public ResponseEntity<Response>validateEmail(@PathVariable(name = "token")  String token) throws IllegalArgumentException, UnsupportedEncodingException
	{
		Response response=userService.validateEmail(token);
		return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
	}

	@PostMapping("/forgot")
	public ResponseEntity<Response>forgotPassword(@RequestBody LoginDto loginDto)
	{
		Response response=userService.forgotPassword(loginDto);
		System.out.println(response);
		return new ResponseEntity<>(response,HttpStatus.OK);		
	}
	
	@PutMapping("/resetPassword/{token}")
	public ResponseEntity<Response>resetPassword(@PathVariable(name = "token") String token,@RequestBody PasswordDto passwordDto) throws IllegalArgumentException, UnsupportedEncodingException
	{
		Response response=userService.resetPassword(token, passwordDto);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
//	@PutMapping("/forgotpassword")
//	public ResponseEntity<Response> changePassword(@RequestHeader String token, @RequestBody String newPassword) throws IllegalArgumentException, UnsupportedEncodingException
//	{
//		Response response = userService.changePassword(token,newPassword);
//		return new ResponseEntity<Response> (response, HttpStatus.ACCEPTED);
//    }		
//   
	@PutMapping("/setProfile")
	public ResponseEntity<Response>setProfile(@RequestParam("imageFile") MultipartFile imageFile,@RequestParam String token) throws IllegalArgumentException, IOException
	{
	     //System.out.println("image ->"+imageFile);
		 UUID uuid = UUID.randomUUID();
		 String uuidString = uuid.toString();
	     System.out.println(uuid.toString());
	        
	     Files.copy(imageFile.getInputStream(), this.pathlocation.resolve(uuidString),StandardCopyOption.REPLACE_EXISTING);
	     System.out.println("hello");
	        
  	     Response response=userService.setProfile(uuidString,token); 
  	    
		 return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

}