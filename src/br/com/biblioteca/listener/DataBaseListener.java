package br.com.biblioteca.listener;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import br.com.biblioteca.model.endereco.*;
import br.com.biblioteca.model.livro.Assunto;
import br.com.biblioteca.model.livro.Autor;
import br.com.biblioteca.model.livro.Editora;
import br.com.biblioteca.model.livro.Livro;
import br.com.biblioteca.persistence.endereco.*;
import br.com.biblioteca.persistence.livro.AssuntoPersistence;
import br.com.biblioteca.persistence.livro.AutorPersistence;
import br.com.biblioteca.persistence.livro.EditoraPersistence;
import br.com.biblioteca.persistence.livro.LivroPersistence;

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
        this.cadastrarLivros();    	
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
    
    @SuppressWarnings("unused")
	private void cadastrarLivros() {    	
    	Assunto assunto = new Assunto("Romance");
    	assuntoDao.create(assunto);
    	Autor autor = new Autor("Machado de Assis");
    	autorDao.create(autor);
    	Editora editora = new Editora("Sextante");
    	editoraDao.create(editora);
    	String[] livros = new String[]{"Ressurreição","A mão e a luva",
    			"Helena","Iaiá Garcia","Memórias Póstumas de Brás Cubas",
    			"Casa Velha","Quincas Borba","Dom Casmurro","Esaú e Jacó",
    			"Memorial de Aires","Crisálidas","Crisálidas","Falenas","Americanas","Ocidentais","Poesias Completas"};
		//this.popularLivro(autor,editora, assunto, livros);
    }
    
    @SuppressWarnings("unused")
	private void popularLivro(Autor autor, Editora editora, Assunto assunto, String[] livros) {
    	Livro livro = null;
    	for(String nome: livros) {
    		livro = new Livro(editora,nome,1872,40,autor,assunto);
    		assunto.getLivros().add(livro);
    		autor.getLivros().add(livro);
    		editora.getLivros().add(livro);
    		livroDao.create(livro);  	
    		
    	}    	
    }
    
	private void cadastrarRegioes() {
		//-------------BRASIL-------------
		Pais pais = new Pais("Brasil");
				
    	Estado estado = new Estado("São Paulo","SP",pais);
    	String[] cidades = new String[]{"São paulo","Guarulhos","Campinas","São Bernardo do Campo","Sorocaba"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	pais.getEstados().add(estado);
    	    	
    	estado = new Estado("Rio de Janeiro","RJ",pais);
    	cidades = new String[]{"Rio de Janeiro","São Gonçalo","Duque de Caxias","Nova Iguaçu","Niterói"};    	
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Paraiba","PB",pais);
    	cidades = new String[]{"João Pessoa","Campina Grande","Santa Rita","Patos","Bayeux"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Piaui","PI",pais);
    	cidades = new String[]{"Teresina","Parnaíba","Picos","Piripiri","Floriano"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Pernambuco","PE",pais);
    	cidades = new String[]{"Recife","Jaboatão dos Guararapes","Olinda","Caruaru","Paulista"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	    	
    	paisDao.create(pais);
    	
    	//-------------USA-------------
    	pais = new Pais("Estados Unidos");
    	
    	estado = new Estado("Nova Iorque","NY",pais);
    	cidades = new String[]{"Nova Iorque","Buffalo","Rochester","Yonkers","Syracuse"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Califórnia","CA",pais);
    	cidades = new String[]{"Los Angeles","San Francisco","San Diego","São Jose","Sacramento"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Texas","TX",pais);
    	cidades = new String[]{"Abilene","Amarillo","Austin-Round Rock","Beaumont-Port Arthur","Brownsville-Harlingen"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Pensilvânia","PA",pais);
    	cidades = new String[]{"Allentown","Bethlehem","Easton","Erie","Filadélfia"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	estado = new Estado("Illinois","IL",pais);
    	cidades = new String[]{"Chicago","Aurora","Joliet","Naperville","Peoria"};
    	pais.getEstados().add(popularEstado(cidades,estado));
    	
    	paisDao.create(pais);
    } 
	
	private Estado popularEstado(String[] cidades, Estado estado) {
		for(String cidade:cidades) {
			estado.getCidades().add(new Cidade(cidade, estado));
		}
		return estado;
	}
    
}
