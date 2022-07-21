package com.example.hassamtouragency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hassamtouragency.settings.SharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    private EditText username,password;
    private TextView login,register;
    private FirebaseAuth fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);

        if (SharedPreference.getDataLogin(getApplicationContext())) {
            if (SharedPreference.getDataAs(getApplicationContext()).equals("user")) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
            }
        }


        fa = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginCustomer();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(SignIn.this, SignUp.class));
            }
        });
    }

    private void loginCustomer() {
        //startActivity(new Intent(SignIn.this,Home.class));
        String semail = username.getText().toString();
        String spassword = password.getText().toString();
        if(semail.isEmpty()){
            username.setError("Please Enter Email Address");
            username.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(semail).matches()){
            username.setError("Please Enter Valid Email Address");
            username.requestFocus();
            return;
        }

        if(spassword.isEmpty()){
            password.setError("Please Enter Password");
            password.requestFocus();
            return;
        }

        if(spassword.length() < 6){
            password.setError("Min Password lenght should be 6 characters");
            password.requestFocus();
            return;
        }

        fa.signInWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // redirect to user dashboard
                    //startActivity(new Intent(LoginUser.this,Dashboard.class));
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

                    reference.orderByChild("email").equalTo(semail).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot datas: dataSnapshot.getChildren()){
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();
                                String emaildata=datas.child("email").getValue().toString();
                                String namedata=datas.child("name").getValue().toString();
                                //Toast.makeText(getApplicationContext(),familyname,Toast.LENGTH_LONG).show();
                                SharedPreference.setDataLogin(SignIn.this, true);
                                SharedPreference.setDataAs(SignIn.this, "user");
                                SharedPreference.setDataUserid(SignIn.this,uid);
                                SharedPreference.setDataEmail(SignIn.this,emaildata);
                                SharedPreference.setDataFullname(SignIn.this,namedata);
                                startActivity(new Intent(SignIn.this, HomeActivity.class));
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }else{
                    Toast.makeText(SignIn.this,"Failed to login try gain", Toast.LENGTH_LONG).show();
                   // progressBar.setVisibility(View.GONE);
                }
            }
        });

    }
}