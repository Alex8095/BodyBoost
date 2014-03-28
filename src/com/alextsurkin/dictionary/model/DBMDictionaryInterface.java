package com.alextsurkin.dictionary.model;

import java.sql.SQLException;
import java.util.List;

public interface DBMDictionaryInterface {
	public List<Dictionary> getAllDictionarys();

	public Dictionary saveDictionary(Dictionary dictionary);

	public Dictionary getDictionaryForId(int id);

	public Dictionary getDictionaryItemForEq(String fieldName, Object value);

	public void deleteDictionary(Dictionary dictionary);

	public void deleteDictionary(int id);

	public List<DictionaryValue> getAllDictionaryValues();

	public DictionaryValue saveDictionaryValue(DictionaryValue dictionaryValue);

	public void deleteDictionaryValue(DictionaryValue dictionaryValue);

	public DictionaryValue getDictionaryValueWithId(int id);
}
