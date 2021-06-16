package com.dalyel.dalyelaltaleb.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dalyel.dalyelaltaleb.Adabter.QustionsAdabter;
import com.dalyel.dalyelaltaleb.Model.Qustion;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.bataBase.DBHelper;

import java.util.ArrayList;


public class QustionsFragment extends Fragment {
RecyclerView recyclerView;
DBHelper dbHelper;
ArrayList<Qustion> qustionArrayList;
QustionsAdabter qustionsAdabter;
    public QustionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView_qustions);
        dbHelper = new DBHelper(getContext());
        qustionArrayList = new ArrayList<>();
        /*
        Qustion q1=new Qustion("الدخول الى صفحة الطالب",R.drawable.regstarion_mada);
        qustionArrayList.add(q1);
        Qustion q2=new Qustion("تسجيل مساق",R.drawable.tahwel);
        qustionArrayList.add(q2);
        Qustion q3=new Qustion("تحويل التخصص",R.drawable.tajel);
        qustionArrayList.add(q3);
        Qustion q4=new Qustion("تأجيل الفصل",R.drawable.kta);
        qustionArrayList.add(q4);
        Qustion q5=new Qustion("الخطة الدراسية",R.drawable.almat);
        qustionArrayList.add(q5);
        Qustion q6=new Qustion("علامات المساقات",R.drawable.jdwal);
        qustionArrayList.add(q6);
        */

        recyclerView.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
  //      Cursor cursor = dbHelper.getData("SELECT * FROM " + DBContract.Question.TABLE_NAME);
        qustionsAdabter = new QustionsAdabter(getContext(), qustionArrayList);
        recyclerView.setAdapter(qustionsAdabter);
/*
         if(cursor!=null  && cursor.getCount() >= 0){

             cursor.moveToFirst();
                 do {
                     int id = cursor.getInt(0);
                     String qustion = cursor.getString(1);
                     byte[] image = cursor.getBlob(2);
                     qustionArrayList.add(new Qustion(id, qustion, image));

                 } while (cursor.moveToNext());


        }*/
}
    private LinearLayoutManager getLinearManger(int orientation) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(orientation);
        return llm;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_qustions, container, false);
    }
}