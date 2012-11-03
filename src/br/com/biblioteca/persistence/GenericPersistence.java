package br.com.biblioteca.persistence;

import java.util.List;
 
public interface GenericPersistence<T> {
	public void create(T t);
	public void remove(T t);
	public void update(T t);
	public T find(long id);
	public List<T> findAll();
	public long count();	
	public List<T> findRange(int maxResults, int firstResult);
	
}
