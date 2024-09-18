package com.roba.roba.data;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToMany(mappedBy = "magacin", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ArtikalRoba> artikli;

    public Integer getId() {
        return id;
    }

    public Integer getMagacinId() {
        return magacinId;
    }
    @Override
    public String toString() {
        return "Magacin{" +
                "id=" + magacinId +


                '}';
    }
}
