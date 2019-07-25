package com.bridgeIt.fundoo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Value("${spring.rabbitmq.template.userExchange}")
    private String userExchange;
    
    @Value("${spring.rabbitmq.template.userQueue}")
    private String userQueue;
    
    @Value("${spring.rabbitmq.template.userRouting-Key}")
    private String userRoutingkey;

    
    @Bean
    Queue userQueue() {
        return new Queue(userQueue);
    }

    @Bean
    DirectExchange userExchange() {
        return new DirectExchange(userExchange);
    }

    @Bean
    Binding bindingUser(Queue userQueue, DirectExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with(userRoutingkey);
    }
//*******************************************************************************************
//    @Value("${spring.rabbitmq.template.noteExchange}")
//    private String noteExchange;
//    
//    @Value("${spring.rabbitmq.template.noteQueue}")
//    private String noteQueue;
//    
//    @Value("${spring.rabbitmq.template.noteRouting-key}")
//    private String noteRoutingkey;
//
//    @Bean
//    Queue noteQueue() {
//        return new Queue(noteQueue);
//    }
//
//    @Bean
//    DirectExchange noteExchange() {
//        return new DirectExchange(noteExchange);
//    }
//
//    @Bean
//    Binding bindingNote(Queue noteQueue, DirectExchange exchange) {
//        return BindingBuilder.bind(noteQueue).to(exchange).with(noteRoutingkey);
//    }
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//		return new Jackson2JsonMessageConverter();
//    }
 
}



