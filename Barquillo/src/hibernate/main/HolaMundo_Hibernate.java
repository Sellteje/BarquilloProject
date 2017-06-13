package hibernate.main;

import hibernate.interfazUsuario.Menu;

public class HolaMundo_Hibernate {


	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.gestionarMenuUsuario();	
		
		System.out.println("FIN DEL PROGRAMA");
		System.exit(0);
	}
	
	
}
