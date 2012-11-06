package br.com.biblioteca.controller.endereco;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
		try {
			if (this.paisSelecionado.getId() == null) {				
				this.paisDao.create(this.paisSelecionado);
			} else {
				this.paisDao.update(this.paisSelecionado);				
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.prepararNovoPais();
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
