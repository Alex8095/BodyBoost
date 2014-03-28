package com.alextsurkin.bodyboost.model;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;

public class DBMBodyboost implements DBMBodyboostInterface {
	static private DBMBodyboost instance;

	static public void init(Context ctx) {
		if (null == instance) {
			instance = new DBMBodyboost(ctx);
		}
	}
	static public DBMBodyboost getInstance() {
		return instance;
	}
	private DatabaseHelper helper;

	private DBMBodyboost(Context ctx) {
		helper = new DatabaseHelper(ctx);
	}
	private DatabaseHelper getHelper() {
		return helper;
	}

	/* Complex */
	@Override
	public List<Complex> getAllComplexes() {
		List<Complex> complexes = getHelper().getComplexDao().queryForAll();
		return complexes;
	}
	@Override
	public List<Complex> getComplexForEq(String fieldName, Object value) {
		return getHelper().getComplexDao().queryForEq(fieldName, value);
	}
	@Override
	public Complex saveComplex(Complex complex) {
		if (complex == null)
			return null;
		if (complex.getId() == 0)
			getHelper().getComplexDao().create(complex);
		else
			getHelper().getComplexDao().update(complex);
		return complex;
	}
	@Override
	public Complex getComplexForId(int id) {
		return getHelper().getComplexDao().queryForId(id);
	}
	@Override
	public void deleteComplex(int id) {
		getHelper().getComplexDao().deleteById(id);
	}
	@Override
	public void deleteComplex(Complex complex) {
		getHelper().getComplexDao().delete(complex);
	}

	/* Exercise */
	@Override
	public List<Exercise> getAllExercises() {
		List<Exercise> exercises = getHelper().getExerciseDao().queryForAll();
		return exercises;
	}
	@Override
	public Exercise getExerciseForId(int id) {
		return getHelper().getExerciseDao().queryForId(id);
	}
	@Override
	public Exercise saveExercise(Exercise exercise) {
		if (exercise == null)
			return null;
		if (exercise.getId() == 0)
			getHelper().getExerciseDao().create(exercise);
		else
			getHelper().getExerciseDao().update(exercise);
		return exercise;
	}
	@Override
	public void deleteExercise(Exercise exercise) {
		getHelper().getExerciseDao().delete(exercise);
	}
	@Override
	public void deleteExercise(int id) {
		getHelper().getComplexDao().deleteById(id);
	}
	@Override
	public List<Exercise> getExerciseForEq(String fieldName, Object value) {
		return getHelper().getExerciseDao().queryForEq(fieldName, value);
	}

	// ComplexExercise
	@Override
	public List<ComplexExercise> lookupComplexExerciseesForComplex(Complex complex) {
		PreparedQuery<ComplexExercise> complexExerciseesForComplexQuery = null;
		try {
			QueryBuilder<Complex, Integer> complexQb = getHelper().getComplexDao().queryBuilder();
			complexQb.selectColumns(Complex.ID_FIELD_NAME).where().eq(Complex.ID_FIELD_NAME, complex.getId());
			QueryBuilder<ComplexExercise, Integer> complexExerciseQb = getHelper().getComplexExerciseDao().queryBuilder();
			complexExerciseQb.where().in(ComplexExercise.COMPLEX_ID_FIELD_NAME, complexQb);
			complexExerciseesForComplexQuery = complexExerciseQb.prepare();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getHelper().getComplexExerciseDao().query(complexExerciseesForComplexQuery);
	}
	@Override
	public List<ComplexExercise> getAllComplexExercisees() {
		return getHelper().getComplexExerciseDao().queryForAll();
	}
	@Override
	public void deleteComplexExercise(List<ComplexExercise> complexExercisees) {
		getHelper().getComplexExerciseDao().delete(complexExercisees);
	}
	@Override
	public void deleteComplexExercise(ComplexExercise complexExercise) {
		getHelper().getComplexExerciseDao().delete(complexExercise);
	}
	@Override
	public void createOrUpdateComplexExercise(ComplexExercise complexExercise) {
		getHelper().getComplexExerciseDao().createOrUpdate(complexExercise);
	}
	@Override
	public ComplexExercise getComplexExerciseForId(int id) {
		return getHelper().getComplexExerciseDao().queryForId(id);
	}
	@Override
	public List<Exercise> lookupExerciseesForComplex(Complex complex) {
		PreparedQuery<Exercise> exerciseForComplexQuery = null;
		try {
			QueryBuilder<ComplexExercise, Integer> complexExerciseQb = getHelper().getComplexExerciseDao().queryBuilder();
			complexExerciseQb.selectColumns(ComplexExercise.EXERCISE_ID_FIELD_NAME);
			SelectArg complexSelectArg = new SelectArg();
			complexExerciseQb.where().eq(ComplexExercise.COMPLEX_ID_FIELD_NAME, complexSelectArg);
			QueryBuilder<Exercise, Integer> exerciseQb = getHelper().getExerciseDao().queryBuilder();
			exerciseQb.where().in(Exercise.ID_FIELD_NAME, complexExerciseQb);
			exerciseForComplexQuery = exerciseQb.prepare();
			exerciseForComplexQuery.setArgumentHolderValue(0, complex);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getHelper().getExerciseDao().query(exerciseForComplexQuery);
	}
	
//	public PreparedQuery<Exercise> makeExercisesForComplexQuery() {
//		PreparedQuery<Exercise> ret = null;
//		try {
//			QueryBuilder<ComplexExercise, Integer> complexExerciseQb = getHelper().getComplexExerciseDao().queryBuilder();
//			complexExerciseQb.selectColumns(ComplexExercise.COMPLEX_ID_FIELD_NAME);
//			SelectArg complexSelectArg = new SelectArg();
//			complexExerciseQb.where().eq(ComplexExercise.COMPLEX_ID_FIELD_NAME, complexSelectArg);
//			QueryBuilder<Exercise, Integer> exerciseQb = getHelper().getExerciseDao().queryBuilder();
//			exerciseQb.where().in(Exercise.ID_FIELD_NAME, complexExerciseQb);
//			ret = exerciseQb.prepare();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return ret;
//	}

	// Traning
	@Override
	public Traning getTraningForId(int id) {
		return getHelper().getTraningDao().queryForId(id);
	}
	@Override
	public Traning saveTraning(Traning traning) {
		if (traning == null)
			return null;
		if (traning.getId() == 0)
			getHelper().getTraningDao().create(traning);
		else
			getHelper().getTraningDao().update(traning);
		return traning;
	}
	@Override
	public void deleteTraning(Traning traning) {
		getHelper().getTraningDao().delete(traning);
	}
	@Override
	public void deleteTraning(int id) {
		getHelper().getTraningDao().deleteById(id);
	}
	@Override
	public List<Traning> getTraningForEq(String fieldName, Object value) {
		return getHelper().getTraningDao().queryForEq(fieldName, value);
	}
	@Override
	public Traning getTraningForFull(int id) {
		Traning traning = getHelper().getTraningDao().queryForId(id);
		traning.setListExercise(lookupExerciseesForComplex(traning.getComplex()));
		if (traning.getListExercise() != null) {
			for (Exercise exercise : traning.getListExercise()) {
				exercise.setActionList(lookupActionForTraningExercise(traning, exercise));
			}
		}
		return traning;
	}
	@Override
	public Traning getTraningForFull(Traning traning) {
		traning.setListExercise(lookupExerciseesForComplex(traning.getComplex()));
		if (traning.getListExercise() != null) {
			for (Exercise exercise : traning.getListExercise()) {
				exercise.setActionList(lookupActionForTraningExercise(traning, exercise));
			}
		}
		return traning;
	}

	// Action
	@Override
	public Action saveAction(Action action) {
		if (action == null)
			return null;
		if (action.getId() == 0)
			getHelper().getActionDao().create(action);
		else
			getHelper().getActionDao().update(action);
		return action;
	}
	private PreparedQuery<Action> actionForTraningExerciseQuery = null;
	@Override
	public List<Action> lookupActionForTraningExercise(Traning traning, Exercise exercise) {
		try {
			QueryBuilder<Action, Integer> actionQb = getHelper().getActionDao().queryBuilder();
			actionQb.where().eq("traning_id", traning.getId()).and().eq("exercise_id", exercise.getId());
			actionForTraningExerciseQuery = actionQb.orderBy("approach", true).prepare();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getHelper().getActionDao().query(actionForTraningExerciseQuery);
	}
}