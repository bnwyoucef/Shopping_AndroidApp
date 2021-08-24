package com.example.deliveryshopping;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderEndPoint {

    @POST("posts")
    Call<Order> postOrder(@Body Order order);
}
