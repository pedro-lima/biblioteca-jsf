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
	@NamedQuery(
			name="Assunto.findAll",
			query="SELECT o FROM Assunto o"),
	@NamedQuery(
			name="Assunto.count",
			query="SELECT COUNT(o) FROM Assunto o"),
	@NamedQuery(
			name="Assunto.findById",
			query="SELECT o FROM Assunto o WHERE o.id=:id"),
	@NamedQuery(
			name="Assunto.Livro.count",
			query="SELECT COUNT(o.livros) FROM Assunto o WHERE o.id=:id"),
	@NamedQuery(
			name="Assunto.Livro.findAll",
			query="SELECT o.livros FROM Assunto o WHERE o.id=:id")
})
public class Assunto implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@OneToMany(mappedBy="assunto",cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE})
	private  List<Livro> livros = new ArrayList<Livro>();
		
	public Assunto() {
		super();
	}

	public Assunto(String nome) {
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
		return "Assunto [id=" + id + ", nome=" + nome + "]";
	}	
	
}
