package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ComplexControlActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final String TYPE_CODE = "complex_type";

	private static int complexTypePosition = 0;

	private Complex complex = new Complex();

	Button btnSave;
	EditText etComplexName;
	Spinner spinnerComplexType;

	List<DictionaryValue> typesComplex;
	ArrayAdapter<String> complexTypeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complex_control);
		// элементы UI
		btnSave = (Button) findViewById(R.id.btnSave);
		etComplexName = (EditText) findViewById(R.id.etComplexName);
		spinnerComplexType = (Spinner) findViewById(R.id.spinnerComplexType);
		//
		btnSave.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createComplexItem();
		createSpinner();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnSave :
				complex.setName(etComplexName.getText().toString());
				complex.setTypeComplex(typesComplex.get(complexTypePosition));
				DatabaseManager.getInstance().saveComplex(complex);
				intent = new Intent(this, ComplexListActivity.class);
				intent.putExtra("complex_type_id", complex.getTypeComplex().getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, ComplexListActivity.class);
		startActivity(intent);
	}

	private void createComplexItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("complex_id")) {
			int complex_id = bundle.getInt("complex_id");
			complex = DatabaseManager.getInstance().getComplexForId(complex_id);
			etComplexName.setText(complex.getName());
		}
	}

	private void createSpinner() {
		spinnerComplexType.setPrompt("Category complex");
		Dictionary dictionary = DatabaseManager.getInstance().getDictionaryItemForEq("code", TYPE_CODE);
		if (dictionary != null) {
			if (dictionary.getDictionaryValueList().size() > 0) {
				List<String> titles = new ArrayList<String>();
				typesComplex = dictionary.getDictionaryValueList();
				int i = 0;
				for (DictionaryValue dictionaryValue : typesComplex) {
					titles.add(dictionaryValue.getName());
					if (complex.getId() > 0) {
						if (dictionaryValue.getId() == complex.getTypeComplex().getId()) {
							complexTypePosition = i;
							Log.d(LOG_TAG, "exerciseTypePosition" + complexTypePosition);
						}
						i++;
					}
				}
				complexTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
				spinnerComplexType.setAdapter(complexTypeAdapter);
				spinnerComplexType.setSelection(complexTypePosition);
			}
		}
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("complex_type_id")) {
			spinnerComplexType.setSelection(bundle.getInt("complex_type_id"));
		}
		// устанавливаем обработчик нажатия
		spinnerComplexType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				complexTypePosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}
}
