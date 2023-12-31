package uk.ac.aston.cs3mdd.fitnessapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentPlaceDetailBinding;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceDetailViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.providers.ServiceProvider;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Photo;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Place;
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

    private PlaceViewModel placeViewModel;

    private PlacesRepository repository;

    private String fields = "";

    private PlaceDetailViewModel model;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        place = PlaceDetailFragmentArgs.fromBundle(getArguments()).getPlace();
        placesServices = ServiceProvider.getPlacesServices();
        model = new ViewModelProvider(requireActivity()).get(PlaceDetailViewModel.class);
        repository = new PlacesRepository(placesServices);
        for (Place.Field field:Place.Field.values()){
            fields += fields.toString() + ",";
        }
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
        model.getCurrentPlaceDetail().observe(getViewLifecycleOwner(), placeDetail -> {
            if (placeDetail!= null){
                for (Photo photo:placeDetail.getPhotos()){
                    model.requestPhotos(repository,photo.getPhoto_reference(), 500,500, getString(R.string.google_maps_api_key));
                }
            }
        });
    }
}