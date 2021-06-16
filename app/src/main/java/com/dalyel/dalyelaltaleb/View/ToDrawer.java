package com.dalyel.dalyelaltaleb.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dalyel.dalyelaltaleb.Fragment.ContactUs;
import com.dalyel.dalyelaltaleb.Fragment.MyQusetion;
import com.dalyel.dalyelaltaleb.Fragment.WhoUs;
import com.dalyel.dalyelaltaleb.R;

public class ToDrawer extends AppCompatActivity {
    private Toolbar toolbar;
    private    TextView tvToolBarTitle;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_drawer);
        toolbar = findViewById(R.id.toolbar_condition);
        tvToolBarTitle = findViewById(R.id.textView5);
back=findViewById(R.id.back);
        setSupportActionBar(toolbar);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        switch (getIntent()
                .getStringExtra("fragment")){
            case "myQusetion" :
                tvToolBarTitle.setText("استفساراتي");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_drawer, new MyQusetion())
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .commit();
                break;
            case "contactUsFragment" :
                tvToolBarTitle.setText("المشاركة والاعلانات");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_drawer, new ContactUs())
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .commit();
                break;
            case "whoUsFragment" :
                tvToolBarTitle.setText("من نحن");

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_drawer, new WhoUs())
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .commit();
                break;
            default:
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}