package uk.ac.aston.cs3mdd.fitnessapp.callbacks;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.collections.ProductList;
import uk.ac.aston.cs3mdd.fitnessapp.models.ProductViewModel;

public class ProductListCallBack implements Callback<ProductList>{

    private ProductViewModel productViewModel;

    public ProductListCallBack(ProductViewModel productViewModel){
        this.productViewModel = productViewModel;
    }

    @Override
    public void onResponse(Call<ProductList> call, Response<ProductList> response) {
        if(response.isSuccessful()){
            productViewModel.addProducts(response.body());
        }
    }

    @Override
    public void onFailure(Call<ProductList> call, Throwable t) {
        Log.i(MainActivity.TAG, "Error: "+t.getMessage());
    }
}
