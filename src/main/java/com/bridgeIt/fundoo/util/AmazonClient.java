//package com.bridgeIt.fundoo.util;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//
//@Service
//public class AmazonClient {
//
//    @SuppressWarnings("unused")
//	private AmazonS3 s3client;
//
//	    @Value("${endpointUrl}")
//	    private String endpointUrl;
//	    @Value("${bucketName}")
//	    private String bucketName;
//	    @Value("${accessKey}")
//	    private String accessKey;
//	    @Value("${secretKey}")
//	    private String secretKey;
//	    
//	    @SuppressWarnings("deprecation")
//		@PostConstruct
//	    private void initializeAmazon() {
//	       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//	       
//	       this.s3client = new AmazonS3Client(credentials);
//	       System.out.println("accesskey:"+this.accessKey);
//	       System.out.println("secreat key:"+this.secretKey);
//	       System.out.println("credential :"+credentials);
//  }
//	
//	}

