package com.example.doan_ltddnc_appbantaphoa.RegisterFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.doan_ltddnc_appbantaphoa.LoadingDialog;
import com.example.doan_ltddnc_appbantaphoa.LoginActivity;
import com.example.doan_ltddnc_appbantaphoa.Model.User;
import com.example.doan_ltddnc_appbantaphoa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsernamePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsernamePasswordFragment extends Fragment {
    TextInputEditText edtEmail,edtPassword,edtconfirmpass;
    Button btnRegister ;
    FirebaseAuth auth ;
    LoadingDialog loadingDialog ;
   /* FirebaseFirestore firestore ;*/
    FirebaseDatabase database ;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsernamePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsernamePasswordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsernamePasswordFragment newInstance(String param1, String param2) {
        UsernamePasswordFragment fragment = new UsernamePasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_username_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtEmail =view.findViewById(R.id.edtEmail);
        edtPassword =view.findViewById(R.id.edtPassword);
        edtconfirmpass =view.findViewById(R.id.edtConfirmPassword);
        btnRegister=view.findViewById(R.id.btnRegister);
        auth =FirebaseAuth.getInstance();
       /* firestore =FirebaseFirestore.getInstance();*/
        database =FirebaseDatabase.getInstance() ;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = getArguments().getString("firstname");
                String lastname = getArguments().getString("lastname");
                String mobile =getArguments().getString("mobile");
                String email =edtEmail.getText().toString().trim();
                String password =edtconfirmpass.getText().toString().trim();
                String confirmPassword =edtconfirmpass.getText().toString().trim();
                if(email.equals("") || password.equals("") || confirmPassword.equals("")){
                    Toast.makeText(getActivity(), "Not null", Toast.LENGTH_SHORT).show();
                }else {
                    if(!checkEmail(email) || !checkLength(password) || !checkHasSymbol(password) || !checkHasUpperCase(password) || !checkHasLowerCase(password) ||!(confirmPassword.equals(password))) {
                        if (!checkEmail(email)) {
                            edtEmail.setError("Not correct format");
                            edtEmail.requestFocus();
                        }
                        if (!checkLength(password) || !checkHasSymbol(password) || !checkHasUpperCase(password) || !checkHasLowerCase(password)) {
                            edtPassword.setError("yêu cầu 6 ký tự trở lên " + "\n"
                                    + "Chữ hoa ,chữ thường ,ký tự");
                            edtPassword.requestFocus();
                        }
                        if (!(confirmPassword.equals(password))) {
                            edtconfirmpass.setError("xác nhận mật khẩu không khớp");
                        }
                    }else{
                        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isComplete()){
                                    loadingDialog =new LoadingDialog(getContext());
                                    loadingDialog.ShowDialog("Đăng ký");
                                    User user =new User(firstname ,lastname,mobile,email,password);
                                    String UID =task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child(UID).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isComplete()){
                                                Intent intent =new Intent(getActivity() , LoginActivity.class);
                                                intent.putExtra("email",email);
                                                intent.putExtra("password",password);
                                                getActivity().setResult(Activity.RESULT_OK, intent);
                                                Toast.makeText(getActivity(), "Hoàn tất", Toast.LENGTH_SHORT).show();
                                                getActivity().finish();
                                                loadingDialog.HideDialog();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            loadingDialog.HideDialog();
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        });
    }
    private boolean checkLength(String password) {
        return String.valueOf(password).length()>=6 ;
    }
    private  boolean checkHasSymbol(String password){
        String passSymbol =String.valueOf(password);
        return passSymbol.matches("(.*[:?!@#$%^&*()].*)");
    }
    private boolean checkHasUpperCase(String password){
        String passUpper =String.valueOf(password);
        return passUpper.matches("(.*[A-Z].*)");

    }
    private boolean checkHasLowerCase(String password){
        String passLower =String.valueOf(password);
        return passLower.matches("(.*[a-z].*)");

    }
    private boolean checkEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}