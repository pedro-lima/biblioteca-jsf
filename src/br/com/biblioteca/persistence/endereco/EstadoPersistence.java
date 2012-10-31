package br.com.biblioteca.persistence.endereco;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.endereco.Estado;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class EstadoPersistence extends AbstractPersistence<Estado> {
	private static final long serialVersionUID = 1L;

	@Override
	public Estado find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Estado) this.getNamedQuery("Estado.findById", parans).
				getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Estado> findAll() {
		return this.getNamedQuery("Estado.findAll").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Estado> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Estado.findAll", 
				maxResults, firstResult).getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Estado.count").getSingleResult();
	}

}
