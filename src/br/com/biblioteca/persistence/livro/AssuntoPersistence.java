package br.com.biblioteca.persistence.livro;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.livro.Assunto;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class AssuntoPersistence extends AbstractPersistence<Assunto> {
	private static final long serialVersionUID = 1L;

	@Override
	public Assunto find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Assunto) this.getNamedQuery("Assunto.findById", parans).
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Assunto> findAll() {
		return this.getNamedQuery("Assunto.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Assunto.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Assunto> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Assunto.findAll", maxResults, firstResult).
				getResultList();
	}

}
