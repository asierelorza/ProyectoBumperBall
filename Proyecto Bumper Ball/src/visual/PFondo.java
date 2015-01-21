package visual;
	
	import javax.swing.ImageIcon;
	import java.awt.image.*;
	import javax.swing.JComponent;
	import javax.swing.JPanel;

	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
import java.awt.Image;
import java.util.*;
	 
	/** Panel de la pantalla de carga
	 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
	 *
	 */
	public class PFondo extends JPanel{	
		
		public void paintComponent(Graphics g) {
									
			super.paintComponents(g);
			int width = this.getSize().width;
			int height = this.getSize().height;
			
			ImageIcon imagenFondo = new ImageIcon(getClass().getResource("img/choque_de_coches.jpg"));
			g.drawImage(imagenFondo.getImage(),0,0,width, height, null);
			setOpaque(false);
			super.paintComponent(g);
			
		}	 
	}
		