package uk.ac.aston.cs3mdd.fitnessapp.collections;

import java.util.ArrayList;
import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.serializers.FoodProduct;

public class ProductList{
    List<FoodProduct> products;

    public ProductList(){
        this.products = new ArrayList<>();
    }

    public List<FoodProduct> getProducts() {
        return this.products;
    }
}
