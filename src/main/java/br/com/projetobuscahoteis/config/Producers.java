package br.com.projetobuscahoteis.config;

import br.com.projetobuscahoteis.dao.DAO;
import br.com.projetobuscahoteis.dao.JpaDAO;
import java.lang.reflect.ParameterizedType;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Producers {
    
    @Produces // Anotação responsável por permitir a injeção dos objetos por dependência.
    @PersistenceContext // Responsável por recuperar o contexto de persistência definido no arquivo persistence.xml
    private EntityManager em;
    
    @Produces // Aqui ele utiliza o InjectionPoint para identificar de onde a dependência foi solicitada.
    public <T> DAO<T> getDao(InjectionPoint ip) {
        // Assim ele irá identificar onde ele deve retornar a dependência solicitada.
        ParameterizedType type = (ParameterizedType) ip.getType();
        Class classe = (Class) type.getActualTypeArguments()[0];
        return new JpaDAO(em, classe);
    }
    
}
