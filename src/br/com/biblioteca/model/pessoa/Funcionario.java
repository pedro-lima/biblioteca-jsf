package br.com.biblioteca.model.pessoa;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import br.com.biblioteca.model.endereco.Endereco;

public class Funcionario extends Pessoa {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@OneToOne
	private Usuario usuario;
	@Column(unique=true)
	private String matricula;
	
	public Funcionario() {
		super();
	}
	
	public Funcionario(String nome, String rg, String telefone,
			Endereco endereco, Usuario usuario, String matricula) {
		super(nome, rg, telefone, endereco);
		this.usuario = usuario;
		this.matricula = matricula;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}		

}
