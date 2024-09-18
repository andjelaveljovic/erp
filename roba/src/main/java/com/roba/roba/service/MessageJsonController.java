//package com.roba.roba.service;
//
//import com.roba.roba.data.ProbaUser;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1")
//public class MessageJsonController {
//    private JSONproducer jsonProducer;
//
//    public MessageJsonController(JSONproducer jsonProducer) {
//        this.jsonProducer = jsonProducer;
//    }
//    @PostMapping("/publish")
//    public ResponseEntity<String> sendJsonMessage(@RequestBody ProbaUser probaUser){
//        jsonProducer.sendJSONMessage(probaUser);
//        return ResponseEntity.ok("Json message sent to RabbitMQ");
//    }
//}
