package com.prodaja.prodaja.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.prodaja.prodaja.data.Product;
import com.prodaja.prodaja.data.SuccesMessage;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtikalService.class);

    @RabbitListener(queues = "queue-products-json")
    public void consumeJson(String message) {
        try {
            LOGGER.info("Stigla poruka, da li radi? ili greska");

            // Check if the message contains "price"
            if (message.contains("price")) {
                // Assuming the format is "price: 99.99"
                String[] parts = message.split(":");

                // Ensure that the format is correct and extract the price
                if (parts.length == 2) {
                    String priceString = parts[1].trim(); // Remove any leading/trailing whitespace
                    priceString = priceString.replace("\"", ""); // Remove any quotes if present
                    double price = Double.parseDouble(priceString); // Convert the string to a double

                    // Log the extracted price
                    LOGGER.info("Uspesna kupovina, cena: {}", price);
                } else {
                    LOGGER.error("Unexpected message format: {}", message);
                }

                // Check if the message contains "sifraDobavljaca" to process JSON
            } else if (message.contains("sifraDobavljaca")) {
                // Parse the JSON message
                JsonNode jsonNode = objectMapper.readTree(message);
                Integer sifra = jsonNode.get("sifraArtikla").asInt();
                String naziv = jsonNode.get("naziv").asText();
                Integer kolicina = jsonNode.get("kolicina").asInt();
                String jedinicaMere = jsonNode.get("jedinicaMere").asText();
                Double cenaPoJediniciMere = jsonNode.get("cenaPoJediniciMere").asDouble();
                Integer sifraDobavljaca = jsonNode.get("sifraDobavljaca").asInt();
                String datumStr = jsonNode.get("datum").asText();

                // Create Product object and set values
                Product product = new Product();
                product.setSifraArtikla(sifra);
                product.setNaziv(naziv);
                product.setKolicina(kolicina);
                product.setJedinicaMere(jedinicaMere);
                product.setCenaPoJediniciMere(cenaPoJediniciMere);

                // Save the Product entity to the database
                productRepository.save(product);

                // Log the extracted data
                LOGGER.info("Received JSON message -> sifra: {}, naziv: {}, kolicina: {}, jedinicaMere: {}, cenaPoJediniciMere: {}, sifraDobavljaca: {}, datum: {}",
                        sifra, naziv, kolicina, jedinicaMere, cenaPoJediniciMere, sifraDobavljaca, datumStr);
            } else {
                // Log any other message
                LOGGER.info("Received message: {}", message);
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON processing error: {}", e.getMessage());
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse price: {}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("General error while processing message: {}", e.getMessage(), e);
        }


    }
}
