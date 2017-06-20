package model.jdbc;

import java.sql.*;

import com.barquillo.utils.Constants;
import com.barquillo.utils.excepciones.*;

public class BBDDConection {

	private String className = "org.sqlite.JDBC";
	private String dbName = "jdbc:sqlite:bdabarquillo.db";
	
	public BBDDConection(){}
	
	public Connection conectarBBDD() throws ConectionException{		
		Connection c = null;
		try {
			Class.forName(className);
			c = DriverManager.getConnection(dbName);
			
		} catch ( Exception e ) {
			throw new ConectionException(Constants.ERROR_CONEXION + ":" +e.getMessage());
		}
		return c;
	}

	public String mostrarTablaUsuarios(){
		String result="";
		
		Connection c = null;
		Statement stmt = null;
		
		try {
			
			c = conectarBBDD();
			stmt = c.createStatement();
			String sql = "Select * from "+ UsuariosTable.TABLE_NAME + " order by "+UsuariosTable.REF_COLUMN; 
			ResultSet rs = stmt.executeQuery(sql);
			
			int cont=0;
			result = Constants.RESULTADO_OPERACION + " \n " 
					+ "   "+UsuariosTable.NOMBRE_COLUMN +"  "+UsuariosTable.PASS_COLUMN + "  "+UsuariosTable.REF_COLUMN + "  "+UsuariosTable.IS_ADMIN_COLUMN;
			
			while(rs.next()){
				cont++;
				
				result = result + "\n R" + cont +" " +  
						 rs.getString(UsuariosTable.NOMBRE_COLUMN) + "  " +
						 rs.getString(UsuariosTable.PASS_COLUMN) + "  "+
						 rs.getString(UsuariosTable.REF_COLUMN)+ "  "+ 
						 rs.getBoolean(UsuariosTable.IS_ADMIN_COLUMN);
			}			
			rs.close();
			stmt.close();
			c.close();
			
		} catch (ConectionException ce){
			result = ce.getMessage();
		} catch ( Exception e ) {			
			result = Constants.SELECT_EXCEPTION + ":"+ e.getMessage();
		}
		return result;		
	}

}
