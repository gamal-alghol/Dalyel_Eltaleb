package com.dalyel.dalyelaltaleb.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalyel.dalyelaltaleb.Adabter.EstfsaratAdabter;
import com.dalyel.dalyelaltaleb.Model.Estfsar;
import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.View.PublishActivity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class EstfsarFragment extends Fragment {
    TextView publish, nameTV;
    CircleImageView imageUser;
    static String name;
    ArrayList<Estfsar> estfsarArrayList;
    ShimmerFrameLayout shimmer1;
    RecyclerView recyclerView;
    EstfsaratAdabter estfsaratAdabter;
       DatabaseReference databaseReference;
    boolean hasEstfsar;
    ChildEventListener valueEventListener;
    public EstfsarFragment() {

        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        publish = view.findViewById(R.id.publish);
        nameTV = view.findViewById(R.id.name);
        imageUser = view.findViewById(R.id.imageView8);
        shimmer1 = view.findViewById(R.id.shimmer_view_container);
        recyclerView = view.findViewById(R.id.recyclerView);
        estfsarArrayList = new ArrayList<>();


        getImageAndNameUser();
        recyclerView.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
        estfsaratAdabter = new EstfsaratAdabter(estfsarArrayList, getContext(), getFragmentManager(), false);
        recyclerView.setAdapter(estfsaratAdabter);
        goToPublishActivity();

    }

    private void getImageAndNameUser() {
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        name=user.getName();
                        byte [] image=Base64.decode(user.getImage(), Base64.URL_SAFE);
try {
    Glide.with(getActivity())
            .asBitmap()
            .load(image)
            .placeholder(getResources().getDrawable(R.drawable.dalel))
            .into(imageUser);
}catch (NullPointerException e){

}

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }


    private LinearLayoutManager getLinearManger(int orientation) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(orientation);
        return llm;
    }

    @Override
    public void onResume() {
        super.onResume();
        estfsarArrayList.clear();
        getEstfsarat();
    }

    private void getEstfsarat() {
        shimmer1.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);

      databaseReference  =FirebaseDatabase.getInstance().getReference("estfsarat");

                valueEventListener=databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Estfsar estfsar = dataSnapshot.getValue(Estfsar.class);
                        estfsarArrayList.add(estfsar);
                        estfsaratAdabter.notifyDataSetChanged();
                        shimmer1.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

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
                        Log.d("ttt", "onCancelled");
                    }

                });
     /*   if (hasEstfsar == false) {
            shimmer1.setVisibility(View.GONE);
            publish.setVisibility(View.VISIBLE);
            imageUser.setVisibility(View.VISIBLE);
        }*/
    }

    @Override
    public void onPause() {
        super.onPause();
databaseReference.removeEventListener(valueEventListener);
    }
/*
    private void getName() {

        FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if ( task.isSuccessful()){
                  User user=task.getResult().toObject(User.class);
                    name=user.getName();

                  nameTV.setText(name);
                }
            }
        });

    }*/

    private void goToPublishActivity() {
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PublishActivity.class).putExtra("name", name));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estfsar, container, false);
    }
}