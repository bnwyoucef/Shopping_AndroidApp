package com.example.deliveryshopping;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GroceryConverter {


    Gson gson = new Gson();
    @TypeConverter
    public String arrayToJson(ArrayList<Review> list){
        return gson.toJson(list);
    }

    @TypeConverter
    public ArrayList<Review> GsonToArray(String data){
        Type type = new TypeToken<ArrayList<Review>>() {}.getType();
        return gson.fromJson(data, type);
    }
}
