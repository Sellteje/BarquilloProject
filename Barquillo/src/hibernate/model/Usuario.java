package hibernate.model;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Usuario {
	
	private int referencia;
	private String nombre;
	private String password;
	private Boolean isAdmin;
	
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String password) {		
		this.nombre = nombre;
		this.password = password;
		this.isAdmin=false;
	}
	
	
	public int getReferencia() {
		return referencia;
	}

	public void setReferencia(int referencia) {
		this.referencia = referencia;
	}

	public String getNombre() {
		return nombre;
	}


	public void setnombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Boolean getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
