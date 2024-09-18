package com.roba.roba.service;

import com.roba.roba.data.ArtikalRoba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/product")
public class ArtikliRobaController {
    @Autowired
    ArtikalRobaService artikalRobaService;


    @PostMapping("/add")
    public ResponseEntity<ArtikalRoba> addArtikalRoba(@RequestBody ArtikalRoba artikalRoba) {
        ArtikalRoba newArtikalRoba = artikalRobaService.addArtikalRoba(artikalRoba);
        return ResponseEntity.ok(newArtikalRoba);
    }
}
