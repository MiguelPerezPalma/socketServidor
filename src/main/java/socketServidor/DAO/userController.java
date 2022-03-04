package socketServidor.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import socketServidor.Coneection.Conexion;
import socketServidor.models.user;


public class userController {

	private static final String GETALL = "SELECT * FROM user";
	private static final String GETBYID = "SELECT * FROM user WHERE id=?";
	private static final String DELETE ="DELETE FROM user WHERE id=?";
	private final static String INSERT = "INSERT INTO user (id,name,password,wallet)" + "VALUES (?,?,?,?)";
	public static List<user> getAllUsers() {
		List<user> users = new ArrayList<user>();

		Connection con = Conexion.getConexion();
		if (con != null) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement(GETALL);
				rs = ps.executeQuery();
				while (rs.next()) {
					user miuser = new user();
					miuser.setId(rs.getInt("id"));
					miuser.setName(rs.getString("name"));
					miuser.setPassword(rs.getString("password"));
					miuser.setWallet(rs.getInt("wallet"));
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
	public static user getUserById(int id) {
		user resultado=new user();
		
		Connection con = Conexion.getConexion();
		if (con != null) {
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				ps = con.prepareStatement(GETBYID);
				ps.setInt(1,id);
				rs=ps.executeQuery();
				while (rs.next()) {
					
					resultado=new user(rs.getInt("id"),
							rs.getString("name"),
							rs.getString("password"),
							rs.getInt("wallet"),
							rs.getBoolean("isOperator"));
				
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
	
	
	
	public static void deleteAccount(user u) {
		int rs=0;
		Connection con = Conexion.getConexion();
		
		if (con != null) {
			try {
				
				PreparedStatement q=con.prepareStatement(DELETE);
				q.setInt(1,u.getId());
				rs =q.executeUpdate();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void createAccount(user u) {
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
				ps.setInt(4, u.getWallet());
				ps.setBoolean(5, u.isOperator());
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
	
}
