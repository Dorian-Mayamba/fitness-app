package uk.ac.aston.cs3mdd.fitnessapp.observers;

import android.util.Log;

import androidx.lifecycle.Observer;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.FoodProduct;
import uk.ac.aston.cs3mdd.fitnessapp.adapters.ProductAdapter;

public class FoodProductObserver implements Observer<List<FoodProduct>>{

    private ProductAdapter adapter;

    public FoodProductObserver(ProductAdapter adapter){
        this.adapter = adapter;
    }
    @Override
    public void onChanged(List<FoodProduct> foodProducts) {
        Log.i(MainActivity.TAG, "Displaying "+ foodProducts.size() + " products");
        for(FoodProduct foodProduct:foodProducts){
            Log.i(MainActivity.TAG, foodProduct.toString());
        }
        adapter.updateFoodList(foodProducts);
    }
}
