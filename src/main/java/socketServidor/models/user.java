package socketServidor.models;

public class user {
	protected int id;
	protected String name;
	protected String password;
	protected int wallet;
	protected boolean isOperator;
	

	public user(int id, String name, String password, int wallet, boolean isOperator) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.wallet = wallet;
		this.isOperator = isOperator;
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
	public boolean isOperator() {
		return isOperator;
	}
	public void setOperator(boolean isOperator) {
		this.isOperator = isOperator;
	}
	
	

}
