package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.PhotoResponseCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.PlaceDetailCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.PlacesRepository;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetail;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetailResponse;

public class PlaceDetailViewModel extends ViewModel {
    private MutableLiveData<PlaceDetail> currentPlaceDetail; //keep track on the current place detail
    public PlaceDetailViewModel(){
        super();
        currentPlaceDetail = new MutableLiveData<PlaceDetail>();
    }

    public void requestPlaceDetail(PlacesRepository repository, String fields, String placeId, String apiKey){
        Call<PlaceDetailResponse> detailResponseCall = repository.getPlaceDetail(fields,placeId,apiKey);
        detailResponseCall.enqueue(new PlaceDetailCallBack(this));
    }

    public LiveData<PlaceDetail> getCurrentPlaceDetail(){
        return this.currentPlaceDetail;
    }
    public void setCurrentPlaceDetail(PlaceDetail newPlaceDetail){
        this.currentPlaceDetail.setValue(newPlaceDetail);
    }
}
