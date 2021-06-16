package com.dalyel.dalyelaltaleb.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalyel.dalyelaltaleb.Adabter.EstfsaratAdabter;
import com.dalyel.dalyelaltaleb.Model.Estfsar;
import com.dalyel.dalyelaltaleb.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MyQusetion extends Fragment {


    ArrayList<Estfsar> estfsarArrayList;
    ShimmerFrameLayout shimmer1 ;
    RecyclerView recyclerView;
    EstfsaratAdabter estfsaratAdabter;
    boolean hasEstfsar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shimmer1=view.findViewById(R.id.shimmer_view_container);
        recyclerView=view.findViewById(R.id.recyclerView);
        estfsarArrayList=new ArrayList<>();
        getEstfsarat();
        recyclerView.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
        estfsaratAdabter = new EstfsaratAdabter(estfsarArrayList,getContext(),getFragmentManager(),true);
        recyclerView.setAdapter(estfsaratAdabter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    private void getEstfsarat() {


            FirebaseDatabase.getInstance().getReference("estfsarat")
                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                            Estfsar estfsar=dataSnapshot.getValue(Estfsar.class);
                            if(estfsar.getFromEmail().equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                                estfsarArrayList.add(estfsar);
                                estfsaratAdabter.notifyDataSetChanged();
                                shimmer1.setVisibility(View.GONE);
                            }



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

                        }
                    });

        if(hasEstfsar==false){
            shimmer1.setVisibility(View.GONE);
        }
    }
    private LinearLayoutManager getLinearManger(int orientation) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(orientation);
        return llm;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_qusetion, container, false);
    }
}