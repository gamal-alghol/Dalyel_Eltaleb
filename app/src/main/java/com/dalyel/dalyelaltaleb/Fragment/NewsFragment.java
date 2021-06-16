package com.dalyel.dalyelaltaleb.Fragment;

import android.os.Bundle;
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

import com.dalyel.dalyelaltaleb.Adabter.NewsAdapter;
import com.dalyel.dalyelaltaleb.Model.News2;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.viewModel.NewsInfoViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class NewsFragment extends Fragment {

    RecyclerView newsRecycler;
NewsInfoViewModel newsInfoViewModel;
    NewsAdapter newsAdapter;
    ProgressBar progressBar;
    ShimmerFrameLayout shimmerFrameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_news, container, false);

        newsRecycler =view.findViewById(R.id.list_news);
        progressBar =view.findViewById(R.id.progress_sub);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (newsAdapter !=null)
            newsAdapter.startListening();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
@Override
public void onResume() {
    super.onResume();
    newsInfoViewModel = ViewModelProviders.of(this).get(NewsInfoViewModel.class);

    createRecyclerViewMajors();
}

    @Override
    public void onPause() {
        super.onPause();
        newsInfoViewModel.getNewsLiveData().removeObservers(this);
    }

    private void createRecyclerViewMajors() {
     //   progressBar.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        newsRecycler.setLayoutManager(getLinearManger(LinearLayoutManager.VERTICAL));
        newsInfoViewModel.getNewsLiveData().observe(getActivity(), new Observer<FirestoreRecyclerOptions<News2>>() {
            @Override
            public void onChanged(FirestoreRecyclerOptions<News2> subjectFirestoreRecyclerOptions) {
                newsAdapter = new NewsAdapter(subjectFirestoreRecyclerOptions,getContext());
                newsRecycler.setAdapter(newsAdapter);
                newsAdapter.startListening();
                shimmerFrameLayout.setVisibility(View.INVISIBLE);
                newsRecycler.setVisibility(View.VISIBLE);

            }
        });

    }
    private LinearLayoutManager getLinearManger(int orientation) {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(orientation);
        return llm;
    }
    @Override
    public void onStop() {
        super.onStop();
     if (newsAdapter !=null)
         newsAdapter.stopListening();
    }

}