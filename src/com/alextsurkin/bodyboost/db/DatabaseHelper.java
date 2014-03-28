package com.alextsurkin.bodyboost.db;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.ComplexExercise;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.alextsurkin.user.model.User;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper class used to manage the creation and upgrading of your
 * database. This class also usually provides the DAOs used by the other
 * classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "bodyBoost.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 35;

	// the DAO object
	private RuntimeExceptionDao<Dictionary, Integer> dictionaryRuntimeDao = null;
	private RuntimeExceptionDao<DictionaryValue, Integer> dictionaryValueRuntimeDao = null;
	private RuntimeExceptionDao<Action, Integer> actionRuntimeDao = null;
	private RuntimeExceptionDao<Complex, Integer> complexRuntimeDao = null;
	private RuntimeExceptionDao<Exercise, Integer> exerciseRuntimeDao = null;
	private RuntimeExceptionDao<Traning, Integer> traningRuntimeDao = null;
	private RuntimeExceptionDao<User, Integer> userRuntimeDao = null;
	private RuntimeExceptionDao<ComplexExercise, Integer> complexExerciseRuntimeDao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i("test", "onCreate");
			TableUtils.createTable(connectionSource, Dictionary.class);
			TableUtils.createTable(connectionSource, DictionaryValue.class);
			TableUtils.createTable(connectionSource, Complex.class);
			TableUtils.createTable(connectionSource, Exercise.class);
			TableUtils.createTable(connectionSource, Traning.class);
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Action.class);
			TableUtils.createTable(connectionSource, ComplexExercise.class);
			
			createDefaultTableData();
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	public void createDefaultTableData() {
		Log.i("test", "createDefaultTableData");
		// dictionarys
		Dictionary typeComplexDictionary = new Dictionary();
		typeComplexDictionary.setCode("complex_type");
		typeComplexDictionary.setName("complex type");
		getDictionaryDao().create(typeComplexDictionary);
		Dictionary typeExerciseDictionary = new Dictionary();
		typeExerciseDictionary.setCode("exercise_type");
		typeExerciseDictionary.setName("exercise type");
		getDictionaryDao().create(typeExerciseDictionary);

		// dictionary values
		DictionaryValue ct1 = new DictionaryValue();
		ct1.setName("complex type 1");
		ct1.setDictionary(typeComplexDictionary);
		getDictionaryValueDao().create(ct1);
		DictionaryValue ct2 = new DictionaryValue();
		ct2.setName("complex type 2");
		ct2.setDictionary(typeComplexDictionary);
		getDictionaryValueDao().create(ct2);
		DictionaryValue ex1 = new DictionaryValue();
		ex1.setName("exercise type 1");
		ex1.setDictionary(typeExerciseDictionary);
		getDictionaryValueDao().create(ex1);
		DictionaryValue ex2 = new DictionaryValue();
		ex2.setName("exercise type 2");
		ex2.setDictionary(typeExerciseDictionary);
		getDictionaryValueDao().create(ex2);
		// complexes
		Complex complex = new Complex();
		complex.setName("complex 1");
		complex.setTypeComplex(ct1);
		getComplexDao().create(complex);
		// exercisees
		Exercise exercise1 = new Exercise();
		exercise1.setName("exercise 1");
		exercise1.setTypeExercise(ex1);
		getExerciseDao().create(exercise1);
		Exercise exercise2 = new Exercise();
		exercise2.setName("exercise 2");
		exercise2.setTypeExercise(ex2);
		getExerciseDao().create(exercise2);
		// complex -exercise
		ComplexExercise complexExercise = new ComplexExercise();
		complexExercise.setComplex(complex);
		complexExercise.setExercise(exercise1);
		getComplexExerciseDao().create(complexExercise);
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Dictionary.class, true);
			TableUtils.dropTable(connectionSource, DictionaryValue.class, true);
			TableUtils.dropTable(connectionSource, Action.class, true);
			TableUtils.dropTable(connectionSource, Complex.class, true);
			TableUtils.dropTable(connectionSource, Exercise.class, true);
			TableUtils.dropTable(connectionSource, Traning.class, true);
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, ComplexExercise.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao
	 * for our SimpleData class. It will create it or just give the cached
	 * value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<Dictionary, Integer> getDictionaryDao() {
		if (dictionaryRuntimeDao == null)
			dictionaryRuntimeDao = getRuntimeExceptionDao(Dictionary.class);
		return dictionaryRuntimeDao;
	}

	public RuntimeExceptionDao<DictionaryValue, Integer> getDictionaryValueDao() {
		if (dictionaryValueRuntimeDao == null)
			dictionaryValueRuntimeDao = getRuntimeExceptionDao(DictionaryValue.class);
		return dictionaryValueRuntimeDao;
	}

	public RuntimeExceptionDao<Action, Integer> getActionDao() {
		if (actionRuntimeDao == null)
			actionRuntimeDao = getRuntimeExceptionDao(Action.class);
		return actionRuntimeDao;
	}

	public RuntimeExceptionDao<Complex, Integer> getComplexDao() {
		if (complexRuntimeDao == null)
			complexRuntimeDao = getRuntimeExceptionDao(Complex.class);
		return complexRuntimeDao;
	}

	public RuntimeExceptionDao<Exercise, Integer> getExerciseDao() {
		if (exerciseRuntimeDao == null)
			exerciseRuntimeDao = getRuntimeExceptionDao(Exercise.class);
		return exerciseRuntimeDao;
	}

	public RuntimeExceptionDao<Traning, Integer> getTraningDao() {
		if (traningRuntimeDao == null)
			traningRuntimeDao = getRuntimeExceptionDao(Traning.class);
		return traningRuntimeDao;
	}

	public RuntimeExceptionDao<User, Integer> getUserDao() {
		if (userRuntimeDao == null)
			userRuntimeDao = getRuntimeExceptionDao(User.class);
		return userRuntimeDao;
	}

	public RuntimeExceptionDao<ComplexExercise, Integer> getComplexExerciseDao() {
		if (complexExerciseRuntimeDao == null)
			complexExerciseRuntimeDao = getRuntimeExceptionDao(ComplexExercise.class);
		return complexExerciseRuntimeDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		dictionaryRuntimeDao = null;
		dictionaryValueRuntimeDao = null;
		actionRuntimeDao = null;
		complexRuntimeDao = null;
		exerciseRuntimeDao = null;
		traningRuntimeDao = null;
		userRuntimeDao = null;
		complexExerciseRuntimeDao = null;
	}
}
