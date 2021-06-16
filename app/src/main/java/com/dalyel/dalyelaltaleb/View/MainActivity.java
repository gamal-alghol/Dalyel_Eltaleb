package com.dalyel.dalyelaltaleb.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.dalyel.dalyelaltaleb.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 650;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStaustBar();
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.imageView);
        /*
String ff="التمريض";
        ArrayList <Doctor> arrayList =new ArrayList<>();
        Doctor doctor1=new Doctor("يوسف الجيش",ff,"yjeesh@iugaza.edu.ps","");
        arrayList.add(doctor1);
        Doctor doctor2=new Doctor("أشرف الجدي",ff,"arodwan@iugaza.edu.ps","");
        arrayList.add(doctor2);
        Doctor doctor3=new Doctor("عبد الكريم رضوان",ff,"fsharif@iugaza.edu.ps","");
        arrayList.add(doctor3);
        Doctor doctor4=new Doctor("ميسون العزيز",ff,"mazizugaza.e@idu.ps","");
        arrayList.add(doctor4);
        Doctor doctor5=new Doctor("ناصر النور",ff,"mzaharna@iugaza.edu.ps","");
        arrayList.add(doctor5);
        Doctor doctor6=new Doctor(" أحمد الشاعر",ff,"aashaer@iugaza.edu.ps","");
        arrayList.add(doctor6);
        Doctor doctor7=new Doctor("أكرم أبوصلاح",ff,"asalah@iugaza.edu.ps","");
        arrayList.add(doctor7);
        Doctor doctor8=new Doctor("شرف الشرافي",ff,"sshurafi@iugaza.edu.ps","");
        arrayList.add(doctor8);
        Doctor doctor9=new Doctor("عاطف إسماعيل",ff,"atismail@iugaza.edu.ps","");
        arrayList.add(doctor9);
        Doctor doctor10=new Doctor("عطاف عابد",ff,"iabed@iugaza.edu.ps","");
        arrayList.add(doctor10);
        Doctor doctor11=new Doctor("محمد العطار",ff,"maattar@iugaza.edu.ps","");
        arrayList.add(doctor11);
        Doctor doctor12=new Doctor("موسى الزعبوط",ff,"mzabout@iugaza.edu.ps","");
        arrayList.add(doctor12);
        Doctor doctor13=new Doctor("وليد الصالحي",ff,"wsalehi@iugaza.edu.ps","");
        arrayList.add(doctor13);
        Doctor doctor14=new Doctor("أسامة عماد",ff,"oemad@iugaza.edu.ps","");
        arrayList.add(doctor14);
        Doctor doctor15=new Doctor("إمتثال أبوعيادة",ff,"eeyada@iugaza.edu.ps","");
        arrayList.add(doctor15);
        Doctor doctor16=new Doctor("بدر الصفدي",ff,"bsafadi@iugaza.edu.ps","");
        arrayList.add(doctor16);
        Doctor doctor17=new Doctor("حمزة عبدالجواد",ff,"khawad@iugaza.edu.ps","");
        arrayList.add(doctor17);
        Doctor doctor18=new Doctor("خالد خضورة",ff,"kkhadoura@iugaza.edu.ps","");
        arrayList.add(doctor18);
        Doctor doctor19=new Doctor("عبدالمطلب الكحلوت",ff,"amkahlout@iugaza.edu.ps","");
        arrayList.add(doctor19);
        Doctor doctor20=new Doctor("أعماد حبوب",ff,"ehabboub@iugaza.edu.ps","");
        arrayList.add(doctor20);
        Doctor doctor21=new Doctor("أحمد المصري",ff,"aamasri@iugaza.edu.ps","");
        arrayList.add(doctor21);
        Doctor doctor22=new Doctor("عريفة البحري",ff,"abahri@iugaza.edu.ps","");
        arrayList.add(doctor22);
        Doctor doctor23=new Doctor("كمال جبر",ff,"lmasharfa@iugaza.edu.ps","");
        arrayList.add(doctor23);
        Doctor doctor24=new Doctor("ليلى المشارفة",ff,"lmasharfa@iugaza.edu.ps","");
        arrayList.add(doctor24);
        Doctor doctor25=new Doctor("وفاء عبيد",ff,"wabeid@iugaza.edu.ps","");
        arrayList.add(doctor25);

        for(int i=0;i<arrayList.size();i++){
         FirebaseDatabase.getInstance()
                    .getReference("doctor").child(arrayList.get(i).getName())
                    .setValue(arrayList.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
//                    Log.d("ttt",task.getResult().toString());
                }
            });
        }


        /*
        //سنة اولى
        //سنة تانية
        //سنة ثالثة
            //سنة رابعة
        HashMap<String ,String> hashMap=new HashMap<>();
        hashMap.put("filesUrl","");
ArrayList<String> arrayList=new ArrayList<>();
arrayList.add("مهارات الخط العربي");
        arrayList.add("تطبيقات في الدراما والمسرح بالتعليم");
        arrayList.add("مهارات لغوية");
     //  arrayList.add("مهارات القراءة والكتابة للصفوف الأولى");




        for(int i=0;i<arrayList.size();i++) {
    FirebaseFirestore.getInstance().collection("colleges")
            .document("كلية التربية")
            .collection("majors").document("قسم التعليم الأساسي")
            .collection("levels").document("سنة رابعة").
            collection("subjects").document(arrayList.get(i)).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
       Log.d("ttt","succus");     }
        }
    });

}
*/ FirebaseApp.initializeApp(getApplicationContext()) ;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()==null){
                    startActivity(new Intent(getApplicationContext(), RegstrationAtivity.class));
                    finish();
                    overridePendingTransition(R.anim.fromleft,R.anim.out_to_right);
                }
                else{

                    startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    overridePendingTransition(R.anim.fromleft,R.anim.out_to_right);
                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);

    }

    private void hideStaustBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        if (getActionBar() != null) {
           getActionBar().hide();

        }}

}
