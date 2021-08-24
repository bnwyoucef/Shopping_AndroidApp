package com.example.deliveryshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import static com.example.deliveryshopping.Message.MESSAGE_BUNDLE;

public class DetailsActivity extends AppCompatActivity implements Message.sendMessageWork {

    public static final String GET_MODEL = "model";
    private Button addButton;
    private TextView name, price, addReview;
    private RecyclerView recyclerView;
    private ImageView imageItem, firstNoRate, secondNoRate, thirdNoRate, firstRate, secondRate, thirdRate;
    private ReviewAdapter adapter;
    private MyDataBase dataBase;
    private RelativeLayout firstStar, secondStar, thirdStart;
    private Model_Grocery incomingItem;
    private static final String TAG = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initViews();
        Intent intent = getIntent();
        if (intent != null) {
            int itemId = intent.getIntExtra(GET_MODEL, -1);
            if (itemId != -1) {
                incomingItem = dataBase.getGroceryDao().getGrocery(itemId);
                incomingItem.setUserPoints(incomingItem.getUserPoints() + 1);
                dataBase.getGroceryDao().updateItem(incomingItem);
                name.setText(incomingItem.getName());
                price.setText(String.valueOf(incomingItem.getPrice()));
                addReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString(MESSAGE_BUNDLE, incomingItem.getName());
                        message.setArguments(bundle);
                        message.show(getSupportFragmentManager(), "from details");
                    }
                });
                ArrayList<Review> reviews = incomingItem.getListReviews();
                adapter.setReviewsList(reviews);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(imageItem);

                handleRating();
            }
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomingItem.setAdded(true);
                incomingItem.setUserPoints(incomingItem.getUserPoints() + 4);
                dataBase.getGroceryDao().updateItem(incomingItem);
                Intent intent1 = new Intent(DetailsActivity.this, CartActivity.class);
                startActivity(intent1);
            }
        });


    }

    private void handleRating() {
        switch (incomingItem.getRate()) {
            case 1:
                firstRate.setVisibility(View.VISIBLE);
                firstNoRate.setVisibility(View.GONE);
                secondRate.setVisibility(View.GONE);
                secondNoRate.setVisibility(View.VISIBLE);
                thirdRate.setVisibility(View.GONE);
                thirdNoRate.setVisibility(View.VISIBLE);
                break;
            case 2:
                firstRate.setVisibility(View.VISIBLE);
                firstNoRate.setVisibility(View.GONE);
                secondRate.setVisibility(View.VISIBLE);
                secondNoRate.setVisibility(View.GONE);
                thirdRate.setVisibility(View.GONE);
                thirdNoRate.setVisibility(View.VISIBLE);
                break;
            case 3:
                firstRate.setVisibility(View.VISIBLE);
                firstNoRate.setVisibility(View.GONE);
                secondRate.setVisibility(View.VISIBLE);
                secondNoRate.setVisibility(View.GONE);
                thirdRate.setVisibility(View.VISIBLE);
                thirdNoRate.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        firstStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 1) {
                    incomingItem.setUserPoints(incomingItem.getUserPoints() + (1 - incomingItem.getRate()) * 2);
                    incomingItem.setRate(1);
                    dataBase.getGroceryDao().updateItem(incomingItem);
                    handleRating();
                }

            }
        });
        secondStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 2) {
                    incomingItem.setRate(2);
                    incomingItem.setUserPoints(incomingItem.getUserPoints() + (2 - incomingItem.getRate()) * 2);
                    dataBase.getGroceryDao().updateItem(incomingItem);
                    handleRating();
                }
            }
        });
        thirdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 3) {
                    incomingItem.setUserPoints(incomingItem.getUserPoints() + (3 - incomingItem.getRate()) * 2);
                    incomingItem.setRate(3);
                    dataBase.getGroceryDao().updateItem(incomingItem);
                    handleRating();
                }
            }
        });
    }

    private void initViews() {
        dataBase = MyDataBase.getInstance(this);
        addButton = findViewById(R.id.addTocartButton);
        name = findViewById(R.id.detailsName);
        price = findViewById(R.id.detailsPrice);
        addReview = findViewById(R.id.addReviewTxt);
        recyclerView = findViewById(R.id.reviewsRecycler);
        imageItem = findViewById(R.id.detailsImage);
        firstNoRate = findViewById(R.id.rateNoFirst);
        secondNoRate = findViewById(R.id.rateNoSecond);
        thirdNoRate = findViewById(R.id.rateNoThird);
        firstRate = findViewById(R.id.rateFirst);
        secondRate = findViewById(R.id.rateSecond);
        thirdRate = findViewById(R.id.rateThird);
        adapter = new ReviewAdapter(this);
        firstStar = findViewById(R.id.firstStar);
        secondStar = findViewById(R.id.secondStar);
        thirdStart = findViewById(R.id.thirdStar);
    }

    @Override
    public void getAddedReview(Review review) {
        incomingItem.getListReviews().add(review);
        incomingItem.setUserPoints(incomingItem.getUserPoints() + 3);
        dataBase.getGroceryDao().updateItem(incomingItem);
        adapter.setReviewsList(incomingItem.getListReviews());
    }

}