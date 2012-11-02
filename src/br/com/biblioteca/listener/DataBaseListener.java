package br.com.biblioteca.listener;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import br.com.biblioteca.model.emprestimo.Emprestimo;
import br.com.biblioteca.model.endereco.*;
import br.com.biblioteca.model.livro.Assunto;
import br.com.biblioteca.model.livro.Autor;
import br.com.biblioteca.model.livro.Editora;
import br.com.biblioteca.model.livro.ItemLivro;
import br.com.biblioteca.model.livro.Livro;
import br.com.biblioteca.model.pessoa.Funcionario;
import br.com.biblioteca.model.pessoa.Locador;
import br.com.biblioteca.model.pessoa.Usuario;
import br.com.biblioteca.persistence.emprestimo.EmprestimoPersistence;
import br.com.biblioteca.persistence.endereco.*;
import br.com.biblioteca.persistence.livro.AssuntoPersistence;
import br.com.biblioteca.persistence.livro.AutorPersistence;
import br.com.biblioteca.persistence.livro.EditoraPersistence;
import br.com.biblioteca.persistence.livro.ItemLivroPersistence;
import br.com.biblioteca.persistence.livro.LivroPersistence;
import br.com.biblioteca.persistence.pessoa.PessoaPersistence;

/**
 * Application Lifecycle Listener implementation class DataBaseListener
 * 
 */
@WebListener
public class DataBaseListener implements ServletContextListener {
	@EJB
	private PaisPersistence paisDao;
	@EJB
	private LivroPersistence livroDao;
	@EJB
	private AutorPersistence autorDao;
	@EJB
	private AssuntoPersistence assuntoDao;
	@EJB
	private EditoraPersistence editoraDao;
	@EJB
	private PessoaPersistence pessoaDao;
	@EJB
	private CidadePersistence cidadeDao;
	@EJB
	private EmprestimoPersistence emprestimoDao;
	@EJB
	private ItemLivroPersistence itemDao;

	/**
	 * Default constructor.
	 */
	public DataBaseListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		this.cadastrarRegioes();
		//this.cadastrarLivros();
		//this.cadastrarPessoa();
		//this.cadastrarEmprestimo();
	}

	private void cadastrarPessoa() {
		Cidade cidade = cidadeDao.find(1l);
		Endereco endereco = new Endereco("Rua das Palmeiras", "Bento",
				"000000-11111", 22, "Perto da padaria", cidade);
		Locador locador = new Locador("Pedro", "123456", "88888-9999", endereco);
		pessoaDao.create(locador);

		endereco = new Endereco("Rua das Palmeiras", "Bento", "000000-11111",
				22, "Perto da padaria", cidade);
		Usuario usuario = new Usuario("maria", "123");
		Funcionario funcionario = new Funcionario("Maria", "123345",
				"45678234", endereco, usuario, "123456asd7890");
		usuario.setFuncionario(funcionario);
		pessoaDao.create(funcionario);

	}

	private void cadastrarEmprestimo() {
		List<ItemLivro> livros = new ArrayList<ItemLivro>();
		livros.add(itemDao.find(1l));

		Locador locador = (Locador) pessoaDao.find(1l);
		List<Emprestimo> emprestimos = emprestimoDao.findAllAtivoByEmprestimo(locador);
		Emprestimo emprestimo = new Emprestimo(locador, livros, 10);
		emprestimos.add(emprestimo);
		emprestimoDao.create(emprestimo);

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	private void cadastrarLivros() {
		ArrayList<Assunto> assuntos = new ArrayList<Assunto>();
		assuntos.add(new Assunto("Assunto 1"));
		assuntos.add(new Assunto("Assunto 2"));
		assuntos.add(new Assunto("Assunto 3"));
		assuntos.add(new Assunto("Assunto 4"));
		assuntos.add(new Assunto("Assunto 5"));
		assuntos.add(new Assunto("Assunto 6"));
		assuntos.add(new Assunto("Assunto 7"));
		assuntos.add(new Assunto("Assunto 8"));
		assuntos.add(new Assunto("Assunto 9"));
		assuntos.add(new Assunto("Assunto 10"));
		for (Assunto a : assuntos) {
			assuntoDao.create(a);
		}

		Autor autor = new Autor("Machado de Assis");
		ArrayList<Editora> editoras = new ArrayList<Editora>();
		editoras.add(new Editora("Editora 1"));
		editoras.add(new Editora("Editora 2"));
		editoras.add(new Editora("Editora 3"));
		editoras.add(new Editora("Editora 4"));
		editoras.add(new Editora("Editora 5"));
		editoras.add(new Editora("Editora 6"));
		editoras.add(new Editora("Editora 7"));
		editoras.add(new Editora("Editora 8"));
		editoras.add(new Editora("Editora 9"));
		editoras.add(new Editora("Editora 10"));
		for (Editora e : editoras) {
			editoraDao.create(e);
		}

		String[] livros = new String[] { "Ressurreição", "A mão e a luva",
				"Helena", "Iaiá Garcia", "Memórias Póstumas de Brás Cubas",
				"Casa Velha", "Quincas Borba", "Dom Casmurro", "Esaú e Jacó",
				"Memorial de Aires", "Crisálidas", "Crisálidas", "Falenas",
				"Americanas", "Ocidentais", "Poesias Completas" };
		this.popularLivro(autor, livros);
		autorDao.create(autor);

		autor = new Autor("Monteiro Lobato");
		livros = new String[] { "O Saci", "Fábulas",
				"As aventuras de Hans Staden", "Peter Pan",
				"Reinações de Narizinho", "Viagem ao céu",
				"Caçadas de Pedrinho", "História do mundo para as crianças",
				"Emília no país da gramática", "Aritmética da Emília",
				"Geografia de Dona Benta", "História das invenções",
				"Dom Quixote das crianças", "Memórias da Emília",
				"Serões de Dona Benta", "O poço do Visconde",
				"Histórias de Tia Nastácia", "O Picapau Amarelo",
				"O minotauro", "A reforma da natureza", "A chave do tamanho",
				"Os doze trabalhos de Hércules", "Histórias diversas" };
		this.popularLivro(autor, livros);
		autorDao.create(autor);
	}

	private void popularLivro(Autor autor, String[] livros) {
		Livro livro = null;
		for (String nome : livros) {
			int numero = (int) (Math.random() * 10);
			List<Editora> editoras = editoraDao.findAll();
			List<Assunto> assuntos = assuntoDao.findAll();
			livro = new Livro(editoras.get(numero), nome, 1872, 40, autor,
					assuntos.get(numero));
			autor.getLivros().add(livro);

			for (int i = 0; i < numero; i++) {
				ItemLivro item = new ItemLivro();
				item.setCodigo(java.util.UUID.randomUUID().toString());
				item.setLivro(livro);
				livro.getItens().add(item);
			}
		}
	}

	private void cadastrarRegioes() {
		// -------------BRASIL-------------
		Pais pais = new Pais("Brasil");

		Estado estado = new Estado("São Paulo", "SP", pais);
		String[] cidades = new String[] { "São paulo", "Guarulhos", "Campinas",
				"São Bernardo do Campo", "Sorocaba" };
		pais.getEstados().add(popularEstado(cidades, estado));
		pais.getEstados().add(estado);

		estado = new Estado("Rio de Janeiro", "RJ", pais);
		cidades = new String[] { "Rio de Janeiro", "São Gonçalo",
				"Duque de Caxias", "Nova Iguaçu", "Niterói" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Paraiba", "PB", pais);
		cidades = new String[] { "João Pessoa", "Campina Grande", "Santa Rita",
				"Patos", "Bayeux" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Piaui", "PI", pais);
		cidades = new String[] { "Teresina", "Parnaíba", "Picos", "Piripiri",
				"Floriano" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Pernambuco", "PE", pais);
		cidades = new String[] { "Recife", "Jaboatão dos Guararapes", "Olinda",
				"Caruaru", "Paulista" };
		pais.getEstados().add(popularEstado(cidades, estado));

		paisDao.create(pais);

		// -------------USA-------------
		pais = new Pais("Estados Unidos");

		estado = new Estado("Nova Iorque", "NY", pais);
		cidades = new String[] { "Nova Iorque", "Buffalo", "Rochester",
				"Yonkers", "Syracuse" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Califórnia", "CA", pais);
		cidades = new String[] { "Los Angeles", "San Francisco", "San Diego",
				"São Jose", "Sacramento" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Texas", "TX", pais);
		cidades = new String[] { "Abilene", "Amarillo", "Austin-Round Rock",
				"Beaumont-Port Arthur", "Brownsville-Harlingen" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Pensilvânia", "PA", pais);
		cidades = new String[] { "Allentown", "Bethlehem", "Easton", "Erie",
				"Filadélfia" };
		pais.getEstados().add(popularEstado(cidades, estado));

		estado = new Estado("Illinois", "IL", pais);
		cidades = new String[] { "Chicago", "Aurora", "Joliet", "Naperville",
				"Peoria" };
		pais.getEstados().add(popularEstado(cidades, estado));

		paisDao.create(pais);
	}

	private Estado popularEstado(String[] cidades, Estado estado) {
		for (String cidade : cidades) {
			estado.getCidades().add(new Cidade(cidade, estado));
		}
		return estado;
	}

}
