package br.com.projetobuscahoteis.dto;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PostDTO {
    
    @XmlElement
    private Long cidade;
    
    @XmlElement
    private BigDecimal precoInicial;
    
    @XmlElement
    private BigDecimal precoFinal;
    
    @XmlElement
    private Short totalCamas;

    public Long getCidade() {
        return cidade;
    }

    public void setCidade(Long cidade) {
        this.cidade = cidade;
    }

    public BigDecimal getPrecoInicial() {
        return precoInicial;
    }

    public void setPrecoInicial(BigDecimal precoInicial) {
        this.precoInicial = precoInicial;
    }

    public BigDecimal getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(BigDecimal precoFinal) {
        this.precoFinal = precoFinal;
    }

    public Short getTotalCamas() {
        return totalCamas;
    }

    public void setTotalCamas(Short totalCamas) {
        this.totalCamas = totalCamas;
    }
    
}
