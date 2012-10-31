package br.com.biblioteca.persistence.livro;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.livro.ItemLivro;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class ItemLivroPersistence extends AbstractPersistence<ItemLivro> {
	private static final long serialVersionUID = 1L;

	@Override
	public ItemLivro find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (ItemLivro) this.getNamedQuery("ItemLivro.findById", parans).
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemLivro> findAll() {
		return this.getNamedQuery("ItemLivro.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("ItemLivro.count").getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemLivro> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("ItemLivro.findAll", maxResults, firstResult).
				getResultList();
	}

}
