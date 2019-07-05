package br.com.projetobuscahoteis.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_BAIRRO")
public class Bairro implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BAIRRO")
    private Long id;
    
    @Column(name = "DS_NOME", nullable = false)
    private String nome;
    
    @JoinColumn(name = "ID_CIDADE", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Cidade cidade;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bairro")
    private Set<Hotel> hoteis = new HashSet<>();

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
    
    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Set<Hotel> getHoteis() {
        return hoteis;
    }

    public void setHoteis(Set<Hotel> hoteis) {
        this.hoteis = hoteis;
    }
    
}
