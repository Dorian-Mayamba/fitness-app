package uk.ac.aston.cs3mdd.fitnessapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.ac.aston.cs3mdd.fitnessapp.R;
import uk.ac.aston.cs3mdd.fitnessapp.serializers.FoodProduct;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private List<FoodProduct> foodProductList;

    private LayoutInflater inflater;

    public ProductAdapter(Context context, List<FoodProduct> foodProducts){
        this.foodProductList = foodProducts;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_items,parent,false);
        return new ProductViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        FoodProduct product = this.foodProductList.get(position);
        holder.foodNameView.setText(product.getTitle());
        Picasso.get().load(product.getImage()).into(holder.foodImageView);
    }

    @Override
    public int getItemCount() {
        return this.foodProductList.size();
    }

    public void updateFoodList(List<FoodProduct> foodProducts){
        this.foodProductList = foodProducts;
        notifyDataSetChanged();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder{
        private ProductAdapter adapter;

        private ImageView foodImageView;

        private TextView foodNameView;
        public ProductViewHolder(@NonNull View itemView, ProductAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.foodImageView = itemView.findViewById(R.id.food_image);
            this.foodNameView = itemView.findViewById(R.id.food_name);
        }
    }
}
