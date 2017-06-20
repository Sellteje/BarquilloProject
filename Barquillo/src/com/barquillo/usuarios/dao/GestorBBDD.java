package com.barquillo.usuarios.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.metamodel.EntityType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.internal.SingularAttributeImpl;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import com.barquillo.hibernate.listeners.HibernateUtil;
import com.barquillo.hibernate.model.Usuario;
import com.barquillo.utils.Constants;
import com.barquillo.utils.Utils;
import com.barquillo.utils.excepciones.NotLoginException;

import model.jdbc.UsuariosTable;

public class GestorBBDD {

	private Session session;
	private Transaction tx;



	private void init() throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
	}

	public  Usuario login(Usuario user) {		
		Usuario result = null;	

		try{
			init();  
			Usuario userResult = session.get(Usuario.class, user.getId_Usuario()); 
			
			if(userResult != null && userResult.getPassword().equals(user.getPassword()) ){
				result = userResult;
			}

		}catch (HibernateException e) {			
		
			e.printStackTrace(); 
		}catch (Throwable ex) {
			System.err.println(Constants.ERROR_CREAR_SESION + ex);
			throw new ExceptionInInitializerError(ex); 
		} finally {
			session.close(); 
		}

		return result;
	}

	public String addUser(String nombre, String apellido, String password){
		String userID = "";
		String result = null;
		try{
			init();    
			Usuario user = new Usuario(nombre,apellido,password);
			userID = (String) session.save(user); 
			tx.commit();		
			result = String.valueOf(userID);

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return result;
	}

	public String deleteUser(int id) {
		Usuario user = null;
		String result = null;
		try{
			init();  
			user = session.get(Usuario.class, id); 
			session.delete(user); 
			result = String.valueOf(user.getId_Usuario());
			tx.commit();		      		    

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String getUsers() {
		String result="";
		try{

			init();
			Query<Usuario> query = session.createQuery("from "+ Usuario.class.getName());
			List<Usuario> userList = query.list();

			result = Constants.RESULTADO_OPERACION + " \n " 
					+ "   "+UsuariosTable.NOMBRE_COLUMN +"  "+UsuariosTable.PASS_COLUMN + "  "+UsuariosTable.REF_COLUMN + "  "+UsuariosTable.IS_ADMIN_COLUMN;
			int cont=0;
			for(Usuario us : userList){
				cont++;
				result = result + "\n R" + cont +" " +  
						us.getNombre() + "  " +
						us.getPassword() + "  "+
						us.getId_Usuario()+ "  "+ 
						us.getIsAdmin();											
			}	  			      
			tx.commit();		      		    

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return result;
	}


	@SuppressWarnings("rawtypes")
	public String updateUser(int id, String[] datos) {		
		String result = null;
		try{

			init();
			Usuario user = session.get(Usuario.class, id); 
			user.setNombre(datos[0]);
			user.setPassword(datos[1]);
			session.update(user); 
			result = String.valueOf(user.getId_Usuario());
			tx.commit();		      		    

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return result;
	}



	@SuppressWarnings("rawtypes")
	public String showTables() {
		String result = "";
		try{

			init();
			Iterator<EntityType<?>> it = session.getMetamodel().getEntities().iterator();
			while(it.hasNext()){
				EntityType<?> t = it.next();
				result += Constants.SALTO_LINEA + Constants.TABLA+ t.getName() + Constants.SALTO_LINEA;		    	

				Iterator<?> g = t.getAttributes().iterator();


				while(g.hasNext()){
					SingularAttributeImpl h = ((SingularAttributeImpl) g.next());
					String nombre = h.getName()+" ";
					String[] tipo = h.getJavaType().getName().split("\\.");	   
					h.isOptional();

					result += Constants.ESPACIADOR +Constants.COLUMNA  +
							nombre+ Constants.ESPACIADOR+
							Constants.TIPO + tipo[tipo.length-1]+Constants.ESPACIADOR+
							Constants.NULLABLE + Utils.booleanToString(h.isOptional())+Constants.ESPACIADOR+
							Constants.CLAVE + Utils.booleanToString(h.isId())+ Constants.SALTO_LINEA;		    		
				}		    	
			}		      

		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return result;
	}

	private void manejaExcepcion(HibernateException he) throws HibernateException{
		tx.rollback();
		throw new HibernateException("Ocurrió un error en la capa de acceso a datos", he);
	}



}
