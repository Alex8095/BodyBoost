package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alextsurkin.bodyboost.adapter.ComplexExerciseAdapter;
import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.ComplexExercise;
import com.alextsurkin.bodyboost.model.Exercise;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ComplexExerciseListActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private Complex complex = new Complex();
	List<Exercise> exercisees = new ArrayList<Exercise>();
	List<ComplexExercise> complexExercisees;
	ComplexExerciseAdapter complexExerciseAdapter;

	TextView tvComplexName, tvComplexType;
	Button btnSave;
	ListView lvComplexExercise;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complex_exercise_list);

		// элементы UI
		tvComplexName = (TextView) findViewById(R.id.tvComplexName);
		tvComplexType = (TextView) findViewById(R.id.tvComplexType);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnSave.setOnClickListener(this);
		// настраиваем список
		lvComplexExercise = (ListView) findViewById(R.id.lvComplexExercise);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createComplexItem();
		createComplexExerciceListView();
	}

	public void createComplexItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("complex_id")) {
			complex = DatabaseManager.getInstance().getComplexForId(bundle.getInt("complex_id"));
			tvComplexName.setText(complex.getName());
			tvComplexType.setText(complex.getTypeComplex().getName());
		}
	}

	public void createComplexExerciceListView() {
		exercisees = DatabaseManager.getInstance().getAllExercises();
		complexExercisees = DatabaseManager.getInstance().lookupComplexExerciseesForComplex(complex);
		complexExerciseAdapter = new ComplexExerciseAdapter(this, exercisees, complex, complexExercisees);
		lvComplexExercise.setAdapter(complexExerciseAdapter);
		// Toast.makeText(this, Integer.toString(complexExercisees.size()),
		// Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnSave :
				complexExerciseAdapter.createResult();
				DatabaseManager.getInstance().deleteComplexExercise(complexExerciseAdapter.getComplexExercise("complex_exercises_delete"));
				List<ComplexExercise> ce = complexExerciseAdapter.getComplexExercise("complex_exercises_create_update");
				if (ce.size() > 0) {
					for (ComplexExercise complexExercise : ce) {
						DatabaseManager.getInstance().createOrUpdateComplexExercise(complexExercise);
					}
				}
				// StringBuilder sb = new StringBuilder();
				// sb.append("saved");
				// sb.append("complex_exercises_create_update=");
				// sb.append(Integer.toString(complexExerciseAdapter.getComplexExercise("complex_exercises_create_update").size()));
				// sb.append("complex_exercises_delete=");
				// sb.append(Integer.toString(complexExerciseAdapter.getComplexExercise("complex_exercises_delete").size()));
				// Toast.makeText(this, sb, Toast.LENGTH_LONG).show();
				intent = new Intent(this, ComplexDetailsActivity.class);
				intent.putExtra("complex_id", complex.getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}
}