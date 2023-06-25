package com.example.activity.service;

public interface UserService {
    void checkOut();

    int checkoutContinuousCount();

    int checkoutCount(String date);

    void lateCheckout(String date);
}
