package com.bridgeIt.fundoo.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.response.ResponseToken;
import com.bridgeIt.fundoo.user.dto.LoginDto;
import com.bridgeIt.fundoo.user.dto.PasswordDto;
import com.bridgeIt.fundoo.user.dto.UserDto;
@Service
public interface UserService 
{
	
 Response registration(UserDto userDto) throws UnsupportedEncodingException;

 ResponseToken login(LoginDto loginDto) throws UnsupportedEncodingException;

	
 Response validateEmail(String token) throws IllegalArgumentException, UnsupportedEncodingException;
	

 Response resetPassword(String token,PasswordDto passwordDto) throws IllegalArgumentException, UnsupportedEncodingException;

 Response forgotPassword(LoginDto loginDto); 

	
 //Response changePassword(String token,String newPassword) throws IllegalArgumentException, UnsupportedEncodingException;
 
Response setProfile(String imageFile, String token) throws IllegalArgumentException, IOException;


 }
