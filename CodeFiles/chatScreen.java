package com.example.i170212_i170321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class chatScreen extends AppCompatActivity {

    Chat chat;
    Contact contact;
    Contact receiver;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;
    EditText text;
    ImageView send;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        getSupportActionBar().hide();

        text=findViewById(R.id.text_of_msg);
        send=findViewById(R.id.send_button);

        key = new String();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        contact = new Contact();
        if(user != null) {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    contact = dataSnapshot.getValue(Contact.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            receiver = (Contact) getIntent().getSerializableExtra("receiver");
            final DatabaseReference ref2 = reference.child("chats");
            final List<Chat> all = new ArrayList<Chat>();
            ref2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot d: dataSnapshot.getChildren())
                    {
                        if(d.getValue(Chat.class).getReceiver().getEmail().equals(receiver.getEmail()))
                        {
                            key = d.getKey();
                            chat = d.getValue(Chat.class);
                            break;
                        }

                    }


//                    profileImage.setImageURI(Uri.parse(receiver.pic));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            RecyclerView recyclerView = findViewById(R.id.chat_rv);
            final DatabaseReference msg_ref = ref2.child(key);
            final ChatAdapter adapter = new ChatAdapter(msg_ref,contact, getApplicationContext());
            RecyclerView.LayoutManager lm = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(lm);
            recyclerView.setAdapter(adapter);

            ImageView profileImage = (ImageView) findViewById(R.id.profileImage);
            Glide.with(getApplicationContext()).load(Uri.parse(receiver.pic)).into(profileImage);


            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Message m = new Message();
                    m.setContent(text.getText().toString());
                    m.setTime((new Date()).toString());
                    chat.getMessages().add(m);
                    msg_ref.push().setValue(m);
                    adapter.notifyDataSetChanged();

                }
            });
        }

    }
}