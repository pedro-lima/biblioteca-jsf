package br.com.biblioteca.controller.pessoa;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import br.com.biblioteca.model.endereco.Cidade;
import br.com.biblioteca.model.endereco.Estado;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.model.pessoa.Locador;
import br.com.biblioteca.persistence.pessoa.PessoaPersistence;

@Named("locadorBean")
@SessionScoped
public class LocadorController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private PessoaPersistence pessoaDao;
	@EJB
	private EnderecoBean enderecoBean;
	private Locador locadorSelecionado = new Locador();
	private Locador locadorDetalhe = new Locador();

	public LocadorController() {
		super();
	}
	
	public void criarListagens() {
		this.getEnderecoBean().construirListagemEndereco();
	}

	public void prepararNovoLocador() {
		this.locadorSelecionado = new Locador();
	}

	public void salvarLocador() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Cidade cidadeNova = this.enderecoBean.getCidade();
			if (this.locadorSelecionado.getId() == null) {
				this.locadorSelecionado.getEndereco().setCidade(cidadeNova);
				cidadeNova.getEnderecos().add(
						this.locadorSelecionado.getEndereco());
				this.pessoaDao.create(this.locadorSelecionado);
			} else {
				Cidade cidadeAntiga = this.enderecoBean.getCidade();
				cidadeAntiga.getEnderecos().remove(
						this.locadorSelecionado.getEndereco());
				this.locadorSelecionado.getEndereco().setCidade(cidadeNova);
				cidadeNova.getEnderecos().add(
						this.locadorSelecionado.getEndereco());
				this.pessoaDao.update(this.locadorSelecionado);
			}
			this.prepararNovoLocador();
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

	public Locador getLocadorSelecionado() {
		return locadorSelecionado;
	}

	public EnderecoBean getEnderecoBean() {
		return enderecoBean;
	}

	public void setLocadorSelecionado(Locador locadorSelecionado) {
		this.locadorSelecionado = locadorSelecionado;
	}

	public List<Locador> getLocadoresCadastrados() {
		return this.pessoaDao.findAllLocadores();
	}

	public void selecionarLocador(long id) {
		Locador locador = pessoaDao.findLocadorEnderecoCompleto(id);
		Cidade cidade = locador.getEndereco().getCidade();
		Estado estado = cidade.getEstado();
		Pais pais = estado.getPais();
		this.setLocadorSelecionado(locador);
		this.getEnderecoBean().construirListagemEndereco(pais.getId(),
				estado.getId(), cidade.getId());

	}

	public Locador getLocadorDetalhe() {
		return locadorDetalhe;
	}

	public void setLocadorDetalhe(Locador locadorDetalhe) {
		this.locadorDetalhe = locadorDetalhe;
	}

	public void selecionarDetalhe(long id) {
		Locador locador = pessoaDao.findLocadorEnderecoCompleto(id);
		this.setLocadorDetalhe(locador);
	}

}
