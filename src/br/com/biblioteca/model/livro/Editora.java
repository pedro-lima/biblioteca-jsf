package br.com.biblioteca.model.livro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Editora.findAll", query = "SELECT o FROM Editora o"),
		@NamedQuery(name = "Editora.count", query = "SELECT COUNT(o) FROM Editora o"),
		@NamedQuery(name = "Editora.findById", query = "SELECT o FROM Editora o WHERE o.id=:id"),
		@NamedQuery(name = "Editora.Livro.count", query = "SELECT COUNT(o.livros) FROM Editora o WHERE o.id=:id"),
		@NamedQuery(name = "Editora.Livro.findAll", query = "SELECT o.livros FROM Editora o WHERE o.id=:id"),
		@NamedQuery(name = "Editora.Join.Livro", query = "SELECT o FROM Editora o "
				+ "LEFT JOIN FETCH o.livros WHERE o.id=:id") })
public class Editora implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@OneToMany(mappedBy = "editora", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE })
	private List<Livro> livros = new ArrayList<Livro>();

	public Editora() {
		super();
	}

	public Editora(String nome) {
		super();
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	@Override
	public String toString() {
		return "Editora [id=" + id + ", nome=" + nome + "]";
	}

}
