package com.dalyel.dalyelaltaleb.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dalyel.dalyelaltaleb.Adabter.MajorsAdapter;
import com.dalyel.dalyelaltaleb.Model.Major;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.viewModel.CollegeInfoViewModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class MajorsFragment extends Fragment {

    RecyclerView majorsRecycler;

CollegeInfoViewModel collegeInfoViewModel;
    MajorsAdapter majorsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_majors, container, false);

        majorsRecycler=view.findViewById(R.id.list_specialties);

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
        if (majorsAdapter!=null)
            majorsAdapter.startListening();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        collegeInfoViewModel= ViewModelProviders.of(this).get(CollegeInfoViewModel.class);
        createRecyclerViewMajors();
        getView().findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_home,new RequirementsFragment())
                        .addToBackStack("hi")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .commit();
            }
        });
    }




    private void createRecyclerViewMajors() {

        collegeInfoViewModel.getListMajors(getMyCollege()).observe(getActivity(), new Observer<FirestoreRecyclerOptions<Major>>() {
            @Override
            public void onChanged(FirestoreRecyclerOptions<Major> majorFirestoreRecyclerOptions) {
                 majorsAdapter = new MajorsAdapter(majorFirestoreRecyclerOptions,getContext());
                majorsRecycler.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
                majorsRecycler.setAdapter(majorsAdapter);

                majorsAdapter.setOnItemClickListener(new MajorsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClicked(String major) {
                        getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("ddd")
                                .replace(R.id.fragment_home,new SubjectsFragment(major)).commit();
                           getActivity().overridePendingTransition(R.anim.fromleft, R.anim.out_to_right);
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




    String getMyCollege(){
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        String collegeName=pref.getString("collegeName","none");
        return collegeName;

    }
}