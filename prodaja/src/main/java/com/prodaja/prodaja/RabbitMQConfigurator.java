package com.prodaja.prodaja;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfigurator {

    public static final String ORDERS_TOPIC_EXCHANGE_NAME = "orders-events-exchange";


    // public static final String PRODUCTS_SERVICE_QUEUE = "products-service-queue";
    public static final String ORDERS_SERVICE_QUEUE_JSON = "queue-orders-json";


}
