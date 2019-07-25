package com.bridgeIt.fundoo.util;

import java.io.UnsupportedEncodingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.service.NoteServices;
import com.bridgeIt.fundoo.user.model.EmailId;
import com.bridgeIt.fundoo.user.service.MailService;

@Component
public class RabbitMQListner
{
	@Autowired
	MailService mailService;
	
	String token;
	
	@Autowired
	NoteServices noteService;
	
	@RabbitListener(queues="userQueue")
	public void  receiveMessage(EmailId email)
	{
		System.out.println("************** user in Lisnter");
		mailService.send(email); 
	}

//	@RabbitListener(queues="noteQueue")
//	public void  receiveMessage(NoteDto noteDto) throws IllegalArgumentException, UnsupportedEncodingException
//	{
//		System.out.println("**************note in Lisnter");
//		noteService.createNote(noteDto, token);	
//	}
}
