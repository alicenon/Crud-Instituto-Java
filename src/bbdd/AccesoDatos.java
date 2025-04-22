package bbdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoDatos {

	/*
	 * mostrar con preparedStatement.
	 */

//READ
	public static ResultSet mostrarAlumnos(Connection con) {
		ResultSet resultado = null;

		try {
			if (con == null) /// Revisar la conexion
				con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
			String consulta = "SELECT * FROM alumnos";

			PreparedStatement sentencia = con.prepareStatement(consulta);

			resultado = sentencia.executeQuery();

		} catch (SQLException ex) {
			// resultado = ex.toString();
			System.out.println(ex.getMessage());
		}
		System.out.println("resultado " + resultado);
		return resultado;
	}

//UPDATE
	public static void ActualizarNombreAlumno(String matricula, String nombre_completo) {
		// TODO Auto-generated method stub
		Connection con = null;

		try {
			if (con == null) /// Revisar la conexion
				con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
			String consulta = "UPDATE alumnos SET apel_nom = ? WHERE mat = ?";
			PreparedStatement ps = con.prepareStatement(consulta);
			ps.setString(1, nombre_completo);
			ps.setString(2, matricula);
			int filasAfectadas = ps.executeUpdate();
			System.out.println("Se ha actualizado " + filasAfectadas + " fila con el valor de: " + nombre_completo);

			System.out.println("ACTUALIZADO CORRECTAMENTE");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error: algun problema con actualizar la tabla - > " + e.getMessage());
		} finally {
			Conexion.desConexion(con);

		}

	}

//DELETE
	public static void BorrarAlumno(String numMAT) {
		// TODO Auto-generated method stub
		Connection con = null;
		try {
			if (con == null) /// Revisar la conexion
				con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
			String consulta = "DELETE FROM alumnos WHERE mat = ?";
			PreparedStatement ps = con.prepareStatement(consulta);
			ps.setString(1, numMAT);
			int filaAfectada = ps.executeUpdate();
			System.out.println(
					"Se ha borrado " + filaAfectada + " corespondiente al alumno matricula/dni (" + numMAT + ")");
		} catch (SQLException e) {
			// TODO: handle exception
			System.out.println("Error: algun problema con borrar la tabla - > " + e.getMessage());
		} finally {
			Conexion.desConexion(con);

		}

	}

//CREATE
	public static boolean CrearAlumno(String matricula, String nombre, String poblacion, String telefono)
			 {
		Connection con = null;
		try {
			if (con == null) /// Revisar la conexion
				con = Conexion.conexion(ConstantesBD.URL, ConstantesBD.USUARIO, ConstantesBD.PWD);
			String consulta = "INSERT INTO alumnos (mat, apel_nom, poblacion, telefono) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = con.prepareStatement(consulta);
			ps.setString(1, matricula);
			ps.setString(2, nombre);
			ps.setString(3, poblacion);
			ps.setString(4, telefono);

			int filasAfectadas = ps.executeUpdate();//
			return filasAfectadas > 0; // RETORNA VERDADERO SI SE INSERTA UN ALUMNO 

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Error al Insertar/Crear Alumno SQL -> " + e.getMessage());
		} finally {

			Conexion.desConexion(con);
		}
		return false;
	}

}
