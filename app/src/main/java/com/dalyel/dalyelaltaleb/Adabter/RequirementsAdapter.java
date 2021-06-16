package com.dalyel.dalyelaltaleb.Adabter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Model.Subject;
import com.dalyel.dalyelaltaleb.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RequirementsAdapter extends FirestoreRecyclerAdapter<Subject, RequirementsAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;

   public interface OnItemClickListener {
      void onItemClicked(String filesUrl);
    }

   public  void setOnItemClickListener (OnItemClickListener onItemClickListener){
       this.onItemClickListener=onItemClickListener;
   }
    public RequirementsAdapter(@NonNull FirestoreRecyclerOptions<Subject> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull final Subject model) {
       final String subject=this.getSnapshots().getSnapshot(position).getId();
        holder.tvMajor.setText(subject);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null)
                onItemClickListener.onItemClicked(model.getFilesUrl());

            }
        });
       // Glide.with(holder.imgMajor).load(model.getImgUrl()).into(holder.imgMajor);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_group2, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

          ImageView imgMajor;
         TextView tvMajor;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
          //  imgMajor = itemView.findViewById(R.id.img_major);
            tvMajor = itemView.findViewById(R.id.lblListHeader);
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.anim);
            itemView.startAnimation(animation);

        }

    }
}
