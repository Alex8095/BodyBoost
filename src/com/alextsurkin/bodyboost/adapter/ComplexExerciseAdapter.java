package com.alextsurkin.bodyboost.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.ComplexExercise;
import com.alextsurkin.bodyboost.model.Exercise;
import com.bodyboost.R;

public class ComplexExerciseAdapter extends BaseAdapter {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final String EXERCISES = "exercise";
	private static final String EXERCISES_SELECTED = "exercise_selected";
	private static final String CE = "complex_exercises";
	private static final String CE_CREATE_UPDATE = "complex_exercises_create_update";
	private static final String CE_DELETE = "complex_exercises_delete";
	Context ctx;
	LayoutInflater lInflater;
	private Complex complex;
	// коллекция упражнений
	private Map<String, List<Exercise>> exerciseMap = new HashMap<String, List<Exercise>>();
	// коллекция связей комплекс - упражнение
	private Map<String, List<ComplexExercise>> complexExerciseMap = new HashMap<String, List<ComplexExercise>>();

	public Map<String, List<Exercise>> getExerciseMap() {
		return exerciseMap;
	}
	public void setExerciseMap(Map<String, List<Exercise>> exerciseMap) {
		this.exerciseMap = exerciseMap;
	}
	public Map<String, List<ComplexExercise>> getComplexExerciseMap() {
		return complexExerciseMap;
	}
	public void setComplexExerciseMap(Map<String, List<ComplexExercise>> complexExerciseMap) {
		this.complexExerciseMap = complexExerciseMap;
	}
	public List<ComplexExercise> getComplexExercise(String key) {
		return complexExerciseMap.get(key);
	}
	// конструктор
	public ComplexExerciseAdapter(Context context, List<Exercise> exerciseList, Complex complexItem, List<ComplexExercise> complexExerciseList) {
		ctx = context;
		lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		exerciseMap.put(EXERCISES, exerciseList);
		exerciseMap.put(EXERCISES_SELECTED, new ArrayList<Exercise>());
		complexExerciseMap.put(CE, complexExerciseList);
		complexExerciseMap.put(CE_CREATE_UPDATE, new ArrayList<ComplexExercise>());
		complexExerciseMap.put(CE_DELETE, new ArrayList<ComplexExercise>());
		complex = complexItem;
		// формируем коллекцию уже выбранных упражнений
		createSelectedExercises();
	}
	@Override
	public int getCount() {
		return exerciseMap.get(EXERCISES).size();
	}
	@Override
	public Object getItem(int position) {
		return exerciseMap.get(EXERCISES).get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null)
			view = lInflater.inflate(R.layout.complex_exercise_list_row, parent, false);
		Exercise e = getExercise(position);
		((TextView) view.findViewById(R.id.tvExerciseName)).setText(e.getName());
		((TextView) view.findViewById(R.id.tvExerciseType)).setText(e.getTypeExercise().getName());
		CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
		cbBuy.setOnCheckedChangeListener(myCheckChangList);
		cbBuy.setTag(position);
		Boolean checked = false;
		if (exerciseMap.get(EXERCISES_SELECTED).size() > 0)
			checked = exerciseMap.get(EXERCISES_SELECTED).contains(e);
		cbBuy.setChecked(checked);
		Log.d(LOG_TAG, "======================= view ");
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
	/**
	 * формирование коллекции уже выбранных упражнений
	 */
	private void createSelectedExercises() {
		if (complexExerciseMap.get(CE).size() > 0) {
			Map<Boolean, ComplexExercise> checkedExercise = new HashMap<Boolean, ComplexExercise>();
			for (Exercise exercise : exerciseMap.get(EXERCISES)) {
				checkedExercise = getCheckedExercise(exercise);
				if (checkedExercise.containsKey(true))
					exerciseMap.get(EXERCISES_SELECTED).add(exercise);
			}
		}
	}
	/**
	 * проверка на существование упражнения в коллекции комплекс - упражнение
	 * 
	 * @param exercise
	 * @return
	 */
	private Map<Boolean, ComplexExercise> getCheckedExercise(Exercise exercise) {
		Map<Boolean, ComplexExercise> ret = new HashMap<Boolean, ComplexExercise>();
		if (complexExerciseMap.get(CE).size() == 0)
			ret.put(false, null);
		for (ComplexExercise ce : complexExerciseMap.get(CE)) {
			if (ce.getExercise().getId() == exercise.getId()) {
				ret.put(true, ce);
				return ret;
			}
		}
		return ret;
	}
	/**
	 * формирование результата
	 */
	public void createResult() {
		// копирование всех элементов коллекции уже выбранных в удаленные
		complexExerciseMap.get(CE_DELETE).addAll(complexExerciseMap.get(CE));
		if (exerciseMap.get(EXERCISES_SELECTED).size() > 0) {
			for (Exercise exercise : exerciseMap.get(EXERCISES_SELECTED)) {
				// проверка на существование упражнения в коллекции комплекс -
				// упражнение
				Map<Boolean, ComplexExercise> checkedExercise = getCheckedExercise(exercise);
				if (checkedExercise.containsKey(true)) {
					// копирование элемента к-у в коллекцию создаваемых
					complexExerciseMap.get(CE_CREATE_UPDATE).add(checkedExercise.get(true));
					// удаление элемента к-у с коллекции удаляемых
					complexExerciseMap.get(CE_DELETE).remove(checkedExercise.get(true));
				} else {
					// создание нового элемента к-у в коллекции создаваемых
					ComplexExercise complexExercise = new ComplexExercise();
					complexExercise.setComplex(complex);
					complexExercise.setExercise(exercise);
					complexExerciseMap.get(CE_CREATE_UPDATE).add(complexExercise);
				}
			}
		}
	}
	/**
	 * обработчик для чекбоксов
	 */
	OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Exercise exercise = getExercise((Integer) buttonView.getTag());
			Boolean issetExerciseES = exerciseMap.get(EXERCISES_SELECTED).contains(exercise);
			if (isChecked) {
				if (!issetExerciseES) {
					exerciseMap.get(EXERCISES_SELECTED).add(exercise);
				}
			} else if (issetExerciseES)
				exerciseMap.get(EXERCISES_SELECTED).remove(exercise);
		}
	};
}