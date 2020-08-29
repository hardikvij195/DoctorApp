package com.example.dapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class UserBookApp extends AppCompatActivity {



    Button SelDate ,  Bok;
    TextView Date , DocDet ;
    DatabaseReference DoctorRef , BookApp , UserRef ;
    private Calendar calendar;
    DatePickerDialog dpd ;
    private int year, month, day;

    int dy = 0, mnth = 0 , yr = 0  ;


    String UserName , UserNum , Name , Phone , Email , Address , Type ;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_app);


        DocDet = (TextView)findViewById(R.id.textView23);
        Date = (TextView)findViewById(R.id.textView25);


        SelDate =  (Button)findViewById(R.id.SelectDate);
        Bok =  (Button)findViewById(R.id.BookApp);

        Date.setText("No Date Selected");

        SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        final String Uid = sharedPrefs.getString("USERID" , "");

        UserRef = FirebaseDatabase.getInstance().getReference("Users/Use/" + Uid + "/Name/") ;
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(dataSnapshot.exists()){


                    UserRef = FirebaseDatabase.getInstance().getReference("Users/Use/" + Uid + "/") ;

                    UserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            if(dataSnapshot.exists()) {

                                UserName = dataSnapshot.child("Name").getValue().toString();
                                UserNum = dataSnapshot.child("Phone Number").getValue().toString();


                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Intent intent = getIntent();
        final String DocId = intent.getExtras().getString("DocId" , "");


        DoctorRef = FirebaseDatabase.getInstance().getReference("Doctor/"  + DocId + "/") ;
        DoctorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                    Name = dataSnapshot.child("Name").getValue().toString();
                    Phone = dataSnapshot.child("Phone").getValue().toString();
                    Email = dataSnapshot.child("Email").getValue().toString();
                    Address = dataSnapshot.child("Address").getValue().toString();
                    Type = dataSnapshot.child("Type").getValue().toString();

                    DocDet.setText("Name : " + Name + "\nType : " + Type + "\nPhone : "+ Phone + "\nEmail : " + Email + "\nAddress : " + Address  );
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        SelDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                dpd = new DatePickerDialog(UserBookApp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int myear, int mmonth, int mday) {

                        dy = mday;
                        mnth = mmonth+1;
                        yr = myear;
                        Date.setText( "Selected Date : " +dy + "/" + mnth + "/" + yr);

                    }
                }, year , month , day);

                dpd.show();


            }
        });




        Bok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mnth != 0 ){

                    String Dt = dy + "-" + mnth + "-" + yr;
                    final String finalDt = Dt;
                    java.util.Date date = new Date();  // to get the date
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); // getting date in this format
                    final String formattedDate = df.format(date.getTime());

                    BookApp = FirebaseDatabase.getInstance().getReference("Appointments/"  + DocId + "/" + formattedDate) ;
                    HashMap<String,String> dataMap1 = new HashMap<String, String>();
                    dataMap1.put("Name" , UserName);
                    dataMap1.put("Num" , UserNum);
                    dataMap1.put("Date" ,finalDt );
                    BookApp.setValue(dataMap1);



                    BookApp = FirebaseDatabase.getInstance().getReference("Appointments/"  + Uid + "/" + formattedDate) ;
                    HashMap<String,String> dataMap2 = new HashMap<String, String>();
                    dataMap2.put("Name" , Name);
                    dataMap2.put("Num" , Phone);
                    dataMap2.put("Date" ,finalDt );
                    dataMap2.put("Email" ,Email );
                    dataMap2.put("Type" ,Type );
                    dataMap2.put("Address" ,Address );
                    BookApp.setValue(dataMap2);


                    Toast.makeText(UserBookApp.this , "Appointment Booked" , Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
}
