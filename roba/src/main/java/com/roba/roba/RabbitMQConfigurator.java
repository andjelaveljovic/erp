package com.roba.roba;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurator {

    public static final String PRODUCTS_TOPIC_EXCHANGE_NAME = "products-events-exchange";


     public static final String PRODUCTS_SERVICE_QUEUE_JSON = "queue-products-json";
//
//    public static final String FOUND_SUCCESS_QUEUE_JSON = "successful-queue";
//    public static final String NOTFOUND_FAIL_QUEUE_JSON = "notfound-queue";
//
////    @Bean
//    Queue productQueue() {
//        return new Queue(PRODUCTS_SERVICE_QUEUE, true);
//    }

    @Bean
    Queue productQueueJSON() {
        return new Queue(PRODUCTS_SERVICE_QUEUE_JSON, true);
    }
//    // Queue for error messages
//    @Bean
//    public Queue errorQueue() {
//        return new Queue(NOTFOUND_FAIL_QUEUE_JSON, true);
//    }
//    // Queue for successful ArtikalRoba
//    @Bean
//    public Queue successfulQueue() {
//        return new Queue(FOUND_SUCCESS_QUEUE_JSON, true);
//    }
//

    @Bean
    TopicExchange productExchange() {
        return new TopicExchange(PRODUCTS_TOPIC_EXCHANGE_NAME);
    }





    @Bean(name = "productQueueJSON")
    Binding productBindingJSON(Queue productQueueJSON, TopicExchange productExchange) {
        return BindingBuilder.bind(productQueueJSON).to(productExchange).with("product.events.json");
    }



//
//
//    // Binding successful queue to the exchange
//    @Bean
//    public Binding successfulBinding(TopicExchange productExchange,@Qualifier("successfulQueue") Queue successfulQueue) {
//        return BindingBuilder.bind(successfulQueue).to(productExchange).with("artikal.found.success");
//    }
//
//
//
//
//    // Binding error queue to the exchange
//    @Bean
//    public Binding errorBinding(TopicExchange productExchange, Queue errorQueue) {
//        return BindingBuilder.bind(errorQueue).to(productExchange).with("artikal.notfound.error");
//    }

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
