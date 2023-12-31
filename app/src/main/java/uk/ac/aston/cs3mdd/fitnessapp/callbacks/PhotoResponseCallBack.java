package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.PlaceDetail;
import uk.ac.aston.cs3mdd.fitnessapp.util.BitmapGenerator;

public class PhotoResponseCallBack implements Callback<ResponseBody> {

    private PlaceDetail detail;

    public PhotoResponseCallBack(PlaceDetail detail) {
        this.detail = detail;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if (response.isSuccessful()) {
            detail.addPhotoGraphic(BitmapGenerator.getImageFormResponseBody(response.body()));
        } else {
            Log.e(MainActivity.TAG, "Error: " + response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        Log.e(MainActivity.TAG, "Error: " + t.getMessage());
    }
}
