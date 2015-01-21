package visual;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import logica.Cronometro;
import logica.Marcador;
import logica.MundoBumper;

/** Panel del marcador
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class PMarcador extends JPanel{
	
	public Marcador ma;
	
	/** Constructor del panel del marcador
	 * @param golesA goles obtenidos por el coche verde
	 * @param golesB goles obtenidos por el coche rojo
	 */
	public PMarcador(int golesA, int golesB){
		
		super();
		setSize(200,60);
		setLayout(null);
		setBackground(Color.white);

		ma = new Marcador(golesA,golesB);
		ma.setLocation(0,0);
		add(ma);

	}
	
	public void setPanelMarcador(int golesA, int golesB){
		ma = new Marcador(golesA, golesB);
	}

}