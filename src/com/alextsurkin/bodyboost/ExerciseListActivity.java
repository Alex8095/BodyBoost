package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ExerciseListActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final int CM_DELETE_ID = 1;
	private static final String TYPE_CODE = "exercise_type";

	private static int exerciseTypePosition = 0;

	Button btnCreate;
	ListView lvExercise;
	Spinner spinnerExerciseType;

	List<DictionaryValue> typesExercise;
	List<Exercise> exercisees;

	ArrayAdapter<String> exerciseAdapter;
	ArrayAdapter<String> exerciseTypeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_list);
		// элементы UI
		btnCreate = (Button) findViewById(R.id.btnCreate);
		lvExercise = (ListView) findViewById(R.id.lvExercise);
		spinnerExerciseType = (Spinner) findViewById(R.id.spinnerExerciseType);
		btnCreate.setOnClickListener(this);
		// контектсное меню дл€ списка
		registerForContextMenu(lvExercise);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		// создание
		createSpinner();
		createListView();
	}

	private void createSpinner() {
		spinnerExerciseType.setPrompt("Category Exercise");
		Dictionary dictionary = DatabaseManager.getInstance().getDictionaryItemForEq("code", TYPE_CODE);
		if (dictionary != null) {
			if (dictionary.getDictionaryValueList().size() > 0) {
				List<String> titles = new ArrayList<String>();
				typesExercise = dictionary.getDictionaryValueList();
				for (DictionaryValue dictionaryValue : typesExercise) {
					titles.add(dictionaryValue.getName());
				}
				exerciseTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
				spinnerExerciseType.setAdapter(exerciseTypeAdapter);
			}
		}
		// устанавливаем обработчик нажати€
		spinnerExerciseType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				exerciseTypePosition = position;
				createListView();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void createListView() {
		// Log.d(LOG_TAG, Integer.toString(exerciseTypePosition));
		// Log.d(LOG_TAG,
		// Integer.toString(typesExercise.get(exerciseTypePosition).getId()));
		exercisees = DatabaseManager.getInstance().getExerciseForEq("type_exercise_id", typesExercise.get(exerciseTypePosition).getId());
		// Log.d(LOG_TAG, Integer.toString(exercisees.size()));
		if (null != exercisees) {
			List<String> titles = new ArrayList<String>();
			for (Exercise Exercise : exercisees) {
				titles.add(Exercise.getName());
			}
			exerciseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
			lvExercise.setAdapter(exerciseAdapter);
			final Activity activity = this;
			lvExercise.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Exercise item = exercisees.get(position);
					Intent intent = new Intent(activity, ExerciseDetailsActivity.class);
					intent.putExtra("exercise_id", item.getId());
					startActivity(intent);
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnCreate :
				Intent intent = new Intent(this, ExerciseControlActivity.class);
				intent.putExtra("exercise_type_id", exerciseTypePosition);
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DELETE_ID, 0, "”далить запись");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.d(LOG_TAG, Integer.toString(CM_DELETE_ID));
		if (item.getItemId() == CM_DELETE_ID) {
			// получаем инфу о пункте списка
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
			// удал€ем из коллекции, использу€ позицию пункта в списке
			DatabaseManager.getInstance().deleteExercise(exercisees.get(acmi.position));
			// уведомл€ем, что данные изменились
			exerciseAdapter.notifyDataSetChanged();
			createListView();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}