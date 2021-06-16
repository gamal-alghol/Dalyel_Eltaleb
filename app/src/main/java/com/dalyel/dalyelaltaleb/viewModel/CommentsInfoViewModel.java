package com.dalyel.dalyelaltaleb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dalyel.dalyelaltaleb.Model.Comment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentsInfoViewModel extends AndroidViewModel {

    public MutableLiveData<ArrayList<Comment>> arrayListMutableLiveData;
    public MutableLiveData<Boolean> booleanMutableLiveData;
    ArrayList<Comment> commentsModelArrayList;
    DatabaseReference reference;


    public CommentsInfoViewModel(@NonNull Application application) {

        super(application);
        arrayListMutableLiveData = new MutableLiveData<>();
        booleanMutableLiveData = new MutableLiveData<>();
    }


    public MutableLiveData<ArrayList<Comment>>getAllComments(String postID){
        commentsModelArrayList = new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference("estfsarat");
        reference.child(postID).child("numComments").orderByChild("commentTimeInMillis").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentsModelArrayList.clear();
                for (DataSnapshot document : dataSnapshot.getChildren()) {
                    Comment comment=document.getValue(Comment.class);
                    commentsModelArrayList.add(comment);
                }

                arrayListMutableLiveData.setValue(commentsModelArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return  arrayListMutableLiveData;
    }

    public MutableLiveData<Boolean>  sendComment(String postID, Comment commentsModel){
        reference= FirebaseDatabase.getInstance().getReference("estfsarat");
        reference.child(postID).child("numComments").push().setValue(commentsModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    booleanMutableLiveData.setValue(true);

            }
        });
return  booleanMutableLiveData;

    }
}
