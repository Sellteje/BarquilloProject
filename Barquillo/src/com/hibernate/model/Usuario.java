package com.hibernate.model;

public class Usuario {
	
	private String id_Usuario;
	private String nombre;
	private String apellido;
	private String password;
	private Boolean isAdmin;
	
	
	public Usuario() {
	}
	
	public Usuario(String nombre, String apellido, String password) {		
		this.nombre = nombre;
		this.password = password;
		this.apellido = apellido;
		this.isAdmin = false;
	}

	
	public String getId_Usuario() {
		return id_Usuario;
	}

	public void setId_Usuario(String id_Usuario) {
		this.id_Usuario = id_Usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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
