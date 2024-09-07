package com.roba.roba.data;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

@Entity
@Table(name = "artikli_roba")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Artikal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "kolicina")
    private Integer kolicina;

    @Column(name = "jedinica_mere")
    private String jedinicaMere;

    @Column(name = "cena_po_jedinici_mere")
    private Double cenaPoJediniciMere;


    //kako sa dobavljacima i magacinima i ove liste
}
