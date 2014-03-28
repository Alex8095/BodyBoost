package com.alextsurkin.bodyboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Exercise;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ExerciseDetailsActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private Exercise exercise = new Exercise();

	Button btnDelete, btnEdit;
	TextView tvExerciseName, tvExerciseType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exercise_details);

		// элементы UI
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		tvExerciseName = (TextView) findViewById(R.id.tvExerciseName);
		tvExerciseType = (TextView) findViewById(R.id.tvExerciseType);
		//
		btnDelete.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createExerciseItem();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ExerciseListActivity.class);
		intent.putExtra("exercise_type_id", exercise.getTypeExercise().getId());
		startActivity(intent);
	}

	private void createExerciseItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("exercise_id")) {
			exercise = DatabaseManager.getInstance().getExerciseForId(bundle.getInt("exercise_id"));
			tvExerciseName.setText(exercise.getName());;
			tvExerciseType.setText(exercise.getTypeExercise().getName());
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnDelete :
				DatabaseManager.getInstance().deleteExercise(exercise.getId());
				intent = new Intent(this, ExerciseListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnEdit :
				intent = new Intent(this, ExerciseControlActivity.class);
				intent.putExtra("exercise_id", exercise.getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}
}
