package com.prodaja.prodaja;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class RabbitMQConfigurator {

    public static final String ORDERS_TOPIC_EXCHANGE_NAME = "orders-events-exchange";


    public static final String ORDERS_QUEUE_JSON = "queue-orders-json";
    public static final String ORDERS_ROUTING_KEY_JSON = "orders.events.json";


    @Bean
    public TopicExchange ordersTopicExchange() {
        return new TopicExchange(ORDERS_TOPIC_EXCHANGE_NAME);
    }

    @Bean
    public Queue ordersQueueJson() {
        return new Queue(ORDERS_QUEUE_JSON, true); // durable
    }

    @Bean
    public Binding ordersQueueJsonBinding(TopicExchange ordersTopicExchange, Queue ordersQueueJson) {
        return BindingBuilder.bind(ordersQueueJson)
                .to(ordersTopicExchange)
                .with(ORDERS_ROUTING_KEY_JSON);
    }

}
