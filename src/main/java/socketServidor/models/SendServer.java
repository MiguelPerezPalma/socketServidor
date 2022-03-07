package socketServidor.models;

import java.io.Serializable;

public class SendServer implements Serializable{

	private static final long serialVersionUID = 1L;
	protected int opcion;
	protected Object object1;
	protected Object object2;
	protected int money;
	
	public SendServer(int opcion, Object object1, Object object2, int money) {
		super();
		this.opcion = opcion;
		this.object1 = object1;
		this.object2 = object2;
		this.money = money;
	}

	public SendServer(int opcion, Object object1) {
		super();
		this.opcion = opcion;
		this.object1 = object1;
	}

	public SendServer(int opcion, Object object1, Object object2) {
		super();
		this.opcion = opcion;
		this.object1 = object1;
		this.object2 = object2;
	}

	public SendServer(int opcion, Object object1, int money) {
		super();
		this.opcion = opcion;
		this.object1 = object1;
		this.money = money;
	}

	public int getOpcion() {
		return opcion;
	}

	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}

	public Object getObject1() {
		return object1;
	}

	public void setObject1(Object object1) {
		this.object1 = object1;
	}

	public Object getObject2() {
		return object2;
	}

	public void setObject2(Object object2) {
		this.object2 = object2;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "SendServer [opcion=" + opcion + ", object1=" + object1 + ", object2=" + object2 + ", money=" + money
				+ "]";
	}
	
	
	
}
