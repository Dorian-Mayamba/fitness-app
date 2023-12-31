package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.FitnessApplication;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.models.PlaceDetailViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.providers.ServiceProvider;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.Photo;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetail;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetailResponse;
import uk.ac.aston.cs3mdd.fitnessapp.services.PlacesServices;
import uk.ac.aston.cs3mdd.fitnessapp.util.BitmapGenerator;

public class PlaceDetailCallBack implements Callback<PlaceDetailResponse> {

    private final PlaceDetailViewModel model;

    public PlaceDetailCallBack(PlaceDetailViewModel model) {
        this.model = model;
    }

    @Override
    public void onResponse(Call<PlaceDetailResponse> call, Response<PlaceDetailResponse> response) {
        if (response.isSuccessful()) {
            PlaceDetail detail = response.body().getResult();
            model.setCurrentPlaceDetail(detail);
        } else {
            Log.e(MainActivity.TAG, "Error: " + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<PlaceDetailResponse> call, Throwable t) {
        Log.e(MainActivity.TAG, "Error: "+ t.getMessage());
    }
}


