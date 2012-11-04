package br.com.biblioteca.controller.livro;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import br.com.biblioteca.model.livro.Assunto;
import br.com.biblioteca.persistence.livro.AssuntoPersistence;

@Named("assuntoBean")
@SessionScoped
public class AssuntoController implements Serializable {
	@EJB
	private AssuntoPersistence assuntoDao;
	private static final long serialVersionUID = 1L;
	private Assunto assuntoSelecionado = new Assunto();	

	public AssuntoController() {
		super();
	}

	public void prepararNovoAssunto() {
		this.assuntoSelecionado = new Assunto();
	}

	public void salvarAssunto() {
		try {
			if (this.assuntoSelecionado.getId() == null) {				
				this.assuntoDao.create(this.assuntoSelecionado);
			} else {
				this.assuntoDao.update(this.assuntoSelecionado);				
			}
			return;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.prepararNovoAssunto();
		}
	}

	public List<Assunto> getAssuntosCadastrados() {
		return this.assuntoDao.findAll();
	}

	public Assunto getAssuntoSelecionado() {
		return assuntoSelecionado;
	}

	public void setAssuntoSelecionado(Assunto assuntoSelecionado) {
		this.assuntoSelecionado = assuntoSelecionado;
	}

	public void prepararAlterartAssunto(Assunto assunto) {		
		this.setAssuntoSelecionado(assunto);
	}

}
