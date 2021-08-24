package com.example.deliveryshopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.deliveryshopping.ResultFragment.TO_RESULT;

public class ThirdCartFragment extends Fragment {
    private TextView itemsTxt, totalPriceTxt, addressTxt,phoneNumberTxt;
    private RadioGroup radioGroup;
    private Button backBtn, checkOut;
    private GroceryDao dao;
    private Order order;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.cart_fragment_three, null);
        initViews(view);
        ArrayList<Model_Grocery> listItems = (ArrayList<Model_Grocery>) dao.addedToCart();
        String string = "";
        for (Model_Grocery m : listItems){
            string += m.getName() + "\n";
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            order = bundle.getParcelable("addressPhone");
            if (order != null) {
                addressTxt.setText(order.getAddress());
                phoneNumberTxt.setText(order.getPhoneNumber());
                itemsTxt.setText(string);
                totalPriceTxt.setText(new FirstCartFragment().getTotalPrice(listItems));
            }

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelable("thirdToTwo", order);
                    SecondCartFragment secondCartFragment = new SecondCartFragment();
                    secondCartFragment.setArguments(bundle1);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.container, secondCartFragment);
                    transaction.commit();
                }
            });
        }

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOutOrder();
            }
        });


        return view;
    }

    private void checkOutOrder() {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.visaCard:
                order.setPaymentMethod("VisaCard");
                break;
            case R.id.paypal:
                order.setPaymentMethod("PayPal");
                break;
            default:
                break;
        }

        order.setIsSucceed(true);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OrderEndPoint endPoint = retrofit.create(OrderEndPoint.class);
        Call<Order> call = endPoint.postOrder(order);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if (response.isSuccessful()) {
                    Order order1 = response.body();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(TO_RESULT, order1);
                    ResultFragment resultFragment = new ResultFragment();
                    resultFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, resultFragment);
                    fragmentTransaction.commit();
                }else {
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.container, new ResultFragment());
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {

            }
        });
    }

    private void initViews(View view) {
        itemsTxt = view.findViewById(R.id.thirdCartItems);
        totalPriceTxt = view.findViewById(R.id.thirdTotalPrice);
        addressTxt = view.findViewById(R.id.thirdAddress);
        phoneNumberTxt = view.findViewById(R.id.thirdPhone);
        radioGroup = view.findViewById(R.id.radioGroup);
        backBtn = view.findViewById(R.id.thirdBackBtn);
        checkOut = view.findViewById(R.id.thirdNextBtn);
        dao = MyDataBase.getInstance(getActivity()).getGroceryDao();
    }
}
