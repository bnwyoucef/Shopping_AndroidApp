package com.example.deliveryshopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class Licences extends DialogFragment {
    private TextView textView;
    private Button  button;
    public static final String LICENCE_TEXT = "txt";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.licence, null);
        textView = view.findViewById(R.id.textLicence);
        button = view.findViewById(R.id.dismissBtn);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String licence = bundle.getString(LICENCE_TEXT);
            if (licence != null) {
                textView.setText(licence);
            }
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }
}
