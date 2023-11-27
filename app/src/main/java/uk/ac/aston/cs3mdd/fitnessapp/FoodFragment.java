package uk.ac.aston.cs3mdd.fitnessapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.ac.aston.cs3mdd.fitnessapp.databinding.FragmentFoodBinding;
import uk.ac.aston.cs3mdd.fitnessapp.products.adapters.ProductAdapter;
import uk.ac.aston.cs3mdd.fitnessapp.products.models.ProductViewModel;
import uk.ac.aston.cs3mdd.fitnessapp.products.observers.FoodProductObserver;
import uk.ac.aston.cs3mdd.fitnessapp.products.repositories.ProductRepository;
import uk.ac.aston.cs3mdd.fitnessapp.products.services.ProductService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodFragment extends Fragment {

    private FragmentFoodBinding binding;

    private ProductViewModel productViewModel;

    private RecyclerView recyclerView;

    private ProductAdapter adapter;

    private EditText editText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        productViewModel = new ViewModelProvider(requireActivity()).get(ProductViewModel.class);
        binding = FragmentFoodBinding.inflate(getLayoutInflater());
        this.recyclerView = binding.foodRecycleView;
        this.adapter = new ProductAdapter(getContext(),productViewModel.getAllProducts().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        editText = binding.foodEdit;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ProductService productService = retrofit.create(ProductService.class);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productViewModel.requestProducts(new ProductRepository(productService), s.toString(), 20);
            }
        });
        productViewModel.getAllProducts().observe(getViewLifecycleOwner(), new FoodProductObserver(adapter));
    }
}