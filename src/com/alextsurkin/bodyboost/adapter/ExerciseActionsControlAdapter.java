package com.alextsurkin.bodyboost.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;

public class ExerciseActionsControlAdapter extends ExerciseActionsAbstractAdapter {
	public ExerciseActionsControlAdapter(Context context, Traning traning, List<Exercise> exercisees) {
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
		if (view == null) {
			view = lInflater.inflate(R.layout.exercise_actions_row, parent, false);
		}
		Exercise exercise = getExercise(position);
		((TextView) view.findViewById(R.id.tvExerciseName)).setText(exercise.getName());
		if (!exerciseActionMap.containsKey(exercise.hashCode())) {
			HashSet<Integer> exerciseActionHashSet = new HashSet<Integer>();

			List<Action> actions = null;
			if (getExercise(position).getActionList() != null) {
				actions = (List<Action>) getExercise(position).getActionList();
			}

			for (int i = 0; i < traning.getCountAction(); i++) {
				int editTextId = exercise.hashCode() + i;
				EditText editTextItem = new EditText(ctx);
				editTextItem.setId(editTextId);

				if (actions != null)
					editTextItem.setHint((actions.get(i) != null ? Double.toString(actions.get(i).getWeight()) : ""));
				
				((LinearLayout) view.findViewById(R.id.llExerciseAction)).addView(editTextItem);
				editTextItem.setOnFocusChangeListener(myOnFocusChangeListener);
				exerciseActionHashSet.add(editTextId);
				actionMap.put(editTextId, null);
			}
			exerciseActionMap.put(exercise.hashCode(), exerciseActionHashSet);
		}
		return view;
	}
	/**
	 * обработчик изминения поля
	 */
	OnFocusChangeListener myOnFocusChangeListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			EditText editTextItem = (EditText) v.findViewById(v.getId());
			if (hasFocus) {
				if (!editTextItem.getText().toString().isEmpty()) {
					Double value = Double.parseDouble(editTextItem.getText().toString());
					actionMap.put(v.getId(), value);
				}
			}
		}
	};

	public List<com.alextsurkin.bodyboost.model.Action> getResult() {
		List<com.alextsurkin.bodyboost.model.Action> result = new ArrayList<com.alextsurkin.bodyboost.model.Action>();
		for (Exercise exercise : exercisees) {
			int exerciseHashCode = exercise.hashCode();
			if (exerciseActionMap.containsKey(exerciseHashCode)) {
				for (int i = 0; i < traning.getCountAction(); i++) {
					int etId = exerciseHashCode + i;
					if (actionMap.containsKey(etId)) {
						com.alextsurkin.bodyboost.model.Action action = new com.alextsurkin.bodyboost.model.Action();
						action.setExercise(exercise);
						action.setApproach(i);
						if (actionMap.get(etId) != null)
							action.setWeight(actionMap.get(etId));
						result.add(action);
					}
				}
			}
		}
		return result;
	}
}