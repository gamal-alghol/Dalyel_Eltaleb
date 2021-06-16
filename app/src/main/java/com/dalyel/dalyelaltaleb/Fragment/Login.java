package com.dalyel.dalyelaltaleb.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.View.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Login extends Fragment {

    TextView signUp, forgetPassword;
    EditText email, password;
    private ProgressDialog dialog;
    Button logIn;
    SignupFragment signupFragment;

    public Login() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.slide_right));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        signUp = view.findViewById(R.id.signUp_txt);
        logIn = view.findViewById(R.id.login_btn);
        email = view.findViewById(R.id.textEmailAddress);
        forgetPassword = view.findViewById(R.id.forget_password);
        password = view.findViewById(R.id.textPassword);
        signupFragment = new SignupFragment();
        onSignUpClick();
        onLoginClick();
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForgetPasswordClick();

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void onForgetPasswordClick() {
        if (inputValid(email)) {
            startLoading();
            FirebaseAuth.getInstance().sendPasswordResetEmail(email.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            stopLoading();
                            if (task.isSuccessful()) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("تم ارسال رابط الى البريد الالكتروني لاعادة تعيين كلمة المرور")
                                        .setConfirmText("تم")

                                        .show();
                            } else
                                Toast.makeText(getContext(), "تعذر اعادة تعيين كلمة المرور", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    if (e.getMessage().contains("badly formatted")) {
                        stopLoading();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("البريد الإلكتروني منسق بشكل خاطئ")
                                .show();
                    }else if (e.getMessage().contains("The user may have been deleted")){

                        stopLoading();
                        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("البريد الإلكتروني غير مسجل")
                                .show();
                    }
                }
            });
        }
    }

    private void onLoginClick() {
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void onSignUpClick() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                ).
                        replace(R.id.fragment_regstration, signupFragment)
                        .addToBackStack(null)
                        .commit();


            }
        });
    }

    private void signIn() {

        if (inputValid(email) && inputValid(password)) {
            if (isEmailValid(email.getText())) {
                startLoading();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(),
                        password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            getUserCollege();
                        } else {
                            stopLoading();
                            new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("خطأ في البريد الالكتروني او كلمه المرور")
                                   .setConfirmText("تعديل")
                                    .show();
                        }
                    }
                });
            }
        }
    }

    private boolean inputValid(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "يرجى اضافة " + editText.getHint(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void startLoading() {
        dialog = ProgressDialog.show(getContext(), "",
                getResources().getString(R.string.loading_wait), true);
        dialog.setCancelable(false);
    }

    private void stopLoading() {
        try{
        dialog.dismiss();
        dialog = null;
        }catch (NullPointerException e){
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    void getUserCollege() {
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        addCollegeToSharePreference(user.getCollege());
                        stopLoading();
                        Intent homeIntent = new Intent(getContext(), HomeActivity.class);
                        startActivity(homeIntent);
                        getActivity().overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

                        getActivity().finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    String getMyCollege() {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0);
        return pref.getString("collegeName", "none");
    }

    void addCollegeToSharePreference(String college) {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        pref.edit().putString("collegeName", college).apply();
        Toast.makeText(getContext(), college, Toast.LENGTH_SHORT).show();
    }


}