package br.com.biblioteca.persistence.livro;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.livro.Editora;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class EditoraPersistence extends AbstractPersistence<Editora> {
	private static final long serialVersionUID = 1L;

	@Override
	public Editora find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Editora) this.getNamedQuery("Editora.findById", parans).
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Editora> findAll() {
		return this.getNamedQuery("Editora.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Editora.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Editora> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Editora.findAll", maxResults, firstResult).
				getResultList();
	}

}
