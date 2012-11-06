package br.com.biblioteca.controller.pessoa;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import br.com.biblioteca.model.pessoa.Funcionario;
import br.com.biblioteca.persistence.pessoa.PessoaPersistence;

public class FuncionarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private PessoaPersistence pessoaDao;
	private Funcionario funcionarioSelecionado = new Funcionario();

	public FuncionarioController() {
		super();
	}

	public PessoaPersistence getPessoaDao() {
		return pessoaDao;
	}

	public void prepararNovoFuncionario() {
		this.funcionarioSelecionado = null;
	}

	public void salvarLocador() {
		try {
			if (this.funcionarioSelecionado.getId() == null) {
				this.pessoaDao.create(this.funcionarioSelecionado);
			} else {
				this.pessoaDao.update(this.funcionarioSelecionado);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.prepararNovoFuncionario();
		}
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public List<Funcionario> getLocadoresCadastrados() {
		return this.pessoaDao.findAllFuncionarios();
	}

	public void selecionarFuncionario(Funcionario funcionario) {
		this.setFuncionarioSelecionado(funcionario);
	}

}
