package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import logica.Partida;
import logica.PrintTest;

public class VentanaEstadisticas extends JFrame{


	
	private ArrayList<Partida> z = new ArrayList<Partida>();
	
	public VentanaEstadisticas(ArrayList<Partida> p)	{
		
		z=p;
		Modelo mod = new Modelo();
		final JTable tabla = new JTable(mod);
		JScrollPane jsp = new JScrollPane(tabla);
		getContentPane().add(jsp, BorderLayout.CENTER);
		JButton print = new JButton("Print");
		getContentPane().add(print, BorderLayout.SOUTH);
		print.addActionListener(new ActionListener(){
			
			/*
			 * Desde aquí se llama a la clase PrintTest que será utilizada para imprimir la tabla realizada. 
			 */
			
			@Override
			public void actionPerformed(ActionEvent e){
				
				PrintTest a = new PrintTest(tabla);
						
			}
			
		});
		
		
		setVisible(true);
		pack();
		
		
	}
	
	/*
	 * Creo una tabla que extiende de AbstractTableModel. 
	 * A pesar de que podía hacer siguiendo de un defaulttable nos ha parecido que así también se podía realizar y que se demostraba más el dominio 
	 * de una JTable.
	 * No hemos visto necesario usar los renderer y los tableeditor para una tabla tan simple. 
	 */
	
	class Modelo extends AbstractTableModel{
		private String headers[] = {"Local", "Visitante", " Resultado"};
		Class<?> columnas[]= {String.class, String.class, String.class};
		private Object data[][] = new Object[z.size()][3];
		
		public Modelo(){
			llenartabla();
		}
		
		/**
		 * Método utilizado para rellenar la tabla. 
		 * Para ello utilizamos un swing que según el valor de j se podrá introducir o en una columna o en otra. 
		 */
		public void llenartabla(){
			
			for(int i = 0; i< z.size();i++){
				
				for(int j = 0; j<3;j++){
					
					switch(j){
						
						case 0: data[i][j]= z.get(i).localdev();
								break;
						
						case 1: data[i][j] = z.get(i).visitdev();
								break;
								
						case 2: data[i][j] = z.get(i).devolver();
								break;
					}
				}
				
			}
			
		}	
		
		
		
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return data.length;
		}
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return headers.length;
		}
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return data[rowIndex][columnIndex];
		}
		
		public  boolean isCellEditable(int r, int c){
			
			return true;
		}
		
		public String getColumnName(int columnIndex) {
		    return headers[columnIndex];
		}
	}
}
