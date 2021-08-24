package com.example.deliveryshopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class SecondCartFragment extends Fragment {
    private EditText address, zipCode, phoneNumber, emailAddress;
    private Button nextBtn, backBtn;
    private TextView warningText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.cart_fragment_two, null);
        initViews(view);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new FirstCartFragment());
                transaction.commit();
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nextButtonControl();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            Order order1 = bundle.getParcelable("thirdToTwo");
            address.setText(order1.getAddress());
            zipCode.setText(order1.getZipCode());
            phoneNumber.setText(order1.getPhoneNumber());
            emailAddress.setText(order1.getEmailAddress());
        }

        return view;
    }

    private void initViews(View view) {
        address = view.findViewById(R.id.addressEdit);
        zipCode = view.findViewById(R.id.zipCodeEdit);
        phoneNumber = view.findViewById(R.id.phoneEdit);
        emailAddress = view.findViewById(R.id.emailEdit);
        nextBtn = view.findViewById(R.id.nextButton);
        backBtn = view.findViewById(R.id.backButton);
        warningText = view.findViewById(R.id.warningFragment);
    }

    private void nextButtonControl() {
        if (address.getText().toString().equals("") || zipCode.getText().toString().equals("")
                || phoneNumber.getText().toString().equals("") || emailAddress.getText().toString().equals("")) {
            warningText.setVisibility(View.VISIBLE);
        } else {
            warningText.setVisibility(View.GONE);
            Bundle bundle = new Bundle();
            Order order = new Order(address.getText().toString(), zipCode.getText().toString(), phoneNumber.getText().toString()
                               , emailAddress.getText().toString());
            bundle.putParcelable("addressPhone", order);
            ThirdCartFragment cartFragment = new ThirdCartFragment();
            cartFragment.setArguments(bundle);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, cartFragment);
            transaction.commit();
        }
    }
}
