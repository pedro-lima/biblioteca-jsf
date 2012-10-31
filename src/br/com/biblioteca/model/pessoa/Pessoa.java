package br.com.biblioteca.model.pessoa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.biblioteca.model.endereco.Endereco;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@NamedQueries({
	@NamedQuery(
			name="Pessoa.findAll",
			query="SELECT o FROM Pessoa o"),
	@NamedQuery(
			name="Pessoa.count",
			query="SELECT COUNT(o) FROM Pessoa o"),
	@NamedQuery(
			name="Pessoa.findById",
			query="SELECT o FROM Pessoa o WHERE o.id=:id")
})
public abstract class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String nome;
	private String rg;
	private String telefone;
	@OneToOne
	private Endereco endereco;

	public Pessoa() {
		super();
	}

	public Pessoa(String nome, String rg, String telefone, Endereco endereco) {
		super();
		this.nome = nome;
		this.rg = rg;
		this.telefone = telefone;
		this.endereco = endereco;
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

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + "]";
	}

}
