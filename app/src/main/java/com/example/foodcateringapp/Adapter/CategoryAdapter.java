package com.example.foodcateringapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodcateringapp.HomeActivity;
import com.example.foodcateringapp.Interface.CategoryItemClickListener;
import com.example.foodcateringapp.Model.Category;
import com.example.foodcateringapp.R;
import com.example.foodcateringapp.ui.food.FoodFragment;
import com.example.foodcateringapp.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category,CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull Category model) {

        Picasso.get().load(model.getImage()).fit().centerCrop().placeholder(R.drawable.alt_img_category).into(holder.imgCategory);

        holder.txtCategory.setText(model.getName());


        final Category clickItem = model;

        holder.setCategoryItemClickListener(new CategoryItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                Toast.makeText(view.getContext(), ""+clickItem.getName(),Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FoodFragment foodFragment = new FoodFragment();
                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.homeFrag, foodFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent,false);
        return new CategoryViewHolder(view);
    }




    //Our View Holder
    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtCategory;
        public ImageView imgCategory;

        private CategoryItemClickListener categoryItemClickListener;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            imgCategory = (ImageView) itemView.findViewById(R.id.imgCategory);

            itemView.setOnClickListener(this);
        }

        public void setCategoryItemClickListener(CategoryItemClickListener categoryItemClickListener) {
            this.categoryItemClickListener = categoryItemClickListener;
        }

        @Override
        public void onClick(View view) {
            categoryItemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }

}
