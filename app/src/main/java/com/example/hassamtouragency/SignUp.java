package com.example.hassamtouragency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassamtouragency.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText editTextmail,editTextusername,editTextpassword,editTextmobile;
    private ProgressBar progressBar;
    private FirebaseAuth fa;
    TextView signup,signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fa = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        editTextmail = (EditText) findViewById(R.id.mail);
        editTextusername = (EditText) findViewById(R.id.username);
        editTextpassword = (EditText) findViewById(R.id.password);
        editTextmobile = (EditText) findViewById(R.id.mobile);
        signin = (TextView) findViewById(R.id.signin);
        signup = (TextView) findViewById(R.id.signup);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, SignIn.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupCustomer();
            }
        });
    }

    private void signupCustomer() {
        String s_mail = editTextmail.getText().toString().trim();
        String s_username = editTextusername.getText().toString().trim();
        String s_password = editTextpassword.getText().toString().trim();
        String s_mobile = editTextmobile.getText().toString().trim();

        if(s_username.isEmpty()){
            editTextusername.setError("Do not leave blank input field");
            editTextusername.requestFocus();
            return;
        }
        if(s_mobile.isEmpty()){
            editTextmobile.setError("Do not leave blank input field");
            editTextmobile.requestFocus();
            return;
        }
        if(s_mail.isEmpty()){
            editTextmail.setError("Do not leave blank input field");
            editTextmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(s_mail).matches()){
            editTextmail.setError("Enter Currect Email Address");
            editTextmail.requestFocus();
            return;
        }
        if(s_password.isEmpty()){
            editTextpassword.setError("Do not leave blank input field");
            editTextpassword.requestFocus();
            return;
        }

        fa.createUserWithEmailAndPassword(s_mail,s_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Users users = new Users(s_mail,s_username,s_mobile);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this,"User has been registered successfully !",Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(SignUp.this,"Failed to registered try gain", Toast.LENGTH_LONG).show();
                                //progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        }) .addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Here you get the error type
                Log.d("Checkerror" + " - On Failure", e.getMessage());
                Toast.makeText(SignUp.this,e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}