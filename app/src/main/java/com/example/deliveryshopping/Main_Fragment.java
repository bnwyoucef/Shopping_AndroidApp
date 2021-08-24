package com.example.deliveryshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Main_Fragment extends Fragment {
    private RecyclerView recyclerViewNewItems, recyclerViewPopular, recyclerViewSuggested;
    private BottomNavigationView navigationView;
    private MyDataBase myDataBase;
    private Main_Adapter adapterOne, adapterTwo, adapterThird;
    private ArrayList<Model_Grocery> itemList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.main_fragment,null);
        intiViews(view);
        itemList = (ArrayList<Model_Grocery>) myDataBase.getGroceryDao().getAllItems();
        //dealRecyclerViews();
        navigationView.setSelectedItemId(R.id.homeBottom);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeBottom:
                        break;
                    case R.id.searchBottom:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        getActivity().startActivity(intent);
                        break;
                    case R.id.cartBottom:
                        Intent intent1 = new Intent(getActivity(), CartActivity.class);
                        getActivity().startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return view;
    }
    private void intiViews(View view){
        recyclerViewNewItems = view.findViewById(R.id.recyclerNewItems);
        recyclerViewPopular = view.findViewById(R.id.recyclerPopularItems);
        recyclerViewSuggested = view.findViewById(R.id.recyclerSuggested);
        navigationView = view.findViewById(R.id.navigationBottom);
        myDataBase = MyDataBase.getInstance(getActivity());
        adapterOne = new Main_Adapter(getActivity());
        adapterTwo = new Main_Adapter(getActivity());
        adapterThird = new Main_Adapter(getActivity());
    }
    private void dealRecyclerViews(){

        ArrayList<Model_Grocery> newItems = (ArrayList<Model_Grocery>) myDataBase.getGroceryDao().getAllItems();
        Comparator<Model_Grocery> comparator = new Comparator<Model_Grocery>() {
            @Override
            public int compare(Model_Grocery o1, Model_Grocery o2) {
                return o1.getId() - o2.getId();
            }
        };

        Comparator<Model_Grocery> reverseOrder = Collections.reverseOrder(comparator);
        Collections.sort(newItems, reverseOrder);
        adapterOne.setItems(newItems);
        recyclerViewNewItems.setAdapter(adapterOne);
        recyclerViewNewItems.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));

        ArrayList<Model_Grocery> popularItems = (ArrayList<Model_Grocery>) myDataBase.getGroceryDao().getAllItems();
        Comparator<Model_Grocery> comparator1 = new Comparator<Model_Grocery>() {
            @Override
            public int compare(Model_Grocery o1, Model_Grocery o2) {
                return o1.getPopularity() - o2.getPopularity();
            }
        };
        Comparator<Model_Grocery> reverseOrder1 = Collections.reverseOrder(comparator1);
        Collections.sort(popularItems, reverseOrder1);
        adapterTwo.setItems(popularItems);
        recyclerViewPopular.setAdapter(adapterTwo);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        ArrayList<Model_Grocery> suggestedItems = (ArrayList<Model_Grocery>) myDataBase.getGroceryDao().getAllItems();
        Comparator<Model_Grocery> comparator2 = new Comparator<Model_Grocery>() {
            @Override
            public int compare(Model_Grocery o1, Model_Grocery o2) {
                return o1.getUserPoints() - o2.getUserPoints();
            }
        };
        Comparator<Model_Grocery> reverseOrder2 = Collections.reverseOrder(comparator2);
        Collections.sort(suggestedItems, reverseOrder2);
        adapterThird.setItems(suggestedItems);
        recyclerViewSuggested.setAdapter(adapterThird);
        recyclerViewSuggested.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

    }

    @Override
    public void onStart() {
        super.onStart();
        dealRecyclerViews();
    }
}
