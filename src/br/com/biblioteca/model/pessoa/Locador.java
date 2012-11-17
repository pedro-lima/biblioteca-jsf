package br.com.biblioteca.model.pessoa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import br.com.biblioteca.model.emprestimo.Emprestimo;
import br.com.biblioteca.model.endereco.Endereco;
import br.com.biblioteca.model.reserva.Reserva;

@Entity
@NamedQueries({
		@NamedQuery(name = "Locador.findAll", query = "SELECT o FROM Locador o"),
		@NamedQuery(name = "Locador.count", query = "SELECT COUNT(o) FROM Locador o"),
		@NamedQuery(name = "Locador.findById", query = "SELECT o FROM Locador o WHERE o.id=:id"),
		@NamedQuery(name = "Locador.Emprestimo.count", query = "SELECT COUNT(o.emprestimos) FROM Locador o WHERE o.id=:id"),
		@NamedQuery(name = "Locador.Emprestimo.findAll", query = "SELECT o.emprestimos FROM Locador o WHERE o.id=:id"),
		@NamedQuery(name = "Locador.Join.EnderecoCompleto", query = "SELECT o from Locador o "
				+ "LEFT JOIN FETCH o.endereco en "
				+ "LEFT JOIN FETCH en.cidade ci "
				+ "LEFT JOIN FETCH ci.estado es "
				+ "LEFT JOIN FETCH es.pais pa " + "WHERE o.id=:id"),
		@NamedQuery(name = "Locador.Join.Emprestimo", query = "SELECT o FROM Locador o LEFT "
				+ "JOIN FETCH o.emprestimos e WHERE o.id=:id"), })
public class Locador extends Pessoa {
	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy = "locador", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE })
	private List<Reserva> reservas = new ArrayList<Reserva>();
	@OneToMany(mappedBy = "locador", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE })
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

	public Locador() {
		super();
	}

	public Locador(String nome, String rg, String telefone, Endereco endereco) {
		super(nome, rg, telefone, endereco);
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

}
