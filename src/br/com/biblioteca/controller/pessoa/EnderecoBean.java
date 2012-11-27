package br.com.biblioteca.controller.pessoa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import br.com.biblioteca.model.endereco.Cidade;
import br.com.biblioteca.model.endereco.Estado;
import br.com.biblioteca.model.endereco.Pais;
import br.com.biblioteca.persistence.endereco.CidadePersistence;
import br.com.biblioteca.persistence.endereco.EstadoPersistence;
import br.com.biblioteca.persistence.endereco.PaisPersistence;

@Stateless
public class EnderecoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private CidadePersistence cidadeDao;
	@EJB
	private EstadoPersistence estadoDao;
	@EJB
	private PaisPersistence paisDao;
	private long paisSelecionado;
	private long estadoSelecionado;
	private long cidadeSelecionada;
	private List<SelectItem> listaPais = new ArrayList<SelectItem>();;
	private List<SelectItem> listaEsdados = new ArrayList<SelectItem>();
	private List<SelectItem> listaCidades = new ArrayList<SelectItem>();

	public EnderecoBean() {
		super();
	}

	public void construirListagemEndereco() {
		this.listaPais.clear();
		this.listaEsdados.clear();
		this.listaCidades.clear();
		this.criarPaisListagem();
		this.criarEstadosListagem();
		this.criarCidadesListagem();
	}

	public void construirListagemEndereco(long paisSelecionado,
			long estadoSelecionado, long cidadeSelecionada) {
		this.listaPais.clear();
		this.listaEsdados.clear();
		this.listaCidades.clear();
		this.criarPaisListagem();
		this.paisSelecionado = paisSelecionado;
		this.criarEstadosListagem();
		this.estadoSelecionado = estadoSelecionado;
		this.criarCidadesListagem();
		this.cidadeSelecionada = cidadeSelecionada;
	}

	private void criarPaisListagem() {
		for (Pais pais : this.paisDao.findAll()) {
			this.listaPais.add(new SelectItem(pais.getId(), pais.getNome()));
		}
		if (this.listaPais.size() > 0) {
			this.paisSelecionado = (Long) this.listaPais.get(0).getValue();
		} else {
			this.paisSelecionado = 0l;
		}
	}

	private void criarEstadosListagem() {
		for (Estado es : this.getEstadosByPais()) {
			this.listaEsdados.add(new SelectItem(es.getId(), es.getNome()));
		}
		if (this.listaEsdados.size() > 0) {
			this.estadoSelecionado = (Long) this.listaEsdados.get(0).getValue();
		} else {
			this.estadoSelecionado = 0l;
		}
	}

	private void criarCidadesListagem() {
		for (Cidade ci : this.getCidadesByEstado()) {
			this.listaCidades.add(new SelectItem(ci.getId(), ci.getNome()));
		}
	}

	private List<Cidade> getCidadesByEstado() {
		try {
			return cidadeDao.findAllByEstado(this.estadoSelecionado);
		} catch (Exception e) {
			return null;
		}

	}

	private List<Estado> getEstadosByPais() {
		try {
			return estadoDao.findAllByPais(new Long(this.paisSelecionado));
		} catch (Exception e) {
			return null;
		}

	}

	public long getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(long paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public long getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(long estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}

	public long getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(long cidadeSelecionada) {
		this.cidadeSelecionada = cidadeSelecionada;
	}

	public List<SelectItem> getListaPais() {
		return listaPais;
	}

	public void setListaPais(List<SelectItem> listaPais) {
		this.listaPais = listaPais;
	}

	public List<SelectItem> getListaEsdados() {
		return listaEsdados;
	}

	public void setListaEsdados(List<SelectItem> listaEsdados) {
		this.listaEsdados = listaEsdados;
	}

	public List<SelectItem> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<SelectItem> listaCidades) {
		this.listaCidades = listaCidades;
	}

	public void valueChangePais(AjaxBehaviorEvent e) {
		this.listaEsdados.clear();
		this.criarEstadosListagem();
		this.listaCidades.clear();
		this.criarCidadesListagem();
	}

	public void valueChangeEstado(AjaxBehaviorEvent e) {
		this.listaCidades.clear();
		criarCidadesListagem();
	}

	public Cidade getCidade() {
		return this.cidadeDao.findCidadeGetEnderecos(this.cidadeSelecionada);
	}

}
