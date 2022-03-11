package socket.models;

import java.io.Serializable;

public class Send implements Serializable{
	private static final long serialVersionUID = 1L;
	protected int select;
	protected user obj1;
	protected account obj2;
	public Send(int select, user obj1, account obj2) {
		super();
		this.select = select;
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	public Send(int select, user obj1) {
		super();
		this.select = select;
		this.obj1 = obj1;
		this.obj2 = new account();
	}

	public int getSelect() {
		return select;
	}
	public void setSelect(int select) {
		this.select = select;
	}
	public user getObj1() {
		return obj1;
	}
	public void setObj1(user obj1) {
		this.obj1 = obj1;
	}
	public account getObj2() {
		return obj2;
	}
	public void setObj2(account obj2) {
		this.obj2 = obj2;
	}
}