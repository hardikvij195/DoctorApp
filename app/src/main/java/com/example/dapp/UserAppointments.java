package com.example.dapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class UserAppointments extends AppCompatActivity {



    ListView DocApp ;
    private DatabaseReference DRef ;

    private ArrayList<UserAppClass> DocNames = new ArrayList<>() ;
    UserAppListAdapter adap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_appointments);


        DocApp = (ListView)findViewById(R.id.ListViewDocApp);
        adap = new UserAppListAdapter(getApplicationContext() , DocNames);
        DocApp.setAdapter(adap);

        SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        String id = sharedPrefs.getString("USERID" , "" );

        DRef = FirebaseDatabase.getInstance().getReference("Appointments/" + id) ;
        DRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                DocNames.clear();
                adap.notifyDataSetChanged();

                if(dataSnapshot.exists()){

                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){

                        String Name = dataSnapshot1.child("Name").getValue().toString();
                        String Phone = dataSnapshot1.child("Num").getValue().toString();
                        String Date = dataSnapshot1.child("Date").getValue().toString();
                        String Address = dataSnapshot1.child("Address").getValue().toString();
                        String Email = dataSnapshot1.child("Email").getValue().toString();
                        String Type = dataSnapshot1.child("Type").getValue().toString();

                        DocNames.add(new UserAppClass(Name , Phone ,Email , Address ,Type , Date ));
                        adap.notifyDataSetChanged();


                    }

                    Collections.reverse(DocNames);
                    adap.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







    }
}
