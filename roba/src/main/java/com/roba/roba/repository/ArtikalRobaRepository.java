package com.roba.roba.repository;

import com.roba.roba.data.ArtikalRoba;
import com.roba.roba.data.Magacin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ArtikalRobaRepository extends JpaRepository<ArtikalRoba, Integer> {
     @Query("SELECT a FROM ArtikalRoba a WHERE a.sifraArtikla = :sifraArtikla")
     List<ArtikalRoba> findBySifraArtikla(@Param("sifraArtikla") Integer sifraArtikla);
}
