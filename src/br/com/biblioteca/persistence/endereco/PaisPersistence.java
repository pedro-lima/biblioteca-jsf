package br.com.biblioteca.persistence.endereco;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class PaisPersistence extends AbstractPersistence<Pais>{
	private static final long serialVersionUID = 1L;
			
	@Override
	public Pais find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id", id));
		return (Pais) this.getNamedQuery("Pais.findById",parans).
				getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Pais> findAll() {		
		return this.getNamedQuery("Pais.findAll").getResultList();        
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Pais> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Pais.findAll",firstResult,maxResults).getResultList();
	}

	@Override
	public long count() {
        return (Long) this.getNamedQuery("Pais.count").getSingleResult();
	}
	
}
