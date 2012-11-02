package br.com.biblioteca.persistence.endereco;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.endereco.Cidade;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class CidadePersistence extends AbstractPersistence<Cidade> {
	private static final long serialVersionUID = 1L;

	@Override
	public Cidade find(long id) {
		return this.manager.find(Cidade.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Cidade> findAll() {
		return this.getNamedQuery("Cidade.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Cidade.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Cidade> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Cidade.findAll", maxResults, firstResult)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cidade> findAllByEstado(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id", id));
		return this.getNamedQuery("Estado.Cidade.findAll", parans).getResultList();
	}

}
