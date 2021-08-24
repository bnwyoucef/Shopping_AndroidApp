package com.example.deliveryshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CartActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_activtty);
        initViews();
        setSupportActionBar(toolbar);
        bottomNavigationView.setSelectedItemId(R.id.cartBottom);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeBottom:
                        Intent intent = new Intent(CartActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.searchBottom:
                        Intent intent1 = new Intent(CartActivity.this, SearchActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new FirstCartFragment());
        transaction.commit();
    }

    private void initViews(){
        toolbar = findViewById(R.id.cartToolBar);
        bottomNavigationView = findViewById(R.id.cartbottomNavBar);
    }
}