package com.dalyel.dalyelaltaleb.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dalyel.dalyelaltaleb.Fragment.Login;
import com.dalyel.dalyelaltaleb.R;

public class RegstrationAtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regstration_ativity);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_regstration,new Login()).setCustomAnimations(R.anim.fromleft,R.anim.fromright)
                .commit();
    }
}