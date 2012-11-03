package br.com.biblioteca.model.endereco;

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
		@NamedQuery(name = "Pais.findAll", query = "SELECT o FROM Pais o"),
		@NamedQuery(name = "Pais.count", query = "SELECT COUNT(o) FROM Pais o"),
		@NamedQuery(name = "Pais.findById", query = "SELECT o FROM Pais o WHERE o.id=:id"),
		@NamedQuery(name = "Pais.Estado.count", query = "SELECT COUNT(o.estados) FROM Pais o WHERE o.id=:id"),
		@NamedQuery(name = "Pais.Estado.findAll", query = "SELECT o.estados FROM Pais o WHERE o.id=:id"),
		@NamedQuery(name = "Pais.Join.Estado", query = "SELECT o from Pais o LEFT JOIN FETCH o.estados WHERE o.id=:id")})
public class Pais implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@OneToMany(mappedBy = "pais", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
	private List<Estado> estados = new ArrayList<Estado>();

	public Pais() {
		super();
	}

	public Pais(String nome) {
		super();
		this.nome = nome;
	}

	public Pais(String nome, List<Estado> estados) {
		super();
		this.nome = nome;
		this.estados = estados;
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

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	@Override
	public String toString() {
		return "Pais [id=" + id + ", nome=" + nome + "]";
	}

}
