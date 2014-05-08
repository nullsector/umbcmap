package group.two.mapapp;


import java.util.ArrayList;
import java.util.Arrays;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;

import org.osmdroid.views.MapView;
import org.osmdroid.views.MapView.Projection;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.util.constants.MapViewConstants;



import android.app.Activity;
import android.database.Cursor;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
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



public class MapApp extends Activity implements LocationListener, MapViewConstants{
	
	//Variable declarations
	String nameSelection = "";

	EditText bNameField;
	String nameEntry;
	EditText bBuildField;
	String buildEntry;
	EditText bRoomField;
	String roomEntry;
	boolean clickFlag = false;
	boolean bookmarkFlag = false;
	boolean searchFlag = false;
	boolean hasResults = false;
		
	Spinner bType1;
	String t1Selection;
	Spinner bType2;
	String t2Selection;
	Spinner bType3;
	String t3Selection;

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
    DBAdapter db;
    String[] buildings = new String[] {"math and psychology building", "academic services building", "administration building", "albin o. kuhn library and gallery", "biological sciences building", "commons", "engineering building", "facilities management", "fine arts building", "information technology and engineering", "math and psychology building", "meyerhoff chemistry building", "performing arts and humanities building",
    		"physics building", "public policy building", "retriever athletics center", "sherman hall", "sondheim hall", "student development and success center", "technology research center", "university center", "erickson hall", "harbor hall", "chesapeake hall", "potomac hall", "susquehanna hall", "walker avenue apartments i", "walker avenue apartments ii", "west hill apartments", "terrace apartments", "hillside apartments", 
    		"dining hall", "university center", };
    double bookmarkLat, bookmarkLong;

	ItemizedOverlay<OverlayItem> mMyLocationOverlay;
	private ResourceProxy mResourceProxy;
	private MapView mapView;
	MapView bookmarkMap;
	
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mResourceProxy = new DefaultResourceProxyImpl(getApplicationContext());
        mapView = new MapView(this, 256); //constructor
        
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        
        mapView.setClickable(true);

        mapView.setBuiltInZoomControls(true);
        
    	 // make database
        db = new DBAdapter(this);

        // if there are no entries, input entries

        db.open();

        if (! db.isEmpty()) {

        db.insertEntries();

        }

        db.close();
        

		//onCreate code to enable the menus begins here
		bookMarkText = new TextView(this);
		bookMarkText.setTextAppearance(this, android.R.style.TextAppearance_Large_Inverse);
		bookMarkText.setBackgroundColor(Color.BLACK);
		bookMarkText.setText("Press the menu button for more options");

		bookMapButton = new Button(this);
		bookMapButton.setText("Click HERE to add a bookmark");

		relativeLayout = new RelativeLayout(this);

		@SuppressWarnings("deprecation")
		final RelativeLayout.LayoutParams mapViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT,RelativeLayout.LayoutParams.FILL_PARENT);
		@SuppressWarnings("deprecation")
		final RelativeLayout.LayoutParams textViewLayoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		relativeLayout.addView(mapView, mapViewLayoutParams);
		relativeLayout.addView(bookMarkText, textViewLayoutParams);

        setContentView(relativeLayout); //displaying the MapView

        mapView.getController().setZoom(16); //set initial zoom-level, depends on your need

        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));


        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        
        
        this.mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>(){
            @Override
            public boolean onItemSingleTapUp(final int index,
                    final OverlayItem item) {
                Toast.makeText(
                        MapApp.this,
                        "Item '" + item.getTitle(), Toast.LENGTH_LONG).show();
                return true; // We 'handled' this event.
            }
            @Override
            public boolean onItemLongPress(final int index,
                    final OverlayItem item) {
                Toast.makeText(
                        MapApp.this, 
                        "Item '" + item.getTitle() ,Toast.LENGTH_LONG).show();
                return false;
            }
        	}, mResourceProxy);
        mapView.getOverlays().add(this.mMyLocationOverlay);
        mapView.invalidate();
        mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.
        cat = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);

    }

    //Un-used Overriden functions
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	//On Single click override
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    int actionType = ev.getAction();
	    switch (actionType) {
	    case MotionEvent.ACTION_UP:
	    	//If we are adding a book mark
	    	if(clickFlag){
	            Projection proj = mapView.getProjection();
	            GeoPoint loc = (GeoPoint) proj.fromPixels((int)ev.getX(), (int)ev.getY()); 
	            bookmarkLong = ((double)loc.getLongitudeE6())/1000000;
	            bookmarkLat = ((double)loc.getLatitudeE6())/1000000;
	            setContentView(R.layout.activity_bookmark);

	            bNameField = (EditText)findViewById(R.id.bNameField);
	            bNameField.setOnEditorActionListener(new OnEditorActionListener() {
	            	@Override
	            	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

	            		nameEntry = bNameField.getText().toString();

	            		//User needs to use xml (enterButton) button

	            		return true;

	            	}
	            });
	            bBuildField = (EditText)findViewById(R.id.bBuildField);
	            bBuildField.setOnEditorActionListener(new OnEditorActionListener() {
	            	@Override
	            	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

	            		buildEntry = bBuildField.getText().toString();

	            		//User needs to use xml (enterButton) button

	            		return true;
	            	}
	            });
	            bRoomField = (EditText)findViewById(R.id.bRoomField);
	            bRoomField.setOnEditorActionListener(new OnEditorActionListener() {
	            	@Override
	            	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

	            		roomEntry = bRoomField.getText().toString();

	            		//User needs to use xml (enterButton) button

	            		return true;

	            	}
	            });

	            bType1 = (Spinner) findViewById(R.id.bType1);
	            cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	            bType1.setAdapter(cat);
	            bType1.setOnItemSelectedListener(new SpinnerActivity());

	            bType2 = (Spinner) findViewById(R.id.bType2);
	            cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	            bType2.setAdapter(cat);
	            bType2.setOnItemSelectedListener(new SpinnerActivity());

	            bType3 = (Spinner) findViewById(R.id.bType3);
	            cat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	            bType3.setAdapter(cat);
	            bType3.setOnItemSelectedListener(new SpinnerActivity());

	            enterButton = (Button) findViewById(R.id.enter);
	            enterButton.setOnClickListener(new ButtonListener());
	            
	            //Code used to display lat and longitude, used for testing
//	            Toast toast = Toast.makeText(getApplicationContext(), "Longitude: "+ longitude +" Latitude: "+ latitude , Toast.LENGTH_LONG);
//	            toast.show();  
	            clickFlag = false;
	    	}

	    }
	return super.dispatchTouchEvent(ev);}
	

	//Menu functionality for the app
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 switch(keyCode){
		 case KeyEvent.KEYCODE_MENU: 
 		 
			 searchFlag = false;
			 bookmarkFlag = false;
			 
			 if(clickFlag){
				 clickFlag = false;
			 }
			 
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
			
			//We are in the bookmark function
			if(bookmarkFlag){				
				if(enterButton.getId() == ((Button)V).getId()){
					mapView.getOverlays().clear();							
					nameEntry = bNameField.getText().toString();
					       
					buildEntry = bBuildField.getText().toString();
					        	
					roomEntry = bRoomField.getText().toString();
					
					db.open();
					
					db.insertBookmarks(nameEntry, buildEntry, roomEntry, bookmarkLat+.0018, bookmarkLong, t1Selection, t2Selection, t3Selection);
					
					db.close();	

					bNameField.setText("");
					bBuildField.setText("");
					bRoomField.setText("");
				}
				bookmarkFlag = false;
			}
			//We are in the search function
			else if(searchFlag){
				//The user is searching by a category selected via drop down
				if(searchCat.getId() == ((Button)V).getId()){
					mapView.getOverlays().clear();
					ArrayList<OverlayItem> searchItems = new ArrayList<OverlayItem>();
					GeoPoint searchResult = null;
					String info = "";
					Cursor c;
					
					db.open();
					
					c = db.getEntryByType(catSelection);
					c.moveToFirst();
					while(c.moveToNext()){
						searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
						info = c.getString(3);
						searchItems.add(new OverlayItem(info, "sample description", searchResult));
					}
					
					
					db.close();
					
					mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(searchItems, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>(){
			            @Override
			            public boolean onItemSingleTapUp(final int index,
			                    final OverlayItem item) {
			                Toast.makeText(
			                        MapApp.this,
			                        "Item '" + item.getTitle(), Toast.LENGTH_LONG).show();
			                return true; // We 'handled' this event.
			            }
			            @Override
			            public boolean onItemLongPress(final int index,
			                    final OverlayItem item) {
			                Toast.makeText(
			                        MapApp.this, 
			                        "Item '" + item.getTitle() ,Toast.LENGTH_LONG).show();
			                return false;
			            }
			        	}, mResourceProxy);
			        mapView.getOverlays().add(mMyLocationOverlay);

			        setContentView(relativeLayout); //displaying the MapView

			        mapView.getController().setZoom(16); //set initial zoom-level, depends on your need

			        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
					mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.
					searchFlag = false;
				}
				else if(searchName.getId() == ((Button)V).getId()){
					mapView.getOverlays().clear();				
					db.open();
					
					ArrayList<OverlayItem> searchItems = new ArrayList<OverlayItem>();
					String search = searchText.getText().toString();
					String info = "";
					GeoPoint searchResult = null;				
					Cursor c;
					
					if(Arrays.asList(buildings).contains(search.toLowerCase())){
						c = db.getEntryByBuilding(search);
						try{
						if(c.moveToFirst()){
							searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
							info = c.getString(1) + "\n";
							while(c.moveToNext()){
								info += c.getString(1) + "\n";
							}
							hasResults = true;
						}
						} catch(Exception e){
							
						}
						c = db.getBookmarkByBuilding(search);
						try{
						if(c.moveToFirst()){
							searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
							info = c.getString(1) + "\n";
							while(c.moveToNext()){
								info += c.getString(1) + "\n";
							}
							hasResults = true;
						}
						}catch(Exception e){
							
						}
					}else{
						c = db.getEntryByName(search);
						try{
						if(c.moveToFirst()){
							searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
							info = c.getString(1) + "\n";
							while(c.moveToNext()){
								info += c.getString(1) + "\n";
							}		
							hasResults = true;					
						}
						}catch(Exception e){
							
						}
						c = db.getBookmarkByName(search);
						try{				
						if(c.moveToFirst()){
							searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
							info = c.getString(1) + "\n";
							while(c.moveToNext()){
								info += c.getString(1) + "\n";
							}				
							hasResults = true;
						}
						}catch(Exception e){
							
						}
					}
					
			        searchItems.add(new OverlayItem(info, "SampleDescription", searchResult));
			        
			        db.close();
			        
			        if(hasResults){
			        	mMyLocationOverlay = new ItemizedIconOverlay<OverlayItem>(searchItems, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>(){
			        		@Override
			        		public boolean onItemSingleTapUp(final int index,
			        				final OverlayItem item) {
			        			Toast.makeText(
			        					MapApp.this,
			        					"Item '" + item.getTitle(), Toast.LENGTH_LONG).show();
			        			return true; // We 'handled' this event.
			        		}
			        		@Override
			        		public boolean onItemLongPress(final int index,
			        				final OverlayItem item) {
			        			Toast.makeText(
			        					MapApp.this, 
			        					"Item '" + item.getTitle() ,Toast.LENGTH_LONG).show();
			        			return false;
			        		}
			        	}, mResourceProxy);
			        	mapView.getOverlays().add(mMyLocationOverlay);

			        	setContentView(relativeLayout); //displaying the MapView

			        	mapView.getController().setZoom(16); //set initial zoom-level, depends on your need

			        	mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
			        	mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.
			        	hasResults = false;
						searchFlag = false;
			        }else{
						searchText.setText("Not Valid Location");
			        }
				}
			}
			//Else we are not in the book mark or search functions. We are just in the menu.
			else{				
				if(searchButton.getId() == ((Button)V).getId()){
					mapView.getOverlays().clear();
					bookmarkFlag = false;
					searchFlag = true;
					
					setContentView(R.layout.activity_search);
					
					//	findViewById for all activity_search components
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
							
							nameSelection = searchText.getText().toString();
							
							//	User needs to use xml button (searchName) to search
					    		
							return true;
							
						}
					});
					
							
				}
				else if(bookmarkButton.getId() == ((Button)V).getId()){
					mapView.getOverlays().clear();
					bookmarkFlag = true;
					searchFlag = false;
					
					bookMarkText.setText("Click to add a bookmark");

			        setContentView(relativeLayout); //displaying the MapView

			        mapView.getController().setZoom(16); //set initial zoom-level, depends on your need

			        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
					mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.
					clickFlag = true;


				}else if(mapButton.getId() == ((Button)V).getId()){
					mapView.getOverlays().clear();
					searchFlag = false;
					bookmarkFlag = false;
					bookMarkText.setText("Press the menu button for more options");

			        setContentView(relativeLayout); //displaying the MapView

			        mapView.getController().setZoom(16); //set initial zoom-level, depends on your need

			        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
					mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.

				}
				
			}
		}

	}


	public class SpinnerActivity extends Activity implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
			// TODO Auto-generated method stub

			if(catSpinner != null){
				if(catSpinner.getId() == ((Spinner)parent).getId()){

					catSelection = parent.getItemAtPosition(pos).toString();
					catSelection = catSelection.toLowerCase();

				}

				else if(bType1.getId() == ((Spinner)parent).getId()){

					if(t1Selection != null){
						t1Selection = parent.getItemAtPosition(pos).toString();
						t1Selection = t1Selection.toLowerCase();
					}

				}

				else if(bType2.getId() == ((Spinner)parent).getId()){

					if(t2Selection != null){
						t2Selection = parent.getItemAtPosition(pos).toString();
						t2Selection = t2Selection.toLowerCase();
					}
				}

				else if(bType3.getId() == ((Spinner)parent).getId()){
					if(t3Selection != null){
						t3Selection = parent.getItemAtPosition(pos).toString();
						t3Selection = t3Selection.toLowerCase();
					}
				}				
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

	}

	
	
}