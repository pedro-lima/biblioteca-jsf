package br.com.biblioteca.model.pessoa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import br.com.biblioteca.model.endereco.Endereco;

@Entity
public class Funcionario extends Pessoa {
	private static final long serialVersionUID = 1L;
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
