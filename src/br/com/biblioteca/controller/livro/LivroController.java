package br.com.biblioteca.controller.livro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import br.com.biblioteca.model.livro.*;
import br.com.biblioteca.persistence.livro.*;

@Named("livroBean")
@SessionScoped
public class LivroController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private EditoraPersistence editoraDao;
	@EJB
	private AssuntoPersistence assuntoDao;
	@EJB
	private AutorPersistence autorDao;
	@EJB
	private ItemLivroPersistence itemDao;
	@EJB
	private LivroPersistence livroDao;
	private Livro livroSelecionado = new Livro();
	private Livro livroDetalhe = new Livro();
	private long editoraSelecionada;
	private long assuntoSelecionado;
	private long autorSelecionado;
	private List<SelectItem> listaEditoras = new ArrayList<SelectItem>();
	private List<SelectItem> listaAssuntos = new ArrayList<SelectItem>();
	private List<SelectItem> listaAutores = new ArrayList<SelectItem>();
	private List<ItemLivro> itensAdicionados = new ArrayList<ItemLivro>();
	private ItemLivro itemEditado = new ItemLivro();

	public LivroController() {
		super();
	}

	public Livro getLivroSelecionado() {
		return livroSelecionado;
	}

	public void setLivroSelecionado(Livro livroSelecionado) {
		this.livroSelecionado = livroSelecionado;
	}
	
	public Livro getLivroDetalhe() {
		return livroDetalhe;
	}

	public void setLivroDetalhe(Livro livroDetalhe) {
		this.livroDetalhe = livroDetalhe;
	}

	public long getEditoraSelecionada() {
		return editoraSelecionada;
	}

	public void setEditoraSelecionada(long editoraSelecionada) {
		this.editoraSelecionada = editoraSelecionada;
	}

	public long getAssuntoSelecionado() {
		return assuntoSelecionado;
	}

	public void setAssuntoSelecionado(long assuntoSelecionado) {
		this.assuntoSelecionado = assuntoSelecionado;
	}

	public long getAutorSelecionado() {
		return autorSelecionado;
	}

	public void setAutorSelecionado(long autorSelecionado) {
		this.autorSelecionado = autorSelecionado;
	}

	public List<SelectItem> getListaEditoras() {
		return listaEditoras;
	}

	public void setListaEditoras(List<SelectItem> listaEditoras) {
		this.listaEditoras = listaEditoras;
	}

	public List<SelectItem> getListaAssuntos() {
		return listaAssuntos;
	}

	public void setListaAssuntos(List<SelectItem> listaAssuntos) {
		this.listaAssuntos = listaAssuntos;
	}

	public List<SelectItem> getListaAutores() {
		return listaAutores;
	}

	public void setListaAutores(List<SelectItem> listaAutores) {
		this.listaAutores = listaAutores;
	}

	public List<ItemLivro> getItensAdicionados() {
		return itensAdicionados;
	}

	public void setItensAdicionados(List<ItemLivro> itensAdicionados) {
		this.itensAdicionados = itensAdicionados;
	}

	public ItemLivro getItemEditado() {
		return itemEditado;
	}

	public void setItemEditado(ItemLivro itemEditado) {
		this.itemEditado = itemEditado;
	}

	public void prepararNovoLivro() {
		this.livroSelecionado = new Livro();
		this.itemEditado = new ItemLivro();
		this.itensAdicionados.clear();
	}

	public void prepararNovoItem() {
		this.itemEditado = new ItemLivro();
	}

	public void selecionarLivro(long id) {
		Livro livro = this.livroDao.findLivroDados(id);
		this.setLivroSelecionado(livro);
	}

	public void prepararListagens() {
		this.construirListaAutor();
		this.construirListaAssunto();
		this.construirListaEditora();
	}

	public void construirListaAutor() {
		this.listaAutores.clear();
		List<Autor> autores = this.autorDao.findAll();
		for (Autor autor : autores) {
			this.listaAutores
					.add(new SelectItem(autor.getId(), autor.getNome()));
		}
	}

	public void construirListaEditora() {
		List<Editora> editoras = this.editoraDao.findAll();
		for (Editora editora : editoras) {
			this.listaEditoras.add(new SelectItem(editora.getId(), editora
					.getNome()));
		}
	}

	public void construirListaAssunto() {
		this.listaAssuntos.clear();
		List<Assunto> assuntos = this.assuntoDao.findAll();
		for (Assunto assunto : assuntos) {
			this.listaAssuntos.add(new SelectItem(assunto.getId(), assunto
					.getNome()));
		}
	}

	public void adicionarNovoItemLivro() {
		this.itensAdicionados.add(itemEditado);
		this.itemEditado = new ItemLivro();
	}

	public void removerItemLivro(ItemLivro item) {
		this.itensAdicionados.remove(item);
	}

	public void cadastrarLivro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Editora editoraNova = this.editoraDao
				.findEditoraGetLivros(editoraSelecionada);
		Assunto assuntoNovo = this.assuntoDao
				.findAssuntoGetLivros(assuntoSelecionado);
		Autor autorNovo = this.autorDao.findAutorGetLivros(autorSelecionado);
		try {
			if (this.livroSelecionado.getId() == null) {
				editoraNova.getLivros().add(this.livroSelecionado);
				assuntoNovo.getLivros().add(this.livroSelecionado);
				autorNovo.getLivros().add(this.livroSelecionado);

				this.livroSelecionado.setAssunto(assuntoNovo);
				this.livroSelecionado.setAutor(autorNovo);
				this.livroSelecionado.setEditora(editoraNova);

				this.livroSelecionado.setItens(this.itensAdicionados);
				for (ItemLivro item : this.itensAdicionados) {
					item.setLivro(livroSelecionado);
				}
				this.livroDao.create(this.livroSelecionado);
			} else {
				Editora editoraAntigo = this.editoraDao
						.findEditoraGetLivros(this.livroSelecionado
								.getEditora().getId());
				Assunto assuntoAntigo = this.assuntoDao
						.findAssuntoGetLivros(this.getLivroSelecionado()
								.getAssunto().getId());
				Autor autorAntigo = this.autorDao.findAutorGetLivros(this
						.getLivroSelecionado().getAutor().getId());
				
				editoraAntigo.getLivros().remove(this.livroSelecionado);
				assuntoAntigo.getLivros().remove(this.livroSelecionado);
				autorAntigo.getLivros().remove(this.livroSelecionado);

				editoraNova.getLivros().add(this.livroSelecionado);
				assuntoNovo.getLivros().add(this.livroSelecionado);
				autorNovo.getLivros().add(this.livroSelecionado);

				this.livroSelecionado.setAssunto(assuntoNovo);
				this.livroSelecionado.setAutor(autorNovo);
				this.livroSelecionado.setEditora(editoraNova);

				this.livroSelecionado.getItens().addAll(this.itensAdicionados);
				for (ItemLivro item : this.itensAdicionados) {
					item.setLivro(livroSelecionado);
				}
				this.livroDao.update(this.livroSelecionado);				
			}
			this.prepararNovoLivro();

			FacesMessage msg = new FacesMessage("SUCESSO",
					"Operação realizada com sucesso.");
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
			fc.addMessage(null, msg);
			fc.renderResponse();
		} catch (Exception ex) {
			FacesMessage msg = new FacesMessage("ERRO:",
					"Erro ao realizar a operação.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, msg);
			fc.renderResponse();
		}
	}
	
	public List<Livro> getLivrosCadastrados() {
		return this.livroDao.findAll();
	}
	
	public void prepararEditarLivro(long id) {
		Livro livro = this.livroDao.findLivroDados(id);
		this.setLivroSelecionado(livro);
		this.setEditoraSelecionada(livro.getEditora().getId());
		this.setAssuntoSelecionado(livro.getAssunto().getId());
		this.setAssuntoSelecionado(livro.getAutor().getId());
	}
	
	public void prepararLivroDetalhe(long id) {
		Livro livro = this.livroDao.findLivroDados(id);
		this.setLivroDetalhe(livro);		
	}

}
