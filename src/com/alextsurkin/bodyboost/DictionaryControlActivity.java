package com.alextsurkin.bodyboost;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.dictionary.model.Dictionary;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class DictionaryControlActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();

	private Dictionary item = new Dictionary();

	Button btnSave;
	EditText etDictionaryName, etDictionaryCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictionary_control);

		// элементы UI
		btnSave = (Button) findViewById(R.id.btnSave);
		etDictionaryName = (EditText) findViewById(R.id.etDictionaryName);
		etDictionaryCode = (EditText) findViewById(R.id.etDictionaryCode);
		btnSave.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createDictionaryItem();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnSave :
				item.setName(etDictionaryName.getText().toString());
				item.setCode(etDictionaryCode.getText().toString());
				DatabaseManager.getInstance().saveDictionary(item);
				intent = new Intent(this, DictionaryListActivity.class);
				// intent.putExtra("dictionary_id", item.getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, DictionaryListActivity.class);
		startActivity(intent);
	}

	private void createDictionaryItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey("dictionary_id")) {
			int dictionary_id = bundle.getInt("dictionary_id");
			item = DatabaseManager.getInstance().getDictionaryForId(dictionary_id);
			etDictionaryName.setText(item.getName());
			etDictionaryCode.setText(item.getCode());
		}
	}
}
