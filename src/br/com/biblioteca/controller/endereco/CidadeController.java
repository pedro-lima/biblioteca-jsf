package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import br.com.biblioteca.model.endereco.Cidade;
import br.com.biblioteca.persistence.endereco.CidadePersistence;

@Named("cidadeController")
@SessionScoped
public class CidadeController implements Serializable {
	private static final long serialVersionUID = 1L;
	private Cidade cidadeSelecionada = new Cidade();
	private DataModel<Cidade> listaCidade;
	@EJB
	private CidadePersistence persistence;
	public static final String indexURL = "/endereco/cidade/index.xhtml";	

	public CidadeController() {
		super();
	}

	public Cidade getCidadeSelecionada() {
		return cidadeSelecionada;
	}

	public void setCidadeSelecionada(Cidade cidade) {
		this.cidadeSelecionada = cidade;
	}

	public DataModel<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(DataModel<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public void prepararNovaCidade() {
		this.cidadeSelecionada = new Cidade();
	}

	public void adicionarCidade() {
		persistence.create(cidadeSelecionada);
	}

	public void alterarCidade() {
		persistence.update(cidadeSelecionada);		
	}

	public List<Cidade> getListaAllCidades() {
		return this.persistence.findAll();
	}
	
	@SuppressWarnings("rawtypes")
	public DataModel getListarCidades() {
		this.listaCidade = new ListDataModel<Cidade>(persistence.findAll());
		return this.listaCidade;
	}

}
