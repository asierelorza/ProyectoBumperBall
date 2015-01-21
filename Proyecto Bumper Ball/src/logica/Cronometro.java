package logica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.w3c.dom.events.EventException;
 
/**Implemetacion de un cronometro para la gestion del tiempo en el juego
 * @author Iker Calzada, Asier Dorronsoro & Asier Elorza
 *
 */
public class Cronometro extends JPanel implements Runnable{ 
	
	private JLabel tiempo;
	public  int nuMin;
	public  int nuSeg;
	public Thread hilo;
	private int contador=0;

	Border Bor=new LineBorder(Color.black, 3);


	
    /**Constructor
     * @param numMin numero de minutos
     * @param nuSeg numero de segundos
     */
    public Cronometro(int numMin , int nuSeg)
    {
    	
    	this.nuMin= numMin;
    	this.nuSeg = nuSeg;
        setSize( 300, 40 );
        setLayout(null);

        tiempo = new JLabel();
        tiempo.setFont( new Font( Font.SERIF, Font.BOLD, 30 ) );
        tiempo.setHorizontalAlignment( JLabel.CENTER );
        tiempo.setForeground( Color.BLACK );
        tiempo.setBackground( Color.WHITE );
        tiempo.setBounds(0,0,100,40);
        tiempo.setBorder(Bor);
        tiempo.setOpaque( true );
 
        add( tiempo, BorderLayout.CENTER ); 

    }
    
   public  synchronized  void run(){
    	

    	if(contador==0){
    	add( tiempo, BorderLayout.CENTER );
    	}
    	contador=200;
    	try {
             for (; ;){         
               if(nuSeg!=00) {
                    nuSeg--;
                   
                 }else{
                     if(nuMin!=00){
                         nuSeg=59;
                         nuMin--;
                         
                     }else{                         
                           break;  
                         }
                     }               
               if(nuSeg < 10){
            	   tiempo.setText("0"+nuMin+":"+"0"+nuSeg);
               }else{
                   tiempo.setText("0"+nuMin+":"+nuSeg);

               }
             try {
     			hilo.sleep(998);
     		} catch (InterruptedException e1) {
     			
     		}
             
             }            
         } catch (EventException e) {
       	  
         }          

        }

    public void iniciarCronometro() {
    	
        hilo = new Thread( this );
        hilo.start();
    }
  
    /** Visualizamos el cronometro
     * 
     */
    public void visualizarCronometro(){
    	
    	iniciarCronometro();
    }
    

    
    /** Vemos si el tiempo ha finalizado
     * @return false si el tiempo ha finalizado, true si no
     */
    public boolean finCronometro(){
    	if(nuMin == 0 && nuSeg == 0){
    	
    		hilo.interrupt();
    		return false;
    	}
    	
    	return true;
    }
    /** Finalizamos el cronometro
     * 
     */
    public void finCronometromenu(){
    	
    	hilo.interrupt();
    }
    
    
}

