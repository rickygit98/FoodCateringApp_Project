package com.example.foodcateringapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodcateringapp.Adapter.CategoryAdapter;
import com.example.foodcateringapp.Model.Category;
import com.example.foodcateringapp.R;
import com.example.foodcateringapp.databinding.FragmentHomeBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private RecyclerView recycler_menu;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference categoryReference;
    private FirebaseRecyclerOptions<Category> optionsParams;

    private CategoryAdapter categoryAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Load Category List Menu
        recycler_menu = (RecyclerView) view.findViewById(R.id.category_list);
        recycler_menu.setLayoutManager(new LinearLayoutManager(getContext()));

        // Take the references
        categoryReference = FirebaseDatabase.getInstance().getReference("Category");
        optionsParams = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(categoryReference, Category.class)
                .build();

        categoryAdapter = new CategoryAdapter(optionsParams);
        recycler_menu.setAdapter(categoryAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        categoryAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        categoryAdapter.stopListening();
    }

}