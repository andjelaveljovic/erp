package com.roba.roba.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artikli_roba")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArtikalRoba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sifra_artikla")
    private  Integer sifraArtikla;

    @Column(name = "naziv")
    private String naziv;

    @Column(name = "kolicina")
    private Integer kolicina;

    @Column(name = "jedinica_mere")
    private String jedinicaMere;

    @Column(name = "cena_po_jedinici_mere")
    private Double cenaPoJediniciMere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magacin_id")  // Foreign key column in artikal_roba table
    private Magacin magacin;



}
