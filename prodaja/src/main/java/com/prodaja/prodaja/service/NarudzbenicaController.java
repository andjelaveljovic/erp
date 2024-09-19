package com.prodaja.prodaja.service;

import com.prodaja.prodaja.data.Narudzbenica;
import com.prodaja.prodaja.repository.NarudzbenicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/narudzbenica")
public class NarudzbenicaController {
    @Autowired
    private NarudzbenicaService narudzbenicaService;




    @PostMapping("/add")
    public ResponseEntity<String> addArtikalRoba(@RequestBody Narudzbenica narudzbenica) {
        try{
            narudzbenicaService.addNarudzbenica(narudzbenica);
            return ResponseEntity.ok("Dodata narudzbenica");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding order: " + e.getMessage());
        }

    }



}
