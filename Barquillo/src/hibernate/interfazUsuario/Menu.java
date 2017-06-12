package hibernate.interfazUsuario;

import java.util.Scanner;

import hibernate.model.GestorBBDD;

public class Menu {
	
	
	public enum Numerar {NUMERO, LETRA_ABC};
	
	private Numerar numerar = Numerar.NUMERO;
	
	private final static String[] opcionesMenu = {"Consultar Usuarios", "Añadir Usuario",
			"Eliminar Usuario", "Actualizar Usuario", "Salir"};
	
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
	
	public void gestionarMenuUsuario(){
		final String CONSULTAR = "1";
		final String ANIADIR = "2";
		final String ELIMINAR = "3";
		final String ACTUALIZAR = "4";
		final String SALIR = "5";
		
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
					result = gestorBBDD.addUser(datos[0],datos[1]);
					if(result != null)
						System.out.println("Usuario"+ result + "registrado");
					break;					
	
				case ELIMINAR :
					int id = getReferenciaUser();
					result= gestorBBDD.deleteUser(id);
					if(result != null)
						System.out.println("Usuario"+ result + "eliminado");
					break;		
	
				case ACTUALIZAR : 
					id = getReferenciaUser();
					datos =getDatosUser(); 
					result= gestorBBDD.updateUser(id,datos);
					if(result != null )
						System.out.println("Usuario"+ result + "modificado");
					break;		
	
	
				case SALIR : 
					seguir = false;
					break;
			}
		} while(seguir);

	}

	private String[] getDatosUser() {
		
		String[] datos = new String[2];
		LecturaTeclado teclado = new LecturaTeclado();
		String nombre = teclado.leerString("Introduzca el nombre del usuario: ");
		String password = teclado.leerString("Introduzca la contraseña del usuario: ");
		
		datos[0]=nombre;
		datos[1]=password;
		
		return datos;
	}
	
	private int getReferenciaUser() {
		
		LecturaTeclado teclado = new LecturaTeclado();
		int id = teclado.leerNatural("Introduzca el id del usuario: ");		
		return id;
	}


}
