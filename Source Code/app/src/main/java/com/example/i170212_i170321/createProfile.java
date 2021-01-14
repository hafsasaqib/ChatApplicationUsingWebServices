package com.example.i170212_i170321;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class createProfile extends AppCompatActivity {

    Contact c;
    ImageView profilePhoto;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    Uri selectedImage = null;
    Uri photo;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        getSupportActionBar().hide();
        c = new Contact();
        c = (Contact) getIntent().getSerializableExtra("contact");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        final EditText fname = (EditText) findViewById(R.id.FirstName);
        final EditText lname = (EditText) findViewById(R.id.LastName);
        final EditText bio = (EditText) findViewById(R.id.bioField);
        final EditText phone = (EditText) findViewById(R.id.phnoField);
        final EditText dob = (EditText) findViewById(R.id.dobField);
        profilePhoto = (ImageView) findViewById(R.id.profilePhoto);

        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
            }
        });

        final TextView male, female, noInterest;
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        noInterest = findViewById(R.id.notInterested);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setGender("male");
                male.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_parrot));
                male.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_button));
                female.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                noInterest.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setGender("female");
                female.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_parrot));
                female.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_button));
                male.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                noInterest.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            }
        });
        noInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.setGender("not interested");
                noInterest.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.light_parrot));
                noInterest.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_button));
                male.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                female.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            }
        });



        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedImage != null)
                {
                    c.setFname(fname.getText().toString());
                    c.setLname(lname.getText().toString());
                    c.setBio(bio.getText().toString());
                    c.setDob(dob.getText().toString());
                    c.setPhone(phone.getText().toString());
                    c.setUsername(c.getFname()+" "+c.getLname());


                    StorageReference storeRef = FirebaseStorage.getInstance().getReference();
                    storeRef = storeRef.child(user.getUid()+".jpg");
                    storeRef.putFile(selectedImage)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                    task
                                            .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    c.setPic(uri.toString());
                                                }
                                            });

                                }
                            });

                    //save to the server
                    reference.setValue(c).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                //Here write code to send this contact to server for saving purpose
                                Intent s = new Intent(getApplicationContext(), manage_messages.class);
                                s.putExtra("contact",c);
                                startActivity(s);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Select a Valid Picture",Toast.LENGTH_SHORT)
                            .show();
                }


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    profilePhoto.setPadding(0, 0, 0, 0);

                    profilePhoto.setImageURI(selectedImage);
                    c.setPic(selectedImage.toString());
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    profilePhoto.setImageURI(selectedImage);
                    profilePhoto.setPadding(0, 0, 0, 0);
                    c.setPic(selectedImage.toString());
                }
                break;
        }
    }
}