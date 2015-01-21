package logica;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.JLabel;

/** Logica de la pelota.
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class Pelota{ 
	
	// Parametros para controlas las fisicas de la pelota
	private double velocidadPelota;
	private double direccionActualPelota;
	private double posXPelota;
	private double posYPelota;
	public static int MASA_PELOTA = 1;
	public static int RADIO_PELOTA = 10;
	public static final double COEF_SUELO = 10;
	public static final double COEF_AIRE = 0.35;
	
	/** Constructor
	 * @param posX Posicion X de la pelota
	 * @param posY Posicion Y de la pelota
	 */
	public Pelota(int posX, int posY){
		velocidadPelota = 0;
		direccionActualPelota = 0;
		this.posXPelota = posX;
		this.posYPelota = posY;
		
		
	}

	public double getPosX() {
		
		return posXPelota;
	}

	public double getPosY() {
		
		return posYPelota;
	}

	public double getVelocidadPelota() {
		return velocidadPelota;
	}

	public void setVelocidadPelota(double velocidadPelota) {
		this.velocidadPelota = velocidadPelota;
	}

	public double getDireccionActualPelota() {
		return direccionActualPelota;
	}

	public void setDireccionActualPelota(double direccionActualPelota) {
		this.direccionActualPelota = direccionActualPelota;
	}

	public void setLocation(double posX, double posY) {
		this.posXPelota = posX;
		this.posYPelota = posY;
	}
			
	
	/** Cambiara la posicion de la pelota dependiendo del choque producido en la pared o en el segmento del coche
	 * @param tiempoDeMovimiento Tiempo en el que se ha de mover la pelota
	 */
	public void mueve(double tiempoDeMovimiento){
		
		setLocation(posXPelota + MundoBumper.calcMovtoX(velocidadPelota, direccionActualPelota, tiempoDeMovimiento), posYPelota + MundoBumper.calcMovtoY(velocidadPelota, direccionActualPelota, tiempoDeMovimiento));
				
	}
	
	/** Calculara la velocidad de la pelota dependiendo de la aceleracion impuesta por el coche y teniendo en cuenta la fuerza de rozamiento
	 * @param aceleracion Aceleracion asignada a la pelota
	 * @param tiempo Tiempo para el calcula de la aceleracion
	 */
	public void acelera(double aceleracion, double tiempo){
		velocidadPelota = MundoBumper.calcVelocidadConAceleracion(velocidadPelota, aceleracion, tiempo);
		//VelocidadPelota = calculo de la nueva velocidad dependiendo de la aceleracion (tanto por parte del coche como la fuerza de rozamiento y el tiempo)
	}
	
		
}
