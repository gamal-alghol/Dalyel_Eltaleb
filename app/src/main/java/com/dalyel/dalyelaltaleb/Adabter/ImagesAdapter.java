package com.dalyel.dalyelaltaleb.Adabter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dalyel.dalyelaltaleb.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ImagesAdapter extends PagerAdapter {

  Context context;
    List<String>listUrlImages;
    static int myPosition;

    OnImageListener onImageListener;
    public  interface OnImageListener{
        void onImageClicked(String urlImg);
    }
    void setOnImageListener(OnImageListener onImageListener){
        this.onImageListener=onImageListener;
    }

    public ImagesAdapter(Context context, List<String>listUrlImages){
          this.context=context;
          this.listUrlImages=listUrlImages;
     }
    @Override
    public int getCount() {
        return listUrlImages.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
         View view = LayoutInflater.from(context).inflate(R.layout.item_img, null);
        SimpleDraweeView imageView = view.findViewById(R.id.img_pager);
        TextView textView = view.findViewById(R.id.textView);

            imageView.setImageURI(listUrlImages.get(position));

        Log.d("ttt",position+"/"+listUrlImages.size());
         myPosition=position+1;
        textView.setText(myPosition+"/"+listUrlImages.size());
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageListener.onImageClicked(listUrlImages.get(position));
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return object == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

}
