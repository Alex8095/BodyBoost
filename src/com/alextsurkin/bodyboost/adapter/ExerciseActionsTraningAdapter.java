package com.alextsurkin.bodyboost.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;

public class ExerciseActionsTraningAdapter extends ExerciseActionsAbstractAdapter {
	public ExerciseActionsTraningAdapter(Context context, Traning traning, List<Exercise> exercisees) {
		super(context, traning, exercisees);
		this.context = context;
	}
	static class ViewHolder {
		Exercise exercise;
		protected TextView tvExercise;
		protected EditText etAction;
	}
	private Context context;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		Exercise exercise = getExercise(position);
		if (view == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = infalInflater.inflate(R.layout.traning_list_row, null);

			if (exercise.getActionList().size() > 0)
				for (Action action : exercise.getActionList()) {
					TextView tv = new TextView(context);
					tv.setId(action.hashCode());
					tv.setText(Double.toString(action.getWeight()));
					((LinearLayout) view.findViewById(R.id.llExerciseAction)).addView(tv);
				}
		}
		TextView tvExerciseName = (TextView) view.findViewById(R.id.tvExerciseName);
		tvExerciseName.setText(exercise.getName());
		TextView tvExerciseType = (TextView) view.findViewById(R.id.tvExerciseType);
		tvExerciseType.setText(exercise.getTypeExercise().getName());
		return view;
	}
}