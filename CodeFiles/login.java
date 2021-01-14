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

public class login extends AppCompatActivity {

    EditText email;
    EditText password;
    public Contact c;
    FirebaseAuth auth;
    FirebaseUser fUser;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        c = new Contact();
        auth = FirebaseAuth.getInstance();
        fUser = auth.getCurrentUser();


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        Button register = findViewById(R.id.register_here);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_email)|| TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(login.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                } else{
                    auth.signInWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(login.this, manage_messages.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(login.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(),signup.class);
                startActivity(s);
            }
        });
    }

    public void loadUser()
    {
        //write code to read contact from database
    }
}