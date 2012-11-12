package br.com.biblioteca.model.livro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
		@NamedQuery(name = "ItemLivro.Emprestimo.findAll", query = "SELECT o.emprestimos FROM ItemLivro o WHERE o.id=:id"),
		@NamedQuery(name = "ItemLivro.Disponiveis", query = "SELECT DISTINCT o FROM ItemLivro o LEFT JOIN FETCH o.emprestimos e "
				+ "WHERE (o.emprestimos IS EMPTY) OR (o.emprestimos IS NOT EMPTY AND e.dataDevolucao IS NOT NULL)"),
		@NamedQuery(name = "ItemLivro.Join.Emprestimo", query = "SELECT o from ItemLivro o LEFT JOIN FETCH o.emprestimos WHERE o.id=:id") })
public class ItemLivro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String codigo;
	private int ano;
	private int edicao;
	private String cutter;
	@ManyToOne
	private Livro livro = new Livro();
	@ManyToMany
	private List<Reserva> reservas = new ArrayList<Reserva>();
	@ManyToMany(mappedBy = "livros",cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE },fetch=FetchType.EAGER)
	private List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

	public ItemLivro() {
		super();
	}

	public ItemLivro(String codigo, int ano, int edicao, String cutter,
			Livro livro) {
		super();
		this.codigo = codigo;
		this.ano = ano;
		this.edicao = edicao;
		this.cutter = cutter;
		this.livro = livro;
	}

	public ItemLivro(String codigo, int ano, int edicao, String cutter) {
		super();
		this.codigo = codigo;
		this.ano = ano;
		this.edicao = edicao;
		this.cutter = cutter;
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

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getEdicao() {
		return edicao;
	}

	public void setEdicao(int edicao) {
		this.edicao = edicao;
	}

	public String getCutter() {
		return cutter;
	}

	public void setCutter(String cutter) {
		this.cutter = cutter;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemLivro other = (ItemLivro) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
