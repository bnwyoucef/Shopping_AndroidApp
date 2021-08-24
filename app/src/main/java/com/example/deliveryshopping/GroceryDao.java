package com.example.deliveryshopping;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GroceryDao {
    @Insert
    public void insertItem(Model_Grocery item);
    @Delete
    public void deleteItem(Model_Grocery item);
    @Update
    public void updateItem(Model_Grocery item);
    @Query("SELECT * FROM groceryTable")
    public List<Model_Grocery> getAllItems();
    @Query("SELECT * FROM groceryTable WHERE id =:itemId")
    public Model_Grocery getGrocery(int itemId);
    @Query("SELECT * FROM groceryTable WHERE category =:itemCat")
    public List<Model_Grocery> itemsByCategory(String itemCat);
    @Query("SELECT * FROM groceryTable WHERE name like :itemName")
    public List<Model_Grocery> likeSearch(String itemName);
    @Query("SELECT * FROM groceryTable WHERE isAdded = 1")
    public List<Model_Grocery> addedToCart();
}
