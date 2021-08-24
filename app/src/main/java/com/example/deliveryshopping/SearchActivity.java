package com.example.deliveryshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SelectCategory.controlCategories{

    private EditText searchEdit;
    private RecyclerView recyclerView;
    private Main_Adapter adapter;
    private TextView drinksCategory, foodCategory, nutsCategory, seeAllCategories;
    private GroceryDao dataBase;
    private ArrayList<Model_Grocery> items;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        drinksCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResult("drinks");
                drinksCategory.setTextColor(Color.rgb(255, 52, 255));
                foodCategory.setTextColor(Color.rgb(76, 185, 80));
                nutsCategory.setTextColor(Color.rgb(76, 185, 80));
            }
        });
        foodCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               searchResult("food");
               foodCategory.setTextColor(Color.rgb(255, 52, 255));
               drinksCategory.setTextColor(Color.rgb(76, 185, 80));
                nutsCategory.setTextColor(Color.rgb(76, 185, 80));
            }
        });
        nutsCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResult("Nuts");
                nutsCategory.setTextColor(Color.rgb(255, 52, 255));
                foodCategory.setTextColor(Color.rgb(76, 185, 80));
                drinksCategory.setTextColor(Color.rgb(76, 185, 80));
            }
        });
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchChange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        seeAllCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("categories", allCategories());
                bundle.putString("type", "searchActivity");
                SelectCategory selectCategory = new SelectCategory();
                selectCategory.setArguments(bundle);
                selectCategory.show(getSupportFragmentManager(), "from search");
            }
        });
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeBottom:
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.searchBottom:
                        break;
                    case R.id.cartBottom:
                        Intent intent1 = new Intent(SearchActivity.this, CartActivity.class);
                        startActivity(intent1);
                        break;
                }
                return false;
            }
        });
        Intent intent = getIntent();
        if (intent != null){
            String type = intent.getStringExtra("fromNav");
            searchResult(type);
        }
    }

    private void searchChange() {
        if (!searchEdit.getText().toString().equals("")) {
            items = (ArrayList<Model_Grocery>) dataBase.likeSearch("%" + searchEdit.getText().toString() + "%");
            for (Model_Grocery m : items) {
                m.setUserPoints(m.getUserPoints() + 2);
                dataBase.updateItem(m);
            }
            adapter.setItems(items);
        }else {
            items.clear();
            adapter.setItems(items);
        }
    }

    private void initViews() {
        items = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.searchNavBottom);
        searchEdit = findViewById(R.id.searchEditTxt);
        recyclerView = findViewById(R.id.searchRecyclerView);
        adapter = new Main_Adapter(this);
        drinksCategory = findViewById(R.id.drinksCategoryTxt);
        foodCategory = findViewById(R.id.foodCategoryTxt);
        nutsCategory = findViewById(R.id.nutsCategoryTxt);
        seeAllCategories = findViewById(R.id.seeAllCategoriesTxt);
        dataBase = MyDataBase.getInstance(this).getGroceryDao();
    }
    private void searchResult(String category){
        items = (ArrayList<Model_Grocery>) dataBase.itemsByCategory(category);
        for (Model_Grocery m : items) {
            m.setUserPoints(m.getUserPoints() + 2);
        }
        adapter.setItems(items);
    }

    protected ArrayList<String> allCategories() {
        items =  (ArrayList<Model_Grocery>) MyDataBase.getInstance(this).getGroceryDao().getAllItems();
        ArrayList<String> categories = new ArrayList<>();
        for (Model_Grocery g : items){
            if (!categories.contains(g.getCategory())){
                categories.add(g.getCategory());
            }
        }
        return categories;
    }

    @Override
    public void categoryOne(String category) {
        searchResult(category);
    }
}