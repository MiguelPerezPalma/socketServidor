package socketServidor.models;

public class user {
	protected int id;
	protected String name;
	protected String password;
	protected int wallet;
	protected account miaccount;
	

	public user(int id, String name, String password, int wallet, account miaccount) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.wallet = wallet;
		this.miaccount = miaccount;
	}

	public user() {
		super();
	}

	public account getMiaccount() {
		return miaccount;
	}

	public void setMiaccount(account miaccount) {
		this.miaccount = miaccount;
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
