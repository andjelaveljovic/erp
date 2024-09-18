//package com.roba.roba.service;
//
//import com.roba.roba.RabbitMQConfigurator;
//import com.roba.roba.data.ProbaUser;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JSONproducer {
//    private RabbitTemplate rabbitTemplate;
//
//
//    private static  final Logger LOGGER = LoggerFactory.getLogger(JSONproducer.class);
//    public JSONproducer(RabbitTemplate rabbitTemplate){
//        this.rabbitTemplate = rabbitTemplate;
//    }
//    public void sendJSONMessage(ProbaUser probaUser){
//        LOGGER.info(String.format("JSON message sent -> %s", probaUser.toString()));
//        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME, "product.events.json", probaUser);
//    }
//
//}
