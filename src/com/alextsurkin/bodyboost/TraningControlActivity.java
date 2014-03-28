package com.alextsurkin.bodyboost;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.alextsurkin.bodyboost.adapter.ExerciseActionsControlAdapter;
import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;

public class TraningControlActivity extends OrmLiteBaseActivity<OrmLiteSqliteOpenHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private Complex complex = new Complex();
	private Traning traning = new Traning();
	private List<Exercise> exercisees;

	LinearLayout llWeightAfter;
	Button btnFinish;
	ListView lvExerciseAction;
	EditText etWeightAfter;

	ExerciseActionsControlAdapter exerciseActionsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traning_control);
		//
		etWeightAfter = (EditText) findViewById(R.id.etWeightAfter);
		llWeightAfter = (LinearLayout) findViewById(R.id.llWeightAfter);
		lvExerciseAction = (ListView) findViewById(R.id.lvExerciseAction);
		btnFinish = (Button) findViewById(R.id.btnFinish);
		//
		btnFinish.setOnClickListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createTraningItem();
		createExerciseActionsListView();
	}

	private void createTraningItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("traning_id")) {
			traning = DatabaseManager.getInstance().getTraningForId(bundle.getInt("traning_id"));
			complex = DatabaseManager.getInstance().getComplexForId(traning.getComplex().getId());
			exercisees = DatabaseManager.getInstance().lookupExerciseesForComplex(complex);
		}
		if (null != bundle && bundle.containsKey("traning_repeat_id")) {
			Traning traningRepeat = DatabaseManager.getInstance().getTraningForFull(bundle.getInt("traning_repeat_id"));
			exercisees.clear();
			exercisees.addAll(traningRepeat.getListExercise());
		}
	}

	private void createExerciseActionsListView() {
		exerciseActionsAdapter = new ExerciseActionsControlAdapter(this, traning, exercisees);
		lvExerciseAction.setItemsCanFocus(true);
		lvExerciseAction.setAdapter(exerciseActionsAdapter);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnFinish :
				traning.setTimeFinish(new Date());
				traning.setWeightAfter(Double.parseDouble(etWeightAfter.getText().toString()));
				DatabaseManager.getInstance().saveTraning(traning);
				List<Action> actions = exerciseActionsAdapter.getResult();
				for (Action action : actions) {
					action.setTraning(traning);
					DatabaseManager.getInstance().saveAction(action);
				}
				intent = new Intent(this, TraningListActivity.class);
				startActivity(intent);
				break;
			default :
				break;
		}
	}
}

// public class TraningControlActivity extends
// OrmLiteBaseListActivity<OrmLiteSqliteOpenHelper> implements OnClickListener {
// private final String LOG_TAG = getClass().getSimpleName();
// private Complex complex = new Complex();
// private Traning traning = new Traning();
// private List<Exercise> exercisees;
//
// LinearLayout llWeightAfter;
// Button btnFinish;
// ListView lvExerciseAction;
// EditText etWeightAfter;
//
// ExerciseActionsControlAdapter exerciseActionsAdapter;
//
// @Override
// protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
// setContentView(R.layout.traning_control);
// //
// etWeightAfter = (EditText) findViewById(R.id.etWeightAfter);
// llWeightAfter = (LinearLayout) findViewById(R.id.llWeightAfter);
// lvExerciseAction = (ListView) findViewById(R.id.lvExerciseAction);
// btnFinish = (Button) findViewById(R.id.btnFinish);
// //
// btnFinish.setOnClickListener(this);
// }
//
// @Override
// protected void onStart() {
// super.onStart();
// DatabaseManager.init(this);
// createTraningItem();
// createExerciseActionsListView();
// }
//
// private void createTraningItem() {
// Bundle bundle = getIntent().getExtras();
// if (null != bundle && bundle.containsKey("traning_id")) {
// traning =
// DatabaseManager.getInstance().getTraningForId(bundle.getInt("traning_id"));
// complex = DatabaseManager.getInstance().getComplexForId(1);
// exercisees =
// DatabaseManager.getInstance().lookupExerciseesForComplex(complex);
// }
// }
//
// private void createExerciseActionsListView() {
// exerciseActionsAdapter = new ExerciseActionsControlAdapter(this, traning,
// exercisees);
// lvExerciseAction.setAdapter(exerciseActionsAdapter);
// }
//
// @Override
// public void onClick(View v) {
// Intent intent;
// switch (v.getId()) {
// case R.id.btnFinish :
// traning.setWeightAfter(Double.parseDouble(etWeightAfter.getText().toString()));
// DatabaseManager.getInstance().saveTraning(traning);
// List<Action> actions = exerciseActionsAdapter.getResult();
// for (Action action : actions) {
// DatabaseManager.getInstance().saveAction(action);
// }
// intent = new Intent(this, TraningListActivity.class);
// startActivity(intent);
// break;
// default :
// break;
// }
// }
// }
