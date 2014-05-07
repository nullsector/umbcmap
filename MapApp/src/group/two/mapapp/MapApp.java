package group.two.mapapp;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;


public class MapApp extends Activity {

	/*
	 * In order to add the menu functionality, you'll need the following:
- The code for MapApp.java
- activity_menu.xml (res/layout)
- activity_search.xml (res/layout)
- activity_bookmark.xml (res/layout)
- the updated strings.xml (res/values)
- *A modified emulator

The emulator may need to be modified to enable the physical buttons.
1. Open the Android Virtual Device Manager
2. Select the Device Definitions tab
3. Double-click the device you are using for your mapApp emulator
4. If the 'Buttons' setting is set to 'Hardware,' the physical buttons are already enabled.  Otherwise, change the 'Buttons' from 'Software' to 'Hardware' and click 'Clone Device'.
5. Update your emulator to use the new cloned device
	 */
	
	//Include the following declarations
	Button searchButton;
	Button bookmarkButton;
	Button mapButton;
	
	RelativeLayout relativeLayout;
	
	Spinner catSpinner;
	ArrayAdapter<CharSequence> cat;
	Button searchName;
	Button searchCat;
	EditText searchText;
	String catSelection = "";
	
	TextView bookMarkText;
	Button bookMapButton;
	
	TextView latText;
	TextView longText;
	Button enterButton;
	
	MapView mapView;
	MapView bookmarkMap;
	
	public void onCreate(Bundle savedInstanceState){
		// Make database
		db = new DBAdapter(this);

		// If there are no entries, input entries
		db.open();

		if(!db.isEmpty()) {
			db.insertEntries();
		}

		db.close();

		super.onCreate(savedInstanceState);

		mapView = new MapView(this, 256); //constructor
		mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);

		//onCreate code to enable the menus begins here
		bookMarkText = new TextView(this);
		bookMarkText.setTextAppearance(this, android.R.style.TextAppearance_Large_Inverse);
		bookMarkText.setBackgroundColor(Color.BLACK);
		bookMarkText.setText("Press the menu button for more options");
			
		bookMapButton = new Button(this);
		bookMapButton.setText("Click HERE to add a bookmark");
		
		relativeLayout = new RelativeLayout(this);

		final RelativeLayout.LayoutParams mapViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
		final RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		relativeLayout.addView(mapView, mapViewLayoutParams);
		relativeLayout.addView(bookMarkText, textViewLayoutParams);
		
		setContentView(relativeLayout); //displaying the MapView
		//onCreate code to enable menus ends here
		
		mapView.getController().setZoom(15); //set initial zoom-level, depends on your need
		mapView.getController().setCenter(new GeoPoint(39.264025, -76.700492)); 
		mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.

		cat = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

	}
	
	//Copy and past the following functions into your MapApp
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 switch(keyCode){
		 case KeyEvent.KEYCODE_MENU: 
 		 
		     setContentView(R.layout.activity_menu);
		
		     searchButton = (Button) findViewById(R.id.search);
		     bookmarkButton = (Button) findViewById(R.id.bookmark);
		     mapButton = (Button) findViewById(R.id.map);
		     
		     searchButton.setOnClickListener(new ButtonListener());
		     bookmarkButton.setOnClickListener(new ButtonListener());
		     mapButton.setOnClickListener(new ButtonListener());
		        
		    return true; 	   
		   case KeyEvent.KEYCODE_SEARCH:
			 onSearchRequested();
		     return true;
		   case KeyEvent.KEYCODE_BACK:
		     onBackPressed();
		     return true;
		   case KeyEvent.KEYCODE_VOLUME_UP:
		     event.startTracking();
		     return true;
		   case KeyEvent.KEYCODE_VOLUME_DOWN:
		     event.startTracking();
		     return true;
		     
		 }
		 return super.onKeyDown(keyCode, event);
		}	
	
	private class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View V) {
			// TODO Auto-generated method stub
			
			if(searchButton.getId() == ((Button)V).getId()){

				setContentView(R.layout.activity_search);
				
				//findViewById for all activity_search components
				catSpinner = (Spinner) findViewById(R.id.type1);
				cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				catSpinner.setAdapter(cat);
				catSpinner.setOnItemSelectedListener(new SpinnerActivity());
				
				searchName = (Button) findViewById(R.id.buttonSearchName);
				searchName.setOnClickListener(new ButtonListener());
				searchCat = (Button) findViewById(R.id.buttonSearchCat);
				searchCat.setOnClickListener(new ButtonListener());

				searchText = (EditText) findViewById(R.id.searchText);
				searchText.setOnEditorActionListener(new OnEditorActionListener() {
				    @Override
				    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				        boolean handled = false;
				        if (actionId == EditorInfo.IME_ACTION_SEND) {
				            //sendMessage();
				            handled = true;
				        }
				        return handled;
				    }
				});
				
				//add activity_search buttons to listener

				
			}else if(bookmarkButton.getId() == ((Button)V).getId()){
										
				bookMarkText.setText("Click to add a bookmark");
		        setContentView(relativeLayout); //displaying the MapView

				//Any default map settings go here...
				
				mapView.getController().setZoom(15); //set initial zoom-level, depends on your need
				mapView.getController().setCenter(new GeoPoint(39.264025, -76.700492)); 
				mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.

								
			}else if(mapButton.getId() == ((Button)V).getId()){
								
				bookMarkText.setText("Press the menu button for more options");

				setContentView(relativeLayout); //displaying the MapView

				//Any default map settings go here...
				
				mapView.getController().setZoom(15); //set initial zoom-level, depends on your need
				mapView.getController().setCenter(new GeoPoint(39.264025, -76.700492)); 
				mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.

			}
			
			else if(bookMapButton.getId() == ((Button)V).getId()){
				
				setContentView(R.layout.activity_bookmark);
				
				latText = (TextView) findViewById(R.id.latitude);
				longText = (TextView) findViewById(R.id.longitude);

				enterButton = (Button) findViewById(R.id.enter);
				
			}
			
			//The user is searching by a category selected via dropdown
			else if(searchCat.getId() == ((Button)V).getId()){
				
				//search using catSelection as your parameter				
				
			}
			
			//else if(searchName
			//else if(finishBook
			
		}
		
	}
	
	public class SpinnerActivity extends Activity implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			// TODO Auto-generated method stub

			if(catSpinner.getId() == ((Spinner)parent).getId()){
			
				catSelection = parent.getItemAtPosition(pos).toString();
				catSelection = catSelection.toLowerCase();
				
			}
			
			//txtView.setText(selection1);
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
