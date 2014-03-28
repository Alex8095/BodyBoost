package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ExerciseControlActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final String TYPE_CODE = "exercise_type";

	private static int exerciseTypePosition = 0;

	private Exercise exercise = new Exercise();

	Button btnSave;
	EditText etExerciseName;
	Spinner spinnerExerciseType;

	List<DictionaryValue> typesExercise;
	ArrayAdapter<String> exerciseTypeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_control);
		// элементы UI
		btnSave = (Button) findViewById(R.id.btnSave);
		etExerciseName = (EditText) findViewById(R.id.etExerciseName);
		spinnerExerciseType = (Spinner) findViewById(R.id.spinnerExerciseType);
		//
		btnSave.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createExerciseItem();
		createSpinner();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnSave :
				exercise.setName(etExerciseName.getText().toString());
				exercise.setTypeExercise(typesExercise.get(exerciseTypePosition));
				DatabaseManager.getInstance().saveExercise(exercise);
				intent = new Intent(this, ExerciseListActivity.class);
				intent.putExtra("exercise_type_id", exercise.getTypeExercise().getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ExerciseListActivity.class);
		startActivity(intent);
	}

	private void createExerciseItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("exercise_id")) {
			exercise = DatabaseManager.getInstance().getExerciseForId(bundle.getInt("exercise_id"));
			etExerciseName.setText(exercise.getName());
		}
	}

	private void createSpinner() {
		spinnerExerciseType.setPrompt("Type exercise");
		Dictionary dictionary = DatabaseManager.getInstance().getDictionaryItemForEq("code", TYPE_CODE);
		if (dictionary != null) {
			if (dictionary.getDictionaryValueList().size() > 0) {
				List<String> titles = new ArrayList<String>();
				typesExercise = dictionary.getDictionaryValueList();
				int i = 0;
				for (DictionaryValue dictionaryValue : typesExercise) {
					titles.add(dictionaryValue.getName());
					if (exercise.getId() > 0) {
						if (dictionaryValue.getId() == exercise.getTypeExercise().getId()) {
							exerciseTypePosition = i;
							Log.d(LOG_TAG, "exerciseTypePosition" + exerciseTypePosition);
						}
						i++;
					}
				}
				exerciseTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
				spinnerExerciseType.setAdapter(exerciseTypeAdapter);
				spinnerExerciseType.setSelection(exerciseTypePosition);
			}
		}
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("exercise_type_id")) {
			spinnerExerciseType.setSelection(bundle.getInt("exercise_type_id"));
		}
		// устанавливаем обработчик нажатия
		spinnerExerciseType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				exerciseTypePosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
}
