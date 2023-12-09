package uk.ac.aston.cs3mdd.fitnessapp.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.FoodProduct;
import uk.ac.aston.cs3mdd.fitnessapp.callbacks.ProductListCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.collections.ProductList;
import uk.ac.aston.cs3mdd.fitnessapp.repositories.ProductRepository;

public class ProductViewModel extends ViewModel{
    private MutableLiveData<List<FoodProduct>> allProducts;

    public ProductViewModel(){
        super();
        allProducts = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<FoodProduct>> getAllProducts() {
        return this.allProducts;
    }

    public void requestProducts(ProductRepository productRepository, String query, int numberOfProducts){
            Call<ProductList> productListCall = productRepository.getProducts(query,numberOfProducts);
            productListCall.enqueue(new ProductListCallBack(this));
    }

    public void addProducts(ProductList productList){
        if(this.allProducts.getValue().size() > 0){
            Log.i(MainActivity.TAG, "Clearing the list");
            allProducts.getValue().clear();
        }
        allProducts.getValue().addAll(productList.getProducts());
        allProducts.setValue(allProducts.getValue());
    }
}
