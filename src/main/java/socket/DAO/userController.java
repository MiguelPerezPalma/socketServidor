package socket.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import socket.Coneection.Conexion;
import socket.models.user;

public class userController extends user {

	// consultas necesarias para manipular la bd
	private static final String GETALL = "SELECT * FROM user";
	private static final String GETBYID = "SELECT * FROM user WHERE id=?";
	private static final String DELETE = "DELETE FROM user WHERE id=?";
	private final static String INSERT = "INSERT INTO user (id,name,password)" + "VALUES (?,?,?)";
	private final static String CHECKNAMEPASS = "SELECT * FROM user WHERE (name=?) AND (password = ?)";

	public userController(String name, String pass) {
		// TODO Auto-generated constructor stub
	}

	public userController() {
		// TODO Auto-generated constructor stub
	}

	// lista todos los usuarios
	public synchronized static List<user> getAllUsers() {

		// lista donde se almacenar� todos los usuarios
		List<user> users = new ArrayList<user>();

		Connection con = Conexion.getConexion();

		// si se realiza la conexi�n con la bd, procede
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				// vamos obteniendo cada usuario y la vamos almacenando en la lista con cada uno
				// de sus parametros hasta que llegamos al final
				while (rs.next()) {
					user miuser = new user();
					miuser.setId(rs.getInt("id"));
					miuser.setName(rs.getString("name"));
					miuser.setPassword(rs.getString("password"));
					users.add(miuser);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		return users;
	}

	// se obtiene el usuario seg�n su id
	public synchronized static user getUserById(int id) {
		user resultado = new user();

		Connection con = Conexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				// comprobamos el id con los de la bd
				while (rs.next()) {

					// obtenemos el usuario seg�n su id
					resultado = new user(rs.getInt("id"), rs.getString("name"), rs.getString("password"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					rs.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		return resultado;
	}

	// obtiene el id del usuario pasado por par�metro y ejecuta la consulta DELETE
	// que borra el usuario en cuesti�n
	public synchronized static void deleteUser(user u) {
		int rs = 0;
		Connection con = Conexion.getConexion();

		if (con != null) {
			try {

				PreparedStatement q = con.prepareStatement(DELETE);
				q.setInt(1, u.getId());
				rs = q.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// introduce los datos del usuario pasado por par�metro ejecutando la consulta
	// INSERT
	public synchronized static void createUser(user u) {
		int result = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = Conexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, u.getId());
				ps.setString(2, u.getName());
				ps.setString(3, u.getPassword());
				ps.executeUpdate();

				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					u.setId(rs.getInt(1));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finally {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}

		}
	}

	// comprueba que los par�metros facilitados concuerden con un usuario de la bd
	public synchronized static boolean checkCredentials(String name, String pass) {
		Connection con = Conexion.getConexion();
		boolean result = false;

		userController a = new userController();
		userController b = new userController();
		b.setName(name);
		b.setPassword(pass);

		System.out.println("argumento name: " + name + " - argumento pass: " + pass);
		if (con != null) {
			try {
				PreparedStatement ps = con.prepareStatement(CHECKNAMEPASS);
				ps.setString(1, name);
				ps.setString(2, pass);
				ResultSet rs = ps.executeQuery();

				// comparaciones de cada usuario de la bd con los datos facilitados
				while (rs.next()) {
					System.out.println(
							"usercontroller name: " + rs.getString("name") + " - pass: " + rs.getString("password"));
					a.setName(rs.getString("name"));
					a.setPassword(rs.getString("password"));
				}

				// si se da el caso de que un usuario concuerda con los datos, se devuelve true
				if (a.getName().equals(b.getName()) && a.getPassword().equals(b.getPassword())) {
					result = true;
				}
				System.out.println(a.toString());
				System.out.println(b.toString());
				System.out.println("Resultado: " + result);

				ps.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}