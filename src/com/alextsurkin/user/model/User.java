package com.alextsurkin.user.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Пользователь
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "user")
public class User {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String login;
	@DatabaseField
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User() {

	}
}
