package socket.models;

import java.io.Serializable;
import java.util.Objects;

public class user implements Serializable {
	private static final long serialVersionUID = 1L;
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

	@Override
	public int hashCode() {
		return Objects.hash(id, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		user other = (user) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", password=" + password + "]";
	}
	

}
