package com.prodaja.prodaja.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.prodaja.prodaja.data.ProbaUser;
import com.prodaja.prodaja.data.Product;
import com.prodaja.prodaja.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
//proba consumera
@Service
public class ArtikalService {

    @Autowired
    private ProductRepository productRepository;

//
//    private static  final Logger LOGGER = LoggerFactory.getLogger(ArtikalService.class);
//    @RabbitListener(queues = "products-service-queue")//case sensitive
//    public void consume(String message){
//        LOGGER.info(String.format("Dobili smo poruku -> %s" , message));
//
//
//
//
//
//    }
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static  final Logger LOGGER = LoggerFactory.getLogger(ArtikalService.class);
    @RabbitListener(queues = "queue-json")
    public void consumeJson(String message) {
        try {
            // Parse the JSON string
            JsonNode jsonNode = objectMapper.readTree(message);

             Integer sifra = jsonNode.get("sifraArtikla").asInt();
             String naziv = jsonNode.get("naziv").asText();
             Integer kolicina = jsonNode.get("kolicina").asInt();
             String jedinicaMere = jsonNode.get("jedinicaMere").asText();
             Double cenaPoJediniciMere = jsonNode.get("cenaPoJediniciMere").asDouble();
             Integer sifraDobavljaca = jsonNode.get("sifraDobavljaca").asInt();
             String datumStr = jsonNode.get("datum").asText();

            Product product = new Product();
            product.setSifraArtikla(sifra);
            product.setNaziv(naziv);
            product.setKolicina(kolicina);
            product.setJedinicaMere(jedinicaMere);
            product.setCenaPoJediniciMere(cenaPoJediniciMere);

            // Save the Product entity to the database
            productRepository.save(product);

            // Log or process the extracted data
            LOGGER.info("Received JSON message -> sifra: {}, naziv: {}, kolicina: {}, jedinicaMere: {}, cenaPoJediniciMere: {}", sifra ,naziv, kolicina, jedinicaMere, cenaPoJediniciMere);
        } catch (Exception e) {
            LOGGER.error("Failed to parse message", e);
        }
    }




}
