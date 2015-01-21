package logica;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JTable;

import java.io.File;
import java.text.*;

 
 
public class PrintTest {
 
   /**
    * @param taula
    * Esta es la clase utilizada para imprimir. 
    * Se le pasa como parámetro una JTable. 
    * Por otra parte destacar el hecho de que tenemos una cabecera y un pie de página, siendo estos de la clase MessageFormat lo cual hace 
    * que quede más original el texto. 
    */
public PrintTest(JTable taula){
	   
	  MessageFormat cabecera = new MessageFormat("BumperBall ");
	 
	  MessageFormat piepagina = new MessageFormat("Página {0, number, integer}");

	  try{
		  
		  /*
		   * Para imprimirlo en un formato normal. Se puede cambiar. 
		   */
		  taula.print(JTable.PrintMode.NORMAL, cabecera, piepagina);
	  }catch(java.awt.print.PrinterException e){
		  
		  System.err.format("No se ha podido imprimir", e.getMessage());
	  }
	 
	  
   }
}
