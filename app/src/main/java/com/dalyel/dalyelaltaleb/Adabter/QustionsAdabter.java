package com.dalyel.dalyelaltaleb.Adabter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.dalyel.dalyelaltaleb.Model.Qustion;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.View.AnswerQusetion;

import java.util.ArrayList;

public class QustionsAdabter extends RecyclerView.Adapter<QustionsAdabter.ViewHolder>{
    private Context context;
    private ArrayList<Qustion>qustionAndyaArray;

    public QustionsAdabter(Context context, ArrayList<Qustion> qustionAndyaArray) {
        this.context = context;
        this.qustionAndyaArray = qustionAndyaArray;
    }


    @Override
    public QustionsAdabter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return  new QustionsAdabter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.qustion_item,parent,false));

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Qustion qustion = qustionAndyaArray.get(position);
        Log.d("ttt",qustion.getQustion());
        holder.bind(qustion);
    }

    @Override
    public int getItemCount() {

        return qustionAndyaArray.size();
    }
    class  ViewHolder extends RecyclerView.ViewHolder {
        TextView qustionTV;
        ConstraintLayout constraintLayout;
        public ViewHolder( View itemView) {
            super(itemView);
            qustionTV=itemView.findViewById(R.id.question);
            constraintLayout=itemView.findViewById(R.id.go_answer);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
            itemView.startAnimation(animation);
        }
        public void bind(final Qustion qustion) {
            qustionTV.setText(qustion.getQustion());
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              context.startActivity(new Intent(context, AnswerQusetion.class).putExtra("image",qustion.getImage()));
                }
            });

        }

    }

}
