package com.bridgeIt.fundoo.user.service;


import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgeIt.fundoo.exception.EmailException;
import com.bridgeIt.fundoo.exception.LoginException;
import com.bridgeIt.fundoo.exception.UserException;
import com.bridgeIt.fundoo.response.Response;
import com.bridgeIt.fundoo.response.ResponseToken;
import com.bridgeIt.fundoo.user.dto.LoginDto;
import com.bridgeIt.fundoo.user.dto.PasswordDto;
import com.bridgeIt.fundoo.user.dto.UserDto;
import com.bridgeIt.fundoo.user.model.EmailId;
import com.bridgeIt.fundoo.user.model.User;
import com.bridgeIt.fundoo.user.repository.UserRepository;
import com.bridgeIt.fundoo.util.RabbitMQSender;
import com.bridgeIt.fundoo.util.ResponseStatus;
import com.bridgeIt.fundoo.util.TokenGenerators;


@Service("userService")
@PropertySource("message.properties")
public class UserServiceImpl implements UserService 
{
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private TokenGenerators tokenGenerators;

	@Autowired
	private MailService mailService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RabbitMQSender rabbitMqSender;
	
	
	
  
	@Override
	public Response registration(UserDto userDto) throws UnsupportedEncodingException 
	{
		EmailId email = new EmailId();
		Response response;
		
		User user = modelMapper.map(userDto, User.class);
		Optional<User> availability = userRepository.findByEmailId(user.getEmailId());
		
		if(availability.isPresent()) 
		{
          throw new LoginException("user is present already", -3);
		} 
			
            // encode user password		
			String password = passwordEncoder.encode(userDto.getPassword());
			user.setPassword(password);

            user.setRegisteredDate(LocalDate.now());
			User status = userRepository.save(user);

    		//hashOperations.put("USER", user.getUserId(), user);
			
            //redis.opsForHash().put("USER", user.getUserId(), user);
			
			email.setFrom("sangharsharanpise408@gmail.com");
			email.setTo(userDto.getEmailId());
			email.setSubject("email verification");
			Long userId=user.getUserId();
			String url=mailService.getLink("http://localhost:4200/user/login/", userId);
            
		    email.setBody(url+"/valid");
			rabbitMqSender.sendMessageToQueue(email);
			
			System.out.println("*********************msg send to rabitmq****************");
		    response = ResponseStatus.statusInformation(environment.getProperty("status.register.success"),
				Integer.parseInt(environment.getProperty("status.regsuccess.code")));
		return response;
	}
                                  
	@Override
	public ResponseToken login(LoginDto loginDto) throws UnsupportedEncodingException 
	{
		ResponseToken response;
		System.out.println(loginDto);
		Optional<User> availability = userRepository.findByEmailId(loginDto.getemailId());
		
		log.info("User Password : " + availability.get().getPassword());
		
		if (availability.isPresent()) 
		{
			boolean status=passwordEncoder.matches(loginDto.getPassword(), availability.get().getPassword());
			if(status==true)
			{
				String tokengenerate=tokenGenerators.generateToken(availability.get().getUserId());
				
				response=ResponseStatus.tokenStatusInformation(environment.getProperty("status.login.success"),
						Integer.parseInt(environment.getProperty("status.success.code")), tokengenerate);
				return response;
			}	
			else
			{
				throw new LoginException("Invalid Password",-1);
			}
		}
		else
		{
			throw new LoginException("Invalid EmailId",-1);
		}
			
	}

	private User verify(User user)
	{
		user.setVerified(true);
		user.setModifiedDate(LocalDate.now());
		
//		redis.opsForHash().put(KEY, user, user);

		return userRepository.save(user);
	}

	@Override
	public Response validateEmail(String token) throws IllegalArgumentException, UnsupportedEncodingException
	{
		Response response = null;
		long userid = tokenGenerators.decodeToken(token);
	
		Optional<User> user = userRepository.findByUserId((long) userid).map(this::verify);
		if (user.isPresent()) 
		{
			response = ResponseStatus.statusInformation(environment.getProperty("status.email.verified"),
					Integer.parseInt(environment.getProperty("status.email.verified.code")));
			return response;
		} 
		else
		{
			throw new LoginException("EmailId is not verified", -3);
		}
		
	}

	@Override
	public Response resetPassword(String token,PasswordDto passwordDto) throws IllegalArgumentException, UnsupportedEncodingException
	{
		Response response = null;
		long userid = tokenGenerators.decodeToken(token);
		
		Optional<User> user = userRepository.findByUserId((int) userid);
		if (passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) 
		{
			user.get().setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
		    userRepository.save(user.get());
		    
		    log.info("Password Reset Successfully");


		    response=ResponseStatus.statusInformation(environment.getProperty("status.resetPassword.success"),
					Integer.parseInt(environment.getProperty("status.resetPassword.success.code")));
			return response;
		} 
		
			response = ResponseStatus.statusInformation(environment.getProperty("status.failure.resetpassword"),
					Integer.parseInt(environment.getProperty("status.failure.resetpassword.code")));
			return response;
	}

	@Override
	public Response forgotPassword(LoginDto loginDto) 
	{
		EmailId emailid = new EmailId();
		Response response;
		log.info("Email of user is :" + loginDto.getemailId());
		Optional<User> user = userRepository.findByEmailId(loginDto.getemailId());
		if (!user.isPresent()) 
		{
			throw new EmailException("No user exist",-4);
		}
			emailid.setTo(loginDto.getemailId());
			emailid.setFrom("sangharsharanpise408@gmail.com");
			emailid.setSubject("forgot password");
			
			try
			{
				emailid.setBody(mailService.getLink("http://localhost:4200/resetPassword/",user.get().getUserId()));
			} 
			catch (IllegalArgumentException | UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			
			mailService.send(emailid);

			response = ResponseStatus.statusInformation(environment.getProperty("status.success.fpassword"),
					Integer.parseInt(environment.getProperty("status.success.fpassword.code")));
		
		   return response;
		
	}

	public User changePassword(Optional<User> user, String password) 
	{
		user.get().setModifiedDate(LocalDate.now());
		
		user.get().setPassword(password);
		return userRepository.save(user.get());
	}

//	@Override
//	public Response changePassword(String token,String newPassword) throws IllegalArgumentException, UnsupportedEncodingException 
//	{
//	
//		Response response = null;
//		long userid = tokenGenerators.decodeToken(token);
//		
//		Optional<User>user=userRepository.findByUserId(userid);
//		user.get().setPassword(passwordEncoder.encode(newPassword));
//		
//		userRepository.save(user.get());
//		log.info("Password Changed");
//		response = ResponseStatus.statusInformation(environment.getProperty("status.resetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));
//        return response;
//        
//	}

//@Override
//	public User getRedisUserData(String token)
//	{
////		   return (User) redis.opsForHash().get(KEY, token);
//   //  return redisUtil.getMapAsSingleEntry(KEY, id);
//	}

	
	
	
}
