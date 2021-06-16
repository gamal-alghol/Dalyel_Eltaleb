package com.dalyel.dalyelaltaleb.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dalyel.dalyelaltaleb.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfoViewModel  extends AndroidViewModel {
    private MutableLiveData<User> userMutableLiveData;
    public MutableLiveData<Boolean> addUserMutableData;



    public UserInfoViewModel(@NonNull Application application) {
        super(application);
        userMutableLiveData =new MutableLiveData<>();
        addUserMutableData =new MutableLiveData<>();

    }
    public MutableLiveData<User> getUserInfo(){
        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              userMutableLiveData.setValue(task.getResult().toObject(User.class));
            }
        });
          return userMutableLiveData;
    }
    public void updateUser( User user){

        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                 Log.d("ttt","success updated user");
            }
        });
    }

    public  MutableLiveData<Boolean> addUser(User user){
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    addUserMutableData.setValue(true);
                    Log.d("ttt","success");
                }}
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt",e.getMessage());
            }
        });
        return  addUserMutableData;
    }


}