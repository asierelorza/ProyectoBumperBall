package visual;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Point2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import logica.CocheJuego;
import logica.Pelota;

/** Panel principal donde va la el juego
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class PJuego extends JPanel{
	
	public Pelota pelota=new Pelota(600,375);
	public Boolean golA;
	public Boolean golB;

	
	private Rectangle I=new Rectangle(18, 248, 30, 100);
	private Rectangle D=new Rectangle(850,248,30,100);

	/** Constructor
	 * 
	 */
	public PJuego(){
		super();

	}
	
	public void creaPelota(int posX, int posY){
		pelota = new Pelota(posX, posY);
	}
	
	public Pelota getPelota(){
		return pelota;
	}
	
	public void setPelota(Pelota pelota){
		this.pelota = pelota;
	}

	@Override
	public void paintComponent(Graphics g) {
		
		// Dibujamos las porterias y la pelota, ademas del campo, a traves de este paintComponent
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;  // El Graphics realmente es Graphics2D
		// Escalado más fino con estos 3 parámetros:
		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("img/campo.jpeg"));
		g2.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), null);
		
		g2.setColor(Color.black);
		g2.fill(I);
		g2.fill(D);
		g2.setColor(Color.white);
		g2.draw(I);
		g2.draw(D);


		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		

		g2.setColor(Color.black);
		
		g2.fillOval((int)pelota.getPosX(), (int)pelota.getPosY(), 20, 20);
		
		
		
		
		
		

//		int width = this.getSize().width;
//		int height = this.getSize().height;
//		
//		ImageIcon imagenFondo = new ImageIcon(getClass().getResource("imagenes/Campo.png"));
//		g.drawImage(imagenFondo.getImage(),0,0,width, height, null);
//		setOpaque(false);
//		pel.paraelpaint(g);
	
//		
	
	}
	
	public void logicaGol(){
		if(I.contains(pelota.getPosX()+20, pelota.getPosY()+20)){
//			System.out.println("bordes porteria izquierda: " + I.getBounds());
//			System.out.println("Pelota: " + pelota.getPosX() + " | " + pelota.getPosY());
			golA = true;
			
		}
		else if(D.contains(pelota.getPosX()+20, pelota.getPosY()+20)){
//			System.out.println("bordes porteria derecha: " + D.getBounds());
//			System.out.println("Pelota: " + pelota.getPosX() + " | " + pelota.getPosY());
			golB = true;
			
		}
		else{
			golA = false;
			golB = false;
		}
		
		
		
		
	}
//	public boolean logicaGol(){
//		for(pelota.get)
//	}
}

