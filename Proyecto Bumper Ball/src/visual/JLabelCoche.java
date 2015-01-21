package visual;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logica.CocheJuego;

public class JLabelCoche extends JLabel {
	
	public static final int TAMANYO_COCHE = 70;
	private int[] arrayX = {10,50,60,60,50,10};
	private int []arrayY = {25,25,33,49,57,57};
	Shape [] forma = new Shape[6];
	private double gradosEnvolvente;
	private int counting = 0;

	public JLabelCoche(String rutaImagen){ // Esto lo inicializaremos a traves de CocheJuego, por lo que la ruta la especificaremos ahi
		try{
			// Tenemos que tener en cuenta que no siempre se cargara la misma imagen al JLabel, ya que tenemos el coche rojo y el coche verde
			setIcon(new ImageIcon(JLabelCoche.class.getResource(rutaImagen)));
			
		}
		catch(Exception e){
			System.err.println("Error de carga de imagen: Coche no encontrado");
			e.printStackTrace();
		}
		setBounds(0,0,TAMANYO_COCHE,TAMANYO_COCHE);
				
	}
	

	
	private double miGiro = 0;
	
	/**
	 * @param gradosGiro
	 * Método utilizado para pasar de ángulos a radianes y tener en cuenta el cambio de orientación de grados en Java. 
	 */
	public void setGiro(double gradosGiro){
		miGiro = gradosGiro * Math.PI/180; // De grados a radianes
		miGiro = -miGiro;
		gradosEnvolvente=miGiro;
		
	}
	
	/**
	 * @param path
	 * @return Siempre devuelve las envolventes. Es decir, el return de null es por el caso en el que no se genere las envolventes. Pero nunca sucederá esto
	 */
	public Shape[] prueba(Path2D.Double path){	

		int numPoints = 6;
		Polygon poly = new Polygon(arrayX, arrayY, numPoints);
		AffineTransform at = new AffineTransform();
		path = new Path2D.Double(poly);
		path = (Path2D.Double) path.createTransformedShape(at);		
		PathIterator pi = path.getPathIterator(null);
		Shape s[] = new Shape[6];
		
		double[]coords = new double[6];		
		double xFirst=0;
		double yFirst=0;
		int cont = 0;
		int tipo=0;

		while(!pi.isDone()){
		
			 tipo = pi.currentSegment(coords);
				 
			if(tipo == PathIterator.SEG_MOVETO){

				 xFirst = arrayX[counting]+5;
				 yFirst = arrayY[counting];
				 coords[0]=xFirst;
				 coords[1] = yFirst;

				counting++;
			}
			else{
				
				if(tipo==PathIterator.SEG_LINETO){


				if(counting==2){
					
					xFirst = xFirst+5;
					coords[0]= arrayX[counting];
					coords[1] = arrayY[counting];
					counting++;
				
				}
				else if((counting==3)||(counting==4)){
					
					yFirst = yFirst+5;
					coords[0]= arrayX[counting];
					coords[1] = arrayY[counting];
					counting++;
				
				}
				else if(counting==5){
					
					xFirst = xFirst-5;
					coords[0]= arrayX[counting]+5;
					coords[1] = arrayY[counting];
					counting++;
					
				}
		
				if(counting==1){
					
					
					coords[0]= arrayX[counting];
					coords[1] = arrayY[counting];
					counting++;
					
					
				}
				
				/*
				 * El problema en el qeu nos encontramos fue el hecho de que cuando dos líneas chocan, ciertos puntos son pertencientes a más de una envolvente, 
				 * por tanto decidimos poner un margen de espacio entre las envolventes ya que como he dicho el putno final de uno era el inicio de otro. 
				 * Las agrupaciones en lso if están hechas teniendo en cuenta la similaritud en los cambios. 
				 * Realizados de forma arbitrario en las envolventes. 
				 */

				Line2D.Double lineas = new Line2D.Double(xFirst, yFirst, coords[0], coords[1]);
				s[cont] = lineas;
				cont++;

				
				}
				else{
					
					xFirst = xFirst-5;
					
					coords[0]= arrayX[0];
					coords[1] = arrayY[0];
					

					Line2D.Double lineas = new Line2D.Double(xFirst, yFirst, coords[0], coords[1]);
					s[cont] = lineas;

					tipo = 45;

					counting = 0;
			
					return s;
			
				}
				
			}
			/*
			 * Es aquí donde se hace el cambio, es decir, el punto final de la anterior línea de las envolventes será el inicio + 5 pixels o no 
			 * según la situación. 
			 */
			xFirst = coords[0];
			yFirst = coords[1];
			pi.next();
		}
		
		
		return null;
	}
	

	
	/**
	 * @return el shape aplicados los grados de rotación. 
	 */
	public Shape[] getShape(){
		Shape s2[] = prueba(new Path2D.Double());
		Shape s3[] = new Shape[6];

		for(int i = 0; i < s2.length; i++){
		
			Shape lin=  s2[i];
			AffineTransform at = new AffineTransform();
			at.rotate(miGiro,35,41);
			s3[i] = at.createTransformedShape(lin);
			
					
		}
		
		return s3;
	}

	
	public double getGradosEnvolvente(){
		return this.gradosEnvolvente;
	}
	
	public void setGradosEnvolvente(double gradosEnvolvente){
		this.gradosEnvolvente = gradosEnvolvente;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		// El dibujado del coche, teniendo en cuenta las envolventes

		Image img = ((ImageIcon)getIcon()).getImage();
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		// Escalado más fino con estos 3 parámetros:
		
		Graphics2D g3 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	
	
		Shape s2[] = prueba(new Path2D.Double());
		Shape s3[] = new Shape[6];
		s3 = getShape();		

		for(int i = 0; i < s2.length; i++){
			g2.draw(s3[i]);
		}


		g2.rotate( miGiro, 35, 41 );
		g2.drawImage( img, 0, 0, TAMANYO_COCHE, TAMANYO_COCHE, null );
		
		for(int i = 0 ; i< s3.length;i++){			

			g2.draw(s2[i]);

		}
		/*
		 * Cabe destacar el hecho de que aquí el dibujado se hace del s2 ya que si lo hicieramos del s3 que es al que se le ha aplicado la rotación, 
		 * la rotación que genera un cambio en la posición de las coordenadas, visualmente se vería una doble rotación que no es lo que queremos, 
		 * por tanto aunque se ve lo que en cuanto a la lógica no cambia. Si que se ve lo que en realidad hace. 
		 */
	
	}

}
