package socketServidor.models;

public class user {
	protected int id;
	protected String name;
	protected String password;
	protected int wallet;
	

	public user(int id, String name, String password, int wallet) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.wallet = wallet;
	}
	
	public user(int id, int wallet) {
		super();
		this.id = id;
		this.wallet = wallet;
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
	public int getWallet() {
		return wallet;
	}
	public void setWallet(int wallet) {
		this.wallet = wallet;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	
	

}
