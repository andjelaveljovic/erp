package com.prodaja.prodaja.service;

import com.prodaja.prodaja.data.Narudzbenica;
import com.prodaja.prodaja.repository.NarudzbenicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/narudzbenica")
public class NarudzbenicaController {

    private NarudzbenicaService narudzbenicaService;



    @PostMapping("/add")
    public ResponseEntity<Narudzbenica> addArtikalRoba(@RequestBody Narudzbenica narudzbenica) {
        Narudzbenica newNarudzbenica = narudzbenicaService.addNarudzbenica(narudzbenica);
        return ResponseEntity.ok(newNarudzbenica);
    }



}
