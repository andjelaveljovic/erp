package com.roba.roba.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roba.roba.RabbitMQConfigurator;
import com.roba.roba.data.ProbaUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.roba.roba.data.ArtikalRoba;
import com.roba.roba.data.Magacin;
import com.roba.roba.repository.ArtikalRobaRepository;
import com.roba.roba.repository.MagacinRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Random;


import java.util.List;


@Service
public class ArtikalRobaService {

    @Autowired
    private ArtikalRobaRepository artikalRobaRepository;

    @Autowired
    private MagacinRepository magacinRepository;
    private RabbitTemplate rabbitTemplate;


    private static  final Logger LOGGER = LoggerFactory.getLogger(ArtikalRobaService.class);

    Random random = new Random();


    //proba samo da se posalje poruka





    public void sendAddMessage(ArtikalRoba artikalRoba){
        LOGGER.info(String.format("JSON message sent -> %s", artikalRoba.toString()));
        rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME, "product.events.json", artikalRoba);
    }



    public ArtikalRoba addArtikalRoba(ArtikalRoba artikalRoba){
        ArtikalRoba artikalRoba1 = new ArtikalRoba();
        artikalRoba1.setSifraArtikla(artikalRoba.getSifraArtikla());
        artikalRoba1.setNaziv(artikalRoba.getNaziv());
        artikalRoba1.setKolicina(artikalRoba.getKolicina());
        artikalRoba1.setJedinicaMere(artikalRoba.getJedinicaMere());
        artikalRoba1.setCenaPoJediniciMere(artikalRoba.getCenaPoJediniciMere());
        artikalRoba1.setSifraDobavljaca(artikalRoba.getSifraDobavljaca());
        artikalRoba1.setDatum(artikalRoba.getDatum());

        List<Magacin> magacini = magacinRepository.findAll();
        if (magacini.isEmpty()) {
            throw new RuntimeException("No Magacin instances found");
        }

        // Choose a random Magacin
        Magacin randomMagacin = magacini.get(random.nextInt(magacini.size()));
        artikalRoba1.setMagacin(randomMagacin);
        artikalRobaRepository.save(artikalRoba1);
        sendAddMessage(artikalRoba1);


        return artikalRoba1;

    }












}
