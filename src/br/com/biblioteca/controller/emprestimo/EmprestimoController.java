package br.com.biblioteca.controller.emprestimo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
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
		if (this.livrosCadastrados == null) {
			List<ItemLivro> livros = this.livroDao.findAllDisponiveis();
			this.livrosCadastrados = livros;
		}
		return livrosCadastrados;
	}

	public void setLivrosCadastrados(List<ItemLivro> livrosCadastrados) {
		this.livrosCadastrados = livrosCadastrados;
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
		this.livrosCadastrados = null;
	}  

	public void salvarEmprestimo() {
		Locador locador  = this.pessoaDao
				.findLocadorGetEmprestimos(this.locadorSelecionado);
		locador.getEmprestimos()
				.add(this.emprestimoSelecionado);

		this.emprestimoSelecionado.setLocador(locador);
		this.emprestimoSelecionado.setDataEmprestimo(Emprestimo
				.calcularDataOperacaoEmprestimo());
		this.emprestimoSelecionado.setDataDevolucaoEsperada(Emprestimo
				.calcularDataDevolucao(
						this.emprestimoSelecionado.getDataEmprestimo(),
						Emprestimo.diasLimite));
		this.emprestimoDao.create(emprestimoSelecionado);
				
		
		List<ItemLivro> livros = new ArrayList<ItemLivro>();
		for(ItemLivro l : livrosSelecionados) {
			livros.add(this.livroDao.findItemLivroGetEmprestimos(l.getId()));
		}
				
		this.emprestimoSelecionado.getLivros().addAll(livros);
		this.emprestimoDao.update(emprestimoSelecionado);
		this.prepararNovoEmprestimo();

	}

	public List<SelectItem> getLocadoresCadastrados() {
		List<Locador> locadores = this.pessoaDao.findAllLocadores();
		List<SelectItem> itens = new ArrayList<SelectItem>();
		for (Locador locador : locadores) {
			itens.add(new SelectItem(locador.getId(),locador.getNome()));
		}
		return itens;
	}

	public List<SelectItem> getLocadores() {
		if(locadores == null) {
			locadores = this.getLocadoresCadastrados();
		}
		return locadores;
	}

	public void setLocadores(List<SelectItem> locadores) {
		this.locadores = locadores;
	}
	
	public void finalizarEmprestimo(long id) {
		Emprestimo emprestimo = this.emprestimoDao.find(id);
		emprestimo.setDataDevolucao(new Date(System.currentTimeMillis()));
		this.emprestimoDao.update(emprestimo);
		this.prepararNovoEmprestimo();
	}

}
