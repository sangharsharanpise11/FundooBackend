package com.bridgeIt.fundoo.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.service.AmazonClientService;

@RestController
@RequestMapping("/storage/")
public class BucketController 
{
	    @Autowired
	    private AmazonClientService amazonClientService;	

	    @PostMapping("/uploadFile")
	    public Response uploadFile(@RequestPart(value = "file") MultipartFile file,@RequestHeader String token) throws IllegalArgumentException, IOException {
	        return this.amazonClientService.uploadFile(file,token);
	    }
	    
	    @DeleteMapping("/deleteFile")
	    public Response deleteFile(@RequestParam String fileName,String token) throws IllegalArgumentException, UnsupportedEncodingException {
	        return this.amazonClientService.deleteProfile(fileName,token);
	    }
	    
	    @GetMapping("/getProfile")
	    public String getProfile(@RequestHeader String token) throws IllegalArgumentException, UnsupportedEncodingException
	    {
	  			return this.amazonClientService.getProfile(token);
	    }
}
