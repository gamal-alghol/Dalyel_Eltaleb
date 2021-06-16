package com.dalyel.dalyelaltaleb.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.dalyel.dalyelaltaleb.R;

public class AnswerQusetion extends AppCompatActivity {
    ImageView imageAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_qusetion);
        imageAnswer=findViewById(R.id.imageView_answer);
        int  image= getIntent().getIntExtra("image",0);
        imageAnswer.setImageDrawable(getDrawable(image));

        /*
             Glide.with(this)
                .asBitmap()
                .load(getDrawable(image))
                .into(imageAnswer);
        Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
        ImageView image=new ImageView(this);
        image.setImageBitmap(bmp);*/
    }
}