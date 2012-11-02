package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
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
	public static final String indexURL = "/endereco/cidade/index.xhtml";
	private Cidade cidadeSelecionada = new Cidade();
	private Pais paisSelecionado = new Pais();
	private Estado estadoSelecionado = new Estado();
	private List<Estado> listaEsdadosFiltradas;
	
	public CidadeController() {
		super();
	}
		
	public void prepararNovaCidade() {
		this.cidadeSelecionada = new Cidade();
	} 

	public void adicionarCidade() {
		cidadeDao.create(cidadeSelecionada);
	}

	public void alterarCidade() {
		cidadeDao.update(cidadeSelecionada);		
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

	public Pais getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(Pais paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public Estado getEstadoSelecionado() {
		return estadoSelecionado;
	}

	public void setEstadoSelecionado(Estado estadoSelecionado) {
		this.estadoSelecionado = estadoSelecionado;
	}
	
	public List<Estado> getEstadosByPais() {
		return estadoDao.findAllByPais(paisSelecionado.getId());
	}
	
	public List<Pais> getAllPais() {
		return this.paisDao.findAll();
	}
	
	public List<Estado> getListaEsdadosFiltradas() {
		return listaEsdadosFiltradas;
	}

	public void setListaEsdadosFiltradas(List<Estado> listaEsdadosFiltradas) {
		this.listaEsdadosFiltradas = listaEsdadosFiltradas;
	}

	public void valueChangePais(ValueChangeEvent e){
		this.setListaEsdadosFiltradas(this.getEstadosByPais());
	}	
		
	public void prepararAlterarCidade(Cidade cidade) {
		this.setCidadeSelecionada(cidade);
		this.setPaisSelecionado(cidade.getEstado().getPais());
		this.setListaEsdadosFiltradas(this.getEstadosByPais());
		this.setEstadoSelecionado(cidade.getEstado());		
		System.out.println(cidade);
		System.out.println(cidade.getEstado());
		System.out.println(cidade.getEstado().getPais());
		System.out.println(this.paisSelecionado);
	}

}
