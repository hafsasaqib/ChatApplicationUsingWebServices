package com.example.i170212_i170321;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapplication.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class UserInfoDisplay extends AppCompatActivity {

    Contact c;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_display);
        getSupportActionBar().hide();

        c = (Contact) getIntent().getSerializableExtra("contact");
        ImageView photo = findViewById(R.id.userImage);
        TextView name = findViewById(R.id.userName);
        TextView phone = findViewById(R.id.userPhone);
        TextView age = findViewById(R.id.userAge);
        TextView bio = findViewById(R.id.userBio);

        photo.setImageURI(Uri.parse(c.pic));
        name.setText(c.getUsername());
        phone.setText(c.getPhone());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Date d = new Date();
        try{
            d = sdf.parse(c.getDob());
        }
        catch (Exception c)
        {
            c.printStackTrace();
        }

        Calendar cl = Calendar.getInstance();
        cl.setTime(d);
        int year = cl.get(Calendar.YEAR);
        int month = cl.get(Calendar.MONTH) + 1;
        int date = cl.get(Calendar.DATE);
        LocalDate l1 = LocalDate.of(year, month, date);
        LocalDate now1 = LocalDate.now();
        Period diff1 = Period.between(l1, now1);


        age.setText(c.getGender()+", "+diff1.getYears());
        bio.setText(c.getBio());
    }
}