package br.com.biblioteca.persistence.livro;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.livro.Livro;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class LivroPersistence extends AbstractPersistence<Livro>  {
	private static final long serialVersionUID = 1L;

	@Override
	public Livro find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Livro) this.getNamedQuery("Livro.findById", parans).
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Livro> findAll() {
		return this.getNamedQuery("Livro.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Livro.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Livro> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Livro.findAll", maxResults, firstResult).
				getResultList();
	}	

}
