package com.dalyel.dalyelaltaleb;

import android.content.SharedPreferences;

import com.dalyel.dalyelaltaleb.Model.Estfsar;
import com.dalyel.dalyelaltaleb.Model.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.concurrent.CountDownLatch;

public class test {

    private CountDownLatch authSignal = new CountDownLatch(10);

    @Mock
    private SharedPreferences sharedPreferences;
    FirebaseMocker firebaseMocker;


@Before
public  void Before(){
     firebaseMocker=new FirebaseMocker();
}
    @Test
    public void getEstfsar(){
        firebaseMocker.getFirebase().child("estfsarat")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            Assert.assertEquals("عرض الاخبار",dataSnapshot.getValue().toString());
                            authSignal.countDown();
                }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        throw new UnsupportedOperationException("Not implemented");
                    }
                });
    }
    @Test
    public void getuser(){
       FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("users")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Assert.assertEquals("عرض الاخبار",dataSnapshot.getValue().toString());
                        authSignal.countDown();
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        throw new UnsupportedOperationException("Not implemented");
                    }
                });
    }
    @Test
    public void getDoctors(){
        FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("doctor")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Assert.assertEquals("عرض الدكاترة",dataSnapshot.getValue().toString());
                        authSignal.countDown();
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        throw new UnsupportedOperationException("Not implemented");
                    }
                });
    }

    @Test
    public void putEstfsar(){
        Estfsar estfsar=new Estfsar();
        estfsar.setPost("Hallo!");
        estfsar.setTime(1555858855);
        estfsar.setPostKey("post_kew");
        estfsar.setFromName("gamal");
        estfsar.setFromEmail("gamal@gmail.com");
        FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("estfsarat").setValue(estfsar);
    }

    @Test
    public void putUser(){
        User user=new User();
        user.setPassword("**********");
        user.setCollege("IT");
        user.setName("gamal");
        user.setGmail("gamal@gmail.com");
        FirebaseMocker firebaseMocker=new FirebaseMocker();
        firebaseMocker.getFirebase().child("User").setValue(user);

    }
}
