package com.roba.roba.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roba.roba.RabbitMQConfigurator;
import com.roba.roba.data.ProbaUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.roba.roba.data.SuccesMessage;
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
import java.util.*;


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
            LOGGER.info("Received message in 'roba'");
            // Parse the JSON message
            JsonNode jsonNode = objectMapper.readTree(message);

            // List to hold found ArtikalRoba with all attributes
            List<ArtikalRoba> foundArtikals = new ArrayList<>();
            // Map to hold the found ArtikalRoba and their new quantities
            Map<ArtikalRoba, Integer> foundArtikalsWithNewQuantities = new HashMap<>();
            boolean allItemsFound = true;

            // Iterate over the JSON array
            if (jsonNode.isArray()) {
                for (JsonNode node : jsonNode) {
                    Integer sifraArtikla = node.has("sifraArtikla") ? node.get("sifraArtikla").asInt() : null;
                    Integer kolicina = node.has("kolicina") ? node.get("kolicina").asInt() : null;

                    LOGGER.info("Extracted sifraArtikla: {}, kolicina: {}", sifraArtikla, kolicina);

                    if (sifraArtikla != null && kolicina != null) {
                        List<ArtikalRoba> availableArtikals = artikalRobaRepository.findBySifraArtikla(sifraArtikla);

// Log the found articles
                        if (availableArtikals.isEmpty()) {
                            LOGGER.info("No ArtikalRoba found for sifraArtikla: {}", sifraArtikla);
                        } else {
                            LOGGER.info("Found ArtikalRoba for sifraArtikla {}:", sifraArtikla);
                            for (ArtikalRoba artikal : availableArtikals) {
                                LOGGER.info("ArtikalRoba - Sifra: {}, Naziv: {}, Kolicina: {}, JedinicaMere: {}, CenaPoJediniciMere: {}, SifraDobavljaca: {}, Datum: {}",
                                        artikal.getSifraArtikla(),
                                        artikal.getNaziv(),
                                        artikal.getKolicina(),
                                        artikal.getJedinicaMere(),
                                        artikal.getCenaPoJediniciMere(),
                                        artikal.getSifraDobavljaca(),
                                        artikal.getDatum());
                            }
                        }
                        int totalFoundQuantity = 0;

                        for (ArtikalRoba availableArtikal : availableArtikals) {
                            int availableQuantity = availableArtikal.getKolicina();
                            int toTake = Math.min(availableQuantity, kolicina - totalFoundQuantity);

                            if (toTake > 0) {
                                ArtikalRoba foundArtikal = new ArtikalRoba();
                                foundArtikal.setSifraArtikla(availableArtikal.getSifraArtikla());
                                foundArtikal.setNaziv(availableArtikal.getNaziv());
                                foundArtikal.setKolicina(toTake);
                                foundArtikal.setJedinicaMere(availableArtikal.getJedinicaMere());
                                foundArtikal.setCenaPoJediniciMere(availableArtikal.getCenaPoJediniciMere());
                                foundArtikal.setSifraDobavljaca(availableArtikal.getSifraDobavljaca());
                                foundArtikal.setDatum(availableArtikal.getDatum());

                                foundArtikals.add(foundArtikal);
                                totalFoundQuantity += toTake;

                                // Track the quantities to update later
                                foundArtikalsWithNewQuantities.put(availableArtikal, toTake);

//                                // Update available quantity in the database
//                                availableArtikal.setKolicina(availableQuantity - toTake);
//                                artikalRobaRepository.save(availableArtikal);

                                // Check if enough quantity is found
                                if (totalFoundQuantity >= kolicina) {
                                    break;  // Break out of the loop if enough quantity is found
                                }
                            }
                        }

                        // If not enough quantity was found, send error message
                        if (totalFoundQuantity < kolicina) {
                            LOGGER.info("Not all items found for sifraArtikla: {}", sifraArtikla);
                            String errorMessage = String.format("Not enough quantity for ArtikalRoba with sifraArtikla %d", sifraArtikla);
                            rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME, "product.events.json", errorMessage);
                            allItemsFound = false;
                            break;  // Stop processing further items
                        }
                    }
                }
            }




            if (allItemsFound) {
                LOGGER.info("svi su nadjeni, sad promena u bazi");
                // Update the quantities in the database
                for (Map.Entry<ArtikalRoba, Integer> entry : foundArtikalsWithNewQuantities.entrySet()) {
                    ArtikalRoba artikal = entry.getKey();
                    int newQuantity = artikal.getKolicina() - entry.getValue(); // Update with found quantity

                    // Update or remove from the database
                    if (newQuantity > 0) {
                        artikal.setKolicina(newQuantity);
                        artikalRobaRepository.save(artikal);
                    } else {
                        artikalRobaRepository.delete(artikal); // Or handle deletion as needed
                    }
                }
                double totalPrice = 0;
                for (ArtikalRoba artikal : foundArtikals) {
                    totalPrice += artikal.getCenaPoJediniciMere() * artikal.getKolicina();
                }
                //SuccesMessage succesMessage = new SuccesMessage(totalPrice);
                String porukaSuccess = "price:" + totalPrice;
                LOGGER.info("pokusaj slanja prodaji");
                LOGGER.info("Sending SuccesMessageMENI: {}", porukaSuccess);
                // Send successful items to the successful-queue
                rabbitTemplate.convertAndSend(RabbitMQConfigurator.PRODUCTS_TOPIC_EXCHANGE_NAME, "product.events.json",porukaSuccess);
            }

            // Log the results
            LOGGER.info("Processed found ArtikalRoba: {}", foundArtikals);

        } catch (IOException e) {
            LOGGER.error("Error processing JSON message", e);
        }
    }

//
//    @RabbitListener(queues = "queue-orders-json")
//    public void receiveMessage(String message) {
//        try {
//            // Parse the JSON message
//            JsonNode jsonNode = objectMapper.readTree(message);
//
//            // Create a list to hold extracted values
//            List<ArtikalRoba> artikli = new ArrayList<>();
//
//            // Iterate over the JSON array
//            if (jsonNode.isArray()) {
//                for (JsonNode node : jsonNode) {
//                    // Extract specific values
//                    Integer sifraArtikla = node.has("sifraArtikla") ? node.get("sifraArtikla").asInt() : null;
//                    Integer kolicina = node.has("kolicina") ? node.get("kolicina").asInt() : null;
//
//                    // Log or use the extracted values
//                    LOGGER.info("Extracted sifraArtikla: {}, kolicina: {}", sifraArtikla, kolicina);
//
//                    // Create and populate ArtikalRoba instance
//                    ArtikalRoba artikalRoba = new ArtikalRoba();
//                    artikalRoba.setSifraArtikla(sifraArtikla);
//                    artikalRoba.setKolicina(kolicina);
//
//                    // Add to the list
//                    artikli.add(artikalRoba);//artikli koje trazimo
//                }
//            }
//
//            // Process the list of ArtikalRoba
//            for (ArtikalRoba artikal : artikli) {
//                // Example: Save to database or perform other actions
//                LOGGER.info("Processed ArtikalRoba: {}", artikal);
//            }
//        } catch (IOException e) {
//            LOGGER.error("Error processing JSON message", e);
//        }
//    }
//













}
