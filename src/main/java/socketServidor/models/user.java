package socketServidor.models;

public class user {
	protected int id;
	protected String name;
	protected String password;

	public user(int id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public user(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public user() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
