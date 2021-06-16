package com.dalyel.dalyelaltaleb.Fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Adabter.RequirementsAdapter;
import com.dalyel.dalyelaltaleb.Model.Subject;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.viewModel.CollegeInfoViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RequirementsFragment extends Fragment {

    RecyclerView requirementsRecycler;
    private InterstitialAd interstitialAd;

CollegeInfoViewModel collegeInfoViewModel;
    RequirementsAdapter requirementsAdapter;
    ProgressBar progressBar;
    String PfilesUrl;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_requirements, container, false);

        requirementsRecycler =view.findViewById(R.id.list_requirements);
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
    public void onStart() {
        super.onStart();
        if (requirementsAdapter !=null)
            requirementsAdapter.startListening();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        collegeInfoViewModel= ViewModelProviders.of(this).get(CollegeInfoViewModel.class);
        createRecyclerViewMajors();
        //      ca-app-pub-7405373373047163/6977593853
        interstitialAd = new InterstitialAd(getContext());
        interstitialAd.setAdUnitId("ca-app-pub-7405373373047163/6977593853");
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
    }

    @Override
    public void onPause() {
        super.onPause();
        collegeInfoViewModel.getRequirementsLiveData().removeObservers(this);
    }

    private void createRecyclerViewMajors() {
        progressBar.setVisibility(View.VISIBLE);
        requirementsRecycler.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
        collegeInfoViewModel.getRequirementsLiveData().observe(getActivity(), new Observer<FirestoreRecyclerOptions<Subject>>() {
            @Override
            public void onChanged(FirestoreRecyclerOptions<Subject> subjectFirestoreRecyclerOptions) {
                requirementsAdapter = new RequirementsAdapter(subjectFirestoreRecyclerOptions);
                requirementsRecycler.setAdapter(requirementsAdapter);
                requirementsAdapter.startListening();
                progressBar.setVisibility(View.INVISIBLE);
                requirementsRecycler.setVisibility(View.VISIBLE);


                requirementsAdapter.setOnItemClickListener(new RequirementsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(String filesUrl) {
PfilesUrl=filesUrl;
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
                                openGoogleDrive(PfilesUrl);

                            }
                        });
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        }
                        else {
                            openGoogleDrive(PfilesUrl);


                            Log.d("ttt","s++++"+2);

                        }

                    }
                });

            }
        });
    }
    private LinearLayoutManager getLinearManger(int orientation) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(orientation);
        return llm;
    }
    void  openGoogleDrive(String filesUrl){
        if(!filesUrl.isEmpty()){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(filesUrl));
            startActivity(i);}else{
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("سيتم توفير الملخصات قريبا")
                    .setContentText("هل تريد مساعدتنا في توفير الملخصات؟")
                    .setCancelText("لا شكرا")
                    .setConfirmText("نعم")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            String smsNumber = "972594132519";
                            Intent sendIntent = new Intent("android.intent.action.MAIN");
                            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
                            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

                            startActivity(sendIntent);

                        }
                    })
                    .show();
        }

    }

    @Override
    public void onStop() {
        super.onStop();
     if (requirementsAdapter !=null)
         requirementsAdapter.stopListening();
    }

}