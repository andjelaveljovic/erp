package com.roba.roba.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Column(name = "sifra_dobavljaca")
    private Integer sifraDobavljaca;

    @Column(name = "datum_dodavanja")
    private LocalDate datum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magacin_id")  // Foreign key column in artikal_roba table
    @JsonBackReference
    private Magacin magacin;

    public Integer getId() {
        return id;
    }

    public Integer getSifraArtikla() {
        return sifraArtikla;
    }

    public String getNaziv() {
        return naziv;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public String getJedinicaMere() {
        return jedinicaMere;
    }

    public Double getCenaPoJediniciMere() {
        return cenaPoJediniciMere;
    }

    public Integer getSifraDobavljaca() {
        return sifraDobavljaca;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public Magacin getMagacin() {
        return magacin;
    }

    public void setSifraArtikla(Integer sifraArtikla) {
        this.sifraArtikla = sifraArtikla;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public void setJedinicaMere(String jedinicaMere) {
        this.jedinicaMere = jedinicaMere;
    }

    public void setCenaPoJediniciMere(Double cenaPoJediniciMere) {
        this.cenaPoJediniciMere = cenaPoJediniciMere;
    }

    public void setSifraDobavljaca(Integer sifraDobavljaca) {
        this.sifraDobavljaca = sifraDobavljaca;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setMagacin(Magacin magacin) {
        this.magacin = magacin;
    }
    @Override
    public String toString() {
        return "ArtikalRoba{" +
                "id=" + id +
                ", sifraArtikla=" + sifraArtikla +
                ", naziv='" + naziv + '\'' +
                ", kolicina=" + kolicina +
                ", jedinicaMere='" + jedinicaMere + '\'' +
                ", cenaPoJediniciMere=" + cenaPoJediniciMere +
                ", sifraDobavljaca=" + sifraDobavljaca +
                ", datum=" + datum +
                ", magacinId=" + (magacin != null ? magacin.getMagacinId() : "null") +
                '}';
    }
}
