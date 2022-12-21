package com.example.foodcateringapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodcateringapp.Interface.FoodItemClickListener;
import com.example.foodcateringapp.Model.Food;
import com.example.foodcateringapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class FoodAdapter extends FirebaseRecyclerAdapter<Food,FoodAdapter.FoodViewHolder> {

    public FoodAdapter(@NonNull FirebaseRecyclerOptions<Food> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
        Picasso.get().load(model.getImage()).fit().centerCrop().placeholder(R.drawable.alt_img_category).into(holder.imgFood);

        holder.txtFood.setText(model.getName());


        final Food clickItem = model;

        holder.setFoodItemClickListener(new FoodItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

            }
        });
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent,false);
        return new FoodViewHolder(view);
    }

    //Our View Holder
    public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtFood;
        public ImageView imgFood;

        private FoodItemClickListener foodItemClickListener;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFood = (TextView) itemView.findViewById(R.id.nameFood);
            imgFood = (ImageView) itemView.findViewById(R.id.imgFood);

            itemView.setOnClickListener(this);
        }

        public void setFoodItemClickListener(FoodItemClickListener FoodItemClickListener) {
            this.foodItemClickListener = foodItemClickListener;
        }

        @Override
        public void onClick(View view) {
            foodItemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }
}
