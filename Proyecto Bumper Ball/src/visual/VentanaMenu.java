package visual;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import logica.Pelota;

/**Ventana en la cual el usuario podra elegir si jugar, ver estadisticas o salir
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class VentanaMenu extends JFrame {

	// Componentes para la creacion de la ventana
	private JLabel lWelcome;
	private JLabel lChoose;
	private PanelImagen pImagen;
	

	/**
	 * Create the application.
	 */
	public VentanaMenu() {
		
		initialize();
				
	}

	/**
	 * Initialize the contents of the frame.
	 */
	/**
	 * 
	 */
	private void initialize() {
		
		pImagen = new PanelImagen();
		getContentPane().add(pImagen);
		pImagen.repaint();
		setVisible(true);
		
		pImagen.setLayout(null);
		
		setTitle("Bumper Ball - Menu");
		setBounds(100, 100, 550, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		lWelcome = new JLabel();
		lWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lWelcome.setText("Bienvenido al juego de Bumper Ball!");
		lWelcome.setFont( new Font( Font.SERIF, Font.BOLD, 18 ));
		lWelcome.setBounds(130, 11, 300, 30);
		pImagen.add(lWelcome);
				
		lChoose = new JLabel();
		lChoose.setHorizontalAlignment(SwingConstants.CENTER);
		lChoose.setText("Escoja una opcion:");
		lChoose.setFont( new Font( Font.SERIF, Font.BOLD, 18 ));
		lChoose.setBounds(185, 42, 168, 30);
		pImagen.add(lChoose);
		
		JButton bJug = new JButton("Jugar");
		bJug.setBounds(210, 120, 120, 30);
		pImagen.add(bJug);
		
		JButton bEst = new JButton("Estadisticas");
		bEst.setBounds(210, 185, 120, 30);
		pImagen.add(bEst);
		
		JButton bSalir = new JButton(new ImageIcon(getClass().getResource("img/exit.png")));
		bSalir.setBounds(257, 270, 30, 30);
		pImagen.add(bSalir);
		
		// Listeners de los botones		
		bJug.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// Pide los nombres de usuario y arranca el juego
				setVisible(false);
				String nomJug1 = JOptionPane.showInputDialog("Inserta el nombre del jugador 1:");
				String nomJug2 = JOptionPane.showInputDialog("Inserta el nombre del jugador 2:");
				VentanaPrincipal.empieza(2,00,0,0,0,0,nomJug1,nomJug2);
			}
		});
		bEst.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// Se visualiza la ventana de las estadisticas
				final VentanaEstadisticas vEst = new VentanaEstadisticas(VentanaPrincipal.aPartida);
				vEst.setSize(1367,730);
				// Cuando se cierra la ventana, vuelve a la ventana menu
				vEst.addWindowListener(new java.awt.event.WindowAdapter(){
					
					@Override
					public void windowClosing(java.awt.event.WindowEvent windowEvent){
						
						vEst.setVisible(false);
						setVisible(true);
						
					}
				});
				setVisible(false);
			}
		});
		// Al dar a salir, termina la ejecucion
		bSalir.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
	}
	
	/** Clase interna para insertar la imagen en la ventana menu
	 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
	 *
	 */
	class PanelImagen extends JPanel {
		
		
			public PanelImagen(){
			setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
			repaint();
			
		}
		
		@Override
		protected void paintComponent(Graphics g){

			
			Image img = new ImageIcon(getClass().getResource("img/bumperBall.png")).getImage();
			
			Graphics2D g2 = (Graphics2D) g;
			// Escalado más fino con estos 3 parámetros:
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
			
			
		}

	}
	
	
	
}
