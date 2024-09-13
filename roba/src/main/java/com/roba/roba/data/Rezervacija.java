package com.roba.roba.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rezervacije")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}
