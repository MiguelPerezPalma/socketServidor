package socketServidor.models;

import java.util.List;

public class account {
	protected int id;
	protected int money;
	protected user miuser;
	public account(int id, int money, user miuser) {
		super();
		this.id = id;
		this.money = money;
		this.miuser = miuser;
	}
	
	public account() {
		super();
	}

	public account(int money, user miuser) {
		super();
		this.money = money;
		this.miuser = miuser;
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
	public user getMiuser() {
		return miuser;
	}
	public void setMiuser(user miuser) {
		this.miuser = miuser;
	}
	@Override
	public String toString() {
		return "account [id=" + id + ", money=" + money + ", miuser=" + miuser + "]";
	}
	
	
	
	
}
