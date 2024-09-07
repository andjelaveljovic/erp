package com.roba.roba.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "otpremnica")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Otpremnica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "")
}
