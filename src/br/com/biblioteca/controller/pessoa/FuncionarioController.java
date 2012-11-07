package br.com.biblioteca.controller.pessoa;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import br.com.biblioteca.model.endereco.Cidade;
import br.com.biblioteca.model.endereco.Estado;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.model.pessoa.Funcionario;
import br.com.biblioteca.persistence.pessoa.PessoaPersistence;

@Named("funcionarioBean")
@SessionScoped
public class FuncionarioController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private PessoaPersistence pessoaDao;
	private Funcionario funcionarioSelecionado = new Funcionario();
	@EJB
	private EnderecoBean enderecoBean;

	public FuncionarioController() {
		super();
	}

	@PostConstruct
	private void criarListagens() {
		this.getEnderecoBean().construirListagemEndereco();
	}

	public void prepararNovoFuncionario() {
		this.funcionarioSelecionado = new Funcionario();
	}

	public void salvarFuncionario() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Cidade cidadeNova = this.enderecoBean.getCidade();
			if (this.funcionarioSelecionado.getId() == null) {
				this.funcionarioSelecionado.getEndereco().setCidade(cidadeNova);
				cidadeNova.getEnderecos().add(
						this.funcionarioSelecionado.getEndereco());
				this.funcionarioSelecionado.getUsuario().setFuncionario(
						funcionarioSelecionado);
				this.pessoaDao.create(this.funcionarioSelecionado);
			} else {
				Cidade cidadeAntiga = this.enderecoBean.getCidade();
				cidadeAntiga.getEnderecos().remove(
						this.funcionarioSelecionado.getEndereco());
				this.funcionarioSelecionado.getEndereco().setCidade(cidadeNova);
				cidadeNova.getEnderecos().add(
						this.funcionarioSelecionado.getEndereco());
				this.pessoaDao.update(this.funcionarioSelecionado);
			}
			this.prepararNovoFuncionario();
			FacesMessage msg = new FacesMessage("SUCESSO",
					"Operação realizada com sucesso.");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			fc.addMessage(null, msg);
			fc.renderResponse();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage("ERRO:",
					"Erro ao realizar a operação.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, msg);
		}
	}

	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public Funcionario getFuncionarioSelecionado() {
		return funcionarioSelecionado;
	}

	public void setFuncionarioSelecionado(Funcionario funcionarioSelecionado) {
		this.funcionarioSelecionado = funcionarioSelecionado;
	}

	public List<Funcionario> getFuncionariosCadastrados() {
		return this.pessoaDao.findAllFuncionarios();
	}

	public void selecionarFuncionario(long id) {
		Funcionario funcionario = pessoaDao.findFuncionarioEnderecoCompleto(id);
		Cidade cidade = funcionario.getEndereco().getCidade();
		Estado estado = cidade.getEstado();
		Pais pais = estado.getPais();
		this.setFuncionarioSelecionado(funcionario);
		this.getEnderecoBean().construirListagemEndereco(pais.getId(),
				estado.getId(), cidade.getId());
	}

	private Funcionario funcionarioDetalhe;

	public Funcionario getFuncionarioDetalhe() {
		return funcionarioDetalhe;
	}

	public void setFuncionarioDetalhe(Funcionario funcionarioDetalhe) {
		this.funcionarioDetalhe = funcionarioDetalhe;
	}

	public void selecionarDetalhe(long id) {
		Funcionario funcionario = pessoaDao.findFuncionarioEnderecoCompleto(id);
		this.setFuncionarioDetalhe(funcionario);
	}

	public void validaLogin(ComponentSystemEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		UIInput login = (UIInput) components.findComponent("login");

		Funcionario funcionario = this.pessoaDao.findByLogin(login.getValue()
				.toString());
		if (funcionario != null) {
			if (!funcionario.getId()
					.equals(this.funcionarioSelecionado.getId())) {
				FacesMessage msg = new FacesMessage("ERRO",
						"Login ja cadastrado.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(login.getClientId(), msg);
				fc.renderResponse();
			}
		}
	}

	public void validaMatricula(ComponentSystemEvent event) {
		FacesContext fc = FacesContext.getCurrentInstance();
		UIComponent components = event.getComponent();

		UIInput matricula = (UIInput) components.findComponent("matricula");
		Funcionario funcionario = this.pessoaDao.findByMatricula(matricula
				.getValue().toString());
		if (funcionario != null) {
			if (!funcionario.getId()
					.equals(this.funcionarioSelecionado.getId())) {
				FacesMessage msg = new FacesMessage("ERRO",
						"Matricula ja cadastrada.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(matricula.getClientId(), msg);
				fc.renderResponse();
			}
		}
	}

}
