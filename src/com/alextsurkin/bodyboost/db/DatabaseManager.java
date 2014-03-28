package com.alextsurkin.bodyboost.db;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.ComplexExercise;
import com.alextsurkin.bodyboost.model.DBMBodyboost;
import com.alextsurkin.bodyboost.model.DBMBodyboostInterface;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.alextsurkin.dictionary.model.DBMDictionary;
import com.alextsurkin.dictionary.model.DBMDictionaryInterface;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.alextsurkin.user.model.DBMUser;

public class DatabaseManager implements DBMBodyboostInterface, DBMDictionaryInterface {

	static private DBMBodyboost BodyboostManager;
	static private DBMDictionary DictionaryManager;
	static private DBMUser UserManager;
	static private DatabaseManager instance;

	static public void init(Context ctx) {
		if (null == instance) {
			instance = new DatabaseManager(ctx);
			BodyboostManager.init(ctx);
			DictionaryManager.init(ctx);
			UserManager.init(ctx);
		}
	}
	static public DatabaseManager getInstance() {
		return instance;
	}
	private DatabaseHelper helper;
	private DatabaseManager(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}
	private DatabaseHelper getHelper() {
		return helper;
	}

	/* Dictionary */
	@Override
	public List<Dictionary> getAllDictionarys() {
		return DictionaryManager.getInstance().getAllDictionarys();
	}
	@Override
	public Dictionary getDictionaryItemForEq(String fieldName, Object value) {
		return DictionaryManager.getInstance().getDictionaryItemForEq(fieldName, value);
	}
	@Override
	public Dictionary saveDictionary(Dictionary dictionary) {
		return DictionaryManager.getInstance().saveDictionary(dictionary);
	}
	@Override
	public Dictionary getDictionaryForId(int id) {
		return DictionaryManager.getInstance().getDictionaryForId(id);
	}
	@Override
	public void deleteDictionary(Dictionary dictionary) {
		DictionaryManager.getInstance().deleteDictionary(dictionary);
	}
	@Override
	public void deleteDictionary(int id) {
		DictionaryManager.getInstance().deleteDictionary(id);
	}

	/* DictionaryValue */
	@Override
	public DictionaryValue getDictionaryValueWithId(int id) {
		return DictionaryManager.getInstance().getDictionaryValueWithId(id);
	}
	@Override
	public DictionaryValue saveDictionaryValue(DictionaryValue dictionaryValue) {
		return DictionaryManager.getInstance().saveDictionaryValue(dictionaryValue);
	}
	@Override
	public void deleteDictionaryValue(DictionaryValue dictionaryValue) {
		DictionaryManager.getInstance().deleteDictionaryValue(dictionaryValue);
	}
	@Override
	public List<DictionaryValue> getAllDictionaryValues() {
		return DictionaryManager.getInstance().getAllDictionaryValues();
	}
	/* Complex */
	@Override
	public List<Complex> getAllComplexes() {
		return BodyboostManager.getInstance().getAllComplexes();
	}
	@Override
	public Complex saveComplex(Complex complex) {
		return BodyboostManager.getInstance().saveComplex(complex);
	}
	@Override
	public Complex getComplexForId(int id) {
		return BodyboostManager.getInstance().getComplexForId(id);
	}
	@Override
	public void deleteComplex(Complex complex) {
		BodyboostManager.getInstance().deleteComplex(complex);
	}
	@Override
	public void deleteComplex(int id) {
		BodyboostManager.getInstance().deleteComplex(id);
	}

	/* Exercise */
	@Override
	public List<Exercise> getAllExercises() {
		return BodyboostManager.getInstance().getAllExercises();
	}
	@Override
	public Exercise saveExercise(Exercise exercise) {
		return BodyboostManager.getInstance().saveExercise(exercise);
	}
	@Override
	public void deleteExercise(Exercise exercise) {
		BodyboostManager.getInstance().deleteExercise(exercise);
	}
	@Override
	public void deleteExercise(int id) {
		BodyboostManager.getInstance().deleteExercise(id);
	}
	@Override
	public Exercise getExerciseForId(int id) {
		return BodyboostManager.getInstance().getExerciseForId(id);
	}
	@Override
	public List<Complex> getComplexForEq(String fieldName, Object value) {
		return BodyboostManager.getInstance().getComplexForEq(fieldName, value);
	}
	@Override
	public List<Exercise> getExerciseForEq(String fieldName, Object value) {
		return BodyboostManager.getInstance().getExerciseForEq(fieldName, value);
	}
	
	// ComplexExercise
	@Override
	public List<ComplexExercise> getAllComplexExercisees() {
		return BodyboostManager.getInstance().getAllComplexExercisees();
	}
	@Override
	public void deleteComplexExercise(List<ComplexExercise> complexExercisees) {
		BodyboostManager.getInstance().deleteComplexExercise(complexExercisees);
	}
	@Override
	public void deleteComplexExercise(ComplexExercise complexExercise) {
		BodyboostManager.getInstance().deleteComplexExercise(complexExercise);
	}
	@Override
	public void createOrUpdateComplexExercise(ComplexExercise complexExercise) {
		BodyboostManager.getInstance().createOrUpdateComplexExercise(complexExercise);
	}
	@Override
	public ComplexExercise getComplexExerciseForId(int id) {
		return BodyboostManager.getInstance().getComplexExerciseForId(id);
	}
	@Override
	public List<Exercise> lookupExerciseesForComplex(Complex complex) {
		return BodyboostManager.getInstance().lookupExerciseesForComplex(complex);
	}
	@Override
	public List<ComplexExercise> lookupComplexExerciseesForComplex(Complex complex) {
		return BodyboostManager.getInstance().lookupComplexExerciseesForComplex(complex);
	}

	// Traning
	@Override
	public Traning getTraningForId(int id) {
		return BodyboostManager.getInstance().getTraningForId(id);
	}
	@Override
	public Traning saveTraning(Traning traning) {
		return BodyboostManager.getInstance().saveTraning(traning);
	}
	@Override
	public void deleteTraning(int id) {
		BodyboostManager.getInstance().deleteTraning(id);
	}
	@Override
	public void deleteTraning(Traning traning) {
		BodyboostManager.getInstance().deleteTraning(traning);
	}
	@Override
	public List<Traning> getTraningForEq(String fieldName, Object value) {
		return BodyboostManager.getInstance().getTraningForEq(fieldName, value);
	}
	@Override
	public Traning getTraningForFull(int id) {
		return BodyboostManager.getInstance().getTraningForFull(id);
	}
	@Override
	public Traning getTraningForFull(Traning traning) {
		return BodyboostManager.getInstance().getTraningForFull(traning);
	}

	// Action
	@Override
	public Action saveAction(Action action) {
		return BodyboostManager.getInstance().saveAction(action);
	}
	public List<Action> lookupActionForTraningExercise(Traning traning, Exercise exercise) {
		return BodyboostManager.getInstance().lookupActionForTraningExercise(traning, exercise);
	}
}