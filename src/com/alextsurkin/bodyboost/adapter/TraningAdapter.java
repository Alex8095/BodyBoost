package com.alextsurkin.bodyboost.adapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;

//http://www.mysamplecode.com/2012/10/android-expandablelistview-example.html
public class TraningAdapter extends BaseExpandableListAdapter {
	private Context context;
	private List<Traning> tranings;
	protected final Map<Integer, HashSet<Integer>> exerciseActionMap = new HashMap<Integer, HashSet<Integer>>();

	public TraningAdapter(Context context, List<Traning> tranings) {
		this.context = context;
		this.tranings = tranings;
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		List<Exercise> exercisees = (List<Exercise>) tranings.get(groupPosition).getListExercise();
		return exercisees.get(childPosition);
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view, ViewGroup parent) {
		Exercise exercise = (Exercise) getChild(groupPosition, childPosition);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.traning_list_row, null);
		}

		if (!exerciseActionMap.containsKey(exercise.hashCode())) {
			if (exercise.getActionList().size() > 0)
				for (Action action : exercise.getActionList()) {
					TextView tv = new TextView(context);
					tv.setId(action.hashCode());
					tv.setText(Double.toString(action.getWeight()));
					((LinearLayout) view.findViewById(R.id.llExerciseAction)).addView(tv);
				}
			exerciseActionMap.put(exercise.hashCode(), new HashSet<Integer>());
		}

		TextView tvExerciseName = (TextView) view.findViewById(R.id.tvExerciseName);
		tvExerciseName.setText(exercise.getName());
		TextView tvExerciseType = (TextView) view.findViewById(R.id.tvExerciseType);
		tvExerciseType.setText(exercise.getTypeExercise().getName());
		return view;
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		List<Exercise> exercisees = (List<Exercise>) tranings.get(groupPosition).getListExercise();
		return exercisees.size();
	}
	@Override
	public Object getGroup(int groupPosition) {
		return tranings.get(groupPosition);
	}
	@Override
	public int getGroupCount() {
		return tranings.size();
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
		Traning traning = (Traning) getGroup(groupPosition);
		if (view == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inf.inflate(R.layout.traning_list_heading, null);
		}
		TextView tvTraningComplexName = (TextView) view.findViewById(R.id.tvTraningComplexName);
		tvTraningComplexName.setText(traning.getComplex().getName());
		TextView tvTraningDate = (TextView) view.findViewById(R.id.tvTraningDate);
		tvTraningDate.setText(traning.getDate().toLocaleString());
		TextView tvTraningDifferenceTime = (TextView) view.findViewById(R.id.tvTraningDifferenceTime);
		tvTraningDifferenceTime.setText(Long.toString(traning.getDifferenceMinutesTime()));
		TextView tvTraningDifferenceWeight = (TextView) view.findViewById(R.id.tvTraningDifferenceWeight);
		tvTraningDifferenceWeight.setText(Double.toString(traning.getDifferenceWeight()));
		return view;
	}
	@Override
	public boolean hasStableIds() {
		return true;
	}
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
