package LayoutPrincipal;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class Ejemplo1Layout extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejemplo1Layout frame = new Ejemplo1Layout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejemplo1Layout() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 742, 357);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 102, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//datos de la tabla
		//String[] datos = new String[]{"dni", "nombre" , "editar"};
		// se crea la tabla
		table = new JTable() {
			@Override
            public Class<?> getColumnClass(int column) {
                return column == 2 ? JButton.class : String.class; // Columna de botones
            }
		};
		table.setForeground(new Color(0, 255, 51));
		table.setBackground(new Color(0, 0, 0));
		// Renderizador personalizado para los botones
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
        
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"xxxx1", "Pepe", "editar"},
				{"xxxx2", "Mario", "editar"},
				{"xxxx3", "Maria", "editar"},
				{"xxxx4", "Jose", "editar"},
			},
			new String[] {
				"dni", "nombre", "acciones"
			}
		));
		table.getColumnModel().getColumn(2).setPreferredWidth(55);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int numcolumnas = table.getColumnCount();
				int numFila = table.getSelectedRow();//RETORNA EL NUMERO DE POSICION DE LAS FILAS - 0,1,2,3
				int numCol= table.getSelectedColumn();//RETORNA EL NUMERO QUE PERTENECE A LA COLUMNA - 0,1,2
				
				//AL SELECCIONAR LA COLUMNA EDITAR SE TRABAJA CON LOS DATOS DE LA TABLA
				if(numCol == 2) { 
					String numDniT = (String) table.getValueAt(numFila, 0); //OBTIENE EL VALOR DE LA FILA Y LA COLUMNA
					String nombreT = (String) table.getValueAt(numFila, 1); //OBTIENE EL VALOR DE LA FILA Y LA COLUMNA
					String mostrarNombre = JOptionPane.showInputDialog(contentPane, "confirma editar nombre por dni " + numDniT, nombreT);
					table.setValueAt(mostrarNombre, numFila, 1);//FILA SELECCIONADA Y LA COLUMNA DE POSICION 1 ES EL NOMBRE
				}
				
			}
		});
		
		
		
		table.setBounds(10, 40, 706, 236);
		contentPane.add(table);
	}
	//USO DE AWT PARA ESTAS ACCIONES
    // Clase para renderizar los botones
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setText((value == null) ? "Editar" : value.toString()); // Mostrar texto "Editar"
         // Definir el tamaño del botón
            setPreferredSize(new Dimension(80, 5)); // Ancho: 80, Alto: 25
            setBackground(Color.ORANGE); // Color de fondo
            setForeground(Color.BLUE); 
            setFont(new Font("Arial", Font.BOLD, 12)); 
            // Opcional: Efecto visual cuando la fila está seleccionada
            if (isSelected) {
                setBackground(Color.RED); // Cambiar color de fondo si la fila está seleccionada
                setForeground(Color.YELLOW); // Cambiar color del texto
            }
            return this;
        }
    }
}
