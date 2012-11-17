package br.com.biblioteca.model.emprestimo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
		@NamedQuery(name = "Emprestimo.findAll", query = "SELECT o FROM Emprestimo o"),
		@NamedQuery(name = "Emprestimo.count", query = "SELECT COUNT(o) FROM Emprestimo o"),
		@NamedQuery(name = "Emprestimo.findById", query = "SELECT o FROM Emprestimo o WHERE o.id=:id"),
		@NamedQuery(name = "Emprestimo.Livro.count", query = "SELECT COUNT(o.livros) FROM Emprestimo o WHERE o.id=:id"),
		@NamedQuery(name = "Emprestimo.Livro.findAll", query = "SELECT o.livros FROM Emprestimo o WHERE o.id=:id"),
		@NamedQuery(name = "Emprestimo.findAll.Ativo", query = "SELECT o FROM Emprestimo o "
				+ " WHERE o.locador.id=:id AND o.dataDevolucao IS NULL"),
		@NamedQuery(name = "Emprestimo.Join.Livro", query = "SELECT o from Emprestimo o LEFT JOIN FETCH o.livros WHERE o.id=:id"),
		@NamedQuery(name = "Emprestimo.Join.Livro.Locador", query = "SELECT o from Emprestimo o "
				+ " LEFT JOIN FETCH o.locador "
				+ " LEFT JOIN FETCH o.livros "
				+ " WHERE o.id=:id") })
public class Emprestimo implements Serializable {
	public static final int diasLimite = 12;
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
	private Locador locador;
	@Temporal(TemporalType.DATE)
	private Date dataEmprestimo;
	@Temporal(TemporalType.DATE)
	private Date dataDevolucaoEsperada;
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date dataDevolucao;
	@ManyToMany
	private List<ItemLivro> livros = new ArrayList<ItemLivro>();

	public Emprestimo() {
		super();
	}

	public Emprestimo(Locador locador, Date dataEmprestimo,
			Date dataDevolucaoEsperada, List<ItemLivro> livros) {
		super();
		this.locador = locador;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucaoEsperada = dataDevolucaoEsperada;
		this.livros = livros;
	}

	public Emprestimo(Locador locador, List<ItemLivro> livros, int dias) {
		this(locador, new Date(System.currentTimeMillis()), Emprestimo
				.calcularDataDevolucao(new Date(System.currentTimeMillis()),
						dias), livros);
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

	public Date getDataEmprestimo() {
		return dataEmprestimo;
	}

	public void setDataEmprestimo(Date dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	public Date getDataDevolucaoEsperada() {
		return dataDevolucaoEsperada;
	}

	public void setDataDevolucaoEsperada(Date dataDevolucaoEsperada) {
		this.dataDevolucaoEsperada = dataDevolucaoEsperada;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public List<ItemLivro> getLivros() {
		return livros;
	}

	public void setLivros(List<ItemLivro> livros) {
		this.livros = livros;
	}

	public static Date calcularDataDevolucao(Date dataEmprestimo, int dias) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(GregorianCalendar.DAY_OF_MONTH,
				gc.get(GregorianCalendar.DAY_OF_MONTH) + dias);
		if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			gc.set(GregorianCalendar.DAY_OF_MONTH,
					gc.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		}
		return gc.getTime();
	}

	public static Date calcularDataOperacaoEmprestimo() {
		return new Date(System.currentTimeMillis());
	}

	public String getSituacaoEmprestimo() {
		if (this.dataDevolucao == null)
			return "Aberto";
		else
			return "Finalizado";
	}

	public boolean getDesabilitarFecharEmprestimo() {
		return this.dataDevolucao != null;
	}

}
