package br.com.biblioteca.controller.login;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import br.com.biblioteca.persistence.pessoa.UsuarioPersistence;

@Named("loginBean")
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;
	private String usuario;
	private String senha;
	private boolean logado = false;
	private String urlAntiga;
	@EJB
	private UsuarioPersistence dao;

	public LoginController() {
		super();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogado() {
		return logado;
	}

	public void setLogado(boolean logado) {
		this.logado = logado;
	}

	public String getUrlAntiga() {
		return urlAntiga;
	}

	public void setUrlAntiga(String urlAntiga) {
		this.urlAntiga = urlAntiga;
	}

	public String login() {
		if (this.validaLogin()) {
			this.limparFormulario();
			this.logado = true;
			if (this.urlAntiga == null) {
				return "/index.jsf";
			} else {
				return this.urlAntiga;
			}
		} else {
			FacesContext fc = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage("ERRO:",
					"Usuario inexistente.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, msg);
		}
		return null;
	}

	private boolean validaLogin() {
		if (this.usuario.equals("admin") && this.senha.equals("admin")) {
			return true;
		}
		if (this.dao.findUsuarioByLoginAndSenha(usuario, senha) != null) {
			return true;
		}
		return false;
	}

	public String logout() {
		this.logado = false;
		this.urlAntiga = null;
		this.limparFormulario();
		return "login";
	}

	public void checkLogin(ComponentSystemEvent event) {
		if (!this.logado) {
			this.setUrlAntiga(this.getCurrentURL());
			FacesContext context = FacesContext.getCurrentInstance();
			ConfigurableNavigationHandler handler = (ConfigurableNavigationHandler) context
					.getApplication().getNavigationHandler();
			handler.performNavigation("login");
		} else {
			this.setUrlAntiga(null);
		}
	}

	public String getCurrentURL() {
		FacesContext context = FacesContext.getCurrentInstance();
		String url = context.getViewRoot().getViewId();
		StringBuilder s = new StringBuilder(url);
		return s.delete(0, s.indexOf("/")).toString();
	}
	
	private void limparFormulario() {
		this.usuario = null;
		this.senha = null;
	}

}
