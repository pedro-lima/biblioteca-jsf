package br.com.biblioteca.persistence.emprestimo;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.emprestimo.Emprestimo;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class EmprestimoPersistence extends AbstractPersistence<Emprestimo> {
	private static final long serialVersionUID = 1L;

	@Override
	public Emprestimo find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Emprestimo) this.getNamedQuery("Emprestimo.findById").
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Emprestimo> findAll() {
		return this.getNamedQuery("Emprestimo.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Emprestimo.count").
				getSingleResult();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Emprestimo> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Emprestimo.findAll", maxResults, firstResult).
				getResultList();
	}

}
