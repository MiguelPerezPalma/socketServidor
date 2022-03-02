package socketServidor.DAO;

import socketServidor.Coneection.Conexion;
import socketServidor.models.account;
import socketServidor.models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class accountController {
	private static final String GETALL = "SELECT * FROM account";
	private static final String GETBYID = "SELECT * FROM account WHERE id=?";
	private static final String DELETE ="DELETE FROM account WHERE id=?";
	private final static String INSERT = "INSERT INTO account (id,money,user_id)" + "VALUES (?,?,?)";
	public static List<account> getAllAccounts() {
		List<account> accounts = new ArrayList<account>();

		Connection con = Conexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
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
	
	
	public account getAccoutByID(int id) {
		account resultado=new account();
		
		Connection con = Conexion.getConexion();
		if (con != null) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1,id);
				rs=ps.executeQuery();
				while (rs.next()) {
					userController x=new userController();
					user xs=x.getUserById(rs.getInt("Id_Genero"));
					
					resultado=new account(rs.getInt("Id"),
							rs.getInt("money"),
							xs);
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					ps.close();
					rs.close();
				}catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		return resultado;
	}
	
	public static void deleteAccount(account a) {
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				
				PreparedStatement q=con.prepareStatement(DELETE);
				q.setInt(1,a.getId());
				rs =q.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void createAccount(account a) {
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
}
