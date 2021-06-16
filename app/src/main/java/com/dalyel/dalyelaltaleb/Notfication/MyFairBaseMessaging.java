package com.dalyel.dalyelaltaleb.Notfication;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;


public class MyFairBaseMessaging extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }
}
