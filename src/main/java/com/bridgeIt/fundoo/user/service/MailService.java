package com.bridgeIt.fundoo.user.service;

import org.springframework.stereotype.Component;

import com.bridgeIt.fundoo.user.model.EmailId;
import com.bridgeIt.fundoo.util.TokenGenerators;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Component
public class MailService 
{
private JavaMailSenderImpl javaMailSender;

@Autowired
public MailService(JavaMailSenderImpl javaMailSender) 
{
	this.javaMailSender = javaMailSender;
}

@Autowired
private TokenGenerators tokenGenerators;

public void send(EmailId emailId)
{
	SimpleMailMessage message=new SimpleMailMessage();
	message.setFrom("sangharsharanpise408@gmail.com");
	message.setTo(emailId.getTo());
	message.setSubject("Email Validation...");
    message.setText(emailId.getBody());
    
    javaMailSender.send(message);
    
}

public String getLink(String link,long id) throws IllegalArgumentException, UnsupportedEncodingException 
{
	return link+tokenGenerators.generateToken(id);
}

}
