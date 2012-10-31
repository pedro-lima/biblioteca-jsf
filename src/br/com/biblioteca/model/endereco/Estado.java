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
	@NamedQuery(
			name="Estado.findAll",
			query="SELECT o FROM Estado o"),
	@NamedQuery(
			name="Estado.count",
			query="SELECT COUNT(o) FROM Estado o"),
	@NamedQuery(
			name="Estado.findById",
			query="SELECT o FROM Estado o WHERE o.id=:id"),
	@NamedQuery(
			name="Estado.Cidade.count",
			query="SELECT COUNT(o.cidades) FROM Estado o WHERE o.id=:id"),
	@NamedQuery(
			name="Estado.Cidade.findAll",
			query="SELECT o.cidades FROM Estado o WHERE o.id=:id"),
})
public class Estado implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String sigla;
	@OneToMany(mappedBy="estado",cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE,CascadeType.DETACH})
	private List<Cidade> cidades = new ArrayList<Cidade>();
	@ManyToOne
	private Pais pais;

	public Estado() {
		super();
	}

	public Estado(String nome, String sigla, List<Cidade> cidades,
			Pais pais) {
		super();
		this.nome = nome;
		this.sigla = sigla;
		this.cidades = cidades;
		this.pais = pais;
	}

	public Estado(String nome, String sigla, Pais pais) {
		super();
		this.nome = nome;
		this.sigla = sigla;
		this.pais = pais;
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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@Override
	public String toString() {
		return "Estado [id=" + id + ", nome=" + nome + ", sigla=" + sigla
				+ ", pais=" + pais + "]";
	}

}
