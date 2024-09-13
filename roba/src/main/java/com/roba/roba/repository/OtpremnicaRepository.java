package com.roba.roba.repository;

import com.roba.roba.data.Otpremnica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpremnicaRepository extends JpaRepository<Otpremnica, Integer> {
}
