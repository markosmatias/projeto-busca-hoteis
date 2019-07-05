package br.com.projetobuscahoteis.dto;

import br.com.projetobuscahoteis.model.Cliente;
import br.com.projetobuscahoteis.model.Quarto;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReservaDTO {
    
    @XmlElement
    private Long id;
    
    @XmlElement
    private Date dataInicio;
    
    @XmlElement
    private Date dataFim;
    
    @XmlElement
    private Cliente cliente;
    
    @XmlElement
    private Set<Quarto> quartos;

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
