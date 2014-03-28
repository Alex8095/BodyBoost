package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.dictionary.model.Dictionary;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class DictionaryListActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final int CM_DELETE_ID = 1;

	Button btnDictionaryCreate;
	ListView lvDictionaryList;

	List<Dictionary> dictionarys;
	ArrayAdapter<String> sAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dictionary_list);

		// элементы UI
		btnDictionaryCreate = (Button) findViewById(R.id.btnDictionaryCreate);
		lvDictionaryList = (ListView) findViewById(R.id.lvDictionaryList);
		btnDictionaryCreate.setOnClickListener(this);

		// контектсное меню дл€ списка справочников
		registerForContextMenu(lvDictionaryList);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		// создание списка справочников
		createListView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnDictionaryCreate :
				Intent intent = new Intent(this, DictionaryControlActivity.class);
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
	public boolean onContextItemSelected(MenuItem item) {
		Log.d(LOG_TAG, Integer.toString(CM_DELETE_ID));
		if (item.getItemId() == CM_DELETE_ID) {
			// получаем инфу о пункте списка
			AdapterContextMenuInfo acmi = (AdapterContextMenuInfo) item.getMenuInfo();
			Log.d(LOG_TAG, dictionarys.get(acmi.position).toString());
			// удал€ем из коллекции, использу€ позицию пункта в списке
			DatabaseManager.getInstance().deleteDictionary(dictionarys.get(acmi.position));
			// уведомл€ем, что данные изменились
			sAdapter.notifyDataSetChanged();
			createListView();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	private void createListView() {
		dictionarys = DatabaseManager.getInstance().getAllDictionarys();
		if (null != dictionarys) {
			List<String> titles = new ArrayList<String>();
			for (Dictionary dictionary : dictionarys) {
				titles.add(dictionary.getName());
			}
			sAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
			lvDictionaryList.setAdapter(sAdapter);
			final Activity activity = this;
			lvDictionaryList.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Dictionary item = dictionarys.get(position);
					Intent intent = new Intent(activity, DictionaryDetailsActivity.class);
					intent.putExtra("dictionary_id", item.getId());
					startActivity(intent);
				}
			});
		}
	}
}
