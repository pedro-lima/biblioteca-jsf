package br.com.biblioteca.model.reserva;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.biblioteca.model.livro.ItemLivro;
import br.com.biblioteca.model.pessoa.Locador;

@Entity
@NamedQueries({
	@NamedQuery(
			name="Reserva.findAll",
			query="SELECT o FROM Reserva o"),
	@NamedQuery(
			name="Reserva.count",
			query="SELECT COUNT(o) FROM Reserva o"),
	@NamedQuery(
			name="Reserva.findById",
			query="SELECT o FROM Reserva o WHERE o.id=:id"),
	@NamedQuery(
			name="Reserva.Livro.count",
			query="SELECT COUNT(o.livros) FROM Reserva o WHERE o.id=:id"),
	@NamedQuery(
			name="Reserva.Livro.findAll",
			query="SELECT o.livros FROM Reserva o WHERE o.id=:id"),
})
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Locador locador = new Locador();
	@Temporal(TemporalType.DATE)
	private Date dataReserva;
	@ManyToMany
	private List<ItemLivro> livros = new ArrayList<ItemLivro>();
	@Enumerated(EnumType.STRING)
	private StatusReserva status;

	public Reserva() {
		super();
	}

	public Reserva(Locador locador, Date dataReserva, List<ItemLivro> livros,
			StatusReserva status) {
		super();
		this.locador = locador;
		this.dataReserva = dataReserva;
		this.livros = livros;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Locador getLocador() {
		return locador;
	}

	public void setLocador(Locador locador) {
		this.locador = locador;
	}

	public Date getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public List<ItemLivro> getLivros() {
		return livros;
	}

	public void setLivros(List<ItemLivro> livros) {
		this.livros = livros;
	}
	
	public void addLivro(ItemLivro livro) {
		this.livros.add(livro);
	}
	
	public void deleteLivro(ItemLivro livro) {
		this.livros.remove(livro);
	}
	
	public void deleteLivro(int index) {
		this.livros.remove(index);
	}
		
	public void limparLivros() {
		this.livros.clear();
	}

	public StatusReserva getStatus() {
		return status;
	}

	public void setStatus(StatusReserva status) {
		this.status = status;
	}
	
	 @Override
	 public boolean equals(Object object) {
		if (!(object instanceof Reserva)) {
			 return false;
		}
		Reserva other = (Reserva) object;
		if ((this.id == null || other.id == null) || 
				(this.id != null && !this.id.equals(other.id))) {
			return false;
		}
	    return true;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", locador=" + locador + ", dataReserva="
				+ dataReserva + "]";
	}
	
}
