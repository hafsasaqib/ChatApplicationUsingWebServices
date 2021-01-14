package com.example.i170212_i170321;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<Message> messages;
    Contact contact;
    private Context c;

    public ChatAdapter(DatabaseReference ref, Contact co, Context c) {
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                messages.clear();
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    messages.add(d.getValue(Message.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        this.contact = co;
        this.c = c;
    }


    @NonNull
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.chat_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatAdapter.MyViewHolder holder, final int position) {

        holder.msg.setText(messages.get(position).getContent());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        if(messages.get(position).sender.equals(contact.getEmail()))
        {
            holder.msg.setBackgroundColor(Color.parseColor("#2ADC65"));
            holder.msg.setTextColor(ContextCompat.getColor(c,R.color.white));
            params.setMargins(100, 0, 0, 0);
            holder.row.setLayoutParams(params);

        }
        else{
            holder.msg.setBackgroundColor(ContextCompat.getColor(c,R.color.back));
            holder.msg.setTextColor(ContextCompat.getColor(c,R.color.black));
            params.setMargins(0, 0, 100, 0);
            holder.row.setLayoutParams(params);
        }



    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView msg;
        RelativeLayout row;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.msg =(TextView) itemView.findViewById(R.id.chat_msg);
            this.row =(RelativeLayout) itemView.findViewById(R.id.chat_row);



        }
    }
}
