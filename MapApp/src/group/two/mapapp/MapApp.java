package group.two.mapapp;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;

import org.osmdroid.views.MapView;



import android.app.Activity;

import android.os.Bundle;



public class MapApp extends Activity {

    public void onCreate(Bundle savedInstanceState){
    	
        super.onCreate(savedInstanceState);

        MapView mapView = new MapView(this, 256); //constructor
        
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        
        mapView.setClickable(true);

        mapView.setBuiltInZoomControls(true);

        setContentView(mapView); //displaying the MapView

        mapView.getController().setZoom(15); //set initial zoom-level, depends on your need

        mapView.getController().setCenter(new GeoPoint(39.264025, -76.700492)); 

        mapView.setUseDataConnection(true); //keeps the mapView from loading online tiles using network connection.

    }
//	protected void onCreate(Bundle savedInstanceState) {
//	    super.onCreate(savedInstanceState);
//	    MapView mapView = new MapView(this, 256);
//	    mapView.setTileSource(TileSourceFactory.MAPNIK);
//	    mapView.setClickable(true);
//	    mapView.setBuiltInZoomControls(true);
//	    setContentView(mapView);
//	}
}