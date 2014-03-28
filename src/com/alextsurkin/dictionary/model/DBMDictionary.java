package com.alextsurkin.dictionary.model;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class DBMDictionary implements DBMDictionaryInterface {
	static private DBMDictionary instance;

	static public void init(Context ctx) {
		if (null == instance) {
			instance = new DBMDictionary(ctx);
		}
	}

	static public DBMDictionary getInstance() {
		return instance;
	}

	private DatabaseHelper helper;

	private DBMDictionary(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}

	private DatabaseHelper getHelper() {
		return helper;
	}

	@Override
	public List<Dictionary> getAllDictionarys() {
		List<Dictionary> dictionarys = getHelper().getDictionaryDao()
				.queryForAll();
		return dictionarys;
	}

	@Override
	public Dictionary saveDictionary(Dictionary dictionary) {
		if (dictionary == null)
			return null;
		if (dictionary.getId() == 0)
			getHelper().getDictionaryDao().create(dictionary);
		else
			getHelper().getDictionaryDao().update(dictionary);
		return dictionary;
	}

	@Override
	public Dictionary getDictionaryForId(int id) {
		return getHelper().getDictionaryDao().queryForId(id);
	}

	@Override
	public void deleteDictionary(Dictionary dictionary) {
		getHelper().getDictionaryDao().delete(dictionary);
	}

	@Override
	public void deleteDictionary(int id) {
		getHelper().getDictionaryDao().deleteById(id);
	}

	@Override
	public DictionaryValue getDictionaryValueWithId(int id) {
		return getHelper().getDictionaryValueDao().queryForId(id);
	}

	@Override
	public DictionaryValue saveDictionaryValue(DictionaryValue dictionaryValue) {
		if (dictionaryValue == null)
			return null;
		if (dictionaryValue.getId() == 0)
			getHelper().getDictionaryValueDao().create(dictionaryValue);
		else
			getHelper().getDictionaryValueDao().update(dictionaryValue);
		return dictionaryValue;
	}

	@Override
	public void deleteDictionaryValue(DictionaryValue dictionaryValue) {
		getHelper().getDictionaryValueDao().delete(dictionaryValue);
	}

	@Override
	public List<DictionaryValue> getAllDictionaryValues() {
		List<DictionaryValue> dictionaryValues = getHelper()
				.getDictionaryValueDao().queryForAll();
		return dictionaryValues;
	}

	@Override
	public Dictionary getDictionaryItemForEq(String fieldName, Object value) {
		Dictionary res = null;
		QueryBuilder<Dictionary, Integer> queryBuilder = getHelper().getDictionaryDao().queryBuilder();
		try {
			queryBuilder.where().eq(fieldName, value);
			PreparedQuery<Dictionary> preparedQuery = queryBuilder.prepare();
			List<Dictionary> list = getHelper().getDictionaryDao().query(preparedQuery);
			if (list.size() != 0)
				res = list.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
		
		/*Dictionary res = null;
		List<Dictionary> list = getHelper().getDictionaryDao().queryForEq(
				fieldName, value);
		if (list.size() != 0)
			res = list.get(0);*/
	}
}
