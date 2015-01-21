package logica;

import visual.JLabelCoche;



/**
 *Retuilización de código de Andoni. Sin embargo se le han aplicado los cambio necesarios para la realización de nuestro juego. 
 */
public class Coche {
	
	public static final double MASA_COCHE = 1;
	public static final double FUERZA_BASE_ADELANTE = 2000;
	public static final double FUERZA_BASE_ATRAS = 1000;
	public static final double COEF_SUELO = 15.5;
	public static final double COEF_AIRE = 0.35;
	
	protected double velocidadCoche;
	protected double direccionActualCoche;
	protected double posXCoche;
	protected double posYCoche;
	protected double posXIniCoche;
	protected double posYIniCoche;
	String piloto;
	
	public Coche(){
		velocidadCoche = 0;
		direccionActualCoche = 0;
		posXCoche = 0;
		posYCoche = 0;
	}

	public double getVelocidadCoche() {
		return velocidadCoche;
	}

	public void setVelocidadCoche(double velocidadCoche) {
		this.velocidadCoche = velocidadCoche;
	}

	public double getDireccionActualCoche() {
		return direccionActualCoche;
	}

	public void setDireccionActualCoche(double direccionActualCoche) {
		if(direccionActualCoche > 360) direccionActualCoche = direccionActualCoche - 360; // Por el tema de los grados, por si se pasa de los 360
		this.direccionActualCoche = direccionActualCoche;

		
	}

	public double getPosXCoche() {
		return posXCoche;
	}

	public void setPosXCoche(double posXCoche) {
		this.posXCoche = posXCoche;
	}

	public double getPosYCoche() {
		return posYCoche;
	}

	public void setPosYCoche(double posYCoche) {
		
		this.posYCoche = posYCoche;
	}
	
	public void setPosicionCoche(double posX, double posY){
		setPosXCoche(posX);
		setPosYCoche(posY);
	}
	
	public void acelera(double aceleracion, double tiempo){
		// El coche acelerara dependiendo de la aceleracion en ese momento
		velocidadCoche = MundoBumper.calcVelocidadConAceleracion(velocidadCoche, aceleracion, tiempo);
	}
	
	public void gira(double giro){
		setDireccionActualCoche(direccionActualCoche + giro);
		if(direccionActualCoche > 360) direccionActualCoche -= 360;
		else if(direccionActualCoche < 0) direccionActualCoche += 360;
	}
	
	public void mueve(double tiempoDeMovimiento){
		// las posiciones x e y cambiaran dependiendo del tiempo y la velocidad y direccion actual
		this.posXIniCoche = posXCoche;
		this.posYIniCoche = posYCoche;
		setPosXCoche( posXCoche + MundoBumper.calcMovtoX( velocidadCoche, direccionActualCoche, tiempoDeMovimiento ) );
		setPosYCoche( posYCoche + MundoBumper.calcMovtoY( velocidadCoche, direccionActualCoche, tiempoDeMovimiento ) );
	}
	
	
		/** Devuelve la fuerza de aceleración del coche, de acuerdo al motor definido en la práctica 2
		 * @return	Fuerza de aceleración en Newtixels
		 */
		public double fuerzaAceleracionAdelante() {
			if (velocidadCoche<=-150) return FUERZA_BASE_ADELANTE;
			else if (velocidadCoche<=0) 
				return FUERZA_BASE_ADELANTE*(-velocidadCoche/150*0.5+0.5);
			else if (velocidadCoche<=250)
				return FUERZA_BASE_ADELANTE*(velocidadCoche/250*0.5+0.5);
			else if (velocidadCoche<=250)
				return FUERZA_BASE_ADELANTE*(velocidadCoche/250*0.5+0.5);
			else if (velocidadCoche<=750)
				return FUERZA_BASE_ADELANTE;
			else return FUERZA_BASE_ADELANTE*(-(velocidadCoche-1000)/250);
		}
		/** Devuelve la fuerza de aceleración hacia atrás del coche, de acuerdo al motor definido en la práctica 2
		 * @return	Fuerza de aceleración en Newtixels
		 */
		public double fuerzaAceleracionAtras() {
			if (velocidadCoche<=-350) 
				return FUERZA_BASE_ATRAS*((velocidadCoche+500)/150);
			else if (velocidadCoche<=-200) 
				return FUERZA_BASE_ATRAS;
			else if (velocidadCoche<=0)
				return FUERZA_BASE_ATRAS*(-velocidadCoche/200*0.7+0.3);
			else if (velocidadCoche<=250)
				return FUERZA_BASE_ATRAS*(velocidadCoche/250*0.55+0.3);
			else return FUERZA_BASE_ATRAS*0.85;
		}
	
	
	
	

}
