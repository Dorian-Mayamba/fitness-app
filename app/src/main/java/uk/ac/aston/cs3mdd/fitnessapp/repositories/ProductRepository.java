package uk.ac.aston.cs3mdd.fitnessapp.repositories;

import retrofit2.Call;
import uk.ac.aston.cs3mdd.fitnessapp.collections.ProductList;
import uk.ac.aston.cs3mdd.fitnessapp.services.ProductService;

public class ProductRepository {
    ProductService productService;

    public ProductRepository(ProductService service){
        this.productService = service;
    }

    public Call<ProductList> getProducts(String query, int numberOfProducts){
        return this.productService.getProducts(query,numberOfProducts);
    }
}
