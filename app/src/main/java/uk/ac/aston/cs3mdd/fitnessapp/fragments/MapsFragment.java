package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentMapsBinding;
import uk.ac.aston.cs3mdd.fitnessapp.models.LocationViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;
import uk.ac.aston.cs3mdd.fitnessapp.services.PlacesServices;

public class MapsFragment extends Fragment {


    private FusedLocationProviderClient fusedLocationClient;

    private LocationCallback locationCallback;

    private LocationRequest locationRequest;
    private FragmentMapsBinding binding;

    final String REQUEST_LOCATION_UPDATE_KEY = "isRequesting";

    final String PLACE_KEYWORD = "puregym";

    final int PLACE_RADIUS = 1230;

    final String PLACE_TYPE = "gym";

    private String API_KEY;
    private LatLng currentUserLocationCoordinates;

    private Location currentUserLocation;
    private PlacesServices services;

    private OnMapReadyCallback callback =
            new OnMapReadyCallback() {

                /**
                 * Manipulates the map once available.
                 * This callback is triggered when the map is ready to be used.
                 * This is where we can add markers or lines, add listeners or move the camera.
                 * In this case, we just add a marker near Sydney, Australia.
                 * If Google Play services is not installed on the device, the user will be prompted to
                 * install it inside the SupportMapFragment. This method will only be triggered once the
                 * user has installed Google Play services and returned to the app.
                 */
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    LatLng astonUniversity = new LatLng(52.48679922085286, -1.8878185869387778);
                    placeViewModel.requestPlaces(new PlacesRepository(services), PLACE_KEYWORD, astonUniversity.latitude + "," + astonUniversity.longitude,
                            PLACE_RADIUS,PLACE_TYPE,API_KEY);
                    map.addMarker(new MarkerOptions().
                            position(astonUniversity).title("Aston University"));
                    currentUserLocationCoordinates = new LatLng(52.486944165236686, -1.9495126024884437);
                    map.addMarker(new MarkerOptions().position(currentUserLocationCoordinates).title("You Are here"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(astonUniversity));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocationCoordinates, 10));
                }
            };

    private LocationViewModel locationViewModel;

    private Boolean requestingLocationUpdates;

    private GoogleMap map;

    private Retrofit retrofit;

    private PlaceViewModel placeViewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull
                                         LocationResult
                                                 locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult != null) {
                    for (Location location :
                            locationResult.getLocations()) {
                        currentUserLocation = location;
                        placeViewModel.requestPlaces(new PlacesRepository(services), PLACE_KEYWORD, currentUserLocation.getLatitude() + "," + currentUserLocation.getLongitude(),
                                PLACE_RADIUS,PLACE_TYPE,API_KEY);
                        locationViewModel.setCurrentLocation(currentUserLocation);
                    }
                }
            }
        };
        locationViewModel = new ViewModelProvider(requireActivity())
                .get(LocationViewModel.class);
        requestingLocationUpdates = true;
        updateStateFromBundleState(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        placeViewModel = new ViewModelProvider(requireActivity()).get(PlaceViewModel.class);
        API_KEY = getString(R.string.google_maps_api_key);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestUserPermissions();
        locationViewModel.getCurrentLocation().observe(
                getViewLifecycleOwner(), location -> {
                    if (location != null) {
                        //Update the UI
                        currentUserLocationCoordinates = new LatLng(location.getLatitude(),location.getLongitude());

                        if(map!=null){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocationCoordinates, 10));
                        }
                    }
                });
        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        services = retrofit.create(PlacesServices.class);
        placeViewModel.getAllPlaces().observe(getViewLifecycleOwner(), places -> {
            if (places != null){
                for (Place place:places){
                    LatLng latLng = new LatLng(place.getGeometry().getLocation().getLat(),
                            place.getGeometry().getLocation().getLng());
                    if (map!=null){
                        map.addMarker(new MarkerOptions().
                                title(place.getName())
                                .position(latLng));
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(REQUEST_LOCATION_UPDATE_KEY, requestingLocationUpdates);
    }
    @Override
    public void onStart() {
        super.onStart();
        if (locationRequest == null) {
            createLocationRequest();
        }
        startLocationUpdates();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requestingLocationUpdates = false;
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    protected void updateStateFromBundleState(Bundle savedInstanceState) {
        if(savedInstanceState == null){
            return;
        }

        if(savedInstanceState.keySet().contains(REQUEST_LOCATION_UPDATE_KEY)){
            requestingLocationUpdates =  savedInstanceState.getBoolean(REQUEST_LOCATION_UPDATE_KEY);
        }
    }

    protected void createLocationRequest() {
        this.locationRequest = new LocationRequest.Builder(10000)
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .build();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        //Success
                        Log.i(MainActivity.TAG, locationSettingsResponse.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ResolvableApiException) {
                            try {
                                ResolvableApiException exception = (ResolvableApiException) e;
                                ((ResolvableApiException) e).startResolutionForResult(requireActivity(), 1);
                            } catch (IntentSender.SendIntentException ex) {
                                //Ignore
                            }
                        }
                    }
                });
    }

    private void requestUserPermissions() {
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts.
                        RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(android.Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null) {
                        //Approximate location request
                        SupportMapFragment mapFragment =
                                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                        if (mapFragment != null) {
                            mapFragment.getMapAsync(callback);
                        }
                    } else if (fineLocationGranted != null && coarseLocationGranted != null) {
                        // Precise location request
                        SupportMapFragment mapFragment =
                                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                        if (mapFragment != null) {
                            mapFragment.getMapAsync(callback);
                        }
                    } else {
                        // Can't do location request
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                },
                                1
                        );
                    }
                });

        locationPermissionRequest.launch(new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        });
    }


}