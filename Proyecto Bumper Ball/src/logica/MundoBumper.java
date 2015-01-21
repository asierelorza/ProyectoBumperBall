package logica;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import javax.swing.JPanel;

import visual.PJuego;




/**Clase donde se gestiona toda la logica del juego
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class MundoBumper {
	
	private JPanel panel; // Panel a gestionar
	
	
	private CocheJuego cocheRojo;
	private CocheJuego cocheVerde;
	private Point2D.Double puntoChoque; // Logica de los dos coches y el punto choque de los dos coches
	
	private int linearojoindex;
	private int lineaverdeindex;
	private int lineaCocheIndex; // Indices para saber que envolvente ha chocado
	
	private Line2D.Double linearojo;
	private Line2D.Double lineaverde;
	private Line2D.Double lineaCoche; // La envolvente que ha chocado
	
	
	/**Constructor
	 * @param panel a gestionar. En nuestro caso, le pasamos el panel PJuego
	 */
	public MundoBumper(JPanel panel){
		this.panel = panel;
	}
	
	/** Creacion del coche Rojo
	 * @param posX posicion X del coche rojo
	 * @param posY posicion Y del coche rojo
	 */
	public void creaCocheRojo(int posX, int posY){
		
		cocheRojo = new CocheJuego("img/Coche Rojo.png", "Rojo");
		
		cocheRojo.setPosicionCoche(posX, posY);
		panel.add(cocheRojo.getGrafico());
		cocheRojo.getGrafico().repaint();
	}
	
	/**Creacion del coche verde
	 * @param posX posicion X del coche verde
	 * @param posY posicion Y del coche verde
	 */
	public void creaCocheVerde(int posX, int posY){
		
		cocheVerde = new CocheJuego("img/Coche Verde.png", "Verde");
		cocheVerde.setPosicionCoche(posX, posY);
		// Ponemos el coche verde en sentido contrario
		cocheVerde.setDireccionActualCoche(180);
		panel.add(cocheVerde.getGrafico());
		cocheVerde.getGrafico().repaint();
	}
	
	
	
	public CocheJuego getCocheRojo(){
		return this.cocheRojo;
	}
	
	public CocheJuego getCocheVerde(){
		return this.cocheVerde;
	}
	
	
	
		/**Caso en el que los coches chocan entre si
		 * @param cocheRojo Coche rojo
		 * @param cocheVerde Coche verde
		 * @return true si han chocado, false si no
		 */
		public boolean chocaCocheConCoche(CocheJuego cocheRojo, CocheJuego cocheVerde){
			
			// Recuperamos las envolventes de cada coche en forma de Shape
			Shape sRojo[] = cocheRojo.getGrafico().getShape();
			Shape sVerde[] = cocheVerde.getGrafico().getShape();
			// Shapes ajustados
			Shape sRojo1 [] = new Shape[6];
			Shape sVerde1 [] = new Shape[6];
			
			// Ajuste de los shapes en funcion de la posicion del coche en la ventana
			for(int i = 0; i<sRojo.length;i++){
				
			sRojo1[i] = new Line2D.Double(sRojo[i].getBounds2D().getX(), sRojo[i].getBounds2D().getY(), sRojo[i].getBounds2D().getWidth()+sRojo[i].getBounds2D().getX(), sRojo[i].getBounds2D().getY()+sRojo[i].getBounds2D().getHeight());
			sVerde1[i]= new Line2D.Double(sVerde[i].getBounds2D().getX(), sVerde[i].getBounds2D().getY(), sVerde[i].getBounds2D().getWidth()+sVerde[i].getBounds2D().getX(), sVerde[i].getBounds2D().getY()+sVerde[i].getBounds2D().getHeight());

				
			}

			// Doble for para recorrer todas las envolventes y verificar si se ha producido choque o no
			for(int i = 0; i<sRojo1.length; i++){
				for(int j = 0; j < sVerde1.length; j++){
					
					
					Line2D.Double lineaRojo = (Line2D.Double)sRojo1[i];
					// Ajuste de la linea en funcion de la posicion del coche rojo en la ventana
					Line2D.Double lineaRojoAjust = new Line2D.Double(lineaRojo.x1+cocheRojo.getPosXCoche(), lineaRojo.y1+cocheRojo.getPosYCoche(), lineaRojo.x2+cocheRojo.getPosXCoche(), lineaRojo.y2+cocheRojo.getPosYCoche());
					
					Line2D.Double lineaVerde = (Line2D.Double)sVerde1[j];
					// Ajuste de la linea en funcion de la posicion del coche verde en la ventana
					Line2D.Double lineaVerdeAjust = new Line2D.Double(lineaVerde.x1+cocheVerde.getPosXCoche(), lineaVerde.y1+cocheVerde.getPosYCoche(), lineaVerde.x2+cocheVerde.getPosXCoche(), lineaVerde.y2+cocheVerde.getPosYCoche());
					
					// Caso en la cual se ha producido choque
					if(lineaRojoAjust.intersectsLine(lineaVerdeAjust)){
					
						// Obtenemos el punto de choque
						puntoChoque = intersection(lineaRojo.x1+cocheRojo.getPosXCoche(), lineaRojo.y1+cocheRojo.getPosYCoche(), lineaRojo.x2+cocheRojo.getPosXCoche(), lineaRojo.y2+cocheRojo.getPosYCoche(), lineaVerde.x1+cocheVerde.getPosXCoche(), lineaVerde.y1+cocheVerde.getPosYCoche(), lineaVerde.x2+cocheVerde.getPosXCoche(), lineaVerde.y2+cocheVerde.getPosYCoche());
						
						// Obtenemos el indice y las envolventes que han chocado
						linearojoindex = i;
						lineaverdeindex=j;
						linearojo = new Line2D.Double(lineaRojo.x1+cocheRojo.getPosXCoche(), lineaRojo.y1+cocheRojo.getPosYCoche(), lineaRojo.x2+cocheRojo.getPosXCoche(), lineaRojo.y2+cocheRojo.getPosYCoche());
						lineaverde  = new Line2D.Double(lineaVerde.x1+cocheVerde.getPosXCoche(), lineaVerde.y1+cocheVerde.getPosYCoche(), lineaVerde.x2+cocheVerde.getPosXCoche(), lineaVerde.y2+cocheVerde.getPosYCoche());
						return true;
						
					}				
					
				}
			}
				
			

			return false;
			
		}
		
		/**Caso en el que el coche choca con la pelota
		 * @param coche Coche
		 * @param pelota Pelota
		 * @return true si ha chocado, false si no
		 */
		public boolean chocaCocheConPelota(CocheJuego coche, Pelota pelota){
			
			// Hacemos el mismo ajuste que en el anterior metodo
			Shape sCoche[] = coche.getGrafico().getShape();

			Shape sCoche1 [] = new Shape[6];

			for(int i = 0; i<sCoche.length;i++){
				
			sCoche1[i] = new Line2D.Double(sCoche[i].getBounds2D().getX(), sCoche[i].getBounds2D().getY(), sCoche[i].getBounds2D().getWidth()+sCoche[i].getBounds2D().getX(), sCoche[i].getBounds2D().getY()+sCoche[i].getBounds2D().getHeight());

			}

			// Recorremos las envolventes para saber si se ha producido choque y obtener las envolventes
			for(int i = 0; i<sCoche1.length; i++){
									
					
					Line2D.Double lineaCoche = (Line2D.Double)sCoche1[i];
					Line2D.Double lineaCocheAjust = new Line2D.Double(lineaCoche.x1+coche.getPosXCoche(), lineaCoche.y1+coche.getPosYCoche(), lineaCoche.x2+coche.getPosXCoche(), lineaCoche.y2+coche.getPosYCoche());
					
					// Vemos si se ha chocado con la pelota
					if(lineaCocheAjust.intersects(pelota.getPosX(), pelota.getPosY(), 20, 20)){
										
						lineaCocheIndex = i;
							
						this.lineaCoche = new Line2D.Double(lineaCoche.x1+coche.getPosXCoche(), lineaCoche.y1+coche.getPosYCoche(), lineaCoche.x2+coche.getPosXCoche(), lineaCoche.y2+coche.getPosYCoche());

						return true;
						
					}				
					
				
			}
				
			return false;
		}
			

		
		/**Formula obtenida para saber el punto donde se ha producido el choque
		 * @param x1 
		 * @param y1
		 * @param x2
		 * @param y2
		 * @param x3
		 * @param y3
		 * @param x4
		 * @param y4
		 * Los parametros son los puntos iniciales y finales de ambas lineas
		 * @return El punto originario del choque si se ha producido; el punto (-200,-200) si no se ha producido
		 */
		public Point2D.Double intersection(double x1,double y1,double x2,double y2, double x3, double y3, double x4,double y4) {
			double d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
			    if 
			    (d == 0) {return new Point2D.Double(-200,-200);
			    }
			    else{
			    
			    double xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
			    double yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
			   
			   return new Point2D.Double(xi,yi);
			    }
			  }
		
		/**Metodo de las aproximaciones sucesivas para llevar el coche hasta el punto del choque
		 * @param cocheRojo coche rojo
		 * @param cocheVerde coche verde
		 * @param posIniRojo posicion inicial rojo
		 * @param posFinRojo posicion final rojo
		 * @param posIniVerde posicion inicial verde
		 * @param posFinVerde posicion final verde
		 * @param numRecs numero de llamadas para obtener el punto exacto
		 */
		public void dondeChoca(CocheJuego cocheRojo, CocheJuego cocheVerde, Point2D.Double posIniRojo, Point2D.Double posFinRojo, Point2D.Double posIniVerde, Point2D.Double posFinVerde, int numRecs){


			if(numRecs > 0){
				
				// Llevamos los coches hasta el punto obtenido
				cocheRojo.posXIniCoche = cocheRojo.posXCoche;
				cocheRojo.posYIniCoche = cocheRojo.posYCoche;
				cocheVerde.posXIniCoche = cocheVerde.posXCoche;
				cocheVerde.posXIniCoche = cocheVerde.posXCoche;
				
				// Dividimos entre 2 las posiciones (llevamos atras)
				cocheRojo.posXCoche = (posIniRojo.x + posFinRojo.x)/2;
				cocheRojo.posYCoche = (posIniRojo.y + posFinRojo.y)/2;
				cocheVerde.posXCoche = (posIniVerde.x + posFinVerde.x)/2;
				cocheVerde.posYCoche = (posIniVerde.y + posFinVerde.y)/2;
				
				if(chocaCocheConCoche(cocheRojo, cocheVerde)){ // Hay choque - mirar antes
					dondeChoca(cocheRojo, cocheVerde, posIniRojo, new Point2D.Double(cocheRojo.posXCoche, cocheRojo.posYCoche), posIniVerde, new Point2D.Double(cocheVerde.posXCoche, cocheVerde.posYCoche), numRecs-1);
					
				}
				else{ // No hay choque - mirar despues
					dondeChoca(cocheRojo, cocheVerde, new Point2D.Double(cocheRojo.posXCoche, cocheRojo.posYCoche), posFinRojo, new Point2D.Double(cocheVerde.posXCoche, cocheVerde.posYCoche), posFinVerde, numRecs-1);
				}
				
				
				
			}
			else{
				chocaCocheConCoche(cocheRojo, cocheVerde);

				
			}
					
			
		}
		
		/**Rebote del los coches
		 * 
		 */
		public void rebota(){
			
			dondeChoca(cocheRojo, cocheVerde, new Point2D.Double(cocheRojo.posXIniCoche, cocheRojo.posYIniCoche), new Point2D.Double(cocheRojo.posXCoche, cocheRojo.posYCoche), new Point2D.Double(cocheVerde.posXIniCoche, cocheVerde.posYIniCoche), new Point2D.Double(cocheVerde.posXCoche, cocheVerde.posYCoche), 10);

			// Cogemos las envolventes de cada coche y sus puntos iniciales y finales			
			Line2D.Double lineaRojo = envolventeChoqueRojo();
			Point2D.Double puntoIniR = new Point2D.Double(lineaRojo.x2, lineaRojo.y2);
			Point2D.Double puntoFinR = new Point2D.Double(lineaRojo.x1, lineaRojo.y1);
			Line2D.Double lineaVerde = envolventeChoqueVerde();
			Point2D.Double puntoIniV = new Point2D.Double(lineaVerde.x2, lineaVerde.y2);
			Point2D.Double puntoFinV = new Point2D.Double(lineaVerde.x1, lineaVerde.y1);
			// Sacamos la pendiente de ambas lineas
			double pendienteRojo = pendienteRecta(puntoIniR, puntoFinR);
			double pendienteVerde = pendienteRecta(puntoIniV, puntoFinV);
			
			// Caso en el cual alguna de las dos pendientes isNaN o is infinity. En este caso, el coche no rebota
			if((pendienteRojo == 200)||(pendienteRojo==-200)||(pendienteVerde==200)||(pendienteVerde==-200)){
				
			}
			
			else{
				
				// Sacamos la pendiente de la normal de la envolvente
				double pendientenormalrojo;
				double pendientenormalverde;
				
				 pendientenormalrojo= -1/pendienteRojo;
				 pendientenormalverde = -1/pendienteVerde;
				
				 	// Sacamos la tangente de ambas lineas
				 	double  tgincidenciaVerde= (pendientenormalrojo-pendienteVerde)/(1+(pendientenormalrojo*pendienteVerde));
					double tgincidenciaRojo = (pendientenormalverde-pendienteRojo)/(1+(pendientenormalverde*pendienteRojo));
					// Calculamos el angulo de incidencia de ambos coches
					double anguloincidenciaRojo =  Math.atan(tgincidenciaRojo);
					double anguloincidenciaVerde = Math.atan(tgincidenciaVerde);
					// Calculamos la nueva direccion de los coches
					double direccionNewRojo = (Math.toDegrees(anguloincidenciaRojo));
					double direccionNewVerde = (Math.toDegrees(anguloincidenciaVerde));
					// Aplicamos la direccion nueva
					cocheRojo.setDireccionActualCoche(direccionNewRojo);
					cocheVerde.setDireccionActualCoche(direccionNewVerde);
					// Movemos el coche en funcion del tiempo de choque
					cocheRojo.mueve(0.004-(0.004*(tiempoRebote(new Point2D.Double(cocheRojo.posXIniCoche, cocheRojo.posYIniCoche), new Point2D.Double(cocheRojo.posXCoche, cocheRojo.posYCoche)))));
					cocheVerde.mueve(0.004-(0.004*(tiempoRebote(new Point2D.Double(cocheVerde.posXIniCoche, cocheVerde.posYIniCoche), new Point2D.Double(cocheVerde.posXCoche, cocheVerde.posYCoche)))));
										
				}			
		}
		
		/**Rebote de la pelota
		 * @param coche coche
		 * @param pelota pelota
		 */
		public void rebotaPelota(CocheJuego coche, Pelota pelota){
			
			// Ponemos la pelota con la misma direccion del coche
			pelota.setDireccionActualPelota(coche.getDireccionActualCoche());
			// Si la velocidad del coche es pequeña y la pelota esta quieta, la velocidad de la pelota sera un poco mayor para que no se trabe con el coche
			if(coche.getVelocidadCoche() >= 0 && coche.getVelocidadCoche() <= 100){
				if(pelota.getVelocidadPelota() == 0){
					pelota.setVelocidadPelota(coche.getVelocidadCoche()*1.5);
				}
				
			}
			// Velocidad "estandar" de la pelota
			else{
				pelota.setVelocidadPelota(coche.getVelocidadCoche()*1.2);

			}
			
		}
		
		
		
		/**Metodo para obtener el tiempo en el que se ha movido el coche
		 * @param inicio punto inicial
		 * @param fin punto final
		 * @return tiempo en el que se ha movido el coche
		 */
		public double tiempoRebote(Point2D.Double inicio, Point2D.Double fin){
			
			double porctiempo =Math.abs( (puntoChoque().x- inicio.x)/(fin.x-inicio.x));
			double tiempo = 0.004*(1-porctiempo);
			
			/*
			 * 
			 * La cuestión es que a veces los valores que  nos da son muy grandes y además en negativo, 
			 * Eso sucede princpalmente porque la posición del jlabel es la de la punta de arriba a la izquierda, entonces claro, 
			 * si puede darse el caso en el que la posición del jlabel y la del choque haya una diferencia realmente considerable. Eso es lo que pasa cuando el coche desaparece. 
			 * Cual es la solución en tal caso? No lo sé. 
			 */
			if((fin.x-inicio.x==0)||(puntoChoque().x-inicio.x==0)){
				
				porctiempo = Math.abs((puntoChoque().y- inicio.y)/(fin.x-inicio.y));
				tiempo = 0.004*(1-porctiempo);
				
			}
			
			if((tiempo<-50)||(tiempo>50)){
				
				tiempo= 0.004*0.25;
			}
			
			return tiempo;
		}
		
		/**Calculo de la pendiente de la recta
		 * @param puntoIni punto inicial
		 * @param puntoFin punto final
		 * @return Si es NaN o Infinity, devuelve -200 o 200; si no, devuelve la pendiente correspondiente
		 */
		public double pendienteRecta(Point2D.Double puntoIni, Point2D.Double puntoFin){
			
		
			double finx;
			double finy;
			double yes = (puntoFin.y - puntoIni.y);
			double xes = (puntoFin.x - puntoIni.x);
			double malda;
			
			if(yes ==0){
		

				return -200;
			}
			else if(xes == 0){

				
				return 200;

			}
			
			else{
				
				return yes/xes;
			}
			

		}
		
		public Line2D.Double envolventeChoqueRojo(){
			
			return linearojo;
		}
		
		public Line2D.Double envolventeChoqueVerde(){
			
			return lineaverde;
		}
		
		public Line2D.Double envolventeChoqueCoche(){
			return lineaCoche;
		}
		
		public Point2D.Double puntoChoque(){
			
			return puntoChoque;
		}
		

		/** Calcula y devuelve la posición X de un movimiento
		 * @param vel    	Velocidad del movimiento (en píxels por segundo)
		 * @param dir    	Dirección del movimiento en grados (0º = eje OX positivo. Sentido antihorario)
		 * @param tiempo	Tiempo del movimiento (en segundos)
		 * @return Posicion X de un movimiento
		 */
		public static double calcMovtoX( double vel, double dir, double tiempo ) {
			return vel * Math.cos(dir/180.0*Math.PI) * tiempo;
		}
		
		/** Calcula y devuelve la posición Y de un movimiento
		 * @param vel    	Velocidad del movimiento (en píxels por segundo)
		 * @param dir    	Dirección del movimiento en grados (0º = eje OX positivo. Sentido antihorario)
		 * @param tiempo	Tiempo del movimiento (en segundos)
		 * @return Posicion Y de un movimiento
		 */
		public static double calcMovtoY( double vel, double dir, double tiempo ) {
			return vel * -Math.sin(dir/180.0*Math.PI) * tiempo;
		
		}

		/** Calcula el cambio de velocidad en función de la aceleración
		 * @param vel		Velocidad original
		 * @param acel		Aceleración aplicada (puede ser negativa) en pixels/sg2
		 * @param tiempo	Tiempo transcurrido en segundos
		 * @return	Nueva velocidad
		 */
		public static double calcVelocidadConAceleracion( double vel, double acel, double tiempo ) {
			return vel + (acel*tiempo);
		}
		
		
		/** Calcula la fuerza de rozamiento que sufre un objeto moviéndose
		 * @param masa
		 * @param coefRozSuelo
		 * @param coefRozAire
		 * @param vel
		 * @return fuerza de rozamiento que sufre un objeto moviendose
		 */
		public static double calcFuerzaRozamiento( double masa, double coefRozSuelo, double coefRozAire, double vel ) {
			double fuerzaRozamientoSuelo = masa * coefRozSuelo * ((vel>0)?(-1):1);  // En contra del movimiento
			double fuerzaRozamientoAire = coefRozAire * (-vel);  // En contra del movimiento
			return fuerzaRozamientoAire + fuerzaRozamientoSuelo;
		}
		
		/** Calcula la aceleración de un objeto dada una fuerza y una masa
		 * @param fuerza	Fuerza aplicada al objeto (en Newton_pixels = Kg*pixels/sg2)
		 * @param masa	Masa del objeto
		 * @return	Aceleración aplicada al objeto (en pixels/sg2)
		 */
		public static double calcAceleracionConFuerza( double fuerza, double masa ) {
			
			return fuerza/masa;
		}
		
		/** Calcula si hay choque en horizontal con los limites del campo
		 * @param coche Coche
		 * @return true si hay choque horizontal, false si no lo hay
		 */
		public boolean hayChoqueHorizontal( CocheJuego coche ) {
			return (coche.getPosXCoche() < 0 
					|| coche.getPosXCoche()>panel.getWidth()-visual.JLabelCoche.TAMANYO_COCHE );
		}
		
		/** Calcula si hay choque en vertical con los límites del mundo
		 * @param coche	Coche cuyo choque se comprueba con su posición actual
		 * @return	true si hay choque vertical, false si no lo hay
		 */
		public boolean hayChoqueVertical( CocheJuego coche ) {
			return (coche.getPosYCoche() <  0 
					|| coche.getPosYCoche()>panel.getHeight());
		}
		
		/** Hacemos lo mismo con la pelota
		 * @param pelota
		 * @return true si hay choque vertical, false si no lo hay
		 */
		public boolean hayChoqueVerticalPelota(Pelota pelota){
			return(pelota.getPosY() < 0 || pelota.getPosY() > panel.getHeight()-20);
		}
		
		/** Hacemos lo mismo con la pelota
		 * @param pelota
		 * @return true si hay choque horizontal, false si no lo hay
		 */
		public boolean hayChoqueHorizontalPelota(Pelota pelota){
			return(pelota.getPosX() + Pelota.RADIO_PELOTA < 0 || pelota.getPosX() > panel.getWidth() - 20);
		}

		/** Realiza un rebote en horizontal del objeto de juego indicado
		 * @param coche	Objeto que rebota en horizontal
		 */
		public void rebotaHorizontal( CocheJuego coche ) {
			
			// Poner el coche donde rebota exactamente
			dondeRebotaRec( coche, coche.posXCoche, coche.posYCoche, coche.posXIniCoche, coche.posYIniCoche, 10 );

			double dir = coche.getDireccionActualCoche();
			dir = 180+dir;   // Rebote espejo sobre OY (complementario de 180)
			if (dir > 360) dir = dir-360;
			// Corrección para mantenerlo en [0,360)
			
			coche.setDireccionActualCoche( dir );
		}
		
		/** Realiza un rebote en vertical del objeto de juego indicado
		 * @param coche	Objeto que rebota en vertical
		 */
		public void rebotaVertical( CocheJuego coche ) {
						
			// Poner el coche donde rebota exactamente
			dondeRebotaRec( coche, coche.posXCoche, coche.posYCoche, coche.posXIniCoche, coche.posYIniCoche, 10 );
				
			double dir = coche.getDireccionActualCoche();
			dir = dir+180;

			if(dir>360){
			dir = 360 - dir;  // Rebote espejo sobre OX (complementario de 360)
			}
			coche.setDireccionActualCoche( dir );
		
		}
		
		/** Rebote horizontal de la pelota con los limites del campo
		 * @param pelota
		 */
		public void rebotaHorizontalPelota(Pelota pelota){
			double dir = pelota.getDireccionActualPelota();
			dir = 180-dir;   // Rebote espejo sobre OY (complementario de 180)
			if (dir < 0) dir = 360+dir;  // Corrección para mantenerlo en [0,360)
			pelota.setDireccionActualPelota( dir );
		}
		
		/** Rebote vertical de la pelota con los limites del campo
		 * @param pelota
		 */
		public void rebotaVerticalPelota(Pelota pelota){
			double dir = pelota.getDireccionActualPelota();
			dir = 360 - dir;  // Rebote espejo sobre OX (complementario de 360)
			pelota.setDireccionActualPelota( dir );
		}
		
			
			
			/** Metodo para hacer el rebote en los limites del campo. 
			 * @param c Coche
			 * @param xIni
			 * @param yIni
			 * @param xFin
			 * @param yFin
			 * Posicion inicial y final del coche
			 * @param numRecs numero de llamadas
			 */
			private void dondeRebotaRec( CocheJuego c, double xIni, double yIni, double xFin, double yFin, int numRecs ) {
						
				if (numRecs>0) {  // == 0 caso base
					c.posXCoche = (xIni+xFin)/2;
					c.posYCoche = (yIni+yFin)/2;
		
					if (hayChoqueHorizontal(c) || hayChoqueVertical(c)) {  // En el medio ya choca  - mirar antes
						dondeRebotaRec(c, xIni, yIni, c.posXCoche, c.posYCoche, numRecs-1);
					} else {  // En el medio no choca - mirar después
					
						dondeRebotaRec(c, c.posXCoche, c.posYCoche, xFin, yFin, numRecs-1);
					}
				}
				
				
				// Esto es porque en el limite inferior del campo a veces no ocurria el rebote correctamente
				if(c.getPosYCoche()>panel.getHeight()+60){
					
					c.setPosYCoche(panel.getHeight()-10);
					
				}
				
				
			}
		
		
		/** Aplica la fuerza a un coche, produciéndose una aceleración y un movimiento acorde.
		 * Se tienen en cuenta las fuerzas de rozamiento.
		 * @param fuerza	Fuerza aplicada en la dirección del movimiento, en Newtixels (negativo = sentido contrario)
		 * @param coche	Coche al que se aplica la fuerza
		 */
		public static void aplicarFuerza( double fuerza, Coche coche ) {
			double fuerzaRozamiento = calcFuerzaRozamiento( Coche.MASA_COCHE ,
					Coche.COEF_SUELO, Coche.COEF_AIRE, coche.getVelocidadCoche() );
			double aceleracion = calcAceleracionConFuerza( fuerza+fuerzaRozamiento, Coche.MASA_COCHE );
			if (fuerza==0) {
				// No hay fuerza, solo se aplica el rozamiento
				double velAntigua = coche.getVelocidadCoche();
				coche.acelera( aceleracion, 0.04 );
				if (velAntigua>=0 && coche.getVelocidadCoche()<0
					|| velAntigua<=0 && coche.getVelocidadCoche()>0) {
					coche.setVelocidadCoche(0);  // Si se está frenando, se para (no anda al revés)
				}
			} else {
				coche.acelera( aceleracion, 0.04 );
			}
			
		}
		
		/** Aplicamos la fuerza a la pelota
		 * @param fuerza
		 * @param pelota
		 */
		public static void aplicarFuerzaPelota(double fuerza, Pelota pelota){
			double fuerzaRozamiento = calcFuerzaRozamiento(Pelota.MASA_PELOTA, Pelota.COEF_SUELO, Pelota.COEF_AIRE, pelota.getVelocidadPelota());
			double aceleracion = calcAceleracionConFuerza(fuerza + fuerzaRozamiento, Pelota.MASA_PELOTA);
			
			double velAntigua = pelota.getVelocidadPelota();
			pelota.acelera(aceleracion, 0.04);
			if(velAntigua>= 0 && pelota.getVelocidadPelota() < 0 || velAntigua <= 0 && pelota.getVelocidadPelota() > 0){
				pelota.setVelocidadPelota(0);
			}
			
			
		}


}
