package com.alextsurkin.bodyboost.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.alextsurkin.user.model.User;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
/**
 * Тренировка
 * 
 * @author Tsurkin Alex
 * 
 */
@DatabaseTable(tableName = "traning")
public class Traning {
	public final static String ID_FIELD_NAME = "id";
	public final static String USER_ID_FIELD_NAME = "user_id";
	public final static String COMPLEX_ID_FIELD_NAME = "complex_id";

	@DatabaseField(generatedId = true, useGetSet = true, columnName = ID_FIELD_NAME)
	private int id;
	@DatabaseField(useGetSet = true)
	private double weightBefore;
	@DatabaseField(useGetSet = true)
	private double weightAfter;
	@DatabaseField(useGetSet = true)
	private Date date;
	@DatabaseField(useGetSet = true)
	private Date timeStart;
	@DatabaseField(useGetSet = true)
	private Date timeFinish;
	@DatabaseField(useGetSet = true)
	private int countAction;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = USER_ID_FIELD_NAME, useGetSet = true)
	private User user;
	@DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = COMPLEX_ID_FIELD_NAME, useGetSet = true)
	private Complex complex;

	private Collection<Exercise> listExercise;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getWeightBefore() {
		return weightBefore;
	}
	public void setWeightBefore(double weightBefore) {
		this.weightBefore = weightBefore;
	}
	public double getWeightAfter() {
		return weightAfter;
	}
	public void setWeightAfter(double weightAfter) {
		this.weightAfter = weightAfter;
	}
	public int getCountAction() {
		return countAction;
	}
	public void setCountAction(int countAction) {
		this.countAction = countAction;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Date timeStart) {
		this.timeStart = timeStart;
	}
	public Date getTimeFinish() {
		return timeFinish;
	}
	public void setTimeFinish(Date timeFinish) {
		this.timeFinish = timeFinish;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Complex getComplex() {
		return complex;
	}
	public void setComplex(Complex complex) {
		this.complex = complex;
	}
	public Collection<Exercise> getListExercise() {
		return listExercise;
	}
	public void setListExercise(Collection<Exercise> listExercise) {
		this.listExercise = listExercise;
	}
	public Traning() {
	}
	public double getDifferenceWeight() {
		return weightBefore - weightAfter;
	}
	public long getDifferenceMinutesTime() {
		long minutesDifference = (timeFinish.getTime() - timeStart.getTime()) / 1000 / 60;
		minutesDifference = Math.round(minutesDifference);
		return minutesDifference;
	}
}
