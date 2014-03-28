package com.alextsurkin.bodyboost.model;

import java.util.Collection;

import com.alextsurkin.dictionary.model.DictionaryValue;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * ”пражнение
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "exercise")
public class Exercise {
	public final static String ID_FIELD_NAME = "id";
	public final static String TYPE_EXERCISE_ID_FIELD_NAME = "type_exercise_id";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID_FIELD_NAME)
	private int id;
	@DatabaseField(useGetSet = true)
	private String name;
	@DatabaseField(useGetSet = true)
	private int pos;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TYPE_EXERCISE_ID_FIELD_NAME, useGetSet = true)
	private DictionaryValue typeExercise;
	private Collection<Action> actionList;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public DictionaryValue getTypeExercise() {
		return typeExercise;
	}
	public void setTypeExercise(DictionaryValue typeExercise) {
		this.typeExercise = typeExercise;
	}
	public Collection<Action> getActionList() {
		return actionList;
	}
	public void setActionList(Collection<Action> actionList) {
		this.actionList = actionList;
	}
	public Exercise() {
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("name=").append(name);
		if (typeExercise != null)
			sb.append("typeExercise=").append(typeExercise.getName());
		return sb.toString();
	}
}
