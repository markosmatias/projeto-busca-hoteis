package br.com.projetobuscahoteis.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
// Implementação do DAO
public class JpaDAO<T> implements DAO<T> {

    private final EntityManager em;
    private final Class<T> classe;

    public JpaDAO(EntityManager em, Class<T> classe) {
        this.em = em;
        this.classe = classe;
    }

    @Override
    public T findById(Long id) {
        return id != null ? em.find(classe, id) : null;
    }

    @Override
    public boolean remove(T entity) {
        em.remove(entity);
        return true;
    }

    @Override
    public boolean removeById(Long id) {
        T entity = this.findById(id);
        em.remove(entity);
        return true;
    }

    @Override
    public void save(T entity) {
        em.persist(entity);
    }

    @Override
    public T atualizar(T entity) {
       return em.merge(entity);
    }

    @Override
    public List<T> findByFields(Map<String, Object> fields, List<String> joins) {
        // Recupera o construtor através do EntityManager.
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        // Cria a base da query a ser realizada para a entidade T.
        CriteriaQuery<T> query = builder.createQuery(classe);
        
        // Basicamente significa "SELECT * FROM entidade T"
        Root<T> from = query.from(classe);
        for (String field : joins) {
            // Realiza o INNER JOIN para as entidades que possuem relacionamento com T
            from.join(field);
        }
        
        List<Predicate> predicados = new ArrayList<>();
        
        // Aqui é onde o WHERE é montado.
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            // Adiciona na lista do WHERE as condições
            predicados.add(builder.equal(from.get(field.getKey()), field.getValue()));
        }
        
        // Aqui é onde as partes são unidas, formando "SELECT * FROM entidade T WHERE predicados"
        query.select(from).where(predicados.toArray(new Predicate[predicados.size()]));
        // Prepara a query para ser consultada
        TypedQuery<T> typedQuery = this.em.createQuery(query);
        // Roda a query no banco
        return typedQuery.getResultList();
    }
    
    @Override
    public List<T> findByNamedQuery(String namedQuery, Map<String, Object> parameters) {
        TypedQuery<T> query = this.em.createNamedQuery(namedQuery, classe);
        for (Map.Entry<String, Object> parameter : parameters.entrySet()) {
            query.setParameter(parameter.getKey(), parameter.getValue());
        }
        return query.getResultList();
    }
    
}
