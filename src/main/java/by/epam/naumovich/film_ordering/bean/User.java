package by.epam.naumovich.film_ordering.bean;

import java.sql.Date;
import java.sql.Time;

/**
 * This bean class describes an user entity
 * 
 * @author Dmitry Naumovich
 * @version 1.0
 */
public class User {

	private int id;
	private String login;
	private String name;
	private String surname;
	private String password;
	private char sex;
	private char type;
	private Date regDate;
	private Time regTime;
	private Date birthDate;
	private String phone;
	private String email;
	private String about;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public char getType() {
		return type;
	}
	public void setType(char type) {
		this.type = type;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Time getRegTime() {
		return regTime;
	}
	public void setRegTime(Time regTime) {
		this.regTime = regTime;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@Override
	public int hashCode() {
		int hash = 11;
	    hash = 7 * hash + id;
	    hash = 19 * hash + (login != null ? login.hashCode() : 0);
	    hash = 19 * hash + (name != null ? name.hashCode() : 0);
	    hash = 19 * hash + (surname != null ? surname.hashCode() : 0);
	    hash = 13 * hash + (password != null ? password.hashCode() : 0);
	    hash = 17 * hash + ((Character)sex).hashCode();
	    hash = 53 * hash + ((Character)type).hashCode();
	    hash = 31 * hash + (phone != null ? phone.hashCode() : 0);
	    hash = 47 * hash + (email != null ? email.hashCode() : 0);
	    hash = 7 * hash + (regDate != null ? regDate.hashCode() : 0);
	    hash = 1 * hash + (regTime != null ? regTime.hashCode() : 0);
	    hash = 3 * hash + (birthDate != null ? birthDate.hashCode() : 0);
	    hash = 19 * hash + (about != null ? about.hashCode() : 0);
	    return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (null == obj) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}

		User user = (User) obj;
		if (id != user.id) {
			return false;
		}
		if (sex != user.sex) {
			return false;
		}
		if (type != user.type) {
			return false;
		}

		if ((null == login) ? (user.login != null) : !login.equals(user.login)) {
			return false;
		}
		if ((null == name) ? (user.name != null) : !name.equals(user.name)) {
			return false;
		}
		if ((null == surname) ? (user.surname != null) : !surname.equals(user.surname)) {
			return false;
		}
		if ((null == password) ? (user.password != null) : !password.equals(user.password)) {
			return false;
		}
		if ((null == regDate) ? (user.regDate != null) : !regDate.equals(user.regDate)) {
			return false;
		}
		if ((null == regTime) ? (user.regTime != null) : !regTime.equals(user.regTime)) {
			return false;
		}
		if ((null == birthDate) ? (user.birthDate != null) : !birthDate.equals(user.birthDate)) {
			return false;
		}
		if ((null == phone) ? (user.phone != null) : !phone.equals(user.phone)) {
			return false;
		}
		if ((null == email) ? (user.email != null) : !email.equals(user.email)) {
			return false;
		}
		if ((null == about) ? (user.about != null) : !about.equals(user.about)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.getClass().getSimpleName() + " Object {");
		result.append(" ID: " + id);
		result.append(", Login: " + login);
		result.append(", Name: " + name);
		result.append(", Surname: " + surname);
		result.append(", Password: " + password);
		result.append(", Sex: " + sex);
		result.append(", Type: " + type);
		result.append(", RegDate: " + regDate);
		result.append(", RegTime: " + regTime);
		if (birthDate != null) {
			result.append(", Birthdate: " + birthDate);
		}
		if (phone != null) {
			result.append(", Phone: " + phone);
		}
		result.append(", Email: " + email);
		if (about != null) {
			result.append(", About: " + about);
		}
		result.append("}");

		return result.toString();
	}
}
