package com.hibernate.interfazUsuario;

import java.util.Scanner;


import com.barquillo.usuarios.dao.GestorBBDD;
import com.barquillo.utils.Constants;



public class Menu {
	
	
	private Scanner scanner;
	public enum Numerar {NUMERO, LETRA_ABC};
	
	private Numerar numerar = Numerar.NUMERO;
	
	private final static String[] opcionesMenu = {Constants.MENU_CONSULTAR_USUARIOS, Constants.MENU_ANIADIR_USUARIO,
			Constants.MENU_ELIMINAR_USUARIO, Constants.MENU_ACTUALIZAR_USUARIO,Constants.MENU_ADMIN_BBDD ,Constants.MENU_SALIR};
	
	private final static String[] menuAdministrador = {Constants.MENU_CREAR_TABLA, Constants.MENU_VER_TABLAS, Constants.MENU_VER_COLUMNAS, Constants.MENU_MODIFICAR_TABLA,Constants.MENU_VOLVER};
	
	private GestorBBDD gestorBBDD;
	
	public Menu(){
		gestorBBDD = new GestorBBDD();
	}
	
	@SuppressWarnings("resource")
	public String activar(){
		String opcionSeleccionada=null;
		int nValor = -1;
		mostrarOpciones();	

		Scanner scanner = new Scanner(System.in);
		while (opcionSeleccionada==null || nValor<1 || nValor>opcionesMenu.length) {
			System.out.print ("\n Seleccione una de las opciones para continuar: ");

			opcionSeleccionada=scanner.next();
			nValor = Integer.valueOf(opcionSeleccionada);	
		}

		return opcionSeleccionada;
	}

	
	private void mostrarOpciones(){
		System.out.println();
		for (int i=0;i<opcionesMenu.length;i++){//for
			String opcion=((this.numerar==Numerar.NUMERO) ? Integer.toString(i+1) : 
				Character.toString((char)((int)'a'+i)));
			System.out.printf ("%s)\t%s\n", opcion,opcionesMenu[i]);
		}
	}
	
	private String activarMenuAdmin(){
		String opcionSeleccionada=null;
		int nValor = -1;
		mostrarOpcionesAdmin();	

		scanner = new Scanner(System.in);
		while (opcionSeleccionada==null || nValor<1 || nValor>menuAdministrador.length) {
			System.out.print ("\n Seleccione una de las opciones para continuar: ");
			opcionSeleccionada=scanner.next();
			nValor = Integer.valueOf(opcionSeleccionada);	
		}

		return opcionSeleccionada;
	}
	
	private void mostrarOpcionesAdmin(){
		System.out.println();
		for (int i=0;i<menuAdministrador.length;i++){//for
			String opcion=((this.numerar==Numerar.NUMERO) ? Integer.toString(i+1) : 
				Character.toString((char)((int)'a'+i)));
			System.out.printf ("%s)\t%s\n", opcion,menuAdministrador[i]);
		}
	}
	
	public void gestionarMenuUsuario(){
		final String CONSULTAR = "1";
		final String ANIADIR = "2";
		final String ELIMINAR = "3";
		final String ACTUALIZAR = "4";
		final String ADMIN_BBDD = "5";
		final String SALIR = "6";
		
		boolean seguir = true;
		do {
			// Mostramos el menu principal y pedimos una operacion al usuario			
			String opcion = activar();
			String result =null;
			String[] datos;

			switch (opcion){
				case CONSULTAR : 
					String info= gestorBBDD.getUsers();
					if(info !="")
						System.out.println(info);					
					break;
	
				case ANIADIR :	
					datos = getDatosUser();
					result = gestorBBDD.addUser(datos[0],"",datos[1]);
					if(result != null)
						System.out.println(Constants.USUARIO+ result + Constants.REGISTRADO);
					break;					
	
				case ELIMINAR :
					int id = getNatural(Constants.MSG_ID_USER);
					result= gestorBBDD.deleteUser(id);
					if(result != null)
						System.out.println(Constants.USUARIO+ result + Constants.ELIMINADO);
					break;		
	
				case ACTUALIZAR : 
					id = getNatural(Constants.MSG_ID_USER);
					datos =getDatosUser(); 
					result= gestorBBDD.updateUser(id,datos);
					if(result != null )
						System.out.println(Constants.USUARIO+ result + Constants.MODIFICADO);
					break;	
					
				case ADMIN_BBDD : 
					getionarMenuAdmin();
					break;		
	
	
				case SALIR : 
					seguir = false;
					//scanner ¡.cl
					break;
			}
		} while(seguir);

	}

	private void getionarMenuAdmin() {
		final String CREAR = "1";
		final String VER_TABLAS = "2";
		final String VER_COLUMNAS = "3";
		final String MODIFICAR = "4";
		final String VOLVER = "5";
		
		boolean seguir = true;
		do {
			// Mostramos el menu principal y pedimos una operacion al usuario			
			String opcion = activarMenuAdmin();
			String result =null;
			switch (opcion){
				case CREAR : 
									
					break;
	
				case VER_TABLAS :	
					result = gestorBBDD.showTables();
					if(result!="")
						escribirResultado(result);				
					break;					
	
				case VER_COLUMNAS :
					break;		
	
				case MODIFICAR : 
					break;					
	
				case VOLVER : 
					seguir = false;
					break;
			}
		} while(seguir);

		
	}


	private String[] getDatosUser() {
		
		String[] datos = new String[2];
		datos[0] = getString(Constants.MSG_NOMBRE_USER);
		datos[1] = getString(Constants.MSG_PASS_USER);				
		return datos;
	}
	
	
	private int getNatural(String texto){		
		LecturaTeclado teclado = new LecturaTeclado();
		int entrada = teclado.leerNatural(texto);		
		return entrada;		
	}
	
	private String getString(String texto){		
		LecturaTeclado teclado = new LecturaTeclado();
		String entrada = teclado.leerString(texto);		
		return entrada;
		
	}

	private void escribirResultado(String result) {
	
		System.out.println();
		System.out.println(Constants.RESULTADO_OPERACION);
		System.out.println(result);
		
	}


}
