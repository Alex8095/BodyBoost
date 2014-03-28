package com.alextsurkin.bodyboost.adapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;

abstract class ExerciseActionsAbstractAdapter extends BaseAdapter {
	private final String LOG_TAG = getClass().getSimpleName();
	protected Traning traning;
	protected List<Exercise> exercisees;
	protected final Map<Integer, HashSet<Integer>> exerciseActionMap = new HashMap<Integer, HashSet<Integer>>();
	protected final Map<Integer, Double> actionMap = new HashMap<Integer, Double>();

	public Traning getTraning() {
		return traning;
	}
	public void setTraning(Traning traning) {
		this.traning = traning;
	}
	public List<Exercise> getExercisees() {
		return exercisees;
	}
	public void setExercisees(List<Exercise> exercisees) {
		this.exercisees = exercisees;
	}

	Context ctx;
	LayoutInflater lInflater;

	public ExerciseActionsAbstractAdapter(Context context, Traning traning, List<Exercise> exercisees) {
		ctx = context;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setTraning(traning);
		setExercisees(exercisees);
	}
	@Override
	public int getCount() {
		return exercisees.size();
	}
	@Override
	public Object getItem(int position) {
		return exercisees.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		return view;
	}
	/**
	 * 
	 * @param position
	 * @return
	 */
	Exercise getExercise(int position) {
		return ((Exercise) getItem(position));
	}
}