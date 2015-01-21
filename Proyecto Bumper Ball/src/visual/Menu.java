package visual;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu{
	
	/*
	 * Clase en la cual se aplica la barra de menú. 
	 */
	
	public Menu(JFrame ventana){	
	
		
	JMenuBar mBar = new JMenuBar();	
	ventana.setJMenuBar(mBar);
	JMenu opc = new JMenu("Opciones");	
	mBar.add(opc);
	/*
	 * Se le añaden los componentes dentro de la barra. 
	 */
	JMenuItem controles = new JMenuItem("Controles");
	JMenuItem salir = new JMenuItem("Salir");
	
	JMenu tiempo = new JMenu("Tiempo");
	JMenuItem t2 = new JMenuItem("2 minutos");
	JMenuItem t3 = new JMenuItem("3 minutos");
	JMenuItem t5 = new JMenuItem("5 minutos");

	tiempo.add(t2);
	tiempo.add(t3);
	tiempo.add(t5);
	
	opc.add(controles);
	opc.add(tiempo);
	opc.add(salir);
	
	
	}
	

	

}
