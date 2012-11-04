package br.com.biblioteca.controller.livro;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import br.com.biblioteca.model.livro.Autor;
import br.com.biblioteca.persistence.livro.AutorPersistence;

@Named("autorBean")
@SessionScoped
public class AutorController implements Serializable {
	@EJB
	private AutorPersistence autorDao;
	private static final long serialVersionUID = 1L;
	private Autor autorSelecionado = new Autor();	

	public AutorController() {
		super();
	}

	public void prepararNovoAutor() {
		this.autorSelecionado = new Autor();
	}

	public void salvarAutor() {
		try {
			if (this.autorSelecionado.getId() == null) {				
				this.autorDao.create(this.autorSelecionado);
			} else {
				this.autorDao.update(this.autorSelecionado);				
			}
			return;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.prepararNovoAutor();
		}
	}

	public List<Autor> getAutoresCadastrados() {
		return this.autorDao.findAll();
	}

	public Autor getAutorSelecionado() {
		return autorSelecionado;
	}

	public void setAutorSelecionado(Autor autorSelecionado) {
		this.autorSelecionado = autorSelecionado;
	}

	public void prepararAlterartAutor(Autor autor) {		
		this.setAutorSelecionado(autor);
	}

}
