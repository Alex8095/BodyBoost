package com.alextsurkin.bodyboost;

import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.alextsurkin.bodyboost.adapter.ExerciseActionsControlAdapter;
import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TraningStartActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private Complex complex = new Complex();
	private Traning traning = new Traning();
	private List<Exercise> exercisees;
	private int traning_repeat_id = 0;
	LinearLayout llWeightBefore, llComplexName, llComplexType, llCountAction;
	Button btnStart;
	TextView tvComplexName, tvComplexType;
	EditText etWeightBefore, etWeightAfter, etCountAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traning_start);

		// элементы UI
		llWeightBefore = (LinearLayout) findViewById(R.id.llWeightBefore);
		llComplexName = (LinearLayout) findViewById(R.id.llComplexName);
		llComplexType = (LinearLayout) findViewById(R.id.llComplexType);
		llCountAction = (LinearLayout) findViewById(R.id.llCountAction);
		btnStart = (Button) findViewById(R.id.btnStart);
		tvComplexName = (TextView) findViewById(R.id.tvComplexName);
		tvComplexType = (TextView) findViewById(R.id.tvComplexType);
		etWeightBefore = (EditText) findViewById(R.id.etWeightBefore);
		etCountAction = (EditText) findViewById(R.id.etCountAction);
		//
		btnStart.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createTraningItem();
	}

	private void createTraningItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("traning_id")) {
			traning_repeat_id = bundle.getInt("traning_id");
			Traning traningRepeat = DatabaseManager.getInstance().getTraningForId(bundle.getInt("traning_id"));
			etCountAction.setText(Integer.toString(traningRepeat.getCountAction()));
			complex = traningRepeat.getComplex();
		}
		if (null != bundle && bundle.containsKey("complex_id")) {
			complex = DatabaseManager.getInstance().getComplexForId(bundle.getInt("complex_id"));
		}
		tvComplexName.setText(complex.getName());
		tvComplexType.setText(complex.getTypeComplex().getName());
		traning.setComplex(complex);
		traning.setDate(new Date());
		traning.setTimeStart(new Date());
		Log.d(LOG_TAG, Integer.toString(traning_repeat_id));
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnStart :
				traning.setWeightBefore(Double.parseDouble(etWeightBefore.getText().toString()));
				traning.setCountAction(Integer.parseInt(etCountAction.getText().toString()));
				DatabaseManager.getInstance().saveTraning(traning);
				intent = new Intent(this, TraningControlActivity.class);
				intent.putExtra("traning_id", traning.getId());
				if (traning_repeat_id != 0)
					intent.putExtra("traning_repeat_id", traning_repeat_id);
				startActivity(intent);
				break;
			default :
				break;
		}
	}
}