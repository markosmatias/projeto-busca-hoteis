package br.com.projetobuscahoteis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TB_RESERVA")
@NamedQueries({
    @NamedQuery(name = Reserva.FIND_BY_DATAS, query = "SELECT r FROM Reserva r "
                                                    + "WHERE r.dataInicio <= :dtIni "
                                                    + "OR r.dataInicio >= :dtFim "
                                                    + "OR r.dataFim >= :dtFim "
                                                    + "OR r.dataFim <= :dtIni")
})
public class Reserva implements Serializable {
    
    public static final String FIND_BY_DATAS = "Reserva.findByDatas";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA")
    private Long id;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DATA_INICIO", nullable = false)
    private Date dataInicio;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "DT_DATA_FIM", nullable = false)
    private Date dataFim;
    
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cliente cliente;
    
    @JoinTable(name = "TB_RESERVA_QUARTO", joinColumns = {
        @JoinColumn(name = "ID_RESERVA")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_QUARTO")
    })
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Quarto> quartos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(Set<Quarto> quartos) {
        this.quartos = quartos;
    }
    
}
