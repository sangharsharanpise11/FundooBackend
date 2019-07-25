package com.bridgeIt.fundoo.user.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bridgeIt.fundoo.exception.UserException;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.user.model.User;
import com.bridgeIt.fundoo.user.repository.UserRepository;
import com.bridgeIt.fundoo.util.ResponseStatus;
import com.bridgeIt.fundoo.util.TokenGenerators;

@Service
public class AmazonClientService 
  {
	private AmazonS3 s3client;
		
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenGenerators tokenGenerators;
	
	@Autowired
	private Environment environment;
	
    @Value("${endpointUrl}")
    private String endpointUrl;
    @Value("${bucketName}")
    private String bucketName;
    @Value("${accessKey}")
    private String accessKey;
    @Value("${secretKey}")
    private String secretKey;
    
    @SuppressWarnings("deprecation")
	@PostConstruct
    private void initializeAmazon() {
       AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
       
       this.s3client = new AmazonS3Client(credentials);
	
	
    }
	
	
	
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
		}
	
	private void uploadFileTos3bucket(String fileName, File file) {
		System.out.println("hello.......................");
		System.out.println("fileName :"+fileName);
		System.out.println("file :"+file);
		System.out.println("bucketName :"+bucketName);
		
	    s3client.putObject(new PutObjectRequest(bucketName,fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
	        
	    System.out.println("after putObject ()");
		System.out.println("bucketName"+bucketName+"fileName"+fileName+",file"+file);
	}
	
	public Response uploadFile(MultipartFile multipartFile, String token) throws IllegalArgumentException, IOException {
		long userid = tokenGenerators.decodeToken(token);
		boolean isUser = userRepository.findById(userid).isPresent();
		if (!isUser) {
			throw new UserException("user not exist",-3);
		}
		User user = userRepository.findByUserId(userid).get();

		
			File file = convertMultiPartToFile(multipartFile);
			System.out.println("file :"+file);
     		String fileUrl=UUID.randomUUID().toString();
     		System.out.println("bucketname: "+bucketName);
     		System.out.println("fileName: "+fileUrl);
     		uploadFileTos3bucket(fileUrl, file);
      		file.delete();
     		user.setImage(fileUrl);
     		System.out.println("fileurl "+fileUrl);
     		userRepository.save(user);
				
		Response response=ResponseStatus.statusInformation(environment.getProperty("status.setProfile.success"),
		    		Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	public Response deleteProfile(String fileName, String token) throws IllegalArgumentException, UnsupportedEncodingException {
		long userid = tokenGenerators.decodeToken(token);
		boolean isUser = userRepository.findById(userid).isPresent();
		if (!isUser) {
			throw new UserException("user not exist",-3);
		}
		User user = userRepository.findByUserId(userid).get();
		user.setImage("");
		s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
		userRepository.save(user);
		Response response=ResponseStatus.statusInformation(environment.getProperty("status.DeleteProfile"),
	    		Integer.parseInt(environment.getProperty("status.success.code")));
	  return response;
		
	}

	public String getProfile(String token) throws IllegalArgumentException, UnsupportedEncodingException
	{
		long userid = tokenGenerators.decodeToken(token);
		boolean isUser = userRepository.findById(userid).isPresent();
		if (!isUser) {
			throw new UserException("user not exist",-3);
		}
		User user = userRepository.findByUserId(userid).get();
		String image=user.getImage();
		System.out.println("image is :"+image);
		return image;
	}
}
