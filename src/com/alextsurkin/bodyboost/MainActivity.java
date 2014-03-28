package com.alextsurkin.bodyboost;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.Exercise;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();

	Button btnTraningStart, btnTraningList, btnComplexList, btnExerciseList, btnDictionaryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// activity elements
		btnTraningStart = (Button) findViewById(R.id.btnTraningStart);
		btnTraningList = (Button) findViewById(R.id.btnTraningList);
		btnComplexList = (Button) findViewById(R.id.btnComplexList);
		btnExerciseList = (Button) findViewById(R.id.btnExerciseList);
		btnDictionaryList = (Button) findViewById(R.id.btnDictionaryList);
		btnTraningStart.setOnClickListener(this);
		btnTraningList.setOnClickListener(this);
		btnComplexList.setOnClickListener(this);
		btnExerciseList.setOnClickListener(this);
		btnDictionaryList.setOnClickListener(this);
		// init ormlite
		DatabaseManager.init(this);

		// DatabaseManager.getInstance().deleteTraning(14);
		// DatabaseManager.getInstance().deleteTraning(15);
		// DatabaseManager.getInstance().deleteTraning(16);
		
//		Intent intent = new Intent(this, TraningControlActivity.class);
//		intent.putExtra("traning_id", 2);
//		intent.putExtra("traning_repeat_id", 1);
//		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnTraningStart :
				intent = new Intent(this, TraningStartActivity.class);
				intent.putExtra("complex_id", 1);
				startActivity(intent);
				break;
			case R.id.btnTraningList :
				intent = new Intent(this, TraningListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnComplexList :
				intent = new Intent(this, ComplexListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnExerciseList :
				intent = new Intent(this, ExerciseListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnDictionaryList :
				intent = new Intent(this, DictionaryListActivity.class);
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	public void testManyToMany() {
		Complex complex = DatabaseManager.getInstance().getComplexForId(1);
		// user1 should have 2 posts
		List<Exercise> exercieses = null;
		exercieses = DatabaseManager.getInstance().lookupExerciseesForComplex(complex);
		String t = "";
		if (null != exercieses) {
			for (Exercise exercise : exercieses) {
				t = exercise.getName();
			}
		}
	}
}
