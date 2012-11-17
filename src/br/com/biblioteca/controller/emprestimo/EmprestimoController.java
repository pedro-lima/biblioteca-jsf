package br.com.biblioteca.controller.emprestimo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import br.com.biblioteca.model.emprestimo.Emprestimo;
import br.com.biblioteca.model.livro.ItemLivro;
import br.com.biblioteca.model.pessoa.Locador;
import br.com.biblioteca.persistence.emprestimo.EmprestimoPersistence;
import br.com.biblioteca.persistence.livro.ItemLivroPersistence;
import br.com.biblioteca.persistence.pessoa.PessoaPersistence;

@Named("emprestimoBean")
@SessionScoped
public class EmprestimoController implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private EmprestimoPersistence emprestimoDao;
	@EJB
	private ItemLivroPersistence livroDao;
	@EJB
	private PessoaPersistence pessoaDao;
	private Emprestimo emprestimoSelecionado = new Emprestimo();
	private long locadorSelecionado;
	private List<ItemLivro> livrosSelecionados = new ArrayList<ItemLivro>();
	private List<ItemLivro> livrosCadastrados = null;
	private List<SelectItem> locadores = null;
	private boolean iscallback = false;

	public EmprestimoController() {
		super();
	}

	public Emprestimo getEmprestimoSelecionado() {
		return emprestimoSelecionado;
	}

	public void setEmprestimoSelecionado(Emprestimo emprestimoSelecionado) {
		this.emprestimoSelecionado = emprestimoSelecionado;
	}

	public long getLocadorSelecionado() {
		return locadorSelecionado;
	}

	public void setLocadorSelecionado(long locadorSelecionado) {
		this.locadorSelecionado = locadorSelecionado;
	}

	public List<ItemLivro> getLivrosSelecionados() {
		return livrosSelecionados;
	}

	public void setLivrosSelecionados(List<ItemLivro> livrosSelecionados) {
		this.livrosSelecionados = livrosSelecionados;
	}

	public List<ItemLivro> getLivrosCadastrados() {
		return livrosCadastrados;
	}

	public void setLivrosCadastrados(List<ItemLivro> livrosCadastrados) {
		this.livrosCadastrados = livrosCadastrados;
	}

	public List<SelectItem> getLocadores() {
		return locadores;
	}

	public void setLocadores(List<SelectItem> locadores) {
		this.locadores = locadores;
	}

	// ===================================================================================================
	public void selecionarEmprestimo(long id) {
		Emprestimo emprestimo = this.emprestimoDao.findEmprestimoGetLivros(id);
		this.emprestimoSelecionado = emprestimo;
	}

	public List<Emprestimo> getEmprestimosCadastrados() {
		return this.emprestimoDao.findAll();
	}

	public void adicionarLivroEmprestimo(ItemLivro livro) {
		if (!this.livrosSelecionados.contains(livro)) {
			this.livrosSelecionados.add(livro);
			this.livrosCadastrados.remove(livro);
		}
	}

	public void removerLivroEmprestimo(ItemLivro livro) {
		if (this.livrosSelecionados.contains(livro)) {
			this.livrosSelecionados.remove(livro);
			this.livrosCadastrados.add(livro);
		}
	}

	public void prepararNovoEmprestimo() {
		this.emprestimoSelecionado = new Emprestimo();
		this.livrosSelecionados.clear();
		this.iscallback = false;
		this.prepararListagens();
	}

	public void salvarEmprestimo() {
		FacesContext fc = FacesContext.getCurrentInstance();

		try {
			Locador locador = this.pessoaDao
					.findLocadorGetEmprestimos(this.locadorSelecionado);
			locador.getEmprestimos().add(this.emprestimoSelecionado);

			this.emprestimoSelecionado.setLocador(locador);
			this.emprestimoSelecionado.setDataEmprestimo(Emprestimo
					.calcularDataOperacaoEmprestimo());
			this.emprestimoSelecionado.setDataDevolucaoEsperada(Emprestimo
					.calcularDataDevolucao(
							this.emprestimoSelecionado.getDataEmprestimo(),
							Emprestimo.diasLimite));
			this.emprestimoDao.create(emprestimoSelecionado);

			List<ItemLivro> livros = new ArrayList<ItemLivro>();
			for (ItemLivro l : livrosSelecionados) {
				livros.add(this.livroDao.findItemLivroGetEmprestimos(l.getId()));
			}

			this.emprestimoSelecionado.getLivros().addAll(livros);
			this.emprestimoDao.update(emprestimoSelecionado);
			this.prepararNovoEmprestimo();
			
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

	public List<SelectItem> getLocadoresCadastrados() {
		List<Locador> locadores = this.pessoaDao.findAllLocadores();
		List<SelectItem> itens = new ArrayList<SelectItem>();
		for (Locador locador : locadores) {
			itens.add(new SelectItem(locador.getId(), locador.getNome()));
		}
		return itens;
	}

	public void finalizarEmprestimo(long id) {
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			Emprestimo emprestimo = this.emprestimoDao.find(id);
			emprestimo.setDataDevolucao(new Date(System.currentTimeMillis()));
			this.emprestimoDao.update(emprestimo);
			this.prepararNovoEmprestimo();
			this.iscallback = false;
			
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

	public void prepararListagens() {
		if (this.iscallback == false) {
			this.criarListagemLivros();
			this.criarListagemLocadores();
			this.iscallback = true;
		}
	}

	public void criarListagemLivros() {
		this.livrosCadastrados = this.livroDao.findAllDisponiveis();
	}

	public void criarListagemLocadores() {
		this.locadores = this.getLocadoresCadastrados();
	}

}
