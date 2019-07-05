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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TB_HOTEL")
@NamedQueries({
    @NamedQuery(name = Hotel.FIND_BY_CIDADE_PRECO_CAMAS, query = "SELECT DISTINCT h FROM Hotel h "
                                                               + "INNER JOIN FETCH h.bairro b "
                                                               + "INNER JOIN b.cidade c "
                                                               + "INNER JOIN h.quartos q "
                                                               + "WHERE c.id = :cidade "
                                                               + "AND q.quantidadeCamas = :totalCamas "
                                                               + "AND q.preco BETWEEN :precoInicial AND :precoFinal ")
})
public class Hotel implements Serializable {
    
    public static final String FIND_BY_CIDADE_PRECO_CAMAS = "Hotel.findByCidadePrecoCamas";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HOTEL")
    private Long id;
    
    @Column(name = "DS_NOME", nullable = false)
    private String nome;
    
    @Column(name = "NM_ESTRELAS", nullable = false)
    private Short estrelas;
    
    // FetchType.LAZY pois é uma boa prática de programação, que evita o carregamento do objeto.
    // de forma automática. Assim o Hibernate só deve carregar o objeto se for solicitado
    // através do "FETCH" na query JPQL
    @JoinColumn(name = "ID_BAIRRO", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Bairro bairro;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel")
    private Set<Quarto> quartos = new HashSet<>();

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
