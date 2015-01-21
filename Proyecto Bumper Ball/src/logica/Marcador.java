package logica;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.border.Border;
//import Logica.Pelota;
import javax.swing.border.LineBorder;

/** Marcador que extiende de JLabel para el conteo de los goles
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class Marcador extends JLabel{
	
	private int GolA;
	private int GolB;
	private Border borde=new LineBorder(Color.black, 3);
	private String Marca = (GolA+" "+"-"+" "+GolB);


	/** Constructor
	 * @param GolesA numero de goles que ha marcado el coche verde
	 * @param GolesB numero de goles que ha marcado el coche rojo
	 */
	public Marcador(int GolesA, int GolesB){
		
		super();
		setSize(150,40);
		setFont( new Font( Font.SERIF, Font.BOLD, 30 ) );
	    setHorizontalAlignment( JLabel.CENTER );
	    setForeground( Color.BLACK );
	    setBackground( Color.WHITE );
	    setBorder(borde);
	    
		this.GolA = GolesA;
		this.GolB = GolesB;
		setText(GolA+" "+"-"+" "+GolB);

	}
	public int getGolesA(){
		return GolA;
	}
	public int getGolesB(){
		return GolB;
	}
	
	public void setMarcador(int golesA, int golesB){
				
		this.GolA = golesA;
		this.GolB = golesB;
	}
	public String getMarca(){
		return Marca;
	}
	/** Incrementamos los goles del coche verde
	 * 
	 */
	public void marcaGolA(){
		this.GolA++;
		this.setText(GolA+" "+"-"+" "+GolB);

	}
	/** Incrementamos los goles del coche rojo
	 * 
	 */
	public void marcaGolB(){
		this.GolB++;
		this.setText(GolA+" "+"-"+" "+GolB);

	}

}