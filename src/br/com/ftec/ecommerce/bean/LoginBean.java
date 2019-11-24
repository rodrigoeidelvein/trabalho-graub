package br.com.ftec.ecommerce.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.ftec.ecommerce.dao.UserDAO;
import br.com.ftec.ecommerce.model.User;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {
	private final static long serialVersionUID = 12L;
	private User user = new User();
	
	public User getUser() {
		return user;
	}
	
	public String efetuaLogin() {
		System.out.println("Fazendo login do usuário: " + this.user.getEmail());
		
		FacesContext context = FacesContext.getCurrentInstance();
		boolean exist = new UserDAO().exists(this.user);
		
		if (exist) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.user);
			
			return "ecommerce?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		return "login?faces-redirect=true";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		
		return "login?faces-redirect=true";
	}
	
	
}
