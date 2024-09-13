package com.roba.roba.data;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "magacini")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Magacin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//kao upis u tabelu

    @Column(name = "magacin_id")
    private Integer magacinId;

    @Column(name = "datum_dodavanja")
    private LocalDate datum;

    @Column(name = "sifra_dobavljaca")
    private Integer sifraDobavljaca;



    @OneToMany(mappedBy = "magacin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ArtikalRoba> artikli;
}
