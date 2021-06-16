package com.dalyel.dalyelaltaleb.Adabter;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dalyel.dalyelaltaleb.Model.Comment;
import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class CommentsSheetAdapter extends RecyclerView.Adapter<CommentsSheetAdapter.ViewHolder> {
    private Context context;
    Calendar now, time;
    DatabaseReference userRef;
    FragmentManager fragmentManager;
    private ArrayList<Comment> commentsArrayList;

    public CommentsSheetAdapter(ArrayList<Comment> commentsArrayList, Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.commentsArrayList = commentsArrayList;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public CommentsSheetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Fresco.initialize(context);
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_comments, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment commentsModel = commentsArrayList.get(position);

        holder.bind(commentsModel);
    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV, comment;
        CircleImageView userImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tv_username);
            comment = itemView.findViewById(R.id.tv_comment);

            userImage = itemView.findViewById(R.id.user_image_view);


        }

        public void bind(Comment commentsModel) {
            time = Calendar.getInstance();
            time.setTimeInMillis(commentsModel.getCommentTimeInMillis());
            now = Calendar.getInstance();
           // loadUser(name, commentsModel.getCommentedByUserKey());
          //getImageUser(userImage, commentsModel.getCommentedByUserKey());
            comment.setText(commentsModel.getComment());
            nameTV.setText(commentsModel.getCommentedByUserKey());
getImageUser(commentsModel.getFromEmail(),userImage);
        }

    }
/*
    private void getImageUser(SimpleDraweeView userImage, String userKey) {
        userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    User usersModel = dataSnapshot.getValue(User.class);
                    if (usersModel != null) {
                        FirebaseStorage.getInstance().getReference().child("usersImages/" + usersModel.getImageUrl()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                userImage.setImageURI(uri);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("ttt",exception.getMessage());
                                // Toast.makeText(getApplicationContext(), "فشل تحميل بعض الصور الشخصية", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        userImage.setImageResource(R.drawable.logo);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/


    private void getImageUser(String keyUser, final CircleImageView userImage) {
        FirebaseDatabase.getInstance().getReference("users")
                .child(keyUser).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user=dataSnapshot.getValue(User.class);
                    byte [] image= Base64.decode(user.getImage(), Base64.URL_SAFE);

                    Glide.with(context)
                            .asBitmap()
                            .load(image)
                            .placeholder(context.getResources().getDrawable(R.drawable.dalel))
                            .into(userImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
Log.d("ttt",databaseError.getMessage());
            }
        });

    }


    private void getTime(TextView time_tv) {
        if (now.getTimeInMillis() - time.getTimeInMillis() >= 59 * 1000 * 60) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(now.getTimeInMillis() - time.getTimeInMillis());
            time_tv.setText((calendar.getTimeInMillis() / (59 * 1000 * 60)) + " " + context.getResources().getString(R.string.hours_ago));
        } else if (now.getTimeInMillis() - time.getTimeInMillis() >= 1000 * 60) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(now.getTimeInMillis() - time.getTimeInMillis());
            time_tv.setText(calendar.get(Calendar.MINUTE) + " " + context.getResources().getString(R.string.minutes_ago));
        } else
            time_tv.setText(R.string.just_now);
    }
/*
    private void loadUser(TextView tv_username, String userKey) {
        if (userRef == null)
            userRef = FirebaseDatabase.getInstance().getReference("users");
        userRef.child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {

                    User usersModel = dataSnapshot.getValue(User.class);
                    if (usersModel != null) {
                        tv_username.setText(usersModel.getFirstName() + " " + usersModel.getLastName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }*/
}
