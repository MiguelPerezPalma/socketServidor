package socketServidor.models;

import java.util.List;

public class account {
	protected int id;
	protected int money;
	protected List<user> users;
	
	
	public account(int id, int money, List<user> users) {
		super();
		this.id = id;
		this.money = money;
		this.users = users;
	}

	public account() {
		super();
	}

	public List<user> getUsers() {
		return users;
	}

	public void setUsers(List<user> users) {
		this.users = users;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	
}
