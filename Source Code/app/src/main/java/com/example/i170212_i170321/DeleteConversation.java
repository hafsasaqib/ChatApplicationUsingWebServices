package com.example.i170212_i170321;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteConversation extends AppCompatActivity {

    Chat c;
    Contact co;
    FirebaseUser user;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_conversation);
        getSupportActionBar().hide();

        c = (Chat) getIntent().getSerializableExtra("chat");
        co = (Contact) getIntent().getSerializableExtra("contact");

        Button delete = findViewById(R.id.chat_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeChat(co,c);
                Intent i = new Intent(getApplicationContext(), manage_messages.class);
                startActivity(i);
            }
        });

    }

    public void removeChat(Contact ct, final Chat ch)
    {
        //write code here to delete chat from database and update contact
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("chats");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    if(d.getValue(Chat.class).equals(ch))
                    {
                        key = d.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        ref.child(key).removeValue();
    }
}