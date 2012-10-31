package br.com.biblioteca.model.pessoa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import br.com.biblioteca.model.endereco.Endereco;

@Entity
@NamedQueries({
	@NamedQuery(name = "Funcionario.findAll", query = "SELECT o FROM Funcionario o"),
	@NamedQuery(name = "Funcionario.count", query = "SELECT COUNT(o) FROM Funcionario o"),
	@NamedQuery(name = "Funcionario.findById", query = "SELECT o FROM Funcionario o WHERE o.id=:id") })
public class Funcionario extends Pessoa {
	private static final long serialVersionUID = 1L;
	@OneToOne(mappedBy="funcionario",cascade={CascadeType.PERSIST,CascadeType.REFRESH,
			CascadeType.MERGE,CascadeType.DETACH})
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

	@Override
	public String toString() {
		return "Funcionario [usuario=" + usuario + ", matricula=" + matricula
				+ "]";
	}	
}
