package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.persistence.endereco.PaisPersistence;

@Named("paisBean")
@SessionScoped
public class PaisController implements Serializable {
	@EJB
	private PaisPersistence paisDao;
	private static final long serialVersionUID = 1L;
	public static final String indexURL = "/biblioteca-jsf/endereco/pais/index.xhtml";
	private Pais paisSelecionado = new Pais();

	public PaisController() {
		super();
	}

	public void prepararNovoPais() {
		this.paisSelecionado = new Pais();
	}

	public void salvarPais() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (this.paisSelecionado.getId() == null) {
				this.paisDao.create(this.paisSelecionado);
			} else {
				this.paisDao.update(this.paisSelecionado);
			}
			this.prepararNovoPais();
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
			fc.renderResponse();
		}
	}

	public List<Pais> getPaisesCadastrados() {
		return this.paisDao.findAll();
	}

	public Pais getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(Pais paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public void prepararAlterarPais(Pais pais) {
		this.setPaisSelecionado(pais);
	}

}
