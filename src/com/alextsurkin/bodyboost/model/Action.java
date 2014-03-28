package com.alextsurkin.bodyboost.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Действие по упражнению (подход, вес)
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "action")
public class Action {
	public final static String ID_FIELD_NAME = "id";
	public final static String EXERCISE_ID_FIELD_NAME = "exercise_id";
	public final static String TRANING_ID_FIELD_NAME = "traning_id";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID_FIELD_NAME)
	public int id;
	@DatabaseField(useGetSet = true)
	public int approach;
	@DatabaseField(useGetSet = true)
	public double weight;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = EXERCISE_ID_FIELD_NAME, useGetSet = true)
	private Exercise exercise;
	@DatabaseField(foreign = true, foreignAutoRefresh = false, columnName = TRANING_ID_FIELD_NAME, useGetSet = true)
	private Traning traning;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getApproach() {
		return approach;
	}
	public void setApproach(int approach) {
		this.approach = approach;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public Exercise getExercise() {
		return exercise;
	}
	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	public Traning getTraning() {
		return traning;
	}
	public void setTraning(Traning traning) {
		this.traning = traning;
	}
	public Action() {
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id=").append(id);
		sb.append("approach=").append(approach);
		sb.append("weight=").append(weight);
		try {
			if (null != exercise)
				sb.append("exercise=").append(exercise.getName());
			if (null != traning)
				sb.append("traning=").append(traning.getId());
		} catch (Exception e) {
		}
		return sb.toString();
	}
}
