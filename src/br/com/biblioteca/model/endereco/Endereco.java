package br.com.biblioteca.model.endereco;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.biblioteca.model.pessoa.Pessoa;

@Entity
@NamedQueries({
	@NamedQuery(
			name="Endereco.findAll",
			query="SELECT o FROM Endereco o"),
	@NamedQuery(
			name="Endereco.count",
			query="SELECT COUNT(o) FROM Endereco o"),
	@NamedQuery(
			name="Endereco.findById",
			query="SELECT o FROM Endereco o WHERE o.id=:id")
})
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	private String rua;
	private String bairro;
	private String cep;
	private int numero;
	private String complemento;
	@ManyToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE,CascadeType.DETACH})
	private Cidade cidade;
	@OneToOne(mappedBy="endereco")
	private Pessoa pessoa;

	public Endereco() {
		super();
	}

	public Endereco(String rua, String bairro, String cep, int numero,
			String complemento, Cidade cidade) {
		super();
		this.rua = rua;
		this.bairro = bairro;
		this.cep = cep;
		this.numero = numero;
		this.complemento = complemento;
		this.cidade = cidade;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public String toString() {
		return "Endereco [rua=" + this.rua + ", bairro=" + this.bairro
				+ ", cep=" + this.cep + ", numero=" + this.numero + ", complemento="
				+ this.complemento + ", cidade=" + this.cidade + "]";
	}

}
