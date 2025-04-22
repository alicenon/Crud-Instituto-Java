package LayoutPrincipal;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.Font;

public class FormCreateAlumno extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMatricula;
	private JTextField txtNombre;
	private JTextField txtPoblacion;
	private JTextField txtTelefono;

	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormCreateAlumno frame = new FormCreateAlumno();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public FormCreateAlumno(DefaultTableModel modelJTable) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);
		setTitle("Formulario Crear Alumno");
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLUE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5, 2, 0, 0));

		// CAMPOS DE TEXTOS
		JLabel label = new JLabel("Matrícula:" );
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setForeground(new Color(0, 255, 0));
		getContentPane().add(label);
		txtMatricula = new JTextField();
		txtMatricula.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(txtMatricula);
		getContentPane().setForeground(Color.GREEN);
		JLabel label_1 = new JLabel("Nombre Completo:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setForeground(new Color(0, 255, 0));
		getContentPane().add(label_1);
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(txtNombre);

		JLabel label_2 = new JLabel("Población:");
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setForeground(new Color(0, 255, 0));
		getContentPane().add(label_2);
		txtPoblacion = new JTextField();
		txtPoblacion.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(txtPoblacion);

		JLabel label_3 = new JLabel("Teléfono:");
		label_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_3.setForeground(new Color(0, 255, 0));
		getContentPane().add(label_3);
		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Tahoma", Font.BOLD, 13));
		getContentPane().add(txtTelefono);

		// BOTON GUARDAR CON PEQUEÑA LAMBDA SIN CUERPO YA QUE SOLO HAGO UNA COSA
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBackground(Color.GREEN);
		btnGuardar.setForeground(Color.WHITE);
		btnGuardar.addActionListener(e -> guardarAlumno(modelJTable));
		getContentPane().add(btnGuardar);

		//BOTON PARA CANCELAR LA OPERACION
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.GREEN);
		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.addActionListener(e -> dispose());//SE CERRARA EL FORMULARIO
		getContentPane().add(btnCancelar);
		setVisible(true);
	}
	//METODO PARA GUARDAR ALUMNOS DEL MODELO DE LA TABLA
    private void guardarAlumno(DefaultTableModel modelJTable) {
        String matricula = txtMatricula.getText().trim();
        String nombre = txtNombre.getText().trim();
        String poblacion = txtPoblacion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        //VALIDAR QUE INGRESE TODOS LOS CAMPOS 
        if (matricula.isEmpty() || nombre.isEmpty() || poblacion.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(contentPane, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean exito = bbdd.AccesoDatos.CrearAlumno(matricula, nombre, poblacion, telefono);
		if (exito) {
		    // AGREGAMOS LA FILA DE ALTA A LA TABLA PRINCIPAL DEL MODELO DE LA TABLA JTABLE
		    modelJTable.addRow(new Object[]{"Editar", "Borrar", matricula, nombre, poblacion, telefono});
		    JOptionPane.showMessageDialog(this, "Registro creado exitosamente.");
		    dispose(); // CERRAR FORMULARIO DE CREAR ALUMNO
		} else {
		    JOptionPane.showMessageDialog(this, "Error al crear el registro.", "Error", JOptionPane.ERROR_MESSAGE);
		}
    }

}
