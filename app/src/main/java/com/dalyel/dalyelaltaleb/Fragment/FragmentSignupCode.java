package com.dalyel.dalyelaltaleb.Fragment;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dalyel.dalyelaltaleb.Model.User;


public class FragmentSignupCode extends Fragment {
    User user;
    Button verify;
    private ProgressDialog dialog;

//    UserInfoViewModel userInfoViewModel;


    public FragmentSignupCode(User user) {
        this.user = user;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*
        verify = view.findViewById(R.id.Button_verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getCurrentUser().reload();
                if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                    startLoading();
                      getActivity().startActivity(new Intent(getContext(), HomeActivity.class).putExtra("college",user.getCollege()));
                      getActivity().overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                      stopLoading();
                } else {
                    Toast.makeText(getContext(), "لم تقم بتاكيد التسجيل,حاول مرة اخرى", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_signup_code, container, false);
        return null;
    }
/*
    private void startLoading() {

        dialog = ProgressDialog.show(getContext(), "",
                getResources().getString(R.string.loading_wait), true);
        dialog.setCancelable(false);
    }

    private void stopLoading() {
        dialog.dismiss();
        dialog = null;
    }


    void  showSuccessDialog(String title){
        new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .show();

    }
*/
}