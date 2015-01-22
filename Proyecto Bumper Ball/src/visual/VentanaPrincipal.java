package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import logica.CocheJuego;
import logica.Cronometro;
import logica.MundoBumper;
import logica.Partida;
import logica.Pelota;

/**Ventana principal en donde ocurre la partida y va el marcador, el cronometro y las opciones
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class VentanaPrincipal extends JFrame{
	
	private static Thread nuevoHilo; // Hilo principal del juego
	
	private static int minutos;
	private static int segundos; // Parametros para el cronometro en el menu
	
	private static String nomJug1;
	private static String nomJug2; // Parametros para los nombres de usuario
	
	private MundoBumper bumper; // La logica del juego

	private PJuego panel = new PJuego(); // Panel principal del juego
	private Cronometro cron; // Cronometro
	private PMarcador marca; // Marcador
	private Border border=new LineBorder(Color.black, 3);
	
	private MiRunnable hilo = null; // Runnable del juego
	private final boolean [] controlCocheRojo = new boolean[4];
	private final boolean [] controlCocheVerde = new boolean[4]; // Arrays para controlar la interaccion de cada usuario con los coches
	
	private CocheJuego cocheRojo;
	private CocheJuego cocheVerde; // Los coches donde va la logica
	private Pelota pelota; // La logica de la pelota

	private boolean golA;
	private boolean golB; // Booleanos para saber si ha habido gol o no
	
	private JFrame vGol = new JFrame("GOL!");
	private ZoomPanel pGol;
	private JPanel pContinuar = new JPanel();
	private JButton bContinuar = new JButton("Continuar"); // Componentes para la ventana de gol.
	
	public static ArrayList<Partida> aPartida = new ArrayList<Partida>(); // Array para el guardado de las estadisticas
	

	
	
	/** Constructor de la clase
	 * @param minutos Minutos para el control del cronometro
	 * @param segundos Segundos para el control del cronometro
	 * @param golesA Goles para el control del marcador
	 * @param golesB Goles para el control del marcador
	 * @param minAsign Minutos para el control de la seleccion de tiempo en la pestaña opciones
	 * @param segAsign Segundos para el control de la seleccion de tiempo en la pestaña opciones
	 * @param nomJug1 Usuario 1
	 * @param nomJug2 Usuario 2
	 */
	public VentanaPrincipal(int minutos, int segundos, int golesA, int golesB, int minAsign, int segAsign, String nomJug1, String nomJug2){
		
		cron =new Cronometro(minutos, segundos);
		marca = new PMarcador(golesA, golesB);
		
		setLocationRelativeTo(null);
		setLayout(null);
//		this.setBackground(Color.LIGHT_GRAY);
		
		
		setBounds(0,0,915,700);
		marca.setLocation(375,0);
		
//		marca.setBounds(250,0,200,100);
		cron.setBounds(450,0,150,50);
		cron.iniciarCronometro();
		cron.setLocation(515, 0);
		
		panel.setBounds(0,40,910,610);
		panel.setLayout(null);
		panel.setBorder(border);
		
		minutos = minAsign;
		segundos = segAsign;
		
		this.nomJug1 = nomJug1;
		this.nomJug2 = nomJug2;
		
		Menu(this);
		
		add(panel);
		add(cron);
		add(marca);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Listeners para la interaccion del usuario con los coches
		panel.addKeyListener(new KeyAdapter(){ 
			@Override							
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: {
					controlCocheRojo[0] = true;
					break;
				}
				case KeyEvent.VK_DOWN: {
					controlCocheRojo[1] = true;
					break;
				}
				case KeyEvent.VK_LEFT: {
					controlCocheRojo[2] = true;
					break;
				}
				case KeyEvent.VK_RIGHT: {
					controlCocheRojo[3] = true;
					break;
				}
				case KeyEvent.VK_W: {
					controlCocheVerde[0] = true;
					break;
				}
				case KeyEvent.VK_S: {
					controlCocheVerde[1] = true;
					break;
				}
				case KeyEvent.VK_A: {
					controlCocheVerde[2] = true;
					break;
				}
				case KeyEvent.VK_D: {
					controlCocheVerde[3] = true;
					break;
				}
			}
			}
			
			@Override
			public void keyReleased(KeyEvent e){
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP: {
					controlCocheRojo[0] = false;
					break;
				}
				case KeyEvent.VK_DOWN: {
					controlCocheRojo[1] = false;
					break;
				}
				case KeyEvent.VK_LEFT: {
					controlCocheRojo[2] = false;
					break;
				}
				case KeyEvent.VK_RIGHT: {
					controlCocheRojo[3] = false;
					break;
				}
				case KeyEvent.VK_W: {
					controlCocheVerde[0] = false;
					break;
				}
				case KeyEvent.VK_S: {
					controlCocheVerde[1] = false;
					break;
				}
				case KeyEvent.VK_A: {
					controlCocheVerde[2] = false;
					break;
				}
				case KeyEvent.VK_D: {
					controlCocheVerde[3] = false;
					break;
				}
			}
			}
				
			
		});
	
	
		panel.setFocusable(true);
		panel.requestFocus();
		
		panel.addFocusListener( new FocusAdapter() { // Enfoque del listener en el panel
			@Override
			public void focusLost(FocusEvent e) {
				panel.requestFocus();
			}
		});
		
		addWindowListener(new java.awt.event.WindowAdapter(){
			
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
				
				System.exit(1);				
			}
		});
	
	}
	

	
	/**Creacion del menu opciones
	 * @param ventana
	 */
	public void Menu(final JFrame ventana){
		
		final JFrame vControles = new JFrame("Controles");
		final PanelImagenControles pControles = new PanelImagenControles(ventana);
			
		vControles.addWindowListener(new java.awt.event.WindowAdapter(){
			
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent){
				
				cron.hilo.resume();
				nuevoHilo.resume();
				vControles.setVisible(false);
				ventana.setVisible(true);
				
			}
		});
		
				
		JMenuBar mBar = new JMenuBar();
		
		ventana.setJMenuBar(mBar);

		JMenu opc = new JMenu("Opciones");
		
		mBar.add(opc);
		
		JMenuItem controles = new JMenuItem("Controles");
		
		controles.addActionListener(new ActionListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cron.hilo.suspend();
				nuevoHilo.suspend();
				vControles.setSize(new Dimension(700,400));
				vControles.add(pControles);
				vControles.setLocationRelativeTo(null);
				vControles.setVisible(true);
				ventana.setVisible(false);
				
			}
			
		});
		
		
		JMenuItem salir = new JMenuItem("Salir");
		
		JMenu tiempo = new JMenu("Tiempo");
		JMenuItem t2 = new JMenuItem("2 minutos");
		JMenuItem t3 = new JMenuItem("3 minutos");
		JMenuItem t5 = new JMenuItem("5 minutos");
		
		t2.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				minutos = 2;
				segundos=0;

				
			}
			
		});
		t3.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				minutos = 3;
				segundos=0;

			}
			
		});
		t5.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				minutos = 5;
				segundos=0;

			}
			
		});
		
		tiempo.add(t2);
		tiempo.add(t3);
		tiempo.add(t5);
		
		opc.add(controles);
		opc.add(tiempo);
		opc.add(salir);
		
		salir.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e){
				nuevoHilo.suspend();
				cron.hilo.suspend();
				setVisible(false);
				VentanaMenu vMenu = new VentanaMenu();
			}
		});
		
		}
	
			
	/** Lanzamiento del juego
	 * @param minutos
	 * @param segundos
	 * @param golesA
	 * @param golesB
	 * @param minAsign
	 * @param segAsign
	 * @param nomJug1
	 * @param nomJug2
	 */
	public static void empieza(int minutos, int segundos, int golesA, int golesB, int minAsign, int segAsign, String nomJug1, String nomJug2) {
		try{
			final VentanaPrincipal v = new VentanaPrincipal(minutos, segundos, golesA, golesB, minAsign, segAsign, nomJug1, nomJug2);
			
			
			v.setResizable(false);
			v.setLocationRelativeTo(null);
			v.setBackground(Color.GRAY);
			v.setVisible(true);
			v.bumper = new MundoBumper(v.panel);
			v.bumper.creaCocheRojo(70, v.getHeight()/2-75);
			v.bumper.creaCocheVerde(750, v.getHeight()/2-75);
			v.panel.creaPelota(445, 295);
			
			v.cocheRojo = v.bumper.getCocheRojo();
			v.cocheVerde = v.bumper.getCocheVerde();
			v.pelota = v.panel.getPelota();
			v.hilo = v.new MiRunnable();
			nuevoHilo = new Thread(v.hilo);
			nuevoHilo.start();
		}
		catch(Exception e){
			System.exit(1);
		}
		
		
	}
	
	/** Clase interna donde va el hilo del juego
	 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
	 *
	 */
	class MiRunnable implements Runnable{
		boolean sigo = true;
		
		@Override
		public void run() {
			while(sigo){ 
				
				cocheRojo.mueve(0.04);
				cocheVerde.mueve(0.04);
				pelota.mueve(0.04); // Movimiento de los componentes
				
				if (bumper.hayChoqueHorizontal(cocheRojo)) // Espejo horizontal si choca en X
					bumper.rebotaHorizontal(cocheRojo);
				if (bumper.hayChoqueVertical(cocheRojo)){ 
					// Espejo vertical si choca en Y
					bumper.rebotaVertical(cocheRojo);
					

				}
				if (bumper.hayChoqueHorizontal(cocheVerde)) // Espejo horizontal si choca en X
					bumper.rebotaHorizontal(cocheVerde);
				if (bumper.hayChoqueVertical(cocheVerde)) // Espejo vertical si choca en Y
					bumper.rebotaVertical(cocheVerde);
				
				if(bumper.hayChoqueHorizontalPelota(pelota)){
					bumper.rebotaHorizontalPelota(pelota);
					panel.setPelota(pelota);
					panel.repaint();
				}
				if(bumper.hayChoqueVerticalPelota(pelota)){
					bumper.rebotaVerticalPelota(pelota);
					panel.setPelota(pelota);
					panel.repaint();
				}
				

				if(bumper.chocaCocheConCoche(cocheRojo, cocheVerde)){ // Choque de los dos coches 

					bumper.rebota();
										
				}
				// Choque del coche con la pelota. En estos casos, hay que pasar la pelota a PJuego para actualizarlo y asi dibujarlo en la posicion correspondiente
				else if(bumper.chocaCocheConPelota(cocheRojo, pelota)){ 
					bumper.rebotaPelota(cocheRojo, pelota);
					panel.setPelota(pelota);
					panel.repaint();
					
				}
				else if(bumper.chocaCocheConPelota(cocheVerde, pelota)){
					bumper.rebotaPelota(cocheVerde, pelota);
					panel.setPelota(pelota);
					panel.repaint();
				}
					
				// Aplicar la fuerza en base a la interaccion del usuario con el coche
				for(int i = 0; i <= 3; i++){
					if(controlCocheRojo[i] == true){
						switch(i){
						case 0: MundoBumper.aplicarFuerza(cocheRojo.fuerzaAceleracionAdelante(), cocheRojo); break;
						case 1: MundoBumper.aplicarFuerza(-cocheRojo.fuerzaAceleracionAtras(), cocheRojo); break;
						case 2: cocheRojo.gira( +10 ); break;
						case 3: cocheRojo.gira( -10 ); break;
					}
					}
					else if(controlCocheRojo[i] == false){
						switch(i){
						case 0: MundoBumper.aplicarFuerza(0, cocheRojo); break;
						case 1: MundoBumper.aplicarFuerza(0, cocheRojo); break;
						case 2: break;
						case 3: break;
					}
					}
					
					if(controlCocheVerde[i] == true){
						switch(i){
						case 0: MundoBumper.aplicarFuerza(cocheVerde.fuerzaAceleracionAdelante(), cocheVerde); break;
						case 1: MundoBumper.aplicarFuerza(-cocheVerde.fuerzaAceleracionAtras(), cocheVerde); break;
						case 2: cocheVerde.gira( +10 ); break;
						case 3: cocheVerde.gira( -10 ); break;
					}
					}
					else if(controlCocheVerde[i] == false){
						switch(i){
						case 0: MundoBumper.aplicarFuerza(0, cocheVerde); break;
						case 1: MundoBumper.aplicarFuerza(0, cocheVerde); break;
						case 2: break;
						case 3: break;
					}
					}
				}
				
				MundoBumper.aplicarFuerzaPelota(0, pelota); // Deceleramos la pelota debido a la fuerza de rozamiento
				MarcaGol(); // Comprobamos si ha habido gol
//				
				if(golA == true || golB == true){ // Si ha habido gol, salimos del while
					
					sigo = false;
				}
				panel.repaint();
				
				// Comprobacion de si se ha terminado el tiempo, y dormir el hilo 4000 milisegundos
				
				try{
					Thread.sleep(40);
				}
				catch(Exception e){
					
				}
				
				if(golA == false && golB == false){ // Seguimos o no con el hilo en funcion del cronometro.
					sigo = cron.finCronometro();
				}
				
				
				
			}
			
			// Interrupcion del hilo y escondemos la ventana principal
			Thread.interrupted();
			setVisible(false);
			
			// Interrupcion debido a fin de tiempo
			if(golA == false && golB == false){
				
			// Creacion de la ventana final
			final JFrame ventanaFin = new JFrame("Partida Terminada!");
			JLabel lFin = new JLabel("La partida ha terminado. Resultado: " + nomJug1 + " " + marca.ma.getGolesA() + " - " + marca.ma.getGolesB() + " " + nomJug2);
			JPanel pFin = new JPanel();
			JButton bReiniciar = new JButton("Reiniciar");
			JButton bSalir = new JButton("Salir");
			
			pFin.add(lFin);
			pFin.add(bReiniciar);
			pFin.add(bSalir);
			
			ventanaFin.add(pFin);
			ventanaFin.setLocationRelativeTo(null);
			ventanaFin.pack();
			ventanaFin.setVisible(true);
			
			// La partida se vuelve a reiniciar
			bReiniciar.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					ventanaFin.setVisible(false);
					reinicio();
				}
			});
			
			// Salimos de la partida
			bSalir.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					ventanaFin.setVisible(false);
					VentanaMenu vMenu = new VentanaMenu(); 
				}
			});
			
			// Guardamos el resultado en el ArrayList
			Partida partida = new Partida(marca.ma.getGolesA(), marca.ma.getGolesB(), nomJug1, nomJug2);
			aPartida.add(partida);
			
			}
			// Interrupcion de gol
			else{
				// Creacion de la ventana de gol
				pGol = new ZoomPanel("img/gol.PNG");
				pContinuar.add(bContinuar);
				vGol.add(pGol);
				vGol.add(pContinuar, BorderLayout.SOUTH);
				vGol.setBounds(0, 0, 700, 200);
				vGol.setLocationRelativeTo(null);
				vGol.setVisible(true);
				cron.finCronometromenu();
				// Cogemos exactamente en que tiempo se ha producido el gol y si el usuario tiene asignado un tiempo determinado para la siguiente partida
				final int minFinal = cron.nuMin;
				final int segFinal = cron.nuSeg;
				final int minAsign = minutos;
				final int segAsign = segundos;

				// Continuamos con la partida
				bContinuar.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						vGol.setVisible(false);
						pGol.pararhilo();
						empieza(minFinal, segFinal, marca.ma.getGolesA(), marca.ma.getGolesB(), minAsign, segAsign, nomJug1, nomJug2);
						
					}
					
				});
				
				
					
				
			}
			
			
			
		}
		
	}
	/** Comprobacion de si se ha producido gol
	 * 
	 */
	public void MarcaGol(){
		
		panel.logicaGol();
		
		golA = panel.golA;
		golB = panel.golB;
		
		if(golA==true){

		marca.ma.marcaGolB();
						
	  	}
	  	if(golB==true){

		  marca.ma.marcaGolA();
		  
		}
	}
	
	/** Reiniciamos la partida, reiniciando asi el juego, el cronometro y el marcador
	 * 
	 */
	public void reinicio(){
				
		if(minutos == 0 && segundos == 0){ // Si el usuario no ha asignado ningun tiempo para la siguiente partida, asignamos el tiempo por defecto
			minutos = 2;
			segundos = 0;
		}
		empieza(minutos, segundos, 0,0,0,0, nomJug1, nomJug2);
		
		
		
	}
	
	/** Clase interna para insertar la imagen en la ventana de los controles
	 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
	 *
	 */
	class PanelImagenControles extends JPanel{
		
	Image imagen;	
		public PanelImagenControles(JFrame f){
			
			ImageIcon img = new ImageIcon(VentanaPrincipal.class.getResource("img/controles.png"));
			imagen = (img).getImage();
			
			this.setSize(f.getWidth(), f.getHeight());
		}
		
		@Override
		public void paintComponent(Graphics g){
		
		Graphics2D g2 =(Graphics2D)g;
		g2.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), null);
		
		}
		
	}
}
