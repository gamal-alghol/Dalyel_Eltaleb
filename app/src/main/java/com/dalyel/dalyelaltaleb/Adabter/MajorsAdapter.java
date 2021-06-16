package com.dalyel.dalyelaltaleb.Adabter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Model.Major;
import com.dalyel.dalyelaltaleb.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class MajorsAdapter extends FirestoreRecyclerAdapter<Major, MajorsAdapter.ViewHolder> {

    private OnItemClickListener onItemClickListener;

    Context context;
   public interface OnItemClickListener {
      void onItemClicked(String major);
    }

   public  void setOnItemClickListener (OnItemClickListener onItemClickListener){
       this.onItemClickListener=onItemClickListener;
   }
    public MajorsAdapter(@NonNull FirestoreRecyclerOptions<Major> options,Context context) {
        super(options);
        this.context=context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Major model) {
       final String major=this.getSnapshots().getSnapshot(position).getId();


        holder.tvMajor.setText(major);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null)
                onItemClickListener.onItemClicked(major);

            }
        });
       // Glide.with(holder.imgMajor).load(model.getImgUrl()).into(holder.imgMajor);


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_major, parent, false));
    }

    class ViewHolder extends RecyclerView.ViewHolder {

          ImageView imgMajor;
         TextView tvMajor;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            imgMajor = itemView.findViewById(R.id.img_major);
            tvMajor = itemView.findViewById(R.id.tv_major_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener!=null){

                    Major major=getItem(getAdapterPosition());

                }
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
                itemView.startAnimation(animation);
            }
        });
        }

    }
}
