package com.prodaja.prodaja.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "artikli")
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

    @Column(name = "cena_po_jedinici")
    private Integer cenaPoJedinici;

    @Column(name = "pdv")
    private Integer pdv;

    @Column(name = "cena_sa_pdv")
    private Integer cenaSaPdv;

    @ManyToOne
    @JoinColumn(name = "narudzbenica_id")
    private Narudzbenica narudzbenica;




}
