package com.dalyel.dalyelaltaleb.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dalyel.dalyelaltaleb.Model.News2;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NewsInfoViewModel extends AndroidViewModel {
    private MutableLiveData <FirestoreRecyclerOptions<News2>> requirementsLiveData;



    public NewsInfoViewModel(@NonNull Application application) {
        super(application);
        requirementsLiveData =new MutableLiveData<>();

    }
    public MutableLiveData<FirestoreRecyclerOptions<News2>> getNewsLiveData() {

        Query query = FirebaseFirestore.getInstance()
                .collection("news").orderBy("datePost", Query.Direction.DESCENDING);
        requirementsLiveData.setValue(new FirestoreRecyclerOptions.Builder<News2>()
                .setQuery(query, News2.class)
                .build());
        return requirementsLiveData;
    }





}