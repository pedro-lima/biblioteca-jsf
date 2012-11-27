package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import br.com.biblioteca.model.endereco.Cidade;
import br.com.biblioteca.model.endereco.Estado;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.persistence.endereco.CidadePersistence;
import br.com.biblioteca.persistence.endereco.EstadoPersistence;
import br.com.biblioteca.persistence.endereco.PaisPersistence;

@Named("cidadeBean")
@SessionScoped
public class CidadeController implements Serializable {
	@EJB
	private CidadePersistence cidadeDao;
	@EJB
	private EstadoPersistence estadoDao;
	@EJB
	private PaisPersistence paisDao;
	private static final long serialVersionUID = 1L;
	public static final String indexURL = "/biblioteca-jsf/endereco/cidade/index.xhtml";
	private Cidade cidadeSelecionada = new Cidade();
	private long paisSelecionado;
	private long estadoSelecionado;
	private List<SelectItem> listaEsdados = new ArrayList<SelectItem>();
	private List<SelectItem> listaPais = new ArrayList<SelectItem>();

	public CidadeController() {
		super();
	}

	public void prepararNovaCidade() {
		this.cidadeSelecionada = new Cidade();
	}

	public void salvarCidade() {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Estado estadoNovo = estadoDao
					.findEstadoGetCidades(this.estadoSelecionado);
			if (this.getCidadeSelecionada().getId() == null) {
				cidadeSelecionada.setEstado(estadoNovo);
				estadoNovo.getCidades().add(cidadeSelecionada);
				cidadeDao.create(cidadeSelecionada);
			} else {
				Estado estadoAntigo = estadoDao
						.findEstadoGetCidades(cidadeSelecionada.getEstado()
								.getId());
				estadoAntigo.getCidades().remove(cidadeSelecionada);
				cidadeSelecionada.setEstado(estadoNovo);
				estadoNovo.getCidades().add(cidadeSelecionada);
				cidadeDao.update(cidadeSelecionada);
			}
			this.prepararNovaCidade();
			this.criarEstadosListagem();
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

	public List<Cidade> getcidadesCadastradas() {
		return this.cidadeDao.findAll();
	}

	public Cidade getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(Cidade cidade) {
		this.cidadeSelecionada = cidade;
	}

	public long getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(long paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public List<SelectItem> getListaPais() {
		return listaPais;
	}

	public void prepararListagemPais() {
		this.listaPais.clear();
		this.listaEsdados.clear();
		for (Pais pais : this.paisDao.findAll()) {
			this.listaPais.add(new SelectItem(pais.getId(), pais.getNome()));
		}
		this.criarEstadosListagem();
	}

	public long getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(long estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public List<Estado> getEstadosByPais() {
		return estadoDao.findAllByPais(new Long(this.paisSelecionado));
	}

	public List<SelectItem> getListaEsdados() {
		return listaEsdados;
	}

	public void setListaEsdados(List<SelectItem> listaEsdados) {
		this.listaEsdados = listaEsdados;
	}

	public void valueChangePais(AjaxBehaviorEvent e) {
		criarEstadosListagem();
	}

	private void criarEstadosListagem() {
		this.listaEsdados.clear();
		if (this.paisSelecionado == 0l && this.listaPais.size() > 0) {
			this.paisSelecionado = (Long) this.listaPais.get(0).getValue();
		}
		for (Estado es : this.getEstadosByPais()) {
			this.listaEsdados.add(new SelectItem(es.getId(), es.getNome()));
		}
	}

	public void prepararAlterarCidade(Cidade cidade) {
		this.setCidadeSelecionada(cidade);
		this.setPaisSelecionado(cidade.getEstado().getPais().getId());
		this.criarEstadosListagem();
		this.setEstadoSelecionado(cidade.getEstado().getId());
	}

}
