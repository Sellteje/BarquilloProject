package main;


import model.jdbc.BBDDConection;

public class HolaMundo {
	
	

	public static void main(String[] args) {
		System.out.println("Inicio Programa");
		
		BBDDConection conexion = new BBDDConection();

		System.out.println(conexion.mostrarTablaUsuarios());
			

		System.out.println("Fin Programa");
		
	}
	

	
	

}
