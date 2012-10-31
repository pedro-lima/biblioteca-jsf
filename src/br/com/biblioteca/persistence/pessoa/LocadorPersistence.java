package br.com.biblioteca.persistence.pessoa;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.pessoa.Locador;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class LocadorPersistence extends AbstractPersistence<Locador> {
	private static final long serialVersionUID = 1L;

	@Override
	public Locador find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Locador) this.getNamedQuery("Locador.findById", parans).
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Locador> findAll() {
		return this.getNamedQuery("Locador.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Locador.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Locador> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Locador.findAll", maxResults, firstResult).
				getResultList();
	}

}
