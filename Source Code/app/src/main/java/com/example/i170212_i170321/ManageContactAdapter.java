package com.example.i170212_i170321;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ManageContactAdapter extends RecyclerView.Adapter<ManageContactAdapter.MyViewHolder> {
    private List<Contact> contacts;
    private Contact contact;
    private Context co;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;

    public ManageContactAdapter(Context co, List<Contact> c) {
        this.co = co;
        this.contact = new Contact();
        this.contacts = c;

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
    public ManageContactAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(co).inflate(R.layout.contacts_row, parent, false);

        return new MyViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final ManageContactAdapter.MyViewHolder holder, final int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date d = new Date();
        try{
            d = sdf.parse(contacts.get(position).getDob());
        }
        catch (Exception c)
        {
            c.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);

        holder.name.setText(contacts.get(position).getUsername());
        holder.msg.setText(contacts.get(position).getGender()+", "+diff1.getYears());
        Glide.with(co).load(Uri.parse(contacts.get(position).getPic())).into(holder.pic);
//        holder.pic.setImageURI(Uri.parse(contacts.get(position).getPic()));

        //when photo of contact is clicked, its info should be displayed
        holder.pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(co, UserInfoDisplay.class);
                i.putExtra("contact",contacts.get(position));
                co.startActivity(i);
            }
        });

        //when remaining space of contact is clicked, its chat screen should be displayed
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(co, chatScreen.class);
//                Chat chat = new Chat();
//                for(Chat e:contact.chats)
//                {
//                    if(e.getReceiver().getEmail().equals(contacts.get(position).getEmail())){
//                        chat = e;
//                        break;
//                    }
//                }
//                i.putExtra("chat",chat);
                i.putExtra("receiver",contacts.get(position));
                co.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return contacts.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, msg;
        ImageView pic;
        LinearLayout row;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name =(TextView) itemView.findViewById(R.id.name);
            this.msg =(TextView) itemView.findViewById(R.id.gender_age);
            this.pic = (ImageView) itemView.findViewById(R.id.contact_photo);
            this.row = (LinearLayout) itemView.findViewById(R.id.contact_content);


        }
    }
}
