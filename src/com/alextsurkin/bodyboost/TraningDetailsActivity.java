package com.alextsurkin.bodyboost;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alextsurkin.bodyboost.adapter.ExerciseActionsControlAdapter;
import com.alextsurkin.bodyboost.adapter.ExerciseActionsTraningAdapter;
import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TraningDetailsActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	TextView tvComplexName, tvComplexType, tvWeightBefore, tvWeightAfter, tvDate, tvDifferenceMinutesTime;
	Button btnDelete, btnRepeatTraning;
	ListView lvExerciseAction;

	private Traning traning = new Traning();
	ExerciseActionsTraningAdapter exerciseActionsTraningAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traning_details);

		tvComplexName = (TextView) findViewById(R.id.tvComplexName);
		tvComplexType = (TextView) findViewById(R.id.tvComplexType);
		tvWeightBefore = (TextView) findViewById(R.id.tvWeightBefore);
		tvWeightAfter = (TextView) findViewById(R.id.tvWeightAfter);
		tvDate = (TextView) findViewById(R.id.tvDate);
		tvDifferenceMinutesTime = (TextView) findViewById(R.id.tvDifferenceMinutesTime);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnRepeatTraning = (Button) findViewById(R.id.btnRepeatTraning);
		lvExerciseAction = (ListView) findViewById(R.id.lvExerciseAction);

		btnDelete.setOnClickListener(this);
		btnRepeatTraning.setOnClickListener(this);
	}

	private void createTraningItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("traning_id")) {
			traning = DatabaseManager.getInstance().getTraningForFull(bundle.getInt("traning_id"));
		}
		if (traning != null) {
			tvComplexName.setText(traning.getComplex().getName());
			tvComplexType.setText(traning.getComplex().getTypeComplex().getName());
			tvWeightBefore.setText(Double.toString(traning.getWeightBefore()));
			tvWeightAfter.setText(Double.toString(traning.getWeightAfter()));
			tvDate.setText(traning.getDate().toLocaleString());
			tvDifferenceMinutesTime.setText(Long.toString(traning.getDifferenceMinutesTime()));
		}
	}

	private void createTraningListView() {
		exerciseActionsTraningAdapter = new ExerciseActionsTraningAdapter(this, traning, (List<Exercise>) traning.getListExercise());
		lvExerciseAction.setAdapter(exerciseActionsTraningAdapter);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		// создание
		createTraningItem();
		createTraningListView();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, TraningListActivity.class);
		intent.putExtra("exercise_type_id", traning.getComplex().getId());
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			case R.id.btnDelete :
				intent = new Intent(this, TraningListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnRepeatTraning :
				intent = new Intent(this, TraningStartActivity.class);
				intent.putExtra("traning_id", traning.getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}
}
