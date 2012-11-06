package br.com.biblioteca.persistence.pessoa;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.pessoa.Funcionario;
import br.com.biblioteca.model.pessoa.Locador;
import br.com.biblioteca.model.pessoa.Pessoa;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class PessoaPersistence extends AbstractPersistence<Pessoa> {
	private static final long serialVersionUID = 1L;

	@Override
	public Pessoa find(long id) {
		return this.manager.find(Pessoa.class,id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Pessoa> findAll() {
		return this.getNamedQuery("Pessoa.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Locador> findAllLocadores() {
		return this.getNamedQuery("Locador.findAll").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Funcionario> findAllFuncionarios() {
		return this.getNamedQuery("Funcionario.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Pessoa.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Pessoa> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Pessoa.findAll", maxResults, firstResult).
				getResultList();
	}

	public Locador findLocadorEnderecoCompleto(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id", id));
		return (Locador) this.getNamedQuery("Locador.Join.EnderecoCompleto", parans).getSingleResult();
	}

}
