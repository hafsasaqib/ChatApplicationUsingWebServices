package com.example.i170212_i170321;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class manage_contacts extends AppCompatActivity {

    Contact c;
    ManageContactAdapter adapter;
    DatabaseReference reference;
    List<Contact> filtered;
    FirebaseUser user;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_contacts);
        getSupportActionBar().hide();

        filtered = new ArrayList<Contact>();
        ImageView back = findViewById(R.id.goBack);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        c =  new Contact();
        final EditText search = findViewById(R.id.auto_complete);



        if(user != null) {
            RecyclerView recyclerView = findViewById(R.id.contacts_rv);

            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    c = dataSnapshot.getValue(Contact.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Users");
            final List<Contact> all = new ArrayList<Contact>();
            ref2.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    all.clear();
                    for(DataSnapshot d: dataSnapshot.getChildren())
                    {
                        if(!d.getValue(Contact.class).getEmail().equals(c.getEmail()))
                        {
                            all.add(d.getValue(Contact.class));
                        }

                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    all.clear();
                    for(DataSnapshot d: dataSnapshot.getChildren())
                    {
                        if(!d.getValue(Contact.class).getEmail().equals(c.getEmail()))
                        {
                            all.add(d.getValue(Contact.class));
                        }

                    }
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    all.clear();
                    for(DataSnapshot d: dataSnapshot.getChildren())
                    {
                        if(!d.getValue(Contact.class).getEmail().equals(c.getEmail()))
                        {
                            all.add(d.getValue(Contact.class));
                        }

                    }
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            if(search.getText().toString() != null)
            {
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        String text = search.getText().toString();
                        if(text != null)
                        {
                            filtered.clear();
                            for(Contact cont:all)
                            {
                                if(cont.getUsername().contains(charSequence))
                                {
                                    filtered.add(cont);
                                }
                            }
                            adapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {


                    }
                });
            }
            if(filtered.size() == 0)
            {
                filtered = all;
            }
            adapter = new ManageContactAdapter(this, filtered);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(adapter);

        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent s = new Intent(getApplicationContext(),manage_messages.class);
                startActivity(s);
            }
        });
    }
}