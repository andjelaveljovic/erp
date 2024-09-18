//package com.roba.roba.service;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/v1")
//public class MessageController {
//
//    private ArtikalRobaService artikalRobaService;
//
//
//    public MessageController(ArtikalRobaService artikalRobaService){
//        this.artikalRobaService = artikalRobaService;
//    }
//    @GetMapping("/publish")
//    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
//        artikalRobaService.sendMessage(message);
//        return  ResponseEntity.ok("poslata u rabitmq");
//
//    }
//
//}
