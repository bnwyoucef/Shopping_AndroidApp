package com.example.deliveryshopping;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FirstCartFragment extends Fragment implements cartAdapter.cartCallBack{
    private RecyclerView recyclerView;
    private cartAdapter adapter;
    private TextView totalPrice, emptyCartTxt, title;
    private Button nextBtn;
    private GroceryDao dataBase;
    private ArrayList<Model_Grocery> list;
    private NestedScrollView nestedScrollView;
    private double totalPriceItems;
    private static final String TAG = "FirstCartFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.cart_fragment_one, null);
        initView(view);
        list = (ArrayList<Model_Grocery>) dataBase.addedToCart();
        adapter.setItems(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (list.size() > 0){
            nestedScrollView.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
            emptyCartTxt.setVisibility(View.GONE);
            totalPrice.setText(getTotalPrice(list));
            title.setVisibility(View.VISIBLE);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, new SecondCartFragment());
                    transaction.commit();
                }
            });
        }else {
            nestedScrollView.setVisibility(View.GONE);
            nextBtn.setVisibility(View.GONE);
            emptyCartTxt.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
        }

        return view;
    }

    private void initView(View view){
        dataBase = MyDataBase.getInstance(getActivity()).getGroceryDao();
        recyclerView = view.findViewById(R.id.FirstCartRecyclerView);
        totalPrice = view.findViewById(R.id.totalPrice);
        emptyCartTxt = view.findViewById(R.id.emptyCartTxt);
        title = view.findViewById(R.id.your);
        nextBtn = view.findViewById(R.id.nextButton);
        adapter = new cartAdapter(getActivity(), this);
        nestedScrollView = view.findViewById(R.id.nestedScroll);
    }

    protected String getTotalPrice(ArrayList<Model_Grocery> items) {
        for (Model_Grocery m : items) {
            totalPriceItems += m.getPrice();
        }
        return String.valueOf(totalPriceItems) + "$";
    }

    @Override
    public void deleteItem(int id) {
        Model_Grocery item = dataBase.getGrocery(id);
        item.setAdded(false);
        dataBase.updateItem(item);
        list = (ArrayList<Model_Grocery>) dataBase.addedToCart();
        totalPriceItems -= item.getPrice();
        totalPrice.setText(String.valueOf(totalPriceItems));
        adapter.setItems(list);
        if (list.size() == 0){
            nestedScrollView.setVisibility(View.GONE);
            nextBtn.setVisibility(View.GONE);
            emptyCartTxt.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
        }
    }
}
