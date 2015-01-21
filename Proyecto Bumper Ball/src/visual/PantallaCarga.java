package visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.print.DocFlavor.URL;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.border.EtchedBorder;


	/** La pantalla de carga, el main del proyecto
	 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
	 *
	 */
	public class PantallaCarga extends JWindow {

	    private JProgressBar Barra = new JProgressBar();
	    private static PantallaCarga PCarga;
	    private int cont;
	    private Timer temp;


	    public PantallaCarga() {
	    	
	        Container container = getContentPane();
	       
	        container.setLayout(null);
	      
	        PFondo fondo = new PFondo();
	        fondo.setBorder(new EtchedBorder());	        
	       
	        fondo.repaint();
	        fondo.setSize(600, 400);
	        fondo.setLayout(null);
	        container.add(fondo);

	        Barra.setMaximum(50);
	        Barra.setBounds(55, 180, 250, 15);
	        container.add(Barra);
	        Barra.setForeground(Color.RED);
	        loadBar();
	        Barra.setSize(400, 25);
	        Barra.setLocation(105, 350);
	        setVisible(true);
	    }
	    public void loadBar() {
	      int in = 70;
	      ActionListener pr = new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                cont++;
	                Barra.setValue(cont);
	                if (cont == 50) {
	                    temp.stop();
	                    PCarga.setVisible(false);
	                    VentanaMenu m = new VentanaMenu();
	                    return;
	                }
	            }
	        };
	        temp = new Timer(in, pr);
	        temp.start();
	    }
	    public static void main(String[] args) {
	    	PCarga = new PantallaCarga();   
	    	PCarga.setSize(600, 400);
	    	PCarga.setLocationRelativeTo(null);
	    	PCarga.setVisible(true);
	        }
		}
	


