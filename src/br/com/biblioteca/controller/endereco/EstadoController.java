package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import br.com.biblioteca.model.endereco.Estado;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.persistence.endereco.EstadoPersistence;
import br.com.biblioteca.persistence.endereco.PaisPersistence;

@Named("estadoBean")
@SessionScoped
public class EstadoController implements Serializable {
	@EJB
	private EstadoPersistence estadoDao;
	@EJB
	private PaisPersistence paisDao;
	private static final long serialVersionUID = 1L;
	public static final String indexURL = "/biblioteca-jsf/endereco/estado/index.xhtml";
	private Estado estadoSelecionado = new Estado();
	private long paisSelecionado;
	private List<SelectItem> listaPais;

	public EstadoController() {
		super();
	}

	public void prepararNovoEstado() {
		this.estadoSelecionado = new Estado();
	}

	public void salvarEstado() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Pais paisNovo = this.paisDao
					.findPaisGetEstados(this.paisSelecionado);
			if (this.getEstadoSelecionado().getId() == null) {
				this.estadoSelecionado.setPais(paisNovo);
				paisNovo.getEstados().add(this.estadoSelecionado);
				this.estadoDao.create(this.estadoSelecionado);
			} else {
				Pais paisAntigo = this.paisDao
						.findPaisGetEstados(this.estadoSelecionado.getPais()
								.getId());
				paisAntigo.getEstados().remove(this.getEstadoSelecionado());
				this.estadoSelecionado.setPais(paisNovo);
				paisNovo.getEstados().add(this.estadoSelecionado);
				this.estadoDao.update(this.estadoSelecionado);
			}
			this.prepararNovoEstado();
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

	public List<Estado> getEstadosCadastrados() {
		return this.estadoDao.findAll();
	}

	public long getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(long paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<SelectItem> getListaPais() {
		if (this.listaPais == null) {
			this.listaPais = new ArrayList<SelectItem>();
			for (Pais pais : this.paisDao.findAll()) {
				this.listaPais
						.add(new SelectItem(pais.getId(), pais.getNome()));
			}
		}
		return listaPais;
	}

	public void prepararAlterarEstado(Estado estado) {
		this.setEstadoSelecionado(estado);
		this.setPaisSelecionado(estado.getPais().getId());
	}

}
