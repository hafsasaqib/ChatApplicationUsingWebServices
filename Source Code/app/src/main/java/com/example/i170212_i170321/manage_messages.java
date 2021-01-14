package com.example.i170212_i170321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class manage_messages extends AppCompatActivity {

   // List<Chat> chats;
    Contact myself;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_messages);
        getSupportActionBar().hide();
        EditText search = findViewById(R.id.search);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        myself = new Contact();
        if(user != null)
        {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myself = dataSnapshot.getValue(Contact.class);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ImageView menu = findViewById(R.id.menu);

            RecyclerView recyclerView = findViewById(R.id.messages_rv);
            ManageMessageAdapter adapter = new ManageMessageAdapter(this);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(adapter);


            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent s = new Intent(getApplicationContext(),manage_contacts.class);
                    startActivity(s);
                }
            });

            ImageView profile = findViewById(R.id.profile);
            Glide.with(getApplicationContext()).load(Uri.parse(myself.pic)).into(profile);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent s = new Intent(getApplicationContext(),UserProfile.class);
                    s.putExtra("contact",myself);
                    startActivity(s);
                }
            });


            menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                    Intent s = new Intent(getApplicationContext(),login.class);
                    startActivity(s);
                }
            });

        }
        else{
            Intent s = new Intent(getApplicationContext(),login.class);
            startActivity(s);
        }


    }

    public void logout(){
        //write code here to logout
        auth.signOut();
    }
}