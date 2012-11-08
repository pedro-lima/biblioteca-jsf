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
		@NamedQuery(name = "Autor.findAll", query = "SELECT o FROM Autor o"),
		@NamedQuery(name = "Autor.count", query = "SELECT COUNT(o) FROM Autor o"),
		@NamedQuery(name = "Autor.findById", query = "SELECT o FROM Autor o WHERE o.id=:id"),
		@NamedQuery(name = "Autor.Livro.count", query = "SELECT COUNT(o.livros) FROM Autor o WHERE o.id=:id"),
		@NamedQuery(name = "Autor.Livro.findAll", query = "SELECT o.livros FROM Autor o WHERE o.id=:id"),
		@NamedQuery(name = "Autor.Join.Livro", query = "SELECT o FROM Autor o "
				+ "LEFT JOIN FETCH o.livros WHERE o.id=:id") })
public class Autor implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@OneToMany(mappedBy = "autor", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE })
	private List<Livro> livros = new ArrayList<Livro>();

	public Autor() {
		super();
	}

	public Autor(String nome) {
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
		return "Autor [id=" + id + ", nome=" + nome + "]";
	}

}
