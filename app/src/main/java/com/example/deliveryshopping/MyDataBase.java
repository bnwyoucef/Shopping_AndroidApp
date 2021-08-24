package com.example.deliveryshopping;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
@Database(entities = {Model_Grocery.class}, version = 1)
public abstract class MyDataBase extends RoomDatabase {
    public abstract GroceryDao getGroceryDao();
    private static MyDataBase instance;

    public static MyDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context, MyDataBase.class,"delivery.db")
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new initialiseGroceryItems(instance).execute();
        }
    };

    static class initialiseGroceryItems extends AsyncTask<Void,Void,Void>{
        private GroceryDao groceryDao;

        public initialiseGroceryItems(MyDataBase dataBase) {
            this.groceryDao = dataBase.getGroceryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Model_Grocery soda = new Model_Grocery("Soda", 3, "A soft drink (see § Terminology for other names) is a drink that usually contains water (often carbonated), a sweetener, and a natural and/or artificial flavoring. The sweetener may be a sugar, high-fructose corn syrup, fruit juice, " +
                    "a sugar substitute (in the case of diet drinks), or some combination of these"
                    , "https://www.vhv.rs/dpng/d/506-5069744_lata-de-soda-png-coca-cola-can-svg.png", "drinks", 150);
            Model_Grocery water = new Model_Grocery("water", 1, "cold water pure life",
                    "https://www.kindpng.com/picc/m/40-409385_transparent-background-water-bottle-png-png-download.png"
                    , "drinks", 320);
            Model_Grocery milk = new Model_Grocery("milk", 2.5, "Milk is a nutrient-rich liquid food produced by the mammary glands of mammals. It is the primary source of nutrition for young mammals, including breastfed human infants before they are able to digest solid food"
                    , "https://www.almarai.com/wp-content/uploads/2017/11/42270-UHT-MILK-FF-1L-SCREWCAP-WITH-VITAMIN-EN_WEB.jpg",
                    "drinks", 20);
            Model_Grocery chese = new Model_Grocery("chese", 4.3, "best chese taste and faste delivery to cook in the moment",
                    "https://e7ut8we.cloudimg.io/s/resize/1000/https://dgduupz79pcvd." +
                            "cloudfront.net/productimages/vow_premium/au77480.jpg", "food", 34);

            Model_Grocery pizza = new Model_Grocery("pizza", 5.5, "Domino's Pizza in Bangalore. Pizza Outlets with Address, Contact Number, Photos, Maps." +
                    " View Domino's Pizza, Bangalore on Justdial.", "https://content3.jdmagicbox.com/comp/bangalore/j1/080pxx80.xx80." +
                    "111107115909.g7j1/catalogue/domino-s-pizza-bommasandra-industrial-area-bangalore-pizza-outlets-20gy50i.jpg", "food", 230);

            Model_Grocery MixedNuta = new Model_Grocery("mixed nuts", 13, "PLANTERS Mixed Nuts with Sea Salt, 56 oz. Resealable" +
                    " Canister - Roasted Nuts: Less Than 50% Peanuts, Almonds, Cashews, Pecans & Hazelnuts - Good Source of Protein, Fiber & Healthy Fats -",
                    "https://m.media-amazon.com/images/I/919oIrxjaRL._SL1500_.jpg", "Nuts", 400);

            Model_Grocery almond = new Model_Grocery("almond", 12, "Rostaa Std Pouch Classic Almonds Pouch, 200 g (Gluten Free, Non-GMO & Vegan", "https://images-na.ssl-images-amazon.com/images/I/81x5DG5rxVL._SY606_.jpg",
                    "Nuts", 2304);

            groceryDao.insertItem(milk);
            groceryDao.insertItem(pizza);
            groceryDao.insertItem(soda);
            groceryDao.insertItem(almond);
            groceryDao.insertItem(water);
            groceryDao.insertItem(chese);
            groceryDao.insertItem(MixedNuta);

            return null;
        }
    }
}
