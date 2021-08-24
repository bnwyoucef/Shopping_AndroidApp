package com.example.deliveryshopping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message extends DialogFragment {
    public static final String MESSAGE_BUNDLE = "message";
    private TextView name, warning;
    private EditText userName, textReview;
    private Button button;
    private sendMessageWork messageWork;
    public interface sendMessageWork{
        void getAddedReview(Review review);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.message_add_review, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        initViews(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String itemName = bundle.getString(MESSAGE_BUNDLE);
            if (itemName != null) {
                name.setText(itemName);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userName.getText().toString().equals("") || textReview.getText().toString().equals("")){
                            warning.setVisibility(View.VISIBLE);
                        }else {
                            warning.setVisibility(View.GONE);
                            Review review = new Review(userName.getText().toString(), textReview.getText().toString(), date());
                            try {
                                messageWork = (sendMessageWork) getActivity();
                                messageWork.getAddedReview(review);
                                dismiss();
                            }catch (ClassCastException exception){
                                exception.printStackTrace();
                                dismiss();
                            }
                        }
                    }
                });
            }
        }

        return builder.create();
    }

    private String date() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(calendar.getTime());
    }

    private void initViews(View view) {
        name = view.findViewById(R.id.messageName);
        warning = view.findViewById(R.id.messageWarning);
        userName = view.findViewById(R.id.messageUserName);
        textReview = view.findViewById(R.id.messageReview);
        button = view.findViewById(R.id.messageButton);

    }
}
