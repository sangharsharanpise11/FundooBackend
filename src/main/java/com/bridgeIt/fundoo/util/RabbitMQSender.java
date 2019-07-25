package com.bridgeIt.fundoo.util;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;

import com.bridgeIt.fundoo.notes.dto.NoteDto;
import com.bridgeIt.fundoo.notes.model.Note;
import com.bridgeIt.fundoo.user.model.EmailId;

@Component
public class RabbitMQSender 
{	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.template.userExchange}")
	private String userExchange;
	
	@Value("${spring.rabbitmq.template.userRouting-Key}")
	private String userRoutingKey;
	
    public void sendMessageToQueue(EmailId email) 
    {
    	System.out.println("heeellooooo in user sender");
    	rabbitTemplate.convertAndSend(userExchange,userRoutingKey,email);
    }
//********************************************************************************************************
//  @Value("${spring.rabbitmq.template.noteExchange}")
//	private String noteExchange;
//	
//	@Value("${spring.rabbitmq.template.noteRouting-Key}")
//	private String noteRoutingKey;
//	
//    public void sendNoteMessageToQueue(Note note) 
//    {
//    	System.out.println("heeellooooo in note sender");
//    	rabbitTemplate.convertAndSend(noteExchange,noteRoutingKey,note);
//    }
    
}
