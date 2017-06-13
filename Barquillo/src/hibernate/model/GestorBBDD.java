package hibernate.model;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import utils.Constants;
import model.jdbc.UsuariosTable;

public class GestorBBDD {
	
	private SessionFactory mFactory;
	
	public GestorBBDD(){
		
	     mFactory = new Configuration().configure("hibernate/resources/hibernate.cfg.xml").buildSessionFactory();
	}
	
	
	
	public String addUser(String nombre, String password){
		 String userID = "";
		 String result = null;
		 try{		    
		     
		     Session session = mFactory.openSession();
		     Transaction tx = null;		    
		     
		     try{
		    	 
		      tx = session.beginTransaction();
		      Usuario user = new Usuario(nombre,password);
		      userID = (String) session.save(user); 
		      tx.commit();		
		      result = String.valueOf(userID);
		      
		     }catch (HibernateException e) {
		    	 if (tx!=null) tx.rollback();
		    	 e.printStackTrace(); 
		     }finally {
		    	 session.close(); 
		     }
		     
		    }catch (Throwable ex) { 
		     System.err.println("Couldn't create session factory." + ex);
		     throw new ExceptionInInitializerError(ex); 
		    }
		 return result;
	}

	public String deleteUser(int id) {
		 Usuario user = null;
		 String result = null;
		 try{
				     
		     Session session = mFactory.openSession();
		     Transaction tx = null;		    
		     
		     try{
		    	 
		      tx = session.beginTransaction();
		      user = session.get(Usuario.class, id); 
		      session.delete(user); 
		      result = String.valueOf(user.getReferencia());
		      tx.commit();		      		    
		      
		     }catch (HibernateException e) {
		    	 if (tx!=null) tx.rollback();
		    	 e.printStackTrace(); 
		     }finally {
		    	 session.close(); 
		     }
		     
		    }catch (Throwable ex) { 
		     System.err.println("Couldn't create session factory." + ex);
		     throw new ExceptionInInitializerError(ex); 
		    }
		 return result;
	}

	@SuppressWarnings("unchecked")
	public String getUsers() {
		 String result="";
		 try{
				     
		     Session session = mFactory.openSession();
		     Transaction tx = null;		    
		     
		     try{
		    	 
		      tx = session.beginTransaction();
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
							 us.getReferencia()+ "  "+ 
							 us.getIsAdmin();											
				}	  			      
		      tx.commit();		      		    
		      
		     }catch (HibernateException e) {
		    	 if (tx!=null) tx.rollback();
		    	 e.printStackTrace(); 
		     }finally {
		    	 session.close(); 
		     }
		     
		    }catch (Throwable ex) { 
		     System.err.println("Couldn't create session factory." + ex);
		     throw new ExceptionInInitializerError(ex); 
		    }
		 return result;
	}
	

	public String updateUser(int id, String[] datos) {
		 Usuario user = null;
		 String result = null;
		 try{
				     
		     Session session = mFactory.openSession();
		     Transaction tx = null;		    
		     
		     try{
		    	 
		      tx = session.beginTransaction();
		      user = session.get(Usuario.class, id); 
		      user.setnombre(datos[0]);
		      user.setPassword(datos[1]);
		      session.update(user); 
		      result = String.valueOf(user.getReferencia());
		      tx.commit();		      		    
		      
		     }catch (HibernateException e) {
		    	 if (tx!=null) tx.rollback();
		    	 e.printStackTrace(); 
		     }finally {
		    	 session.close(); 
		     }
		     
		    }catch (Throwable ex) { 
		     System.err.println("Couldn't create session factory." + ex);
		     throw new ExceptionInInitializerError(ex); 
		    }
		 return result;
	}
	
	
}
