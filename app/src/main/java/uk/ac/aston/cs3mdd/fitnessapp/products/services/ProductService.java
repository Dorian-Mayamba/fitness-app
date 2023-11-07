package uk.ac.aston.cs3mdd.fitnessapp.products.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import uk.ac.aston.cs3mdd.fitnessapp.products.collections.ProductList;

public interface ProductService {
    @Headers({
            "x-api-key: c0ea9e9e24664ee2a0891e27906bdeea"
    })
    @GET("food/products/search")
    Call<ProductList> getProducts(@Query("query") String query, @Query("number") int numberOfProducts);
}
