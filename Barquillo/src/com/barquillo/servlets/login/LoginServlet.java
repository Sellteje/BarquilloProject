package com.barquillo.servlets.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.barquillo.hibernate.model.Usuario;
import com.barquillo.usuarios.dao.GestorBBDD;

/**
 * Servlet implementation class p
 */
public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	static Logger logger = Logger.getLogger(LoginServlet.class);


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
			Usuario user = new Usuario(); 
			GestorBBDD gestorBBDD = new GestorBBDD();

			user.setId_Usuario(request.getParameter("nombre"));
			user.setPassword(request.getParameter("pass"));
			logger.debug("Logando usuario "+user.getId_Usuario());
//			
//			String tablas = gestorBBDD.showTables();
//			System.out.println(tablas);
//			System.out.println(gestorBBDD.getUsers());
			
			user = gestorBBDD.login(user);			
			if (user != null) { 
				HttpSession session = request.getSession(true); 
				session.setAttribute("currentSessionUser",user);  
				response.sendRedirect("login/userLogged.jsp");
				logger.debug("Login correcto....");
			} else {
				response.sendRedirect("login/invalidLogin.jsp"); //error page
				logger.debug("Login Incorrecto, usuario rechazado");
			} 

		} catch (Throwable theException) { 
			logger.debug(theException);
		}
	}

}
