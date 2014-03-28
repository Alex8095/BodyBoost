package com.alextsurkin.dictionary.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Значение справочника
 * @author Tsurkin Alex
 *
 */
@DatabaseTable(tableName = "dictionary_value")
public class DictionaryValue {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "dictionary_id")
	private Dictionary dictionary;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public DictionaryValue() {
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=").append(name);
		sb.append("Dictionary=").append(dictionary);
		return sb.toString();
	}
}
