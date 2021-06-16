package com.dalyel.dalyelaltaleb.Adabter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Model.Doctor;
import com.dalyel.dalyelaltaleb.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private Context context;
    ArrayList <Doctor> doctorArrayList;
    FragmentManager fragmentManager;
    Activity activAity;
    private DoctorFilter doctorFilter;
    public DoctorAdapter(ArrayList <Doctor> doctorArrayList, Context context, FragmentManager fragmentManager, Activity activAity) {

        this.context=context;
        this.doctorArrayList=doctorArrayList;
this.fragmentManager=fragmentManager;
this.activAity=activAity;
        doctorFilter =new DoctorFilter();
    }
    public Filter getFilter(){
        return doctorFilter;
    }



    @NonNull
    @Override
    public DoctorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = doctorArrayList.get(position);
        Log.d("ttt",doctor.getName());
        holder.bind(doctor);
    }




    @Override
    public int getItemCount() {
        return doctorArrayList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,college;
        ConstraintLayout constraintLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            college = itemView.findViewById(R.id.college);
             constraintLayout=itemView.findViewById(R.id.doctor_constraint);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim);
            itemView.startAnimation(animation);
        }
        void  bind(final Doctor doctor){
            name.setText(doctor.getName());
            college.setText(doctor.getCollege());
constraintLayout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activAity);
        final AlertDialog dialog = builder.create();
        View dialogLayout = LayoutInflater.from(context).inflate(R.layout.info_doctor, null,false);
        TextView email=dialogLayout.findViewById(R.id.gmail);
        TextView  phone=dialogLayout.findViewById(R.id.phone);
        TextView copyEmail=dialogLayout.findViewById(R.id.imageButton_email);
        TextView copyPhone=dialogLayout.findViewById(R.id.imageButton_phone);

        email.setText(doctor.getEmail());
        phone.setText(doctor.getPhone());
copyEmail.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!doctor.getEmail().isEmpty()){
        copy(doctor.getEmail());}
    }
});

        copyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!doctor.getPhone().isEmpty()){

                    copy(doctor.getPhone());}
            }
        });

        dialog.setView(dialogLayout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        if (!dialog.isShowing())
            dialog.show();
        /*
        InfoDoctorSheet infoDoctorSheet = new InfoDoctorSheet(doctor.getEmail(),doctor.getPhone());
        infoDoctorSheet.show(fragmentManager,infoDoctorSheet.getTag());*/
    }
}
);
        }
    }
    class DoctorFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchQuery=charSequence.toString();
            FilterResults filterResults=new FilterResults();
            List<Doctor> tempList=new ArrayList<>();
            for (Doctor user :doctorArrayList){
                if (user.getName().contains(searchQuery)){
                    tempList.add(user);
                }
            }
            filterResults.values=tempList;
            filterResults.count=tempList.size();


            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            doctorArrayList= (ArrayList<Doctor>) filterResults.values;
            notifyDataSetChanged();

        }
    }

    private void copy(String text){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText(context, "تم نسخ"+text, Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(text,text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "تم نسخ"+text, Toast.LENGTH_SHORT).show();

        }
    }
    }


