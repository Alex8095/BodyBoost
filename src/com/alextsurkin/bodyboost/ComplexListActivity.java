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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.dictionary.model.Dictionary;
import com.alextsurkin.dictionary.model.DictionaryValue;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ComplexListActivity extends OrmLiteBaseActivity<DatabaseHelper> implements OnClickListener {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final int CM_DELETE_ID = 1;
	private static final String TYPE_CODE = "complex_type";

	private static int complexTypePosition = 0;

	Button btnCreate;
	ListView lvComplex;
	Spinner spinnerComplexType;

	List<DictionaryValue> typesComplex;
	List<Complex> complexes;

	ArrayAdapter<String> complexAdapter;
	ArrayAdapter<String> complexTypeAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.complex_list);
		// элементы UI
		btnCreate = (Button) findViewById(R.id.btnCreate);
		lvComplex = (ListView) findViewById(R.id.lvComplex);
		spinnerComplexType = (Spinner) findViewById(R.id.spinnerComplexType);
		btnCreate.setOnClickListener(this);
		// контектсное меню дл€ списка
		registerForContextMenu(lvComplex);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		// создание
		createSpinner();
		createListView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btnCreate :
				Intent intent = new Intent(this, ComplexControlActivity.class);
				intent.putExtra("complex_type_id", complexTypePosition);
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
			// удал€ем из коллекции, использу€ позицию пункта в списке
			DatabaseManager.getInstance().deleteComplex(complexes.get(acmi.position));
			// уведомл€ем, что данные изменились
			complexAdapter.notifyDataSetChanged();
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

	private void createSpinner() {
		spinnerComplexType.setPrompt("Category complex");
		Dictionary dictionary = DatabaseManager.getInstance().getDictionaryItemForEq("code", TYPE_CODE);
		if (dictionary != null) {
			if (dictionary.getDictionaryValueList().size() > 0) {
				List<String> titles = new ArrayList<String>();
				typesComplex = dictionary.getDictionaryValueList();
				for (DictionaryValue dictionaryValue : typesComplex) {
					titles.add(dictionaryValue.getName());
				}
				complexTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
				spinnerComplexType.setAdapter(complexTypeAdapter);
			}
		}
		// устанавливаем обработчик нажати€
		spinnerComplexType.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				complexTypePosition = position;
				createListView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void createListView() {
		complexes = DatabaseManager.getInstance().getComplexForEq("type_complex_id", typesComplex.get(complexTypePosition).getId());
		// complexes = DatabaseManager.getInstance().getAllComplexes();
		if (null != complexes) {
			List<String> titles = new ArrayList<String>();
			for (Complex complex : complexes) {
				titles.add(complex.getName());
			}
			complexAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
			lvComplex.setAdapter(complexAdapter);
			final Activity activity = this;
			lvComplex.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Complex item = complexes.get(position);
					Intent intent = new Intent(activity, ComplexDetailsActivity.class);
					intent.putExtra("complex_id", item.getId());
					startActivity(intent);
				}
			});
		}
	}
}