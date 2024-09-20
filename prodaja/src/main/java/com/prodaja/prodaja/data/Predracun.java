package com.prodaja.prodaja.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "predracuni")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Predracun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ukupna_cena")
    private Double ukupnaCena;//marza 20 posto

    @Column(name = "datum_palacanja")
    private LocalDate datum;

    @OneToOne
    @JoinColumn(name = "narudzbenica_id", referencedColumnName = "id")//id se odnosi na ovaj id
    private Narudzbenica narudzbenica;// cuva id ovde, povezano






}
