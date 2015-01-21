package logica;

import java.util.ArrayList;

import visual.VentanaEstadisticas;

/**
 * Clase utilizada para la impresión de la tabla, 
 *
 */
public class Partida {

	String usulocal;
	String usuvisitante;
	int  local;
	int visitante;
	String sentencia;
	
	/**
	 * @param local
	 * @param visitante
	 * @param usulocal
	 * @param usuvisitante
	 * 
	 * Aquí se asignan los valores. 
	 */
	public Partida(int local, int visitante, String usulocal, String usuvisitante){
	
	this.local= local;
	this.visitante = visitante;
	this.usulocal = usulocal;
	this.usuvisitante = usuvisitante;
		
	sentencia = local+ " - "+ visitante;
	
	}

	
	public String devolver(){
		
		return sentencia;
	}
	
	public String localdev(){
		return usulocal;
	}
	
	public String visitdev(){
		
		return usuvisitante;
	}
	

}
