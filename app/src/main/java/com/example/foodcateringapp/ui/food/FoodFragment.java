package com.example.foodcateringapp.ui.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodcateringapp.Adapter.CategoryAdapter;
import com.example.foodcateringapp.Adapter.FoodAdapter;
import com.example.foodcateringapp.Model.Category;
import com.example.foodcateringapp.Model.Food;
import com.example.foodcateringapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FoodFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView recycler_food;
    private RecyclerView.LayoutManager layoutManager;
    private DatabaseReference foodReferences;
    private FirebaseRecyclerOptions<Food> optionsParams;

    private FoodAdapter foodAdapter;

    public FoodFragment() {
        // Required empty public constructor
    }


    public static FoodFragment newInstance(String param1) {
        FoodFragment fragment = new FoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        // Load Category List Menu
        recycler_food = (RecyclerView) view.findViewById(R.id.food_list);
        recycler_food.setLayoutManager(new LinearLayoutManager(getContext()));

        // Take the references
        foodReferences = FirebaseDatabase.getInstance().getReference("Foods");
        optionsParams = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(foodReferences, Food.class)
                .build();

        foodAdapter = new FoodAdapter(optionsParams);
        recycler_food.setAdapter(foodAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        foodAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        foodAdapter.stopListening();
    }
}