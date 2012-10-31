package br.com.biblioteca.persistence;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public abstract class AbstractPersistence<T> implements GenericPersistence<T>, Serializable{
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	protected EntityManager manager;
		
	@Override
	public void create(T t) {
		manager.persist(t);
	}

	@Override
	public void remove(T t) {
		manager.remove(t);		
	}

	@Override
	public void update(T t) {
		manager.getTransaction().begin();
		manager.merge(t);
		manager.getTransaction().commit();
	}
	
	protected Query getNamedQuery(String nome) {
		return this.manager.createNamedQuery(nome); 
	}
	
	protected Query getNamedQuery(String nome,List<QueryParam> parans) {
		Query query = this.getNamedQuery(nome);
		for(QueryParam param:parans) {
			query.setParameter(param.getName(), param.getValue());
		}
		return query;
	}
	
	protected Query getNamedQuery(String nome,int maxResults, int firstResult) {		
		return this.getNamedQuery(nome).
				setFirstResult(firstResult).setMaxResults(maxResults);		
	}
	
	protected Query getNamedQuery(String nome,List<QueryParam> parans,	int maxResults, int firstResult) {
		return this.getNamedQuery(nome, parans).setFirstResult(firstResult).setMaxResults(maxResults);
	}
			
}
