package com.prodaja.prodaja.service;

import com.prodaja.prodaja.RabbitMQConfigurator;
import com.prodaja.prodaja.data.Narudzbenica;
import com.prodaja.prodaja.repository.NarudzbenicaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NarudzbenicaService {
    @Autowired
    private NarudzbenicaRepository narudzbenicaRepository;
    private RabbitTemplate rabbitTemplate;


    private static  final Logger LOGGER = LoggerFactory.getLogger(NarudzbenicaService.class);


    public void sendAddMessage(Narudzbenica narudzbenica){
        LOGGER.info(String.format("JSON message sent -> %s", narudzbenica.toString()));
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.ORDERS_TOPIC_EXCHANGE_NAME, "orders.events.json", narudzbenica);
    }



    public Narudzbenica addNarudzbenica(Narudzbenica narudzbenica){
        Narudzbenica narudzbenica1 = new Narudzbenica();






        narudzbenicaRepository.save(narudzbenica1);
        sendAddMessage(narudzbenica1);


        return narudzbenica1;

    }



}
