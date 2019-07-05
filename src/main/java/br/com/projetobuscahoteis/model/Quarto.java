package br.com.projetobuscahoteis.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_QUARTO")
public class Quarto implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_QUARTO")
    private Long id;
    
    @Column(name = "NM_ANDAR", nullable = false)
    private Short andar;
    
    @Column(name = "NM_NUMERO", nullable = false)
    private Short numero;
    
    @Column(name = "NM_QUANTIDADE_CAMAS", nullable = false)
    private Short quantidadeCamas;
    
    @Column(name = "VL_PRECO", nullable = false)
    private BigDecimal preco;
    
    @JoinColumn(name = "ID_HOTEL", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Hotel hotel;
    
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "quartos")
    private Set<Reserva> reservas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Short getAndar() {
        return andar;
    }

    public void setAndar(Short andar) {
        this.andar = andar;
    }

    public Short getNumero() {
        return numero;
    }

    public void setNumero(Short numero) {
        this.numero = numero;
    }

    public Short getQuantidadeCamas() {
        return quantidadeCamas;
    }

    public void setQuantidadeCamas(Short quantidadeCamas) {
        this.quantidadeCamas = quantidadeCamas;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }
    
}
