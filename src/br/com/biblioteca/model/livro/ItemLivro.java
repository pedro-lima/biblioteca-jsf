package br.com.biblioteca.model.livro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import br.com.biblioteca.model.emprestimo.Emprestimo;
import br.com.biblioteca.model.reserva.Reserva;

@Entity
@NamedQueries({
		@NamedQuery(name = "ItemLivro.findAll", query = "SELECT o FROM ItemLivro o"),
		@NamedQuery(name = "ItemLivro.count", query = "SELECT COUNT(o) FROM ItemLivro o"),
		@NamedQuery(name = "ItemLivro.findById", query = "SELECT o FROM ItemLivro o WHERE o.id=:id"),
		@NamedQuery(name = "ItemLivro.Reserva.count", query = "SELECT COUNT(o.reservas) FROM ItemLivro o WHERE o.id=:id"),
		@NamedQuery(name = "ItemLivro.Reserva.findAll", query = "SELECT o.reservas FROM ItemLivro o WHERE o.id=:id"),
		@NamedQuery(name = "ItemLivro.Emprestimo.count", query = "SELECT COUNT(o.emprestimos) FROM ItemLivro o WHERE o.id=:id"),
		@NamedQuery(name = "ItemLivro.Emprestimo.findAll", query = "SELECT o.emprestimos FROM ItemLivro o WHERE o.id=:id") 
		})
public class ItemLivro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String codigo;
	@ManyToOne
	private Livro livro = new Livro();
	@ManyToMany
	private List<Reserva> reservas = new ArrayList<Reserva>();
	@ManyToMany
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

	public ItemLivro() {
		super();
	}

	public ItemLivro(String codigo, Livro livro) {
		super();
		this.codigo = codigo;
		this.livro = livro;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	@Override
	public String toString() {
		return "ItemLivro [id=" + this.id + ", livro=" + this.livro + "]";
	}

}
