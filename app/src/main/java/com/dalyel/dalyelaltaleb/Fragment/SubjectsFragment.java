package com.dalyel.dalyelaltaleb.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.dalyel.dalyelaltaleb.Adabter.ExpandableListAdapter2;
import com.dalyel.dalyelaltaleb.Model.Subject;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.viewModel.CollegeInfoViewModel;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SubjectsFragment extends Fragment {

    ExpandableListView expandableListView;
    SharedPreferences pref;
    ProgressBar progressBar;
    private InterstitialAd interstitialAd;

CollegeInfoViewModel collegeInfoViewModel;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String myMajor,filesUrl;
    public SubjectsFragment(String myMajor){
        this.myMajor=myMajor;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_subjects, container, false);

        expandableListView =view.findViewById(R.id.list_subjects);
        progressBar =view.findViewById(R.id.progress_sub);
        return  view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collegeInfoViewModel= ViewModelProviders.of(this).get(CollegeInfoViewModel.class);
        createRecyclerViewMajors();
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-7405373373047163/2314372225");
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }






    private void createRecyclerViewMajors() {
        progressBar.setVisibility(View.VISIBLE);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
              collegeInfoViewModel.getLevelsTitles(getMyCollege(),myMajor).observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                listDataHeader.addAll(strings);
                collegeInfoViewModel.getAllSub(getMyCollege(),myMajor).observe(getActivity(), new Observer<HashMap<String, List<Subject>>>() {
                    @Override
                    public void onChanged(HashMap<String, List<Subject>> listSubjects) {
                        progressBar.setVisibility(View.INVISIBLE);
                        ExpandableListAdapter2 listAdapter = new ExpandableListAdapter2(getContext(), listDataHeader, listSubjects);
                        // setting list adapter
                        expandableListView.setAdapter(listAdapter);
                        expandableListView.setVisibility(View.VISIBLE);
                        listAdapter.setOnItemClickListener(new ExpandableListAdapter2.OnItemClickListener() {
                            @Override
                            public void onItemClicked(String filesUrl) {
                               pref =getActivity()
                                        .getSharedPreferences("MyPref", 0);
                                SubjectsFragment.this.filesUrl=filesUrl;
                                interstitialAd.setAdListener(new AdListener(){
                                    @Override
                                    public void onAdFailedToLoad(int i) {
                                        super.onAdFailedToLoad(i);


                                    }
                                });
                                interstitialAd.setAdListener(new AdListener() {
                                    @Override
                                    public void onAdClosed() {
                                        super.onAdClosed();
                                        if(!pref.contains("firstTime")){
                                            final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.BUTTON_NEUTRAL);
                                            pDialog.setTitleText("سيتم نقلك الي تطبيق Drive");
                                            pDialog.setCancelable(true);
                                            pDialog.setConfirmButton("موافق", new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    pref.edit().putBoolean("firstTime",true).apply();
                                                    pDialog.dismiss();
                                                    openGoogleDrive( SubjectsFragment.this.filesUrl );
                                                }
                                            });
                                            pDialog.show();

                                        }else{
                                            openGoogleDrive( SubjectsFragment.this.filesUrl );

                                        }

                                    }
                                });
                                if (interstitialAd.isLoaded()) {
                                    interstitialAd.show();
                                } else {
                                    if(!pref.contains("firstTime")){
                                        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.BUTTON_NEUTRAL);
                                        pDialog.setTitleText("سيتم نقلك الي تطبيق Drive");
                                        pDialog.setCancelable(true);
                                        pDialog.setConfirmButton("موافق", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                pref.edit().putBoolean("firstTime",true).apply();
                                                pDialog.dismiss();
                                                openGoogleDrive( SubjectsFragment.this.filesUrl );
                                            }
                                        });
                                        pDialog.show();

                                    }else{
                                        openGoogleDrive( SubjectsFragment.this.filesUrl );

                                    }


                                    Log.d("ttt","s++++"+2);

                                }

                            }
                        });
                        
                    }
                });

            }
        });


    }
    void  openGoogleDrive(String filesUrl){
     if(!filesUrl.isEmpty()){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(filesUrl));
        startActivity(i);
      }else{
         new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                 .setTitleText("سيتم توفير الملخصات قريبا")
                 .setContentText("هل تريد مساعدتنا في توفير الملخصات؟")
                 .setCancelText("لا شكرا")
                 .setConfirmText("نعم")
                 .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                     @Override
                     public void onClick(SweetAlertDialog sweetAlertDialog) {
                         try {
                             String smsNumber = "972594132519";
                             Intent sendIntent = new Intent("android.intent.action.MAIN");
                             sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                             sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");
                             startActivity(sendIntent);
                         }catch (Exception e){
copy("972594132519");
                         }
                     }
                 }).show();
     }
    }
    private void copy(String text){
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(text);
            Toast.makeText( getContext(), "تم نسخ"+text+"للتواصل", Toast.LENGTH_SHORT).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText(text,text);
            clipboard.setPrimaryClip(clip);
            Toast.makeText( getContext(), "تم نسخ"+text+"للتواصل", Toast.LENGTH_SHORT).show();

        }
    }
    String getMyCollege(){
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        return  pref.getString("collegeName","none");
    }
    /*
     * Preparing the list data
     */
    /*
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("سنة اولى ");
        listDataHeader.add("سنة ثانية ");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");



        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
    }
*/

}