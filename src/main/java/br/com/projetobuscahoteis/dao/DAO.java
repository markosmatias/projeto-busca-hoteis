package br.com.projetobuscahoteis.dao;

import java.util.List;
import java.util.Map;

// Interface responsável pelo acesso ao banco de dados através dos objetos.
public interface DAO<T> {
    
    public T findById(Long id);
    
    public boolean remove(T entity);
    
    public boolean removeById(Long id);
    
    public void save(T entity);
    
    public T atualizar(T entity);
    
    public List<T> findByFields(Map<String, Object> fields, List<String> joins);
    
    public List<T> findByNamedQuery(String namedQuery, Map<String, Object> parameters);
    
}
