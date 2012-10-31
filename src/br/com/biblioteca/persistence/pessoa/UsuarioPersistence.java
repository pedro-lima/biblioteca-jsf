package br.com.biblioteca.persistence.pessoa;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.pessoa.Usuario;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class UsuarioPersistence extends AbstractPersistence<Usuario> {
	private static final long serialVersionUID = 1L;

	@Override
	public Usuario find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Usuario) this.getNamedQuery("Usuario.findById", parans).
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> findAll() {
		return this.getNamedQuery("Usuario.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Usuario.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Usuario.findAll", maxResults, firstResult).
				getResultList();
	}

}

