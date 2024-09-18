package com.prodaja.prodaja.repository;

import com.prodaja.prodaja.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
