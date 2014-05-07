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
    String[] buildings = new String[] {"Math and Psychology Building", "Academic Services Building", "Administration Building", "Albin O. Kuhn Library and Gallery", "Biological Sciences Building", "Commons", "Engineering Building", "Facilities Management", "Fine Arts Building", "Information Technology and Engineering", "Math and Psychology Building", "Meyerhoff Chemistry Building", "Performing Arts and Humanities Building",
    		"Physics Building", "Public Policy Building", "Retriever Athletics Center", "Sherman Hall", "Sondheim Hall", "Student Development and Success Center", "Technology Research Center", "University Center", "Erickson Hall", "Harbor Hall", "Chesapeake Hall", "Potomac Hall", "Susquehanna Hall", "Walker Avenue Apartments I", "Walker Avenue Apartments II", "West Hill Apartments", "Terrace Apartments", "Hillside Apartments", 
    		"Dining Hall", "University Center"};

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
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    int actionType = ev.getAction();
	    switch (actionType) {
	    case MotionEvent.ACTION_UP:
	            Projection proj = mapView.getProjection();
	            GeoPoint loc = (GeoPoint) proj.fromPixels((int)ev.getX(), (int)ev.getY()); 
	            String longitude = Double.toString(((double)loc.getLongitudeE6())/1000000);
	            String latitude = Double.toString(((double)loc.getLatitudeE6())/1000000);
//				if(flag){
//				send to form page
//				add to db
//				flag = false
//			}
//	             Toast toast = Toast.makeText(getApplicationContext(), "Longitude: "+ longitude +" Latitude: "+ latitude , Toast.LENGTH_LONG);
//	            toast.show();

	    }
	return super.dispatchTouchEvent(ev);}
	

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

		        mapView.getController().setZoom(17); //set initial zoom-level, depends on your need

		        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
				mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.


			}else if(mapButton.getId() == ((Button)V).getId()){

				bookMarkText.setText("Press the menu button for more options");

		        setContentView(relativeLayout); //displaying the MapView

		        mapView.getController().setZoom(17); //set initial zoom-level, depends on your need

		        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
				mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.

			}

			else if(bookMapButton.getId() == ((Button)V).getId()){
				
			}

			//The user is searching by a category selected via drop down
			else if(searchCat.getId() == ((Button)V).getId()){
				ArrayList<OverlayItem> searchItems = new ArrayList<OverlayItem>();
				String search = "Food";
				GeoPoint searchResult = null;
				String info = "";
				
				Cursor c = db.getEntryByType(search);
				if(c != null){
					c.moveToFirst();
					searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
					info = c.getString(1);
					searchItems.add(new OverlayItem(info, "sample description", searchResult));
				}
				c = db.getBookmarkByType(search);
				if(c != null){
					c.moveToFirst();
					searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
					info = c.getString(1);
					searchItems.add(new OverlayItem(info, "sample description", searchResult));
				}

		        searchItems.add(new OverlayItem(info, "SampleDescription", searchResult));
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
		        setContentView(mapView);
				
			}

			else if(searchName.getId() == ((Button)V).getId()){
				
				db.open();
				
				ArrayList<OverlayItem> searchItems = new ArrayList<OverlayItem>();
				String search = "International Educational Services";
				String info = "";
				GeoPoint searchResult = null;				
				Cursor c;
				
//				try {
//				c.moveToFirst();
//				searchResult = new GeoPoint(Float.parseFloat(c.getString(4)), Float.parseFloat(c.getString(5)));
//				info = c.getString(1) + "\n";
//				while(c.moveToNext()){
//					info += c.getString(1) + "\n";
//				}
//				} catch (Exception e) {
//					// the database didn't have any results. Do something.
//				}
				
				if(Arrays.asList(buildings).contains(search)){
					c = db.getEntryByBuilding(search);
					if(c.moveToFirst()){
						searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
						info = c.getString(1) + "\n";
						while(c.moveToNext()){
							info += c.getString(1) + "\n";
						}					
					}
					c = db.getBookmarkByBuilding(search);
					if(c.moveToFirst()){
						searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
						info = c.getString(1) + "\n";
						while(c.moveToNext()){
							info += c.getString(1) + "\n";
						}
					}					
				}else{
					c = db.getEntryByName(search);
					if(c.moveToFirst()){
						searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
						info = c.getString(1) + "\n";
						while(c.moveToNext()){
							info += c.getString(1) + "\n";
						}							
					}
					c = db.getBookmarkByName(search);
					if(c.moveToFirst()){
						searchResult = new GeoPoint(Double.parseDouble(c.getString(4)), Double.parseDouble(c.getString(5)));
						info = c.getString(1) + "\n";
						while(c.moveToNext()){
							info += c.getString(1) + "\n";
						}				
					}
				}
				
		        searchItems.add(new OverlayItem(info, "SampleDescription", searchResult));
		        
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

		        mapView.getController().setZoom(17); //set initial zoom-level, depends on your need

		        mapView.getController().setCenter(new GeoPoint(39.255025, -76.711192));
				mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.
			}
			//else if(finishBook)
			

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