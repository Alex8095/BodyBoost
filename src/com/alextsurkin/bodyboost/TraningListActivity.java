package com.alextsurkin.bodyboost;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Spinner;

import com.alextsurkin.bodyboost.adapter.TraningAdapter;
import com.alextsurkin.bodyboost.db.DatabaseHelper;
import com.alextsurkin.bodyboost.db.DatabaseManager;
import com.alextsurkin.bodyboost.model.Action;
import com.alextsurkin.bodyboost.model.Complex;
import com.alextsurkin.bodyboost.model.Exercise;
import com.alextsurkin.bodyboost.model.Traning;
import com.bodyboost.R;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class TraningListActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	private final String LOG_TAG = getClass().getSimpleName();
	private static final int CM_DETAILS_ID = 1;
	private static final int CM_REPEAT_ID = 2;
	private static final String TYPE_CODE = "complex_type";
	private static int complexPosition = 0;
	private Map<Integer, List<Action>> actionMap = null;

	List<Traning> tranings;
	Complex complex;
	List<Complex> complexes;
	ArrayAdapter<String> complexAdapter;

	EditText etTraningDate;
	ExpandableListView elvTraning;
	Spinner spinnerComplex;
	TraningAdapter traningAdapter;

	private int traningGroupPositio;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.traning_list);
		// ıëåìåíòû UI
		spinnerComplex = (Spinner) findViewById(R.id.spinnerComplex);
		elvTraning = (ExpandableListView) findViewById(R.id.elvTraning);

		// íàæàòèå íà ãğóïïó
		elvTraning.setOnGroupClickListener(new OnGroupClickListener() {
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				traningGroupPositio = groupPosition;
				return false;
			}
		});

		// êîíòåêòñíîå ìåíş äëÿ ñïèñêà ñïğàâî÷íèêîâ
		registerForContextMenu(elvTraning);
	}

	private void createSpinner() {
		spinnerComplex.setPrompt("Complexes");
		complexes = DatabaseManager.getInstance().getAllComplexes();
		if (complexes != null) {
			if (complexes.size() > 0) {
				List<String> titles = new ArrayList<String>();
				for (Complex complex : complexes) {
					titles.add(complex.getName());
				}
				complexAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, titles);
				spinnerComplex.setAdapter(complexAdapter);
			}
		}
		// óñòàíàâëèâàåì îáğàáîò÷èê íàæàòèÿ
		spinnerComplex.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				complexPosition = position;
				createExpandableListView();
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
	}

	private void createExpandableListView() {
		List<Exercise> exercisees = new ArrayList<Exercise>();
		// âûáîğêà òğåíèğîâîê â çàâèñèìîñòè îò êîìïëåêñà
		tranings = DatabaseManager.getInstance().getTraningForEq("complex_id", complexes.get(complexPosition).getId());

		Log.d(LOG_TAG, Integer.toString(tranings.size()));
		// âûáîğêà óïğàæíåíèé â çàâèñèìîñòè îò êîìïëåêñà
		exercisees = DatabaseManager.getInstance().lookupExerciseesForComplex(complexes.get(complexPosition));
		// Log.d(LOG_TAG, "tranings.size()" + tranings.size());
		// âûáîğêà ïîäõîäîâ â çàâèñèìîñòè îò óïğàæåíèÿ è òğåíèğîâêè
		if (tranings != null) {
			for (Traning traning : tranings) {
				traning = DatabaseManager.getInstance().getTraningForFull(traning);
			}
		}
		traningAdapter = new TraningAdapter(this, tranings);
		elvTraning.setAdapter(traningAdapter);
		elvTraning.setItemsCanFocus(true);
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, CM_DETAILS_ID, 0, getString(R.string.details));
		menu.add(0, CM_REPEAT_ID, 1, getString(R.string.repeat_traning));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Intent intent;
		if (item.getItemId() == CM_DETAILS_ID) {
			intent = new Intent(this, TraningDetailsActivity.class);
			intent.putExtra("traning_id", tranings.get(traningGroupPositio).getId());
			startActivity(intent);
			return true;
		}
		if (item.getItemId() == CM_REPEAT_ID) {
			intent = new Intent(this, TraningStartActivity.class);
			intent.putExtra("traning_id", tranings.get(traningGroupPositio).getId());
			startActivity(intent);
			return true;
		}
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onStart() {
		super.onStart();
		DatabaseManager.init(this);
		// ñîçäàíèå
		createSpinner();
		createExpandableListView();
	}
}

// package com.as400samplecode;
//  
// import java.util.ArrayList;
// import java.util.LinkedHashMap;
//  
// import android.os.Bundle;
// import android.app.Activity;
// import android.view.Menu;
// import android.view.View;
// import android.view.View.OnClickListener;
// import android.widget.ArrayAdapter;
// import android.widget.Button;
// import android.widget.EditText;
// import android.widget.ExpandableListView;
// import android.widget.Spinner;
// import android.widget.Toast;
// import android.widget.ExpandableListView.OnChildClickListener;
// import android.widget.ExpandableListView.OnGroupClickListener;
//  
// public class MainActivity extends Activity implements OnClickListener{
//  
//  private LinkedHashMap<String, HeaderInfo> myDepartments = new
// LinkedHashMap<String, HeaderInfo>();
//  private ArrayList<HeaderInfo> deptList = new ArrayList<HeaderInfo>();
//  
//  private MyListAdapter listAdapter;
//  private ExpandableListView myList;
//  
//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//   super.onCreate(savedInstanceState);
//   setContentView(R.layout.activity_main);
//  
//   Spinner spinner = (Spinner) findViewById(R.id.department);
//   // Create an ArrayAdapter using the string array and a default spinner
// layout
//   ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//     R.array.dept_array, android.R.layout.simple_spinner_item);
//   // Specify the layout to use when the list of choices appears
//   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//   // Apply the adapter to the spinner
//   spinner.setAdapter(adapter);
//  
//   //Just add some data to start with
//   loadData();
//  
//   //get reference to the ExpandableListView
//   myList = (ExpandableListView) findViewById(R.id.myList);
//   //create the adapter by passing your ArrayList data
//   listAdapter = new MyListAdapter(MainActivity.this, deptList);
//   //attach the adapter to the list
//   myList.setAdapter(listAdapter);
//  
//   //expand all Groups
//   expandAll();
//  
//   //add new item to the List
//   Button add = (Button) findViewById(R.id.add);
//   add.setOnClickListener(this);
//    
//   //listener for child row click
//   myList.setOnChildClickListener(myListItemClicked);
//   //listener for group heading click
//         myList.setOnGroupClickListener(myListGroupClicked);
//          
//          
//  }
//  
//  public void onClick(View v) {
//  
//   switch (v.getId()) {
//  
//   //add entry to the List
//   case R.id.add:
//  
//    Spinner spinner = (Spinner) findViewById(R.id.department);
//    String department = spinner.getSelectedItem().toString();
//    EditText editText = (EditText) findViewById(R.id.product);
//    String product = editText.getText().toString();
//    editText.setText("");
//     
//    //add a new item to the list
//    int groupPosition = addProduct(department,product);
//    //notify the list so that changes can take effect
//    listAdapter.notifyDataSetChanged();
//        
//    //collapse all groups
//    collapseAll();
//    //expand the group where item was just added
//    myList.expandGroup(groupPosition);
//    //set the current group to be selected so that it becomes visible
//    myList.setSelectedGroup(groupPosition);
//     
//    break;
//  
//    // More buttons go here (if any) ...
//  
//   }
//  }
//  
//  
//  //method to expand all groups
//  private void expandAll() {
//   int count = listAdapter.getGroupCount();
//   for (int i = 0; i < count; i++){
//    myList.expandGroup(i);
//   }
//  }
//   
//  //method to collapse all groups
//  private void collapseAll() {
//   int count = listAdapter.getGroupCount();
//   for (int i = 0; i < count; i++){
//    myList.collapseGroup(i);
//   }
//  }
//  
//  //load some initial data into out list
//  private void loadData(){
//  
//   addProduct("Apparel","Activewear");
//   addProduct("Apparel","Jackets");
//   addProduct("Apparel","Shorts");
//  
//   addProduct("Beauty","Fragrances");
//   addProduct("Beauty","Makeup");
//  
//  }
//   
//  //our child listener
//  private OnChildClickListener myListItemClicked =  new OnChildClickListener()
// {
//  
//   public boolean onChildClick(ExpandableListView parent, View v,
//     int groupPosition, int childPosition, long id) {
//     
//    //get the group header
//    HeaderInfo headerInfo = deptList.get(groupPosition);
//    //get the child info
//    DetailInfo detailInfo =  headerInfo.getProductList().get(childPosition);
//    //display it or do something with it
//    Toast.makeText(getBaseContext(), "Clicked on Detail " +
// headerInfo.getName()
//      + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();
//    return false;
//   }
//    
//  };
//   
//  //our group listener
//  private OnGroupClickListener myListGroupClicked =  new
// OnGroupClickListener() {
//  
//   public boolean onGroupClick(ExpandableListView parent, View v,
//     int groupPosition, long id) {
//     
//    //get the group header
//    HeaderInfo headerInfo = deptList.get(groupPosition);
//    //display it or do something with it
//    Toast.makeText(getBaseContext(), "Child on Header " +
// headerInfo.getName(),
//      Toast.LENGTH_LONG).show();
//      
//    return false;
//   }
//    
//  };
//  
//  //here we maintain our products in various departments
//  private int addProduct(String department, String product){
//  
//   int groupPosition = 0;
//    
//   //check the hash map if the group already exists
//   HeaderInfo headerInfo = myDepartments.get(department);
//   //add the group if doesn't exists
//   if(headerInfo == null){
//    headerInfo = new HeaderInfo();
//    headerInfo.setName(department);
//    myDepartments.put(department, headerInfo);
//    deptList.add(headerInfo);
//   }
//  
//   //get the children for the group
//   ArrayList<DetailInfo> productList = headerInfo.getProductList();
//   //size of the children list
//   int listSize = productList.size();
//   //add to the counter
//   listSize++;
//  
//   //create a new child and add that to the group
//   DetailInfo detailInfo = new DetailInfo();
//   detailInfo.setSequence(String.valueOf(listSize));
//   detailInfo.setName(product);
//   productList.add(detailInfo);
//   headerInfo.setProductList(productList);
//  
//   //find the group position inside the list
//   groupPosition = deptList.indexOf(headerInfo);
//   return groupPosition;
//  }
//  
//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//   getMenuInflater().inflate(R.menu.activity_main, menu);
//   return true;
//  }
// }