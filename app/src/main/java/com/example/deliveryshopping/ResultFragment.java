package com.example.deliveryshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {
    private TextView resultText;
    private Button startShoppingBtn;
    public static final String TO_RESULT = "result";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.result_fragment, null);
        resultText = view.findViewById(R.id.resultText);
        startShoppingBtn = view.findViewById(R.id.startShoppingBtn);

        Bundle bundle = getArguments();
        if (bundle != null) {
            Order order = bundle.getParcelable(TO_RESULT);
            if (order != null) {
                if (order.getIsSucceed()) {
                    resultText.setText("Payment was Successful\nYour Order will arrive in 3 days");
                }else {
                    resultText.setText("Payment failed\nPlease try another method");
                }
            }else {
                resultText.setText("Payment failed\nPlease try another method");
            }

        }else {
            resultText.setText("Payment failed\nPlease try another method");
        }
        startShoppingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
