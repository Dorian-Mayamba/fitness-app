package uk.ac.aston.cs3mdd.fitnessapp.products.models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.ac.aston.cs3mdd.fitnessapp.MainActivity;
import uk.ac.aston.cs3mdd.fitnessapp.products.FoodProduct;
import uk.ac.aston.cs3mdd.fitnessapp.products.callbacks.ProductListCallBack;
import uk.ac.aston.cs3mdd.fitnessapp.products.collections.ProductList;
import uk.ac.aston.cs3mdd.fitnessapp.products.repositories.ProductRepository;

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
        if(this.allProducts.getValue().size() == 0){
            Log.i(MainActivity.TAG, "Adding the new products to the list");
            this.allProducts.getValue().addAll(productList.getProducts());
            this.allProducts.setValue(this.allProducts.getValue());
        }else{
            Log.i(MainActivity.TAG, "Food product list not empty, updating the list");
            this.allProducts.setValue(productList.getProducts());
        }
    }
}
