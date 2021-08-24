package com.example.deliveryshopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

public class SelectCategory extends DialogFragment {

    private ListView listView;
    private static final String TAG = "SelectCategory";
    private controlCategories controlCat;
    private SearchActivity searchActivity;

    public interface controlCategories {
        public void categoryOne(String category);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.categories_message, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        listView = view.findViewById(R.id.listView);
        searchActivity = new SearchActivity();
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> categories = bundle.getStringArrayList("categories");
            String activityType = bundle.getString("type");
            switch (activityType) {
                case "MainActivity":
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity()
                            , android.R.layout.simple_list_item_1
                            , searchActivity.allCategories());
                    listView.setAdapter(adapter1);
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            intent.putExtra("fromNav", listView.getItemAtPosition(position).toString());
                            getActivity().startActivity(intent);
                        }
                    });
                    break;
                case "searchActivity":
                    if (categories != null) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                                android.R.layout.simple_list_item_1
                                , categories);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    controlCat = (controlCategories) getActivity();
                                    controlCat.categoryOne(categories.get(position));
                                    dismiss();
                                } catch (ClassCastException ex) {
                                    ex.printStackTrace();
                                    dismiss();
                                }
                            }
                        });
                    }
                    break;
                default:
                    break;
            }


        }
        return builder.create();
    }

}
