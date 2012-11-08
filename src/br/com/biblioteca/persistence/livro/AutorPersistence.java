package br.com.biblioteca.persistence.livro;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

import br.com.biblioteca.model.livro.Autor;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class AutorPersistence extends AbstractPersistence<Autor> {
	private static final long serialVersionUID = 1L;

	@Override
	public Autor find(long id) {
		return this.manager.find(Autor.class, id);
	}

	public Autor findAutorGetLivros(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id", id));
		return (Autor) this.getNamedQuery("Autor.Join.Livro", parans)
				.getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Autor> findAll() {
		return this.getNamedQuery("Autor.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Autor.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Autor> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Autor.findAll", maxResults, firstResult)
				.getResultList();
	}

}
