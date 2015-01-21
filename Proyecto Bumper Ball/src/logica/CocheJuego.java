package logica;

import visual.JLabelCoche;

public class CocheJuego extends Coche { // Crearemos 2 CocheJuegos, uno verde y otro rojo
	
	private JLabelCoche graficoCoche;
	
	
	
	public CocheJuego(String rutaImagen, String identificado){ // Podemos pedir que se cargue la imagen del coche de forma interactiva
		graficoCoche = new JLabelCoche(rutaImagen);
		this.piloto = identificado;
		
		
		
	}
	
	public JLabelCoche getGrafico(){
		return graficoCoche;
	}
	
		
	@Override
	public void setPosXCoche(double posX){
		super.setPosXCoche(posX);
		graficoCoche.setLocation((int)posX, (int)posYCoche);
	}
	
	@Override
	public void setPosYCoche(double posY){
		super.setPosYCoche(posY);
		graficoCoche.setLocation((int)posXCoche, (int)posY);
	}
	
	@Override
	public void setDireccionActualCoche(double dir){
		super.setDireccionActualCoche(dir);
		graficoCoche.setGiro(direccionActualCoche);
		graficoCoche.repaint();
	}
	

}
