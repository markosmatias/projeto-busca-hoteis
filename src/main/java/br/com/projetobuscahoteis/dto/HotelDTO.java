package br.com.projetobuscahoteis.dto;

import br.com.projetobuscahoteis.model.Bairro;
import br.com.projetobuscahoteis.model.Quarto;
import java.util.Set;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// DTO = DATA TRANSFER OBJECT, nada mais que uma classe responsável por passar 
// os dados para a entidade principal. Usado devido a questões de segurança, 
// pois é possivel haver quebra de segurança utilizando as próprias entidades
// nas requisições.
@XmlRootElement
public class HotelDTO {
    
    @XmlElement
    private Long id;
    
    @XmlElement
    private String nome;
    
    @XmlElement
    private Short estrelas;
    
    @XmlElement
    private Bairro bairro;
    
    @XmlElement
    private Set<Quarto> quartos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Short getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Short estrelas) {
        this.estrelas = estrelas;
    }

    public Bairro getBairro() {
        return bairro;
    }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    public Set<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(Set<Quarto> quartos) {
        this.quartos = quartos;
    }
    
}
