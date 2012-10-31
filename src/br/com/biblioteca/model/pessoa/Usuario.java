package br.com.biblioteca.model.pessoa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@NamedQueries({
	@NamedQuery(
			name="Usuario.findAll",
			query="SELECT o FROM Usuario o"),
	@NamedQuery(
			name="Usuario.count",
			query="SELECT COUNT(o) FROM Usuario o"),
	@NamedQuery(
			name="Usuario.findById",
			query="SELECT o FROM Usuario o WHERE o.id=:id")	
})
public class Usuario implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique=true)
	private String login;
	private String senha;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE,CascadeType.DETACH})
	private Pessoa pessoa;
	
	public Usuario() {
		super();
	}
	
	public Usuario(String login, String senha, Pessoa pessoa) {
		super();
		this.login = login;
		this.senha = senha;
		this.pessoa = pessoa;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", pessoa=" + pessoa
				+ "]";
	}	
	
}
