package usermanagement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User extends Person {
	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	public User() {
	}

	public User(String name, byte age, String email, String password) {
		super(name, age);
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "\nUsername : " + getName() + "\nAge : " + getAge() + "\nEmail : " + getEmail() + "\nPassword : "
				+ getPassword();
	}

}
