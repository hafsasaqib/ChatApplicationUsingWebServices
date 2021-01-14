package com.example.i170212_i170321;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageMessageAdapter extends RecyclerView.Adapter<ManageMessageAdapter.MyViewHolder> {
    private Contact contact;
    private Context c;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;

    public ManageMessageAdapter(Context c) {

        this.c = c;
        this.contact = new Contact();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if(user != null) {
            reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    contact= dataSnapshot.getValue(Contact.class);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


    @NonNull
    @Override
    public ManageMessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.message_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManageMessageAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(contact.chats.get(position).getReceiver().getUsername());
        holder.msg.setText(contact.chats.get(position).getMessages().get(contact.chats.get(position).getMessages().size() - 1).getContent());
        Glide.with(c).load(Uri.parse(contact.chats.get(position).getReceiver().pic)).into(holder.pic);
//        holder.pic.setImageURI(Uri.parse(contact.chats.get(position).getReceiver().pic));



    //when the row is clicked, it should take to the chat screen
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, chatScreen.class);
//                i.putExtra("chat", contact.chats.get(position));
//                i.putExtra("contact", contact);
                i.putExtra("receiver",contact.chats.get(position).getReceiver());
                c.startActivity(i);
            }
        });


        holder.row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent i = new Intent(c, DeleteConversation.class);
                i.putExtra("chat",contact.chats.get(position));
                i.putExtra("contact",contact);
                c.startActivity(i);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
//        return contacts.size(); use this instead of below line
        return contact.chats.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, msg;
        ImageView pic;
        RelativeLayout row;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name =(TextView) itemView.findViewById(R.id.name);
            this.msg =(TextView) itemView.findViewById(R.id.mess);
            this.pic = (ImageView) itemView.findViewById(R.id.contactPhoto);
            this.row = (RelativeLayout) itemView.findViewById(R.id.row);


        }
    }
}
