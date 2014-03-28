package com.alextsurkin.bodyboost.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Связь комплекса и управжнения
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "complex_exercise")
public class ComplexExercise {
	public final static String ID_FIELD_NAME = "id";
	public final static String EXERCISE_ID_FIELD_NAME = "exercise_id";
	public final static String COMPLEX_ID_FIELD_NAME = "complex_id";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID_FIELD_NAME)
	private int id;
	@DatabaseField(foreign = true, columnName = COMPLEX_ID_FIELD_NAME, useGetSet = true)
	private Complex complex;
	@DatabaseField(foreign = true, columnName = EXERCISE_ID_FIELD_NAME, useGetSet = true)
	private Exercise exercise;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public ComplexExercise() {

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		try {
			if (complex != null)
				sb.append("complex=").append(complex.toString());
			if (exercise != null)
				sb.append("exercise=").append(exercise.toString());
		} catch (Exception e) {
		}
		return sb.toString();
	}
}