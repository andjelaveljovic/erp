package com.prodaja.prodaja.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodaja.prodaja.RabbitMQConfigurator;
import com.prodaja.prodaja.data.Artikal;
import com.prodaja.prodaja.data.Narudzbenica;
import com.prodaja.prodaja.data.Product;
import com.prodaja.prodaja.repository.NarudzbenicaRepository;
import com.prodaja.prodaja.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NarudzbenicaService {
    @Autowired
    private NarudzbenicaRepository narudzbenicaRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    private static final Logger LOGGER = LoggerFactory.getLogger(NarudzbenicaService.class);

    private ObjectMapper objectMapper = new ObjectMapper();


// body dodavanja narudzbenice
//    {
//        "sifraKupca": 123,
//            "nazivKupca": "John Doe",
//            "artikli": [
//        {
//            "sifraArtikla": 1,
//                "kolicina": 2
//        },
//        {
//            "sifraArtikla": 2,
//                "kolicina": 5
//        }
//  ]
//    }

    public void addNarudzbenica(Narudzbenica narudzbenica){


        narudzbenicaRepository.save(narudzbenica);



        // Create Artikal list with only sifraArtikla and kolicina, i onda nakon popunim ostale delove kad dobijem odgovor, tj napravim nove, videcu
        List<Artikal> artikli = narudzbenica.getArtikli().stream()
                .map(artikal -> {
                    Artikal newArtikal = new Artikal(); // Use default constructor
                    newArtikal.setSifraArtikla(artikal.getSifraArtikla());
                    newArtikal.setKolicina(artikal.getKolicina());
                    return newArtikal;
                })
                .collect(Collectors.toList());
        LOGGER.error("proslo stvaranje liste" );
        try {
            String jsonArtikli = objectMapper.writeValueAsString(artikli);
            LOGGER.error("napravljen json" );
            rabbitTemplate.convertAndSend(RabbitMQConfigurator.ORDERS_TOPIC_EXCHANGE_NAME, "orders.events.json", jsonArtikli);
        } catch (Exception e) {
            LOGGER.error("Error serializing Artikal list", e);
        }








    }



}
