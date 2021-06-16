package com.dalyel.dalyelaltaleb.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dalyel.dalyelaltaleb.Fragment.ContactUs;
import com.dalyel.dalyelaltaleb.Fragment.EstfsarFragment;
import com.dalyel.dalyelaltaleb.Fragment.MajorsFragment;
import com.dalyel.dalyelaltaleb.Fragment.MyQusetion;
import com.dalyel.dalyelaltaleb.Fragment.NewsFragment;
import com.dalyel.dalyelaltaleb.Fragment.QustionsFragment;
import com.dalyel.dalyelaltaleb.Fragment.WhoUs;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.iid.FirebaseInstanceId;

import com.dalyel.dalyelaltaleb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.IOException;

public class HomeActivity extends AppCompatActivity {
    ChipNavigationBar navigationView;
    private Toolbar toolbar;
    public static long selectedItem = 0;
    ContactUs contactUsFragment;
    WhoUs whoUsFragment;
    QustionsFragment qustionsFragment;
    EstfsarFragment istfsarFragment;
    MyQusetion myQusetion;
    TextView tvToolBarTitle;
    NewsFragment newsFragment;
    MajorsFragment majorsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Fresco.initialize(this);
        toolbar = findViewById(R.id.toolbar_condition);
        tvToolBarTitle = findViewById(R.id.textView5);

        whoUsFragment = new WhoUs();
        contactUsFragment = new ContactUs();
        newsFragment = new NewsFragment();

        qustionsFragment = new QustionsFragment();
        istfsarFragment = new EstfsarFragment();
        myQusetion = new MyQusetion();
        majorsFragment = new MajorsFragment();
        navigationView = findViewById(R.id.bottom_navigation);

        setSupportActionBar(toolbar);

        createNavigationView();
        createDrawer();
        toolbar.setNavigationIcon(R.drawable.drawer);

    }

    private void createDrawer() {
        AccountHeaderBuilder accountHeaderBuilder = new AccountHeaderBuilder();
        accountHeaderBuilder.withActivity(this).withHeaderBackground(R.drawable.dalel);
        AccountHeader accountHeader = accountHeaderBuilder.build();

        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(1).withName(getResources().getString(R.string.my_question)).withIcon(R.drawable.ic_conversation);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(2).withName("المشاركة و الاعلانات").
                withIcon(R.drawable.ic_phone_call);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(4).withName("دكاترة الجامعة").withIcon(R.drawable.ic_teacher);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(5).withName("من نحن ").withIcon(R.drawable.ic_baseline_perm_device_information_24);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(6).withName("تسجيل خروج").withIcon(R.drawable.ic_exit);
        new DrawerBuilder().
                withSystemUIHidden(false).withActionBarDrawerToggle(true)
                .withAccountHeader(accountHeader).withCloseOnClick(true)
                .withActivity(this)
                .withToolbar(toolbar)
                .withCloseOnClick(true)
                .withSelectedItem(selectedItem)
                .addDrawerItems( item2, item3, item5, item6, item7)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                if (!myQusetion.isAdded()) {
                                   // tvToolBarTitle.setText(R.string.my_question);

                                  /* */
                                    startActivity(new Intent(getApplicationContext(),ToDrawer.class).putExtra("fragment","myQusetion"));

                                    selectedItem = 1;
                                    }
                                break;
                            case 2:
                                if (!contactUsFragment.isAdded()) {
                                  //  tvToolBarTitle.setText(R.string.contact_us);

startActivity(new Intent(getApplicationContext(),ToDrawer.class).putExtra("fragment","contactUsFragment"));
                                /*    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_drawer, contactUsFragment).setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                                            .commit();*/

                                    selectedItem = 2;
                                    //hideNav();


                                }
                                break;

                            case 3:

                                    startActivity(new Intent(getApplicationContext(),DoctorActivity.class));

                                    selectedItem = 3;

                                break;
                            case 4:
                                if (!whoUsFragment.isAdded()) {
                                    startActivity(new Intent(getApplicationContext(),ToDrawer.class).putExtra("fragment","whoUsFragment"));

                                    selectedItem = 4;

                                }
                                break;


                            case 5:
                                FirebaseAuth.getInstance().signOut();
                                try {
                                    emptySharePreference();
                                    FirebaseAuth.getInstance().getFirebaseAuthSettings();
                                    FirebaseInstanceId.getInstance().deleteInstanceId();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                                break;




                        }
                        return false;
                    }
                }).build();
    }
    void emptySharePreference(){
        SharedPreferences pref= getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        pref.edit().putString("collegeName", "null");
        pref.edit().putString("email", "null");
        pref.edit().putString("password", "null");
    }

    private void createNavigationView() {

        navigationView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), android.R.color.transparent));

        navigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int item) {
                switch (item) {
                    case R.id.main:
                        if (!newsFragment.isVisible()) {
                            tvToolBarTitle.setText("دليل الطالب");
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, newsFragment).commit();
                        }
                        break;
                    case R.id.majors:
                        if (!majorsFragment.isVisible()) {
                            tvToolBarTitle.setText("دليل الطالب");
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, majorsFragment)
                                    .commit();
                        }
                        break;
                    case R.id.inquiry:
                        if (!istfsarFragment.isAdded()) {
                            tvToolBarTitle.setText("دليل الطالب");

                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_home, new EstfsarFragment()  )
                                    .commit();
                            getFragmentManager().popBackStack();
                        }
                        break;
                }
            }
        });
        navigationView.setItemSelected(R.id.main, true);
    }
    void  hideNav(){
    /*    Animation bottomUp = AnimationUtils.loadAnimation(this,
            R.anim.bottom_down);
        navigationView.setAnimation(bottomUp);*/
        navigationView.setVisibility(View.GONE);
    }


    void  showNav(){
       /* Animation bottomUp = AnimationUtils.loadAnimation(this,
                R.anim.bottom_up);
        navigationView.setAnimation(bottomUp);*/
        navigationView.setVisibility(View.VISIBLE);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) HomeActivity.this.getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryRefinementEnabled(true);
        searchView.setQueryHint("ابحث عن المادة");
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

*/
}