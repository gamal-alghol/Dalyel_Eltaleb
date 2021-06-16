package com.dalyel.dalyelaltaleb.Adabter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.dalyel.dalyelaltaleb.Model.Subject;
import com.dalyel.dalyelaltaleb.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter2 extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Subject>> _listDataChild;



    public ExpandableListAdapter2(Context context, List<String> listDataHeader,
                                  HashMap<String, List<Subject>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
//

    }

    public interface OnItemClickListener {
        void onItemClicked(String filesUrl);
    }
    private OnItemClickListener onItemClickListener;


    public  void setOnItemClickListener (OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        final Subject subject = (Subject) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(subject.getId());
        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null)
                    onItemClickListener.onItemClicked(subject.getFilesUrl());

            }
        });

        Animation animation = AnimationUtils.loadAnimation(_context, R.anim.anim);
        convertView.startAnimation(animation);
        txtListChild.setBackgroundColor(_context.getResources().getColor(R.color.back_Child_2));
        txtListChild.setTextColor(_context.getResources().getColor(R.color.white));

        convertView.setBackgroundColor(_context.getResources().getColor(R.color.back_Child));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }catch (NullPointerException e){
            return 0;
        }

    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
if(isExpanded){
    lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_top,0,0,0);
}else{
    lblListHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bottom,0,0,0);

}
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}