package com.alextsurkin.bodyboost.model;

import java.sql.SQLException;
import java.util.List;

public interface DBMBodyboostInterface {
	// Complex
	public List<Complex> getAllComplexes();
	public List<Complex> getComplexForEq(String fieldName, Object value);
	public Complex saveComplex(Complex complex);
	public Complex getComplexForId(int id);
	public void deleteComplex(Complex complex);
	public void deleteComplex(int id);
	
	// Exercise
	public List<Exercise> getAllExercises();
	public List<Exercise> getExerciseForEq(String fieldName, Object value);
	public Exercise getExerciseForId(int id);
	public Exercise saveExercise(Exercise exercise);
	public void deleteExercise(Exercise exercise);
	public void deleteExercise(int id);
	
	// ComplexExercise
	public ComplexExercise getComplexExerciseForId(int id);
	public List<ComplexExercise> getAllComplexExercisees();
	public void deleteComplexExercise(List<ComplexExercise> complexExercisees);
	public void deleteComplexExercise(ComplexExercise complexExercise);
	public void createOrUpdateComplexExercise(ComplexExercise complexExercise);
	public List<Exercise> lookupExerciseesForComplex(Complex complex);
	public List<ComplexExercise> lookupComplexExerciseesForComplex(Complex complex);
	
	// Traning
	public Traning getTraningForId(int id);
	public Traning getTraningForFull(int id);
	public Traning getTraningForFull(Traning traning);
	public Traning saveTraning(Traning traning);
	public void deleteTraning(Traning traning);
	public void deleteTraning(int id);	
	public List<Traning> getTraningForEq(String fieldName, Object value);
	
	// Action
	public Action saveAction(Action action);
	public List<Action> lookupActionForTraningExercise(Traning traning, Exercise exercise);
}
