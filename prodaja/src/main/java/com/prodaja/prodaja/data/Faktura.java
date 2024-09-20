package com.prodaja.prodaja.data;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "faktura")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Faktura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "predracun_id", referencedColumnName = "id")//id se odnosi na ovaj id
    private Predracun predracun;

    @Column
    private LocalDate datumPlacanja;

    @Column
    private Double iznos;


}