package br.com.biblioteca.model.pessoa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import br.com.biblioteca.model.emprestimo.Emprestimo;
import br.com.biblioteca.model.reserva.Reserva;

@Entity
@NamedQueries({
	@NamedQuery(
			name="Locador.findAll",
			query="SELECT o FROM Locador o"),
	@NamedQuery(
			name="Locador.count",
			query="SELECT COUNT(o) FROM Locador o"),
	@NamedQuery(
			name="Locador.findById",
			query="SELECT o FROM Locador o WHERE o.id=:id"),
	@NamedQuery(
			name="Locador.Emprestimo.count",
			query="SELECT COUNT(o.emprestimos) FROM Locador o WHERE o.id=:id"),
	@NamedQuery(
			name="Locador.Emprestimo.findAll",
			query="SELECT o.emprestimos FROM Locador o WHERE o.id=:id"),
})
public class Locador extends Pessoa{
	private static final long serialVersionUID = 1L;
	@OneToMany(mappedBy="locador",cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE,CascadeType.DETACH})
	private List<Reserva> reservas;
	@OneToMany(mappedBy="locador",cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE,CascadeType.DETACH})
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();
	
	public Locador() {
		super();
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
