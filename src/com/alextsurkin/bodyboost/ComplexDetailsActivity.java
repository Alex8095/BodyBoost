package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.ComplexExercise;
import com.alextsurkin.bodyboost.model.Exercise;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ComplexDetailsActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();

	private static final int CM_DELETE_ID = 1;
	private Complex complex = new Complex();
	private List<Exercise> exercisees;

	Button btnStartTraning, btnDelete, btnEdit, btnCreate, btnCheckedExercise;
	TextView tvComplexName, tvComplexType;
	ListView lvExercice;

	ArrayAdapter<String> sAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complex_details);
		// элементы UI
		btnStartTraning = (Button) findViewById(R.id.btnStartTraning);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnCheckedExercise = (Button) findViewById(R.id.btnCheckedExercise);
		tvComplexName = (TextView) findViewById(R.id.tvComplexName);
		tvComplexType = (TextView) findViewById(R.id.tvComplexType);
		lvExercice = (ListView) findViewById(R.id.lvExercise);

		btnStartTraning.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		btnCreate.setOnClickListener(this);
		btnCheckedExercise.setOnClickListener(this);

		// контектсное меню для списка
		// registerForContextMenu(lvExercice);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createComplexItem();
		createExerciceListView();
	}

	private void createComplexItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("complex_id")) {
			complex = DatabaseManager.getInstance().getComplexForId(bundle.getInt("complex_id"));
			tvComplexName.setText(complex.getName());;
			tvComplexType.setText(complex.getTypeComplex().getName());
			exercisees = DatabaseManager.getInstance().lookupExerciseesForComplex(complex);
		}
	}

	private void createExerciceListView() {
		if (null != exercisees) {
			List<String> titles = new ArrayList<String>();
			for (Exercise exercise : exercisees) {
				titles.add(exercise.getName() + "(" + exercise.getTypeExercise().getName() + ")");
			}
			sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
			lvExercice.setAdapter(sAdapter);
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ComplexListActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnDelete :
				DatabaseManager.getInstance().deleteComplex(complex.getId());
				intent = new Intent(this, ComplexListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnEdit :
				intent = new Intent(this, ComplexControlActivity.class);
				intent.putExtra("complex_id", complex.getId());
				startActivity(intent);
				break;
			case R.id.btnCreate :
				intent = new Intent(this, ExerciseControlActivity.class);
				intent.putExtra("complex_id", complex.getId());
				startActivity(intent);
				break;
			case R.id.btnStartTraning :
				intent = new Intent(this, TraningStartActivity.class);
				intent.putExtra("complex_id", complex.getId());
				startActivity(intent);
				break;
			case R.id.btnCheckedExercise :
				intent = new Intent(this, ComplexExerciseListActivity.class);
				intent.putExtra("complex_id", complex.getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}
}
