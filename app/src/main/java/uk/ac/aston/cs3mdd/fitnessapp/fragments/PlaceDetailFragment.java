package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.PlaceOpeningHoursAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.PlacePhotoAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentPlaceDetailBinding;
import uk.ac.aston.cs3mdd.fitnessapp.dialogs.OpeningHoursDialogFragment;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceDetailPhotosViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceDetailViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.providers.ServiceProvider;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Photo;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetail;
import uk.ac.aston.cs3mdd.fitnessapp.services.PlacesServices;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlaceDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlaceDetailFragment extends Fragment {

    private FragmentPlaceDetailBinding binding;

    private Place place;

    private PlacesServices placesServices;

    private PlacesRepository repository;

    private String fields = "";

    private PlaceDetailViewModel model;

    private PlaceDetailPhotosViewModel placeDetailPhotosViewModel;

    private SliderView placePhotoSlider;

    private RecyclerView openingHoursView;

    private TextView addressText, phoneNumberText, placeStatusText;

    private ImageView backButton;

    private PlaceDetail placeDetail;

    private Button displayHoursButton;

    public PlaceDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        place = PlaceDetailFragmentArgs.fromBundle(getArguments()).getPlace();
        placesServices = ServiceProvider.getPlacesServices();
        model = new ViewModelProvider(requireActivity()).get(PlaceDetailViewModel.class);
        placeDetailPhotosViewModel = new ViewModelProvider(requireActivity()).get(PlaceDetailPhotosViewModel.class);
        repository = new PlacesRepository(placesServices);
        for (Place.Field field:Place.Field.values()){
            fields += field.getFieldName() + ",";
        }
        fields = fields.substring(0,fields.length() - 1);
        Log.i(MainActivity.TAG, place.getPlace_id());
        Log.i(MainActivity.TAG, fields);
        model.requestPlaceDetail(repository,fields,place.getPlace_id(), getString(R.string.google_maps_api_key));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlaceDetailBinding.inflate(inflater, container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placePhotoSlider = view.findViewById(R.id.slider_view);
        PlacePhotoAdapter placePhotoAdapter = new PlacePhotoAdapter(placeDetailPhotosViewModel.getAllPlacePhotos().getValue());
        placePhotoSlider.setSliderAdapter(placePhotoAdapter);
        placePhotoSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        placePhotoSlider.setScrollTimeInSec(3);
        placePhotoSlider.setAutoCycle(true);
        placePhotoSlider.startAutoCycle();
        openingHoursView = view.findViewById(R.id.opening_hours_view);
        PlaceOpeningHoursAdapter openingHoursAdapter = new PlaceOpeningHoursAdapter(new ArrayList<>());
        addressText = view.findViewById(R.id.place_address);
        phoneNumberText = view.findViewById(R.id.place_phone);
        placeStatusText = view.findViewById(R.id.place_status);
        backButton = view.findViewById(R.id.back_btn_img);
        displayHoursButton = view.findViewById(R.id.display_hours_button);
        displayHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Display available hours after the user clicks
                new OpeningHoursDialogFragment(placeDetail.getOpening_hours().getWeekday_text()).show(getActivity()
                        .getSupportFragmentManager(),"OPENING_HOURS_DIALOG");
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PlaceDetailFragment.this)
                        .navigate(R.id.action_place_detail_to_local_gyms);
            }
        });
        model.getCurrentPlaceDetail().observe(getViewLifecycleOwner(), placeDetail -> {
            if (placeDetail!= null){
                binding.placeDetail.setText(placeDetail.getName());
                this.placeDetail = placeDetail;
                // update the current place detail's text
                addressText.setText(placeDetail.getFormatted_address());
                phoneNumberText.setText(placeDetail.getFormatted_phone_number());
                if (placeDetail.getOpening_hours() != null){
                    placeStatusText.setText(placeDetail.getOpening_hours().isOpen_now() ? "Opened" : "Closed");
                    for (String openingHours:placeDetail.getOpening_hours().getWeekday_text()){
                        Log.i(MainActivity.TAG, "Opening hour: "+openingHours);
                    }
                    displayHoursButton.setVisibility(View.VISIBLE);
                    openingHoursAdapter.updateOpeningHours(placeDetail.getOpening_hours().getWeekday_text());
                }else{
                    placeStatusText.setVisibility(View.GONE);
                }
                if (placeDetail.getPhotos() != null){
                    for (Photo photo:placeDetail.getPhotos()){
                        placeDetailPhotosViewModel.requestPhotos(repository,photo.getPhoto_reference(), 500,500, getString(R.string.google_maps_api_key),placeDetail);
                    }
                }else{
                    placePhotoSlider.setVisibility(View.GONE);
                }
            }
        });
        placeDetailPhotosViewModel.getAllPlacePhotos().observe(getViewLifecycleOwner(), placesPhotos->{
            if (placesPhotos != null){
                //Update the place photo slider adapter list
                placePhotoAdapter.submitList(placesPhotos);
            }
        });
    }

    @Override
    public void onDestroyView() {
        placeDetailPhotosViewModel.clearPhotos();
        super.onDestroyView();
    }
}