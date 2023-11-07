package uk.ac.aston.cs3mdd.fitnessapp.products.observers;

import android.util.Log;

import androidx.lifecycle.Observer;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.products.FoodProduct;

public class FoodProductObserver implements Observer<List<FoodProduct>>{
    @Override
    public void onChanged(List<FoodProduct> foodProducts) {
        Log.i(MainActivity.TAG, "Displaying "+ foodProducts.size() + " products");
        for(FoodProduct foodProduct:foodProducts){
            Log.i(MainActivity.TAG, foodProduct.toString());
        }
    }
}