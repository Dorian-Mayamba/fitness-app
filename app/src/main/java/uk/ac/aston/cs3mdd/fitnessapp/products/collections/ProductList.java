package uk.ac.aston.cs3mdd.fitnessapp.products.collections;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.products.FoodProduct;

public class ProductList{
    List<FoodProduct> products;

    public ProductList(){
        this.products = new ArrayList<>();
    }

    public List<FoodProduct> getProducts() {
        return this.products;
    }
}
