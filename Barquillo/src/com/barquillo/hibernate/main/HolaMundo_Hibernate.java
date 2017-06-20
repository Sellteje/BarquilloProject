package com.barquillo.hibernate.main;

import com.hibernate.interfazUsuario.Menu;


public class HolaMundo_Hibernate {


	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.gestionarMenuUsuario();	
//		GestorBBDD gestorBBDD = new GestorBBDD();
//		System.out.println(gestorBBDD.showTables());
//		System.out.println(gestorBBDD.getUsers());
//		
		System.out.println("FIN DEL PROGRAMA");
		System.exit(0);
	}
	
	
}
