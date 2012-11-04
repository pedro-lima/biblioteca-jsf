package br.com.biblioteca.controller.livro;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import br.com.biblioteca.model.livro.Editora;
import br.com.biblioteca.persistence.livro.EditoraPersistence;

@Named("editoraBean")
@SessionScoped
public class EditoraController implements Serializable {
	@EJB
	private EditoraPersistence editoraDao;
	private static final long serialVersionUID = 1L;
	private Editora editoraSelecionada = new Editora();	

	public EditoraController() {
		super();
	}

	public void prepararNovaEditora() {
		this.editoraSelecionada = new Editora();
	}

	public void salvarEditora() {
		try {
			if (this.editoraSelecionada.getId() == null) {				
				this.editoraDao.create(this.editoraSelecionada);
			} else {
				this.editoraDao.update(this.editoraSelecionada);				
			}
			return;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {
			this.prepararNovaEditora();
		}
	}

	public List<Editora> getEditorasCadastradas() {
		return this.editoraDao.findAll();
	}

	public Editora getEditoraSelecionada() {
		return editoraSelecionada;
	}

	public void setEditoraSelecionada(Editora editoraSelecionada) {
		this.editoraSelecionada = editoraSelecionada;
	}

	public void prepararAlterarEditora(Editora editora) {		
		this.setEditoraSelecionada(editora);
	}

}
