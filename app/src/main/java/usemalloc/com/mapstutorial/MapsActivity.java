package usemalloc.com.mapstutorial;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //private static final String TAG = MapsActivityCurrentPlace.class.getSimpleName();
    private CameraPosition cameraPosition;

    //Places API
    private GeoDataClient geoDataClient;
    private PlaceDetectionClient placeDetectionClient;

    //Fused Location Provider
    private FusedLocationProviderClient fusedLocationProviderClient;

    //Default location when location permission is not provided
    private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    //Last Know location
    private Location lastKnownLocation;

    // Activity state
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    //For the selection of current place
    private static final int M_MAX_ENTRIES =  5;
    private String placeName;
    private String placeAddress;
    private String placeAttributions;
    private String placeLatLang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieve location and camera position from saved instances
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        setContentView(R.layout.activity_maps);

        geoDataClient = Places.getGeoDataClient(this,null);

        placeDetectionClient = Places.getPlaceDetectionClient(this,null);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //Save the state of the map when activity is paused.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION,mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION,lastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    // Set up menu option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu,menu);
        return false;
    }

    // Click on menu option handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.option_get_place) {
            showCurrentPlace();
        }
        return true;
    }

    //Manipulate the map when available
    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,(FrameLayout) findViewById(R.id.map),false);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        //Prompt for location permission
        getLocationPermission();

        //Turn on my location layer
        updateLocationUI();

        //get the current location of the device
        getDeviceLocation();
    }

    private void getDeviceLocation() {
        
    }












}
