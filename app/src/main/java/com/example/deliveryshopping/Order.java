package com.example.deliveryshopping;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Order implements Parcelable {

    private String address;
    private String zipCode;
    private String phoneNumber;
    private String emailAddress;
    private ArrayList<Model_Grocery> itemsCart;
    private String paymentMethod;
    private boolean isSucceed;

    public Order(String address, String zipCode, String phoneNumber, String emailAddress) {
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }


    protected Order(Parcel in) {
        address = in.readString();
        zipCode = in.readString();
        phoneNumber = in.readString();
        emailAddress = in.readString();
        paymentMethod = in.readString();
        isSucceed = in.readByte() != 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ArrayList<Model_Grocery> getItemsCart() {
        return itemsCart;
    }

    public void setItemsCart(ArrayList<Model_Grocery> itemsCart) {
        this.itemsCart = itemsCart;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(boolean isSucceed) {
        this.isSucceed = isSucceed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(address);
        dest.writeString(zipCode);
        dest.writeString(phoneNumber);
        dest.writeString(emailAddress);
        dest.writeString(paymentMethod);
        dest.writeByte((byte) (isSucceed ? 1 : 0));
    }

}
