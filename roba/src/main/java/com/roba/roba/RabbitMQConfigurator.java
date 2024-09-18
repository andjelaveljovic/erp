package com.roba.roba;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurator {

    public static final String PRODUCTS_TOPIC_EXCHANGE_NAME = "products-events-exchange";


   // public static final String PRODUCTS_SERVICE_QUEUE = "products-service-queue";
     public static final String PRODUCTS_SERVICE_QUEUE_JSON = "queue-json";

//    @Bean
//    Queue productQueue() {
//        return new Queue(PRODUCTS_SERVICE_QUEUE, true);
//    }

     @Bean
     Queue productQueueJSON() {
        return new Queue(PRODUCTS_SERVICE_QUEUE_JSON, true);
   }


    @Bean
    TopicExchange productExchange() {
        return new TopicExchange(PRODUCTS_TOPIC_EXCHANGE_NAME);
    }



//    @Bean(name = "productQueue")
//    Binding productBinding(Queue productQueue, TopicExchange productExchange) {
//        return BindingBuilder.bind(productQueue).to(productExchange).with("product.events.pokusavam");
//    }

    @Bean(name = "productQueueJSON")
    Binding productBindingJSON(Queue productQueueJSON, TopicExchange productExchange) {
        return BindingBuilder.bind(productQueueJSON).to(productExchange).with("product.events.json");
    }

    //new rabbit template for json messages
    @Bean
    public MessageConverter converter() {
        return  new Jackson2JsonMessageConverter();
    }
    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
