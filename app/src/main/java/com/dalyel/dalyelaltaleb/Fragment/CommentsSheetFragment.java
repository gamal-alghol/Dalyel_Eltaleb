package com.dalyel.dalyelaltaleb.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Adabter.CommentsSheetAdapter;
import com.dalyel.dalyelaltaleb.Helper;
import com.dalyel.dalyelaltaleb.Model.Comment;
import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;

import com.dalyel.dalyelaltaleb.viewModel.CommentsInfoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class CommentsSheetFragment extends BottomSheetDialogFragment {
    View view;
    RecyclerView commentsRecyclerView;
    EditText commentsEditText;

    ImageView sendCommentImageView;
    String postID;
    User user;
    ShimmerFrameLayout shimmer1;
    DatabaseReference reference;
    com.dalyel.dalyelaltaleb.Adabter.CommentsSheetAdapter CommentsSheetAdapter;
    LinearLayoutManager layoutManager;
    ArrayList<Comment> commentsModelArrayList;
    CommentsInfoViewModel commentsInfoViewModel;
    ArrayList<Comment> comments;


    public CommentsSheetFragment(String postID) {
        this.postID = postID;

    }
    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.comments_sheet, null, false);
        view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        shimmer1 = view.findViewById(R.id.shimmer_view_container);
        commentsRecyclerView = view.findViewById(R.id.comments);
        commentsEditText = view.findViewById(R.id.edit_comment);
        sendCommentImageView = view.findViewById(R.id.send_comment_img);
        layoutManager = new LinearLayoutManager(getContext());
        commentsRecyclerView.setLayoutManager(layoutManager);

        commentsInfoViewModel = ViewModelProviders.of(getActivity()).get(CommentsInfoViewModel.class);
     /*   commentsInfoViewModel.getAllComments(postID).observe(getActivity(), new Observer<ArrayList<Comment>>() {
            @Override
            public void onChanged(ArrayList<Comment> comments) {

                CommentsSheetAdapter = new CommentsSheetAdapter(comments, getContext(), getFragmentManager());
                commentsRecyclerView.setAdapter(CommentsSheetAdapter);
                CommentsSheetAdapter.notifyDataSetChanged();
                shimmer1.setVisibility(View.GONE);

            }
        });*/


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sendCommentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment2(); }});
        comments = new ArrayList<>();
        getComments();
        CommentsSheetAdapter = new CommentsSheetAdapter(comments, getContext(), getFragmentManager());
        commentsRecyclerView.setAdapter(CommentsSheetAdapter);
        CommentsSheetAdapter.notifyDataSetChanged();
        if (comments.size()==0){
            shimmer1.setVisibility(View.INVISIBLE);
            commentsRecyclerView.setVisibility(View.VISIBLE);
        }


    }

    void  getComments(){
        shimmer1.setVisibility(View.VISIBLE);
        commentsRecyclerView.setVisibility(View.INVISIBLE);
        FirebaseDatabase.getInstance().getReference("estfsarat").child(postID).child("numComments")
                .orderByChild("commentTimeInMillis").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                comments.add(comment);
                CommentsSheetAdapter.notifyDataSetChanged();
                shimmer1.setVisibility(View.INVISIBLE);
                commentsRecyclerView.setVisibility(View.VISIBLE);            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    private void sendComment2() {

        if (!commentsEditText.getText().toString().trim().equals("") && commentsEditText.getText() != null) {
            Comment commentsModel = new Comment();
            commentsModel.setComment(commentsEditText.getText().toString());
            commentsModel.setCommentedByUserKey(EstfsarFragment.name);
            commentsModel.setCommentTimeInMillis(Helper.getCurrentTimeMilliSecond());
            commentsModel.setFromEmail(FirebaseAuth.getInstance().getCurrentUser().getUid());
            commentsInfoViewModel.sendComment(postID, commentsModel).observe(getActivity(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    commentsEditText.setText("");
                }
            });


        }


    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        View rootView = getActivity().getLayoutInflater().inflate(R.layout.comments_sheet, null, false);
        dialog.setContentView(rootView);
    }

}
/*
    private void getAllComments() {
        commentsModelArrayList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("estfsarat");
        reference.child(postID).child("numComments").orderByChild("commentTimeInMillis").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot document : dataSnapshot.getChildren()) {
                            Comment comment = document.getValue(Comment.class);
                            commentsModelArrayList.add(comment);
                        }
                        loadCommentsBar.setVisibility(View.GONE);
                        CommentsSheetAdapter = new CommentsSheetAdapter(commentsModelArrayList, getContext(), getFragmentManager());
                        commentsRecyclerView.setAdapter(CommentsSheetAdapter);
                        CommentsSheetAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


    }
*/
/*
    private void sendComment() {

        if (!commentsEditText.getText().toString().trim().equals("") && commentsEditText.getText() != null) {
            Comment commentsModel = new Comment();
            commentsModel.setComment(commentsEditText.getText().toString());
            commentsModel.setCommentedByUserKey(EstfsarFragment.name);
            commentsModel.setCommentTimeInMillis(Helper.getCurrentTimeMilliSecond());
            reference = FirebaseDatabase.getInstance().getReference("estfsarat");
            reference.child(postID).child("numComments").push().setValue(commentsModel).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //      commentsModelArrayList.clear();
                                getAllComments();
                                commentsEditText.setText("");
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "فشل الارسال", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }*/