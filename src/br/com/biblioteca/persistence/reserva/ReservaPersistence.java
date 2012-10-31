package br.com.biblioteca.persistence.reserva;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import br.com.biblioteca.model.reserva.Reserva;
import br.com.biblioteca.persistence.AbstractPersistence;
import br.com.biblioteca.persistence.QueryParam;

@Stateless
public class ReservaPersistence extends AbstractPersistence<Reserva> {
	private static final long serialVersionUID = 1L;

	@Override
	public Reserva find(long id) {
		List<QueryParam> parans = new ArrayList<QueryParam>();
		parans.add(new QueryParam("id",id));
		return (Reserva) this.getNamedQuery("Reserva.findById").
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Reserva> findAll() {
		return this.getNamedQuery("Reserva.findAll").getResultList();
	}

	@Override
	public long count() {
		return (Long) this.getNamedQuery("Reserva.count").
				getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Reserva> findRange(int maxResults, int firstResult) {
		return this.getNamedQuery("Reserva.findAll", maxResults, firstResult).
				getResultList();
	}

}
