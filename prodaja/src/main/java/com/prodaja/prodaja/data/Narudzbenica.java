package com.prodaja.prodaja.data;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "narudzbenice")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Narudzbenica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sifra_kupca", nullable = false)
    private Integer sifraKupca;

    @Column(name = "naziv_kupca")
    private String nazivKupca;

    @OneToMany(mappedBy = "narudzbenica", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Artikal> artikli;


    @OneToOne(mappedBy = "narudzbenica", cascade = CascadeType.ALL)
    private Predracun predracun;// cuva id ovde, povezano


}
