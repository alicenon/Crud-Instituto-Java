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
import java.io.BufferedWriter;
import java.lang.classfile.instruction.ReturnInstruction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.w3c.dom.css.RGBColor;

import bbdd.Conexion;
import bbdd.ConstantesBD;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.BorderLayout;

public class InstitutoUIPrincipal extends JFrame {

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
					InstitutoUIPrincipal frame = new InstitutoUIPrincipal();
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
	public InstitutoUIPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 500);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("CON DB ");

		setContentPane(contentPane);
		// MODELO DE LA TABLA VACIA
		DefaultTableModel modelJTable = new DefaultTableModel(new Object[][] {}, // Datos iniciales vacíos
				new String[] { "Update", "Delete", "Matricula", "Nombre", "Poblacion", "Telefono" } // Nombres de las
																									// columnas
		);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 716, 0 };
		gbl_contentPane.rowHeights = new int[] { 154, 154, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		// datos de la tabla
		// String[] datos = new String[]{"dni", "nombre" , "editar"};
		// se crea la tabla
		table = new JTable() {
			@Override
			public Class<?> getColumnClass(int column) {

				if (column == 0 || column == 1) {
					return JButton.class;
				} else {
					return String.class;
				}
				// return column == 0 ? JButton.class : String.class; // Columna de botones
			}
		};
		table.setForeground(new Color(0, 255, 0));
		table.setBackground(new Color(0, 0, 139));
		// Renderizador personalizado para los botones
		table.setDefaultRenderer(JButton.class, new ButtonRenderer());
		table.setModel(modelJTable);
		// TAMAÑO DE UNA CELDA EN FUNCION DE LA COLUMNA
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(35);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int numcolumnas = table.getColumnCount();
				int numFila = table.getSelectedRow();// RETORNA EL NUMERO DE POSICION DE LAS FILAS - 0,1,2,3
				int numCol = table.getSelectedColumn();// RETORNA EL NUMERO QUE PERTENECE A LA COLUMNA - 0,1,2

				// AL SELECCIONAR LA COLUMNA <EDITAR> SE TRABAJA CON LOS DATOS DE LA TABLA
				if (numCol == 0) {
					String numMAT = (String) table.getValueAt(numFila, 2); // OBTIENE EL VALOR DE LA FILA Y LA COLUMNA
					String nombreT = (String) table.getValueAt(numFila, 3); // OBTIENE EL VALOR DE LA FILA Y LA COLUMNA
					// CON ESTO RECUPERO EL NOMBRE DEL JOPTIONPANE Y LUEGO LO MANDO POR EL METODO
					// PARRA ACTUALIZAR EN LA BBDD
					String mostrarNombre = JOptionPane.showInputDialog(contentPane,
							"confirma editar nombre por dni " + numMAT, nombreT);
					// table.setValueAt(mostrarNombre, numFila, 3);//FILA SELECCIONADA Y LA COLUMNA
					// DE POSICION 3 ES EL NOMBRE
					if (mostrarNombre != null) {
						table.setValueAt(mostrarNombre, numFila, 3);
						// actualizar la base de datos
						bbdd.AccesoDatos.ActualizarNombreAlumno(numMAT.toString().trim(),
								mostrarNombre.toString().trim());
						System.out.println(mostrarNombre);
					}
				}
				// SELECCIONADO EL BOTON TEXTO DE <BORRAR>
				if (numCol == 1) {
					String numMAT = (String) table.getValueAt(numFila, 2);
					String nombreT = (String) table.getValueAt(numFila, 3);

					int confirmar = JOptionPane.showConfirmDialog(contentPane,
							"¿Desea eliminar el Registro Alumno: " + numMAT + " - " + nombreT + "?",
							"SE ELIMINARÁ UN REGISTRO: ", JOptionPane.YES_NO_OPTION);
					if (confirmar == JOptionPane.YES_OPTION) {
						try {
							bbdd.AccesoDatos.BorrarAlumno(numMAT);
							modelJTable.removeRow(numFila);

						} catch (ArrayIndexOutOfBoundsException e2) {
							JOptionPane.showMessageDialog(contentPane, "Fuera del rango! Debes pinchar en los Botones ",
									e2.getMessage(), 1);
							System.out.println(e2.getMessage());
						}
					}

				}

			}
		});
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.gridwidth = 1; // Ocupa 1 columna
		gbc_table.gridheight = 4;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		contentPane.add(table, gbc_table);

		JPanel panel2 = new JPanel();
		GridBagConstraints gbc_panel2 = new GridBagConstraints();
		gbc_panel2.fill = GridBagConstraints.BOTH;

		gbc_panel2.gridx = 0;
		gbc_panel2.gridy = 5;
		gbc_panel2.gridheight = 2;
		gbc_panel2.weightx = 0.5; // Distribuir espacio horizontal
		gbc_panel2.weighty = 0; // Distribuir espacio vertical
		contentPane.add(panel2, gbc_panel2);
		panel2.setLayout(new GridLayout(0, 1, 0, 0));
		// CREAR - USO DE LAMBDA SIN CUERPO, PORQUE SOLO TENIA QUE CREAR EL FORMULARIO
		JButton btnNewButton = new JButton("Crear Registro");
		panel2.add(btnNewButton);
		//INSTANCIAR FormCreateAlumno
		btnNewButton.addActionListener(e -> new FormCreateAlumno(modelJTable));
		btnNewButton.setBackground(new Color(0, 255, 0));
		btnNewButton.setForeground(new Color(0, 0, 0));

		JLabel lblNewLabel = new JLabel("111");
		lblNewLabel.setBackground(new Color(105, 105, 105));
		panel2.add(lblNewLabel);

		// conexion
		Connection conexion = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
		ResultSet rs = bbdd.AccesoDatos.mostrarAlumnos(conexion);
		try {
			while (rs.next()) {
				String mat = rs.getString("mat");
				String nombre_completo = rs.getString("apel_nom");
				String poblacion = rs.getString("poblacion");
				String telf = rs.getString("telefono");
				// table.setModel(new DefaultTableModel().addRow(new Object[] {mat,
				// nombre_completo,"editar"}));
				modelJTable.addRow(new Object[] { "Editar", "Borrar", mat, nombre_completo, poblacion, telf });

			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// USO DE AWT PARA ESTAS ACCIONES
	// CLASE PARA RRENDERIZARR BOTONES
	static class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText((value == null) ? "Editar" : value.toString()); // Mostrar texto "Editar"
			// Definir el tamaño del botón
			// setPreferredSize(new Dimension(80, 5)); // Ancho: 80, Alto: 25

			// CAMBIAMOS EL COLOR DEL BOTON SEGUN EL TEXTO
			if ("Editar".equalsIgnoreCase((String) value)) {
				setBackground(new Color(255, 194, 5)); // Color de fondo
				setForeground(Color.black);
			} else if ("Borrar".equalsIgnoreCase(value.toString())) {
				setBackground(Color.RED); // Color de fondo
				setForeground(Color.WHITE);
			}

			setFont(new Font("Arial", Font.BOLD, 13));
			// Opcional: Efecto visual cuando la fila está seleccionada
			if (isSelected) {
				setBackground(Color.RED); // Cambiar color de fondo si la fila está seleccionada
				setForeground(Color.YELLOW); // Cambiar color del texto
			}
			return this;
		}
	}
}
