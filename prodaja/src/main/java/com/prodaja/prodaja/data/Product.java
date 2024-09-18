package com.prodaja.prodaja.data;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_prodaja")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {//da pratim cene i stanja
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

    public Integer getSifraArtikla() {
        return sifraArtikla;
    }

    public void setSifraArtikla(Integer sifraArtikla) {
        this.sifraArtikla = sifraArtikla;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public String getJedinicaMere() {
        return jedinicaMere;
    }

    public void setJedinicaMere(String jedinicaMere) {
        this.jedinicaMere = jedinicaMere;
    }

    public Double getCenaPoJediniciMere() {
        return cenaPoJediniciMere;
    }

    public void setCenaPoJediniciMere(Double cenaPoJediniciMere) {
        this.cenaPoJediniciMere = cenaPoJediniciMere;
    }
}
