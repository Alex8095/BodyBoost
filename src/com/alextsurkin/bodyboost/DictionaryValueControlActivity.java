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
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class DictionaryValueControlActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();

	private DictionaryValue item = new DictionaryValue();

	Button btnSave;
	EditText etDictionaryValueName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictionary_value_control);

		// элементы UI
		btnSave = (Button) findViewById(R.id.btnSave);
		etDictionaryValueName = (EditText) findViewById(R.id.etDictionaryValueName);
		//
		btnSave.setOnClickListener(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createDictionaryValueItem();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnSave :
				item.setName(etDictionaryValueName.getText().toString());
				DatabaseManager.getInstance().saveDictionaryValue(item);
				intent = new Intent(this, DictionaryDetailsActivity.class);
				intent.putExtra("dictionary_id", item.getDictionary().getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, DictionaryDetailsActivity.class);
		intent.putExtra("dictionary_id", item.getDictionary().getId());
		startActivity(intent);
	}

	private void createDictionaryValueItem() {
		Bundle bundle = getIntent().getExtras();
		if (null != bundle) {
			if (bundle.containsKey("dictionary_value_id")) {
				int dv_id = bundle.getInt("dictionary_value_id");
				item = DatabaseManager.getInstance().getDictionaryValueWithId(dv_id);
				etDictionaryValueName.setText(item.getName());
			}
			if (bundle.containsKey("dictionary_id")) {
				int dv_id = bundle.getInt("dictionary_id");
				item.setDictionary(DatabaseManager.getInstance().getDictionaryForId(dv_id));
			}
		}
	}
}
