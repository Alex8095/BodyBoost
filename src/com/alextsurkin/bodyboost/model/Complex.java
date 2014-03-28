package com.alextsurkin.bodyboost.model;

import com.alextsurkin.dictionary.model.DictionaryValue;
import com.alextsurkin.user.model.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Комплекс
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "complex")
public class Complex {
	public final static String ID_FIELD_NAME = "id";
	public final static String USER_ID_FIELD_NAME = "user_id";
	public final static String TYPE_COMPLEX_ID_FIELD_NAME = "type_complex_id";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID_FIELD_NAME)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = USER_ID_FIELD_NAME, useGetSet = true)
	private User user;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TYPE_COMPLEX_ID_FIELD_NAME, useGetSet = true)
	private DictionaryValue typeComplex;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DictionaryValue getTypeComplex() {
		return typeComplex;
	}

	public void setTypeComplex(DictionaryValue typeComplex) {
		this.typeComplex = typeComplex;
	}

	public Complex() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=").append(name);
		try {
			if (typeComplex != null)
				sb.append("typeComplex=").append(typeComplex.getName());
		} catch (Exception e) {
		}
		return sb.toString();
	}
}
