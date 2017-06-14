package hibernate.model;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.metamodel.EntityType;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.metamodel.internal.SingularAttributeImpl;
import org.hibernate.query.Query;

import utils.Constants;
import utils.Utils;
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
		     System.err.println(Constants.ERROR_CREAR_SESION+ ex);
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
		     System.err.println(Constants.ERROR_CREAR_SESION + ex);
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
		     System.err.println(Constants.ERROR_CREAR_SESION+ ex);
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
		     System.err.println(Constants.ERROR_CREAR_SESION + ex);
		     throw new ExceptionInInitializerError(ex); 
		    }
		 return result;
	}



	@SuppressWarnings("rawtypes")
	public String showTables() {
		 String result = "";
		 try{
				     
		     Session session = mFactory.openSession();
		     Transaction tx = null;		    
		     
		     try{
		    	 
		      tx = session.beginTransaction();
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
		     
		    }catch (Throwable ex) { 
		    	System.err.println(Constants.ERROR_CREAR_SESION + ex);
		    	throw new ExceptionInInitializerError(ex); 
		    }
		 return result;
	}
	
	
	
	
}
