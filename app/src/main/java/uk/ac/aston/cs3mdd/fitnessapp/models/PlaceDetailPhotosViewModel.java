package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.PhotoResponseCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetail;

public class PlaceDetailPhotosViewModel extends ViewModel {
    private MutableLiveData<List<Bitmap>> allPlacesPhotos;

    public PlaceDetailPhotosViewModel(){
        super();
        allPlacesPhotos = new MutableLiveData<>(new ArrayList<>());
    }

    public void requestPhotos(PlacesRepository repository, String photoReference, int maxWidth, int maxHeight, String key, PlaceDetail currentPlaceDetail){
        Call<ResponseBody> responseBodyCall = repository.getPlacePhoto(photoReference,maxWidth,maxHeight,key);
        responseBodyCall.enqueue(new PhotoResponseCallBack(currentPlaceDetail, this));
    }

    public LiveData<List<Bitmap>> getAllPlacePhotos(){
        return allPlacesPhotos;
    }

    public void clearPhotos(){
        allPlacesPhotos.getValue().clear();
        allPlacesPhotos.setValue(allPlacesPhotos.getValue());
    }

    public void addPlacePhoto(Bitmap bitmap){
        Log.i(MainActivity.TAG, "Adding new place photo");
        allPlacesPhotos.getValue().add(bitmap);
        allPlacesPhotos.setValue(allPlacesPhotos.getValue());
    }

}
