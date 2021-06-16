package com.dalyel.dalyelaltaleb.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.transition.TransitionInflater;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dalyel.dalyelaltaleb.Model.User;
import com.dalyel.dalyelaltaleb.R;
import com.dalyel.dalyelaltaleb.View.HomeActivity;
import com.dalyel.dalyelaltaleb.viewModel.CollegeInfoViewModel;
import com.dalyel.dalyelaltaleb.viewModel.UserInfoViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SignupFragment extends Fragment {

    EditText email,name,universityId,phoone,password;
    String college;
    Spinner collegeSpinner;
    Button sginUp;
    private ProgressDialog dialog;
    FirebaseUser user;
    User userModel;
    UserInfoViewModel userInfoViewModel;

    FragmentSignupCode fragmentSignupCode;
    String byteArray;
    CollegeInfoViewModel collegeInfoViewModel;
    private CircleImageView imageUser;
    private Uri imageUri= Uri.parse("");
    private int REQUEST_PICK_PHOTO = 100;
    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            TransitionInflater inflater = TransitionInflater.from(requireContext());
            setEnterTransition(inflater.inflateTransition(R.transition.fade));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name=view.findViewById(R.id.name);
        email=view.findViewById(R.id.email);
        universityId=view.findViewById(R.id.university_id);
        phoone=view.findViewById(R.id.phoone);
        password=view.findViewById(R.id.password);
        collegeSpinner=view.findViewById(R.id.college);
        sginUp=view.findViewById(R.id.sginUp);
        imageUser=view.findViewById(R.id.image_view_profile_person);
        collegeInfoViewModel = ViewModelProviders.of(this).get(CollegeInfoViewModel.class);
        userInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sginUpClick();
     selectImage();
        collegeInfoViewModel.getAllColleges().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {

                strings.add(0,"اختار الكلية");
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getContext(),  android.R.layout.simple_spinner_item, strings);

                collegeSpinner.setAdapter(adapter);

                collegeSpinner.setSelection(0);
                collegeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(i!=0){

                            college= (String) collegeSpinner.getItemAtPosition(i);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }
        });
    }

    private void selectImage() {
        getView().findViewById(R.id.imageView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });
    }

    private void selectPhoto() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (imageUri != null) {
                imageUser.setImageURI(imageUri);
                imageViewToByte(imageUser);
            }
        }
    }

    private void sginUpClick() {
        sginUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(inputValid(name) && inputValid(phoone)&& inputValid(email)&& inputValid(password)&& inputValid(universityId)){
                    if(imageUser!=null&&!imageUri.toString().equals("")){
                        if(collegeSpinner.getSelectedItemPosition()!=0){
                    startLoading();
                    AuthUser();
                        }else {
                            Toast.makeText(getContext(), "الرجاء اختيار الكلية", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "الرجاء اضافة صورة شخصية", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }




            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_signup, container, false);
    }
    public  void AuthUser(){
        if(isPhoneValid(phoone.getText())&&isEmailValid(email.getText())) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            stopLoading();
                            if (e.getMessage().contains("badly formatted")) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("البريد الإلكتروني منسق بشكل خاطئ")
                                        .show();
                            } else if (e.getMessage().contains("least 6 characters")) {
                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("يجب أن تتكون كلمة المرور من 6 أحرف على الأقل")
                                        .show();
                            }else if(e.getMessage().contains("another account")){
                                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("لقد قمت بالتسجيل مسبقا, قم بتسجيل الدخول")
                                        .show();
                            }
                        }
                    }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendVerification();
                }
            });
        }
    }
    void addCollegeToSharePreference(String college) {
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        pref.edit().putString("collegeName", college).apply();
        Toast.makeText(getContext(), college, Toast.LENGTH_SHORT).show();
    }
    public  String imageViewToByte(CircleImageView simpleDraweeView) { ;
        Bitmap bitmap = ((BitmapDrawable)simpleDraweeView.getDrawable()).getBitmap();
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG  , 70, stream);

  byte[]  imageB64 = stream.toByteArray();
            Log.d("ttt",imageB64+"////toByteArray");
         byteArray = Base64.encodeToString(imageB64, Base64.URL_SAFE);
            return byteArray;
        }
        return byteArray;

    }

    void addEmailAndPasswordToSharePreference(String email,String password){
        SharedPreferences pref= getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        pref.edit().putString("email", email).putString("password",password).apply();
    }

    private  void sendVerification(){
        getUserInfo();
        addUserOnDataBase2();
        addCollegeToSharePreference(college);
        stopLoading();
        getActivity().startActivity(new Intent(getContext(), HomeActivity.class).putExtra("college",userModel.getCollege()));
        getActivity().overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
      /*  user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener( getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        getUserInfo();
                        addUserOnDataBase2();
                        addEmailAndPasswordToSharePreference(userModel.getGmail(),userModel.getPassword());
                        fragmentSignupCode=new FragmentSignupCode(userModel);
                        user.reload();
                        stopLoading();

                        getFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fromleft,R.anim.out_to_right)
                                .replace(R.id.fragment_regstration,fragmentSignupCode)
                                .commit();
                        getActivity().overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);

                    }
                });*/
    }
    private void getUserInfo() {
        userModel=new User();
        userModel.setName(name.getText().toString());
        userModel.setGmail(email.getText().toString());
        userModel.setPassword(password.getText().toString());
        userModel.setPhoon(phoone.getText().toString());
        userModel.setUniversityId(universityId.getText().toString());
        userModel.setCollege(college);
        userModel.setImage(byteArray);
    }
    private void addUserOnDataBase2() {
        userInfoViewModel.addUser(userModel).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userAdded) {
                if (userAdded){
                    SharedPreferences pref= getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    pref.edit().putString("collegeName", userModel.getCollege()).apply(); // Storing string
                    stopLoading();

                }
            }
        });
    }

    private boolean inputValid(EditText editText) {
        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(getContext(),"يرجى اضافة "+editText.getHint(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    boolean isPhoneValid(CharSequence phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }
    private void startLoading() {
        dialog = ProgressDialog.show(getContext(), "",
                getResources().getString(R.string.loading_wait), true);
        dialog.setCancelable(false);
    }

    private void stopLoading() {
        if(dialog!=null){
        dialog.dismiss();
        dialog = null;}
    }


}