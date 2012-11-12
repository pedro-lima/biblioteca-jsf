package br.com.biblioteca.controller.livro;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			if (this.editoraSelecionada.getId() == null) {
				this.editoraDao.create(this.editoraSelecionada);
			} else {
				this.editoraDao.update(this.editoraSelecionada);
			}
			this.prepararNovaEditora();
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
