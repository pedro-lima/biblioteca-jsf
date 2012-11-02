package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
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
	public static final String indexURL = "/endereco/cidade/index.xhtml";
	private Cidade cidadeSelecionada = new Cidade();
	private long paisSelecionado;
	private long estadoSelecionado;
	private List<SelectItem> listaEsdados = new ArrayList<SelectItem>();
	private List<SelectItem> listaPais;
	
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
	
	public long getPaisSelecionado() {
		return paisSelecionado;
	}

	public void setPaisSelecionado(long paisSelecionado) {
		this.paisSelecionado = paisSelecionado;
	}

	public List<SelectItem> getListaPais() {
		if(this.listaPais == null){
			this.listaPais = new ArrayList<SelectItem>();
			for(Pais pais : this.paisDao.findAll()) {
				this.listaPais.add(new SelectItem(pais.getId(), pais.getNome()));	
			}
		}
		return listaPais;
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

	public void valueChangePais(ValueChangeEvent e){		
		criarCidadesListagem();		
	}

	private void criarCidadesListagem() {
		this.listaEsdados.clear();
		for(Estado es:  this.getEstadosByPais()){
			this.listaEsdados.add(new SelectItem(es.getId(), es.getNome()));			
		}
	}	
		
	public void prepararAlterarCidade(Cidade cidade) {
		this.setCidadeSelecionada(cidade);
		this.setPaisSelecionado(cidade.getEstado().getPais().getId());
		this.criarCidadesListagem();
		this.setEstadoSelecionado(cidade.getEstado().getId());
		//this.setPaisSelecionado(cidade.getEstado().getPais());
		//this.setListaEsdadosFiltrados(this.getEstadosByPais());
		//this.setEstadoSelecionado(cidade.getEstado());		
		//System.out.println(cidade);
		//System.out.println(cidade.getEstado());
		//System.out.println(cidade.getEstado().getPais());
		//System.out.println(this.paisSelecionado);
	}
	
}
