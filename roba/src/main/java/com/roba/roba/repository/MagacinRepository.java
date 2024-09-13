package com.roba.roba.repository;

import com.roba.roba.data.Magacin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagacinRepository extends JpaRepository<Magacin, Integer> {
}
