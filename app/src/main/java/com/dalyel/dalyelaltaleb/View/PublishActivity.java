package com.dalyel.dalyelaltaleb.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dalyel.dalyelaltaleb.Model.Estfsar;
import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class PublishActivity extends AppCompatActivity {
EditText post;
Button publish;
    Long time;
    Context context;
    private ProgressDialog dialog;
    SweetAlertDialog pDialogLoding;
    SweetAlertDialog error;
    CircleImageView imageUser;
    TextView name,count;
    Estfsar estfsar;
    DatabaseReference postReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        getImageUser();
        post=findViewById(R.id.post);
        name=findViewById(R.id.name);
        count=findViewById(R.id.count);
        imageUser=findViewById(R.id.imageView6);
        name.setText(getIntent().getStringExtra("name"));
        context=this;
        publish=findViewById(R.id.button);
        pDialogLoding = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialogLoding.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialogLoding.setTitleText(getResources().getString(R.string.loading_wait));
        pDialogLoding.setCancelable(false);

post.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
count.setText("0/200");
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        count.setText(charSequence.length()+"/100");
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
});
        publish.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!post.getText().toString().trim().equals("")){
                    pDialogLoding.show();
                    time=getCurrentTimeMilliSecond();

                     postReference = FirebaseDatabase.getInstance().getReference("estfsarat");

                    String postKey = postReference.push().getKey();

                    estfsar=new Estfsar();
                    estfsar.setPost(post.getText().toString());
                    estfsar.setTime(time);
                    estfsar.setPostKey(postKey);
                    estfsar.setFromName(getIntent().getStringExtra("name"));
                    estfsar.setFromEmail((FirebaseAuth.getInstance().getCurrentUser().getUid()));

                             postReference.child(postKey).setValue(estfsar)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            pDialogLoding.cancel();
                            new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("تم النشر بنجاح")
                                    .show();
                                    post.setText("");
                            finish();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pDialogLoding.dismiss();
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("للاسف تعذر النشر")
                                    .show();
                        }
                    });
                }else{
                    Toast.makeText(context, "تعذر النشر, المحتوى قصير", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void getImageUser() {
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);

                        byte [] image=Base64.decode(user.getImage(), Base64.URL_SAFE);

                        Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(image)
                                .placeholder(getResources().getDrawable(R.drawable.dalel))
                                .into(imageUser);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
    public static long getCurrentTimeMilliSecond() {
        Calendar aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return aGMTCalendar.getTimeInMillis();
    }

}