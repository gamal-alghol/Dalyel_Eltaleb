package com.dalyel.dalyelaltaleb.Adabter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dalyel.dalyelaltaleb.Fragment.CommentsSheetFragment;
import com.dalyel.dalyelaltaleb.Model.Estfsar;
import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class EstfsaratAdabter extends RecyclerView.Adapter<EstfsaratAdabter.ViewHolder>{
    private Context context;
    private ArrayList<Estfsar> estfsarArrayList;
    private Calendar time;
    private Calendar now;
    private  int position;
    private boolean ifMyqusetionFragmen;
FragmentManager fragmentManager;
public EstfsaratAdabter(ArrayList<Estfsar> estfsarArrayList, Context context,FragmentManager fragmentManager, boolean ifMyqusetionFragmen) {
    this.context = context;
    this.estfsarArrayList = estfsarArrayList;
this.ifMyqusetionFragmen = ifMyqusetionFragmen;
    this.fragmentManager = fragmentManager;
}



@Override
public EstfsaratAdabter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
    return  new EstfsaratAdabter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.estfsar_item,parent,false));

}

@Override
public void onBindViewHolder(@NonNull EstfsaratAdabter.ViewHolder holder, final int position) {
try {
    Estfsar estfsar = estfsarArrayList.get(estfsarArrayList.size()-1-position);
    checkAndDelete(estfsar,estfsarArrayList.size()-1-position);
    holder.bind(estfsar);

        holder.comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentsSheetFragment bottomSheetFilter = new CommentsSheetFragment(estfsarArrayList.get(estfsarArrayList.size()-1-position).getPostKey());
                bottomSheetFilter.show(fragmentManager,bottomSheetFilter.getTag());
            }
        });



    if(ifMyqusetionFragmen==true){

        holder.itemEstfsar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("تأكيد حذف الاستفسار")
                        .setCancelText("الغاء")
                        .setConfirmText("حذف").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        delete(estfsarArrayList.get(estfsarArrayList.size()-1-position).getFromEmail()
                                  ,estfsarArrayList.get(estfsarArrayList.size()-1-position).getPostKey());
                        estfsarArrayList.remove(estfsarArrayList.size()-1-position);
                        sweetAlertDialog.dismiss();
                        notifyDataSetChanged();
                    }
                }).show();
                return false;
            }
        });
        holder.itemEstfsar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ضغطة مطولة لحذف الاستفسار", Toast.LENGTH_SHORT).show();
            }
        });
    }
}catch (Exception e ){

}


}

@Override
public int getItemCount() {
    return estfsarArrayList.size();
}

class  ViewHolder extends RecyclerView.ViewHolder {
    TextView name, timePost, post;
    FrameLayout comments;
CircleImageView circleImageView;
ConstraintLayout itemEstfsar;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        timePost = itemView.findViewById(R.id.time);
        post = itemView.findViewById(R.id.post);
        comments = itemView.findViewById(R.id.comments);
        itemEstfsar = itemView.findViewById(R.id.estfsar_item);
        circleImageView = itemView.findViewById(R.id.imageView9);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
        itemView.startAnimation(animation);



    }
    public void bind(final Estfsar estfsar) {
        getImageUser(estfsar.getFromEmail(),circleImageView);
name.setText(estfsar.getFromName());
post.setText(estfsar.getPost());
        time = Calendar.getInstance();
        time.setTimeInMillis(estfsar.getTime());
        now = Calendar.getInstance();
        setTime(timePost);


    }
            }
    private void getImageUser(String keyUser, final CircleImageView userImage) {
        FirebaseDatabase.getInstance().getReference("users")
                .child(keyUser)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
try {
    byte [] image=Base64.decode(user.getImage(), Base64.URL_SAFE);

    Glide.with(context)
            .asBitmap()
            .load(image)
            .placeholder(context.getResources().getDrawable(R.drawable.dalel))
            .into(userImage);

}catch (Exception e){

}

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void checkAndDelete(Estfsar estfsarModel, int position) {
        if (estfsarModel == null) return;
        Calendar now = Calendar.getInstance();
        //for (int i = 0; i < estfsarModel.size(); i++) {
            Calendar time = Calendar.getInstance();
            time.setTimeInMillis(estfsarModel.getTime());
            if (now.getTimeInMillis() - time.getTimeInMillis() >= (1000 * 60 * 60 * 48))

                try {
                    delete(estfsarModel.getFromEmail(), estfsarModel.getPostKey());
                    estfsarArrayList.remove(position);
                    notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                }
       // }
    }
    DatabaseReference story;
    private void delete(String fromId, String storyKey) {
        if (story == null)
            story = FirebaseDatabase.getInstance().getReference("estfsarat");
        story.child(storyKey).removeValue();

    }
private void setTime(TextView timePost) {
    if (now.getTimeInMillis() - time.getTimeInMillis() >= 59 * 1000 * 60*24*7) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now.getTimeInMillis() - time.getTimeInMillis());
        timePost.setText((calendar.getTimeInMillis() / (59 * 1000 * 60*24*7)) + " " + context.getResources().getString(R.string.weeks_ago));
    } else
    if (now.getTimeInMillis() - time.getTimeInMillis() >= 59 * 1000 * 60*24) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now.getTimeInMillis() - time.getTimeInMillis());
        timePost.setText((calendar.getTimeInMillis() / (59 * 1000 * 60*24)) + " " + context.getResources().getString(R.string.days_ago));
    } else
    if (now.getTimeInMillis() - time.getTimeInMillis() >= 59 * 1000 * 60) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now.getTimeInMillis() - time.getTimeInMillis());
        timePost.setText((calendar.getTimeInMillis() / (59 * 1000 * 60)) + " " + context.getResources().getString(R.string.hours_ago));
    } else if (now.getTimeInMillis() - time.getTimeInMillis() >= 1000 * 60) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now.getTimeInMillis() - time.getTimeInMillis());
        timePost.setText(calendar.get(Calendar.MINUTE) + " " + context.getResources().getString(R.string.minutes_ago));
    } else{
        timePost.setText(R.string.just_now);}
}

}




