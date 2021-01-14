package com.example.i170212_i170321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference reference;
    Contact c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        c = new Contact();
        final EditText email = findViewById(R.id.emailAddress);
        final EditText pass1 = findViewById(R.id.cpassword);
        final EditText pass2 = findViewById(R.id.cpassword2);

        Button register = findViewById(R.id.Register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pass1.getText().toString().equals(pass2.getText().toString()))
                {
                    Intent i = new Intent(getApplicationContext(),signup.class);
                    startActivity(i);
                }
                else{
                    String txt_email = email.getText().toString();
                    String txt_password = pass1.getText().toString();

                    if(TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_email)) {
                        Toast.makeText(signup.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                    } else if(txt_password.length() < 6){
                        Toast.makeText(signup.this, "Password must be of length atleast 6 characters", Toast.LENGTH_SHORT).show();
                    } else{
                        register(txt_email,txt_password);
                    }
                }

            }
        });
        Button login = findViewById(R.id.signin_here);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(),login.class);
                startActivity(s);
            }
        });
    }
    private void register(final String email, final String password)
    {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users");

                    c.setEmail(email);
                    c.setPassword(password);
                    DatabaseReference keyRef = reference.child(userid);
                    keyRef.setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent s = new Intent(getApplicationContext(),createProfile.class);
                                s.putExtra("contact",c);
                                startActivity(s);
                            }
                        }
                    });
                } else {
                    Toast.makeText(signup.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}