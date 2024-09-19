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

    @Column(name = "sifra_artikla")
    private  Integer sifraArtikla;

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

    public Integer getCenaPoJedinici() {
        return cenaPoJedinici;
    }

    public void setCenaPoJedinici(Integer cenaPoJedinici) {
        this.cenaPoJedinici = cenaPoJedinici;
    }

    public Integer getPdv() {
        return pdv;
    }

    public void setPdv(Integer pdv) {
        this.pdv = pdv;
    }

    public Integer getCenaSaPdv() {
        return cenaSaPdv;
    }

    public void setCenaSaPdv(Integer cenaSaPdv) {
        this.cenaSaPdv = cenaSaPdv;
    }

    public Narudzbenica getNarudzbenica() {
        return narudzbenica;
    }

    public void setNarudzbenica(Narudzbenica narudzbenica) {
        this.narudzbenica = narudzbenica;
    }
}
