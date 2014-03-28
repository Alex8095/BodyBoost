package com.alextsurkin.dictionary.model;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Справочник
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "dictionary")
public class Dictionary {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String code;
	@ForeignCollectionField
	private ForeignCollection<DictionaryValue> dictionaryValueList;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDictionaryValueList(
			ForeignCollection<DictionaryValue> dictionaryValueList) {
		this.dictionaryValueList = dictionaryValueList;
	}

	public List<DictionaryValue> getDictionaryValueList() {
		ArrayList<DictionaryValue> itemList = new ArrayList<DictionaryValue>();
		for (DictionaryValue item : dictionaryValueList) {
			itemList.add(item);
		}
		return itemList;
	}

	public Dictionary() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id").append(id);
		sb.append("name=").append(name);
		sb.append("dictionaryValueList").append(dictionaryValueList);
		return sb.toString();
	}
}