package com.dalyel.dalyelaltaleb.View;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Adabter.DoctorAdapter;
import com.dalyel.dalyelaltaleb.Model.Doctor;
import com.dalyel.dalyelaltaleb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {
ArrayList arrayListDoctor;
RecyclerView recyclerViewDoctor;
EditText searchView;
    ProgressBar progressBar;
    DoctorAdapter doctorAdapter;
    @VisibleForTesting
    public static final String ROW_TEXT = "ROW_TEXT";

    @VisibleForTesting
    public static final String ROW_ENABLED = "ROW_ENABLED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        arrayListDoctor=new ArrayList();
        recyclerViewDoctor=findViewById(R.id.recyclerView_doctors);
        searchView =findViewById(R.id.searchView);
        progressBar=findViewById(R.id.progressBar);

        createRecyclerViewDoctor();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doctorAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()<=0){
                    doctorAdapter = new DoctorAdapter(arrayListDoctor,getApplicationContext(),getSupportFragmentManager(), DoctorActivity.this);
                    recyclerViewDoctor.setAdapter(doctorAdapter);

                    }
            }
        });
    }

    private void createRecyclerViewDoctor() {
        progressBar.setVisibility(View.VISIBLE);
  FirebaseDatabase.getInstance()
                .getReference("doctor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                            Doctor doctor=dataSnapshot1.getValue(Doctor.class);

                            arrayListDoctor.add(doctor);

                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerViewDoctor.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
                        doctorAdapter = new DoctorAdapter(arrayListDoctor,getApplicationContext(),getSupportFragmentManager(), DoctorActivity.this);
                        recyclerViewDoctor.setAdapter(doctorAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
    private LinearLayoutManager getLinearManger(int orientation) {
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        llm.setOrientation(orientation);
        return llm;
    }
}