package com.roba.roba.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roba.roba.RabbitMQConfigurator;
import com.roba.roba.data.ProbaUser;
import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import java.util.List;


@Service
public class ArtikalRobaService {

    @Autowired
    private ArtikalRobaRepository artikalRobaRepository;

    @Autowired
    private MagacinRepository magacinRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    private static  final Logger LOGGER = LoggerFactory.getLogger(ArtikalRobaService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

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



    @RabbitListener(queues = "queue-orders-json")
    public void receiveMessage(String message) {
        try {
            // Parse the JSON message
            JsonNode jsonNode = objectMapper.readTree(message);

            // Create a list to hold extracted values
            List<ArtikalRoba> artikli = new ArrayList<>();

            // Iterate over the JSON array
            if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    // Extract specific values
                    Integer sifraArtikla = node.has("sifraArtikla") ? node.get("sifraArtikla").asInt() : null;
                    Integer kolicina = node.has("kolicina") ? node.get("kolicina").asInt() : null;

                    // Log or use the extracted values
                    LOGGER.info("Extracted sifraArtikla: {}, kolicina: {}", sifraArtikla, kolicina);

                    // Create and populate ArtikalRoba instance
                    ArtikalRoba artikalRoba = new ArtikalRoba();
                    artikalRoba.setSifraArtikla(sifraArtikla);
                    artikalRoba.setKolicina(kolicina);

                    // Add to the list
                    artikli.add(artikalRoba);//artikli koje trazimo
                }
            }

            // Process the list of ArtikalRoba
            for (ArtikalRoba artikal : artikli) {
                // Example: Save to database or perform other actions
                LOGGER.info("Processed ArtikalRoba: {}", artikal);
            }
        } catch (IOException e) {
            LOGGER.error("Error processing JSON message", e);
        }
    }














}
