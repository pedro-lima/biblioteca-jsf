package br.com.biblioteca.persistence.endereco;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.endereco.Endereco;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class EnderecoPersistence extends AbstractPersistence<Endereco> {
	private static final long serialVersionUID = 1L;

	@Override
	public Endereco find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Endereco) this.getNamedQuery("Endereco.findById").
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Endereco> findAll() {
		return this.getNamedQuery("Endereco.findAll").getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Endereco> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Endereco.findAll", maxResults, firstResult).
				getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Endereco.count").getSingleResult();
	}

}
