package com.dalyel.dalyelaltaleb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dalyel.dalyelaltaleb.Model.Major;
import com.dalyel.dalyelaltaleb.Model.Subject;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

    public class CollegeInfoViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> listMutableLiveData;
    private MutableLiveData<FirestoreRecyclerOptions<Major>> majorsLiveData;
    private MutableLiveData<List<String>> levelsMutibleLiveData;
    private MutableLiveData<HashMap<String, List<String>>> listDataChild;
    private MutableLiveData<HashMap<String, List<Subject>>> listDataChild2;
    List<String> levelsName;
    HashMap<String, List<Subject>> listHashMap2;
    String id;
    private MutableLiveData<FirestoreRecyclerOptions<Subject>> requirementsLiveData;


    public CollegeInfoViewModel(@NonNull Application application) {
        super(application);
        listMutableLiveData = new MutableLiveData<>();
        majorsLiveData = new MutableLiveData<>();
        levelsMutibleLiveData = new MutableLiveData<>();
        listDataChild2 = new MutableLiveData<>();
        requirementsLiveData = new MutableLiveData<>();


    }

    public MutableLiveData<List<String>> getAllColleges() {

        FirebaseFirestore.getInstance().collection("colleges")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> ids = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        ids.add(id);
                    }
                    listMutableLiveData.setValue(ids);

                }

            }
        });

        return listMutableLiveData;
    }

    public MutableLiveData<FirestoreRecyclerOptions<Major>> getListMajors(String collegeName) {

        Query query = FirebaseFirestore.getInstance()
                .collection("colleges").document(collegeName).collection("majors");

        majorsLiveData.setValue(new FirestoreRecyclerOptions.Builder<Major>()
                .setQuery(query, Major.class)
                .build());
  query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
          if(task.isSuccessful()){
          }
      }
  });

        return majorsLiveData;
    }


    public MutableLiveData<List<String>> getLevelsTitles(String myCollege, String major) {
        FirebaseFirestore.getInstance().
                collection("colleges")
                .document(myCollege)
                .collection("majors")
                .document(major)
                .collection("levels")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<String> levelsName = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String id = document.getId();
                        levelsName.add(id);
                    }
                    levelsMutibleLiveData.setValue(levelsName);

                }

            }
        });

        return levelsMutibleLiveData;
    }



    public MutableLiveData<HashMap<String, List<Subject>>> getAllSub(final String myCollege, final String major) {

        listHashMap2 = new HashMap<>();
        FirebaseFirestore.getInstance().
                collection("colleges")
                .document(myCollege)
                .collection("majors")
                .document(major)
                .collection("levels")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().size(); i++) {

                        final String id = task.getResult().getDocuments().get(i).getId();
                        FirebaseFirestore.getInstance().collection("colleges")
                                .document(myCollege)
                                .collection("majors")
                                .document(major)
                                .collection("levels")
                                .document(id).
                                collection("subjects")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            ArrayList<Subject> subjects = new ArrayList<>();
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Subject subject = document.toObject(Subject.class);
                                                subject.setId(document.getId());
                                                subjects.add(subject);

                                            }
                                         listHashMap2.put(id, subjects);
                                        }

                                    }
                                });

                    }
                    listDataChild2.setValue(listHashMap2);
                }

            }
        });

        return listDataChild2;
        //  return ;
    }

    public MutableLiveData<FirestoreRecyclerOptions<Subject>> getRequirementsLiveData() {

        Query query = FirebaseFirestore.getInstance()
                .collection("requirements");
        requirementsLiveData.setValue(new FirestoreRecyclerOptions.Builder<Subject>()
                .setQuery(query, Subject.class)
                .build());
        return requirementsLiveData;
    }


}