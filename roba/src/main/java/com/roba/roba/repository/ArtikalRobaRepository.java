package com.roba.roba.repository;

import com.roba.roba.data.ArtikalRoba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtikalRobaRepository extends JpaRepository<ArtikalRoba, Integer> {
}
