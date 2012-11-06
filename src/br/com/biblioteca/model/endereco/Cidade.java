package br.com.biblioteca.model.endereco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Cidade.findAll", query = "SELECT o FROM Cidade o"),
		@NamedQuery(name = "Cidade.count", query = "SELECT COUNT(o) FROM Cidade o"),
		@NamedQuery(name = "Cidade.findById", query = "SELECT o FROM Cidade o WHERE o.id=:id"),
		@NamedQuery(name = "Cidade.Endereco.count", query = "SELECT COUNT(o.enderecos) FROM Cidade o WHERE o.id=:id"),
		@NamedQuery(name = "Cidade.Endereco.findAll", query = "SELECT o.enderecos FROM Cidade o WHERE o.id=:id"),
		@NamedQuery(name = "Cidade.Join.Enderecos", query = "SELECT o from Cidade o LEFT JOIN FETCH o.enderecos WHERE o.id=:id") })
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	@ManyToOne
	private Estado estado;
	@OneToMany(mappedBy = "cidade", cascade = { CascadeType.PERSIST,
			CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH })
	private List<Endereco> enderecos = new ArrayList<Endereco>();

	public Cidade() {
		super();
	}

	public Cidade(String nome, Estado estado, List<Endereco> enderecos) {
		super();
		this.nome = nome;
		this.estado = estado;
		this.enderecos = enderecos;
	}

	public Cidade(String nome, Estado estado) {
		super();
		this.nome = nome;
		this.estado = estado;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@Override
	public String toString() {
		return "Cidade [id=" + id + ", nome=" + nome + ", estado=" + estado
				+ "]";
	}

}
