package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class DictionaryDetailsActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();

	private static final int CM_DELETE_ID = 1;
	private Dictionary item = new Dictionary();

	Button btnDelete, btnEdit, btnCreate;
	TextView dictionaryName, dictionaryCode;
	ListView lvDictionaryValue;

	// List<Dictionary> dictionarys;
	ArrayAdapter<String> sAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictionary_details);

		// элементы UI
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnEdit = (Button) findViewById(R.id.btnEdit);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		dictionaryName = (TextView) findViewById(R.id.dictionaryName);
		dictionaryCode = (TextView) findViewById(R.id.dictionaryCode);
		lvDictionaryValue = (ListView) findViewById(R.id.lvDictionaryValue);
		//
		btnDelete.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		btnCreate.setOnClickListener(this);

		// контектсное меню дл€ списка
		registerForContextMenu(lvDictionaryValue);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		createDictionaryItem();
		createListView();
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			case R.id.btnDelete :
				DatabaseManager.getInstance().deleteDictionary(item.getId());
				intent = new Intent(this, DictionaryListActivity.class);
				startActivity(intent);
				break;
			case R.id.btnEdit :
				intent = new Intent(this, DictionaryControlActivity.class);
				intent.putExtra("dictionary_id", item.getId());
				startActivity(intent);
				break;
			case R.id.btnCreate :
				intent = new Intent(this, DictionaryValueControlActivity.class);
				intent.putExtra("dictionary_id", item.getId());
				startActivity(intent);
				break;
			default :
				break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DELETE_ID, 0, "”далить запись");
	}

	@Override
	public boolean onContextItemSelected(MenuItem menuItem) {
		if (menuItem.getItemId() == CM_DELETE_ID) {
			// получаем инфу о пункте списка
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) menuItem.getMenuInfo();
			// удал€ем из коллекции, использу€ позицию пункта в списке
			DatabaseManager.getInstance().deleteDictionaryValue(item.getDictionaryValueList().get(acmi.position));
			// уведомл€ем, что данные изменились
			sAdapter.notifyDataSetChanged();
			createListView();
			return true;
		}
		return super.onContextItemSelected(menuItem);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, DictionaryListActivity.class);
		startActivity(intent);
	}

	private void createDictionaryItem() {
		Bundle bundle = getIntent().getExtras();
		int dictionary_id = bundle.getInt("dictionary_id");
		item = DatabaseManager.getInstance().getDictionaryForId(dictionary_id);
		dictionaryName.setText(item.getName());
		dictionaryCode.setText(item.getCode());
	}

	private void createListView() {
		if (null != item.getDictionaryValueList()) {
			List<String> titles = new ArrayList<String>();
			for (DictionaryValue dv : item.getDictionaryValueList()) {
				titles.add(dv.getName());
			}
			sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
			lvDictionaryValue.setAdapter(sAdapter);
			final Activity activity = this;
			lvDictionaryValue.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					DictionaryValue dictionaryValue = item.getDictionaryValueList().get(position);
					Intent intent = new Intent(activity, DictionaryValueControlActivity.class);
					intent.putExtra("dictionary_value_id", dictionaryValue.getId());
					startActivity(intent);
				}
			});
		}
	}
}
