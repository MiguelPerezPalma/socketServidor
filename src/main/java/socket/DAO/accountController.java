package socket.DAO;

import socket.Coneection.Conexion;
import socket.models.account;
import socket.models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class accountController extends account {

	// consultas necesarias para manipular la bd
	private static final String GETALL = "SELECT * FROM account";
	private static final String GETBYID = "SELECT * FROM account WHERE id=?";
	private static final String DELETE = "DELETE FROM account WHERE id=?";
	private final static String INSERT = "INSERT INTO account (id,money,user_id)" + "VALUES (?,?,?)";
	private final static String UPDATEMONEY = "UPDATE cuenta SET money=? WHERE id=?";
	private final static String GETBYIDUSER = "SELECT * FROM account WHERE user_id=?";

	// lista todas las cuentas
	public synchronized static List<account> getAllAccounts() {

		// lista donde se almacenar� todas las cuentas
		List<account> accounts = new ArrayList<account>();

		Connection con = Conexion.getConexion();

		// si se realiza la conexi�n con la bd, procede
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				// vamos obteniendo cada cuenta y la vamos almacenando en la lista con cada uno
				// de sus parametros hasta que llegamos al final
				while (rs.next()) {
					account miaccount = new account();
					miaccount.setId(rs.getInt("id"));
					miaccount.setMoney(rs.getInt("money"));
					miaccount.setMiuser(userController.getUserById(rs.getInt("user_id")));
					accounts.add(miaccount);
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
		return accounts;
	}

	// se obtiene la cuenta seg�n su id
	public synchronized static account getAccoutByID(int id) {
		account resultado = new account();

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
					userController x = new userController();
					user xs = x.getUserById(rs.getInt("Id_Genero"));

					// obtenemos el usuario seg�n id
					resultado = new account(rs.getInt("Id"), rs.getInt("money"), xs);

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

	// obtiene el id de la cuenta pasada por par�metro y ejecuta la consulta DELETE
	// que borra la cuenta en cuesti�n
	public synchronized static void deleteAccount(account a) {
		int rs = 0;
		Connection con = Conexion.getConexion();

		if (con != null) {
			try {
				PreparedStatement q = con.prepareStatement(DELETE);
				q.setInt(1, a.getId());
				rs = q.executeUpdate();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// introduce los datos de la cuenta pasada por par�metro ejecutando la consulta
	// INSERT
	public synchronized static void createAccount(account a) {
		int result = -1;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection con = Conexion.getConexion();

		if (con != null) {
			try {
				ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, a.getId());
				ps.setInt(2, a.getMoney());
				ps.setDouble(3, a.getMiuser().getId());

				ps.executeUpdate();

				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					a.setId(rs.getInt(1));
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

	public synchronized static account actualizaDinero(account cuenta, boolean opcion, int cantidad) {

		account result = new account();
		Connection con = Conexion.getConexion();

		if (con != null) {

			if (opcion) {
				int addMoney = accountController.getAccoutByID(cuenta.getId()).getMoney() + cantidad;

				try {
					PreparedStatement q = con.prepareStatement(UPDATEMONEY);
					q.setInt(1, addMoney);
					q.setInt(2, cuenta.getId());
					q.executeUpdate();
					result = cuenta;
					result.setMoney(addMoney);
					q.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				int extractMoney = accountController.getAccoutByID(cuenta.getId()).getMoney() - cantidad;

				try {
					PreparedStatement q = con.prepareStatement(UPDATEMONEY);
					q.setInt(1, extractMoney);
					q.setInt(2, cuenta.getId());
					q.executeUpdate();
					result = cuenta;
					result.setMoney(extractMoney);
					q.close();

				} catch (SQLException e) {
					e.printStackTrace();
				}

			}
		}
		return result;
	}

	public synchronized static account getAccountByUserId(int user_id) {
		Connection con = Conexion.getConexion();
		account result = new account();

		if (con != null) {
			try {
				PreparedStatement q = con.prepareStatement(GETBYIDUSER);
				q.setInt(1, user_id);
				ResultSet rs = q.executeQuery();
				while (rs.next()) {
					result.setId(rs.getInt("user_id"));
					result.setMoney(rs.getInt("wallet"));
					result.setMiuser(userController.getUserById(rs.getInt(user_id)));
				}
				rs.close();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
